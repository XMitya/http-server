package ru.ifmo.server.annotation;

import ru.ifmo.server.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Aleksey P. on 27/12/2017.
 */
@Target(value= ElementType.METHOD)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface Url {
    String url();
    HttpMethod method() default HttpMethod.GET;
    HttpMethod[] methods() default {HttpMethod.GET};
}
