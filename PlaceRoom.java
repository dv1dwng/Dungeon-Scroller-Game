package byog.Core;

import java.io.Serializable;
//import java.security.Key;
import java.util.ArrayList;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.HashMap;
import java.util.Random;

public class PlaceRoom implements Serializable {
    public static final int MINWIDTH = 5;
    public static final int MAXWIDTH = 15;
    public static final int MINHEIGHT = 5;
    public static final int MAXHEIGHT = 15;
    private static ArrayList<Room> roomlist = new ArrayList<>();
    private static ArrayList<ArrayList<Room>> roomArray = new ArrayList<>();
    private static ArrayList<Room> hallList = new ArrayList<>();
    private static final long serialVersionUID = 123123123123123L;

    public static void fillRoom(TETile[][] world, Room room) {
        for (int x = room.getX1(); x < room.getX2(); x += 1) {
            for (int y = room.getY1(); y < room.getY2(); y += 1) {
                world[x][y] = Tileset.WALL;
            }
        }
        for (int x = room.getX1() + 1; x < room.getX2() - 1; x += 1) {
            for (int y = room.getY1() + 1; y < room.getY2() - 1; y += 1) {
                world[x][y] = Tileset.FLOOR;
            }
        }

    }

    public static void placing(TETile[][] world, Random random) {
        int numroom = RandomUtils.uniform(random, 50, 200);
        for (int i = 0; i < numroom; i++) {
            int x = RandomUtils.uniform(random, Game.WIDTH);
            int y = RandomUtils.uniform(random, Game.HEIGHT - 4);
            int width = RandomUtils.uniform(random, MINWIDTH, MAXWIDTH);
            int height = RandomUtils.uniform(random, MINHEIGHT, MAXHEIGHT);
            boolean flag = true;
            Room room = new Room(x, y, width, height, Game.WIDTH, Game.HEIGHT - 4);
            for (Room r: roomlist) {
                if (room.intersects(r)) {
                    flag = false;
                    continue;
                }
            }
            if (flag) {
                fillRoom(world, room);
                roomlist.add(room);
            }
        }

    }

    //Now we want to remove both elements from roomArray to connect rooms from both arraylists
    //First we check if any of the removed array lists have more than one element
    //Second we want to randomly choose from those arraylists, so basically redo what we did above
    //Third, we use our corridor/hall connector methods to connect those rooms
    //Fourth, we combine both of the array together into one variable
    //Fifth, we add that back to roomArray
    //At the very end replace all

    public static void placeHalls(TETile[][] world, ArrayList<Room> rooms, Random random) {
        //Adds rooms to make a deep ArrayList called roomArray
        for (Room r: rooms) {
            ArrayList<Room> fatherTemp = new ArrayList<>();
            fatherTemp.add(r);
            roomArray.add(fatherTemp);
        }
        int counter = roomArray.size();
        //Starting to add halls
        while (counter > 1) {
            Room point1;
            Room point2;
            //This is to make sure we generate two distinct indices.
            int num1 = RandomUtils.uniform(random, roomArray.size());
            ArrayList<Room> temp1 = roomArray.remove(num1);
            int num2 = RandomUtils.uniform(random, roomArray.size());
            //These are the removed arrayList from roomArray
            //ArrayList<Room> temp1 = roomArray.remove(num1);
            ArrayList<Room> temp2 = roomArray.remove(num2);
            //These are the randomly chosen rooms for each of the temp arrayLists
            int itemnum1 = RandomUtils.uniform(random, temp1.size());
            point1 = temp1.get(itemnum1);
            int itemnum2 = RandomUtils.uniform(random, temp2.size());
            point2 = temp2.get(itemnum2);
            //Start connecting the hallways
            connect(world, point1, point2);
            //Starting to combine temp1 and temp2
            temp1.addAll(temp2);
            //add temp1 back to the roomArray
            roomArray.add(temp1);
            counter = roomArray.size();
        }

    }
    public static void connect(TETile[][] world, Room a, Room b) {
        int hStart = Math.min(a.getCenter().x(), b.getCenter().x());
        int hEnd = Math.max(a.getCenter().x(), b.getCenter().x());
        int hY = 0;
        int vStart = 0;
        int vEnd = 0;
        int vX = 0;
        //Setting up the Horizantal plane's variables
        if (a.getCenter().x() <= b.getCenter().x()) {
            hStart = a.getCenter().x();
            hEnd = b.getCenter().x() + 3;
            hY = a.getCenter().y();
        } else if (a.getCenter().x() > b.getCenter().x()) {
            hStart = b.getCenter().x();
            hEnd = a.getCenter().x() + 3;
            hY = b.getCenter().y();
        }
        //Setting up the Vertical plane's variables
        if (a.getCenter().y() <= b.getCenter().y()) {
            vStart = a.getCenter().y();
            vEnd = b.getCenter().y() + 3;
            vX = hEnd - 3;
        } else if (a.getCenter().y() > b.getCenter().y()) {
            vStart = b.getCenter().y();
            vEnd = a.getCenter().y() + 3;
            vX = hEnd - 3;
        }
        //Adding horizantal and vertical halls
        addHorizantalHall(world, hStart, hEnd, hY);
        addVerticalHall(world, vStart, vEnd, vX);

    }

    public static void addHorizantalHall(TETile[][] world, int x1, int x2, int y) {
        Room hHall = new Room(x1, y, x2 - x1, 3, Game.WIDTH, Game.HEIGHT);
        hallList.add(hHall);
        fillRoom(world, hHall);


    }

    public static void addVerticalHall(TETile[][] world, int y1, int y2, int x) {
        Room vHall = new Room(x, y1, 3, y2 - y1, Game.WIDTH, Game.HEIGHT);
        hallList.add(vHall);
        fillRoom(world, vHall);
    }

    public static void reDrawRoom(TETile[][] world, ArrayList<Room> rooms) {
        for (Room room: rooms) {
            for (int x = room.getX1() + 1; x < room.getX2() - 1; x += 1) {
                for (int y = room.getY1() + 1; y < room.getY2() - 1; y += 1) {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }

    }

    public static void placeAll(TETile[][] world, Random random) {
        placing(world, random);
        placeHalls(world, PlaceRoom.roomlist, random);
        reDrawRoom(world, PlaceRoom.roomlist);
        reDrawRoom(world, PlaceRoom.hallList);
    }

    public static TETile[][] storeWorld(TETile[][] world, long seed,
                                        HashMap<Long, TETile[][]> storage) {
        if (!storage.containsKey(seed)) {
            storage.put(seed, world);
        }
        return storage.get(seed);

    }

    public static void initializeWorld(TETile[][] world, int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static ArrayList<Room> getRoomlist() {
        return roomlist;
    }

    public static ArrayList<Room> getHallList() {
        return hallList;
    }





}
