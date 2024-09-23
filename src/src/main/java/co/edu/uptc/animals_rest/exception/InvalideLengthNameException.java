package co.edu.uptc.animals_rest.exception;

import java.time.LocalDateTime;

public class InvalideLengthNameException extends RuntimeException{
    private String message;
    private LocalDateTime locateDateTime;

    public InvalideLengthNameException (String message) {
        super(message);
        this.message = message;
        this.locateDateTime = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return locateDateTime;
    }

    @Override
    public String toString() {
        return "status: 400\n" +
               "message: \"" + message + "\"\n" +
               "timestamp: \"" + locateDateTime + "\"";
    }


}
