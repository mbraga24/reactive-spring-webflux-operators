package com.reactivespring.service;

import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class FluxAndMonoGeneratorServiceTest {

	FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
	
	@Test
	void namesFlux() {
		
		var namesFlux = fluxAndMonoGeneratorService.namesFlux();
		
		StepVerifier.create(namesFlux)
         // .expectNext("Alex", "Joye", "Mark") // expecting the exact elements
         // .expectNextCount(3) // expecting the exact number of elements
			.expectNext("Alex") // 1. expecting the first element 
			.expectNextCount(2) // 2. expecting the remaining count of elements
			.verifyComplete();
	}
	
	
	@Test
	void namesFlux_map() {
		
		var namesFlux = fluxAndMonoGeneratorService.namesFlux_map();
		
		StepVerifier.create(namesFlux)
			.expectNext("ALEX", "JOYE", "MARK")
			.verifyComplete();
		
	}
	
	@Test
	void namesFlux_immutability() {
		
		var namesFlux = fluxAndMonoGeneratorService.namesFlux_immutability();
		
		StepVerifier.create(namesFlux)
         // .expectNext("ALEX", "JOYE", "MARK") // this test case won't pass due the immutable nature of Reactive String
		    .expectNext("Alex", "Joye", "Mark") // test passes!
			.verifyComplete();
		
	}
	
	@Test
	void namesFlux_filter() {

		// given
		int strLength = 3;
		
		// when
		var namesFlux = fluxAndMonoGeneratorService.namesFlux_filter(strLength);
		
		// then
		StepVerifier.create(namesFlux)
			.expectNext("4: ALEX", "4: JOYE", "4: MARK")
			.verifyComplete();
			
	}	
	
	@Test
	void namesFlux_flatmap() {
		
		// given 
		
		// when
		var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap();
		
		// then
		StepVerifier.create(namesFlux)
		 	.expectNext("A","L","E","X","R","O","D")
		 	.verifyComplete();	
	}
	
	@Test
	void namesFlux_flatmap_async() {
		
		var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap_async();
		
		StepVerifier.create(namesFlux)
         // .expectNext("A","L","E","X","R","O","D") // the results will not be in order due the asynchronous nature of .flatMap() 
			.expectNextCount(7)
			.verifyComplete();
		
	}
	
	
	/*
	* NOTES: 
	* 	Trade offs of when to use flatMap() and concatMap():
	*		concatMap(): order of the elements are preserved but it will take more time to preserve the order/
	*		flatMap(): lose order of the elements but gain time.
	*/
	
	@Test
	void namesFlux_concatmap() {
		
		var namesFlux = fluxAndMonoGeneratorService.namesFlux_concatmap();
		
		/* 
		 * concatMap() will preserve the order of the elements being processed.
		 * Every time that asynchronous operations need to preserve
		 * the order of the elements that are being processed by the
		 * pipeline, use concatMap().
		 */
		StepVerifier.create(namesFlux)
			.expectNext("A","L","E","X","R","O","D") 
			.verifyComplete();
	}
	
	@Test
	void namesMono_flatmap() {
		
		int strLength = 3;
		
		var monoListStr = fluxAndMonoGeneratorService.namesMono_flatmap(strLength);
		
		StepVerifier.create(monoListStr)
			.expectNext(List.of("A","L","E","X"))
			.verifyComplete();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
