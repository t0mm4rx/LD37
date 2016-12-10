package fr.tommarx.ld37;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.BoxBody;
import fr.tommarx.gameengine.Components.BoxRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.GameObject;

public class Player extends GameObject{

    BoxBody body;
    private final int ACCELERATION = 20, SPEED = 300, DECELERATION = 7;

    public Player(Transform transform) {
        super(transform);

        body = new BoxBody(this, 32, 32, BodyDef.BodyType.DynamicBody);

        addComponent(new BoxRenderer(this, 32, 32, new Color(1, 1, 1, 1)));
        addComponent(body);
    }

    protected void update(float delta) {

        Game.debug(3, "VelX : " + body.getBody().getLinearVelocity().x + " && VelY : " + body.getBody().getLinearVelocity().y);

        //Decreasing speed
        if (body.getBody().getLinearVelocity().x < 0) {
            body.getBody().setLinearVelocity(body.getBody().getLinearVelocity().x + DECELERATION, body.getBody().getLinearVelocity().y);
        }
        if (body.getBody().getLinearVelocity().x > 0) {
            body.getBody().setLinearVelocity(body.getBody().getLinearVelocity().x - DECELERATION, body.getBody().getLinearVelocity().y);
        }
        if (body.getBody().getLinearVelocity().y < 0) {
            body.getBody().setLinearVelocity(body.getBody().getLinearVelocity().x, body.getBody().getLinearVelocity().y + DECELERATION);
        }
        if (body.getBody().getLinearVelocity().y > 0) {
            body.getBody().setLinearVelocity(body.getBody().getLinearVelocity().x, body.getBody().getLinearVelocity().y - DECELERATION);
        }

        //Key listening, moving the player on key pressed
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (body.getBody().getLinearVelocity().y < SPEED) {
                body.getBody().setLinearVelocity(new Vector2(body.getBody().getLinearVelocity().x, body.getBody().getLinearVelocity().y + ACCELERATION));
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (body.getBody().getLinearVelocity().y > -SPEED) {
                body.getBody().setLinearVelocity(new Vector2(body.getBody().getLinearVelocity().x, body.getBody().getLinearVelocity().y - ACCELERATION));
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (body.getBody().getLinearVelocity().x > -SPEED) {
                body.getBody().setLinearVelocity(new Vector2(body.getBody().getLinearVelocity().x - ACCELERATION, body.getBody().getLinearVelocity().y));
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (body.getBody().getLinearVelocity().x < SPEED) {
                body.getBody().setLinearVelocity(new Vector2(body.getBody().getLinearVelocity().x + ACCELERATION, body.getBody().getLinearVelocity().y));
            }
        }

        //If velocity is very weak, set it to 0. If not, it will be moving slowly in the opposite direction
        if (body.getBody().getLinearVelocity().x < DECELERATION && body.getBody().getLinearVelocity().x > -DECELERATION) {
            body.getBody().setLinearVelocity(new Vector2(0, body.getBody().getLinearVelocity().y));
        }

        if (body.getBody().getLinearVelocity().y < DECELERATION && body.getBody().getLinearVelocity().y > -DECELERATION) {
            body.getBody().setLinearVelocity(new Vector2(body.getBody().getLinearVelocity().x, 0));
        }

    }
}
