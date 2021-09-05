package com.masonbeale;

public class Card {
    private final int cardNumber;
    private final Suit cardSuit;
    private boolean isFlipped = false;
    private final Color cardColor;

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

    public Suit getCardSuit() {
        return cardSuit;
    }

    public Color getCardColor() {
        return cardColor;
    }

    @Override
    public String toString() {
        String printedCardSuit;
        if(cardSuit == Suit.Diamonds){
            printedCardSuit = "♦";
        }
        else if(cardSuit == Suit.Clubs){
            printedCardSuit = "♣";
        }
        else if(cardSuit == Suit.Hearts){
            printedCardSuit = "♥";
        }
        else{
            printedCardSuit = "♠";
        }
        return cardNumber + printedCardSuit;
    }
}
