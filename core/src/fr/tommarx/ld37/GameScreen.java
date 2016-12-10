package fr.tommarx.ld37;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.Screen;

public class GameScreen extends Screen{

    Player player;
    private final int WIDTH = 40, HEIGHT = 25;

    public GameScreen(Game game) {
        super(game);
    }

    public void show() {
        world.setGravity(new Vector2(0f, 0f));
        player = new Player(new Transform(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)));

        //Generate the room
        for (int x = 0; x < WIDTH; x++) {
            add(new Wall(new Transform(new Vector2(x * 32 + 1, 0))));
            add(new Wall(new Transform(new Vector2(x * 32, HEIGHT * 32))));
        }
        for (int y = 0; y < HEIGHT; y++) {
            add(new Wall(new Transform(new Vector2(0, y * 32))));
            add(new Wall(new Transform(new Vector2(WIDTH * 32, y * 32))));
        }

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
