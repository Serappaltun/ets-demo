package com.seraptemel.etsdemo.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
@Data
public class FileServiceProperties {
    private String uploadFolder;
}
