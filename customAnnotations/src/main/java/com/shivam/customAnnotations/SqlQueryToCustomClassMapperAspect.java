package com.shivam.customAnnotations;


import jakarta.persistence.Column;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * This class is an Aspect that converts SQL queries into JSON format.
 * It uses Spring's JdbcTemplate to execute the SQL queries and returns the result as a list of maps.
 * The aspect is triggered by methods annotated with the {@link SqlQueryToCustomClassMapper} annotation.
 *
 * @author Shivam
 * @since 1.0
 */
@Component
@Aspect
public class SqlQueryToCustomClassMapperAspect {

    /**
     * The JdbcTemplate instance used to execute SQL queries.
     */
    private final JdbcTemplate jdbcTemplate;


    /**
     * Constructor for the QueryToJsonAspect class.
     * It injects the JdbcTemplate instance using Spring's dependency injection.
     *
     * @param jdbcTemplate the JdbcTemplate instance
     */
    @Autowired
    public SqlQueryToCustomClassMapperAspect(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * This method is around advice that intercepts methods annotated with {@link SqlQueryToCustomClassMapper}.
     * It executes the SQL query specified in the annotation and returns the result as a list of objects.
     *
     * @param joinPoint the join point for the method being intercepted. This is used to get the method signature and annotation.
     * @return the result of the SQL query as a list of objects. Each object is an instance of the class specified in the {@link SqlQueryToCustomClassMapper} annotation.
     * @throws Throwable if an error occurs during the execution of the SQL query or the mapping process.
     */
    @Around("@annotation(com.shivam.customAnnotations.SqlQueryToCustomClassMapper)")
    public List<Object> aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        // Get the MyCustomQuery annotation from the method signature
        SqlQueryToCustomClassMapper sqlQueryToCustomClassMapper = ((MethodSignature) joinPoint.getSignature())
                .getMethod()
                .getAnnotation(SqlQueryToCustomClassMapper.class);

        // Get the SQL query and the class to map the result to from the annotation
        String query = sqlQueryToCustomClassMapper.query();
        Class<?> mapperClass = sqlQueryToCustomClassMapper.mapperClass();

        // Execute the SQL query and get the result as a list of maps
        List<Map<String, Object>> queried = this.jdbcTemplate.queryForList(query);

        // Create a new list to store the result objects
        List<Object> result = new ArrayList<>();

        // Iterate over the queried maps and map each map to an object of the specified class
        for (Map<String, Object> q : queried) {
            Object object = mapData(q, mapperClass);
            result.add(object);
        }

        // Return the list of result objects
        return result;
    }


    /**
     * This method maps the data from a Map to an object of a given class.
     *
     * @param <T>            the type of the object to be created. It should be a class that has fields annotated with {@link Column}.
     * @param resultMap      the map containing the data to be mapped. The keys of the map should match the uppercase names of the fields in the class.
     * @param userGivenClass the class of the object to be created. The class should have fields annotated with {@link Column}.
     * @return an object of the given class, with its fields populated from the map.
     * @throws RuntimeException if an error occurs during the mapping process. This can happen due to issues with instantiation, reflection, or access control.
     */
    public <T> T mapData(Map<String, Object> resultMap, Class<T> userGivenClass) {

        try {
            // Create a new instance of the given class
            T newInstance = userGivenClass.getDeclaredConstructor().newInstance();

            // Iterate over the map entries
            for (Map.Entry<String, Object> datum : resultMap.entrySet()) {

                String key = datum.getKey().toUpperCase();
                Object value = datum.getValue();

                // Get the field in the class with the same name as the map key
                for (Field declaredField : userGivenClass.getDeclaredFields()) {

                    Column annotation = declaredField.getAnnotation(Column.class);
                    if (annotation != null && annotation.name().equals(key)) {

                        // Make the field accessible
                        declaredField.setAccessible(true);

                        // Set the field value in the new object instance
                        declaredField.set(newInstance, value);
                    }

                }

            }

            // Return the new object instance
            return newInstance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            // Throw a runtime exception if an error occurs during the instantiation or reflection process
            throw new RuntimeException(e);
        }
    }
}