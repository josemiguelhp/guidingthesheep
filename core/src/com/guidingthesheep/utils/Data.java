package com.guidingthesheep.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

public class Data {
    private static final String DATA_FILE = "bin/dataBin.json";
    public GameVars gameVars;
    public Preferences prefs = Gdx.app.getPreferences("eabPreferences");
    private FileHandle fileGameVars;
    public Data(){
        fileGameVars=Gdx.files.local(DATA_FILE);
        loadGameVars();
    }

    public void saveGameVars(){
        prefs.flush();
        Json json=new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        fileGameVars.writeString(Base64Coder.encodeString(json.toJson(gameVars)),false);
    }
    private void loadGameVars(){
        Json json=new Json();
        if(fileGameVars.exists()) {
            try {
                gameVars=json.fromJson(GameVars.class,Base64Coder.decodeString(fileGameVars.readString()));
                System.out.println("cargado");
            } catch( Exception e ) {
                Gdx.app.error( "Game Reset", "Failed Reading", e );
            }
        } else {
            System.out.println("Creando nuevo juego");
            gameVars=new GameVars(true);
            saveGameVars();
        }
    }
    public float expForNextRank(){
        return 370.4f;
    }


}

