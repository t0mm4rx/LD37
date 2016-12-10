package fr.tommarx.ld37;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import java.util.UUID;
import java.util.concurrent.Callable;

import fr.tommarx.gameengine.Components.BoxBody;
import fr.tommarx.gameengine.Components.BoxRenderer;
import fr.tommarx.gameengine.Components.Text;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Easing.Tween;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.GameObject;

public class Door extends GameObject {

    Text text;
    String id;
    GameObject _this;
    float width, height;
    boolean isVertical;

    public Door(Transform transform, float width, float height) {
        super(transform);
        _this = this;
        setTag("Door");
        addComponent(new BoxRenderer(this, width, height, Color.WHITE));
        addComponent(new BoxBody(this, width, height, BodyDef.BodyType.StaticBody));
        text = new Text(this, Gdx.files.internal("vcr.ttf"), 12, "Press enter to open", new Color(1f, 1f, 1f, 0f));
        id = UUID.randomUUID().toString();
        this.width = width;
        this.height = height;
        if (height > width) {
            isVertical = true;
            text.setOffset(80, 0);
        } else {
            isVertical = false;
            text.setOffset(0, -30);
        }

        addComponent(text);
    }

    protected void update(float delta) {
        for (GameObject go : Game.getCurrentScreen().getGameObjects()) {
            if (go.getTag().equals("Player")) {
                if (go.getTransform().getPosition().dst(getTransform().getPosition()) < 100) {
                    if (text.getColor().a == 0) {
                        Game.tweenManager.goTween(new Tween("DoorAlpha:" + id, Tween.LINEAR_EASE_NONE, 0f, 1f, .4f, 0f, false));
                    }
                    if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                        open();
                    }
                } else {
                    if (text.getColor().a == 1) {
                        Game.tweenManager.goTween(new Tween("DoorAlpha:" + id, Tween.LINEAR_EASE_NONE, 1f, -1f, .4f, 0f, false));
                    }
                }
            }
        }
        text.setColor(new Color(1, 1, 1, Game.tweenManager.getValue("DoorAlpha:" + id)));
        if (Game.tweenManager.getValue("DoorScale:" + id) != 0) {
            if (isVertical) {
                ((BoxRenderer) getComponentByClass("BoxRenderer")).setHeight(Game.tweenManager.getValue("DoorScale:" + id) * height);
            } else {
                ((BoxRenderer) getComponentByClass("BoxRenderer")).setWidth(Game.tweenManager.getValue("DoorScale:" + id) * width);
            }
        }
    }

    private void open() {
        Game.tweenManager.goTween(new Tween("DoorScale:" + id, Tween.LINEAR_EASE_NONE, 1f, -1f, 1f, 0f, false));
        Game.waitAndDo(1f, new Callable() {
            public Object call() throws Exception {
                Game.getCurrentScreen().remove(_this);
                return null;
            }
        });
    }
}
