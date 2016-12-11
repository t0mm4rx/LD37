package fr.tommarx.ld37.Monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.CircleBody;
import fr.tommarx.gameengine.Components.SpriteRenderer;
import fr.tommarx.gameengine.Components.Transform;

public class Bullet extends Monster {

    Vector2 direction;
    CircleBody body;
    Vector2 to;

    public Bullet(Transform transform, Vector2 to) {
        super(transform, 5000, 1, 20);
        //addComponent(new BoxRenderer(this, 8, 8, Color.WHITE));
        addComponent(new SpriteRenderer(this, Gdx.files.internal("sprites/turret/bullet_idle.png")));
        body = new CircleBody(this, 4, BodyDef.BodyType.DynamicBody);
        body.getBody().setTransform(body.getBody().getTransform().getPosition(), getAngle(to) + 90 + 180);
        addComponent(body);
        setTag("MonsterBullet");
        this.to = to;
        direction = new Vector2(
                to.x - getTransform().getPosition().x,
                to.y - getTransform().getPosition().y
        ).nor().scl(speed);

        body.getBody().setLinearVelocity(direction);
    }

    protected void update(float delta) {
        this.body.getBody().setLinearVelocity(direction);
    }

    public float getAngle(Vector2 target) {
        float angle = (float) Math.toDegrees(Math.atan2(target.y - getTransform().getPosition().y, target.x - getTransform().getPosition().x));

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }
}
