package com.hubosm.turingsimulator.domain;


import lombok.Getter;
import lombok.Setter;

public class Tape {

    private final char blank;
    private TapeCell head;

    public Tape(char blank) {
        this.blank = blank;
        head = new TapeCell(blank);
    }

    public void moveHeadRight(){
        if(head.rightCell == null){
            head.rightCell = new TapeCell(blank);
            head.rightCell.leftCell = head;
        }
        head = head.rightCell;
    }

    public void moveHeadLeft(){
        if(head.leftCell == null){
            head.leftCell = new TapeCell(blank);
            head.leftCell.rightCell = head;
        }
        head = head.leftCell;
    }

    public char readHead(){
        return head.value;
    }

    public void writeOnHead(char value){
        head.value = value;
    }

    //places text onto tape, starting from current head position and moving right
    //doesn't change head position
    public void placeText(String input){
        TapeCell originalHead = head;
        for(char inputChar : input.toCharArray()){
            writeOnHead(inputChar);
            moveHeadRight();
        }
        head = originalHead;
    }

    private static class TapeCell{
        private char value;
        private TapeCell leftCell = null;
        private TapeCell rightCell = null;

        public TapeCell(char value) {
            this.value = value;
        }
    }
}
