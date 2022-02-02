package ru.bootjava.graduating.restaurantsvoting.error;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}