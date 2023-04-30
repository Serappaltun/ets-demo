package com.seraptemel.jwtdemo.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
@Data
public class FileServiceProperties {
    private String uploadFolder;
}
