package com.masonbeale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class Solitaire {
    public final List<Card> cardDeck = new ArrayList<>();
    private final CardColumn[] cardColumns = new CardColumn[7];
    private List<Card> drawPile = new ArrayList<>();
    private Card[] cardFoundation = new Card[4];
    private int posInDrawPile = -1;
    private boolean hasWon = false;

    public Solitaire(){
        CreateInitialDeck();
        Arrays.fill(cardFoundation, null);

        for(int i =0; i < 7; i++){
            cardColumns[i] = new CardColumn();
        }
    }

    private void CreateInitialDeck() {
        for(int i = 1; i <= 13; i++){
            cardDeck.add(new Card(i, Card.Suit.Spades));
            cardDeck.add(new Card(i, Card.Suit.Clubs));
            cardDeck.add(new Card(i, Card.Suit.Diamonds));
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
        for(int j =0; j < 7; j++) {
            boolean hasDoneFirstIndex = false;
            int firstIndexLength = -1;
            int spacesIndex = j;
            int col = j;

            for (int k = 7; k != j; k--) {
                int index = (int) (Math.random() * cardDeck.size());
                while (spacesIndex > 0) {
                    System.out.print("    ");
                    spacesIndex -= 1;
                }
                if (!hasDoneFirstIndex) {
                    firstIndexLength = cardDeck.get(index).getCardNumber() < 10 ? 2 : 1;
                    hasDoneFirstIndex = true;
                }
                if (k < 7) {
                    if (firstIndexLength == 2) {
                        System.out.print("  x ");
                    } else {
                        System.out.print(" x  ");
                    }
                } else {
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
        int initRemainingCards = cardDeck.size();
        while (cardsAdded < initRemainingCards){
            int index = (int)(Math.random() * cardDeck.size());
            drawPile.add(cardDeck.get(index));
            cardDeck.remove(index);
            cardsAdded++;
        }
    }

    public boolean isHasWon() {
        return hasWon;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    private void redrawAfterPlay(){
        checkWin();
        if(!hasWon){
            printTopOfGameField();
            System.out.println();
            printBottomOfGameField();
        }
        else
        {
            System.out.println("YOU WON! CONGRATS!!!");
        }
    }

    private void printTopOfGameField() {
        for(int i = 0; i < cardFoundation.length; i++) {
            printFoundation(i);
        }
        printDrawPile();
    }
    private void printFoundation(int i) {
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
    private void printDrawPile() {
        if(posInDrawPile >= drawPile.size()){
            posInDrawPile = -1;
        }
        if(posInDrawPile == -1){
            System.out.println("                |O|" );
        }
        else {
            System.out.println("                " + drawPile.get(posInDrawPile) );
        }
    }
    private void printBottomOfGameField() {
        for(int i = 0; i < getMaxColLength(); i++) {
            boolean hasDoneFirstIndex = false;
            int firstIndexLength = -1;

            for (int k = 0; k < 7; k++) {
                if (!hasDoneFirstIndex) {
                    if (i < cardColumns[k].getColSize() && cardColumns[k].getCard(i) != null) {
                        firstIndexLength = cardColumns[k].getCard(i).getCardNumber() < 10 ? 2 : 1;
                        hasDoneFirstIndex = true;
                    }
                }
                if (i >= cardColumns[k].getColSize()) {
                    printSpaces();
                }
                else
                {
                    printCards(i, firstIndexLength, k);
                }
            }
            System.out.println();
        }
    }

    private void printSpaces() {
        System.out.print("    ");
    }

    private void printCards(int i, int firstIndexLength, int k) {
        if(cardColumns[k].getCard(i) != null){
            if (!cardColumns[k].getCard(i).isFlipped()) {
                if (firstIndexLength == 2) {

                    System.out.print("  x ");
                } else if (firstIndexLength == 1) {
                    System.out.print(" x  ");
                }
            }
            else {
                System.out.print(" " + cardColumns[k].getCard(i) + " ");
            }
        }
    }

    public void moveCardFromColToCol(int fromCol, int toCol, int numCards){

        int fromCardToCheck = cardColumns[fromCol-1].getColSize() - numCards;
        int toCardToCheck = cardColumns[toCol-1].getColSize()-1;

        if(ableToMoveCardsFromColToCol(cardColumns[fromCol - 1], cardColumns[toCol - 1], fromCardToCheck, toCardToCheck) == true){

            Card[] cardsToMove = new Card[numCards];
            for(int i = 0; i < numCards; i++){
                cardsToMove[i] = cardColumns[fromCol-1].getBottomCard();
            }

            for(int i = numCards-1; i >=0; i--){
                cardColumns[toCol-1].addToCardColumn(cardsToMove[i]);
            }
        }

        redrawAfterPlay();
    }
    public void moveCardFromDrawToCol(int toCol){

        int toCardToCheck = cardColumns[toCol-1].getColSize()-1;
        if(cardColumns[toCol-1].getColSize() == 0 && drawPile.get(posInDrawPile).getCardNumber() == 13){
            Card cardToMove = drawPile.get(posInDrawPile);
            cardToMove.setFlipped(true);
            cardColumns[toCol-1].addToCardColumn(cardToMove);
            drawPile.remove(posInDrawPile);
        }
        else if(ableToMoveCardsFromDrawToCol(cardColumns[toCol - 1], toCardToCheck) == true){
            Card cardToMove = drawPile.get(posInDrawPile);
            cardToMove.setFlipped(true);
            cardColumns[toCol-1].addToCardColumn(cardToMove);
            drawPile.remove(posInDrawPile);
        }
        redrawAfterPlay();
    }
    public void moveCardFromDrawToFoundation(int toFoundation){
        if(ableToMoveFromDrawToFoundation(toFoundation) == true){
            Card cardToMove = drawPile.get(posInDrawPile);
            cardToMove.setFlipped(true);
            cardFoundation[toFoundation-1] = cardToMove;
            drawPile.remove(posInDrawPile);
        }
        redrawAfterPlay();
    }
    public void MoveCardToFoundation(int fromCol, int toFoundation){
        int fromCardToCheck = cardColumns[fromCol-1].getColSize() - 1;
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
    private boolean ableToMoveCardsFromColToCol(CardColumn fromCol, CardColumn toCol, int fromCardToCheck, int toCardToCheck) {
        boolean areNumbersInOrder = false;
        boolean areDifferentSuits = false;
        boolean isAbleToMove = false;

        if(fromCol.getCard(fromCardToCheck).getCardNumber() == 13 && toCol.getColSize() == 0){
            return true;
        }
        if (toCol.getCard(toCardToCheck).getCardNumber() - fromCol.getCard(fromCardToCheck).getCardNumber() == 1){
            areNumbersInOrder = true;
        }
        if(fromCol.getCard(fromCardToCheck).getCardColor() != toCol.getCard(toCardToCheck).getCardColor()){
            areDifferentSuits = true;
        }
        if(areNumbersInOrder == true && areDifferentSuits == true){
            isAbleToMove = true;
        }
        else{
            System.out.println("Invalid move");
        }
        return isAbleToMove;
    }
    private boolean ableToMoveCardsFromDrawToCol(CardColumn toCol, int toCardToCheck) {
        boolean areNumbersInOrder = false;
        boolean areDifferentSuits = false;
        boolean isAbleToMove = false;

        if (toCol.getCard(toCardToCheck).getCardNumber() - drawPile.get(posInDrawPile).getCardNumber() == 1){
            areNumbersInOrder = true;
        }
        if(drawPile.get(posInDrawPile).getCardColor() != toCol.getCard(toCardToCheck).getCardColor()){
            areDifferentSuits = true;
        }
        if(areNumbersInOrder == true && areDifferentSuits == true){
            isAbleToMove = true;
        }
        else{
            System.out.println("Invalid move");
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
        else if(cardColumn.getCard(fromCardToCheck).getCardNumber() == 1){
            isAbleToMove = true;
        }
        else{
            System.out.println("Invalid move");
        }
        return isAbleToMove;
    }
    private boolean ableToMoveFromDrawToFoundation(int toFoundationToCheck) {
        boolean areNumbersInOrder = false;
        boolean areSameSuit = false;
        boolean isAbleToMove = false;
        if(cardFoundation[toFoundationToCheck-1] != null){
            if (drawPile.get(posInDrawPile).getCardNumber() - cardFoundation[toFoundationToCheck-1].getCardNumber() == 1){
                areNumbersInOrder = true;
            }
            if(drawPile.get(posInDrawPile).getCardColor() == cardFoundation[toFoundationToCheck-1].getCardColor()){
                areSameSuit = true;
            }
            if(areNumbersInOrder == true && areSameSuit == true){
                isAbleToMove = true;
            }
            else{
                System.out.println("Invalid move");
            }
        }
        else if(drawPile.get(posInDrawPile).getCardNumber() == 1){
            isAbleToMove = true;
        }
        else{
            System.out.println("Invalid move");
        }
        return isAbleToMove;
    }
    private int getMaxColLength(){
        int maxColLength = 0;

        for(int i = 0; i < cardColumns.length -1; i ++){
            if(cardColumns[i].getColSize() < cardColumns[i+1].getColSize() ){
                maxColLength = cardColumns[i+1].getColSize();
            }
        }
        return maxColLength;
    }
    private void checkWin(){
        int foundationSum = 0;
        for(int i = 0; i < 4; i++){
            if(cardFoundation[i] != null){
                foundationSum += cardFoundation[i].getCardNumber();
            }
        }
        if(foundationSum == 52){
            hasWon = true;
        }

    }
}
