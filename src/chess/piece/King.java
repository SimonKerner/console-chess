package chess.piece;

import chess.Color;

public class King extends ChessPiece implements IsPathEmpty {

    public boolean hasMoved;
    public boolean castled;

    public King(Color color) {
        super(ChessPieceType.KING, color);
        this.hasMoved = false;
        this.castled = false;
    }

    //basic movement
    private boolean basicMove(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {
        hasMoved = true;
        return (Math.abs(rowDes - rowCur) <= 1 && Math.abs(colDes - colCur) <= 1);
    }

    //castling movement
    private boolean castleMove(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {
        if (hasMoved) {
            return false;
        }

        if (castled) {
            return false;
        }

        if (Math.abs((colDes - colCur)) == 2 && rowCur == rowDes) {
            //castle king side
            if (IsPathEmpty.isRightPathEmpty(board, rowCur, colCur, rowDes, colDes) || IsPathEmpty.isLeftPathEmpty(board, rowCur, colCur, rowDes, colDes)) {
                castled = true;
                hasMoved = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String c;
        if (getColor() == Color.WHITE) {
            c = "\u2654";                       //if white  U+2654  ♔
        } else {
            c = "\u265a";                       //if black  U+265A  ♚
        }
        return c;
    }

    @Override
    public boolean canMove(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {
        return castleMove(board, rowCur, colCur, rowDes, colDes) || basicMove(board, rowCur, colCur, rowDes, colDes);
    }



}
