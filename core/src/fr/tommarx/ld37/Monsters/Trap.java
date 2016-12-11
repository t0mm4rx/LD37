package fr.tommarx.ld37.Monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import fr.tommarx.gameengine.Components.BoxBody;
import fr.tommarx.gameengine.Components.BoxRenderer;
import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.GameObject;

public class Trap extends GameObject {

    BoxRenderer renderer;
    public boolean open;
    float duration;
    double timeB;

    public Trap(Transform transform, float duration) {
        super(transform);
        setTag("Trap");
        renderer = new BoxRenderer(this, 86, 32, Color.RED);
        addComponent(renderer);
        addComponent(new BoxBody(this, 86, 32, BodyDef.BodyType.KinematicBody, true));
        open = true;
        timeB = System.currentTimeMillis();
        setLayout(0);
        this.duration = duration;
    }

    protected void update(float delta) {
        if (System.currentTimeMillis() - timeB >= duration * 1000) {
            timeB = System.currentTimeMillis();
            change();
        }
    }

    private void change() {
        open = !open;
        if (open) {
            renderer.setColor(Color.RED);
        } else {
            renderer.setColor(Color.GREEN);
        }
    }
}
