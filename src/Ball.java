/**
 * Created with IntelliJ IDEA.
 * User: adrian
 * Date: 1/9/13
 * Time: 11:15 AM
 * Ball Class
 */

class Ball {
    int windowWidth, windowHeight, SIZE;
    double theta, speed,x,y,oldY, oldXPaddle, oldYPaddle;
    static byte lives;

    Ball(int x, int y, double speed, int windowWidth, int windowHeight, double direction, int SIZE) {
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.theta = direction;
        this.SIZE = SIZE;
    }

    void move(){
        oldY = y;
        x = (x + speed * Math.cos(theta));
        y =  (y - speed * Math.sin(theta));
    }

    byte process(Paddle p, Block b) {
        oldXPaddle = p.getX();
        oldYPaddle = p.getY();
        double function = ((p.getX() + p.getWidth()/2.0) - getX() + SIZE/2.0)/ (0.5 * p.getWidth());
        if (y <= 0) {
            theta = -theta;
            speed = 1.0;
            return 1;
        } else if(y > oldY&& (x + SIZE  >= p.getX() && x  <= p.getWidth() + p.getX() && y + SIZE >= p.getY() && y + SIZE < p.getY() + p.getHeight())){
            theta = (Math.PI/4.0) * function + Math.PI/2.0;
            return 2;
        } else if (x <= 0 || x + SIZE >= windowWidth) {
            theta = Math.PI - theta;
            speed = 1.0;
            return 1;
        } else if (y + SIZE >= windowHeight) {
            x = 10;
            y = 80;
            theta = -Math.PI / 3.0;
            speed = 1.0;
            lives++;
        }  else{
                for (int i = 0; i < b.size(); i++) {   // testing whether or not ball hits the bricks

                    if ( b.get(i) !=null && x + SIZE >= b.get(i).getX() &&
                            x <= b.get(i).getX() + b.get(i).getWidth() &&
                            y <= b.get(i).getY() + b.get(i).getHeight() &&
                            y + SIZE >= b.get(i).getY()) {
                        theta = -theta;
                        System.out.println("HIT: " + i + " Speed: " + speed);
                        speed = 1.0;
                        return 3;

                    }

                }
            }
        return 0;
        }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getTheta() {
        return theta;
    }

    public int getThetaDegrees() {
        return (int) (theta * (180 / Math.PI));
    }

    public int getSIZE() {
        return SIZE;
    }

    public boolean isDead() {
        if (lives > 2)
            return true;
        return false;
    }

    public byte getLives(){
        return lives;
    }

    public void resetLives(){
        lives = 0;
    }
}