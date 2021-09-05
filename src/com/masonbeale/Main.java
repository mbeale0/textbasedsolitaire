package com.masonbeale;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    Solitaire deck = new Solitaire();
	    //deck.printInitialDeck();
        deck.dealInitialGame();
        Scanner scanner = new Scanner((System.in));

        System.out.println("Enter 1: ");
        scanner.nextInt();
        deck.MoveCardToCol(2, 1, 1);

        System.out.println("Enter 2: ");
        scanner.nextInt();
        deck.MoveCardToFoundation(3, 1);

        System.out.println("Enter 3: ");
        scanner.nextInt();
        deck.useDrawPile();
    }
}
