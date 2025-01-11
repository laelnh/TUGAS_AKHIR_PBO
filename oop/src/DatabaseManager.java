import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/snake_game";
    private static final String USER = "phpmyadmin"; // Ganti dengan user Anda
    private static final String PASSWORD = "67616E7431aelaax@"; // Ganti dengan password Anda
    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addHighScore(String name, int score) {
        String query = "INSERT INTO HighScores (name, score) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, score);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getHighestScore() {
        String query = "SELECT name, score FROM HighScores ORDER BY score DESC LIMIT 1";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return "Highest Score: " + rs.getString("name") + " - " + rs.getInt("score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No scores available.";
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

