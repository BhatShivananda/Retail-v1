package com.app.retail.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@RefreshScope
@Component
public class AuthFilterProps {
    private final String serviceName;
    private final String accessToken;
    private final String commaSeparatedBypassUrlList;
    private final boolean proceedOnUnauthorized;

    @Autowired
    public AuthFilterProps(
            @Value("${spring.application.name:ServiceNameNotFound}") String serviceName,
            @Value("${access.token:Bearer access_token}") String accessToken,
            @Value("${bypass.url.list:/swagger-ui.html}") String commaSeparatedBypassUrlList,
            @Value("${service.security.proceedOnUnauthorized:false}") Boolean proceedOnUnauthorized){
        this.serviceName = serviceName;
        this.accessToken = accessToken;
        this.commaSeparatedBypassUrlList = commaSeparatedBypassUrlList;
        this.proceedOnUnauthorized = proceedOnUnauthorized;
    }
}
