package fr.tommarx.ld37;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.concurrent.Callable;

import fr.tommarx.gameengine.Collisions.CollisionsListener;
import fr.tommarx.gameengine.Collisions.CollisionsManager;
import fr.tommarx.gameengine.Components.BoxRenderer;
import fr.tommarx.gameengine.Components.SpriteRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Easing.Tween;
import fr.tommarx.gameengine.Game.EmptyGameObject;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.GameObject;
import fr.tommarx.gameengine.Game.Screen;
import fr.tommarx.ld37.Monsters.Monster;

public class GameScreen extends Screen{

    EmptyGameObject overlay;
    public static float width, height;
    HUD hud;
    private boolean isDead;

    public GameScreen(Game game) {
        super(game);
    }

    public void show() {
        isDead = false;
        areLightsEnabled(true);
        world.setGravity(new Vector2(0f, 0f));
        rayHandler.setAmbientLight(new Color(0, 0, 0, 0.4f));
        generateRoom();
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
                if (a.getTag().equals("Player") && b.getTag().contains("Monster")) {
                    ((Player) a).hurt((int)((Monster)b).damages, b.getTransform().getPosition(), ((Monster)b).knockback);
                }
                if (b.getTag().equals("Player") && a.getTag().contains("Monster")) {
                    ((Player) b).hurt((int)((Monster)a).damages, a.getTransform().getPosition(), ((Monster)a).knockback);
                }
                if (b.getTag().contains("Bullet")) {
                    Game.getCurrentScreen().remove(b);
                }
                if (a.getTag().contains("Bullet")) {
                    Game.getCurrentScreen().remove(a);
                }
            }

            public void collisionEnd(GameObject a, GameObject b) {}
        });
        hud = new HUD(new Transform(new Vector2(0, 0)));
        addInHUD(hud);
        overlay = new EmptyGameObject(new Transform(new Vector2(camera.position.x, camera.position.y)));
        overlay.addComponent(new BoxRenderer(overlay, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new Color(0, 0, 0, 1)));
        add(overlay);
        Game.tweenManager.goTween(new Tween("AlphaGameOverlay", Tween.LINEAR_EASE_NONE, 1f, -1f, 1f, 0f, false));
    }

    public void update() {

        Game.debug(1, "## DEBUG ##");
        Game.debug(2, "FPS : " + Gdx.graphics.getFramesPerSecond());

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Game.debugging = !Game.debugging;
        }

        overlay.getTransform().setPosition(new Vector2(camera.position.x, camera.position.y));
        ((BoxRenderer)overlay.getComponentByClass("BoxRenderer")).setColor(new Color(0, 0, 0, Game.tweenManager.getValue("AlphaGameOverlay")));
    }

    public void generateRoom() {
        Vector2 size = fr.tommarx.ld37.Map.MapReader.read(Gdx.files.internal("map.map"));
        this.width = size.x;
        this.height = size.y;
        EmptyGameObject background = new EmptyGameObject(new Transform(new Vector2(64, 64)));
        for (int x = 0; x < width / 128; x++) {
            for (int y = 0; y < height / 128; y++) {
                SpriteRenderer renderer = new SpriteRenderer(background, Gdx.files.internal("sprites/ground.png"));
                renderer.setOffset(x * 128, y * 128);
                background.addComponent(renderer);
            }
        }
        background.setLayout(-1);
        add(background);
    }

    public void die() {
        if (!isDead) {
            isDead = true;
            remove(hud);
            for (GameObject go : getGameObjects()) {
                if (go.getComponentByClass("ConeLight") != null || go.getComponentByClass("PointLight") != null) {
                    remove(go);
                }
            }
            Game.tweenManager.goTween(new Tween("AlphaGameOverlay", Tween.LINEAR_EASE_NONE, 0f, 1f, 1f, 0f, false));
            Game.waitAndDo(1f, new Callable() {
                public Object call() throws Exception {
                    setScreen(new GameOverScreen(game));
                    return null;
                }
            });
        }
    }

}
