/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.gui.controller;

import java.util.Random;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import tictactoe.bll.GameBoard;
import tictactoe.bll.IGameModel;
import tictactoe.gui.views.StartingScreen;

/**
 *
 * @author Stegger
 */
public class TicTacViewController implements Initializable
{


    @FXML
    private Label lblPlayer;

    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    @FXML
    private Button btn9;

    @FXML
    private Button btnNewGame;

    @FXML
    private GridPane gridPane;
    
    private static final String TXT_PLAYER = "Player: ";
    private IGameModel game;
    boolean stopGame =false; // if its true you cant add new signs
    // and then you have to refresh it when making a new game

    @FXML
    private void handleButtonAction(ActionEvent event)
    { StartingScreen startingScreen = new StartingScreen();

    if(startingScreen.chosenMode() == 1) {
        try {
            Integer row = GridPane.getRowIndex((Node) event.getSource());
            Integer col = GridPane.getColumnIndex((Node) event.getSource());
            int r = (row == null) ? 0 : row;
            int c = (col == null) ? 0 : col;
            int player = game.getNextPlayer();
            if (game.play(c, r) && !stopGame) {
                Button btn = (Button) event.getSource();
                String xOrO = player == 1 ? "X" : "O";
                btn.setText(xOrO);

                if (game.isGameOver()) {
                    int winner = game.getWinner();
                    displayWinner(winner);
                    stopGame = true;
                } else {
                    game.setPlayer(game.getNextPlayer());
                    setPlayer();
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        }
        if(startingScreen.chosenMode==2)
        {
           // lblPlayer.setText("Player vs Computer");
            try
            {
                Integer row = GridPane.getRowIndex((Node) event.getSource());
                Integer col = GridPane.getColumnIndex((Node) event.getSource());
                int r = (row == null) ? 0 : row;
                int c = (col == null) ? 0 : col;
                Button btn =(Button) event.getSource();
                if(game.play(c,r) && !stopGame){
                    GameBoard gameBoard = new GameBoard();
                    gameBoard.player = 1; // I want to ensure

                    String xOrO =  "X";// computers is O;
                    btn.setText(xOrO);

                    AImove();

                    if (game.isGameOver()) {
                        int winner = game.getWinner();
                        displayWinnerAI(winner); // other method for AI
                        stopGame = true;
                    }
                }

            }catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

    }

    private void AImove()
    {   Random rand = new Random();
        int randomRow = rand.nextInt(3);
        int randomCol = rand.nextInt(3);

        GameBoard gameBoard = new GameBoard();
        if(gameBoard.board[randomRow][randomCol] == 0) // need to change it to make it work all the time
        {
           // here we need to set text programmatically
            if(gameBoard.board[randomRow][randomCol] == gameBoard.board[0][0])
            { btn1.setText("O"); }
            else if(gameBoard.board[randomRow][randomCol] == gameBoard.board[0][1])
                btn2.setText("O");
            else if(gameBoard.board[randomRow][randomCol] == gameBoard.board[0][2])
                btn3.setText("O");
            else if(gameBoard.board[randomRow][randomCol] == gameBoard.board[1][0])
                btn4.setText("O");
           else if(gameBoard.board[randomRow][randomCol] == gameBoard.board[1][1])
                btn5.setText("O");
            else if(gameBoard.board[randomRow][randomCol] == gameBoard.board[1][2])
                btn6.setText("O");
            else if(gameBoard.board[randomRow][randomCol] == gameBoard.board[2][0])
                btn7.setText("O");
           else  if(gameBoard.board[randomRow][randomCol] == gameBoard.board[2][1])
                btn8.setText("O");
            else if(gameBoard.board[randomRow][randomCol] == gameBoard.board[2][2])
                btn9.setText("O");

        }
        //else
          // AImove();

    }

    @FXML
    private void handleNewGame(ActionEvent event)
    {
        game.newGame();
        setPlayer();
        clearBoard();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        game = new GameBoard();
        StartingScreen startingScreen = new StartingScreen();
        if(startingScreen.chosenMode==1)
            setPlayer();
        else if(startingScreen.chosenMode==2)
            lblPlayer.setText("Player vs Computer");
        stopGame=false;
    }

    private void setPlayer()
    {
        lblPlayer.setText(TXT_PLAYER + game.getNextPlayer());
    }

    private void displayWinner(int winner)
    {
        String message = switch (winner) {
            case -1 -> "It's a draw :-(";
            default -> "Player " + winner + " wins!!!";
        };
        lblPlayer.setText(message);
    }

    private void displayWinnerAI(int winner)
    {
        String message = switch (winner) {
            case -1 -> "It's a draw :-(";
            case 1 -> "You won!";
            case 2 -> "Computer won!";
            default -> "There is some error.";
        };
        lblPlayer.setText(message);
    }


    private void clearBoard()
    {
        for(Node n : gridPane.getChildren())
        {
            Button btn = (Button) n;
            btn.setText("");
        }
        stopGame=false;

        GameBoard gameBoard = new GameBoard();
        gameBoard.player =1; // its needed for AI
    }

}
