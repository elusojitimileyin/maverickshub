package com.maverickstube.maverickshub.security.utils;

import java.util.List;

public class SecurityUtils {

    private SecurityUtils() {} // Prevent instantiation of utility class

    public static final List<String> PUBLIC_ENDPOINT = List.of("/api/v1/auth");

    public static final String JWT_PREFIX = "Bearer";
}
