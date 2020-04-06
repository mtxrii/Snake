package io.github.mtxrii.snake;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class Main extends Applet implements Runnable, KeyListener {

    //------------------------------[ settings ]--------------------------------------
    static final int SIZE = 300;                        // dimensions of map
    private final Color BACKGROUND_COLOR = Color.BLACK; // map color
    private final Color SNAKE_COLOR = Color.WHITE;      // snake color
    static  final Color TOKEN_COLOR = Color.GREEN;      // token & text color
    static  final int SNAKE_SIZE = 4;                   // snake size
    private final int SNAKE_STARTING_LENGTH = 20;       // snake starting length
    private final int SNAKE_STARTING_XPOS = 150;        // snake starting x coordinate
    private final int SNAKE_STARTING_YPOS = 150;        // snake starting y coordinate
    private int GAME_SPEED = 40;                        // starting speed (in millisec)
    private int INCREASE_RATE = 2;                      // millisec speed-up every point
    //--------------------------------------------------------------------------------
    static final boolean wallsHurt = true;              // does hitting walls end game?
    // true - classic snake rules
    // false - snake doesn't die when hitting walls, instead wraps around screen
    //--------------------------------------------------------------------------------


    // holds visuals
    private Graphics gc;
    private Image img;

    // applet thread
    private Thread thread;

    // actual snake
    private Snake snake;

    // indicates when player loses
    private boolean gameOver;

    // references the token
    Token token;

    // Runnable's psvm
    public void init() {

        // prepare map
        this.resize(SIZE, SIZE);
        this.addKeyListener(this);
        this.gameOver = false;

        img = createImage(SIZE, SIZE);
        gc = img.getGraphics();
        snake = new Snake(SNAKE_COLOR,
                SNAKE_SIZE,
                SNAKE_STARTING_LENGTH,
                SNAKE_STARTING_XPOS,
                SNAKE_STARTING_YPOS);
        token = new Token(snake);
        thread = new Thread(this);
        thread.start();
    }

    // draws current frame
    public void paint(Graphics g) {
        gc.setColor(BACKGROUND_COLOR);
        gc.fillRect(0, 0, SIZE, SIZE);

        // if game is still going
        if (!gameOver) {
            // draw snake & token on frame
            snake.draw(gc);
            token.draw(gc);
        }

        // else draw end game stuff
        else {
            gc.setColor(TOKEN_COLOR);
            gc.drawString("GAME OVER", (SIZE / 2) - 40, (SIZE / 2) - 6);
            gc.drawString("Score: " + token.getScore(),
                    (SIZE / 2) - 40, (SIZE / 2) + 6); // put these in middle of screen
        }

        // update completed frame
        g.drawImage(img, 0, 0, null);
    }

    // draw new frame
    public void update(Graphics g) {
        paint(g);
    }

    // start game
    public void run() {

        // keep going till game ends
        while (!gameOver) {

            // moves snake into next position and checks if action will end game
            snake.move();
            this.checkGameOver();

            // also checks for snake hitting token
            if (token.snakeCollusion())
                // and speeds up game if true (to a max of 20)
                if (GAME_SPEED > 20)
                    GAME_SPEED = GAME_SPEED - INCREASE_RATE;


            // paints new frame
            paint(gc);

            // updates frame onto map
            this.repaint();

            // waits some time
            try {
                Thread.sleep(GAME_SPEED);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // checks if snake hit wall or self
    public void checkGameOver() {
        Pixel head = snake.getHead();

        // if snake goes out of bounds (and wall damage is on), end game
        if (wallsHurt) {
            if (head.getX() < 0 || head.getX() > SIZE - SNAKE_SIZE) gameOver = true;
            if (head.getY() < 0 || head.getY() > SIZE - SNAKE_SIZE) gameOver = true;
        }

        // if snake hits self, end game
        if (snake.snakeCollision()) gameOver = true;

    }

    // unused mandatory import
    @Override
    public void keyTyped(KeyEvent e) {

    }

    // changes direction of snake when in motion
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (!snake.isMoving()) snake.setMoving(true);
            if (snake.getyDir() != 1) {
                snake.setyDir(-1);
                snake.setxDir(0);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!snake.isMoving()) snake.setMoving(true);
            if (snake.getyDir() != -1) {
                snake.setyDir(1);
                snake.setxDir(0);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (snake.getxDir() != 1) {
                snake.setxDir(-1);
                snake.setyDir(0);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (!snake.isMoving()) snake.setMoving(true);
            if (snake.getxDir() != -1) {
                snake.setxDir(1);
                snake.setyDir(0);
            }
        }
    }

    // unused mandatory import
    @Override
    public void keyReleased(KeyEvent e) {

    }

    // creates game
    public static void main(String[] args) {
        new Main();
    }
}
