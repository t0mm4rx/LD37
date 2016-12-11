package fr.tommarx.ld37;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.AnimationManager;
import fr.tommarx.gameengine.Components.BoxBody;
import fr.tommarx.gameengine.Components.SpriteRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Easing.Tween;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.gameengine.Game.GameObject;
import fr.tommarx.gameengine.Util.Animation;

public class Player extends GameObject{

    BoxBody body;
    private final int ACCELERATION = 20, SPEED = 500, DECELERATION = 7, UP = 1, DOWN = 2, RIGHT = 3, LEFT = 4;
    private String[] images = new String[10];
    public int keys, life;
    private Vector2 knockback;
    private AnimationManager anim;
    int lastDirection;

    public Player(Transform transform) {
        super(transform);

        setTag("Player");
        setLayout(1);

        keys = 0;
        life = 10;

        body = new BoxBody(this, 16, 20, BodyDef.BodyType.DynamicBody);
        body.getBody().setFixedRotation(true);

        images[UP] = "sprites/player/up.png";
        images[DOWN] = "sprites/player/down.png";
        images[LEFT] = "sprites/player/left.png";
        images[RIGHT] = "sprites/player/right.png";

        //addComponent(new BoxRenderer(this, 32, 32, Color.WHITE));

        addComponent(new SpriteRenderer(this, Gdx.files.internal("sprites/player/down.png")));
        anim = new AnimationManager(this);
        anim.addAnimation(new Animation(this, new Texture(Gdx.files.internal("sprites/player/player_down.png")), 6, 1, .1f, true, DOWN));
        anim.addAnimation(new Animation(this, new Texture(Gdx.files.internal("sprites/player/player_up.png")), 3, 1, .1f, true, UP));
        anim.addAnimation(new Animation(this, new Texture(Gdx.files.internal("sprites/player/player_right.png")), 5 , 1, .1f, true, RIGHT));
        anim.addAnimation(new Animation(this, new Texture(Gdx.files.internal("sprites/player/player_left.png")), 5, 1, .1f, true, LEFT));
        //anim.setCurrentAnimation(1);
        lastDirection = DOWN;

        addComponent(anim);
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

        if (isWalking()) {
            if (anim.getCurrentAnimation() != getDirection()) {
                anim.setCurrentAnimation(getDirection());
            }
        } else {
            getSpriteRenderer().setTexture(new TextureRegion(new Texture(Gdx.files.internal(images[getDirection()]))));
        }

        Game.debug(4, "Direction : " + getDirection());
        Game.debug(5, "Walking : " + isWalking());

    }

    public int getDirection() {
        Vector2 speed = body.getBody().getLinearVelocity();
        if (speed.x < 1 && speed.x > -1 && speed.y < 1 && speed.y > -1) {
            return lastDirection;
        }
        if (distanceTo0(speed.x) > distanceTo0(speed.y)) {
            if (speed.x < 0) {
                lastDirection = LEFT;
                return LEFT;
            } else if(speed.x > 0) {
                lastDirection = RIGHT;
                return RIGHT;
            }
        }
        if (distanceTo0(speed.x) < distanceTo0(speed.y)) {
            if (speed.y < 0) {
                lastDirection = DOWN;
                return DOWN;
            } else if(speed.y > 0) {
                lastDirection = UP;
                return UP;
            }
        }
        return lastDirection;
    }

    public float distanceTo0(float x) {
        if (x < 0) {
            return -x;
        }
        return x;
    }

    public boolean isWalking() {
        Vector2 speed = body.getBody().getLinearVelocity();
        if ((speed.x > 1 || speed.x < -1) || (speed.y > 1 || speed.y < -1)) {
            return true;
        }
        return false;
    }

    public void hurt (int damages, Vector2 other, float knockback) {
        life -= damages;
        this.knockback = new Vector2(other.x - getTransform().getPosition().x, other.y - getTransform().getPosition().y).nor().scl(-knockback);
    }
}
