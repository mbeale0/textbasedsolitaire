package com.masonbeale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solitaire {
    private final List<Card> cardDeck = new ArrayList<>();
    private final CardColumn[] cardColumns = new CardColumn[7];
    private List<Card> drawPile = new ArrayList<>();
    private boolean inDrawPile = false;
    private Card[] cardFoundation = new Card[4];
    private int posInDrawPile = -1;

    public Solitaire(){
        CreateInitialDeck();
        Arrays.fill(cardFoundation, null);

        for(int i =0; i < 7; i++){
            cardColumns[i] = new CardColumn();
        }
    }

    private void CreateInitialDeck() {
        for(int i = 1; i <= 13; i++){
            cardDeck.add(new Card(i, Card.Suit.Clubs));
        }
        for(int i = 1; i <= 13; i++){
            cardDeck.add(new Card(i, Card.Suit.Clubs));
        }
        for(int i = 1; i <= 13; i++){
            cardDeck.add(new Card(i, Card.Suit.Diamonds));
        }
        for(int i = 1; i <= 13; i++){
            cardDeck.add(new Card(i, Card.Suit.Hearts));
        }
    }

    public void dealInitialGame(){
        for(int i = 0; i < cardFoundation.length; i++) {
            if(i !=3){
                System.out.print("0, ");
            }
            else{
                System.out.print("0");
            }
        }
        System.out.println("             |O|" );
        System.out.println();
        for(int j =0; j < 7; j++){
            boolean hasDoneFirstIndex = false;
            int firstIndexLength = -1;
            int spacesIndex = j;
            int col = j;

            for(int k =7; k != j; k--){
                int index = (int)(Math.random() * cardDeck.size());
                while (spacesIndex > 0) {
                    System.out.print("    ");
                    spacesIndex -= 1;
                }
                if(!hasDoneFirstIndex){
                    firstIndexLength = cardDeck.get(index).getCardNumber() < 10 ? 2 : 1 ;
                    hasDoneFirstIndex = true;
                }
                if(k < 7 ){
                    if(firstIndexLength == 2){
                        System.out.print("  x ");
                    }
                    else {
                        System.out.print(" x  ");
                    }
                }
                else {
                    System.out.print(cardDeck.get(index));
                    cardDeck.get(index).setFlipped(true);
                }
                cardColumns[col].addToCardColumn(cardDeck.get(index));
                cardDeck.remove(index);
                col++;
            }
            System.out.println();
        }
        int cardsAdded = 0;
        while (cardsAdded < cardDeck.size()){
            int index = (int)(Math.random() * cardDeck.size());
            drawPile.add(cardDeck.get(index));
            cardDeck.remove(index);
            cardsAdded++;
        }
    }

    private void redrawAfterPlay(){
        for(int i = 0; i < cardFoundation.length; i++) {
            if(i !=3){
                if(cardFoundation[i] == null){
                    System.out.print("0, ");
                }
                else{
                    System.out.print(cardFoundation[i] + ", ");
                }
            }
            else{
                if(cardFoundation[i] == null){
                    System.out.print("0");
                }
                else{
                    System.out.print(cardFoundation[i]);
                }
            }
        }
        if(posInDrawPile == -1){
            System.out.println("                |O|" );
        }
        else {
            System.out.println("                " + drawPile.get(posInDrawPile) );
        }
        System.out.println();
        for(int i = 0; i < 7; i++){
            boolean hasDoneFirstIndex = false;
            int firstIndexLength = -1;

            for(int k =0; k < 7 ; k++){
                if(!hasDoneFirstIndex){
                    if(i < cardColumns[k].getColSize()){

                        firstIndexLength = cardColumns[k].getCard(i).getCardNumber() < 10 ? 2 : 1 ;
                        hasDoneFirstIndex = true;
                    }
                }
                if(i >= cardColumns[k].getColSize()){
                    System.out.print("    ");
                }
                else{
                    if(!cardColumns[k].getCard(i).isFlipped()){
                        if(firstIndexLength == 2 ){

                            System.out.print("  x ");
                        }
                        else if(firstIndexLength == 1) {
                            System.out.print(" x  ");
                        }
                    }
                    else {
                        System.out.print(cardColumns[k].getCard(i));
                    }
                }
            }
            System.out.println();
        }
    }

    public void MoveCardToCol(int fromCol, int toCol, int numCards){

        int fromCardToCheck = cardColumns[fromCol-1].getColSize() - numCards;
        int toCardToCheck = cardColumns[toCol-1].getColSize()-1;

        if(ableToMoveCardsToCol(cardColumns[fromCol - 1], cardColumns[toCol - 1], fromCardToCheck, toCardToCheck) == true){

            Card[] cardsToMove;
            cardsToMove = cardColumns[fromCol-1].getStack(numCards);
            for(int i = numCards-1; i >=0; i--){
                cardColumns[toCol-1].addToCardColumn(cardsToMove[i]);
            }
        }

        redrawAfterPlay();
    }

    private boolean ableToMoveCardsToCol(CardColumn fromCol, CardColumn toCol, int fromCardToCheck, int toCardToCheck) {
        boolean areNumbersInOrder = false;
        boolean areDifferentSuits = false;
        boolean isAbleToMove = false;
        if (toCol.getCard(toCardToCheck).getCardNumber() - fromCol.getCard(fromCardToCheck).getCardNumber() == 1){
            areNumbersInOrder = true;
        }
        if(fromCol.getCard(fromCardToCheck).getCardColor() != toCol.getCard(toCardToCheck).getCardColor()){
            areDifferentSuits = true;
        }
        if(areNumbersInOrder == true && areDifferentSuits == true){
            isAbleToMove = true;
        }
        return isAbleToMove;
    }
    private boolean ableToMoveCardsToFoundation(CardColumn cardColumn, int fromCardToCheck, int toFoundationToCheck) {
        boolean areNumbersInOrder = false;
        boolean areSameSuit = false;
        boolean isAbleToMove = false;
        if(cardFoundation[toFoundationToCheck-1] != null){
            if (cardColumn.getCard(fromCardToCheck).getCardNumber() - cardFoundation[toFoundationToCheck-1].getCardNumber() == 1){
                areNumbersInOrder = true;
            }
            if(cardColumn.getCard(fromCardToCheck).getCardColor() == cardFoundation[toFoundationToCheck-1].getCardColor()){
                areSameSuit = true;
            }
            if(areNumbersInOrder == true && areSameSuit == true){
                isAbleToMove = true;
            }
        }
        else{
            isAbleToMove = true;
        }
        return isAbleToMove;
    }
    public void MoveCardToFoundation(int fromCol, int toFoundation){
        int fromCardToCheck = cardColumns[fromCol-1].getColSize() - 1;
        System.out.println("S: " + cardColumns[fromCol-1].getColSize());
        if(ableToMoveCardsToFoundation(cardColumns[fromCol - 1], fromCardToCheck, toFoundation) == true){

            Card[] cardToMove = cardColumns[fromCol-1].getStack(1);
            cardFoundation[toFoundation-1] = cardToMove[0];
        }


        redrawAfterPlay();
    }
    public void useDrawPile(){
        posInDrawPile += 3;
        if(posInDrawPile >= drawPile.size()){
            posInDrawPile = -1;
        }
        redrawAfterPlay();
    }
}
