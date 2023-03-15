package chess.piece;

import chess.Color;

public class Knight extends ChessPiece {

    public Knight(Color color) {
        super(ChessPieceType.KNIGHT, color);
    }

    private static Boolean lShapedPath(int rowCur, int colCur, int rowDes, int colDes) {
        // returns true if the path is L-shaped
        return ((Math.abs(rowCur - rowDes) == 2 && Math.abs(colCur - colDes) == 1) ||
                (Math.abs(rowCur - rowDes) == 1 && Math.abs(colCur - colDes) == 2));
    }

    @Override
    public String toString() {
        String c;
        if (getColor() == Color.WHITE) {
            c = "\u2658";
        } else {
            c = "\u265e";
        }
        return c;
    }

    @Override
    public boolean canMove(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {
        return lShapedPath(rowCur, colCur, rowDes, colDes);
    }
}
