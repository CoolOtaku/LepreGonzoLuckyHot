package com.engrand.lepregonzo_luckyhot;

import static com.engrand.lepregonzo_luckyhot.Game.BET;
import static com.engrand.lepregonzo_luckyhot.Game.CREDIT;
import static com.engrand.lepregonzo_luckyhot.Game.WIN;
import static com.engrand.lepregonzo_luckyhot.GameActivity.music;

import android.annotation.SuppressLint;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Controls {

    private DecimalFormat decimalFormat = new DecimalFormat("$00.00");

    private TextView txtCredit, txtBet, txtWin;
    private ImageButton btnMinus, btnSpin, btnPlus;
    private ImageButton btnExit, btnMusic, btnPrivacyPolicy;

    @SuppressLint("WrongViewCast")
    public Controls(LinearLayout controlsView) {
        this.txtCredit = controlsView.findViewById(R.id.txtCredit);
        this.txtBet = controlsView.findViewById(R.id.txtBet);
        this.txtWin = controlsView.findViewById(R.id.txtWin);

        this.btnMinus = controlsView.findViewById(R.id.btnMinus);
        this.btnSpin = controlsView.findViewById(R.id.btnSpin);
        this.btnPlus = controlsView.findViewById(R.id.btnPlus);

        this.btnExit = controlsView.findViewById(R.id.btnExit);
        this.btnMusic = controlsView.findViewById(R.id.btnMusic);
        this.btnPrivacyPolicy = controlsView.findViewById(R.id.btnPrivacyPolicy);
    }

    public void update(boolean isSpin) {
        if (isSpin && btnSpin.isEnabled()) {
            btnMinus.setEnabled(false);
            btnSpin.setEnabled(false);
            btnPlus.setEnabled(false);
            btnExit.setEnabled(false);
            btnPrivacyPolicy.setEnabled(false);

            btnMinus.setAlpha(0.3f);
            btnSpin.setAlpha(0.3f);
            btnPlus.setAlpha(0.3f);
            btnExit.setAlpha(0.3f);
            btnPrivacyPolicy.setAlpha(0.3f);
        } else if (!isSpin && !btnSpin.isEnabled()) {
            btnMinus.setEnabled(true);
            btnSpin.setEnabled(true);
            btnPlus.setEnabled(true);
            btnExit.setEnabled(true);
            btnPrivacyPolicy.setEnabled(true);

            btnMinus.setAlpha(1.0f);
            btnSpin.setAlpha(1.0f);
            btnPlus.setAlpha(1.0f);
            btnExit.setAlpha(1.0f);
            btnPrivacyPolicy.setAlpha(1.0f);
        }
        if (music.isPlaying() && btnMusic.getAlpha() != 1.0f) {
            btnMusic.setAlpha(1.0f);
            btnMusic.setBackgroundResource(R.drawable.icon_music_on);
        } else if (!music.isPlaying() && btnMusic.getAlpha() == 1.0f) {
            btnMusic.setAlpha(0.5f);
            btnMusic.setBackgroundResource(R.drawable.icon_music_off);
        }
        txtCredit.setText(decimalFormat.format(CREDIT));
        txtBet.setText(decimalFormat.format(BET));
        txtWin.setText(decimalFormat.format(WIN));
    }

}