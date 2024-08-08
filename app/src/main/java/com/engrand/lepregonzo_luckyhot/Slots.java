package com.engrand.lepregonzo_luckyhot;

import static com.engrand.lepregonzo_luckyhot.Game.BET;
import static com.engrand.lepregonzo_luckyhot.Game.CREDIT;
import static com.engrand.lepregonzo_luckyhot.Game.RAND;
import static com.engrand.lepregonzo_luckyhot.Game.WIN;
import static com.engrand.lepregonzo_luckyhot.Game.saveData;
import static com.engrand.lepregonzo_luckyhot.GameActivity.sound;
import static com.engrand.lepregonzo_luckyhot.Sound.soundFellDown;
import static com.engrand.lepregonzo_luckyhot.Sound.soundSpinEnd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.LinkedList;

public class Slots {

    private Context context;
    private Animation animation;

    public boolean isSpin, isFellDown;
    private int colsSpinCount;
    private float startY;

    private LinearLayout slotsView;
    private LinearLayout[] cols = new LinearLayout[5];
    private ImageView[][] slots = new ImageView[5][5];

    public Slots(Context context, LinearLayout slotsView) {
        this.context = context;
        this.animation = AnimationUtils.loadAnimation(context, R.anim.win_animation);
        this.slotsView = slotsView;
        for (int i = 0; i < this.slotsView.getChildCount(); i++) {
            this.cols[i] = (LinearLayout) this.slotsView.getChildAt(i);
            for (int j = 0; j < this.cols[i].getChildCount(); j++) {
                this.slots[i][j] = (ImageView) this.cols[i].getChildAt(j);
            }
        }
        initRandomImages();
    }

    public void update() {
        if (isSpin) {
            if (!isFellDown && cols[colsSpinCount].getY() < slotsView.getY() + slotsView.getHeight()) {
                cols[colsSpinCount].setY(cols[colsSpinCount].getY() + 100);
            } else if (!isFellDown && cols[colsSpinCount].getY() >= slotsView.getY() + slotsView.getHeight()) {
                colsSpinCount++;
                sound.playSound(soundFellDown);
                if (colsSpinCount == cols.length) {
                    isFellDown = true;
                    colsSpinCount = 0;
                    for (LinearLayout i : cols) i.setY(slotsView.getY() - i.getHeight());
                    initRandomImages();
                }
            } else if (isFellDown && cols[colsSpinCount].getY() < startY) {
                cols[colsSpinCount].setY(cols[colsSpinCount].getY() + 100);
            } else if (isFellDown && cols[colsSpinCount].getY() >= startY) {
                cols[colsSpinCount].setY(startY);
                colsSpinCount++;
                sound.playSound(soundSpinEnd);
                if (colsSpinCount == cols.length) {
                    isSpin = false;
                    checkWinningCombination();
                }
            }
        }
    }

    public void spin() {
        if (isSpin) return;

        colsSpinCount = 0;
        startY = cols[colsSpinCount].getY();
        isFellDown = false;
        isSpin = true;
    }

    @SuppressLint("DiscouragedApi")
    private void initRandomImages() {
        for (ImageView[] col : slots) {
            for (ImageView slot : col) {
                int res = RAND.nextInt(8);
                slot.setTag(res);
                slot.setImageResource(context.getResources().getIdentifier("slot_" + res,
                        "drawable", context.getPackageName()));
            }
        }
    }

    private void checkWinningCombination() {
        float coefficient = 0.0f;
        int sizeRow = 2;

        LinkedList<ImageView> temp = new LinkedList<>();

        for (ImageView[] i : slots) {
            LinkedList<ImageView> t = new LinkedList<>();
            for (ImageView j : i) {
                if (t.isEmpty()) t.add(j);
                else if (t.getLast() != null && j.getTag() == t.getLast().getTag()) t.add(j);
                else if (t.size() < sizeRow) {
                    t.clear();
                    t.add(j);
                } else t.add(null);
            }
            if (t.size() < sizeRow) t.clear();
            temp.addAll(t);
        }

        for (int i = 0; i < slots.length; i++) {
            LinkedList<ImageView> t = new LinkedList<>();
            for (int j = 0; j < slots.length; j++) {
                ImageView item = slots[j][i];
                if (t.isEmpty()) t.add(item);
                else if (t.getLast() != null && item.getTag() == t.getLast().getTag()) t.add(item);
                else if (t.size() < sizeRow) {
                    t.clear();
                    t.add(item);
                } else t.add(null);
            }
            if (t.size() < sizeRow) t.clear();
            temp.addAll(t);
        }

        for (ImageView i : temp) {
            if (i != null) {
                i.startAnimation(animation);
                coefficient += 0.5f;
            }
        }
        WIN = BET * coefficient;
        CREDIT+=WIN;

        saveData();
    }

}