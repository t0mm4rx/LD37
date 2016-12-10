package fr.tommarx.ld37.Monsters;

import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.CircleBody;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.GameObject;

public abstract class Monster extends GameObject {

    CircleBody body;
    public float speed, damages, knockback;

    public Monster(Transform transform, float speed, float damages, float knockback) {
        super(transform);
        this.speed = speed;
        this.damages = damages;
        this.knockback = knockback;
        setTag("Monster");
        body = new CircleBody(this, 16, BodyDef.BodyType.DynamicBody);
        addComponent(body);
    }

}
