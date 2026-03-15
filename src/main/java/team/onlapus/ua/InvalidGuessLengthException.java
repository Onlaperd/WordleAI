package team.onlapus.ua;

public class InvalidGuessLengthException extends Exception{

    private final String message;

    public InvalidGuessLengthException(String message){
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
