package com.ruxuanwo.data.export;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ruxuanwo
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.ruxuanwo"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}
}
