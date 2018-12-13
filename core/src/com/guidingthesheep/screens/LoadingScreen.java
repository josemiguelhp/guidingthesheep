package com.guidingthesheep.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.guidingthesheep.Core;
import com.guidingthesheep.utils.Texts;

public class LoadingScreen extends BaseScreen {
    private Stage stage;
    private Image logo,sheep;
    private Label cargando;
    private float percent,maxSheepW;

    public  LoadingScreen(Core game) {
        super(game);
    }

    @Override
    public void show() {
        core.getManager().load("skin/loadingSkin.atlas", TextureAtlas.class);
        core.getManager().load("skin/loadingSkin.json", Skin.class, new SkinLoader.SkinParameter("skin/loadingSkin.atlas"));        //wait until it load all the assets
        core.getManager().finishLoading();


        stage = new Stage(new ExtendViewport(480f, 800f));
        // Get our textureatlas from the manager
        // Grab the regions from the atlas and create some images

        TextureAtlas atlas = core.getManager().get("skin/loadingSkin.atlas", TextureAtlas.class);
        logo=new Image(atlas.findRegion("logo"));
        sheep=new Image(atlas.findRegion("sheep_shadow"));
        maxSheepW=sheep.getPrefWidth()+100;
        sheep.setSize(maxSheepW,sheep.getPrefHeight()+100);
        cargando=new Label("Cargando...",(Skin) core.getManager().get("skin/loadingSkin.json"));
        updatePositions();
        // Add all the actors to the stage
        stage.addActor(sheep);
        stage.addActor(logo);
        stage.addActor(cargando);
        //load all assets
        //skin
        core.getManager().load("skin/skin.atlas", TextureAtlas.class);
        core.getManager().load("skin/skin.json", Skin.class, new SkinLoader.SkinParameter("skin/skin.atlas"));
        //textures
        //core.getManager().load("chipi/chipi.atlas", TextureAtlas.class);
        //particles
        //core.getManager().load("particles/particleUplevel.p", ParticleEffect.class);
        //data


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(194f/255,134f/255,244f/255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        animateLogo(delta);
        if(percent>=0.90)
            cargando.setText(Texts.t1+"100%");
        else
            cargando.setText(Texts.t1+(int)(percent*100)+"%");
        cargando.setSize(cargando.getPrefWidth(),cargando.getPrefHeight());
        updatePositions();
        if (core.getManager().update()) {
            //load screens
            if(!core.screensInitialize())
                core.initializeScreens();
            if(percent>=0.99f) {//animation then kill loading
                if (animationGo(delta))
                    core.finishLoading(this);
            }

        }
        // Show the loading screen
        percent = Interpolation.linear.apply(percent, core.getManager().getProgress(), 0.1f);

        stage.draw();
    }
    private Boolean animationGoActivate=false;
    private Boolean animationGo(float delta) {
          if(!animationGoActivate)
              animationGoActivate=true;
          if(logo.getY()<stage.getHeight()-logo.getHeight()-10)
          logo.setY(logo.getY()+3);
          sheep.setY(sheep.getY()-6);
          cargando.setY(cargando.getY()-6);
          if(logo.getY()>=stage.getHeight()-logo.getHeight()-10&&sheep.getY()<=-sheep.getHeight())
              return true;
          return false;
    }

    private float palanca=1;
    private int couna=0;
    private Boolean der=true;
    private void animateLogo(float delta) {
        couna++;
        if(couna>=2000*delta){
            palanca=palanca*-1;
            couna=0;
        }

        logo.setWidth(logo.getWidth()-palanca);
        if(Math.abs(sheep.getWidth())>=maxSheepW)
            der=!der;
        if(!der)
        sheep.setWidth(sheep.getWidth()-6);
        else sheep.setWidth(sheep.getWidth()+6);

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        updatePositions();
    }

    private void updatePositions() {
        logo.setX((stage.getWidth() - logo.getWidth()) / 2);
        cargando.setX((stage.getWidth() - cargando.getPrefWidth()) / 2);
        sheep.setX((stage.getWidth() - sheep.getWidth()) / 2);
        if(!animationGoActivate){
            logo.setY((stage.getHeight() - logo.getHeight()) -150);
            cargando.setY(100);
            sheep.setY(cargando.getY()+cargando.getPrefHeight()/2+100);
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        core.getManager().unload("skin/loadingSkin.atlas");
        core.getManager().unload("skin/loadingSkin.json");
    }


}

