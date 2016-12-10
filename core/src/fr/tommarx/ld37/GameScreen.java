package fr.tommarx.ld37;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import fr.tommarx.gameengine.Collisions.CollisionsListener;
import fr.tommarx.gameengine.Collisions.CollisionsManager;
import fr.tommarx.gameengine.Components.SpriteRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.EmptyGameObject;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.GameObject;
import fr.tommarx.gameengine.Game.Screen;
import fr.tommarx.ld37.Monsters.Knight;
import fr.tommarx.ld37.Monsters.Monster;
import fr.tommarx.ld37.Monsters.Zombie;

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
                if (a.getTag().equals("Player") && b.getTag().equals("Monster")) {
                    ((Player) a).hurt((int)((Monster)b).damages, b.getTransform().getPosition(), ((Monster)b).knockback);
                }
                if (b.getTag().equals("Player") && a.getTag().equals("Monster")) {
                    ((Player) b).hurt((int)((Monster)a).damages, a.getTransform().getPosition(), ((Monster)a).knockback);
                }
            }

            public void collisionEnd(GameObject a, GameObject b) {}
        });
        addInHUD(new HUD(new Transform(new Vector2(0, 0))));
    }

    public void update() {

        Game.debug(1, "## DEBUG ##");
        Game.debug(2, "FPS : " + Gdx.graphics.getFramesPerSecond());

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Game.debugging = !Game.debugging;
        }
    }

    public void generateRoom() {
        EmptyGameObject background = new EmptyGameObject(new Transform(new Vector2(64, 64)));
        for (int x = 0; x < WIDTH * 32 / 128; x++) {
            for (int y = 0; y < HEIGHT * 32 / 128 + 1; y++) {
                SpriteRenderer renderer = new SpriteRenderer(background, Gdx.files.internal("sprites/ground.png"));
                renderer.setOffset(x * 128, y * 128);
                background.addComponent(renderer);
            }
        }
        background.setLayout(0);
        add(background);
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
        add(new Wall(new Transform(new Vector2(4 * 32, 4 * 32))));
        add(new Wall(new Transform(new Vector2(3 * 32, 4 * 32))));
        add(new Wall(new Transform(new Vector2(2 * 32, 4 * 32))));
        add(new Wall(new Transform(new Vector2(1 * 32, 4 * 32))));
        add(new ExitDoor(new Transform(new Vector2((WIDTH - 1) * 32, HEIGHT / 2 * 32))));
        //add(new Zombie(new Transform(new Vector2(800, 500)), new Vector2(500, 300)));
        add(new Knight(new Transform(new Vector2(1000, 700)), new Vector2(700, 500)));
    }
}
