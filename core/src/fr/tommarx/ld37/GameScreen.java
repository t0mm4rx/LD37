package fr.tommarx.ld37;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import fr.tommarx.gameengine.Collisions.CollisionsListener;
import fr.tommarx.gameengine.Collisions.CollisionsManager;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.GameObject;
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
        generateRoom();
        add(player);
        new CollisionsManager(new CollisionsListener() {
            public void collisionEnter(GameObject a, GameObject b) {
                if (a.getTag().equals("Player") && b.getTag().equals("Key")) {
                    Game.getCurrentScreen().remove(b);
                    ((Player)a).keys++;
                }
                if (a.getTag().equals("Key") && b.getTag().equals("Player")) {
                    Game.getCurrentScreen().remove(a);
                    ((Player)b).keys++;
                }
            }

            public void collisionEnd(GameObject a, GameObject b) {}
        });
    }

    public void update() {

        Game.debug(1, "## DEBUG ##");
        Game.debug(2, "FPS : " + Gdx.graphics.getFramesPerSecond());

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Game.debugging = !Game.debugging;
        }
    }

    public void generateRoom() {
        //Generate the room
        for (int x = 0; x < WIDTH; x++) {
            add(new Wall(new Transform(new Vector2(x * 32 + 1, 0))));
            add(new Wall(new Transform(new Vector2(x * 32, HEIGHT * 32))));
        }
        for (int y = 0; y < HEIGHT; y++) {
            add(new Wall(new Transform(new Vector2(0, y * 32))));
            add(new Wall(new Transform(new Vector2(WIDTH * 32, y * 32))));
        }
        add(new Key(new Transform(new Vector2(2 * 32, 2 * 32))));
        add(new Wall(new Transform(new Vector2(4 * 32, 1 * 32))));
        add(new Door(new Transform(new Vector2(4 * 32, 2.5f * 32))));
        /*add(new Wall(new Transform(new Vector2(4 * 32, 2 * 32))));
        add(new Wall(new Transform(new Vector2(4 * 32, 3 * 32))));*/
        add(new Wall(new Transform(new Vector2(4 * 32, 4 * 32))));
        add(new Wall(new Transform(new Vector2(3 * 32, 4 * 32))));
        add(new Wall(new Transform(new Vector2(2 * 32, 4 * 32))));
        add(new Wall(new Transform(new Vector2(1 * 32, 4 * 32))));
    }
}
