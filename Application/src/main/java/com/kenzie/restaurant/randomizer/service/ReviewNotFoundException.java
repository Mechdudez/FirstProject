package com.kenzie.restaurant.randomizer.service;

public class ReviewNotFoundException extends RuntimeException{
    public ReviewNotFoundException() {
    }
    public ReviewNotFoundException(String message) {
        super(message);
    }
}
