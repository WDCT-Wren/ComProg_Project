package org.group1.GamePackage.Music;

import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.dsl.FXGL;

public class AudioManager {


    public void playBackgroundMusic() {
        Music music = FXGL.getAssetLoader().loadMusic("main_music.wav");
        FXGL.getAudioPlayer().loopMusic(music);
        music.getAudio().setVolume(0.3);
    }

    public void playDeathSound() {
        Sound sound = FXGL.getAssetLoader().loadSound("death_sound.wav");
        FXGL.getAudioPlayer().playSound(sound);
        sound.getAudio().setVolume(20.0);
    }

    public void playHeartGain() {
        Sound sound = FXGL.getAssetLoader().loadSound("heart_gain.wav");
        FXGL.getAudioPlayer().playSound(sound);
        sound.getAudio().setVolume(20.0);
    }

    public void gameOver() {
        //TODO: Sound effect for gameover
    }

    public void FAH() {
        FXGL.play("fah.wav");
    }

}
