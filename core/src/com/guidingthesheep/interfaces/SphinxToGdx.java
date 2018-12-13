package com.guidingthesheep.interfaces;

public interface SphinxToGdx {
    void onCommand(String command);
    void pocketSphinxError(String error);
    void stopListening();
}