package com.ct.usecase.demo.util;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtil {

    public static Long getCurrentOrgId() {
        Claims claims =
            (Claims) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getDetails();
        return claims.get("orgId", Long.class);
    }
}

