package byog.Core;
import java.io.Serializable;


public class Position implements Serializable {
    private int x;
    private int y;
    private static final long serialVersionUID = 454982347987875634L;


    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int x() {
        return x;
    }
    public int y() {
        return y;
    }

}
