package com.nt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Report API", version = "3.0", description = "Course Information"))
public class ReportApi01Application {

	public static void main(String[] args) {
		SpringApplication.run(ReportApi01Application.class, args);
	}

}
