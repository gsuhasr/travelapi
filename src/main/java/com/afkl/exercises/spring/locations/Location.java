package com.afkl.exercises.spring.locations;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Value;

@JsonInclude(NON_NULL)
@Value
public class Location {

    private String code, name, description;
    private Coordinates coordinates;
    private Location parent;
    private Set<Location> children;
    
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	

    
    
    
}
