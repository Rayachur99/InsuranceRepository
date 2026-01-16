package com.ct.usecase.demo;

import org.springframework.boot.SpringApplication;

public class TestInsuranceEligibilityApplication {

	public static void main(String[] args) {
		SpringApplication.from(InsuranceEligibilityApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
