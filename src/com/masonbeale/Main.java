package com.masonbeale;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    Solitaire deck = new Solitaire();
	    //deck.printInitialDeck();
        deck.dealGame();
        Scanner scanner = new Scanner((System.in));

        System.out.println("Enter 1: ");
        scanner.nextInt();
        deck.MoveCard(2, 1, 1);
    }
}
