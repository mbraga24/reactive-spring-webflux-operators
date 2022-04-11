package com.reactivespring.service;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class FluxAndMonoGeneratorServiceTest {

	FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
	
	@Test
	void namesFlux() {
		
		var namesFlux = fluxAndMonoGeneratorService.namesFlux();
		
		// then
		StepVerifier.create(namesFlux)
//			.expectNext("Alex", "Joye", "Mark") // expecting the exact elements
//			.expectNextCount(3) // expecting the exact number of elements
			.expectNext("Alex") // 1. expecting the first element 
			.expectNextCount(2) // 2. expecting the remaining count of elements
			.verifyComplete();
	}
	
	
}
