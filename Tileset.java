package byog.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile TRAINER = new TETile('*', Color.white, Color.black, "Trainer",
            "/byog/TileEngine/poketrainer.png");
    public static final TETile PLAYER = new TETile('@', Color.white, Color.black, "Player");

    public static final TETile PLAYER1 = new TETile('k', Color.white, Color.black, "Regice",
            "/byog/TileEngine/regiback.png");
    public static final TETile PLAYER2 = new TETile('l', Color.white, Color.black, "Regrock",
            "/byog/TileEngine/regirock.png");
    public static final TETile PLAYER3 = new TETile('m', Color.white, Color.black, "Registeel",
            "/byog/TileEngine/fixedregisteel.png");
    public static final TETile CHEATCODE = new TETile('q', Color.white, Color.black, "Regigigas",
            "/byog/TileEngine/regigigas.png");
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "Wall", "/byog/TileEngine/pokewall.png");

    public static final TETile OWALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "Wall");
    public static final TETile FLOOR = new TETile('.', new Color(128, 192, 128), Color.black,
            "Floor", "/byog/TileEngine/pokefloor.png");
    public static final TETile OFLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "Floor");
    public static final TETile NOTHING1 = new TETile(' ', Color.black, Color.black, "Nothing");
    public static final TETile NOTHING = new TETile('z', Color.black, Color.black, "Background",
            "/byog/TileEngine/background.png");
    public static final TETile HEALTH = new TETile('"', Color.green, Color.black, "Potion",
            "/byog/TileEngine/pokehealth.png");
    public static final TETile SHARD = new TETile('≈', Color.blue, Color.black, "Shard",
            "/byog/TileEngine/pokeshard.png");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "Flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "Locked Area");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "Ladder", "/byog/TileEngine/pokeladder.png");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "Sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "Mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "Tree");

}


