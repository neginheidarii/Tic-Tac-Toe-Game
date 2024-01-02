// Name: Negin Heidari
// Description: This class implements the minmax algorithm to find the best move for the computer player.

package com.example.tictactoe;

public class MinMax {
    private char player;
    private char opponent;
    private int depth;
    private Board board;
    private int[] bestMove;
    private int bestScore;

    public MinMax(char player, int depth) {
        this.player = player;
        this.depth = depth;
        this.bestMove = new int[2];
        this.bestScore = 0;
        if (this.player == 'X' || this.player == 'x') {
            this.opponent = 'O';
        } else {
            this.opponent = 'X';
        }
    }

    public int[] getBestMove(Board board) {
        this.board = board;
        this.bestScore = max(this.board, this.depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return this.bestMove;
    }

    public int[] getRandomLegalMove(Board board) {
        int[] move = new int[2];
        int i = (int) (Math.random() * board.getSize());
        int j = (int) (Math.random() * board.getSize());
        while (!board.isLegalMove(i, j)) {
            i = (int) (Math.random() * board.getSize());
            j = (int) (Math.random() * board.getSize());
        }
        move[0] = i;
        move[1] = j;
        return move;
    }

    public int getBestScore() {
        return this.bestScore;
    }

    // max function for minmax
    private int max(Board board, int depth, int alpha, int beta) {
        if (depth == 0 || board.isGameOver()) {
            return board.getScore(this.player);
        }
        int maxScore = Integer.MIN_VALUE;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.isLegalMove(i, j)) {
                    board.makeMove(this.player, i, j);
                    int score = min(board, depth - 1, alpha, beta);
                    board.undoMove(i, j);
                    if (score > maxScore) {
                        maxScore = score;
                        if (depth == this.depth) {
                            this.bestMove[0] = i;
                            this.bestMove[1] = j;
                        }
                    }
                    alpha = Math.max(alpha, score);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }
        return maxScore;
    }

    // min function for minmax
    private int min(Board board, int depth, int alpha, int beta) {
        if (depth == 0 || board.isGameOver()) {
            return board.getScore(this.player);
        }
        int minScore = Integer.MAX_VALUE;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.isLegalMove(i, j)) {
                    board.makeMove(this.opponent, i, j);
                    int score = max(board, depth - 1, alpha, beta);
                    board.undoMove(i, j);
                    if (score < minScore) {
                        minScore = score;
                    }
                    beta = Math.min(beta, score);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }
        return minScore;
    }

}
