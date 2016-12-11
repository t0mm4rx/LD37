package fr.tommarx.ld37.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.Game;
import fr.tommarx.ld37.ExitDoor;
import fr.tommarx.ld37.Key;
import fr.tommarx.ld37.Monsters.Trap;
import fr.tommarx.ld37.Monsters.Turret;
import fr.tommarx.ld37.Monsters.Zombie;
import fr.tommarx.ld37.Player;
import fr.tommarx.ld37.Wall;

public class MapReader {

    public static Vector2 read(FileHandle file) {
        ArrayList<Point> points = new ArrayList<Point>();
        String content = file.readString();

        String map = content.split("\n\n")[0];
        String mobs = content.split("\n\n")[1];
        Vector2 size = new Vector2();
        for (int y = 0; y < map.split("\n").length; y++) {
            String line = map.split("\n")[y];
            size.y = map.split("\n").length * 32;
            for (int x = 0; x < line.split(",").length; x++) {
                size.x = line.split(",").length * 32;

                if (isNumeric(line.split(",")[x])) {
                    int object = Integer.parseInt(line.split(",")[x]);
                    if (object == 1) {
                        Game.getCurrentScreen().add(new Wall(new Transform(new Vector2(x * 32 + 16, size.y - y * 32 - 16))));
                    }
                    if (object == 2) {
                        Game.getCurrentScreen().add(new Key(new Transform(new Vector2(x * 32 + 16, size.y - y * 32 - 16))));
                    }
                    if (object == 3) {
                        Game.getCurrentScreen().add(new Player(new Transform(new Vector2(x * 32 + 16, size.y - y * 32 - 16), new Vector2(1.5f, 1.5f), 0)));
                    }
                    if (object == 4) {
                        Game.getCurrentScreen().add(new ExitDoor(new Transform(new Vector2(x * 32 + 16, y * 32 - 48))));
                    }
                } else {
                    points.add(new Point(line.split(",")[x], x, size.y / 32 - y));
                }
            }
        }

        for (String mob : mobs.split("\n")) {
            String name = mob.split(":")[0];
            String props = mob.split(":")[1];
            if (name.equals("zombie")) {
                Game.getCurrentScreen().add(new Zombie(new Transform(getPoint(points, getPropertie(props, 0)).position), getPoint(points, getPropertie(props, 1)).position, 100, 1));
            }
            if (name.equals("zombie2")) {
                Game.getCurrentScreen().add(new Zombie(new Transform(getPoint(points, getPropertie(props, 0)).position), getPoint(points, getPropertie(props, 1)).position, 100, 2));
            }
            if (name.equals("zombie3")) {
                Game.getCurrentScreen().add(new Zombie(new Transform(getPoint(points, getPropertie(props, 0)).position), getPoint(points, getPropertie(props, 1)).position, 100, 2));
            }
            if (name.equals("turret")) {
                Game.getCurrentScreen().add(new Turret(new Transform(getPoint(points, getPropertie(props, 0)).position), Integer.parseInt(getPropertie(props, 1))));
            }
            if (name.equals("trap")) {
                Vector2 pos = getPoint(points, getPropertie(props, 0)).position;
                pos.x += 16;
                pos.y -= 16;
                Game.getCurrentScreen().add(new Trap(new Transform(pos), Float.parseFloat(getPropertie(props, 1))));
            }
        }


        return size;
    }

    public static boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

    public static Point getPoint(ArrayList<Point> points, String name) {
        for (Point p : points) {
            if (p.name.equals(name)) {
                return p;
            }
        }
        return null;
    }

    public static String getPropertie(String props, int id) {
        return props.split(",")[id];
    }

}
