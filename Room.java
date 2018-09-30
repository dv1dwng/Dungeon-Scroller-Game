package byog.Core;

import java.io.Serializable;

public class Room implements Serializable {
    //These are the coordinates of the room
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    //These should be the randomly generated Width and Height of the room
    private int w;
    private int h;
    private static final long serialVersionUID = 123123123123456L;


    //This is the center point
    private Position center;
    public Room(int x, int y, int width, int height, int worldwidth, int worldheight) {
        if (x < 0 || y < 0) {
            throw new RuntimeException("Can't have negative");
        }
        this.w = width;
        this.h = height;
        x1 = Math.min(x, worldwidth - width);
        x2 = Math.min(x + width, worldwidth);
        y1 = Math.min(y, worldheight - height);
        y2 = Math.min(y + height, worldheight);
        center = new Position(Math.round((x1 + x2) / 2), Math.round((y1 + y2) / 2));

    }
    public boolean intersects(Room room) {
        return (x1 <= room.x2) && (x2 >= room.x1)
                && (y1 <= room.y2) && (y2 >= room.y1);

    }
    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }
    public int getW() {
        return w;
    }
    public int getH() {
        return h;
    }
    public Position getCenter() {
        return center;
    }
}
