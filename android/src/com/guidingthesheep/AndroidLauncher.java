package com.guidingthesheep;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.guidingthesheep.interfaces.GameToAndroid;

public class AndroidLauncher extends AndroidApplication implements GameToAndroid {
	private PocketSphinx recognizer;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize Libgdx
		Core game=new Core(this);
		//initialize PocketSphinxS
		recognizer = new PocketSphinx(game,this,this);
		//play
		initialize(game, config);
	}

	@Override
	public void resumeAn() { recognizer.resume(); }
	@Override
	public void pauseAn() { recognizer.pause(); }
	@Override
	public void disposeAn() {
		recognizer.dispose();
	}
	@Override
	public void reset(int numberOfSearch) {
		recognizer.reset(numberOfSearch);

	}
	@Override
	protected void onPause(){
		super.onPause();
		pauseAn();
	}

	@Override
	public int getCurrentSearch() {
		return recognizer.getCurrentSearch();
	}


	@Override
	public void startPocketSphinx() {
		recognizer.runRecognizerSetup();
	}
}

