package io.github.mtxrii.snake;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class Snake {

    // area of map covered by snake pixels
    private List<Pixel> snakeArea;

    // snake attributes
    private int xDir, yDir;         // direction facing
    private final Color color;      // color (doesnt change)
    private final int width;        // snake size (doesnt change)
    private final int startLength;  // starting length (doesnt change)
    private final int startX;       // starting x pos (doesnt change)
    private final int startY;       // starting y pos (doesnt change)
    private boolean isMoving;       // has game started?
    private boolean elongate;       // should snake increase?

    // constructor
    public Snake(Color color, int width, int length, int x, int y) {

        // fill in everything
        snakeArea = new ArrayList<>();
        this.color = color;
        this.width = width;
        startLength = length;
        startX = x;
        startY = y;
        xDir = 0;
        yDir = 0;
        isMoving = false;
        elongate = false;

        // make the snake
        snakeArea.add(new Pixel(startX, startY)); // head pixel
        for (int i = 1; i < startLength; i++) {   // body pixels
            snakeArea.add(new Pixel(startX - (i * width), startY));
        }

    }

    // places snake on map
    public void draw(Graphics g) {
        g.setColor(color);
        for (Pixel p : snakeArea) {
            g.fillRect(p.getX(), p.getY(), width, width);
        }
    }

    // getters & setters
    public int getxDir() {
        return xDir;
    }
    public void setxDir(int xDir) {
        this.xDir = xDir;
    }
    public int getyDir() {
        return yDir;
    }
    public void setyDir(int yDir) {
        this.yDir = yDir;
    }

    // gets head of snake
    public Pixel getHead() {
        return snakeArea.get(0);
    }

    // shifts over snake by one
    public void move() {

        if (!isMoving) return;

        Pixel first = snakeArea.get(0);
        Pixel last = snakeArea.get(snakeArea.size() - 1);

        // new front pixel
        Pixel newStart = new Pixel(first.getX() + (xDir * width),
                first.getY() + (yDir * width));

        // if wallsHurt is disabled, and if snake reaches wall, move to adjacent wall
        if (!Main.wallsHurt) {
            if (newStart.getX() < 0) newStart.setX(Main.SIZE - width);
            if (newStart.getX() > Main.SIZE - width) newStart.setX(0);
            if (newStart.getY() < 0) newStart.setY(Main.SIZE - width);
            if (newStart.getY() > Main.SIZE - width) newStart.setY(0);
        }

        // loop thru snake pixels pushing them all forward one
        for (int i = snakeArea.size() - 1; i >= 1; i--) {
            snakeArea.set(i, snakeArea.get(i - 1));
        }

        // set front pixel to new position
        snakeArea.set(0, newStart);

        // if token is collected, increase length
        if (elongate) {
            snakeArea.add(last);
            elongate = false;
        }
    }

    // checks if head of snake is touching its body anywhere
    public boolean snakeCollision() {
        int x = this.getHead().getX();
        int y = this.getHead().getY();

        // loop thru pixels in snake and check for matches w/ head
        for (int i = 1; i < snakeArea.size(); i++) {
            if (snakeArea.get(i).getX() == x && snakeArea.get(i).getY() == y)
                return true;
        }

        return false; // no collision
    }

    // checks if snake is in motion / game has started
    public boolean isMoving() {
        return isMoving;
    }

    // sets snake movement status
    public void setMoving(boolean b) {
        isMoving = b;
    }

    public void setElongate(boolean b) {
        elongate = b;
    }

}
