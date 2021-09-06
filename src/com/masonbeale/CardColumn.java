package com.masonbeale;

import java.util.ArrayList;
import java.util.List;

public class CardColumn {
    private List<Card> cardColumn = new ArrayList<Card>();
    int cards = 0;
    public void addToCardColumn(Card card) {
        cardColumn.add(card);
    }

    public Card getTopCard(){
        return cardColumn.get(cardColumn.size()-1);
    }

    public Card[] getStack(int numOfCards){
        Card[] cards = new Card[numOfCards];

        int cardIndex =0;
        if(numOfCards == 1){
            cards[cardIndex] = cardColumn.get(cardColumn.size()-1);
            cardColumn.remove(cardColumn.size()-1);
        }
        else if(numOfCards == cardColumn.size()-1){
            for(int i = cardColumn.size() - 1; i >  numOfCards-1; i--){
                cards[cardIndex] = cardColumn.get(i);
                cardColumn.remove(i);
                cardIndex++;
            }
        }
        else {
            System.out.println("N: " + numOfCards);
            for(int i = cardColumn.size() - 1; i >  numOfCards; i--){
                cards[cardIndex] = cardColumn.get(i);
                cardColumn.remove(i);
                cardIndex++;
            }
        }
        cardColumn.get(cardColumn.size()-1).setFlipped(true);
        return cards;
    }
    public Card getBottomCard(){
        return cardColumn.get(0);
    }
    public List<Card> getCardColumn() {
        return cardColumn;
    }

    public Card getCard(int index) {
        return cardColumn.get(index);
    }
    public int getColSize(){
        return cardColumn.size();
    }
}
