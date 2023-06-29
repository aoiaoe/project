package com.cz.springcloudsdk;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreCommonResponse {
}
