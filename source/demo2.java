import java.util.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;
//import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;

//import javax.sound.sampled.AudioInputStream;
 //import javax.sound.sampled.Clip;
//import javax.sound.sampled.AudioSystem;


/**
 * A class modelling a tic-tac-toe ( Xs and Os) game.
 * 
 */

public class demo2
{
   public static final String PLAYER_X = "X"; // player using "X"
   public static final String PLAYER_O = "O"; // player using "O"
   public static final String EMPTY = " ";  // empty cell
   public static final String TIE = "T"; // game ended in a tie
   
 
   private String player;   // current player (PLAYER_X or PLAYER_O)
   private JMenuItem newGame;   //new game menu
   private JMenuItem resetScore;    //reset score menu
   private JMenuItem quit;      //quit menu
  

   private JButton board[][]; // 3x3 array representing the board  
   
   
   private JLabel status; // displays the game's status (turn, winner or tie)
   private JTextArea score; // displays score
   private int Xwins = 0;
   private int Owins = 0;
   private int numFreeSquares = 9; // number of squares still free
   
   /** 
    * Constructs a new Tic-Tac-Toe GUI with game board, status, and menu bar. Board is a 2d array of Jbuttons.
    */
   public demo2()
   {
       JFrame frame = new JFrame("Tic Tac Toe");  
       frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Admin\\Desktop\\food\\images\\almighty-tic-tac-toe-2015-06-04.png"));
       frame.setSize(500,500);
       frame.setLocationRelativeTo(null);

       JMenuBar menuBar = new JMenuBar();
       menuBar.setBorderPainted(false);
       menuBar.setForeground(Color.WHITE);
       menuBar.setBackground(new Color(243, 236, 194));
       JMenu menu = new JMenu("Menu");
       
 
       newGame = new JMenuItem("New Game");
       newGame.setOpaque(true);
       newGame.setBackground(new Color(243, 236, 194));
       newGame.addActionListener(new MenuListener());
       newGame.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
       
       
       resetScore = new JMenuItem("Reset Score");
       resetScore.setOpaque(true);
       resetScore.setBackground(new Color(243, 236, 194));
       resetScore.addActionListener(new MenuListener());
       resetScore.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
       
     
       quit = new JMenuItem("Quit");
       quit.addActionListener(new MenuListener());
       quit.setOpaque(true);
       quit.setBackground(new Color(243, 236, 194));
       quit.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
       
       
       
       menu.add(newGame);
       menu.add(resetScore);
       menu.add(quit);
       menuBar.add(menu);
       
       frame.setJMenuBar(menuBar);
       
       board = new JButton[3][3];
       
       JPanel mainPanel = new JPanel(new BorderLayout());
       mainPanel.setPreferredSize(new Dimension(475,475));
       frame.getContentPane().add(mainPanel);
       
       
       JPanel gamePanel = new JPanel(new GridLayout(3,3,9,9));
       gamePanel.setBorder(new LineBorder(new Color(128, 128, 128), 9));
       gamePanel.setBackground(Color.GRAY);
       gamePanel.setPreferredSize(new Dimension(200,200));
       
       player = PLAYER_X;
       
       
       status = new JLabel("Player  " + player + "'s turn");
       status.setHorizontalAlignment(SwingConstants.CENTER);
       status.setForeground(new Color(143, 173, 136));
       status.setFont(new Font("Arial", Font.BOLD, 20));
       status.setPreferredSize(new Dimension(100,50));
       
       
       score = new JTextArea("\n\n\n\n\n  Wins: \n   X: \n   O: ");
       score.setBackground(new Color(143, 173, 136));
       score.setForeground(new Color(243, 236, 194));
       score.setFont(new Font("Arial", Font.BOLD, 24));
       score.setPreferredSize(new Dimension(100,50));
       score.setEditable(false);
       
       
       
       mainPanel.add(gamePanel, BorderLayout.CENTER);
       mainPanel.add(status, BorderLayout.SOUTH);
       mainPanel.add(score, BorderLayout.EAST);
       mainPanel.setBackground(new Color(243, 236, 194));
       
       initializeBoard(gamePanel);
       
       frame.setVisible(true);
   }
   
   /**
    * Initializes the tic tac toe board (2d array of JButtons) and adds an actionlistener for each button.
    * 
    *gamePanel  The JPanel which contains the 3x3 gridLayout of JButtons.
    */
   private void initializeBoard(JPanel gamePanel)
   {
       for(int i = 0; i < 3; i++) {                      //Create 2D array of JButtons
           for(int j = 0; j < 3; j++) {

        	   board[i][j] = new JButton();                
               board[i][j].setText("");
               board[i][j].setFont(new Font("Arial", Font.PLAIN, 80));
               board[i][j].setVisible(true);
               gamePanel.add(board[i][j]); 
               board[i][j].addActionListener(new ButtonListener());   //Add listener to button
           }
       }
       board[0][0].setBackground(new Color(249, 213, 110));
       board[0][1].setBackground(new Color(232, 80, 91));
       board[0][2].setBackground(new Color(249, 213, 110));
       board[1][0].setBackground(new Color(232, 80, 91));
       board[1][1].setBackground(new Color(249, 213, 110));
       board[1][2].setBackground(new Color(232, 80, 91));
       board[2][0].setBackground(new Color(249, 213, 110));
       board[2][1].setBackground(new Color(232, 80, 91));
       board[2][2].setBackground(new Color(249, 213, 110));
       for(int i = 0; i < 3; i++) {                      //Create 2D array of JButtons
           for(int j = 0; j < 3; j++) {
        	   gamePanel.add(board[i][j]);        	   
           }
          }
       }
       
   

   /**
    *   Marks all squares in the Tic Tac Toe board as empty,
    *  indicates no winner yet, 9 free squares and the current player is player X.
    */
   private void clearBoard()
   {
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
        	 
            board[i][j].setText("");
            board[i][j].setEnabled(true);
         }
      }
      numFreeSquares = 9;
      player = PLAYER_X;     // Player X always has the first turn.
      status.setText("Player_" + player + "'s turn");
      
   }


   /**
    * Returns true if filling the given square gives us a winner, and false
    * otherwise.
    *
    *  int row of square just set
    *  int col of square just set
    * 
    * return true if we have a winner, false otherwise
    */
   private boolean haveWinner(int row, int col) 
   {
      

       if (numFreeSquares>4) return false;

       // check row "row"
       if ( board[row][0].getText().equals(board[row][1].getText()) &&
            board[row][0].getText().equals(board[row][2].getText()) ) return true;
       
       // check column "col"
       if ( board[0][col].getText().equals(board[1][col].getText()) &&
            board[0][col].getText().equals(board[2][col].getText()) ) return true;

       // if row=col check one diagonal
       if (row==col)
          if ( board[0][0].getText().equals(board[1][1].getText()) &&
               board[0][0].getText().equals(board[2][2].getText()) ) return true;

       // if row=2-col check other diagonal
       if (row==2-col)
          if ( board[0][2].getText().equals(board[1][1].getText()) &&
               board[0][2].getText().equals(board[2][0].getText()) ) return true;

       // no winner yet
       return false;
   }
   
    
   /**
    * Main function creates an new Tic Tac Toe GUI Frame to run the game.
    */ 
   public static void main(String[] args) {
	   
       new demo2();
   }
   
   /**
    * Private class ButtonListener handles the case of a JButton being pressed.
    */
   private class ButtonListener implements ActionListener {
       /**
        * When a JButton is pressed, this method disables that button, checks for a winner.
        * It then adjusts the game status to show the current turn, winner or displays a tie.
        * It prints the record of wins for each player.
        * 
        *  e  The ActionEvent which was triggered by the JButton that was pressed.
        */
       public void actionPerformed(ActionEvent e) {
           int r = 0;
           int c = 0;
           for (int i = 0; i < 3; i++) {
               for (int j = 0; j < 3; j++) {
                   if (e.getSource() == board[i][j]) {
                	   
                	   //play sound when click button
                	   try {
             		      AudioClip a = Applet.newAudioClip(new URL("file:click.wav" ));
             		      a.play();
             		      }
             		    catch (Exception error) {
             		      System.out.println(error);
             		    }
             		                  	   
                       board[i][j].setText(player);
                       board[i][j].setEnabled(false);
                       numFreeSquares--;
                       r = i;
                       c = j;
                       break;
                   }
               }
           }

           if (haveWinner(r,c)) {
        	   //if a player won       	   
               status.setText("Player_" + player + " wins!");
               //play win sound
               try {     		       
       		      AudioClip b = Applet.newAudioClip(new URL("file:win.wav"));
       		      b.play();
       		      }
       		    catch (Exception error) {
       		      System.out.println(error);
       		    }
               // set score
               if (player.equals(PLAYER_X)) Xwins ++;
               else Owins++;
               score.setText("\n\n\n\n\n  Wins: \n   X: " + Xwins + "\n   O: " + Owins);
               
               
               for (int i = 0; i < 3; i++) {
                   for (int j = 0; j < 3; j++) {
                       board[i][j].setEnabled(false);
                   }
               }
           }
           else if (!haveWinner(r,c) && numFreeSquares == 0) {   //if there is a tie;
               status.setText("Tie Game!");
           }
           else {
               if (player==PLAYER_X) player=PLAYER_O;
               else player=PLAYER_X;
               status.setText("Player_" + player + "'s turn");
           }
       }
   }
    
   /**
    * Private class MenuListener handles the case of a JMenuItem being selected or triggered by its keyboard shortcut.
    */
   private class MenuListener implements ActionListener {
       /**
        * This method terminates the program if the JMenuItem selected is Quit or if ctrl-q is entered.
        * The method resets the game if the New Game menu item is selected or if ctrl+n is inputed.
        * 
        *  The ActionEvent which was triggered by the JMenuItem that was pressed.
        */ 
       public void actionPerformed(ActionEvent e) {
            if (e.getSource() == quit) System.exit(0);
            else if (e.getSource() == resetScore) {
                Xwins = 0;
                Owins = 0;
                score.setText("\n\n\n\n\n  Wins: \n   X: " + Xwins + "\n   O: " + Owins);
                clearBoard();
            }
            else if (e.getSource() == newGame) clearBoard();
        }
     
    }
}

