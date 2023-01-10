package com.cmeza.spring.security.jwt.domain.usecase.utils;

public final class JwtConstants {

    public static final String TIME = "1h";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String TOKEN_PATH = "/auth/token";
    public static final String VALIDATE_PATH = "/auth/validate";
    public static final String SECURED_PATH = "/**";
    public static final String ROLE = "ROLE_";
    public static final String PROPERTY_PREFIX = "spring.security.jwt";
    public static final String PROPERTY_PATHS_PREFIX = "spring.security.jwt.paths";

    public static final String HOUR_SUFFIX = "h";
    public static final String MINUTE_SUFFIX = "min";
    public static final String SECOND_SUFFIX = "sec";
    public static final String MILLISECOND_SUFFIX = "mill";
    public static final String YEAR_SUFFIX = "y";
    public static final String MONTH_SUFFIX = "m";
    public static final String WEEK_SUFFIX = "w";
    public static final String DAY_SUFFIX = "d";

    private JwtConstants() {
    }
}
