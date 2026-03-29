package dev.silvercapes.orderservice.exceptions;


public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(final String message){
        super(message);
    }
}
