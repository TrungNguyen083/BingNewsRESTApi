package org.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GetMapping {
    @AliasFor(
            annotation = RequestMapping.class
    )
    String name() default "";
    @AliasFor(
            annotation = RequestMapping.class
    )
    String value() default "";
    @AliasFor(
            annotation = RequestMapping.class
    )
    String path() default "";

    @AliasFor(
            annotation = RequestMapping.class
    )
    String params() default "";
}
