package com.example.gregfunk.androidgameconnect3;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    // 0 = player 1, 1 = player 2
    int activePlayer = 0;

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

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int slot = Integer.parseInt(counter.getTag().toString());

        if (gameState[slot] == 2) {
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
            for(int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {
                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText("Player " + (gameState[winningPosition[0]] + 1) + " wins!");

                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
