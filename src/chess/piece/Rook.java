package chess.piece;

import chess.Color;

public class Rook extends ChessPiece implements IsPathEmpty {

    public Rook(Color color) {
        super(ChessPieceType.ROOK, color);
    }

    private static boolean straightPath(int rowCur, int colCur, int rowDes, int colDes) {
        // returns true if the path is straight
        return !((rowCur != rowDes) && (colCur != colDes));
    }

    @Override
    public String toString() {
        String c;
        if (getColor() == Color.WHITE) {
            c = "\u2656";
        } else {
            c = "\u265c";
        }
        return c;
    }

    @Override
    public boolean canMove(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {
        return straightPath(rowCur, colCur, rowDes, colDes) && IsPathEmpty.areStraightPathsEmpty(board, rowCur, colCur, rowDes, colDes);
    }

}
