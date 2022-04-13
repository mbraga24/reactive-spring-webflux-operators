package com.reactivespring.service;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxAndMonoGeneratorService {

	// namesFlux functions will return a flux of type String
	public Flux<String> namesFlux() {
		
		// fromIterable - takes in a collection, creates and returns the flux
		return Flux.fromIterable(List.of("Alex", "Joye", "Mark"))
				.log(); // flux will be coming from a database or remote service call		
	}
	
	public Mono<String> nameMono() {
		return Mono.just("John")
				.log();
	}
	
	// executable class
	public static void main(String[] args) {
		
		FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService(); // create instance of FluxAndMonoGeneratorService
		
        // use the instance of FluxAndMonoGeneratorService and call the namesFlux function
		fluxAndMonoGeneratorService.namesFlux() // namesFlux will make access to flux possible
		                           .subscribe( name -> { 
		                        	   // the only way to access flux is by calling the subscribe() function. 
		                        	   // When a call is made to the subscribe function, accessing the elements are possible. 
		                        	   // All the elements in flux will be sent as a form of Stream.
		            	System.out.println("Flux => Name is : " + name);
		            });
		            
		fluxAndMonoGeneratorService.nameMono()
		                           .subscribe( name -> {
			System.out.println("Mono => Name is : " + name);
		});
		
	}
	
//	******************* Map() & filter() *******************
	
	public Flux<String> namesFlux_map() {
		
		// fromIterable - takes in a collection, creates and returns the flux
		return Flux.fromIterable(List.of("Alex", "Joye", "Mark"))
				.map(String::toUpperCase)
             // .map(str -> str.toUpperCase())
				.log();
	}
	
	// Reactive Strings are immutable
	// The map() operator will return a new Flux as a response.
	public Flux<String> namesFlux_immutability() {
		
		var namesFlux = Flux.fromIterable(List.of("Alex", "Joye", "Mark"));
		namesFlux.map(String::toUpperCase); // the values won't change to uppercase words
		return namesFlux;
				
	}
	
	public Flux<String> namesFlux_filter(int strLength) {
		
		return Flux.fromIterable(List.of("Alex", "Joye", "Mark", "Tom"))
				.map(String::toUpperCase)
				.filter(str -> str.length() > strLength)
				.map(str -> str.length() + ": " + str)
				.log();	
	}
	
//	******************* flatMap() *******************
	
	public Flux<String> namesFlux_flatmap() {
	
		return Flux.fromIterable(List.of("Alex", "Rod"))
				.map(String::toUpperCase)
				.flatMap(str -> splitString(str)) // A,L,E,X,R,O,D
				.log();
	}
	
	public Flux<String> namesFlux_flatmap_async() {
		
		return Flux.fromIterable(List.of("Alex", "Rod"))
				.map(String::toUpperCase)
				.flatMap(str -> splitString_withDelay(str))
				.log();
				
	}
	
//	******************* concatMap() *******************
	
	public Flux<String> namesFlux_concatmap() {
		
		return Flux.fromIterable(List.of("Alex", "Rod"))
				.map(String::toUpperCase)
				.concatMap(str -> splitString_withDelay(str))
				.log();
	}
	
	
//	******************* flatMap() Mono/Flux *******************
	
	/* 
	 * Use flatMap() operator when the transformation returns a Mono.
	 * When the transformation involves making a REST API call
	 * or any kind of functionality that can be done asynchronously.
	*/
	
	public Mono<List<String>> namesMono_flatmap(int strLength) {
		return Mono.just("alex")
				.map(String::toUpperCase)
				.filter(str -> str.length() > strLength)
				.flatMap(this::splitStringMono) // Mono<List of A, L, E, X>
				.log(); 
	}
	
	/* 
	 * Use flatMapMany() opeartor when a transformation returns a Flux
	 * in the Mono pipeline. 
	*/
	
	public Flux<String> namesMono_flatMapMany(int strLength) {
		
		return Mono.just("alex")
				.map(String::toUpperCase)
				.filter(str -> str.length() > strLength)
				.flatMapMany(this::splitString)
				.log();
	}
	
//	**************** Function Functional Interface **************** 
	
	/*
	 * Use case: When a similar functionality is being used across the project, 
	 * Function functional interface can be used to minimize repetition simply 
	 * by passing it as a variable. This concept is knows as ** BEHAVIOR PARAMETERIZATION **.
	 */
	
	public Flux<String> namesFlux_transform(int strLength) {
		
		Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
				.filter(str -> str.length() > strLength);
		
		return Flux.fromIterable(List.of("Alex", "Rod"))
				.transform(filterMap)
				.flatMap(this::splitString) 
				.log();	
	}
	
//	============================================================================
//								PRIVATE METHODS
//  ============================================================================
	
	private Mono<List<String>> splitStringMono(String word) {
		var charArr = word.split(""); 
		var charList = List.of(charArr); // A, L, E, X		
		return Mono.just(charList);
	}
	
	// Flux(A,L,E,X)
	// Flux(R,O,D)
	public Flux<String> splitString(String name) {
		var charArray = name.split("");
		return Flux.fromArray(charArray);
	}
	
	public Flux<String> splitString_withDelay(String name) {
		var charArray = name.split("");
		var delay = new Random().nextInt(2000);
		return Flux.fromArray(charArray)
				.delayElements(Duration.ofMillis(delay));
	}
	
}
