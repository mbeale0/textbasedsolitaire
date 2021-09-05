package com.masonbeale;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    Solitaire deck = new Solitaire();
	    //deck.printInitialDeck();
        deck.dealInitialGame();
        Scanner scanner = new Scanner((System.in));

        /*System.out.println("Enter 1: ");
        scanner.nextInt();
        deck.MoveCardToCol(2, 1, 1);*/

        System.out.println("Enter 1: ");
        int col = scanner.nextInt();
        System.out.println("Enter 2: ");
        int found = scanner.nextInt();
        deck.MoveCardToFoundation(col, found);

        System.out.println("Enter 3: ");
        scanner.nextInt();
        deck.useDrawPile();
    }
}
