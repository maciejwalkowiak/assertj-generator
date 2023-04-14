package com.maciejwalkowiak.assertj.generator;

import com.maciejwalkowiak.assertj.generator.util.StringUtils;

public class MethodNames {

    static String has(MethodDescription m) {
        return "has" + StringUtils.capitalize(m.fieldName());
    }

    static String hasNo(MethodDescription m) {
        return "hasNo" + StringUtils.capitalize(m.fieldName());
    }

    static String hasSatisfying(MethodDescription m) {
        return has(m) + "Satisfying";
    }
}
