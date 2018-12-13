package com.guidingthesheep;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.guidingthesheep.interfaces.GameToAndroid;
import com.guidingthesheep.interfaces.SphinxToGdx;
import com.guidingthesheep.screens.LoadingScreen;
import com.guidingthesheep.screens.MenuScreen;
import com.guidingthesheep.utils.Data;


public class Core extends Game implements SphinxToGdx {
	private AssetManager manager=new AssetManager();
	private Data data;
	//private GameScreen gameScreen;
	private LoadingScreen loadingScreen;
	private MenuScreen menuScreen;
	private Boolean screensCreated=false;
	private GameToAndroid toAndroid;
	public Core(){}
	public Core(GameToAndroid toAndroid){
		this();
		this.toAndroid=toAndroid;
	}
	@Override
	public void create () {
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}
	public boolean screensInitialize() {
		return screensCreated;
	}

	public void initializeScreens() {
		if(!screensCreated) {
			screensCreated = true;
			//initialize statics
			//VisUI.load((Skin) manager.get("skin/skin.json"));
			//soundManager=new SoundManager(manager);
			//Initialize Data
			data = new Data();
			//initialize menu
			menuScreen = new MenuScreen(this);
			//gameScreen = new GameScreen(this);
		}
	}

	public void finishLoading(LoadingScreen loadingScreen) {
		if(loadingScreen==screen){
			setScreen(menuScreen);
			loadingScreen.dispose();
		}
	}


	@Override
	public void dispose () {
		if(menuScreen!=null)
			menuScreen.dispose();
		data.saveGameVars();

	}

	public AssetManager getManager() {
		return manager;
	}


	public Data getData() {
		return data;
	}

	@Override
	public void onCommand(String command) {
		if(command.endsWith(" "))
			command=command.substring(0,command.lastIndexOf(" "));
		String lastWord=command;
		if(command.contains(" "))
			lastWord = command.substring(command.lastIndexOf(" ")+1);
		/*if(gameScreen!=null)
			gameScreen.onCommand(lastWord);*/
	}

	@Override
	public void pocketSphinxError(String error) {
		/*if(gameScreen!=null)
			gameScreen.onCommand(error);*/
	}

	@Override
	public void stopListening() {

	}
	public void switchSearch(int n){
		if(toAndroid!=null&&n==-1)
			toAndroid.reset(toAndroid.getCurrentSearch());
		else if(toAndroid!=null)
			toAndroid.reset(n);
	}
	public void moveGameCamera(float x, int y) {
		//gameScreen.moveGameCamera(x,y);
	}
}
