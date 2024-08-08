package com.engrand.lepregonzo_luckyhot;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class Sound {

    private static final int MAX_STREAMS = 5;
    private static final int SOUND_QUALITY = 0;

    public static int soundFellDown, soundSpinEnd;

    private SoundPool soundPool;
    private AudioManager audioManager;
    private Context context;

    public Sound(Context context) {
        this.context = context;
        initSoundPool();

        soundFellDown = loadSound(R.raw.sound_fell_down);
        soundSpinEnd = loadSound(R.raw.sound_spin_end);
    }

    private void initSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(MAX_STREAMS)
                    .setAudioAttributes(attributes)
                    .build();
        } else {
            soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, SOUND_QUALITY);
        }
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public int loadSound(int resId) {
        return soundPool.load(context, resId, 1);
    }

    public void playSound(int soundId) {
        float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) /
                audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        soundPool.play(soundId, volume, volume, 1, 0, 1.0f);
    }

    public void release() {
        soundPool.release();
    }

}