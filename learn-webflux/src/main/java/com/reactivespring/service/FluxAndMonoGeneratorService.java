package com.reactivespring.service;

import java.util.List;

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
	
}
