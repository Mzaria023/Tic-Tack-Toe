package org.example.tictactoe;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicTacToeBoard {

    private static final int SIZE = 3;
    // True for player X, False for computer O
    private boolean xTurn = true;
    private Button[][] buttons = new Button[SIZE][SIZE];
    private GridPane grid = new GridPane();
    private Random random = new Random();

    public TicTacToeBoard() {
        initializeBoard();
    }

    private void initializeBoard() {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Button button = new Button();
                button.setMinSize(100, 100);
                button.setStyle("-fx-font-size: 36; -fx-font-weight: bold;");
                button.setOnAction(e -> handleButtonClick(button));
                buttons[row][col] = button;
                grid.add(button, col, row);
            }
        }
    }
    //Method to handle button actions
    private void handleButtonClick(Button button) {
        // Computer's turn or button already clicked
        if (!xTurn || !button.getText().isEmpty()) return;

        button.setText("X");
        if (checkWin()) {
            showWinMessage("X");
            resetBoard();
        } else if (isBoardFull()) {
            showWinMessage("No one");
            resetBoard();
        } else {
            xTurn = false;
            makeComputerMove();
        }
    }
    //Method handles computer's move
    private void makeComputerMove() {
        //A list of empty buttons
        List<Button> emptyButtons = getEmptyButtons();
        if (emptyButtons.isEmpty()) return;
        //Uses the random class to randomly click a button that is empty
        Button move = emptyButtons.get(random.nextInt(emptyButtons.size()));
        move.setText("O");

        if (checkWin()) {
            showWinMessage("O");
            resetBoard();
        } else if (isBoardFull()) {
            showWinMessage("No one");
            resetBoard();
        } else {
            xTurn = true;
        }
    }
    //Gets List of empty/not clicked buttons
    private List<Button> getEmptyButtons() {
        List<Button> emptyButtons = new ArrayList<>();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    emptyButtons.add(buttons[row][col]);
                }
            }
        }
        return emptyButtons;
    }
    //Method to check wins
    private boolean checkWin() {
        String player = xTurn ? "X" : "O";

        // Check rows and columns
        for (int i = 0; i < SIZE; i++) {
            if (checkLine(buttons[i][0], buttons[i][1], buttons[i][2], player) ||
                    checkLine(buttons[0][i], buttons[1][i], buttons[2][i], player)) {
                return true;
            }
        }

        // Check diagonals
        if (checkLine(buttons[0][0], buttons[1][1], buttons[2][2], player) ||
                checkLine(buttons[0][2], buttons[1][1], buttons[2][0], player)) {
            return true;
        }

        return false;
    }

    private boolean checkLine(Button b1, Button b2, Button b3, String player) {
        return b1.getText().equals(player) && b2.getText().equals(player) && b3.getText().equals(player);
    }

    private boolean isBoardFull() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showWinMessage(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        if ("No one".equals(winner)) {
            alert.setContentText("It's a draw!");
        } else {
            alert.setContentText("Player " + winner + " wins!");
        }
        alert.showAndWait();
    }
    private void resetBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                buttons[row][col].setText("");
            }
        }
        // Player X starts after reset
        xTurn = true;
    }

    public Parent getRoot() {
        return grid;
    }
}
