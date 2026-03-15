package team.onlapus.ua;

import team.onlapus.ua.menuActions.GameAction;
import team.onlapus.ua.menuActions.WordleAIInterfaceAction;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int userAnswer;

        appLoop:
        while (true){

            System.out.print("What you wanna do?\n 0. Exit\n1. play Wordle\n2. See AI play wordle\n3. Infinite mode\n> ");
            userAnswer = scanner.nextInt();
            switch (userAnswer){
                case 0: break appLoop;
                case 1:
                    new GameAction(scanner, 5).run();
                    break;
                case 2:
                    new WordleAIInterfaceAction(scanner, 5).run();
                    break;
                case 3:
                    new GameAction(scanner, 999999999).run();
                    break;
                default:
                    System.out.println("invalid input.");
                    break;
            }

        }

    }
}