import java.awt.Graphics;

public abstract class GameObject {
    protected int x, y;
    protected final int TILE_SIZE = 30;

    // Metode abstrak yang harus di-override oleh kelas turunan
    public abstract void draw(Graphics g);

    // Setter untuk x dan y
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
