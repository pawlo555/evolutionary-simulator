package GUI.Controllers;

import GUI.ISimulationObserver;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import main.SimulationEngine;

public class MapButtonsController implements ISimulationObserver {

    @FXML private Label epoch;

    private SimulationEngine engine1;
    private SimulationEngine engine2;
    private Timeline timeline;

    public void updateEpochLabel() {
        epoch.setText(Integer.toString(engine1.getCurrentEpoch()));
    }

    public void setFirstEngine(SimulationEngine engine) {
        this.engine1 = engine;
        setTimeline();
    }

    public void setSecondEngine(SimulationEngine engine) {
        this.engine2 = engine;
    }

    public void setTimeline() {
        this.timeline = new Timeline(getAppropriateKeyFrame(1000));
        timeline.setCycleCount(Animation.INDEFINITE);
    }
    @Override
    public void nextEpochRendered() {
        updateEpochLabel();
    }

    public void pauseButton() {
        timeline.pause();
    }

    public void playButton() {
        changeSpeed(1000);
        timeline.play();
    }

    public void halfSpeedButton() {
        changeSpeed(2000);
        timeline.play();
    }

    public void doubleSpeedButton() {
        changeSpeed(500);
        timeline.play();
    }

    public void quadrupleSpeedButton() {
        changeSpeed(250);
        timeline.play();
    }

    private void changeSpeed(int milliSeconds) {
        timeline.stop();
        timeline.getKeyFrames().setAll(getAppropriateKeyFrame(milliSeconds));
    }

    private KeyFrame getAppropriateKeyFrame(int milliSeconds) {
        if (engine1 == null && engine2 == null) {
            return new KeyFrame(
                    Duration.millis(milliSeconds),
                    ae -> System.out.println("Lack of engines!"));
        }
        else if (engine2 == null) {
            return new KeyFrame(
                    Duration.millis(milliSeconds),
                    ae -> engine1.nextEpoch());
        }
        else {
            return new KeyFrame(
                    Duration.millis(milliSeconds),
                    ae ->  { engine1.nextEpoch(); engine2.nextEpoch(); });
        }
    }

}
