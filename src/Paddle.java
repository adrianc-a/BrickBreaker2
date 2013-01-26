/**
 * Created with IntelliJ IDEA.
 * User: adrian
 * Date: 1/10/13
 * Time: 7:35 AM
 * Paddle Class
 * for BrickBreaker 3
 */
class Paddle {
    int x, y, width, height,windowWidth,windowHeight,origWidth;
    static final byte PADDLESTEP = 1;

    Paddle(int x, int y, int width, int height,int windowWidth,int windoHeight) {
        this.x = x;
        this.y = y;
        this.width = width;
        origWidth = width;
        this.height = height;
        this.windowHeight = windoHeight;
        this.windowWidth = windowWidth;
    }

    synchronized void move(int dir) {  //non-autopilot version of the
        if (dir == 0) {
            x -= PADDLESTEP;
        } else if (dir == 1) {
            x += PADDLESTEP;
        } else if (dir == 2) {
            y -= PADDLESTEP;
        } else if (dir == 3) {
            y += PADDLESTEP;
        }
        if (x + width/2 > windowWidth) {
            x = windowWidth - width/2;
        } else if (x + width/2 < 0) {
            x = -width/2;
        }
        if (y < 0) {
            y = windowHeight - height;
        } else if (y + height > windowHeight) {
            y = 0;
        }

    }

    protected synchronized void move(boolean algo, int ballX) { // This is basically an autopilot... 'a' toggles
        if (algo) {
            x = ballX - (width/2 - 10); // where 10 is ball.width/2;
        } else {
            return;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void resize(){
        width+=50;
    }

    public void original(){
        width = origWidth;
    }
}