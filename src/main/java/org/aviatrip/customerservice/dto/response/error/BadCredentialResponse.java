package org.aviatrip.customerservice.dto.response.error;

public class BadCredentialResponse extends ErrorResponse {

    private static final String errorMessage = "incorrect email or password";

    public BadCredentialResponse() {
        super(errorMessage, null);
    }
}
