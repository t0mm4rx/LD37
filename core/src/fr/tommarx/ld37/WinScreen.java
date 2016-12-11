package fr.tommarx.ld37;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import fr.tommarx.gameengine.Components.BoxRenderer;
import fr.tommarx.gameengine.Components.Text;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Easing.Tween;
import fr.tommarx.gameengine.Game.EmptyGameObject;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.Screen;

public class WinScreen extends Screen{

    EmptyGameObject title, overlay, restart;

    public WinScreen(Game game) {
        super(game);
    }

    public void show() {
        title = new EmptyGameObject(new Transform(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)));
        title.addComponent(new Text(title, Gdx.files.internal("vcr.ttf"), 30, "You've won !", Color.WHITE));
        add(title);

        restart = new EmptyGameObject(new Transform(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 50)));
        restart.addComponent(new Text(restart, Gdx.files.internal("vcr.ttf"), 15, "Press R to restart...", Color.WHITE));
        Game.tweenManager.goTween(new Tween("Restart", Tween.LINEAR_EASE_NONE, 1, -1, 1f, 1, true));
        add(restart);

        overlay = new EmptyGameObject(new Transform(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)));
        overlay.addComponent(new BoxRenderer(overlay, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Color.BLACK));
        add(overlay);

        Game.tweenManager.goTween(new Tween("AlphaGameOver", Tween.LINEAR_EASE_NONE, 1, -1, 1f, 1, false));

    }

    public void update() {
        Game.debug(1, "" + ((BoxRenderer)overlay.getComponentByClass("BoxRenderer")).getColor().a);
        ((BoxRenderer)overlay.getComponentByClass("BoxRenderer")).setColor(new Color(0, 0, 0, Game.tweenManager.getValue("AlphaGameOver")));
        ((Text)restart.getComponentByClass("Text")).setColor(new Color(1, 1, 1, Game.tweenManager.getValue("Restart")));
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            setScreen(new GameScreen(game));
        }
    }

}
