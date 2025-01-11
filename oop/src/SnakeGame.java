import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.sql.*;
import javax.sound.sampled.Clip;

public class SnakeGame extends JPanel {
    private final int BOARD_WIDTH = 800;
    private final int BOARD_HEIGHT = 600;
    private final int TILE_SIZE = 30;
    private Snake snake;
    private Food food;
    private int score;
    private boolean gameOver = false;
    private boolean gameStarted = false;
    private Timer timer;
    private String playerName = "";
    private Connection connection;

    private Clip startSound;
    private Clip eatSound;
    private Clip gameOverSound;

    public SnakeGame() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(Color.black); // Warna hitam sebagai latar
        setFocusable(true);

        snake = new Snake();
        food = new Food();

        // Load sounds
        startSound = SoundManager.loadSound("sounds/game-start-6104.wav");
        eatSound = SoundManager.loadSound("sounds/bruitage-bouton-v1-274125.wav");
        gameOverSound = SoundManager.loadSound("sounds/mixkit-sad-game-over-trombone-471.wav");

        // Membuka koneksi ke database
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/snake_game", "phpmyadmin", "67616E7431aelaax@");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Meminta input nama pemain
        String name = JOptionPane.showInputDialog(this, "Enter your name:");
        if (name != null && !name.trim().isEmpty()) {
            playerName = name;
        }

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!gameStarted) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        SoundManager.playSound(startSound);
                        startGame();
                    }
                } else {
                    if (gameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
                        restartGame();  // Restart the game when ENTER is pressed after game over
                    } else if (e.getKeyCode() == KeyEvent.VK_L) {
                        showLeaderboard();  // Show leaderboard when 'L' is pressed
                    } else {
                        snake.handleKeyPress(e.getKeyCode());
                    }
                }
            }
        });

        timer = new Timer(200, e -> gameLoop());
    }

    private void startGame() {
        gameStarted = true;
        score = 0;
        snake.reset();
        food.placeFood();
        timer.start();
    }

    private void restartGame() {
        gameOver = false;
        gameStarted = true;
        score = 0;
        snake.reset();
        food.placeFood();
        repaint();
    }

    private void gameLoop() {
        if (!gameOver) {
            snake.move();
            if (snake.checkCollision(BOARD_WIDTH, BOARD_HEIGHT)) {
                gameOver = true;
                SoundManager.playSound(gameOverSound);
                saveHighScore();
            }
            if (snake.eatFood(food)) {
                score++;
                SoundManager.playSound(eatSound);
                food.changeColor();
                food.placeFood();
            }
            repaint();
        }
    }

    private void saveHighScore() {
        try {
            String query = "INSERT INTO leaderboard (name, score) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, playerName);
            statement.setInt(2, score);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showLeaderboard() {
        String leaderboard = "Leaderboard:\n";
        try {
            String query = "SELECT * FROM leaderboard ORDER BY score DESC LIMIT 5";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                leaderboard += rs.getString("name") + ": " + rs.getInt("score") + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(this, leaderboard);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!gameStarted) {
            // Warna latar hitam polos tanpa grid
            g.setColor(Color.black);
            g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

            drawStartMessage(g); // Tampilkan pesan awal
        } else {
            // Gambar grid papan permainan saat permainan dimulai
            drawGrid(g);

            if (gameOver) {
                drawGameOverMessage(g);
            } else {
                drawGame(g);
            }
        }
    }

    private void drawGrid(Graphics g) {
        g.setColor(new Color(50, 50, 50)); // Abu tua
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(Color.black);
        for (int x = 0; x <= BOARD_WIDTH; x += TILE_SIZE) {
            g.drawLine(x, 0, x, BOARD_HEIGHT); // Garis vertikal
        }
        for (int y = 0; y <= BOARD_HEIGHT; y += TILE_SIZE) {
            g.drawLine(0, y, BOARD_WIDTH, y); // Garis horizontal
        }
    }

    private void drawStartMessage(Graphics g) {
        String startMessage = "Press Enter to Start";
        g.setColor(Color.white);
        g.setFont(g.getFont().deriveFont(20f));
        g.drawString(startMessage, (BOARD_WIDTH - g.getFontMetrics().stringWidth(startMessage)) / 2, BOARD_HEIGHT / 2);
    }

    private void drawGameOverMessage(Graphics g) {
        String gameOverMessage = "GAME OVER!";
        g.setColor(Color.red);
        g.setFont(g.getFont().deriveFont(48f)); // Ukuran font besar (48)
        g.drawString(gameOverMessage,
                (BOARD_WIDTH - g.getFontMetrics().stringWidth(gameOverMessage)) / 2,
                BOARD_HEIGHT / 2 - 40);

        g.setColor(Color.white);
        String restartMessage = "Press Enter to Restart";
        String leaderboardMessage = "Press L to view leaderboard";
        g.setFont(g.getFont().deriveFont(16f)); // Ukuran font kecil (16)
        g.drawString(restartMessage,
                (BOARD_WIDTH - g.getFontMetrics().stringWidth(restartMessage)) / 2,
                BOARD_HEIGHT / 2 + 20);
        g.drawString(leaderboardMessage,
                (BOARD_WIDTH - g.getFontMetrics().stringWidth(leaderboardMessage)) / 2,
                BOARD_HEIGHT / 2 + 50);
    }

    private void drawGame(Graphics g) {
        snake.draw(g);
        food.draw(g);
        g.setColor(Color.white);
        g.drawString("Score: " + score, 10, 20);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setVisible(true);
    }
}
