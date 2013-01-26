import javax.swing.*;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: adrian
 * Date: 1/9/13
 * Time: 12:56 PM
 * Main Applet here...
 * Note: I have an Object which is an ArrayList of Brick Objects, thinking it would be easier to say "Block b = new Block()"
 * as opposed to having to initialize the ArrayList in the main applet, unfortunately, foreach loops don't work that way
 * the loops are still for loops
 */
public class BrickBreaker extends JApplet implements Runnable, KeyListener {
    Thread t;
    int timeStep = 3;
    static final short SIZE = 10;
    static final int WIDTH = 600,HEIGHT = 800;
    Ball ball, ball2;
    Paddle paddle;
    Block block;
    boolean start = true;
    boolean algo = false;
    boolean multiBall = false;
    static final long STARTTIME = System.currentTimeMillis();
    int score = 0;
    int activeKey = 0;
    int numOfBricks = 0;
    AudioClip brickBroke,wallBounce,paddleHit;
    byte ballProcess, blockActivate, ball2Process,block2Activate;

    public void init() {
        resize(WIDTH, HEIGHT);
        ball = new Ball(10, 80, 1.0, WIDTH, HEIGHT, -Math.PI / 3, SIZE);
        block = new Block(WIDTH,HEIGHT);
        paddle = new Paddle(250, 500, 100, 10,WIDTH,HEIGHT);
        numOfBricks = block.size();
        URL location1 = getClass().getClassLoader().getResource("tennis02.aiff");
        wallBounce = getAudioClip(location1);
        brickBroke = getAudioClip(getClass().getClassLoader().getResource("tennis_volley.aiff"));
        paddleHit = getAudioClip(getClass().getClassLoader().getResource("tennis01.aiff"));
        t = new Thread(this);
        addKeyListener(this);
    }

    public void paint(Graphics g) {
        g.clearRect(0, 0, WIDTH * 2, HEIGHT * 2);
        if (block.isEmpty()) {
            g.drawString("YOU WIN", WIDTH/2, HEIGHT/2);
        }
        if (ball.isDead()) {
            g.drawString("YOU LOSE", WIDTH/2, HEIGHT/2);
            wallBounce.loop();
        }
        if (ball.isDead() || block.isEmpty()) {
            g.drawString("SCORE: " + score, WIDTH/2, HEIGHT/2 + 50);
        }
        if (start) {
            g.drawString("Click the Mouse, then the Space Bar to start, arrow keys to control the paddle:", 50, 300);
            g.drawString("'r' to restart, black colored bricks give you multiple balls",215,325);
            g.drawString("Written by: Adrian Chmielewski-Anders",100,350);

        }
        g.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
        g.fillOval((int) ball.getX(), (int) ball.getY(), SIZE, SIZE);
        if(multiBall){
            g.fillOval((int) ball2.getX(), (int) ball2.getY(), SIZE, SIZE);
        }
        showStatus("Lives left: " + (3 - ball.getLives()));
        for (int i = 0; i < block.size(); i++) {
            if(block.get(i) != null){
                g.setColor(block.get(i).color);
                g.fill3DRect(block.get(i).getX(), block.get(i).getY(), block.get(i).getWidth(), block.get(i).getHeight(), true);
            }
        }

    }

    public void run() {
        try {
            while (!ball.isDead()) {
                ballProcess = ball.process(paddle,block);
                blockActivate = block.activate(ball);

                if(multiBall){
                    block2Activate = block.activate(ball2);
                    ball2Process = ball2.process(paddle,block);
                    ball2.move();
                    if(ball2Process == 1){
                        wallBounce.play();
                    } else if(ball2Process == 2){
                        paddleHit.play();
                    }
                    if(block2Activate == 1){
                        brickBroke.play();
                    }
                }
                if(ballProcess == 1 ){
                    wallBounce.play();
                } else if(ballProcess == 2){
                    paddleHit.play();
                }
                if (block.isEmpty()) {
                    break;
                }
                if (algo) {
                    paddle.move(algo, (int) ball.getX());
                }
                if(blockActivate == 1){
                    brickBroke.play();
                }
                if(blockActivate == 2){
                    System.out.println("ball2 Created");
                    if(!multiBall){
                        ball2 = new Ball(paddle.getX() + paddle.getWidth()/2 - ball.SIZE/2,paddle.getY()-ball.SIZE,1.0,WIDTH,HEIGHT,Math.PI/3.0,SIZE);
                        System.out.println("HERE");
                    }
                    multiBall = true;
                } else if(blockActivate == 3){
                    paddle.resize();
                }
                if (ball.isDead() || block.isEmpty()) {
                    totalScore();
                }
                if (activeKey == KeyEvent.VK_LEFT) {
                    paddle.move(0);
                } else if (activeKey == KeyEvent.VK_RIGHT) {
                    paddle.move(1);
                } else if (activeKey == KeyEvent.VK_UP) {
                    paddle.move(2);
                } else if (activeKey == KeyEvent.VK_DOWN) {
                    paddle.move(3);
                }
                 ball.move();
                repaint();
                t.sleep(timeStep);
            }
            System.out.println("Thread has ended");
        } catch (InterruptedException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    private void totalScore() throws Exception {
        PrintStream out = new PrintStream(new FileOutputStream(new File(new File(System.getProperty("user.home")), "HighScores2.txt"), true));
        final long ENDTIME = System.currentTimeMillis();
        if (ball.isDead() || block.isEmpty()) {
            score += (((numOfBricks - block.size()) * 10000) - (ENDTIME - STARTTIME));
        }
        if(ball.isDead() || block.isEmpty())
        out.println("Varun: " + score);
        out.close();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        if (key == keyEvent.VK_A) {
            algo = !algo;
        }
        switch (key) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                activeKey = key;
                break;
            case KeyEvent.VK_SPACE:
                t.start();
                start = false;
                break;
            case KeyEvent.VK_R:
                restartGame();
                break;
            default:
                break;
        }
    }

    private void restartGame(){
        if(t.isAlive()){
            return;
        }
        wallBounce.stop();
        multiBall = false;
        ball.resetLives();
        paddle.original();
        block.clear();
        block = new Block(WIDTH,HEIGHT);
        ball = new Ball(paddle.getX() + paddle.getWidth()/2 - ball.SIZE/2,paddle.getY()-ball.SIZE,1.0,WIDTH,HEIGHT,Math.PI/3.0,SIZE);
        t = new Thread(this);
        t.start();


    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() != KeyEvent.VK_SPACE) {
            activeKey = 0;
        }
    }
}