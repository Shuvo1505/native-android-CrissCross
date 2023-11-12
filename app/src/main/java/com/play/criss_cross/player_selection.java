package com.play.criss_cross;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class player_selection extends AppCompatActivity {
    ImageView cont_bt;
    EditText pl1, pl2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_selection);


        cont_bt = findViewById(R.id.cont);
        pl1 = findViewById(R.id.input_pname1);
        pl2 = findViewById(R.id.input_pname2);

        cont_bt.setOnClickListener(new View.OnClickListener() {
            final Animation bounce = AnimationUtils.loadAnimation(player_selection.this, R.anim.bounce);

            @Override
            public void onClick(View v) {
                if (pl1.getText().toString().length() > 13 || pl2.getText().toString().length() > 13) {
                    AlertDialog.Builder out_bound = new AlertDialog.Builder(player_selection.this);
                    out_bound.setMessage("Player names shouldn't be greater than 13 !").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            pl1.setText("");
                            pl2.setText("");
                        }
                    });
                    AlertDialog out_bound_alert = out_bound.create();
                    out_bound_alert.show();
                } else if (pl1.getText().toString().equals("") || pl2.getText().toString().equals("")) {
                    AlertDialog.Builder out_bound = new AlertDialog.Builder(player_selection.this);
                    out_bound.setMessage("Player names shouldn't be empty !").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog out_bound_alert = out_bound.create();
                    out_bound_alert.show();
                } else if (pl1.getText().toString().equals(pl2.getText().toString())) {
                    AlertDialog.Builder out_bound = new AlertDialog.Builder(player_selection.this);
                    out_bound.setMessage("Same player names are not accepted !").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog out_bound_alert = out_bound.create();
                    out_bound_alert.show();
                } else {
                    cont_bt.startAnimation(bounce);
                    Intent player_confirm = new Intent(player_selection.this, GameLogic.class);
                    player_confirm.putExtra("player1", pl1.getText().toString());
                    player_confirm.putExtra("player2", pl2.getText().toString());
                    startActivity(player_confirm);
                    overridePendingTransition(R.anim.slide_right, R.anim.fade_out);
                    finish();
                }
            }
        });
    }
}