package fr.tommarx.ld37;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import fr.tommarx.gameengine.Components.Text;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.GameObject;

public class HUD extends GameObject{

    Text keyCounter;
    Text lifeCounter;
    int lastKeys, lastLife;

    public HUD(Transform transform) {
        super(transform);
        keyCounter = new Text(this, ((Player)Game.getCurrentScreen().getGameObjectByTag("Player")).keys + " / " + Game.getCurrentScreen().getGameObjectsByTag("Key").size(), Color.WHITE);
        keyCounter.setOffset(Gdx.graphics.getWidth() - 70, Gdx.graphics.getHeight() - 20);
        addComponent(keyCounter);
        lifeCounter = new Text(this, ((Player)Game.getCurrentScreen().getGameObjectByTag("Player")).life + " lifes", Color.WHITE);
        lifeCounter.setOffset(Gdx.graphics.getWidth() - 70, Gdx.graphics.getHeight() - 40);
        addComponent(lifeCounter);
    }

    protected void update(float delta) {
        if (((Player)Game.getCurrentScreen().getGameObjectByTag("Player")).keys != lastKeys) {
            lastKeys = ((Player)Game.getCurrentScreen().getGameObjectByTag("Player")).keys;
            keyCounter.setText(((Player)Game.getCurrentScreen().getGameObjectByTag("Player")).keys + " / " + (Game.getCurrentScreen().getGameObjectsByTag("Key").size() + ((Player)Game.getCurrentScreen().getGameObjectByTag("Player")).keys));
        }
        if (((Player)Game.getCurrentScreen().getGameObjectByTag("Player")).life != lastLife) {
            lastLife = ((Player)Game.getCurrentScreen().getGameObjectByTag("Player")).life;
            lifeCounter.setText(((Player)Game.getCurrentScreen().getGameObjectByTag("Player")).life + " lifes");
        }
    }
}
