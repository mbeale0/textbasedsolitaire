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
    private int test;
    boolean hasDoneFirstIndexSize = false;
    GameDisplay gameDisplay = new GameDisplay();

    public SolitaireGameLogic(){
        CreateInitialDeck();
        Arrays.fill(this.cardFoundation, null);

        for(int i =0; i < 7; i++){
            this.cardColumns[i] = new CardColumn();
        }
    }

    private void CreateInitialDeck() {
        for(int i = 1; i <= 13; i++){
            this.cardDeck.add(new Card(i, Card.Suit.Spades));
            this.cardDeck.add(new Card(i, Card.Suit.Clubs));
            this.cardDeck.add(new Card(i, Card.Suit.Diamonds));
            this.cardDeck.add(new Card(i, Card.Suit.Hearts));
        }
    }

    public List<Card> getCardDeck() {
        return this.cardDeck;
    }
    public List<Card> getDrawPile() {
        return this.drawPile;
    }
    public CardColumn[] getCardColumns() {
        return this.cardColumns;
    }
    public Card[] getCardFoundation() {
        return this.cardFoundation;
    }
    public int getPosInDrawPile() { return this.posInDrawPile; }
    public boolean getHasDoneFirstIndexSize() { return this.hasDoneFirstIndexSize; }
    public boolean getHasWon() { return this.hasWon; }
    public void setHasWon(boolean hasWon) { this.hasWon = hasWon; }
    public void setPosInDrawPile(int posInDrawPile) {
        this.posInDrawPile = posInDrawPile;
    }
    public void setHasDoneFirstIndexSize(boolean hasDoneFirstIndexSize)
    {
        this.hasDoneFirstIndexSize = hasDoneFirstIndexSize;
    }

    public void manageDeal(int index, CardColumn cardColumn, Card card)
    {
        cardColumn.addToCardColumn(card);
        this.cardDeck.remove(index);
    }

    public void createDrawPile()
    {
        int cardsAdded = 0;
        int initRemainingCards = this.cardDeck.size();
        while (cardsAdded < initRemainingCards){
            int index = (int)(Math.random() * cardDeck.size());
            this.drawPile.add(cardDeck.get(index));
            this.cardDeck.remove(index);
            cardsAdded++;
        }
    }

    public void moveCardFromColToCol(int fromCol, int toCol, int numCards)
    {
        int fromCardToCheck = this.cardColumns[fromCol-1].getColSize() - numCards;
        int toCardToCheck = this.cardColumns[toCol-1].getColSize()-1;

        if(ableToMoveCardsFromColToCol(this.cardColumns[fromCol - 1], this.cardColumns[toCol - 1], fromCardToCheck, toCardToCheck) == true){
            Card[] cardsToMove = new Card[numCards];
            getCards(this.cardColumns[fromCol - 1], numCards, cardsToMove);
            addCards(this.cardColumns[toCol - 1], numCards, cardsToMove);
        }

        this.gameDisplay.redrawAfterPlay();
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

        int toCardToCheck = this.cardColumns[toCol-1].getColSize()-1;
        if(this.cardColumns[toCol-1].getColSize() == 0 && this.drawPile.get(this.posInDrawPile).getCardNumber() != 13){
            System.out.println("Invalid move");
            this.gameDisplay.redrawAfterPlay();
        }
        else if(ableToMoveCardsFromDrawToCol(this.cardColumns[toCol - 1], toCardToCheck) == true){
            moveCardToCol(this.cardColumns[toCol - 1]);
        }
        else{
            this.gameDisplay.redrawAfterPlay();
        }
    }

    public void MoveCardToFoundation(int fromCol, int toFoundation){
        int fromCardToCheck = this.cardColumns[fromCol-1].getColSize() - 1;
        if(ableToMoveCardsToFoundation(this.cardColumns[fromCol - 1], fromCardToCheck, toFoundation) == true){
            Card cardToMove = cardColumns[fromCol-1].getBottomCard();
            this.cardFoundation[toFoundation-1] = cardToMove;
        }
        this.gameDisplay. redrawAfterPlay();
    }
    public void useDrawPile(){
        this.posInDrawPile += 3;
        if(this.posInDrawPile >= this.drawPile.size()){

            this.posInDrawPile = -1;
        }
        this.gameDisplay.redrawAfterPlay();
    }

    private void moveCardToCol(CardColumn cardColumn) {
        Card cardToMove = this.drawPile.get(this.posInDrawPile);
        cardToMove.setFlipped(true);
        cardColumn.addToCardColumn(cardToMove);
        this.drawPile.remove(this.posInDrawPile);
        this.gameDisplay.redrawAfterPlay();
    }

    public void moveCardFromDrawToFoundation(int toFoundation){
        if(ableToMoveFromDrawToFoundation(toFoundation) == true){
            moveCardToTop(toFoundation);
        }
        this.gameDisplay.redrawAfterPlay();
    }

    private void moveCardToTop(int toFoundation) {
        Card cardToMove = this.drawPile.get(this.posInDrawPile);
        cardToMove.setFlipped(true);
        this.cardFoundation[toFoundation -1] = cardToMove;
        this.drawPile.remove(this.posInDrawPile);
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

        areNumbersInOrder = checkAreNumbersInOrder(toCol.getCard(toCardToCheck), this.drawPile.get(this.posInDrawPile));
        areDifferentSuits = checkAreDifferentSuits(toCol, toCardToCheck, this.drawPile.get(this.posInDrawPile));
        if(toCol.getColSize() == 0 && this.drawPile.get(this.posInDrawPile).getCardNumber() == 13){
            isAbleToMove = true;
        }
        else if(areNumbersInOrder == true && areDifferentSuits == true){
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
        if(this.cardFoundation[toFoundationToCheck-1] != null){
            areNumbersInOrder = checkAreNumbersInOrder(cardColumn.getCard(fromCardToCheck), this.cardFoundation[toFoundationToCheck-1]);
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
        if(this.cardFoundation[toFoundationToCheck -1] != null){
            areNumbersInOrder = checkAreNumbersInOrder(this.drawPile.get(this.posInDrawPile), this.cardFoundation[toFoundationToCheck -1]);
            areSameSuit = checkAreSameSuit(toFoundationToCheck, this.drawPile.get(this.posInDrawPile));

            if(areNumbersInOrder == true && areSameSuit == true){
                return true;
            }
            else{
                System.out.println("Invalid move");
            }
        }
        else if(this.drawPile.get(this.posInDrawPile).getCardNumber() == 1){
            return true;
        }
        else{
            System.out.println("Invalid move");
        }
        return false;
    }

    private boolean checkAreSameSuit(int toFoundationToCheck, Card card) {
        if (card.getCardColor() == this.cardFoundation[toFoundationToCheck - 1].getCardColor()) {
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

        for(int i = 0; i < this.cardColumns.length -1; i ++){
            if(this.cardColumns[i].getColSize() < this.cardColumns[i+1].getColSize() ){
                maxColLength = this.cardColumns[i+1].getColSize();
            }
        }
        return maxColLength;
    }
    public void checkWin(){
        int foundationSum = 0;
        for(int i = 0; i < 4; i++){
            if(this.cardFoundation[i] != null){
                foundationSum += this.cardFoundation[i].getCardNumber();
            }
        }
        if(foundationSum == 52){
            this.hasWon = true;
        }
    }
}
