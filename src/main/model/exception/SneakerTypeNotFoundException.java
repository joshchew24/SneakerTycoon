package model.exception;

/*
 * Represents exception that occurs when attempting to get the name of a SneakerType
 * by searching the enum with a label that doesn't match any of the types.
 */

public class SneakerTypeNotFoundException extends RuntimeException {
    public SneakerTypeNotFoundException(String message) {
        super(message);
    }
}
