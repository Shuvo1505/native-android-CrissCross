package com.play.criss_cross;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class player_confirmation extends AppCompatActivity {
    ImageView play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_confirmation);

        play = findViewById(R.id.play_btn);

        Intent splash = new Intent(player_confirmation.this, splash_screen.class);
        startActivity(splash);

        play.setOnClickListener(new View.OnClickListener() {
            final Animation bounce = AnimationUtils.loadAnimation(player_confirmation.this, R.anim.bounce);

            @Override
            public void onClick(View v) {
                play.startAnimation(bounce);
                Intent game_scr = new Intent(getApplicationContext(), player_selection.class);
                startActivity(game_scr);
                overridePendingTransition(R.anim.slide_right, R.anim.fade_out);
                finish();
            }
        });
    }
}