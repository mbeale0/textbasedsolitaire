package com.masonbeale;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Solitaire {
    private List<Card> cardDeck = new ArrayList<Card>();
    private List<Card> dealtCard = new ArrayList<Card>();
    private List<CardColumn> cardColumns = new ArrayList<CardColumn>();
    private List<Card> drawPile = new ArrayList<Card>();
    private boolean inDrawPile = false;
    private Card[] cardFoundation = new Card[4];

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

    public void dealInitialGame(){
        for(int i = 0; i < cardFoundation.length; i++) {
            if(i !=3){
                System.out.print("0, ");
            }
            else{
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

    private void redrawAfterPlay(){
        for(int i = 0; i < 7; i++){
            boolean hasDoneFirstIndex = false;
            int firstIndexLength = -1;

            for(int k =0; k < 7 - i; k++){
                if(!hasDoneFirstIndex){
                    if(i < cardColumns.get(k).getColSize()){

                        firstIndexLength = cardColumns.get(k).getCard(i).getCardNumber() < 10 ? 2 : 1 ;
                        hasDoneFirstIndex = true;
                    }
                }
                if(i >= cardColumns.get(k).getColSize()){
                    System.out.print("    ");
                }
                else{
                    if(!cardColumns.get(k).getCard(i).isFlipped()){
                        if(firstIndexLength == 2 ){
                            System.out.print("  x ");
                        }
                        else if(firstIndexLength == 1) {
                            System.out.print(" x  ");
                        }
                    }
                    else {
                        System.out.print(cardColumns.get(k).getCard(i));
                    }
                }
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
}
