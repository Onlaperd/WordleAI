package team.onlapus.ua.menuActions;

import team.onlapus.ua.CharStateEnum;
import team.onlapus.ua.exceptions.AIHasNoClueException;
import team.onlapus.ua.exceptions.InvalidGuessLengthException;
import team.onlapus.ua.exceptions.OutOfTriesException;
import team.onlapus.ua.exceptions.UnsupportedCharStateException;
import team.onlapus.ua.services.Wordle;
import team.onlapus.ua.services.WordleAI;

import java.util.HashMap;
import java.util.Scanner;

public class WordleAIInterfaceAction implements MenuAction{

    private final Scanner scanner;
    private final int tries;


    public WordleAIInterfaceAction(Scanner scanner, int tries){
        this.scanner = scanner;
        this.tries = tries;
    }

    @Override
    public void run() {

        Wordle wordle = new Wordle(tries);
        WordleAI wordleAI = new WordleAI();
        String answer = wordle.revealAnswer();
        String aiGuess;
        HashMap<Integer, CharStateEnum> result = new HashMap<>();
        char[] charredAiGuess;
        String prevGuess = null;

        gameLoop:
        for (int i = 0; i < tries; i++) {
            while (true) {
                try {
                    aiGuess = wordleAI.guess(result, prevGuess);
                } catch (AIHasNoClueException | UnsupportedCharStateException e) {
                    System.out.println("AI gives up.");
                    break gameLoop;
                }
                prevGuess = aiGuess;
                System.out.println("AI has " + (tries - i) + " tries left, it's guess: " + aiGuess);
                charredAiGuess = aiGuess.toCharArray();
                System.out.println("Press [Enter to continue]");
                scanner.nextLine();

                if (aiGuess.equals(answer)) {
                    System.out.println("AI guessed right!");
                    break gameLoop;
                }

                if (aiGuess.isBlank()) {
                    System.out.println("it typed nothing, give it a second chance?");
                    continue;
                }

                try {
                    result = wordle.guess(aiGuess);
                } catch (InvalidGuessLengthException e) {
                    System.out.println("Invalid guess length, valid word length: " + answer.length());
                    continue;
                } catch (OutOfTriesException e) {
                    System.out.println("GG, the answer was: " + answer);
                }

                for (int j = 0; j < charredAiGuess.length; j++)
                    System.out.println(charredAiGuess[j] + result.get(j).getSymbol());

                break;
            }
        }
        System.out.println("GG, the answer was: " + answer);
    }

    @Override
    public String getDescription() {
        return "See AI play Wordle";
    }
}
