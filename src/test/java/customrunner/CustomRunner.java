package customrunner;



import com.qa.challenge.readexcel.BeforeSuite;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class CustomRunner extends Runner {

    private static final Logger logger = LogManager.getLogger(CustomRunner.class);

    /** This is the JUnit runner class annotated with @RunWith(CustomRunner.class) and @CucumberOptions */
    private final Class<?> junitRunnerClass;

    /** Serenity runner that actually executes Cucumber + Serenity */
    private CucumberWithSerenity serenityRunner;

    public CustomRunner(Class<?> junitRunnerClass) throws InitializationError {
        this.junitRunnerClass = junitRunnerClass;
        this.serenityRunner = new CucumberWithSerenity(junitRunnerClass);
    }

    @Override
    public Description getDescription() {
        return serenityRunner.getDescription();
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            runAnnotatedStaticMethods(junitRunnerClass, BeforeSuite.class);

            // Re-create runner after BeforeSuite in case it generated data/features/config
            serenityRunner = new CucumberWithSerenity(junitRunnerClass);

        } catch (InitializationError e) {
            logger.error("Failed to initialize Serenity runner", e);
            throw new RuntimeException(e);

        } catch (InvocationTargetException e) {
            logger.error("A @BeforeSuite method threw an exception", e.getTargetException());
            throw new RuntimeException(e.getTargetException());

        } catch (IllegalAccessException e) {
            logger.error("Could not access a @BeforeSuite method", e);
            throw new RuntimeException(e);
        }

        serenityRunner.run(notifier);
    }

    private static void runAnnotatedStaticMethods(Class<?> targetClass, Class<? extends Annotation> annotation)
            throws InvocationTargetException, IllegalAccessException {

        if (annotation == null || !annotation.isAnnotation()) {
            return;
        }

        // getMethods() = public methods including inherited
        // If you also want private methods, switch to getDeclaredMethods() and setAccessible(true)
        Method[] methods = targetClass.getMethods();

        for (Method method : methods) {
            if (!method.isAnnotationPresent(annotation)) {
                continue;
            }

            // Enforce static + no-args because we will invoke with null target
            boolean isStatic = Modifier.isStatic(method.getModifiers());
            boolean noArgs = method.getParameterCount() == 0;

            if (!isStatic || !noArgs) {
                throw new IllegalStateException(
                        "Method " + method.getDeclaringClass().getName() + "#" + method.getName()
                                + " is annotated with @" + annotation.getSimpleName()
                                + " but must be public static and have no parameters."
                );
            }

            method.invoke(null);
        }
    }
}
