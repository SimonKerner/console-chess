package chess.piece;

import chess.Color;


public class Bishop extends ChessPiece implements IsPathEmpty {

    public Bishop(Color color) {
        super(ChessPieceType.BISHOP, color);
    }

    private static boolean diagonalPath(int rowCur, int colCur, int rowDes, int colDes) {
        //returns true if the path is diagonal
        return ((Math.abs(rowCur - rowDes) == Math.abs(colCur - colDes)));             //diagonal movement
    }

    @Override
    public String toString() {
        String c;
        if (getColor() == Color.WHITE) {
            c = "\u2657";
        } else {
            c = "\u265d";
        }
        return c;
    }

    @Override
    public boolean canMove(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {
        return diagonalPath(rowCur, colCur, rowDes, colDes) && IsPathEmpty.areDiagonalPathsEmpty(board, rowCur, colCur, rowDes, colDes);
    }
}
