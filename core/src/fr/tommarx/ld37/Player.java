package fr.tommarx.ld37;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.BoxBody;
import fr.tommarx.gameengine.Components.BoxRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Easing.Tween;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.GameObject;

public class Player extends GameObject{

    BoxBody body;
    private final int ACCELERATION = 20, SPEED = 300, DECELERATION = 7;
    public int keys, life;
    private Vector2 knockback;

    public Player(Transform transform) {
        super(transform);

        setTag("Player");
        setLayout(1);

        keys = 0;
        life = 10;

        body = new BoxBody(this, 32, 32, BodyDef.BodyType.DynamicBody);
        body.getBody().setFixedRotation(true);

        addComponent(new BoxRenderer(this, 32, 32, new Color(1, 1, 1, 1)));
        addComponent(body);
    }

    protected void update(float delta) {

        //Game.debug(3, "VelX : " + body.getBody().getLinearVelocity().x + " && VelY : " + body.getBody().getLinearVelocity().y);

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

        //Zoom when M is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            if (Game.tweenManager.getValue("CameraZoom") == 0f) {
                Game.tweenManager.goTween(new Tween("CameraZoom", Tween.CUBE_EASE_INOUT, 0, 1, 1f, 0f, false));
            }
        }
        //Dezoom if M is not pressed
        if (!Gdx.input.isKeyPressed(Input.Keys.M)) {
            if (Game.tweenManager.getValue("CameraZoom") == 1f) {
                Game.tweenManager.goTween(new Tween("CameraZoom", Tween.CUBE_EASE_INOUT, 1, -1, 1f, .1f, false));
            }
        }

        //Knockback
        if (knockback != null) {
            body.getBody().setTransform(new Vector2(
                    getTransform().getPosition().x + knockback.x,
                    getTransform().getPosition().y + knockback.y
            ), 0);
            if (knockback.x > 0) {
                knockback.x -= DECELERATION / 2;
            }
            if (knockback.x < 0) {
                knockback.x += DECELERATION / 2;
            }
            if (knockback.y > 0) {
                knockback.y -= DECELERATION / 2;
            }
            if (knockback.y < 0) {
                knockback.y += DECELERATION / 2;
            }
            if (knockback.x < DECELERATION && knockback.x > -DECELERATION) {
                knockback.x = 0;
            }
            if (knockback.y < DECELERATION && knockback.y > -DECELERATION) {
                knockback.y = 0;
            }
            if (knockback.x == 0 || knockback.y == 0) {
                knockback = null;
            }
        }

        //Setting the camera centered on the player
        if (getTransform().getPosition().x - Gdx.graphics.getWidth() / 2 > 0 && getTransform().getPosition().x + Gdx.graphics.getWidth() / 2 < ((GameScreen)Game.getCurrentScreen()).width) {
            Game.getCurrentScreen().camera.position.set(getTransform().getPosition().x, Game.getCurrentScreen().camera.position.y, 0);
        }
        if (getTransform().getPosition().y - Gdx.graphics.getHeight() / 2 > 0 && getTransform().getPosition().y + Gdx.graphics.getHeight() / 2 < ((GameScreen)Game.getCurrentScreen()).height) {
            Game.getCurrentScreen().camera.position.set(Game.getCurrentScreen().camera.position.x, getTransform().getPosition().y, 0);
        }

        //Set the zoom with the tween 'CameraZoom'
        Game.getCurrentScreen().camera.zoom = Game.tweenManager.getValue("CameraZoom") + 1;

        if (life <= 0) {
            ((GameScreen)Game.getCurrentScreen()).die();
        }

    }

    public void hurt (int damages, Vector2 other, float knockback) {
        life -= damages;
        this.knockback = new Vector2(other.x - getTransform().getPosition().x, other.y - getTransform().getPosition().y).nor().scl(-knockback);
    }
}
