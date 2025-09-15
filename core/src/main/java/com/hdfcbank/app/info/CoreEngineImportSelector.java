package com.hdfcbank.app.info;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class CoreEngineImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                "com.hdfcbank.app.info.CoreEngineConfiguration"
        };
    }
}
