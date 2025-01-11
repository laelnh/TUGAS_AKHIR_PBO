public class Main {
    public static void main(String[] args) {
        javax.swing.JFrame frame = new javax.swing.JFrame();
        SnakeGame game = new SnakeGame();
        frame.setTitle("Snake Game");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setVisible(true);
    }
}
