package utilities.listeners;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.IAnnotation;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransformer implements IAnnotationTransformer {

    public void transform(ITestAnnotation annotation, Class tesclass, Constructor constructor, Method testMethod){
        annotation.setRetryAnalyzer(Retry.class);
    }
}
