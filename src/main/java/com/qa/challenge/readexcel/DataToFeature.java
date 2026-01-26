package com.qa.challenge.readexcel;



import com.qa.challenge.exceptions.PropertiesDoesNotLoadException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.qa.challenge.utils.Constans.CLASE_UTILIDAD;
import static com.qa.challenge.utils.Constans.MARKER;

/**
 * Java 17 compatible.
 *
 * Pre-processes .feature files replacing a custom marker:
 *   ##@externaldata@<excelPath>@<sheetName>[@<selector>]
 *
 * selector formats:
 * - omitted            -> all data rows
 * - N                  -> only row N (1-based)
 * - A-B                -> range A..B inclusive (1-based)
 * - A,B,C              -> multiple specific rows (1-based)
 *
 * It overwrites feature files in-place.
 */
public final class DataToFeature {

  //  private static final String MARKER = "##@externaldata";
    //public static final String CLASE_UTILIDAD = "Clase Utilidad";

    private DataToFeature() {
        throw new IllegalStateException(CLASE_UTILIDAD);
    }

    private static List<String> setExcelDataToFeature(File featureFile) throws IOException {
        List<String> output = new ArrayList<>();

        boolean skipExistingTableRows = false;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(featureFile), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {

                // 1) If we just injected a table, skip old table rows in the feature
                if (skipExistingTableRows && isGherkinTableRow(line)) {
                    continue;
                }
                if (!isGherkinTableRow(line)) {
                    skipExistingTableRows = false;
                }

                // 2) Detect marker
                if (line.trim().contains(MARKER)) {
                    output.add(line); // keep marker line as-is (you can remove if desired)

                    MarkerInfo info = parseMarker(line.trim());
                    List<Map<String, String>> excelData;
                    try {
                        excelData = new LectorExcel().getData(info.excelPath, info.sheetName);
                    } catch (Exception e) {
                        throw new PropertiesDoesNotLoadException(e);
                    }

                    // 3) Inject rows selected from Excel as |...| lines
                    List<Integer> selectedRowIndexes = selectRowIndexes(excelData.size(), info.selector);

                    for (int rowIndex : selectedRowIndexes) {
                        Map<String, String> row = excelData.get(rowIndex);

                        StringBuilder sb = new StringBuilder();
                        // Keep insertion order: LectorExcel should return LinkedHashMap
                        for (Map.Entry<String, String> cell : row.entrySet()) {
                            sb.append("   |").append(safe(cell.getValue()));
                        }
                        sb.append("|");
                        output.add(sb.toString());
                    }

                    // After injection, skip the old table rows that may exist below in the feature
                    skipExistingTableRows = true;
                    continue;
                }

                // default: keep line
                output.add(line);
            }
        }

        return output;
    }

    private static boolean isGherkinTableRow(String line) {
        String t = line.trim();
        return t.startsWith("|") || t.endsWith("|");
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }

    private static List<Integer> selectRowIndexes(int excelRowCount, String selector) {
        // excelData returned by LectorExcel typically contains only data rows (not headers),
        // so index 0 = first data row.
        if (excelRowCount <= 0) {
            return List.of();
        }

        // If selector is null/blank => all rows
        if (selector == null || selector.isBlank()) {
            List<Integer> all = new ArrayList<>(excelRowCount);
            for (int i = 0; i < excelRowCount; i++) all.add(i);
            return all;
        }

        selector = selector.trim();

        // Multiple: "2,5,9"
        if (selector.contains(",")) {
            String[] parts = selector.split(",");
            List<Integer> indexes = new ArrayList<>();
            for (String p : parts) {
                int oneBased = Integer.parseInt(p.trim());
                int idx = oneBased - 1;
                if (idx >= 0 && idx < excelRowCount) {
                    indexes.add(idx);
                }
            }
            return indexes;
        }

        // Range: "2-5" (inclusive)
        if (selector.contains("-")) {
            String[] parts = selector.split("-");
            int start = Integer.parseInt(parts[0].trim());
            int end = Integer.parseInt(parts[1].trim());

            // normalize
            if (end < start) {
                int tmp = start;
                start = end;
                end = tmp;
            }

            int startIdx = Math.max(0, start - 1);
            int endIdx = Math.min(excelRowCount - 1, end - 1);

            List<Integer> indexes = new ArrayList<>();
            for (int i = startIdx; i <= endIdx; i++) {
                indexes.add(i);
            }
            return indexes;
        }

        // Single: "3"
        int oneBased = Integer.parseInt(selector);
        int idx = oneBased - 1;
        if (idx < 0 || idx >= excelRowCount) {
            return List.of();
        }
        return List.of(idx);
    }

    private static MarkerInfo parseMarker(String markerLine) {
        // expected: ##@externaldata@path@sheet[@selector]
        String[] parts = markerLine.split("@");
        if (parts.length < 4) {
            throw new IllegalArgumentException(
                    "Invalid externaldata marker. Expected: ##@externaldata@<excelPath>@<sheetName>[@<selector>]");
        }

        String excelPath = parts[2].trim();
        String sheetName = parts[3].trim();
        String selector = (parts.length >= 5) ? parts[4].trim() : "";

        if (excelPath.isBlank() || sheetName.isBlank()) {
            throw new IllegalArgumentException("Invalid marker: excelPath and sheetName are required.");
        }

        return new MarkerInfo(excelPath, sheetName, selector);
    }

    private static List<File> listOfFeatureFiles(File folder) {
        List<File> featureFiles = new ArrayList<>();

        if (folder.isFile() && folder.getName().endsWith(".feature")) {
            featureFiles.add(folder);
            return featureFiles;
        }

        File[] files = folder.listFiles();
        if (files == null) return featureFiles;

        for (File entry : files) {
            if (entry.isDirectory()) {
                featureFiles.addAll(listOfFeatureFiles(entry));
            } else if (entry.isFile() && entry.getName().endsWith(".feature")) {
                featureFiles.add(entry);
            }
        }
        return featureFiles;
    }

    public static void overrideFeatureFiles(String featuresDirectoryPath) throws IOException {
        List<File> features = listOfFeatureFiles(new File(featuresDirectoryPath));
        for (File featureFile : features) {
            List<String> updated = setExcelDataToFeature(featureFile);

            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(featureFile), StandardCharsets.UTF_8))) {
                for (String s : updated) {
                    writer.write(s);
                    writer.newLine();
                }
            }
        }
    }

    private record MarkerInfo(String excelPath, String sheetName, String selector) {}
}
