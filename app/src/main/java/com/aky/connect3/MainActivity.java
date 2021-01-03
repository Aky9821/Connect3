package com.aky.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.gridlayout.widget.GridLayout;

import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myV = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        Spinner mode = findViewById(R.id.modeSpinner);
        String[] list = {"2 Player"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        mode.setAdapter(adapter);
    }

    int activePlayer = 0;
    //0-fire 1-water
    int gamestate[] = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int winningPositions[][] = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    boolean gameActive = true;
    Vibrator myV;
    public void playFunction(View v) {
        ImageView clicked = (ImageView) v;
        int clickedTag = Integer.parseInt(clicked.getTag().toString());
        myV.vibrate(5);
        if (gamestate[clickedTag] == 2 && gameActive) {
            clicked.setTranslationY(-1500);
            if (activePlayer == 0) {
                clicked.setImageResource(R.drawable.fire);
                gamestate[clickedTag] = activePlayer;
                activePlayer = 1;
            } else if (activePlayer == 1) {
                clicked.setImageResource(R.drawable.water);
                gamestate[clickedTag] = activePlayer;
                activePlayer = 0;
            }
            clicked.animate().translationYBy(1500).rotation(3600).setDuration(300);
            TextView winner = findViewById(R.id.winnerTextView);
            Button playAgain = findViewById(R.id.playAgainButton);
            for (int[] x : winningPositions) {
                if (gamestate[x[0]] == gamestate[x[1]] && gamestate[x[1]] == gamestate[x[2]] && gamestate[x[0]] != 2) {
                    playAgain.setVisibility(View.VISIBLE);
                    winner.setVisibility(View.VISIBLE);
                    if (activePlayer == 1) {
                        winner.setTextColor(Color.rgb(255, 68, 68));
                        winner.setText("Fire WINS");
                        TextView fire = findViewById(R.id.fireScore);
                        fire.setText((Integer.parseInt(fire.getText().toString()) + 1) + "");
                        gameActive = false;
                    } else if (activePlayer == 0) {
                        winner.setTextColor(Color.rgb(51, 181, 229));
                        winner.setText("Water WINS");
                        TextView water = findViewById(R.id.waterScore);
                        water.setText((Integer.parseInt(water.getText().toString()) + 1) + "");
                        gameActive = false;
                    }
                }
            }
            if (gameActive) {
                boolean tie = true;
                for (int x : gamestate) {
                    if (x == 2) {
                        tie = false;
                        break;
                    }
                }
                if (tie) {
                    playAgain.setVisibility(View.VISIBLE);
                    winner.setVisibility(View.VISIBLE);
                    winner.setText("Tie!");
                    gameActive = false;
                }
            }
        }
    }

    public void playAgainFunction(View v) {
        myV.vibrate(5);
        Arrays.fill(gamestate, 2);
        activePlayer = 0;
        TextView winner = findViewById(R.id.winnerTextView);
        Button playAgain = findViewById(R.id.playAgainButton);
        playAgain.setVisibility(View.INVISIBLE);
        winner.setVisibility(View.INVISIBLE);
        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout);
        for (int x = 0; x < grid.getChildCount(); x++) {
            ImageView child = (ImageView) grid.getChildAt(x);
            child.setImageDrawable(null);
        }
        gameActive = true;
    }
}