package com.hdfcbank.app.info;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(CoreEngineImportSelector.class)
public @interface EnableCore {
}
