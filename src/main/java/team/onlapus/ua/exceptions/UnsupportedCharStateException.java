package team.onlapus.ua.exceptions;

public class UnsupportedCharStateException extends Exception{

    private final String message;

    public UnsupportedCharStateException(String message){
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
