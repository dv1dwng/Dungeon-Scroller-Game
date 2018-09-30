package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Player implements Serializable {
    int x;
    int y;
    TETile skin;
    TETile[][] playerworld;
    int turn;
    String name;
    int health;
    int shards;
    ArrayList<Room> roomlist;
    int doorX;
    int doorY;
    private static final long serialVersionUID = 45498234798734234L;


    public Player(TETile[][] world, String name, TETile skin, int x, int y, int doorX, int doorY) {
        this.x = x;
        this.y = y;
        this.skin = skin;
        this.name = name;
        playerworld = world;
        playerworld[x][y] = skin;
        turn = 0;
        health = 15;
        shards = 0;
        roomlist = PlaceRoom.getRoomlist();
        this.doorX = doorX;
        this.doorY = doorY;



    }

    public void right() {
        if (!wallCheck(x + 1, y) && !doorCheck(x + 1, y)) {
            if (playerworld[x + 1][y].equals(Tileset.SHARD)) {
                shards += 1;
            }
            if (playerworld[x + 1][y].equals(Tileset.HEALTH)) {
                health += 1;
            }
            if (playerworld[x + 1][y].equals(Tileset.TRAINER)) {
                health -= 2;
            }
            playerworld[x][y] = Tileset.FLOOR;
            playerworld[x + 1][y] = skin;
            this.x = x + 1;
            turn += 1;

        }
    }
    public void left() {
        if (!wallCheck(x - 1, y) && !doorCheck(x - 1, y)) {
            if (playerworld[x - 1][y].equals(Tileset.SHARD)) {
                shards += 1;
            }
            if (playerworld[x - 1][y].equals(Tileset.HEALTH)) {
                health += 1;
            }
            if (playerworld[x - 1][y].equals(Tileset.TRAINER)) {
                health -= 2;
            }
            playerworld[x][y] = Tileset.FLOOR;
            playerworld[x - 1][y] = skin;
            this.x = x - 1;
            turn += 1;
        }

    }
    public void up() {
        if (!wallCheck(x, y + 1) && !doorCheck(x, y + 1)) {
            if (playerworld[x][y + 1].equals(Tileset.SHARD)) {
                shards += 1;
            }
            if (playerworld[x][y + 1].equals(Tileset.HEALTH)) {
                health += 1;
            }
            if (playerworld[x][y + 1].equals(Tileset.TRAINER)) {
                health -= 2;
            }
            playerworld[x][y] = Tileset.FLOOR;
            playerworld[x][y + 1] = skin;
            this.y = y + 1;
            turn += 1;
        }

    }
    public void down() {
        if (!wallCheck(x, y - 1) && !doorCheck(x, y - 1)) {
            if (playerworld[x][y - 1].equals(Tileset.SHARD)) {
                shards += 1;
            }
            if (playerworld[x][y - 1].equals(Tileset.HEALTH)) {
                health += 1;
            }
            if (playerworld[x][y - 1].equals(Tileset.TRAINER)) {
                health -= 2;
            }
            playerworld[x][y] = Tileset.FLOOR;
            playerworld[x][y - 1] = skin;
            this.y = y - 1;
            turn += 1;
        }

    }
    public boolean wallCheck(int i, int k) {
        return playerworld[i][k].equals(Tileset.WALL);
    }
    public boolean doorCheck(int i, int k) {
        return playerworld[i][k].equals(Tileset.LOCKED_DOOR);
    }

    public int addTurn() {
        return turn += 1;
    }
    public int getTurn() {
        return turn;
    }
    public static String getDate() {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date now = new Date();
        String strdate = date.format(now);
        return strdate;
    }

    public String getName() {
        return name;
    }
    public int getHealth() {
        return health;
    }

    public void changeHealth(int n) {
        health += n;
    }

    public void changeSkin() {
        skin = Tileset.CHEATCODE;
    }

    public int getShards() {
        return shards;
    }

    public void addShard() {
        shards += 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}

