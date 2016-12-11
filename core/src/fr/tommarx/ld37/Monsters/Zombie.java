package fr.tommarx.ld37.Monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.BoxRenderer;
import fr.tommarx.gameengine.Components.CircleBody;
import fr.tommarx.gameengine.Components.Transform;

public class Zombie extends Monster {

    private Vector2 from, to, target;
    private CircleBody body;

    public Zombie(Transform transform, Vector2 target, float speed, float damages) {
        super(transform, speed, damages, 20);
        from = transform.getPosition();
        to = target;
        addComponent(new BoxRenderer(this, 32, 32, Color.BLACK));
        body = new CircleBody(this, 16, BodyDef.BodyType.DynamicBody);
        addComponent(body);
        this.target = to;
    }

    protected void update(float delta) {
        body.getBody().setLinearVelocity(new Vector2(target.x - getTransform().getPosition().x, target.y - getTransform().getPosition().y).nor().scl(speed));
        if (getTransform().getPosition().dst(target) < 10) {
            changeDirection();
        }
        if (body.getBody().getLinearVelocity().x == 0 && body.getBody().getLinearVelocity().y == 0) {
            changeDirection();
        }
    }

    private void changeDirection() {
        if (target == to) {
            target = from;
        } else {
            target = to;
        }
    }

}
