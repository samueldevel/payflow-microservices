package com.samueldev.project.authentication.security.jwt;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "jwt.config")
@Data
public class JwtProperties {

    private String loginUrl = "/login/**";

    @NestedConfigurationProperty
    private Header header = new Header();

    private int expiration = 3600;

    private String secret = "JSGpscRfOZHEQeuRCXHC";

    @Getter
    @Setter
    public static class Header {
        private String prefix = "Bearer ";
    }
}
