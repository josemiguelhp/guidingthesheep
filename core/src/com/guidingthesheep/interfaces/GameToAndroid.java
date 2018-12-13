package com.guidingthesheep.interfaces;

public interface GameToAndroid {
    void resumeAn();
    void pauseAn();
    void disposeAn();
    void reset(int numberOfSearch);
    int getCurrentSearch();
    void startPocketSphinx();
}
