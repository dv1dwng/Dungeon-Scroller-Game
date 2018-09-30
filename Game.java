package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;


public class Game implements  Serializable {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 43;
    private static HashMap<Long, TETile[][]> storage = new HashMap<>();
    private static final long serialVersionUID = 45498236543734234L;



    /**
     * Method used for playing a fresh game.
     * The game should start from the main menu.
     */
    public void playWithKeyboard() {
        Interact.initialframe(WIDTH, HEIGHT);
        ArrayList<Room> roomlist = PlaceRoom.getRoomlist();
        ArrayList<Room> halllist = PlaceRoom.getHallList();
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        Player player1 = new Player(world, "Player", Tileset.PLAYER, WIDTH / 2, HEIGHT / 2, 0 , 0);
        boolean quitflag = false;
        boolean hudflag = false;
        boolean started = false;
        boolean gameover = false;
        boolean gamewon = false;
        String sSeed = "31432";
        long lSeed = 1L;
        TETile icon = Tileset.PLAYER;
        String name = "";
        String type = "";
        boolean cheatflag1 = false;
        boolean cheatflag2 = false;
        boolean cheatflag3 = false;
        boolean cheatflag4 = false;
        boolean cheatflag5 = false;
        boolean cheatflag6 = false;
        boolean cheatflag7 = false;
        int roomnum = 0;
        int doorX = 0;
        int doorY = 0;
        boolean doorunlock = false;
        while (!gameover && !gamewon) {
            while(!StdDraw.hasNextKeyTyped() && started) {
                int mouseX = (int) Math.floor(StdDraw.mouseX());
                int mouseY = (int) Math.floor(StdDraw.mouseY());
                if (doorunlock && player1.getX() == player1.doorX && player1.getY() == player1.doorY) {
                    gamewon = true;
                }
                if (player1.getHealth() == 0) {
                    gameover = true;
                }
                if (player1.getShards() == roomnum - 3) {
                    world[player1.doorX][player1.doorY] = Tileset.UNLOCKED_DOOR;
                    doorunlock = true;
                }
                updateHud(world, ter, player1, player1.getName(), player1.getDate(), sSeed, mouseX, mouseY);
            }
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                char k = Character.toLowerCase(c);
                switch (k) {
                    case 'n':
                        icon = Interact.charSelection();
                        name = Interact.nameInput();
                        sSeed = Interact.seedInput();
                        world = generatewWorld(sSeed, WIDTH, HEIGHT, lSeed);
                        roomlist = PlaceRoom.getRoomlist();
                        halllist = PlaceRoom.getHallList();
                        roomnum = roomlist.size();
                        doorX = roomlist.get(roomlist.size() - 2).getCenter().x();
                        doorY = roomlist.get(roomlist.size() - 2).getCenter().y();
                        player1 = new Player(world, name, icon,
                                roomlist.get(roomlist.size() - 1).getCenter().x(),
                                roomlist.get(roomlist.size() - 1).getCenter().y(), doorX, doorY);
                        placeDoor(world, player1.doorX, player1.doorY);
                        genHealth(world, roomlist, halllist, lSeed);
                        genMonster(world, halllist);
                        genShards(world, roomlist);
                        started = true;
                        break;
                    case 'l':
                        player1  = loadPlayer();
                        world = player1.playerworld;
                        roomnum = player1.roomlist.size();
                        started = true;
                        break;
                    case ':':
                        quitflag = true;
                        break;
                    case 'q':
                        if (quitflag) {
                            started = false;
                            savePlayer(player1);
                            System.exit(0);
                        } else {
                            System.exit(0);
                        }
                        break;
                    case 'w':
                        player1.up();
                        if (cheatflag6) {
                            cheatflag7 = true;
                        }
                        if (cheatflag1) {
                            cheatflag2 = true;
                        }
                        cheatflag1 = true;
                        break;
                    case 'a':
                        player1.left();
                        if (cheatflag2) {
                            cheatflag3 = true;
                        }
                        break;
                    case 's':
                        player1.down();
                        if (cheatflag3) {
                            cheatflag4 = true;
                        }
                        break;
                    case 'd':
                        player1.right();
                        if (cheatflag5) {
                            cheatflag6 = true;
                        }
                        if (cheatflag4) {
                            cheatflag5 = true;
                        }
                        break;
                    case '#':
                        if (cheatflag7) {
                            player1.changeSkin();
                            player1.changeHealth(9984);
                            cheatflag1 = false;
                            cheatflag2 = false;
                            cheatflag3 = false;
                            cheatflag4 = false;
                            cheatflag5 = false;
                            cheatflag6 = false;
                            cheatflag7 = false;
                        }
                    default:

                }
            }


        }
        if (gamewon) {
            Interact.winnerFrame(player1);
        } else {
            Interact.loserFrame(player1);
        }



    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the
     * saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        char[] inputArray = input.toCharArray();
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        Player player1 = new Player(finalWorldFrame, "Player", Tileset.PLAYER, 1, 1, 0 , 0);
        boolean quitflag = false;
        long lSeed = 1L;
        for (char e: inputArray) {
            char c = Character.toLowerCase(e);
            switch (c) {
                case 'n':
                    finalWorldFrame = generatewWorld(input, WIDTH, HEIGHT, lSeed);
                    ArrayList<Room> roomlist = PlaceRoom.getRoomlist();
                    player1 = new Player(finalWorldFrame, "Player", Tileset.PLAYER,
                            roomlist.get(1).getCenter().x(),
                            roomlist.get(1).getCenter().y(), 0 , 0);
                    break;
                case 'l':
                    player1  = loadPlayer();
                    finalWorldFrame = player1.playerworld;
                    quitflag = false;
                    break;
                case ':':
                    quitflag = true;
                    break;
                case 'q':
                    if (quitflag) {
                        savePlayer(player1);
                        //System.exit(0);
                    }
                    break;
                case 'w':
                    player1.up();
                    player1.addTurn();
                    break;
                case 'a':
                    player1.left();
                    player1.addTurn();
                    break;
                case 's':
                    player1.down();
                    player1.addTurn();
                    break;
                case 'd':
                    player1.right();
                    player1.addTurn();
                    break;
                default:
            }
        }


        //initializing the surrounding as black
        //PlaceRoom.initializeWorld(finalWorldFrame, WIDTH, HEIGHT);
        //actual world generation
        //PlaceRoom.placeAll(finalWorldFrame, random);
        //storing world so that if call the same world it will be the same
        //TETile[][] storedWorld = PlaceRoom.storeWorld(finalWorldFrame, seed, storage);

        return finalWorldFrame;

    }

    public static TETile[][] generatewWorld(String input, int width, int height, long seed) {
        String numbers = input.replaceAll("\\D+", "");
        seed = Long.valueOf(numbers);
        Random random = new Random(seed);
        TETile[][] world = new TETile[width][height];
        PlaceRoom.initializeWorld(world, width, height);
        PlaceRoom.placeAll(world, random);
        TETile[][] storedWorld = PlaceRoom.storeWorld(world, seed, storage);
        return storedWorld;


    }

    private static Player loadPlayer() {
        File f = new File("./player.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                Player player = (Player) os.readObject();
                os.close();
                return player;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }


        /* In the case no World has been saved yet, we return a new one. */
        return new Player(new TETile[WIDTH][HEIGHT], "Player", Tileset.PLAYER, 1, 2, 0 , 0);
    }
    private static void savePlayer(Player p) {
        File f = new File("./player.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(p);
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }


    public static void updateHud(TETile[][] world, TERenderer ter, Player player, String name, String date, String seed, int x, int y) {
        String type = "";
        /*for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                System.out.println(world[i][j].description());
            }
        } */
        if (x < WIDTH && y < HEIGHT) {
            if (world[x][y].equals(Tileset.FLOOR)) {
                type = Tileset.FLOOR.description();
            } else if (world[x][y].equals(Tileset.WALL)) {
                type = Tileset.WALL.description();

            } else if (world[x][y].equals(Tileset.NOTHING)) {
                type = Tileset.NOTHING.description();

            } else if (world[x][y].equals(player.skin)) {
                type = player.skin.description();

            } else if (world[x][y].equals(Tileset.HEALTH)) {
                type = Tileset.HEALTH.description();

            } else if (world[x][y].equals(Tileset.SHARD)) {
                type = Tileset.SHARD.description();

            } else if (world[x][y].equals(Tileset.TRAINER)) {
                type = Tileset.TRAINER.description();

            } else if (world[x][y].equals(Tileset.UNLOCKED_DOOR)) {
                type = Tileset.UNLOCKED_DOOR.description();

            } else if (world[x][y].equals(Tileset.LOCKED_DOOR)) {
                type = Tileset.LOCKED_DOOR.description();

            }
        }
        StdDraw.clear(Color.black);
        ter.renderFrame(world);
        Font bigFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.black);
        StdDraw.textLeft(0.0, HEIGHT - 1, "Type: " + type);
        StdDraw.text(WIDTH / 2, HEIGHT - 1, "Player:" + name);
        StdDraw.textRight(WIDTH, HEIGHT - 1, date);
        StdDraw.textLeft(0.0, HEIGHT - 3, "Shard: " + player.getShards());
        StdDraw.text(WIDTH / 2, HEIGHT - 3, "Seed: " + seed);
        StdDraw.textRight(WIDTH, HEIGHT - 3, "Health: " + player.getHealth());
        StdDraw.line(0, HEIGHT - 4, WIDTH, HEIGHT - 4);
        StdDraw.show();

    }

    public static void genShards(TETile[][] world, ArrayList<Room> roomlist) {
        for (int i = 0; i < roomlist.size() - 3; i++) {
            world[roomlist.get(i).getCenter().x()][roomlist.get(i).getCenter().y()] = Tileset.SHARD;
        }
    }
    public static void genHealth(TETile[][] world, ArrayList<Room> roomlist, ArrayList<Room> halllist, long seed) {
        Random random = new Random(seed);
        for (int i = 0; i < roomlist.size() / 2; i ++) {
            int x = RandomUtils.uniform(random, roomlist.get(i).getX1() + 1, roomlist.get(i).getX2() - 1);
            int y = RandomUtils.uniform(random, roomlist.get(i).getY1() + 1, roomlist.get(i).getY2() - 1);
            world[x][y] = Tileset.HEALTH;
        }
        for (int i = 0; i < halllist.size() / 4; i ++) {
            world[halllist.get(i).getCenter().x()][halllist.get(i).getCenter().y()] = Tileset.HEALTH;
        }

    }
    public static void genMonster(TETile[][] world, ArrayList<Room> halllist) {
        for (int i = halllist.size() / 4; i < halllist.size(); i++) {
            world[halllist.get(i).getCenter().x()][halllist.get(i).getCenter().y()] = Tileset.TRAINER;
        }

    }

    public static void placeDoor(TETile[][] world, int x, int y) {
        world[x][y] = Tileset.LOCKED_DOOR;

    }




}
