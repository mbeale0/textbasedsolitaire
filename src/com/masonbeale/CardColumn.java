package com.masonbeale;

import java.util.ArrayList;
import java.util.List;

public class CardColumn {
    private List<Card> cardColumn = new ArrayList<>();
    public void addToCardColumn(Card card) {
        cardColumn.add(card);
    }

    public Card[] getStack(int numOfCards){
        Card[] cards = new Card[numOfCards];

        int cardIndex =0;
        int colIndexSize = cardColumn.size() - 1;
        if(numOfCards == 1){
            cards[cardIndex] = cardColumn.get(colIndexSize);
            cardColumn.remove(colIndexSize);
        }
        else{
            int timesToLoop = colIndexSize -numOfCards;
            for(int i = colIndexSize; i > timesToLoop; i--){
                changeCards(cards, cardIndex, i);
            }
        }
        if(cardColumn.size() != 0){
            colIndexSize = cardColumn.size()-1;
            cardColumn.get(colIndexSize).setFlipped(true);
        }

        return cards;
    }

    private void changeCards(Card[] cards, int cardIndex, int i) {
        cards[cardIndex] = cardColumn.get(i);
        cardColumn.remove(i);
        cardIndex++;

    }

    public Card getCard(int index) {
        if(cardColumn.size() != 0){
            return cardColumn.get(index);
        }
        else {
            return null;
        }
    }
    public int getColSize(){
        return cardColumn.size();
    }
}
