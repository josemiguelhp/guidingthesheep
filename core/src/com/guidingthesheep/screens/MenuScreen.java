package com.guidingthesheep.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.guidingthesheep.Core;
import com.guidingthesheep.utils.Globals;
import com.guidingthesheep.utils.Texts;

public class MenuScreen extends BaseScreen {
    private Stage stage;
    private Image logo;
    private Label rights;

    public MenuScreen(Core game) {
        super(game);
        stage=new Stage(new ExtendViewport(Globals.VIRTUAL_WIDTH,Globals.VIRTUAL_HEIGHT));//set up camera & stage
        Skin skin = core.getManager().get("skin/skin.json");
        logo=new Image(skin.getDrawable("logo"));
        logo.setPosition(stage.getWidth()/2-logo.getPrefWidth()/2+10,stage.getHeight()-logo.getHeight()-10);
        stage.addActor(logo);
        rights=new Label(Texts.l1,skin);
        rights.setPosition(stage.getWidth()/2-rights.getPrefWidth()/2,3);
        stage.addActor(rights);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(194f/255,134f/255,244f/255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
    @Override
    public void dispose() {

    }

}
