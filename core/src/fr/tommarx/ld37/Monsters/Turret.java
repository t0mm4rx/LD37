package fr.tommarx.ld37.Monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.BoxBody;
import fr.tommarx.gameengine.Components.BoxRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.Game;

public class Turret extends Monster{

    double timeB;
    float fireRate;

    public Turret(Transform transform, float fireRate) {
        super(transform, 0, 1, 20);
        addComponent(new BoxRenderer(this, 16, 16, Color.WHITE));
        addComponent(new BoxBody(this, 16, 16, BodyDef.BodyType.StaticBody));
        this.fireRate = fireRate;
        timeB = System.currentTimeMillis();
    }

    protected void update(float delta) {
        if (System.currentTimeMillis() - timeB >= fireRate * 1000) {
            if (getTransform().getPosition().dst(Game.getCurrentScreen().getGameObjectByClass("Player").getTransform().getPosition()) < 300) {
                timeB = System.currentTimeMillis();
                Vector2 direction = new Vector2(
                        Game.getCurrentScreen().getGameObjectByTag("Player").getTransform().getPosition().x - getTransform().getPosition().x,
                        Game.getCurrentScreen().getGameObjectByTag("Player").getTransform().getPosition().y - getTransform().getPosition().y
                ).nor();
                Vector2 position = new Vector2(
                        getTransform().getPosition().x + direction.x * 16,
                        getTransform().getPosition().y + direction.y * 16
                );
                Game.getCurrentScreen().add(new Bullet(new Transform(position), Game.getCurrentScreen().getGameObjectByTag("Player").getTransform().getPosition()));
            }
        }
    }
}
