package org.timecrafters.SkyStone_2019_2020.Autonomos.States;

/**********************************************************************************************
 * Name: SkystoneSight
 * Description: Observes stones with Tensor Flow Object detection and sets SkystonePosition to -1,0
 * or 1 to enable different drive paths.
 **********************************************************************************************/

import org.cyberarm.NeXT.StateConfiguration;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.timecrafters.SkyStone_2019_2020.Drive;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

import java.util.List;

public class SkystoneSight extends State {

    private StateConfiguration StateConfig;
    private String StateConfigID;
    private VuforiaLocalizer Vuforia;
    private TFObjectDetector TensorFlow;
    private Recognition SkyStone1;
    private Recognition SkyStone2;
    private Recognition Stone1;
    private Recognition Stone2;
    private double RightBoundary = 15;
    private double LeftBoundary = -10;
    private Recognition TargetStone;
    private long StartTime;
    private long EndTime;
    private double MinimumConfidence;
    private int ClippingMarginLeft;
    private int ClippingMarginRight;
    private int ClippingMarginTop;
    private int ClippingMarginBottom;


    public int SkystonePosition;
    private boolean FirstRun = true;

    public SkystoneSight(Engine engine, StateConfiguration stateConfig, String stateConfigID) {
        this.engine = engine;
        StateConfig = stateConfig;
        StateConfigID = stateConfigID;
    }

    @Override
    public void init() {

        MinimumConfidence = StateConfig.get(StateConfigID).variable("minConfidence");
        ClippingMarginLeft = StateConfig.get(StateConfigID).variable("marginLeft");
        ClippingMarginRight = StateConfig.get(StateConfigID).variable("marginRight");
        ClippingMarginTop = StateConfig.get(StateConfigID).variable("marginTop");
        ClippingMarginBottom = StateConfig.get(StateConfigID).variable("marginBottom");
        EndTime = StateConfig.get(StateConfigID).variable("time");

        //Vuforia Init
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = "AcU+kbn/////AAAAGWDmHA7mS0gCoiMy9pA5e1AVyLZeqKejLOtP9c3COfi9g9m4Cs1XuVQVdqRFhyrFkNUynXwrhQyV65hPnPkGgRky9MjHlLLCWuqdpHzDLJonuOSBh5zVO11PleXH+2utK1lCnbBxvOM+/OrB9EAHUBrcB0ItRxjzFQOe8TXrjGGe1IyjC/Ljke3lZf/LVVinej3zjGNqwsNQoZ0+ahxYNPCJOdzRFkXjyMDXJVDQYMtVQcWKpbEM6dJ9jQ9f0UFIVXANJ7CC8ZDyrl2DQ8o4sOX981OktCKWW0d4PH0IwAw/c2nGgt1t2V/7PwTwysBYM1N+SjVpMNRg52u9gNl9os4ulF6AZw+U2LcVj4kqGZDi";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;


        Vuforia = ClassFactory.getInstance().createVuforia(parameters);



        //Tensor Flow
        int tfodMonitorViewId = engine.hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", engine.hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = MinimumConfidence;
        TensorFlow = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, Vuforia);
        TensorFlow.loadModelFromAsset("Skystone.tflite", "Stone", "Skystone");
        TensorFlow.setClippingMargins(ClippingMarginLeft, ClippingMarginTop, ClippingMarginRight, ClippingMarginBottom);

    }

    @Override
    public void exec() throws InterruptedException {
        if (StateConfig.allow(StateConfigID)) {

            if (FirstRun) {
                TensorFlow.activate();
                StartTime = System.currentTimeMillis();
                FirstRun = false;
            }

            SkyStone1 = null;
            SkyStone2 = null;
            Stone1 = null;
            Stone2 = null;



            //Sorts Recognitions into Stones and Skystones
            List<Recognition> recognitions = TensorFlow.getRecognitions();
            engine.telemetry.addData("recognitions", recognitions.size());
            for (Recognition recognition : recognitions) {
                if (recognition.getLabel().equals("Skystone") && SkyStone1 == null) {
                    SkyStone1 = recognition;
                } else if (recognition.getLabel().equals("Skystone") && SkyStone1 != null) {
                    SkyStone2 = recognition;
                } else if (recognition.getLabel().equals("Stone") && Stone1 == null) {
                    Stone1 = recognition;
                } else if (recognition.getLabel().equals("Stone") && Stone1 != null) {
                    Stone2 = recognition;
                }

            }
            engine.telemetry.update();

            //If there happen to be multiple skystones, the robot selects the one on the left.
            if (SkyStone1 != null) {

                if (SkyStone2 == null) {
                    TargetStone = SkyStone1;
                } else if (SkyStone1.getLeft() < SkyStone2.getLeft() && StateConfigID.startsWith("R")) {
                    TargetStone = SkyStone1;
                } else if (SkyStone1.getLeft() > SkyStone2.getLeft() && StateConfigID.startsWith("R")) {
                    TargetStone = SkyStone2;
                } else if (SkyStone1.getLeft() > SkyStone2.getLeft() && StateConfigID.startsWith("B")) {
                    TargetStone = SkyStone1;
                } else if (SkyStone1.getLeft() <  SkyStone2.getLeft() && StateConfigID.startsWith("B")) {
                    TargetStone = SkyStone2;
                }
            }

            //If the robot recognizes two ordinary stones, it uses there positions to deduce the
            //position of the third. The camera proved better at Identifying ordinary stones than
            //skystones, so this strategy is prioritized
            if (Stone1 != null && Stone2 != null) {
                double stone1Angle = Stone1.estimateAngleToObject(AngleUnit.DEGREES);
                double stone2Angle = Stone2.estimateAngleToObject(AngleUnit.DEGREES);
                if ((stone1Angle > RightBoundary && stone2Angle > LeftBoundary)||(stone2Angle > RightBoundary && stone1Angle > LeftBoundary)) {
                    engine.telemetry.addLine("Left");
                    SkystonePosition = -1;
                } else if ((stone1Angle < RightBoundary && stone2Angle < LeftBoundary)||(stone2Angle < RightBoundary && stone1Angle < LeftBoundary)) {
                    engine.telemetry.addLine("Right");
                    SkystonePosition = 1;
                } else if ((stone1Angle > RightBoundary &&  stone2Angle < LeftBoundary)||(stone2Angle > RightBoundary && stone1Angle < LeftBoundary)) {
                    engine.telemetry.addLine("Center");
                    SkystonePosition = 0;
                }
            } else if (TargetStone != null) {
            //if the robot recognizes a Skystone, it determines it's position based on the angle to it.
                double skystoneAngle = TargetStone.estimateAngleToObject(AngleUnit.DEGREES);
                if (skystoneAngle != 0) {
                    if (skystoneAngle > RightBoundary) {
                        engine.telemetry.addLine("Right");
                        SkystonePosition = 1;
                    } else if (skystoneAngle < LeftBoundary) {
                        engine.telemetry.addLine("Left");
                        SkystonePosition = -1;
                    } else {
                        engine.telemetry.addLine("Center");
                        SkystonePosition = 0;
                    }

                }
                engine.telemetry.addData("EstimatedAngle", TargetStone.estimateAngleToObject(AngleUnit.DEGREES));
                engine.telemetry.update();

            } else {
                //if nothing else, just select the rightward path
                SkystonePosition = 1;
            }

            //finish state after X amount of time. the result at this point will be used for the
            //rest of the program.
            setFinished(System.currentTimeMillis() - StartTime > EndTime)  ;


        } else {
            engine.telemetry.addData("Skipping Step", StateConfigID);
            engine.telemetry.update();
            SkystonePosition = StateConfig.get(StateConfigID).variable("ifSkip");
            sleep(EndTime);
            setFinished(true);
        }

    }
}
