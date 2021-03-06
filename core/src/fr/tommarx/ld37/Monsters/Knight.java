package fr.tommarx.ld37.Monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.BoxRenderer;
import fr.tommarx.gameengine.Components.CircleBody;
import fr.tommarx.gameengine.Components.Transform;

public class Knight extends Monster {

    private Vector2 point1, point2, point3, point4;
    int currentDir;
    CircleBody body;

    public Knight(Transform transform, Vector2 target) {
        super(transform, 100, 20, 20);
        currentDir = 1;
        addComponent(new BoxRenderer(this, 32, 32, Color.BLACK));
        body = new CircleBody(this, 16, BodyDef.BodyType.DynamicBody);
        addComponent(body);
        point1 = transform.getPosition();
        point2 = new Vector2(target.x, point1.y);
        point3 = target;
        point4 = new Vector2(point1.x, target.y);
    }

    protected void update(float delta) {
        if (currentDir == 1) {
            body.getBody().setLinearVelocity(new Vector2(point2.x - point1.x, point2.y - point1.y).nor().scl(speed));
            if (getTransform().getPosition().dst(point2) < speed) {
                currentDir = 2;
            }
        }
        if (currentDir == 2) {
            body.getBody().setLinearVelocity(new Vector2(point3.x - point2.x, point3.y - point2.y).nor().scl(speed));
            if (getTransform().getPosition().dst(point3) < speed) {
                currentDir = 3;
            }
        }
        if (currentDir == 3) {
            body.getBody().setLinearVelocity(new Vector2(point4.x - point3.x, point4.y - point3.y).nor().scl(speed));
            if (getTransform().getPosition().dst(point4) < speed) {
                currentDir = 4;
            }
        }
        if (currentDir == 4) {
            body.getBody().setLinearVelocity(new Vector2(point1.x - point4.x, point1.y - point4.y).nor().scl(speed));
            if (getTransform().getPosition().dst(point1) < speed) {
                currentDir = 1;
            }
        }
    }
}