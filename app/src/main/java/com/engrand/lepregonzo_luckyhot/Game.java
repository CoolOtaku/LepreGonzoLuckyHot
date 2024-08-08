package com.engrand.lepregonzo_luckyhot;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import java.util.Random;

public class Game implements Runnable {

    public static Random RAND = new Random();
    public static Handler HAND = new Handler();
    public static float CREDIT, BET, WIN;

    private static SharedPreferences data;
    private static SharedPreferences.Editor editorData;

    private Slots slots;
    private Controls controls;

    public Game(Context context, Slots slots, Controls controls) {
        data = context.getSharedPreferences("gameData", Context.MODE_PRIVATE);
        editorData = data.edit();

        CREDIT = data.getFloat("CREDIT", 100);
        BET = 2;
        if (CREDIT < BET) BET = CREDIT;
        WIN = 0;

        this.slots = slots;
        this.controls = controls;
    }

    @Override
    public void run() {
        update();
        HAND.postDelayed(this, 30);
    }

    private void update() {
        if (CREDIT <= 0) CREDIT = 100;
        if (BET <= 1) BET = 1;
        if (BET >= CREDIT) BET = CREDIT;

        slots.update();
        controls.update(slots.isSpin);
    }

    public static void saveData() {
        editorData.putFloat("CREDIT", CREDIT);
        editorData.apply();
    }

}