import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class Snake {
    private final int TILE_SIZE = 30;
    private LinkedList<int[]> body;
    private char direction = 'R'; // Right

    public Snake() {
        body = new LinkedList<>();
        body.add(new int[]{150, 150}); // Head
        body.add(new int[]{120, 150}); // Body segment
        body.add(new int[]{90, 150});  // Body segment
    }

    public void reset() {
        body.clear();
        body.add(new int[]{150, 150}); // Head
        body.add(new int[]{120, 150}); // Body segment
        body.add(new int[]{90, 150});  // Body segment
        direction = 'R';
    }

    public void move() {
        int[] newHead = new int[2];
        int headX = body.get(0)[0];
        int headY = body.get(0)[1];

        switch (direction) {
            case 'U': newHead[0] = headX; newHead[1] = headY - TILE_SIZE; break;  // Up
            case 'D': newHead[0] = headX; newHead[1] = headY + TILE_SIZE; break;  // Down
            case 'L': newHead[0] = headX - TILE_SIZE; newHead[1] = headY; break;  // Left
            case 'R': newHead[0] = headX + TILE_SIZE; newHead[1] = headY; break;  // Right
        }

        body.addFirst(newHead);
        body.removeLast();
    }

    public void draw(Graphics g) {
        // Draw the head of the snake (circle, yellow)
        int[] head = body.get(0);
        g.setColor(Color.yellow);
        g.fillOval(head[0], head[1], TILE_SIZE, TILE_SIZE);

        // Draw the body (curved segments, green)
        g.setColor(Color.green);
        for (int i = 1; i < body.size(); i++) {
            int[] segment = body.get(i);
            g.fillOval(segment[0], segment[1], TILE_SIZE, TILE_SIZE);
        }
    }

    public void handleKeyPress(int keyCode) {
        if (keyCode == java.awt.event.KeyEvent.VK_UP && direction != 'D') {
            direction = 'U';
        } else if (keyCode == java.awt.event.KeyEvent.VK_DOWN && direction != 'U') {
            direction = 'D';
        } else if (keyCode == java.awt.event.KeyEvent.VK_LEFT && direction != 'R') {
            direction = 'L';
        } else if (keyCode == java.awt.event.KeyEvent.VK_RIGHT && direction != 'L') {
            direction = 'R';
        }
    }

    public boolean checkCollision(int boardWidth, int boardHeight) {
        int[] head = body.get(0);
        // Check collision with walls
        if (head[0] < 0 || head[0] >= boardWidth || head[1] < 0 || head[1] >= boardHeight) {
            return true;
        }
        // Check collision with body
        for (int i = 1; i < body.size(); i++) {
            int[] segment = body.get(i);
            if (head[0] == segment[0] && head[1] == segment[1]) {
                return true;
            }
        }
        return false;
    }

    public boolean eatFood(Food food) {
        int[] head = body.get(0);
        if (head[0] == food.getX() && head[1] == food.getY()) {
            body.addLast(new int[]{food.getX(), food.getY()});  // Add new segment to the snake
            return true;
        }
        return false;
    }
}
