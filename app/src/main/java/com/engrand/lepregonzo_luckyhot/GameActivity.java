package com.engrand.lepregonzo_luckyhot;

import static com.engrand.lepregonzo_luckyhot.Game.BET;
import static com.engrand.lepregonzo_luckyhot.Game.CREDIT;
import static com.engrand.lepregonzo_luckyhot.Game.WIN;
import static com.engrand.lepregonzo_luckyhot.Game.saveData;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    public static Sound sound;
    public static MediaPlayer music;

    private Game game;
    private Slots slots;
    private Controls controls;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        sound = new Sound(getApplicationContext());
        music = MediaPlayer.create(getApplicationContext(), R.raw.music_background);
        music.setLooping(true);

        slots = new Slots(GameActivity.this, findViewById(R.id.slotsView));
        controls = new Controls(findViewById(R.id.controlsView));
        game = new Game(getApplicationContext(), slots, controls);
        game.run();
    }

    @Override
    public void onWindowFocusChanged(boolean isFocus) {
        super.onWindowFocusChanged(isFocus);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }

    @Override
    protected void onResume() {
        music.start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        music.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        saveData();
        sound.release();
        music.release();
        super.onDestroy();
    }

    public void onMinus(View view) {
        Animation.playAnimation(view);
        BET -=2;
    }

    public void onSpin(View view) {
        Animation.playAnimation(view);
        if (CREDIT != 0 && BET <= CREDIT) {
            CREDIT -= BET;
            saveData();
            WIN = 0;
            slots.spin();
        }
    }

    public void onPlus(View view) {
        Animation.playAnimation(view);
        BET +=2;
    }

    public void onExit(View view) {
        Animation.playAnimation(view);
        saveData();
        System.exit(0);
    }

    public void onMusic(View view) {
        Animation.playAnimation(view);
        if (music.isPlaying()) music.pause();
        else music.start();
    }

    public void onPrivacyPolicy(View view) {
        Animation.playAnimation(view);
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(getString(R.string.privacy_policy_url))
        ));
    }

}