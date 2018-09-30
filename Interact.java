package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

public class Interact extends Game implements Serializable {
    static int  wMiddle = WIDTH / 2;
    static int hMiddle = HEIGHT / 2;
    static int step = HEIGHT - hMiddle;
    private static final long serialVersionUID = 45498234798734212L;

    public static void initialframe(int width, int height) {
        StdDraw.enableDoubleBuffering();
        int mWidth = width / 2;
        int mHeight = height / 2;
        int increment = height - mHeight;
        Font font1 =  new Font("Monaco", Font.BOLD, 30);
        StdDraw.setCanvasSize(width * 16, height * 16);
        StdDraw.clear(Color.black);


        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(mWidth, mHeight + 3, "New Game (N)");
        StdDraw.text(mWidth, mHeight, "Load Game (L)");
        StdDraw.text(mWidth, mHeight - 3, "Quit (Q)");
        StdDraw.setFont(font1);
        StdDraw.text(mWidth, height - increment / 2, "CS61B: The Game");
        StdDraw.show();


    }
    public static void seedFrame(String s) {

        StdDraw.clear();
        StdDraw.clear(Color.black);
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(wMiddle, HEIGHT - step / 2, "ENTER SEED:");
        StdDraw.text(wMiddle, hMiddle, s);
        StdDraw.show();
    }

    public static String seedInput() {
        String input = "";
        seedFrame(input);
        //char check = 'e';

        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            //check = key;
            if (key == 's' || key == 'S') {
                break;
            }
            input += String.valueOf(key);
            seedFrame(input);
        }
        StdDraw.pause(500);
        return input;
    }



    public static TETile charSelection() {
        TETile choice = Tileset.PLAYER;
        boolean start = false;
        StdDraw.clear(Color.black);
        Font bigFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(wMiddle, HEIGHT - 3 , "CHOOSE YOUR CHARACTER");
        StdDraw.text(wMiddle, HEIGHT - 5, "PICK A NUMBER:");
        StdDraw.text(14, HEIGHT - 9, "1");
        StdDraw.text(wMiddle, HEIGHT - 9, "2");
        StdDraw.text(WIDTH - 15, HEIGHT - 9, "3");
        StdDraw.text(14, 9, "Regice");
        StdDraw.text(wMiddle, 9, "Regirock");
        StdDraw.text(WIDTH - 15, 9, "Registeel");
        StdDraw.picture(15, HEIGHT / 2, "/byog/TileEngine/regiicebig.png");
        StdDraw.picture(WIDTH / 2, HEIGHT / 2, "/byog/TileEngine/regirockbig.png");
        StdDraw.picture(WIDTH - 15, HEIGHT / 2, "/byog/TileEngine/registeelbig.png");

        StdDraw.show();
        while (!start)
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (c == '1') {
                    start = false;
                    return Tileset.PLAYER1;
                }
                if (c == '2') {
                    start = false;
                    return Tileset.PLAYER2;
                }
                if (c == '3') {
                    start = false;
                    return Tileset.PLAYER3;
                }
            }
        return choice;
    }

    public static void nameFrame(String n) {
        StdDraw.clear();
        StdDraw.clear(Color.black);
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(wMiddle, HEIGHT - step / 2, "ENTER NAME:");
        StdDraw.text(wMiddle, step / 2, "PRESS THE # TO CONTINUE");
        StdDraw.text(wMiddle, hMiddle, n);
        StdDraw.show();
    }

    public static String nameInput() {
        String input = "";
        nameFrame(input);
        //char check = 'e';

        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            //check = key;
            if (key == '#') {
                break;
            }
            input += String.valueOf(key);

            nameFrame(input);
        }
        StdDraw.pause(500);
        return input;
    }

    public static void winnerFrame(Player player) {
        StdDraw.clear();
        StdDraw.clear(Color.black);
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(wMiddle, HEIGHT / 2, player.getName() + ", YOU WIN!!!");
        StdDraw.show();
    }

    public static void loserFrame(Player player) {
        StdDraw.clear();
        StdDraw.clear(Color.black);
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(wMiddle, HEIGHT / 2, player.getName() + ", You Lose...");
        StdDraw.show();
    }

}
