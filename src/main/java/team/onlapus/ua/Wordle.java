package team.onlapus.ua;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Random;

public class Wordle {
    private static final MiniLogger logger = new MiniLogger();
    private static final String[] wordset;

    static {
        try {
            wordset = Files.readString(Path.of("words.txt")).split("\n");
        } catch (IOException e) {
            logger.error("IO exception:\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String word;
    private int tries;


    public Wordle(){
        Random random = new Random();
        this.tries = 5;
        this.word = wordset[random.nextInt(wordset.length)];
    }

    public Wordle(int tries){
        Random random = new Random();
        this.tries = tries;
        this.word = wordset[random.nextInt(wordset.length)];
    }


    public HashMap<Integer, CharStateEnum> guess(String guess) throws InvalidGuessLengthException {

        if (tries < 0){
            logger.warn("You ran out of tries!");
            return null;
        }

        char[] charredWord = word.toCharArray();
        char[] charredGuess = guess.toCharArray();

        if (charredWord.length != charredGuess.length) throw new InvalidGuessLengthException(
                    "Invalid guess word length\n given: " +
                            charredGuess.length +
                            "\nexpected: " +
                            charredWord.length);



        HashMap<Integer, CharStateEnum> result = new HashMap<>();
        HashMap<Character, Integer> charRepetitions = new HashMap<>();

        for (char c : word.toCharArray()) {
            charRepetitions.put(c, charRepetitions.getOrDefault(c, 0) + 1);
        }



        for (int i = 0; i < word.toCharArray().length; i++) {
            if (charredWord[i] == charredGuess[i]){
                result.put(i, CharStateEnum.GREEN);
            } else if (word.indexOf(charredGuess[i]) != -1) {
                if (charRepetitions.get(charredGuess[i]) == 0) {
                    result.put(i, CharStateEnum.GRAY);
                    continue;
                }
                result.put(i, CharStateEnum.YELLOW);
                charRepetitions.put(charredGuess[i], charRepetitions.get(charredGuess[i]) - 1);
            } else {
                result.put(i, CharStateEnum.GRAY);
            }
        }

        tries--;
        return result;
    }

    public String revealAnswer(){
        return word;
    }



}
