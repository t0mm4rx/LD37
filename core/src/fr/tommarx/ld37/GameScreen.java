package fr.tommarx.ld37;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.Screen;

public class GameScreen extends Screen{

    public GameScreen(Game game) {
        super(game);
    }

    public void show() {

    }

    public void update() {

        Game.debug(1, "## DEBUG ##");

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            Game.debugging = !Game.debugging;
        }
    }
}
