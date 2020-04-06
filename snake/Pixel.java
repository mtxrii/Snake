package io.github.mtxrii.snake;

public final class Pixel {

    // coordinates
    private int x, y;

    // default constructor
    public Pixel() {
        x = 0;
        y = 0;
    }

    // custom constructor
    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // getters & setters
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

}
