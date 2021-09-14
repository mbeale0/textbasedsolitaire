package com.masonbeale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolitaireGameLogic {
    public final List<Card> cardDeck = new ArrayList<>();
    private final CardColumn[] cardColumns = new CardColumn[7];
    private List<Card> drawPile = new ArrayList<>();
    private Card[] cardFoundation = new Card[4];
    private int posInDrawPile = -1;
    private boolean hasWon = false;
    boolean hasDoneFirstIndexSize = false;
    GameDisplay gameDisplay = new GameDisplay();
    public SolitaireGameLogic(){
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

    public List<Card> getCardDeck() {
        return cardDeck;
    }

    public CardColumn[] getCardColumns() {
        return cardColumns;
    }

    public List<Card> getDrawPile() {
        return drawPile;
    }

    public Card[] getCardFoundation() {
        return cardFoundation;
    }

    public int getPosInDrawPile() {
        return posInDrawPile;
    }

    public void setPosInDrawPile(int posInDrawPile) {
        this.posInDrawPile = posInDrawPile;
    }

    public boolean getHasDoneFirstIndexSize() {
        return hasDoneFirstIndexSize;
    }

    public boolean getHasWon() {
        return hasWon;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }
    public void setHasDoneFirstIndexSize(boolean hasDoneFirstIndexSize) {
        this.hasDoneFirstIndexSize = hasDoneFirstIndexSize;
    }

    public void manageDeckToCol(int index, CardColumn cardColumn, Card card) {
        cardColumn.addToCardColumn(card);
        cardDeck.remove(index);
    }

    public void createDrawPile() {
        int cardsAdded = 0;
        int initRemainingCards = cardDeck.size();
        while (cardsAdded < initRemainingCards){
            int index = (int)(Math.random() * cardDeck.size());
            drawPile.add(cardDeck.get(index));
            cardDeck.remove(index);
            cardsAdded++;
        }
    }

    public void moveCardFromColToCol(int fromCol, int toCol, int numCards){

        int fromCardToCheck = cardColumns[fromCol-1].getColSize() - numCards;
        int toCardToCheck = cardColumns[toCol-1].getColSize()-1;

        if(ableToMoveCardsFromColToCol(cardColumns[fromCol - 1], cardColumns[toCol - 1], fromCardToCheck, toCardToCheck) == true){

            Card[] cardsToMove = new Card[numCards];
            getCards(cardColumns[fromCol - 1], numCards, cardsToMove);
            addCards(cardColumns[toCol - 1], numCards, cardsToMove);
        }

        gameDisplay.redrawAfterPlay();
    }

    private void addCards(CardColumn cardColumn, int numCards, Card[] cardsToMove) {
        for(int i = numCards-1; i >=0; i--){
            cardColumn.addToCardColumn(cardsToMove[i]);
        }
    }

    private void getCards(CardColumn cardColumn, int numCards, Card[] cardsToMove) {
        for(int i = 0; i < numCards; i++){
            cardsToMove[i] = cardColumn.getBottomCard();
        }
    }

    public void moveCardFromDrawToCol(int toCol){

        int toCardToCheck = cardColumns[toCol-1].getColSize()-1;
        if(cardColumns[toCol-1].getColSize() == 0 && drawPile.get(posInDrawPile).getCardNumber() != 13){
            // invalid move
            gameDisplay.redrawAfterPlay();
        }
        else if(ableToMoveCardsFromDrawToCol(cardColumns[toCol - 1], toCardToCheck) == true){
            moveCardToCol(cardColumns[toCol - 1]);
        }
        else{
            gameDisplay.redrawAfterPlay();
        }
    }

    private void moveCardToCol(CardColumn cardColumn) {
        Card cardToMove = drawPile.get(posInDrawPile);
        cardToMove.setFlipped(true);
        cardColumn.addToCardColumn(cardToMove);
        drawPile.remove(posInDrawPile);
        gameDisplay.redrawAfterPlay();
    }

    public void moveCardFromDrawToFoundation(int toFoundation){
        if(ableToMoveFromDrawToFoundation(toFoundation) == true){
            moveCardToTop(toFoundation);
        }
        gameDisplay.redrawAfterPlay();
    }

    private void moveCardToTop(int toFoundation) {
        Card cardToMove = drawPile.get(posInDrawPile);
        cardToMove.setFlipped(true);
        cardFoundation[toFoundation -1] = cardToMove;
        drawPile.remove(posInDrawPile);
    }

    public void MoveCardToFoundation(int fromCol, int toFoundation){
        int fromCardToCheck = cardColumns[fromCol-1].getColSize() - 1;
        if(ableToMoveCardsToFoundation(cardColumns[fromCol - 1], fromCardToCheck, toFoundation) == true){
            Card cardToMove = cardColumns[fromCol-1].getBottomCard();
            cardFoundation[toFoundation-1] = cardToMove;
        }
        gameDisplay. redrawAfterPlay();
    }
    public void useDrawPile(){
        posInDrawPile += 3;
        if(posInDrawPile >= drawPile.size()){
            posInDrawPile = -1;
        }
        gameDisplay.redrawAfterPlay();
    }
    private boolean ableToMoveCardsFromColToCol(CardColumn fromCol, CardColumn toCol, int fromCardToCheck, int toCardToCheck) {
        boolean areNumbersInOrder;
        boolean areDifferentSuits;
        boolean isAbleToMove = false;

        if(fromCol.getCard(fromCardToCheck).getCardNumber() == 13 && toCol.getColSize() == 0){
            return true;
        }
        areNumbersInOrder = checkAreNumbersInOrder(toCol.getCard(toCardToCheck), fromCol.getCard(fromCardToCheck));
        areDifferentSuits = checkAreDifferentSuits(toCol, toCardToCheck, fromCol.getCard(fromCardToCheck));
        if(areNumbersInOrder == true && areDifferentSuits == true){
            isAbleToMove = true;
        }
        else{
            System.out.println("Invalid move");
        }
        return isAbleToMove;
    }

    private boolean ableToMoveCardsFromDrawToCol(CardColumn toCol, int toCardToCheck) {
        boolean areNumbersInOrder;
        boolean areDifferentSuits;
        boolean isAbleToMove = false;

        areNumbersInOrder = checkAreNumbersInOrder(toCol.getCard(toCardToCheck), drawPile.get(posInDrawPile));
        areDifferentSuits = checkAreDifferentSuits(toCol, toCardToCheck, drawPile.get(posInDrawPile));
        if(areNumbersInOrder == true && areDifferentSuits == true){
            isAbleToMove = true;
        }
        else{
            System.out.println("Invalid move");
        }
        return isAbleToMove;
    }

    private boolean ableToMoveCardsToFoundation(CardColumn cardColumn, int fromCardToCheck, int toFoundationToCheck) {
        boolean areNumbersInOrder;
        boolean areSameSuit;
        boolean isAbleToMove = false;
        if(cardFoundation[toFoundationToCheck-1] != null){
            areNumbersInOrder = checkAreNumbersInOrder(cardColumn.getCard(fromCardToCheck), cardFoundation[toFoundationToCheck-1]);
            areSameSuit = checkAreSameSuit(toFoundationToCheck, cardColumn.getCard(fromCardToCheck));

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
        boolean isAbleToMove = false;
        isAbleToMove = checkAbleToMove(toFoundationToCheck, isAbleToMove);
        return isAbleToMove;
    }

    private boolean checkAbleToMove(int toFoundationToCheck, boolean isAbleToMove) {
        boolean areSameSuit;
        boolean areNumbersInOrder;
        if(cardFoundation[toFoundationToCheck -1] != null){
            areNumbersInOrder = checkAreNumbersInOrder(drawPile.get(posInDrawPile), cardFoundation[toFoundationToCheck -1]);
            areSameSuit = checkAreSameSuit(toFoundationToCheck,  drawPile.get(posInDrawPile));

            if(areNumbersInOrder == true && areSameSuit == true){
                return true;
            }
            else{
                System.out.println("Invalid move");
            }
        }
        else if(drawPile.get(posInDrawPile).getCardNumber() == 1){
            return true;
        }
        else{
            System.out.println("Invalid move");
        }
        return false;
    }

    private boolean checkAreSameSuit(int toFoundationToCheck, Card card) {
        if (card.getCardColor() == cardFoundation[toFoundationToCheck - 1].getCardColor()) {
            return true;
        }
        return false;
    }
    private boolean checkAreDifferentSuits(CardColumn toCol, int toCardToCheck, Card card) {
        if (card.getCardColor() != toCol.getCard(toCardToCheck).getCardColor()) {
            return true;
        }
        return false;
    }
    private boolean checkAreNumbersInOrder(Card card, Card card2) {
        if (card.getCardNumber() - card2.getCardNumber() == 1) {
            return true;
        }
        return false;
    }
    public int getMaxColLength(){
        int maxColLength = 0;

        for(int i = 0; i < cardColumns.length -1; i ++){
            if(cardColumns[i].getColSize() < cardColumns[i+1].getColSize() ){
                maxColLength = cardColumns[i+1].getColSize();
            }
        }
        return maxColLength;
    }
    public void checkWin(){
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
