package com.seraptemel.etsdemo;

import com.seraptemel.etsdemo.property.FileServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileServiceProperties.class
})
public class EtsDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtsDemoApplication.class, args);
	}

}
