package com.masonbeale;

import java.util.ArrayList;
import java.util.List;

public class CardColumn {
    private List<Card> cardColumn = new ArrayList<>();
    public void addToCardColumn(Card card) {
        cardColumn.add(card);
    }

    private void changeCards(Card[] cards, int cardIndex, int i) {
        cards[cardIndex] = cardColumn.get(i);
        cardColumn.remove(i);
        cardIndex++;

    }
    public Card getBottomCard(){
        if(cardColumn.size() == 0){
            return null;
        }
        Card bottomCard = cardColumn.get(cardColumn.size()-1);
        cardColumn.remove(cardColumn.get(cardColumn.size()-1));
        if(cardColumn.size() != 0){
            cardColumn.get(cardColumn.size()-1).setFlipped(true);
        }
        return bottomCard;
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
