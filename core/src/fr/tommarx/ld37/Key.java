package fr.tommarx.ld37;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.BoxRenderer;
import fr.tommarx.gameengine.Components.CircleBody;
import fr.tommarx.gameengine.Components.PointLight;
import fr.tommarx.gameengine.Components.SpriteRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.GameObject;

public class Key extends GameObject {

    public Key(Transform transform) {
        super(transform);
        setTag("Key");
        addComponent(new SpriteRenderer(this, Gdx.files.internal("sprites/key.png")));
        CircleBody body = new CircleBody(this, 5, BodyDef.BodyType.DynamicBody);
        body.getBody().setFixedRotation(true);
        addComponent(body);
        addComponent(new PointLight(this, 100, 100, Color.ORANGE, Game.getCurrentScreen().rayHandler));
    }

    protected void update(float delta) {

    }
}
