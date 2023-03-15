package chess.piece;

import chess.Color;

public class Filler extends ChessPiece {

    public Filler() {
        super(ChessPieceType.FILLER, Color.COLORLESS);
    }

    @Override
    public String toString() {
        return "\u26f6";
    }

    @Override
    public boolean canMove(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {
        return false;
    }
}
