package fr.tommarx.ld37;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.BoxBody;
import fr.tommarx.gameengine.Components.BoxRenderer;
import fr.tommarx.gameengine.Components.SpriteRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.GameObject;

public class Wall extends GameObject{

    public Wall(Transform transform) {
        super(transform);
        setTag("Wall");
        addComponent(new SpriteRenderer(this, Gdx.files.internal("sprites/wall.png")));
        addComponent(new BoxBody(this, 32, 32, BodyDef.BodyType.StaticBody));
    }

    protected void update(float delta) {

    }
}
