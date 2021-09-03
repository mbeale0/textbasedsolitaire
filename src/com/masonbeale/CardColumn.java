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

        // TODO The issue lies here, in that the for loop iterates to many times
        // EG: when size is 3, i = 2, and numofCards =1, the loop iteratres twice but it should only once
        for(int i = cardColumn.size() - 1; i >=  numOfCards; i--){
            System.out.println("IN: " + cardIndex);
            cards[cardIndex] = cardColumn.get(i);
            cardColumn.remove(i);
            cardIndex++;
        }
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
}
