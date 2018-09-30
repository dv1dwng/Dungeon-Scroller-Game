package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class GameLauncher {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 43;
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        Random random = new Random(1231231);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Room a = new Room(10, 10, 10, 5, WIDTH, HEIGHT);
        Room b = new Room(30, 30, 15, 10, WIDTH, HEIGHT);
        PlaceRoom.fillRoom(world, a);
        PlaceRoom.fillRoom(world, b);
        PlaceRoom.connect(world, a, b);
        //PlaceRoom.connect(world, b, a);
        //PlaceRoom.placing(world, random);
        //PlaceRoom.addVerticalHall(world, 0,20, 5);
        //PlaceRoom.addHorizantalHall(world, 10, 40, 10);



        ter.renderFrame1(world);
    }
}
