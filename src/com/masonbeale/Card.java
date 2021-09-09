package com.masonbeale;

import java.awt.*;

public class Card {
    private final int cardNumber;
    private final Suit cardSuit;
    private boolean isFlipped = false;
    private final Color cardColor;
    private static final String ANSI_RESET = "\u001b[0m";
    private static final String ANSI_BLACK = "\u001b[30m";
    private static final String ANSI_RED = "\u001b[31m";

    public enum Color{
        red,
        black
    }
    public enum Suit{
        Spades,
        Clubs,
        Hearts,
        Diamonds
    }

    public Card(int cardNumber, Suit cardSuit) {
        this.cardNumber = cardNumber;
        this.cardSuit = cardSuit;
        if(cardSuit == Suit.Clubs || cardSuit == Suit.Spades){
            cardColor = Color.black;
        }
        else {
            cardColor = Color.red;
        }
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public int getCardNumber() {
        return cardNumber;
    }
    
    public Color getCardColor() {
        return cardColor;
    }

    @Override
    public String toString() {
        String printedCardSuit;
        if(cardSuit == Suit.Diamonds){
            printedCardSuit = ANSI_RED + "♦";
        }
        else if(cardSuit == Suit.Clubs){
            printedCardSuit = ANSI_BLACK + "♣";
        }
        else if(cardSuit == Suit.Hearts){
            printedCardSuit = ANSI_RED + "♥";
        }
        else{
            printedCardSuit =  ANSI_BLACK + "♠";
        }
        return cardNumber + printedCardSuit + ANSI_RESET;
    }
}
