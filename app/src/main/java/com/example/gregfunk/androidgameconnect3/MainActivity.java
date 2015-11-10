package com.example.gregfunk.androidgameconnect3;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    // 0 = player 1, 1 = player 2
    int activePlayer = 0;

    boolean gameIsActive = true;

    // 2 = unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {
        {0,1,2},
        {3,4,5},
        {6,7,8},
        {0,3,6},
        {1,4,7},
        {2,5,8},
        {0,4,8},
        {2,4,6}
    };

    public void showPlayerTurn() {
        TextView playerTurnText = (TextView) findViewById(R.id.playerTurnText);
        playerTurnText.setText("Player " + (activePlayer + 1) + "'s turn");

    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int slot = Integer.parseInt(counter.getTag().toString());

        if (gameState[slot] == 2 && gameIsActive) {
            gameState[slot] = activePlayer;

            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.ana);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.pug);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).rotation(360f).setDuration(300);

            //check if player won
            int winner = 2;  // 2 == no one
            for(int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {
                    winner = gameState[winningPosition[0]];
                }
            }
            if (winner < 2) {
                gameIsActive = false;

                TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                winnerMessage.setText("Player " + (winner + 1) + " wins!");

                LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                layout.setVisibility(View.VISIBLE);
            } else {
                showPlayerTurn();

                //check if it's a draw
                boolean gameIsOver = true;
                for(int counterState : gameState) {
                    if (counterState == 2) {
                        gameIsOver = false;
                    }
                }
                if (gameIsOver) {
                    gameIsActive = false;

                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText("It's a tie!");

                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void playAgain(View view) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        // 0 = player 1, 1 = player 2
        activePlayer = 0;

        // 2 = unplayed
        for (int i=0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        // remove tiles from all slots
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for (int i=0; i < gridLayout.getChildCount(); i++) {
            ImageView slot = (ImageView) gridLayout.getChildAt(i);
            slot.setImageResource(0);
        }

        showPlayerTurn();

        gameIsActive = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showPlayerTurn();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
