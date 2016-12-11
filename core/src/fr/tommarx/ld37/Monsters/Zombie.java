package fr.tommarx.ld37.Monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.AnimationManager;
import fr.tommarx.gameengine.Components.BoxRenderer;
import fr.tommarx.gameengine.Components.CircleBody;
import fr.tommarx.gameengine.Components.SpriteRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Util.Animation;

public class Zombie extends Monster {

    private Vector2 from, to, target;
    private CircleBody body;
    private final int UP = 1, DOWN = 2, RIGHT = 3, LEFT = 4;
    private AnimationManager anim;

    public Zombie(Transform transform, Vector2 target, float speed, float damages) {
        super(transform, speed, damages, 20);
        from = transform.getPosition();
        to = target;
        //addComponent(new BoxRenderer(this, 32, 32, Color.BLACK));
        body = new CircleBody(this, 10, BodyDef.BodyType.DynamicBody);
        body.getBody().setFixedRotation(true);
        addComponent(body);

        addComponent(new SpriteRenderer(this, Gdx.files.internal("empty32x32.png")));

        anim = new AnimationManager(this);
        if (damages < 2) {
            anim.addAnimation(new Animation(this, new Texture(Gdx.files.internal("sprites/zombie/walk_down.png")), 4, 1, .1f, true, DOWN));
            anim.addAnimation(new Animation(this, new Texture(Gdx.files.internal("sprites/zombie/walk_up.png")), 3, 1, .1f, true, UP));
            anim.addAnimation(new Animation(this, new Texture(Gdx.files.internal("sprites/zombie/walk_right.png")), 3, 1, .1f, true, RIGHT));
            anim.addAnimation(new Animation(this, new Texture(Gdx.files.internal("sprites/zombie/walk_left.png")), 3, 1, .1f, true, LEFT));
        } else {
            anim.addAnimation(new Animation(this, new Texture(Gdx.files.internal("sprites/zombie2/walk_down.png")), 3, 1, .1f, true, DOWN));
            anim.addAnimation(new Animation(this, new Texture(Gdx.files.internal("sprites/zombie2/walk_up.png")), 3, 1, .1f, true, UP));
            anim.addAnimation(new Animation(this, new Texture(Gdx.files.internal("sprites/zombie2/walk_right.png")), 3, 1, .1f, true, RIGHT));
            anim.addAnimation(new Animation(this, new Texture(Gdx.files.internal("sprites/zombie2/walk_left.png")), 3, 1, .1f, true, LEFT));
        }

        addComponent(anim);

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

        if (anim.getCurrentAnimation() != getDirection()) {
            anim.setCurrentAnimation(getDirection());
        }
    }

    private void changeDirection() {
        if (target == to) {
            target = from;
        } else {
            target = to;
        }
    }

    public int getDirection() {
        Vector2 speed = body.getBody().getLinearVelocity();
        if (speed.x < 1 && speed.x > -1 && speed.y < 1 && speed.y > -1) {
        }
        if (distanceTo0(speed.x) > distanceTo0(speed.y)) {
            if (speed.x < 0) {
                return LEFT;
            } else if(speed.x > 0) {
                return RIGHT;
            }
        }
        if (distanceTo0(speed.x) < distanceTo0(speed.y)) {
            if (speed.y < 0) {
                return DOWN;
            } else if(speed.y > 0) {
                return UP;
            }
        }
        return 0;
    }

    public float distanceTo0(float x) {
        if (x < 0) {
            return -x;
        }
        return x;
    }

}
