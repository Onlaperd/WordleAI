package team.onlapus.ua.services;

import team.onlapus.ua.CharStateEnum;
import team.onlapus.ua.exceptions.AIHasNoClueException;
import team.onlapus.ua.exceptions.UnsupportedCharStateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


// consider it being length-flexible, just a reminder for later
public class WordleAI {

    static Random random = new Random();

    private static final String[] bestOpeners = new String[]{
            "slate",
            "crane",
            "adieu",
            "trace",
            "slant",
            "carte",
            "raise",
            "crate",
            "stare",
            "later",
            "irate"
    };


    private final ArrayList<Character> grayChars = new ArrayList<>();
    private final HashMap<Character, ArrayList<Integer>> yellowChars = new HashMap<>();
    private final HashMap<Integer, Character> greenChars = new HashMap<>();

    public WordleAI() {
    }

    public String guess(HashMap<Integer, CharStateEnum> input, String prevGuess) throws UnsupportedCharStateException, AIHasNoClueException {
        if (input == null || prevGuess == null) return bestOpeners[random.nextInt(bestOpeners.length)];
        parseInput(input, prevGuess.toCharArray());

        return makeNextGuess();
    }

    private void parseInput(HashMap<Integer, CharStateEnum> input, char[] prevGuess) throws UnsupportedCharStateException {

        for (int elem : input.keySet()) {
            if (input.get(elem) == CharStateEnum.GREEN) greenChars.put(elem, prevGuess[elem]);
            else if (input.get(elem) == CharStateEnum.GRAY) grayChars.add(prevGuess[elem]);
            else if (input.get(elem) == CharStateEnum.YELLOW) {
                if (!yellowChars.containsKey(prevGuess[elem]))
                    yellowChars.put(prevGuess[elem], new ArrayList<>());
                yellowChars.get(prevGuess[elem]).add(elem);
            } else throw new UnsupportedCharStateException("not green, yellow or gray, then what?");
        }
    }

    private String makeNextGuess() throws UnsupportedCharStateException, AIHasNoClueException {
        char[] charredElem;
        ArrayList<String> possibleResults = new ArrayList<>();

        if (greenChars.isEmpty() && grayChars.isEmpty() && yellowChars.isEmpty()) return guess(null, null);

        wordLoop:
        for (String elem : Wordle.getWordset()) {
            charredElem = elem.toCharArray();

            if (!greenChars.isEmpty()) for (int index : greenChars.keySet()) {
                    if (index >= charredElem.length) continue wordLoop;
                    if (charredElem[index] != greenChars.get(index)) continue wordLoop;
                }

            for (char character : grayChars)
                if (elem.indexOf(character) != -1) continue wordLoop;

            for (char character : yellowChars.keySet()) {
                if (elem.indexOf(character) == -1) continue wordLoop;
                for (int badIndex : yellowChars.get(character))
                    if (elem.charAt(badIndex) == character) continue wordLoop;

            }
            possibleResults.add(elem);
        }

        if (possibleResults.isEmpty()) {
            throw new AIHasNoClueException("I have no idea what the word could be.");
        }

        return possibleResults.get(random.nextInt(possibleResults.size()));
    }

    public int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }
}
