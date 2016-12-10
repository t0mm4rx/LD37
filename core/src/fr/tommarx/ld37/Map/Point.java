package fr.tommarx.ld37.Map;

import com.badlogic.gdx.math.Vector2;

public class Point {

    public Vector2 position;
    public String name;

    public Point(String name, float x, float y) {
        this.name = name;
        position = new Vector2(x * 32, y * 32);
    }

}
