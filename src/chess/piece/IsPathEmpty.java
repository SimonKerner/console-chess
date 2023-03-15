package chess.piece;

import static chess.Color.*;

public interface IsPathEmpty {

    //following ones check all possible directions

    //returns true if all diagonal paths are empty
    static boolean areDiagonalPathsEmpty(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {
        if (rowCur < rowDes && colCur < colDes) {                                   // diagonal up right
            for (int i = rowDes - rowCur - 1; i > 0; i--) {
                if (board[rowCur + i][colCur + i].getColor() != COLORLESS) {
                    return false;
                }
            }
        } else if (rowCur > rowDes && colCur > colDes) {                             // diagonal down left
            for (int i = rowCur - rowDes - 1; i > 0; i--) {
                if (board[rowCur - i][colCur - i].getColor() != COLORLESS) {
                    return false;
                }
            }
        } else if (rowCur > rowDes && colCur < colDes) {                             // diagonal down right
            for (int i = rowCur - rowDes - 1; i > 0; i--) {
                if (board[rowCur - i][colCur + i].getColor() != COLORLESS) {
                    return false;
                }
            }
        } else if (rowCur < rowDes && colCur > colDes) {                             // diagonal up left
            for (int i = rowDes - rowCur - 1; i > 0; i--) {
                if (board[rowCur + i][colCur - i].getColor() != COLORLESS) {
                    return false;
                }
            }
        }
        return true;
    }

    //returns if all straight paths are empty
    static boolean areStraightPathsEmpty(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {
        if (rowCur == rowDes && colCur < colDes) {                                  // loop check if right path is empty
            for (int i = colCur + 1; i < colDes; i++) {
                if (board[rowCur][i].getColor() != COLORLESS) {
                    return false;
                }
            }
        } else if (rowCur == rowDes && colCur > colDes) {                             // loop check if left path is empty
            for (int i = colCur - 1; i > colDes; i--) {
                if (board[rowCur][i].getColor() != COLORLESS) {
                    return false;
                }
            }
        } else if (rowCur < rowDes && colCur == colDes) {                             // loop checks if path above is empty
            for (int i = rowCur + 1; i < rowDes; i++) {
                if (board[i][colCur].getColor() != COLORLESS) {
                    return false;
                }
            }
        } else if (rowCur > rowDes && colCur == colDes) {                             // loop checks if path underneath is empty
            for (int i = rowCur - 1; i > rowDes; i--) {
                if (board[i][colCur].getColor() != COLORLESS) {
                    return false;
                }
            }
        }
        return true;
    }


    //following ones only check a single direction

    //only for the pawn class - loop checks if path above is empty
    static boolean isStraightPathAboveEmpty(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {
        if (rowCur < rowDes && colCur == colDes) {
            for (int i = rowCur + 1; i <= rowDes; i++) {
                if (board[i][colCur].getColor() != COLORLESS) {
                    return false;
                }
            }
        }
        return true;
    }

    //only for the pawn class - loop checks if path underneath is empty
    static boolean isStraightPathUnderneathEmpty(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {
        if (rowCur > rowDes && colCur == colDes) {
            for (int i = rowCur - 1; i >= rowDes; i--) {
                if (board[i][colCur].getColor() != COLORLESS) {
                    return false;
                }
            }
        }
        return true;
    }

    //only for king class - loop check if right path is empty -- for castling
    static boolean isRightPathEmpty(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {
        if (rowCur == rowDes && colCur < colDes) {
            for (int i = colCur + 1; i < colDes; i++) {
                if (board[rowCur][i].getColor() != COLORLESS) {
                    return false;
                }
            }
        }
        return true;
    }

    //only for king class- loop check if left path is empty - for castling
    static boolean isLeftPathEmpty(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {
        if (rowCur == rowDes && colCur > colDes) {
            for (int i = colCur - 1; i > colDes; i--) {
                if (board[rowCur][i].getColor() != COLORLESS) {
                    return false;
                }
            }
        }
        return true;
    }
}
