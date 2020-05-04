package com.gly.dcs.annotation;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.TYPE})
public @interface AccessLimit {

    int limit() default 5;
    int sec() default 5;
    
}
