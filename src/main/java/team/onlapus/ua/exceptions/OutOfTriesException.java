package team.onlapus.ua.exceptions;

public class OutOfTriesException extends Exception{

    private final String message;

    public OutOfTriesException(String message){
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
