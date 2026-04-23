package org.group1.GamePackage.UI.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import org.group1.GamePackage.Music.AudioManager;
import org.group1.GamePackage.UI.PauseInterface;

public class PauseController {
    private PauseInterface menu;
    @FXML
    private VBox audioMenu;

    @FXML
    private VBox optionsMenu;

    @FXML
    private VBox pauseMenu;

    @FXML
    private Slider soundVolumeSlider;

    @FXML
    private Slider musicVolumeSlider;

    /**
     * Initializes the PauseController by setting up the audio sliders to be able to
     * change the music and sound effects volume
     */
    @FXML
    private void initialize() {
        // Set sliders to current values first
        musicVolumeSlider.setValue(AudioManager.getMusicVolume());
        soundVolumeSlider.setValue(AudioManager.getAudioVolume());

        // Connect Music Slider to music volume
        musicVolumeSlider.valueProperty().addListener((_, _, newVal) -> {
            AudioManager.setMusicVolume(newVal.doubleValue());
        });

        // Connect Sound Slider to the sound effects
        soundVolumeSlider.valueProperty().addListener((_, _, newVal) -> {
            AudioManager.setGlobalSoundVolume(newVal.doubleValue());
        });
    }
    public void setMenu(PauseInterface menu) {
        this.menu = menu;
    }

    /**
     * Resumes the game by calling the PauseInterface's resume method inherited from FXGLMenu
     */
    @FXML
    private void onResume() { menu.resume(); }

    /**
     * switches the pause menu to the options menu scene
     */
    @FXML
    private void onPauseMenu() {
        optionsMenu.setVisible(false);
        audioMenu.setVisible(false);
        pauseMenu.setVisible(true);
    }

    /**
     * switches the pause menu to the options menu scene
     */
    @FXML
    private void onOptions() {
        optionsMenu.setVisible(true);
        audioMenu.setVisible(false);
        pauseMenu.setVisible(false);
    }

    /**
     * switches the options menu to the audio menu scene
     */
    @FXML
    private void onAudio() {
        audioMenu.setVisible(true);
        optionsMenu.setVisible(false);
        pauseMenu.setVisible(false);
    }

    /**
     * Exits the game to the main menu
     * using the PauseInterface's exitToMainMenu method inherited from FXGLMenu
     */
    @FXML
    private void onMainMenu() { menu.exitToMainMenu(); }
}
