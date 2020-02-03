package org.cyberarm.minibot.states;

import com.vuforia.CameraDevice;

import org.cyberarm.engine.CyberarmEngine;
import org.cyberarm.engine.V2.CyberarmStateV2;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public class SkyStoneTensorFlowTestState extends CyberarmStateV2 {
  private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
  private static final String LABEL_FIRST_ELEMENT = "Stone";
  private static final String LABEL_SECOND_ELEMENT = "Skystone";
  private VuforiaLocalizer vuforia;
  private TFObjectDetector tensorFlow;

  @Override
  public void init() {
    VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

    parameters.vuforiaLicenseKey = "AcU+kbn/////AAAAGWDmHA7mS0gCoiMy9pA5e1AVyLZeqKejLOtP9c3COfi9g9m4Cs1XuVQVdqRFhyrFkNUynXwrhQyV65hPnPkGgRky9MjHlLLCWuqdpHzDLJonuOSBh5zVO11PleXH+2utK1lCnbBxvOM+/OrB9EAHUBrcB0ItRxjzFQOe8TXrjGGe1IyjC/Ljke3lZf/LVVinej3zjGNqwsNQoZ0+ahxYNPCJOdzRFkXjyMDXJVDQYMtVQcWKpbEM6dJ9jQ9f0UFIVXANJ7CC8ZDyrl2DQ8o4sOX981OktCKWW0d4PH0IwAw/c2nGgt1t2V/7PwTwysBYM1N+SjVpMNRg52u9gNl9os4ulF6AZw+U2LcVj4kqGZDi";
    parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;


    vuforia = ClassFactory.getInstance().createVuforia(parameters);

    int tfodMonitorViewId = cyberarmEngine.hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", cyberarmEngine.hardwareMap.appContext.getPackageName());
    TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
    tfodParameters.minimumConfidence = 0.8;
    tensorFlow = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
    tensorFlow.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);

    CameraDevice.getInstance().setFlashTorchMode(true);

    tensorFlow.activate();
  }

  @Override
  public void exec() {

  }

  @Override
  public void telemetry() {
    if (tensorFlow != null) {
      // getUpdatedRecognitions() will return null if no new information is available since
      // the last time that call was made.
//      List<Recognition> updatedRecognitions = tensorFlow.getUpdatedRecognitions();
//      if (updatedRecognitions != null) {
//        cyberarmEngine.telemetry.addData("# Object Detected", updatedRecognitions.size());
//
//        // step through the list of recognitions and display boundary info.
//        int i = 0;
//        for (Recognition recognition : updatedRecognitions) {
//          cyberarmEngine.telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
//          cyberarmEngine.telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
//                  recognition.getLeft(), recognition.getTop());
//          cyberarmEngine.telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
//                  recognition.getRight(), recognition.getBottom());
//          cyberarmEngine.telemetry.addData("Angle to target", recognition.estimateAngleToObject(AngleUnit.DEGREES));
//
//          i++;
//        }
//        cyberarmEngine.telemetry.update();
//      }

      List<Recognition> recognitions = tensorFlow.getRecognitions();
      if (recognitions != null) {
        cyberarmEngine.telemetry.addLine();
        cyberarmEngine.telemetry.addLine("Static Recognitions");

        cyberarmEngine.telemetry.addData("# Object Detected", recognitions.size());

        int i = 0;
        for (Recognition recognition : recognitions) {
          cyberarmEngine.telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
          cyberarmEngine.telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                  recognition.getLeft(), recognition.getTop());
          cyberarmEngine.telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                  recognition.getRight(), recognition.getBottom());
          cyberarmEngine.telemetry.addData("Angle to target", recognition.estimateAngleToObject(AngleUnit.DEGREES));

          i++;
        }

        cyberarmEngine.telemetry.update();
      }
    }
  }

  @Override
  public void stop() {
    CameraDevice.getInstance().setFlashTorchMode(false);
    tensorFlow.deactivate();
  }
}
