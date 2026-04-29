package org.group1.GamePackage.Music;

import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.dsl.FXGL;

public class AudioManager {
    private static double audioVolume = 20.0;
    private static double musicVolume = 0.1;

    public static void setGlobalSoundVolume(double newVolume) {
        audioVolume = newVolume;
        FXGL.getSettings().setGlobalSoundVolume(newVolume);
    }
    public static void setMusicVolume(double newVolume) {
        musicVolume = newVolume;
        FXGL.getSettings().setGlobalMusicVolume(newVolume);
    }

    public void playBackgroundMusic() {
        Music music = FXGL.getAssetLoader().loadMusic("main_music.wav");
        FXGL.getAudioPlayer().loopMusic(music);
        music.getAudio().setVolume(musicVolume);
    }

    public void playDeathSound() {
        Sound sound = FXGL.getAssetLoader().loadSound("death_sound.wav");
        FXGL.getAudioPlayer().playSound(sound);
        sound.getAudio().setVolume(audioVolume);
    }

    public void playHeartGain() {
        Sound sound = FXGL.getAssetLoader().loadSound("heart_gain.wav");
        FXGL.getAudioPlayer().playSound(sound);
        sound.getAudio().setVolume(audioVolume);
    }

    public static void bossDie() {
        FXGL.play("boss_die.wav");
    }

    public static void switchBullet() {
        FXGL.play("switch_bullet.wav");
    }

    public void playGameOver() {
        //TODO: Sound effect for gameover
    }

    public void speedUpSound() {
        FXGL.play("speed.wav");
    }

    public void potionDrink() {
        FXGL.play("potion_drink.wav");
    }


    public void FAH() {
        FXGL.play("fah.wav");
    }

    // stop loop
    public static void stopAll() {
        FXGL.getAudioPlayer().stopAllMusic();
        FXGL.getAudioPlayer().stopAllSounds();
    }

    //getters
    public static double getMusicVolume() {return musicVolume;}
    public static double getAudioVolume() {return audioVolume;}
}
