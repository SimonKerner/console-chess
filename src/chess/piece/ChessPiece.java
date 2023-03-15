package chess.piece;

import chess.Board;
import chess.Color;


public abstract class ChessPiece {

    //variables
    private ChessPieceType type;
    private Color color;

    //constructor
    public ChessPiece(ChessPieceType type, Color color) {
        this.type = type;
        this.color = color;
    }

    //getter
    public ChessPieceType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    //abstract method to represent the figure as String
    public abstract String toString();

    //overwritten equals method to check if two figures are the same
    public boolean equals(ChessPiece figure) {
        if (figure == null) {
            return false;
        }
        if (figure == this) {
            return true;
        }
        if (!figure.getClass().equals(figure.getClass())) {
            return false;
        }
        ChessPiece piece = (ChessPiece) figure;
        return (this.getColor() == piece.getColor() &&
                this.getType() == piece.getType());
    }

/*
    //used the canMove method different, therefore this method won't work

    public boolean[][] getPossibleDestinations(Board board) {
        boolean[][] possibleDestination = new boolean[8][8];

        for (int i = 0; i < possibleDestination.length; i++) {
            for (int j = 0; j < possibleDestination.length; j++) {
                if (canMove(board, i, j)) {
                    possibleDestination[i][j] = true;
                } else {
                    possibleDestination[i][j] = false;
                }
            }
        }
        return possibleDestination;
    }
*/

    public abstract boolean canMove(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes);
}
