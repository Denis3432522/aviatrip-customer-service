package org.aviatrip.customerservice.exception;

public class FatalKafkaException extends RuntimeException{

    public FatalKafkaException() {}

    public FatalKafkaException(String message) {
        super(message);
    }
}
