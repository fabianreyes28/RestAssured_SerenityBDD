================================================================================
                    README - QA CHALLENGE E2E SAUCEDEMO
                    Automatización con SerenityBDD + ScreenPlay
================================================================================

DESCRIPCIÓN:
Prueba funcional automatizada (E2E) del flujo de compra en SauceDemo que incluye:
- Autenticarse con usuario: standard_user y password: secret_sauce
- Agregar dos productos al carrito
- Visualizar el carrito
- Completar el formulario de compra
- Finalizar la compra hasta la confirmación: "THANK YOU FOR YOUR ORDER"

================================================================================
1. PREREQUISITOS
================================================================================

Para ejecutar este proyecto se requiere tener configurado en la máquina local:

- Sistema Operativo: Windows 10 / Windows 11
- IDE: IntelliJ IDEA 2023.1 o superior
- JDK: versión 17 (debe estar en la variable de entorno JAVA_HOME)
- Gradle: versión 7.6.1 (debe estar en la variable de entorno PATH)
- Google Chrome: última versión instalada

VERIFICAR INSTALACIÓN:

    java -version
    (Debe mostrar: openjdk version "17.x.x")

    gradle -v
    (Debe mostrar: Gradle 7.6.1)

================================================================================
2. COMANDOS DE INSTALACIÓN
================================================================================

PASO 1: Ingresar al directorio del proyecto

    cd qa-challenge-e2e-serenity

PASO 2: Descargar todas las dependencias del build.gradle

    gradle clean build -x test

================================================================================
3. INSTRUCCIONES PARA EJECUTAR LOS TESTS
================================================================================

PASO 1: Ejecutar los tests

    gradle clean test

PASO 2: Esperar a que finalice la ejecución (aproximadamente 30 segundos)

PASO 3: Verificar el resultado en la consola

    BUILD SUCCESSFUL

PASO 4: Abrir el reporte de Serenity

    start target/site/serenity/index.html

UBICACIÓN DE REPORTES:

    Reporte Serenity HTML: target/site/serenity/index.html

================================================================================
4. INFORMACIÓN ADICIONAL
================================================================================

ESTRUCTURA DEL PROYECTO:

    qa-challenge-e2e-serenity/
    ├── src/main/java/com/qa/challenge/
    │   ├── tasks/              (Acciones del usuario)
    │   └── userinterfaces/     (Localizadores de elementos)
    ├── src/test/java/com/qa/challenge/
    │   ├── glue/               (Step Definitions)
    │   └── runners/            (Test Runner)
    ├── src/test/resources/
    │   ├── features/           (Escenarios Gherkin)
    │   └── serenity.conf       (Configuración del navegador)
    ├── build.gradle
    ├── readme.txt
    └── conclusiones.txt

TECNOLOGÍAS Y VERSIONES:

    | Tecnología       | Versión  |
    |------------------|----------|
    | Java             | 17       |
    | Gradle           | 7.6.1    |
    | SerenityBDD      | 4.0.14   |
    | Cucumber         | 7.14.0   |
    | JUnit            | 4.13.2   |
    | WebDriverManager | 5.6.3    |

DATOS DE PRUEBA (Parametrizados en feature file):

    | username      | password     | producto1           | producto2             |
    | standard_user | secret_sauce | Sauce Labs Backpack | Sauce Labs Bike Light |

================================================================================
AUTOR: Kevin Donoso
================================================================================