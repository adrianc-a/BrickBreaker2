import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: adrian
 * Date: 1/9/13
 * Time: 5:48 PM
 * Brick
 */
class Brick {
    Color color;
    int rank, x, y;
    static int WIDTH = 20,HEIGHT = 10;
    Brick(Color color, int rank, int x, int y) {
        this.color = color;
        this.rank = rank;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRank() {
        return rank;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
}