package org.aviatrip.customerservice.decoder;

import java.io.InputStream;

public interface ErrorHandler {

    Exception handle(InputStream inputStream);

    boolean supports(int statusCode);
}
