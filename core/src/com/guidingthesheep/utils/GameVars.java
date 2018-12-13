package com.guidingthesheep.utils;

import com.badlogic.gdx.utils.Array;

public class GameVars {
    private int level;
    private float time,exp,stageX;
    private Boolean folowing;
    public String username;
    /*public Array<Floor> floors;
    public Chipi bingo;*/
    GameVars (){}
    GameVars(Boolean newGame){
        //first time configuration
        username="Jose";
        time =0;
        level=1;
        exp=0;
        folowing=true;
        setStagePosition(0);
        /*floors = new Array<Floor>(7);
        for(int i=0;i<7;i++){
            floors.add(new Floor(true,i));
        }
        bingo=new Chipi(480/2,800);*/
        System.out.println("se creo una gamevars");
    }

    public int getLevel(){
        return level;
    }
    public float getExp() {return exp;}
    public void addExp(float newExp){exp=exp+newExp;}
    public float getStageX() {
        return stageX;
    }

    public void setStagePosition(float stageX) {
        this.stageX = stageX;
    }


    public Boolean getFolowing() {
        return folowing;
    }

    public void setFolowing(Boolean folowing) {
        this.folowing = folowing;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
