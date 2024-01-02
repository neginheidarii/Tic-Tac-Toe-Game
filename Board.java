// Name: Negin Heidari
// Description: The Board class is used to create a board object with a size and a 2D array of characters.

package com.example.tictactoe;

import javafx.scene.Node;
import javafx.scene.shape.Line;

public class Board {
    private char[][] board;
    private int size;
    private int numMoves;

    private int[][] winnerIndices;

    public Board(int size) {
        this.size = size;
        this.board = new char[size][size];
        this.winnerIndices = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = ' ';
                this.winnerIndices[i][j] = 0;
            }
        }
        this.numMoves = 0;
    }

    public void printBoard() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                System.out.print(" " + this.board[i][j] + " ");
                if (j < this.size - 1) {
                    System.out.print("|");
                }
            }
            if (i < this.size - 1) {
                System.out.println();
                for (int j = 0; j < this.size; j++) {
                    System.out.print("---");
                    if (j < this.size - 1) {
                        System.out.print("+");
                    }
                }
            }
            System.out.println();
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public void resetBoard() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.board[i][j] = ' ';
            }
        }
        this.numMoves = 0;
    }

    public boolean isFull() {
        return this.numMoves == this.size * this.size;
    }

    public boolean isLegalMove(int row, int col) {
        return row >= 0 && row < this.size && col >= 0 && col < this.size && this.board[row][col] == ' ';
    }

    public void makeMove(char player, int row, int col) {
        if (this.isLegalMove(row, col)) {
            this.board[row][col] = player;
            this.numMoves++;
        }
    }

    public void undoMove(int row, int col) {
        if (row >= 0 && row < this.size && col >= 0 && col < this.size && this.board[row][col] != ' ') {
            this.board[row][col] = ' ';
            this.numMoves--;
        }
    }

    public boolean isGameOver() {
        return this.isFull() || this.isWinner('X') || this.isWinner('O');
    }

    public void resetWinnerIndices() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.winnerIndices[i][j] = 0;
            }
        }
    }

    public int[][] getWinnerIndices() {
        int[][] indices = new int[this.size][2];
        int index = 0;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.winnerIndices[i][j] == 1) {
                    indices[index][0] = i;
                    indices[index++][1] = j;
                }
            }
        }
        return indices;
    }
    public boolean isWinner(char player) {
        // check rows
        for (int i = 0; i < this.size; i++) {
            boolean win = true;
            for (int j = 0; j < this.size; j++) {
                this.winnerIndices[i][j] = 1;
                if (this.board[i][j] != player) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return true;
            }
            resetWinnerIndices();
        }
        // check columns
        for (int j = 0; j < this.size; j++) {
            boolean win = true;
            for (int i = 0; i < this.size; i++) {
                this.winnerIndices[i][j] = 1;
                if (this.board[i][j] != player) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return true;
            }
            resetWinnerIndices();
        }
        // check diagonals
        boolean win = true;
        for (int i = 0; i < this.size; i++) {
            this.winnerIndices[i][i] = 1;
            if (this.board[i][i] != player) {
                win = false;
                break;
            }
        }
        if (win) {
            return true;
        }
        resetWinnerIndices();
        win = true;
        for (int i = 0; i < this.size; i++) {
            this.winnerIndices[i][this.size - i - 1] = 1;
            if (this.board[i][this.size - i - 1] != player) {
                win = false;
                break;
            }
        }
        if (win) {
            return true;
        }
        resetWinnerIndices();
        return false;
    }

    public int getScore(char player) {
        if (this.isWinner(player)) {
            return 1;
        } else if (this.isWinner(player == 'X' ? 'O' : 'X')) {
            return -1;
        } else if (this.isFull()) {
            return 0;
        }
        return -2;
    }

    public int getSize() {
        return this.size;
    }

}
