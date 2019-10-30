package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

import org.cyberarm.NeXT.StateConfiguration;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

import java.util.List;

public class SkystoneSight extends State {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private VuforiaLocalizer Vuforia;
    private TFObjectDetector TensorFlow;
    private List<Recognition> LastRecognitions;
    private Recognition SkyStone;
    private boolean FirstRun = true;

    public SkystoneSight(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
        this.engine = engine;
        StateConfig = stateConfig;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {

        //Vuforia Init
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = "AcU+kbn/////AAAAGWDmHA7mS0gCoiMy9pA5e1AVyLZeqKejLOtP9c3COfi9g9m4Cs1XuVQVdqRFhyrFkNUynXwrhQyV65hPnPkGgRky9MjHlLLCWuqdpHzDLJonuOSBh5zVO11PleXH+2utK1lCnbBxvOM+/OrB9EAHUBrcB0ItRxjzFQOe8TXrjGGe1IyjC/Ljke3lZf/LVVinej3zjGNqwsNQoZ0+ahxYNPCJOdzRFkXjyMDXJVDQYMtVQcWKpbEM6dJ9jQ9f0UFIVXANJ7CC8ZDyrl2DQ8o4sOX981OktCKWW0d4PH0IwAw/c2nGgt1t2V/7PwTwysBYM1N+SjVpMNRg52u9gNl9os4ulF6AZw+U2LcVj4kqGZDi";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        Vuforia = ClassFactory.getInstance().createVuforia(parameters);

        //Tensor Flow
        int tfodMonitorViewId = engine.hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", engine.hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        TensorFlow = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, Vuforia);
        TensorFlow.loadModelFromAsset("Skystone.tflite", "Stone", "Skystone");

    }

    @Override
    public void exec() throws InterruptedException {
        if (StateConfig.allow(StateConfigID)) {

            if (FirstRun) {
                TensorFlow.activate();
                FirstRun = false;
            }

            List<Recognition> recognitions = TensorFlow.getRecognitions();






        } else {
            engine.telemetry.addData("Skipping Step", StateConfigID);
            engine.telemetry.update();
            sleep(1000);
            setFinished(true);
        }

    }
}
