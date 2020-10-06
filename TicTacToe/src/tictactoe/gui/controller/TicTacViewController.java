/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.gui.controller;

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

/**
 *
 * @author Stegger
 */
public class TicTacViewController implements Initializable
{

    @FXML
    private Label lblPlayer;

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
    {
        try
        {
            Integer row = GridPane.getRowIndex((Node) event.getSource());
            Integer col = GridPane.getColumnIndex((Node) event.getSource());
            int r = (row == null) ? 0 : row;
            int c = (col == null) ? 0 : col;
            int player = game.getNextPlayer();
            if (game.play(c, r) )
            {
                if(stopGame==false) {
                    Button btn = (Button) event.getSource();
                    String xOrO = player == 1 ? "X" : "O";
                    btn.setText(xOrO);
                }
                if (game.isGameOver())
                {
                    int winner = game.getWinner();
                    displayWinner(winner);
                    stopGame = true;
                }
                else
                {
                    game.setPlayer(game.getNextPlayer());
                    setPlayer();
                }
            }
            /*if(game.isGameOver()){
                Button btn = (Button) event.getSource();
                String xOrO = player == 1 ? "X" : "O";
                btn.setText(xOrO);
            int winner = game.getWinner();
            displayWinner(winner);

             */


        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
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
        setPlayer();
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

    private void clearBoard()
    {
        for(Node n : gridPane.getChildren())
        {
            Button btn = (Button) n;
            btn.setText("");
        }
        stopGame=false;
    }

}
