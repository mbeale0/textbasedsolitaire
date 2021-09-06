package com.masonbeale;

import java.util.Scanner;

public class Main {
    static Solitaire deck = new Solitaire();
    static boolean isPLaying = true;
    public static void main(String[] args) {
        Scanner optionsScanner = new Scanner((System.in));
        String userInput;
        printOptions();
        deck.dealInitialGame();
        while (isPLaying){
            System.out.println("Enter command: ");
            userInput = optionsScanner.nextLine().toLowerCase();
            //optionsScanner.next();
            System.out.println("UI: " + userInput);
            if(userInput.toLowerCase() == "q"){
                quit();
            }
            else if(userInput.equals("mc")){
                moveToCol();
            }
            else if(userInput.equals("mt")){
                moveToTop();
            }
            else if(userInput.equals("d")){
                useDrawPile();
            }
            else if(userInput.equals("o")){
                printOptions();
            }
            else {
                System.out.println("Invalid output");
            }
        }

    }
    private static void printOptions(){
        System.out.println("Options:\n" +
                "\tq  - Quit\n" +
                "\tmc - Move to col\n" +
                "\tmt - Move to top\n" +
                "\td  - Draw from pile\n" +
                "\to  - Print options");

    }
    private static void moveToCol(){
        Scanner valueScanner = new Scanner(System.in);
        System.out.println("Enter # of col you're moving from");
        int fromCol = valueScanner.nextInt();

        System.out.println("Enter # of col you're moving to");
        int toCol = valueScanner.nextInt();

        System.out.println("Enter # of cards you're moving");
        int numCards = valueScanner.nextInt();
        deck.MoveCardToCol(fromCol, toCol, numCards);
    }
    private static void moveToTop(){
        Scanner valueScanner = new Scanner(System.in);
        System.out.println("Enter # of col you're moving from");
        int fromCol = valueScanner.nextInt();

        System.out.println("Enter top # you're moving to");
        int toFoundation = valueScanner.nextInt();

        deck.MoveCardToFoundation(fromCol, toFoundation);
    }
    private static void useDrawPile(){
        deck.useDrawPile();
    }
    private static void quit(){
        isPLaying = false;
    }
}
