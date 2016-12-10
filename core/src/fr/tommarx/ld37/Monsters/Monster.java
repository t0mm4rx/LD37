package fr.tommarx.ld37.Monsters;

import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.GameObject;

public abstract class Monster extends GameObject {

    public float speed, damages, knockback;

    public Monster(Transform transform, float speed, float damages, float knockback) {
        super(transform);
        this.speed = speed;
        this.damages = damages;
        this.knockback = knockback;
        setTag("Monster");
    }

}
