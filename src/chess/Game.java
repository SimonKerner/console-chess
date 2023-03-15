package chess;

import java.util.Random;
import java.util.Scanner;

import static chess.Color.*;

public class Game {
    //constructor for a game
    private Game() {
    }

    //method to start the game
    private void gameStarter() {
        int rowCur, colCur, rowDes, colDes;
        Color color = WHITE;
        String coordinateOld;
        String coordinateNew;
        Player playerOne = new Player("Player 1", color);
        Player playerTwo = new Player("Player 2", color);
        Scanner input = new Scanner(System.in);

        System.out.println("\nWelcome to a game of chess\nChoose who will be *Player 1* and who will be *Player 2*\n");
        System.out.println("If you are ready press 'Enter' for a random color assignment:");

        //if Enter is pressed, game continues
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //random color assignment for players
        Random playerColor = new Random();
        if (playerColor.nextBoolean()) {
            playerOne.setColorWhite();
            playerTwo.setColorBlack();
        } else {
            playerOne.setColorBlack();
            playerTwo.setColorWhite();
        }

        //prints the result of the color assignment
        System.out.println("Color assignment:\n" + playerOne + "\n" + playerTwo + "\n");

        System.out.println("If you are ready press 'Enter' to start the game");

        //if Enter is pressed, game continues
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //create new board
        Board chessBoard = new Board();

        //prints chessboard with initial setup
        System.out.println(chessBoard.toString());

        /*main loop for the game
            -loop changes Player colors after every turn
            -player is not able to cancel a given input
            -or to cancel and repeat a whole move
            -if a player is checkmate the loop breaks and the game is over
            -a player is checkmate if a king is twice check - means if the a player could not get out of a check situation
        */
        while (true) {
            //move from position - asks for player input
            System.out.println("It's your turn * * " + color + " * *");
            System.out.println("Move a piece from position:             (e.g. 'd4')");
            coordinateOld = input.next().toLowerCase();
            colCur = chessBoard.letterConverter(coordinateOld.charAt(0));
            rowCur = chessBoard.numberConverter((coordinateOld.charAt(1)));

            //move to position - asks for player input
            System.out.println("Move piece to position:               (e.g. 'd6')");
            coordinateNew = input.next().toLowerCase();
            colDes = chessBoard.letterConverter(coordinateNew.charAt(0));
            rowDes = chessBoard.numberConverter((coordinateNew.charAt(1)));

            //checks if player input is valid (isMoveValid method) and executes it if correct,
            //else repeats the loop and asks again for input
            if (chessBoard.isMoveValid(color, rowCur, colCur, rowDes, colDes)) {
                chessBoard.move(color, rowCur, colCur, rowDes, colDes);

                //checks if a player is in check e.g. White threatens black king - black king is in check
                if (chessBoard.isPlayerCheck(changeColor(color))) {
                    System.err.println("\n" + changeColor(color) + " is in check.");
                }
                System.out.println(chessBoard.toString());
            } else {
                continue;
            }

            //responsible for a turnbased game (changes color)
            color = changeColor(color);

            //checks if a player is checkmate
            if (chessBoard.isPlayerCheck(changeColor(color))) {
                if (chessBoard.isPlayerCheck(changeColor(color))) {
                    System.err.println("Checkmate! Player: " + color + " wins");
                    System.err.println("Thank you for playing my Game!");
                    break;
                }
            }
        }
    }

    //changes the color form one to another
    private Color changeColor(Color color) {
        if (color == WHITE) {
            return BLACK;
        }
        return WHITE;
    }

    //main method
    public static void main(String[] args) {
        //constructor to create a new game of chess
        Game game = new Game();
        //method to start the game
        game.gameStarter();
    }
}

