package chess.piece;

import chess.Color;

public class Queen extends ChessPiece implements IsPathEmpty {

    public Queen(Color color) {
        super(ChessPieceType.QUEEN, color);
    }

    private static boolean diagonalPath(int curRow, int colCur, int rowDes, int colDes) {
        // returns true if the path is diagonal
        return ((Math.abs(curRow - rowDes) == Math.abs(colCur - colDes)));
    }

    private static boolean straightPath(int rowCur, int colCur, int rowDes, int colDes) {
        // returns true if the path is straight
        return !((rowCur != rowDes) && (colCur != colDes));
    }

    @Override
    public String toString() {
        String c;
        if (getColor() == Color.WHITE) {
            c = "\u2655";
        } else {
            c = "\u265b";
        }
        return c;
    }

    @Override
    public boolean canMove(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {
        return (diagonalPath(rowCur, colCur, rowDes, colDes) && IsPathEmpty.areDiagonalPathsEmpty(board, rowCur, colCur, rowDes, colDes)) ||
                ((straightPath(rowCur, colCur, rowDes, colDes) && IsPathEmpty.areStraightPathsEmpty(board, rowCur, colCur, rowDes, colDes)));
    }

}
