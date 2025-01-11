import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Food extends GameObject {
    private Color color; // Warna makanan
    private int size; // Ukuran makanan
    private boolean growing; // Arah perubahan ukuran (mengembang/kempis)
    private final int MIN_SIZE = TILE_SIZE - 5; // Ukuran minimum makanan
    private final int MAX_SIZE = TILE_SIZE + 5; // Ukuran maksimum makanan

    public Food() {
        placeFood(); // Tempatkan makanan di posisi awal
        changeColor(); // Tetapkan warna awal
        size = TILE_SIZE; // Ukuran default
        growing = true; // Animasi dimulai dengan mengembang
    }

    public void placeFood() {
        Random rand = new Random();
        x = rand.nextInt(20) * TILE_SIZE; // Posisi acak di grid horizontal
        y = rand.nextInt(13) * TILE_SIZE; // Posisi acak di grid vertikal
    }

    public void changeColor() {
        Random rand = new Random();
        int r = rand.nextInt(156) + 100; // Warna cerah antara 100-255
        int g = rand.nextInt(156) + 100;
        int b = rand.nextInt(156) + 100;
        color = new Color(r, g, b);
    }

    public void animate() {
        // Animasi ukuran makanan
        if (growing) {
            size++;
            if (size >= MAX_SIZE) growing = false; // Berhenti mengembang
        } else {
            size--;
            if (size <= MIN_SIZE) growing = true; // Berhenti mengecil
        }
    }

    @Override
    public void draw(Graphics g) {
        animate(); // Jalankan animasi
        g.setColor(color != null ? color : Color.RED); // Gunakan warna atau default merah
        g.fillOval(x + (TILE_SIZE - size) / 2, y + (TILE_SIZE - size) / 2, size, size); // Gambar lingkaran animasi
    }
}