package com.shivam.customAnnotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This custom annotation is used to define a custom query for a method.
 * The query is defined using the 'query' attribute of this annotation.
 *
 * @author Shivam
 * @version 1.0
 * @since 2022-01-01
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlQueryToCustomClassMapper {

    /**
     * The custom query to be executed.
     *
     * @return the custom query
     */
    String query();
    Class<?> mapperClass();
}
