import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game2048 extends JFrame {
    private static final int SIZE = 4;
    private static final int TILE_SIZE = 100;

    private int[][] board;
    private JLabel[][] tiles;
    private JLabel scoreLabel;
    private int score;

    public Game2048() {
        setTitle("2048");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        board = new int[SIZE][SIZE];
        tiles = new JLabel[SIZE][SIZE];

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(SIZE, SIZE));
        gamePanel.setBackground(new Color(187, 173, 160));

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tiles[i][j] = new JLabel("", SwingConstants.CENTER);
                tiles[i][j].setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
                tiles[i][j].setFont(new Font("Arial", Font.BOLD, 30));
                tiles[i][j].setForeground(new Color(119, 110, 101));
                tiles[i][j].setOpaque(true);
                tiles[i][j].setBackground(new Color(204, 192, 179));

                gamePanel.add(tiles[i][j]);
            }
        }

        add(scoreLabel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        startNewGame();

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_UP) {
                    moveUp();
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    moveDown();
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    moveLeft();
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    moveRight();
                }

                updateGame();

                if (isGameOver()) {
                    JOptionPane.showMessageDialog(Game2048.this, "Ви билися як лев і набрали " + score +" балів.");
                    startNewGame();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        setFocusable(true);
        requestFocus();
    }

    private void startNewGame() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 0;
                tiles[i][j].setText("");
            }
        }

        score = 0;
        updateScoreLabel();

        addRandomTile();
        addRandomTile();
    }

    private void addRandomTile() {
        int emptyCount = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    emptyCount++;
                }
            }
        }

        if (emptyCount == 0) {
            return;
        }

        int randomIndex = (int) (Math.random() * emptyCount) + 1;
        int value = Math.random() < 0.9 ? 2 : 4;

        emptyCount = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    emptyCount++;

                    if (emptyCount == randomIndex) {
                        board[i][j] = value;
                        return;
                    }
                }
            }
        }
    }

    private void moveUp() {
        for (int col = 0; col < SIZE; col++) {
            for (int row = 1; row < SIZE; row++) {
                int value = board[row][col];

                if (value != 0) {
                    int newRow = row;

                    while (newRow > 0 && board[newRow - 1][col] == 0) {
                        board[newRow - 1][col] = value;
                        board[newRow][col] = 0;
                        newRow--;
                    }

                    if (newRow > 0 && board[newRow - 1][col] == value) {
                        board[newRow - 1][col] *= 2;
                        score += board[newRow - 1][col];
                        board[newRow][col] = 0;
                    }
                }
            }
        }

        addRandomTile();
    }

    private void moveDown() {
        for (int col = 0; col < SIZE; col++) {
            for (int row = SIZE - 2; row >= 0; row--) {
                int value = board[row][col];

                if (value != 0) {
                    int newRow = row;

                    while (newRow < SIZE - 1 && board[newRow + 1][col] == 0) {
                        board[newRow + 1][col] = value;
                        board[newRow][col] = 0;
                        newRow++;
                    }

                    if (newRow < SIZE - 1 && board[newRow + 1][col] == value) {
                        board[newRow + 1][col] *= 2;
                        score += board[newRow + 1][col];
                        board[newRow][col] = 0;
                    }
                }
            }
        }

        addRandomTile();
    }

    private void moveLeft() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 1; col < SIZE; col++) {
                int value = board[row][col];

                if (value != 0) {
                    int newCol = col;

                    while (newCol > 0 && board[row][newCol - 1] == 0) {
                        board[row][newCol - 1] = value;
                        board[row][newCol] = 0;
                        newCol--;
                    }

                    if (newCol > 0 && board[row][newCol - 1] == value) {
                        board[row][newCol - 1] *= 2;
                        score += board[row][newCol - 1];
                        board[row][newCol] = 0;
                    }
                }
            }
        }

        addRandomTile();
    }

    private void moveRight() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = SIZE - 2; col >= 0; col--) {
                int value = board[row][col];

                if (value != 0) {
                    int newCol = col;

                    while (newCol < SIZE - 1 && board[row][newCol + 1] == 0) {
                        board[row][newCol + 1] = value;
                        board[row][newCol] = 0;
                        newCol++;
                    }

                    if (newCol < SIZE - 1 && board[row][newCol + 1] == value) {
                        board[row][newCol + 1] *= 2;
                        score += board[row][newCol + 1];
                        board[row][newCol] = 0;
                    }
                }
            }
        }

        addRandomTile();
    }

    private void updateGame() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int value = board[row][col];
                String text = value == 0 ? "" : String.valueOf(value);

                tiles[row][col].setText(text);
                tiles[row][col].setBackground(getTileColor(value));
            }
        }

        updateScoreLabel();
    }

    private Color getTileColor(int value) {
        switch (value) {
            case 2:
                return new Color(238, 228, 218);
            case 4:
                return new Color(237, 224, 200);
            case 8:
                return new Color(242, 177, 121);
            case 16:
                return new Color(245, 149, 99);
            case 32:
                return new Color(246, 124, 95);
            case 64:
                return new Color(246, 94, 59);
            case 128:
                return new Color(237, 207, 114);
            case 256:
                return new Color(237, 204, 97);
            case 512:
                return new Color(237, 200, 80);
            case 1024:
                return new Color(237, 197, 63);
            case 2048:
                return new Color(237, 194, 46);
            default:
                return new Color(205, 193, 180);
        }
    }

    private boolean isGameOver() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    return false;
                }

                if (row > 0 && board[row - 1][col] == board[row][col]) {
                    return false;
                }

                if (row < SIZE - 1 && board[row + 1][col] == board[row][col]) {
                    return false;
                }

                if (col > 0 && board[row][col - 1] == board[row][col]) {
                    return false;
                }

                if (col < SIZE - 1 && board[row][col + 1] == board[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game2048::new);
    }
}
