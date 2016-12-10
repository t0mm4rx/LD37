package fr.tommarx.ld37;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.Screen;

public class GameScreen extends Screen{

    Player player;

    public GameScreen(Game game) {
        super(game);
    }

    public void show() {
        world.setGravity(new Vector2(0f, 0f));
        player = new Player(new Transform(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)));


        add(player);
    }

    public void update() {

        Game.debug(1, "## DEBUG ##");
        Game.debug(2, "FPS : " + Gdx.graphics.getFramesPerSecond());

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Game.debugging = !Game.debugging;
        }
    }
}
