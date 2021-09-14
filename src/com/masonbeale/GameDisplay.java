package com.masonbeale;

public class GameDisplay {
    static SolitaireGameLogic gameLogic = new SolitaireGameLogic();
    public void dealInitialGame(){
        printTopOfInitialGame();
        printBottomOfInitialGame();
    }

    private void printTopOfInitialGame() {
        for(int i = 0; i < gameLogic.getCardFoundation().length; i++) {
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
            gameLogic.setHasDoneFirstIndexSize(false);
            int firstIndexLength = -1;
            int spacesIndex = col;
            int colIndex = col;

            for (int row = 7; row != col; row--) {
                int index = (int) (Math.random() * gameLogic.getCardDeck().size());
                if(spacesIndex != 0){
                    printFirstSpaces(spacesIndex);
                }
                if (!gameLogic.getHasDoneFirstIndexSize()) {
                    firstIndexLength = setInitialDealIndexSize(index);
                }
                printCardPlayingColumns(firstIndexLength, row, index);

                gameLogic.manageDeckToCol(index, gameLogic.getCardColumns()[colIndex], gameLogic.getCardDeck().get(index));
                colIndex++;
            }
            System.out.println();
        }

        gameLogic.createDrawPile();
    }
    private int setInitialDealIndexSize(int index) {
        int firstIndexLength;
        firstIndexLength = gameLogic.getCardDeck().get(index).getCardNumber() < 10 ? 2 : 1;
        gameLogic.setHasDoneFirstIndexSize(true);
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
            System.out.print(gameLogic.getCardDeck().get(index));
            gameLogic.getCardDeck().get(index).setFlipped(true);
        }
    }
    public void redrawAfterPlay(){
        gameLogic.checkWin();
        if(!gameLogic.getHasWon()){
            printTopOfGameField();
            printBottomOfGameField();
        }
        else
        {
            System.out.println("YOU WON! CONGRATS!!!");
        }
    }
    private void printTopOfGameField() {
        for(int i = 0; i < gameLogic.getCardFoundation().length; i++) {
            printFoundation(i);
        }
        printDrawPile();
        System.out.println();
    }

    private void printFoundation(int i) {
        if(i !=3){
            printAllFirstThreeFoundations(i);
        }
        else{
            printFinalFoundation(i);
        }
    }

    private void printAllFirstThreeFoundations(int i) {
        if(gameLogic.getCardFoundation()[i] == null){
            System.out.print("0, ");
        }
        else{
            System.out.print(gameLogic.getCardFoundation()[i] + ", ");
        }
    }

    private void printFinalFoundation(int i) {
        if(gameLogic.getCardFoundation()[i] == null){
            System.out.print("0");
        }
        else{
            System.out.print(gameLogic.getCardFoundation()[i]);
        }
    }

    private void printDrawPile() {
        int drawPilePos = gameLogic.getPosInDrawPile();
        if( drawPilePos >= gameLogic.getDrawPile().size()){
            gameLogic.setPosInDrawPile(-1);
        }
        if(drawPilePos == -1){
            System.out.println("                |O|" );
        }
        else {
            System.out.println("                " + gameLogic.getDrawPile().get(drawPilePos));
        }
    }
    private void printBottomOfGameField() {
        for(int row = 0; row < gameLogic.getMaxColLength(); row++) {
            gameLogic.setHasDoneFirstIndexSize(false);
            int firstIndexLength = -1;

            for (int col = 0; col < 7; col++) {
                if (!gameLogic.hasDoneFirstIndexSize) {
                    firstIndexLength = setFirstIndexLengthOfRedraw(row, col);
                }
                if (row >= gameLogic.getCardColumns()[col].getColSize()) {
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
    private void printSpaces() {
        System.out.print("    ");
    }
    private void printCards(int i, int firstIndexLength, int k) {
        if(gameLogic.getCardColumns()[k].getCard(i) != null){
            if (!gameLogic.getCardColumns()[k].getCard(i).isFlipped()) {
                if (firstIndexLength == 2) {

                    System.out.print("  x ");
                } else if (firstIndexLength == 1) {
                    System.out.print(" x  ");
                }
            }
            else {
                System.out.print(" " + gameLogic.getCardColumns()[k].getCard(i) + " ");
            }
        }
    }
    private int setFirstIndexLengthOfRedraw(int row,  int col) {
        if (row < gameLogic.getCardColumns()[col].getColSize() && gameLogic.getCardColumns()[col].getCard(row) != null) {
            gameLogic.setHasDoneFirstIndexSize(true);
            return gameLogic.getCardColumns()[col].getCard(row).getCardNumber() < 10 ? 2 : 1;
        }
        return 0;
    }


}
