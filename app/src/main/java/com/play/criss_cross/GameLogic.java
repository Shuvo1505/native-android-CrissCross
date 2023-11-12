package com.play.criss_cross;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class GameLogic extends AppCompatActivity {
    int[] emptyState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winVal = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    boolean runGame = true;
    int val = 1;
    private TextView t1, t2, t3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_screen);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
        t2 = findViewById(R.id.f_p);
        t3 = findViewById(R.id.f_p2);
        Intent fetch_turn_pl = getIntent();
        String first_turn_pl = fetch_turn_pl.getStringExtra("player1");
        String second_turn_pl = fetch_turn_pl.getStringExtra("player2");
        t2.setText(first_turn_pl);
        t3.setText(second_turn_pl);
    }

    public void mark(View view) {
        Intent fetch_turn_pl = getIntent();
        String first_turn_pl = fetch_turn_pl.getStringExtra("player1");
        String second_turn_pl = fetch_turn_pl.getStringExtra("player2");
        t1 = findViewById(R.id.turn_pl);
        ImageView imageView = (ImageView) view;
        int index = Integer.parseInt(imageView.getTag().toString());
        if (val == 1 && emptyState[index] == 2 && runGame) {
            Animation anim = AnimationUtils.loadAnimation(GameLogic.this, R.anim.bounce);
            imageView.startAnimation(anim);
            imageView.setImageResource(R.drawable.close);
            val = 0;
            emptyState[index] = val;
        } else if (val == 0 && emptyState[index] == 2 && runGame) {
            Animation anim = AnimationUtils.loadAnimation(GameLogic.this, R.anim.bounce);
            imageView.startAnimation(anim);
            imageView.setImageResource(R.drawable.circle);
            val = 1;
            emptyState[index] = val;
        } else {
            int count = 0;
            for (int draw : emptyState) {
                if (draw == 2) {
                    count++;
                    break;
                }
            }
            if (count == 0 && runGame) {
                AlertDialog.Builder draw = new AlertDialog.Builder(GameLogic.this);
                final View customlay = getLayoutInflater().inflate(R.layout.draw_dialog, null);
                draw.setView(customlay);
                Button bt = customlay.findViewById(R.id.draw_button);
                AlertDialog match_draw = draw.create();
                match_draw.show();
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        match_draw.dismiss();
                    }
                });
                runGame = false;
            }
        }
        t2 = findViewById(R.id.f_p);
        t3 = findViewById(R.id.f_p2);
        for (int[] win : winVal) {
            if (emptyState[win[0]] == emptyState[win[1]] && emptyState[win[1]] == emptyState[win[2]] && emptyState[win[0]] != 2) {
                runGame = false;
                if (val == 1) {
                    AlertDialog.Builder circle_win = new AlertDialog.Builder(GameLogic.this);
                    final View customlay = getLayoutInflater().inflate(R.layout.win_dialog, null);
                    circle_win.setView(customlay);
                    TextView t1 = customlay.findViewById(R.id.winner_name);
                    Button bt = customlay.findViewById(R.id.winner_button);
                    t1.setText(second_turn_pl + " Won The Match !");
                    AlertDialog win_circle = circle_win.create();
                    win_circle.show();
                    bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            win_circle.dismiss();
                        }
                    });
                } else if (val == 0) {
                    AlertDialog.Builder cross_win = new AlertDialog.Builder(GameLogic.this);
                    final View customlay = getLayoutInflater().inflate(R.layout.win_dialog, null);
                    cross_win.setView(customlay);
                    TextView t1 = customlay.findViewById(R.id.winner_name);
                    Button bt = customlay.findViewById(R.id.winner_button);
                    t1.setText(first_turn_pl + " Won The Match !");
                    AlertDialog win_cross = cross_win.create();
                    win_cross.show();
                    bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            win_cross.dismiss();
                        }
                    });
                }
                t1.setText("Game over !");
            }
        }
    }

    public void refreshBoard() {
        Animation anim = AnimationUtils.loadAnimation(GameLogic.this, R.anim.bounce);
        t1.setText("Tap to play !");
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageDrawable(null);
        }
        for (int i = 0; i < emptyState.length; i++) {
            emptyState[i] = 2;
        }
        runGame = true;
        val = 1;
    }

    public void closeGame(View view) {
        Animation anim = AnimationUtils.loadAnimation(GameLogic.this, R.anim.bounce);
        ImageView closeBtn = findViewById(R.id.close);
        closeBtn.startAnimation(anim);
        AlertDialog.Builder alert = new AlertDialog.Builder(GameLogic.this);
        alert.setMessage("Do you want to quit from this game ?").setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent startMain = new Intent(GameLogic.this, player_selection.class);
                startActivity(startMain);
                finish();
            }
        });
        AlertDialog close_alert = alert.create();
        close_alert.show();
    }

    public void about(View view) {
        Animation anim = AnimationUtils.loadAnimation(GameLogic.this, R.anim.bounce);
        ImageView about_bt = findViewById(R.id.about);
        about_bt.startAnimation(anim);
        AlertDialog.Builder about_box = new AlertDialog.Builder(GameLogic.this);
        final View customlay = getLayoutInflater().inflate(R.layout.about_app, null);
        about_box.setView(customlay);
        ImageView back_bt = customlay.findViewById(R.id.back);
        AlertDialog ab_oo = about_box.create();
        ab_oo.show();
        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ab_oo.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(GameLogic.this);
        alert.setMessage("Do you want to quit from this game ?").setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent startMain = new Intent(GameLogic.this, player_selection.class);
                Toast.makeText(getApplicationContext(), "Game Cancelled !", Toast.LENGTH_SHORT).show();
                startActivity(startMain);
                finish();
            }
        });
        AlertDialog close_alert = alert.create();
        close_alert.show();
    }
}