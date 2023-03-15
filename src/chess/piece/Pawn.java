package chess.piece;

import chess.Color;

public class Pawn extends ChessPiece implements IsPathEmpty {

    public Pawn(Color color) {
        super(ChessPieceType.PAWN, color);
    }

    private boolean basicMoves(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {
        if (getColor() == Color.WHITE) {
            return (((colCur == colDes) && rowCur == (rowDes - 1)) ||                                           //moves forward by one or
                    ((rowCur == 1) && (colCur == colDes) && (rowCur == (rowDes - 2)))) &&                       //moves forward by two only
                    IsPathEmpty.isStraightPathAboveEmpty(board, rowCur, colCur, rowDes, colDes);                //if path is empty
        } else {
            return (((colCur == colDes) && rowCur == (rowDes + 1)) ||
                    ((rowCur == 6) && (colCur == colDes) && (rowCur == (rowDes + 2)))) &&
                    IsPathEmpty.isStraightPathUnderneathEmpty(board, rowCur, colCur, rowDes, colDes);
        }
    }

    private boolean canCapture(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {
        if (getColor() == Color.WHITE) {
            return ((rowCur == (rowDes - 1)) && (Math.abs(colCur - colDes) == 1)) && board[rowDes][colDes].getColor() == Color.BLACK;
        } else {
            return ((rowCur == (rowDes + 1)) && (Math.abs(colCur - colDes) == 1)) && board[rowDes][colDes].getColor() == Color.WHITE;
        }
    }

    @Override
    public boolean canMove(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {
        return canCapture(board, rowCur, colCur, rowDes, colDes) || basicMoves(board, rowCur, colCur, rowDes, colDes);
    }

    @Override
    public String toString() {
        String c;
        if (getColor() == Color.WHITE) {
            c = "\u2659";
        } else {
            c = "\u265f";
        }
        return c;
    }
}

