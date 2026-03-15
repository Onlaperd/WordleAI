package team.onlapus.ua.exceptions;

public class AIHasNoClueException extends Exception{

    private final String message;

    public AIHasNoClueException(String message){
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }

}
