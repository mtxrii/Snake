package io.github.mtxrii.snake;

import java.awt.*;

public final class Token {

    // coordinates, score tally & snake pointer
    private int x, y, score;
    private Snake snake;

    // constructor, places token at random point
    public Token(Snake s) {
        x = (int) (Math.random() * (Main.SIZE - 5));
        y = (int) (Math.random() * (Main.SIZE - 5));
        snake = s;
    }

    // moves token to new random point
    public void changePosition() {
        x = (int) (Math.random() * (Main.SIZE - 5));
        y = (int) (Math.random() * (Main.SIZE - 5));
    }

    // getter
    public int getScore() {
        return score;
    }

    // paints token on frame
    public void draw(Graphics g) {
        g.setColor(Main.TOKEN_COLOR);
        g.fillRect(x, y, 8, 8);
    }

    // checks if snake is touching the token
    public boolean snakeCollusion() {
        int snakeX = snake.getHead().getX() + (Main.SNAKE_SIZE / 2);
        int snakeY = snake.getHead().getY() + (Main.SNAKE_SIZE / 2);

        if (snakeX >= x-1 && snakeX <= (x + 9))
            if (snakeY >= y-1 && snakeY <= (y + 9)) {
                changePosition();
                score++;
                snake.setElongate(true);
                return true;
            }
        return false;
    }
}
