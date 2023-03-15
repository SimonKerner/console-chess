package chess;

import chess.piece.*;

import static chess.Color.*;

import java.util.Arrays;
import java.util.Scanner;

public class Board {

    //filler object - used for filling null spaces
    //had to do this because of the toString method
    private Filler filler = new Filler();

    //the board as two dimensional object array
    public ChessPiece[][] board = new ChessPiece[8][8];

    //constructor for board with default setup
    public Board() {
        this.initializeBoard();
    }

    //creates a copy of a given board
    public Board(ChessPiece[][] board) {
        this.board = board;
    }

    //method to set initial board setup
    //may also be used to reset an available board with the initial setup, without the need to create a new board
    private void initializeBoard() {

        //initialize array indices with white chess figures
        board[0][0] = new Rook(WHITE);
        board[0][1] = new Knight(WHITE);
        board[0][2] = new Bishop(WHITE);
        board[0][3] = new Queen(WHITE);
        board[0][4] = new King(WHITE);
        board[0][5] = new Bishop(WHITE);
        board[0][6] = new Knight(WHITE);
        board[0][7] = new Rook(WHITE);

        board[1][0] = new Pawn(WHITE);
        board[1][1] = new Pawn(WHITE);
        board[1][2] = new Pawn(WHITE);
        board[1][3] = new Pawn(WHITE);
        board[1][4] = new Pawn(WHITE);
        board[1][5] = new Pawn(WHITE);
        board[1][6] = new Pawn(WHITE);
        board[1][7] = new Pawn(WHITE);

        //construct filler objects
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = filler;
            }
        }

        //initialize array indices with black chess figures
        board[7][0] = new Rook(BLACK);
        board[7][1] = new Knight(BLACK);
        board[7][2] = new Bishop(BLACK);
        board[7][3] = new Queen(BLACK);
        board[7][4] = new King(BLACK);
        board[7][5] = new Bishop(BLACK);
        board[7][6] = new Knight(BLACK);
        board[7][7] = new Rook(BLACK);

        board[6][0] = new Pawn(BLACK);
        board[6][1] = new Pawn(BLACK);
        board[6][2] = new Pawn(BLACK);
        board[6][3] = new Pawn(BLACK);
        board[6][4] = new Pawn(BLACK);
        board[6][5] = new Pawn(BLACK);
        board[6][6] = new Pawn(BLACK);
        board[6][7] = new Pawn(BLACK);
    }

    //getter
    //returns Arrays with current board setup
    // if field equals filler returns null
    public ChessPiece[][] getBoard() {                                                      //Nachfragen
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getType() == ChessPieceType.FILLER) {   //set all filler objects to null for correct representation
                    board[i][j] = null;
                }
            }
        }
        return this.board;
    }

    //returns an array with the position of a requested piece
    public int[] getPosition(ChessPiece piece) {
        int[] position = new int[2];
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == piece) {
                    position = new int[]{i, j};
                }
            }
        }
        return position;
    }

    //methods
    //setting new location to piece type and setting old location to filler
    protected void move(Color player, int rowCur, int colCur, int rowDes, int colDes) {
        ChessPiece updatePosition = board[rowCur][colCur];

        if (canPawnPromote(player, rowCur, colCur, rowDes)) {                                           //pawn promotion
            promoter(rowCur, colCur, rowDes, colDes);
        } else if (isCastleMove(board, rowCur, colCur, rowDes, colDes)) {                               //castling
            castle(board, rowCur, colCur, rowDes, colDes);
        } else if ((player == WHITE) && (updatePosition.getColor() == WHITE)) {                          //white movement
            board[rowDes][colDes] = board[rowCur][colCur];
            board[rowCur][colCur] = filler;
        } else if ((player == BLACK) && (updatePosition.getColor() == BLACK)) {                         //black movement
            board[rowDes][colDes] = board[rowCur][colCur];
            board[rowCur][colCur] = filler;
        }
    }

    //converter for coordinate input of a player
    //converts a letter character to a usable int number
    protected int letterConverter(char charIn) {
        int numOut = -1;
        char[] sideLetters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

        for (int i = 0; i < sideLetters.length; i++) {
            if (sideLetters[i] == charIn) {
                numOut = i;
            }
        }
        return numOut;
    }

    //converts a number character to a usable int number
    protected int numberConverter(char charIn) {
        int numOut = -1;
        int convertedNum = Character.getNumericValue(charIn);
        int[] sideNum = {1, 2, 3, 4, 5, 6, 7, 8};

        for (int i : sideNum) {
            if (i == convertedNum) {
                //-1 so that the locations are stored starting from 0
                numOut = convertedNum - 1;
            }
        }
        return numOut;
    }

    //checks if a move is valid or not
    protected boolean isMoveValid(Color color, int rowCur, int colCur, int rowDes, int colDes) {
        // invalid if the move origin or destination is outside the board
        if (rowCur < 0 || rowCur > 7 || colCur < 0 || colCur > 7 || rowDes < 0
                || rowDes > 7 || colDes < 0 || colDes > 7) {
            System.err.println("    Move is outside the board!\n    Try it again:\n");
            return false;
        }

        // Invalid if origin is a filler
        if (board[rowCur][colCur].getColor() == COLORLESS) {
            System.err.println("    Origin is empty!\n    Try it again:\n");
            return false;
        }

        // invalid if specific piece rules are not obeyed
        if (!board[rowCur][colCur].canMove(board, rowCur, colCur, rowDes, colDes)) {
            System.err.println("    This piece can't move like that!\n    Try it again:\n");
            return false;
        }

        // Invalid if player moves tries to move opponents figures
        if ((board[rowCur][colCur].getColor().equals(WHITE) && color == BLACK)
                || (board[rowCur][colCur].getColor().equals(BLACK) && color == WHITE)) {
            System.err.println("    This is not your piece!\n    Try it again:\n");
            return false;
        }

        // invalid if the white lands on white
        if (board[rowCur][colCur].getColor() == WHITE
                && board[rowDes][colDes].getColor() == WHITE) {
            System.err.println("    White cannot land on white!\n    Try it again:\n");
            return false;
        }

        // invalid if the black lands on black
        if (board[rowCur][colCur].getColor() == BLACK
                && board[rowDes][colDes].getColor() == BLACK) {
            System.err.println("    Black cannot land on black!\n    Try it again:\n");
            return false;
        }
/*
        This loop should prevent the king to move to a threatened field,
        but it is not working correctly.
        King can move to threatened fields right now

        if (((board[rowCur][colCur].getType() == ChessPieceType.KING &&
                board[rowCur][colCur].getColor() == BLACK) && isPlayerCheck(BLACK)) ||
            ((board[rowCur][colCur].getType() == ChessPieceType.KING &&
                    board[rowCur][colCur].getColor() == WHITE) && isPlayerCheck(WHITE))) {
            System.err.println("    King cannot move to a threatened field!\n");
            return false;
        }

*/
        return true;
    }

    //checks if a pawn is promotable
    private boolean canPawnPromote(Color player, int rowCur, int colCur, int rowDes) {
        if (board[rowCur][colCur].getType() == ChessPieceType.PAWN &&
                player == WHITE && rowDes == 7) {
            System.err.println("White Pawn can be promoted");
            return true;
        } else if (board[rowCur][colCur].getType() == ChessPieceType.PAWN &&
                player == BLACK && rowDes == 0) {
            System.err.println("Black Pawn can be promoted");
            return true;
        }
        return false;
    }

    //promotes a pawn to knight, queen, bishop or rook if conditions are true
    private void promoter(int rowCur, int colCur, int rowDes, int colDes) {
        Scanner input = new Scanner(System.in);
        System.err.println("Pawn can be promoted to: Queen; Knight, Bishop or Rook\nType the letter 'Q', 'K', 'B' or 'R' for promotion");
        String promote = input.next().toLowerCase();
        switch (promote) {
            case "q":
                board[rowDes][colDes] = new Queen(board[rowCur][colCur].getColor());
                board[rowCur][colCur] = filler;
                break;
            case "k":
                board[rowDes][colDes] = new Knight(board[rowCur][colCur].getColor());
                board[rowCur][colCur] = filler;
                break;
            case "b":
                board[rowDes][colDes] = new Bishop(board[rowCur][colCur].getColor());
                board[rowCur][colCur] = filler;
                break;
            case "r":
                board[rowDes][colDes] = new Rook(board[rowCur][colCur].getColor());
                board[rowCur][colCur] = filler;
                break;
        }
    }

    //checks if movement is a castle move
    private boolean isCastleMove(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {
        return (board[rowCur][colCur].getType() == ChessPieceType.KING) && colCur == 4 && (colDes == 6 || colDes == 2);
    }

    //executes the castle move
    private void castle(ChessPiece[][] board, int rowCur, int colCur, int rowDes, int colDes) {

        if (board[rowCur][colCur].getType() == ChessPieceType.KING) {
            //white
            if (board[rowCur][colCur].getColor() == WHITE) {                                    //white
                if (colDes == 6 && board[0][7].getType() == ChessPieceType.ROOK) {              //castle king side
                    board[rowDes][colDes] = board[rowCur][colCur];
                    board[0][5] = board[0][7];
                    board[0][7] = filler;
                    board[rowCur][colCur] = filler;
                } else if (colDes == 2 && board[0][0].getType() == ChessPieceType.ROOK) {       //castle queen side
                    board[rowDes][colDes] = board[rowCur][colCur];
                    board[0][3] = board[0][0];
                    board[0][0] = filler;
                    board[rowCur][colCur] = filler;
                }
            } else if (board[rowCur][colCur].getColor() == BLACK) {                             //black
                if (colDes == 6 && board[7][7].getType() == ChessPieceType.ROOK) {              //castle king side
                    board[rowDes][colDes] = board[rowCur][colCur];
                    board[7][5] = board[7][7];
                    board[7][7] = filler;
                    board[rowCur][colCur] = filler;
                } else if (colDes == 2 && board[7][0].getType() == ChessPieceType.ROOK) {       //castle queen side
                    board[rowDes][colDes] = board[rowCur][colCur];
                    board[7][3] = board[7][0];
                    board[7][0] = filler;
                    board[rowCur][colCur] = filler;
                }
            }
        }
    }

    //checks if King with given color is in check
    protected boolean isPlayerCheck(Color player) {
        int[] kingPos = getKingPos(player);
        int row = kingPos[0];
        int col = kingPos[1];

        for (int rowCur = 0; rowCur < board.length; rowCur++) {
            for (int colCur = 0; colCur < board[0].length; colCur++) {
                if (board[rowCur][colCur].getColor() != Color.COLORLESS) {
                    if (board[rowCur][colCur].canMove(board, rowCur, colCur, row, col) && !board[rowCur][colCur].getColor().equals(player)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //gets the coordinates of the king
    //could have used the getPositionMethod, but it would not work
    private int[] getKingPos(Color color) {
        int row = 0, col = 0;

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y].getColor() != COLORLESS) {
                    if (board[x][y].getClass().isInstance(new King(WHITE)) && board[x][y].getColor().equals(color)) {
                        row = x;
                        col = y;
                    }
                }
            }
        }
        int[] returnArray = new int[2];
        returnArray[0] = row;
        returnArray[1] = col;

        return returnArray;

    }

    //toString method for correct representation of the board
    public String toString() {
        String str = "";
        str += "\n   ----------------------- C H E S S ----------------------\n\n";
        str += "       a     b      c      d      e      f      g      h\n";
        str += "   --------------------------------------------------------\n";
        for (int i = 7; i >= 0; i--) {
            str += (i + 1) + "  |  ";
            for (int j = 0; j < 8; j++) {
                str += board[i][j] + "  |  ";
            }
            str += "\n   -------------------------------------------------------" + "-";
            str += "\n";
        }
        return str;
    }
}


