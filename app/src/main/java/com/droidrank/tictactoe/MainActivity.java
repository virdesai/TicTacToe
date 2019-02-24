package com.droidrank.tictactoe;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{



    Button block1, block2, block3, block4, block5, block6, block7, block8, block9, restart;
    TextView result;
    private boolean started, ended = false;
    private Button[] buttons;
    private Context context;
    private String player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        block1 = (Button) findViewById(R.id.bt_block1);
        block2 = (Button) findViewById(R.id.bt_block2);
        block3 = (Button) findViewById(R.id.bt_block3);
        block4 = (Button) findViewById(R.id.bt_block4);
        block5 = (Button) findViewById(R.id.bt_block5);
        block6 = (Button) findViewById(R.id.bt_block6);
        block7 = (Button) findViewById(R.id.bt_block7);
        block8 = (Button) findViewById(R.id.bt_block8);
        block9 = (Button) findViewById(R.id.bt_block9);
        result = (TextView) findViewById(R.id.tv_show_result);
        restart = (Button) findViewById(R.id.bt_restart_game);

        context = getApplicationContext();
        buttons = new Button[]{block1, block2, block3, block4, block5, block6, block7, block8, block9};
        player = context.getString(R.string.player_1_move);

        for(int i = 0; i < buttons.length; i++) {
            buttons[i].setText(" ");
            final Button button = buttons[i];
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playerMove(button);
                }
            });
        }

        /**
         * Restarts the game
         */
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.app_name)
                        .setMessage(R.string.restart_message)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                restartGame();
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        });

    }

    private void playerMove(Button button) {
        if(!ended && button.getText() == " ") {
            if(!started) {
                started = true;
                restart.setText(R.string.restart_button_text_in_middle_of_game);
            }
            button.setText(String.valueOf(player));
            switchPlayer();
            checkBoard();
        }
    }

    private void switchPlayer() {
        player = (
                player == context.getString(R.string.player_1_move)
                        ? context.getString(R.string.player_2_move)
                        : context.getString(R.string.player_1_move));
    }

    private void restartGame() {
        started = false;
        ended = false;
        player = context.getString(R.string.player_1_move);
        for(int i = 0; i < buttons.length; i++) {
            buttons[i].setText(" ");
        }
        restart.setText(R.string.restart_button_text_initially);
        result.setText(R.string.empty_space);
    }

    private String getButtonValue(int x, int y) {
        return buttons[3 * y + x].getText().toString();
    }

    private boolean checkBoard() {
        for(int i = 0; i < 3; i++) {
            // vertical check
            if (getButtonValue(i, 0) != " " &&
                    getButtonValue(i, 0) == getButtonValue(i, 1) &&
                    getButtonValue(i, 1) == getButtonValue(i, 2)) {
                ended = true;
                declareWinner(i ,0);
                return ended;
            }

            // horizontal check
            if (getButtonValue(0, i) != " " &&
                    getButtonValue(0, i) == getButtonValue(1, i) &&
                    getButtonValue(1, i) == getButtonValue(2, i)) {
                ended = true;
                declareWinner(0 ,i);
                return ended;
            }
        }

        // diagonal checks
        if(getButtonValue(0,0) != " " &&
                getButtonValue(0,0) == getButtonValue(1, 1) &&
                getButtonValue(1, 1) == getButtonValue(2, 2)) {
            ended = true;
            declareWinner(0 ,0);
            return ended;
        }
        if(getButtonValue(2,0) != " " &&
                getButtonValue(2,0) == getButtonValue(1, 1) &&
                getButtonValue(1, 1) == getButtonValue(0, 2)) {
            ended = true;
            declareWinner(2 ,0);
            return ended;
        }

        ended = true;
        for(int i = 0; i < buttons.length; i++) {
            if (buttons[i].getText() == " ") {
                ended = false;
            }
        }
        if(ended) {
            result.setText(R.string.draw);
        }
        return ended;
    }

    private void declareWinner(int x, int y) {
        if(getButtonValue(x, y) == context.getString(R.string.player_1_move)) {
            result.setText(R.string.player_1_wins);
        } else {
            result.setText(R.string.player_2_wins);
        }
    }

}
