import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: adrian
 * Date: 1/9/13
 * Time: 6:09 PM
 * A Block is a lot of Bricks...
 * ArrayList of Bricks...
 * I was using Vectors because I was having some thread timing issues,
 * both the threads were accessing the same methods, that also explains "synchronized" as a method modifier
 */
class Block {
    ArrayList<Brick> blocks = new ArrayList<Brick>();    //replaced due to Thread timing issues
    //Vector<Brick> blocks = new Vector<Brick>();
    Brick b;
    int windowWidth, windowHeight,numBricks;
    byte widthOffset = 1,heightOffset = 10,initialHeight = 10;
    Random r = new Random();
    int rand,otherRand;

    public Block(int windowWidth,int windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        numBricks = windowWidth/(Brick.WIDTH + widthOffset);
        rand = r.nextInt(numBricks);
        otherRand = r.nextInt(numBricks);
//        rand = 2;
//        otherRand = 22;
        System.out.println("Rand:" + rand + " otherRand: " + otherRand);
        for (int i = 0; i < numBricks; i++) {
                blocks.add(i, b = new Brick(Color.blue, 1, i * (Brick.WIDTH + widthOffset), initialHeight));
//                blocks.add(i , b = new Brick(Color.GREEN, 1, i * (Brick.WIDTH + widthOffset), heightOffset+10 + Brick.HEIGHT));
//                blocks.add(i, b = new Brick(Color.red, 1, i * (Brick.WIDTH + widthOffset),heightOffset + 20 + (2*Brick.HEIGHT)));
//                blocks.add(i, b = new Brick(Color.orange, 1, i * (Brick.WIDTH + widthOffset),heightOffset + 30 + (3 * Brick.HEIGHT)));
        }
        blocks.get(rand).color = Color.black;
        blocks.get(otherRand).color = Color.magenta;
    }

    synchronized byte activate(Ball ball){
        for (int i = 0; i < size(); i++) {

            if (get(i) !=null &&  ball.getX() + ball.SIZE >= get(i).getX() &&
                    ball.getX() <= get(i).getX() + get(i).getWidth() &&
                    ball.getY() <= get(i).getY() + get(i).getHeight() &&
                    ball.getY() + ball.SIZE >= get(i).getY()) {
                remove(i);
                if(i  == rand){
                    return 2;
                } else if(i == otherRand){
                    return 3;
                }else{
                System.out.println("HIT: " + i + "SIZE: " + ball.SIZE + ":" + ball.getSIZE());
                return 1;
                }
            }

        }

        return 0;
    }



    public int size() {
        return blocks.size();
    }

    public Brick get(int i) {
        return blocks.get(i);
    }

    public void remove(int a) {
        blocks.set(a,null);
    }

    public boolean isEmpty() {
        for(int i = 0; i < blocks.size(); i++){
            if(blocks.get(i) != null){
                return false;
            }
        }
        return true;
    }

    public void clear(){
        blocks.clear();

    }
}