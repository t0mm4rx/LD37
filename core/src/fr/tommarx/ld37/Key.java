package fr.tommarx.ld37;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.BoxRenderer;
import fr.tommarx.gameengine.Components.CircleBody;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.GameObject;

public class Key extends GameObject {

    public Key(Transform transform) {
        super(transform);
        setTag("Key");
        addComponent(new BoxRenderer(this, 10, 10, Color.YELLOW));
        addComponent(new CircleBody(this, 5, BodyDef.BodyType.DynamicBody));
    }

    protected void update(float delta) {

    }
}
