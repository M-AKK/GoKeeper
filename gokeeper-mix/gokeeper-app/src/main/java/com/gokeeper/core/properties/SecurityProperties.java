package com.gokeeper.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhailiang
 *
 */
@Data
@ConfigurationProperties(prefix = "gokeeper.validate")
public class SecurityProperties {

    private ValidateCodeProperties code;


}

