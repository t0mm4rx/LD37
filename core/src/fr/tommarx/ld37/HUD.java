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
    Text timeCounter;
    int lastKeys, lastLife;
    int seconds, minutes;
    double lastTime;

    public HUD(Transform transform) {
        super(transform);
        seconds = 00;
        minutes = 2;
        keyCounter = new Text(this, Gdx.files.internal("vcr.ttf"), 20,((Player)Game.getCurrentScreen().getGameObjectByTag("Player")).keys + " / " + Game.getCurrentScreen().getGameObjectsByTag("Key").size(), Color.WHITE);
        keyCounter.setOffset(Gdx.graphics.getWidth() - 70, Gdx.graphics.getHeight() - 20);
        addComponent(keyCounter);
        lifeCounter = new Text(this, Gdx.files.internal("vcr.ttf"), 20, ((Player)Game.getCurrentScreen().getGameObjectByTag("Player")).life + " lifes", Color.WHITE);
        lifeCounter.setOffset(Gdx.graphics.getWidth() - 70, Gdx.graphics.getHeight() - 40);
        timeCounter = new Text(this, Gdx.files.internal("vcr.ttf"), 25, getTime(), Color.WHITE);
        timeCounter.setOffset((int)(Gdx.graphics.getWidth() / 2 - transform.getPosition().x), Gdx.graphics.getHeight() - 20);
        addComponent(lifeCounter);
        addComponent(timeCounter);
        lastTime = System.currentTimeMillis();
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
        if (System.currentTimeMillis() - lastTime >= 1000) {
            lastTime = System.currentTimeMillis();
            if (minutes > 0 || seconds > 0) {
                if (seconds == 0) {
                    seconds = 59;
                    minutes--;
                } else {
                    seconds--;
                }
            } else {
                ((GameScreen)Game.getCurrentScreen()).die();
            }
            timeCounter.setText(getTime());
        }
    }

    private String getTime() {
        String sseconds, sminutes;
        if (seconds < 10) {
            sseconds = "0" + seconds;
        } else {
            sseconds = seconds + "";
        }
        if (minutes < 10) {
            sminutes = "0" + minutes;
        } else {
            sminutes = minutes + "";
        }
        return sminutes + ":" + sseconds;
    }
}
