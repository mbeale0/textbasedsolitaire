package com.masonbeale;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Solitaire {
    List<Card> cardDeck = new ArrayList<Card>();
    List<Card> dealtCard = new ArrayList<Card>();
    List<CardColumn> cardColumns = new ArrayList<CardColumn>();
    List<Card> drawPile = new ArrayList<Card>();
    private boolean inDrawPile = false;

    Card[] cardFoundation = new Card[4];
    public Solitaire(){
        CreateInitialDeck();
        for(int i = 0; i < cardFoundation.length; i++){
            cardFoundation[i] = null;
        }
        for(int i =0; i < 7; i++){
            cardColumns.add(new CardColumn());
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

    public void dealGame(){
        for(int i = 0; i < cardFoundation.length; i++) {
            if(cardFoundation[i] == null && i !=3){
                System.out.print("0, ");
            }
            else if(cardFoundation[3] == null){
                System.out.print("0");
            }
        }
        if(!inDrawPile){
            System.out.println("             |O|" );
        }
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
                cardColumns.get(col).addToCardColumn(cardDeck.get(index));
                cardDeck.remove(index);
                col++;
            }
            System.out.println();
        }
    }
    public void MoveCard(int fromCol, int toCol, int numCards){
        Card[] cardsToMove;
        cardsToMove = cardColumns.get(fromCol-1).getStack(numCards);
        for(int i = numCards-1; i >=0; i--){
            cardColumns.get(toCol-1).addToCardColumn(cardsToMove[i]);
        }
        redrawAfterPlay();
    }
    private void redrawAfterPlay(){
        for(int i = 0; i < 7; i++){
            boolean hasDoneFirstIndex = false;
            int firstIndexLength = -1;

            for(int k =0; k < 7 - i; k++){
                if(i > 0){
                    int iteratorI = i;
                    while (iteratorI >cardColumns.get(k).getCardColumn().size() - 1) {
                        System.out.print("    ");
                        iteratorI--;
                    }
                }

                if(!hasDoneFirstIndex){
                    System.out.println("K: " + k + " & I: " + i);
                    System.out.println(cardColumns.get(k).getColSize());
                    firstIndexLength = cardColumns.get(k).getCard(i).getCardNumber() < 10 ? 2 : 1 ;
                    hasDoneFirstIndex = true;
                }
                try{
                    if(!cardColumns.get(k).getCard(i).isFlipped()){
                        if(firstIndexLength == 2 ){
                            System.out.print("  x ");
                        }
                        else {
                            System.out.print(" x  ");
                        }
                    }

                    else if(k > cardColumns.get(k).getCardColumn().size()-1){
                        System.out.print("    ");
                    }
                    else {
                        System.out.print(cardColumns.get(k).getCard(i));
                    }

                }
                catch (IndexOutOfBoundsException e){

                }

            }
            System.out.println();
        }

    }
    private void OLDPrintCardBase(int i, int index) {
        dealtCard.add(cardDeck.get(index));
        cardDeck.remove(index);
        int tester = i + 1;
        while (tester < 7) {
            System.out.print("x  ");
            index = (int) (Math.random() * cardDeck.size());
            dealtCard.add(cardDeck.get(index));
            cardDeck.remove(index);
            tester++;
        }

    }
    public void printInitialDeck(){
        Iterator<Card> iterator = cardDeck.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
    }

}
