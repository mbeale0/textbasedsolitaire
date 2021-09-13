package com.masonbeale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solitaire {
    public final List<Card> cardDeck = new ArrayList<>();
    private final CardColumn[] cardColumns = new CardColumn[7];
    private List<Card> drawPile = new ArrayList<>();
    private Card[] cardFoundation = new Card[4];
    private int posInDrawPile = -1;
    private boolean hasWon = false;
    boolean hasDoneFirstIndexSize = false;

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
        printTopOfInitialGame();
        printBottomOfInitialGame();
    }



    private void printTopOfInitialGame() {
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
    }
    private void printBottomOfInitialGame() {
        for(int col =0; col < 7; col++) {
            hasDoneFirstIndexSize = false;
            int firstIndexLength = -1;
            int spacesIndex = col;
            int colIndex = col;

            for (int row = 7; row != col; row--) {
                int index = (int) (Math.random() * cardDeck.size());
                if(spacesIndex != 0){
                    printFirstSpaces(spacesIndex);
                }
                if (!hasDoneFirstIndexSize) {
                    firstIndexLength = setInitialDealIndexSize(index);
                }
                printCardPlayingColumns(firstIndexLength, row, index);

                manageDeckToCol(index, cardColumns[colIndex], cardDeck.get(index), cardDeck);
                colIndex++;
            }
            System.out.println();
        }

        createDrawPile();
    }

    private void manageDeckToCol(int index, CardColumn cardColumn, Card card, List<Card> cardDeck) {
        cardColumn.addToCardColumn(card);
        cardDeck.remove(index);
    }

    private void createDrawPile() {
        int cardsAdded = 0;
        int initRemainingCards = cardDeck.size();
        while (cardsAdded < initRemainingCards){
            int index = (int)(Math.random() * cardDeck.size());
            drawPile.add(cardDeck.get(index));
            cardDeck.remove(index);
            cardsAdded++;
        }
    }

    private int setInitialDealIndexSize(int index) {
        int firstIndexLength;
        firstIndexLength = cardDeck.get(index).getCardNumber() < 10 ? 2 : 1;
        hasDoneFirstIndexSize = true;
        return firstIndexLength;
    }

    private void printFirstSpaces(int spacesIndex) {
        while (spacesIndex > 0) {
            System.out.print("    ");
            spacesIndex -= 1;
        }
    }

    private void printCardPlayingColumns(int firstIndexLength, int row, int index) {
        if (row < 7) {
            if (firstIndexLength == 2) {
                System.out.print("  x ");
            } else {
                System.out.print(" x  ");
            }
        } 
        else {
            System.out.print(cardDeck.get(index));
            cardDeck.get(index).setFlipped(true);
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
        System.out.println();
    }

    private void printFoundation(int i) {
        if(i !=3){
            printAllEmptyFoundations(i);
        }
        else{
            printUsedFoundations(i);
        }
    }

    private void printAllEmptyFoundations(int i) {
        if(cardFoundation[i] == null){
            System.out.print("0, ");
        }
        else{
            System.out.print(cardFoundation[i] + ", ");
        }
    }

    private void printUsedFoundations(int i) {
        if(cardFoundation[i] == null){
            System.out.print("0");
        }
        else{
            System.out.print(cardFoundation[i]);
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
        for(int row = 0; row < getMaxColLength(); row++) {
            hasDoneFirstIndexSize = false;
            int firstIndexLength = -1;

            for (int col = 0; col < 7; col++) {
                if (!hasDoneFirstIndexSize) {
                    firstIndexLength = setFirstIndexLengthOfRedraw(row, col);
                }
                if (row >= cardColumns[col].getColSize()) {
                    printSpaces();
                }
                else
                {
                    printCards(row, firstIndexLength, col);
                }
            }
            System.out.println();
        }
    }

    private int setFirstIndexLengthOfRedraw(int row,  int col) {
        if (row < cardColumns[col].getColSize() && cardColumns[col].getCard(row) != null) {
            hasDoneFirstIndexSize = true;
            return cardColumns[col].getCard(row).getCardNumber() < 10 ? 2 : 1;
        }
        return 0;
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
            getCards(cardColumns[fromCol - 1], numCards, cardsToMove);
            addCards(cardColumns[toCol - 1], numCards, cardsToMove);
        }

        redrawAfterPlay();
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
            redrawAfterPlay();
        }
        else if(ableToMoveCardsFromDrawToCol(cardColumns[toCol - 1], toCardToCheck) == true){
            moveCardToCol(cardColumns[toCol - 1]);
        }
        else{
            redrawAfterPlay();
        }

    }

    private void moveCardToCol(CardColumn cardColumn) {
        Card cardToMove = drawPile.get(posInDrawPile);
        cardToMove.setFlipped(true);
        cardColumn.addToCardColumn(cardToMove);
        drawPile.remove(posInDrawPile);
        redrawAfterPlay();
    }

    public void moveCardFromDrawToFoundation(int toFoundation){
        if(ableToMoveFromDrawToFoundation(toFoundation) == true){
            moveCardToTop(toFoundation);
        }
        redrawAfterPlay();
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
