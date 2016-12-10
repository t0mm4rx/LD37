package fr.tommarx.ld37.Monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.BoxRenderer;
import fr.tommarx.gameengine.Components.CircleBody;
import fr.tommarx.gameengine.Components.Transform;

public class Bullet extends Monster {

    Vector2 direction;
    CircleBody body;

    public Bullet(Transform transform, Vector2 to) {
        super(transform, 5000, 1, 20);
        addComponent(new BoxRenderer(this, 8, 8, Color.WHITE));
        body = new CircleBody(this, 4, BodyDef.BodyType.DynamicBody);
        addComponent(body);
        setTag("MonsterBullet");
        direction = new Vector2(
                to.x - getTransform().getPosition().x,
                to.y - getTransform().getPosition().y
        ).nor().scl(speed);

        body.getBody().setLinearVelocity(direction);
    }

    protected void update(float delta) {
        this.body.getBody().setLinearVelocity(direction);
    }
}
