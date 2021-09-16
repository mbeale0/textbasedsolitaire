package com.masonbeale;

import java.util.Scanner;

public class Main {
    static GameDisplay display = new GameDisplay();
    static boolean isPlaying = true;
    public static void main(String[] args) {
        Scanner optionsScanner = new Scanner((System.in));
        printOptions();
        display.dealInitialGame();
        while (isPlaying){
            if(!display.getGameLogic().getHasWon()){
                playTurn(optionsScanner);
            }
            else {
                checkPlayAgain(optionsScanner);
            }
        }
    }

    private static void playTurn(Scanner optionsScanner) {
        String userInput;
        System.out.println("Enter command: ");
        userInput = optionsScanner.nextLine().toLowerCase();
        if(userInput.toLowerCase() == "q"){
            quit();
        }
        else if(userInput.equals("mc")){
            moveToCol();
        }
        else if(userInput.equals("mt")){
            moveToTop();
        }
        else if(userInput.equals("mdc")){
            moveDrawToCol();
        }
        else if(userInput.equals("mdt")){
            moveDrawToTop();
        }
        else if(userInput.equals("d")){
            useDrawPile();
        }
        else if(userInput.equals("o")){
            printOptions();
        }
        else if(userInput.equals("q")){
            quit();
        }
        else {
            System.out.println("Invalid input");
        }
    }

    private static void checkPlayAgain(Scanner optionsScanner) {
        System.out.println("Play Again? y/n");
        String playAgain = optionsScanner.nextLine().toLowerCase();
        if(playAgain == "y"){
            display.getGameLogic().setHasWon(false);
        }
        else if(playAgain == "n") {
            isPlaying = false;
        }
        else {
            System.out.println("Invalid input");
        }
    }

    private static void printOptions(){
        System.out.println("Options:\n" +
                "\tq   - Quit\n" +
                "\tmc  - Move to col\n" +
                "\tmt  - Move to top\n" +
                "\td   - Draw from pile\n" +
                "\tmdc - Move draw to col\n" +
                "\tmdt - Move draw to top\n" +
                "\to   - Print options");

    }
    private static void moveToCol(){
        Scanner valueScanner = new Scanner(System.in);
        System.out.println("Enter # of col you're moving from");
        int fromCol = valueScanner.nextInt();

        System.out.println("Enter # of col you're moving to");
        int toCol = valueScanner.nextInt();

        System.out.println("Enter # of cards you're moving");
        int numCards = valueScanner.nextInt();
        display.getGameLogic().moveCardFromColToCol(fromCol, toCol, numCards);
    }
    private static void moveToTop(){
        Scanner valueScanner = new Scanner(System.in);
        System.out.println("Enter # of col you're moving from");
        int fromCol = valueScanner.nextInt();

        System.out.println("Enter top # you're moving to");
        int toFoundation = valueScanner.nextInt();

        display.getGameLogic().MoveCardToFoundation(fromCol, toFoundation);
    }
    private static void useDrawPile(){
        display.getGameLogic().useDrawPile();
    }
    private static void moveDrawToCol(){
        Scanner valueScanner = new Scanner(System.in);
        System.out.println("Enter # of col you're moving to");
        int toCol = valueScanner.nextInt();
        display.getGameLogic().moveCardFromDrawToCol(toCol);
    }
    private static void moveDrawToTop(){
        Scanner valueScanner = new Scanner(System.in);
        System.out.println("Enter # of top you're moving to");
        int toFound = valueScanner.nextInt();
        display.getGameLogic().moveCardFromDrawToFoundation(toFound);
    }
    private static void quit(){
        isPlaying = false;
    }
}
