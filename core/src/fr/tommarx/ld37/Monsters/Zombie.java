package fr.tommarx.ld37.Monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.BoxRenderer;
import fr.tommarx.gameengine.Components.CircleBody;
import fr.tommarx.gameengine.Components.Transform;

public class Zombie extends Monster {

    private Vector2 from, to;
    private boolean isComingBack;
    private CircleBody body;

    public Zombie(Transform transform, Vector2 target) {
        super(transform, 60, 1, 20);
        from = transform.getPosition();
        to = target;
        isComingBack = false;
        addComponent(new BoxRenderer(this, 32, 32, Color.BLACK));
        body = new CircleBody(this, 16, BodyDef.BodyType.DynamicBody);
        addComponent(body);
    }

    protected void update(float delta) {
        if (isComingBack) {
            body.getBody().setLinearVelocity(new Vector2(from.x - to.x, from.y - to.y).nor().scl(speed));
            if (getTransform().getPosition().dst(from) < speed / 8) {
                isComingBack = false;
            }
        } else {
            body.getBody().setLinearVelocity(new Vector2(to.x - from.x, to.y - from.y).nor().scl(speed));
            if (getTransform().getPosition().dst(to) < speed / 8) {
                isComingBack = true;
            }
        }
    }
}
