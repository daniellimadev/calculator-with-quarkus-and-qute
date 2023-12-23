package com.github.daniellimadev.util;

import io.quarkus.qute.TemplateExtension;

import java.math.BigDecimal;

@TemplateExtension
public class Fomater {
    
    public static String formatSign(BigDecimal value) {
        return "US$ ".concat(value.toString().replace(".", ","));
    }

}
