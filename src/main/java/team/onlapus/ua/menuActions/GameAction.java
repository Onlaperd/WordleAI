package team.onlapus.ua.menuActions;

import team.onlapus.ua.CharStateEnum;
import team.onlapus.ua.exceptions.InvalidGuessLengthException;
import team.onlapus.ua.exceptions.OutOfTriesException;
import team.onlapus.ua.services.Wordle;

import java.util.HashMap;
import java.util.Scanner;

public class GameAction implements MenuAction {

    private final Scanner scanner;
    private final int tries;

    public GameAction(Scanner scanner, int tries){
        this.scanner = scanner;
        this.tries = tries;
    }

    public void run() {

        Wordle wordle = new Wordle(tries);
        String answer = wordle.revealAnswer();
        String userGuess;
        HashMap<Integer, CharStateEnum> result = new HashMap<>();
        char[] charredUserGuess;

        gameLoop:
        for (int i = 0; i < tries; i++) {
            while (true) {
                System.out.print("You have " + (tries - i) + " tries left, your guess: ");
                userGuess = scanner.next();
                charredUserGuess = userGuess.toCharArray();

                if (userGuess.equals(answer)) {
                    System.out.println("You guessed right!");
                    break gameLoop;
                }

                if (userGuess.isBlank()) {
                    System.out.println("You typed nothing, missclick?");
                    continue;
                }

                if (!validateWord(userGuess)){
                    System.out.println("invalid word!");
                    continue;
                }

                try {
                    result = wordle.guess(userGuess);
                } catch (InvalidGuessLengthException e) {
                    System.out.println("Invalid guess length, valid word length: " + answer.length());
                    continue;
                } catch (OutOfTriesException e) {
                    System.out.println("GG, the answer was: " + answer);
                }

                for (int j = 0; j < charredUserGuess.length; j++) {
                    System.out.println(charredUserGuess[j] + result.get(j).getSymbol());
                }
                break;
            }
        }
        System.out.println("GG, the answer was: " + answer);
    }

    public String getDescription(){
        return "Play Wordle";
    }

    private static boolean validateWord(String word){
        for (String e : Wordle.getWordset()) if (e.equals(word)) return true;
        return false;
    }


}
