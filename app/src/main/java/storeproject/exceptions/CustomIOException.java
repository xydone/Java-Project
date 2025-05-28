package storeproject.exceptions;

import java.io.IOException;

public class CustomIOException extends IOException {
    public CustomIOException(String message) {
        super("Greshka pri input/output: " + message);
    }

    public CustomIOException(String message, Throwable cause) {
        super(message, cause);
    }
}