package fr.tommarx.ld37;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

import fr.tommarx.gameengine.Components.Transform;
import fr.tommarx.gameengine.Game.Game;

public class MapReader {

    public static Vector2 read(FileHandle file) {
        String content = file.readString();
        Vector2 size = new Vector2();
        for (int y = 0; y < content.split("\n").length; y++) {
            String line = content.split("\n")[y];
            size.y = content.split("\n").length * 32;
            for (int x = 0; x < line.split(",").length; x++) {
                size.x = line.split(",").length * 32;
                int object = Integer.parseInt(line.split(",")[x]);
                if (object == 1) {
                    Game.getCurrentScreen().add(new Wall(new Transform(new Vector2(x * 32 + 16, size.y - y * 32 - 16))));
                }
                if (object == 2) {
                    Game.getCurrentScreen().add(new Key(new Transform(new Vector2(x * 32 + 16, size.y - y * 32 - 16))));
                }
                if (object == 3) {
                    Game.getCurrentScreen().add(new Player(new Transform(new Vector2(x * 32 + 16, size.y - y * 32 - 16))));
                }
            }
        }
        return size;
    }

}
