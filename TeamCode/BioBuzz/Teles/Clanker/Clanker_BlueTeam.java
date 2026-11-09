package org.firstinspires.ftc.teamcode.Decode.Teles.Clanker;


import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.Decode.Setup.ImportedStuffs.NO_TOUCH.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.Decode.SubSystems.Driving_System;
import org.firstinspires.ftc.teamcode.Decode.SubSystems.Scoring_System;

import java.util.List;

@Disabled
public class Clanker_BlueTeam extends OpMode {
    Limelight3A limelight;
    GoBildaPinpointDriver odo;
    double oldTime = 0;
    Driving_System DS;
    Scoring_System SS;
    double LX1;
    double LY1;
    double RX1;
    boolean BL1;
    boolean BR1;
    double PowerMod;
    boolean A1;
    boolean A2;
    boolean X2;
    boolean DD1;
    boolean DU1;
    boolean DL1;
    boolean DR1;
    boolean DD2;
    boolean DU2;
    double XposCurrent;
    double YposCurrent;
    double RposCurrent;

    @Override
    public void init () {
        LLinit();
        limelight.pipelineSwitch(2);
        // set pipleline
        // pipe 1 is for tag ID 20 for blue classifier

        DS.Drive_MotorCal(hardwareMap);
        SS.Score_MotorCal(hardwareMap);
        //Initilise HardwareMap setup

        odo = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        odo.setOffsets(0 , 0 , DistanceUnit.CM);
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED
                , GoBildaPinpointDriver.EncoderDirection.FORWARD);
        // x dirention then y dierncton
        odo.resetPosAndIMU();
        // odo cal bits
        odo.setHeading( -90, AngleUnit.DEGREES);
    }

    @Override
    public void loop () {
        LLLoop();
        Pose2D pos = odo.getPosition();
        XposCurrent = pos.getY(DistanceUnit.INCH);
        YposCurrent = pos.getX(DistanceUnit.INCH);
        RposCurrent = pos.getHeading(AngleUnit.DEGREES);
        //feeding the odo positions into code varibles
        // it calls strafe Y and X is frward backard, so calling Y X and X Y
        // to use X as horoziontal strafe and Y forward Backward

        LX1 = -1 * gamepad1.left_stick_x;
        LY1 = +1 * gamepad1.left_stick_y;
        RX1 = -1 * gamepad1.right_stick_x;
        BL1 = gamepad1.left_bumper;
        BR1 = gamepad1.right_bumper;
        A1 = gamepad1.a;
        A2 = gamepad2.a;
        X2 = gamepad2.x;
        DD1 = gamepad1.dpad_down;
        DU1 = gamepad1.dpad_up;
        DL1 = gamepad1.dpad_left;
        DR1 = gamepad1.dpad_right;
        DD2 = gamepad2.dpad_down;
        DU2 = gamepad2.dpad_up;
        // gamepad setting

        if (BL1 == true) {
            PowerMod = 1.0;
        } else if (BR1 == true) {
            PowerMod = 0.4;
        } else {
            PowerMod = 0.7;
        }
        //power matrix

        if (A1 == true) {odo.resetPosAndIMU();}
        //odo yaw reset

        if (DD1 == true) {
            DS.Drive_Grabber(0, 1, 0, PowerMod, 0);
        } else if (DU1 == true) {
            DS.Drive_Grabber(0, -1, 0, PowerMod, 0);
        } else if (DL1 == true) {
            DS.Drive_Grabber(1, 0, 0, PowerMod, 0);
        } else if (DR1 == true) {
            DS.Drive_Grabber(-1, 0, 0, PowerMod, 0);
        } else {
            DS.Drive_Grabber(LX1, LY1, RX1, PowerMod, odo.getHeading(AngleUnit.RADIANS) );
        }
        DS.Drive_Running();
        // drive system module

        SS.Scoring_Grabber(DU2, DD2);
        SS.OuttakeSeting(0.914);
        if (SS.OuttakeVel() > (SS.OutTPS * 0.85)) {
            SS.Scoring_Running();
        } else {
            SS.Scoring_Mini_In();
        }
        // Scoring system module

        odo.update();
        // self explaitry

        double newTime = getRuntime();
        double loopTime = newTime-oldTime;
        double frequency = 1/loopTime;
        oldTime = newTime;
        // odo update module

        telemetry.addData("X Input",LX1);
        telemetry.addData("Y Input",LY1);
        telemetry.addData("R Input",RX1);
        // normal telmetry
        telemetry.addLine();
        telemetry.addData("Odo_X", XposCurrent);
        telemetry.addData("Odo_Y", YposCurrent);
        telemetry.addData("Odo_R", RposCurrent);
        telemetry.addData("Power", PowerMod );
        // odo telemtery
            //telemetry moduel
    }
    private void LLinit () {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        telemetry.setMsTransmissionInterval(11);

        //limelight.pipelineSwitch(2);

        //Starts polling for data.  If you neglect to call start(), getLatestResult() will return null.
        limelight.start();

        telemetry.addData(">", "Robot Ready.  Press Play.");
        telemetry.update();
    }
    private void LLLoop () {
        LLStatus status = limelight.getStatus();
        telemetry.addData("Name", "%s",
                status.getName());
        telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
                status.getTemp(), status.getCpu(),(int)status.getFps());
        telemetry.addData("Pipeline", "Index: %d, Type: %s",
                status.getPipelineIndex(), status.getPipelineType());

        LLResult result = limelight.getLatestResult();
        if (result.isValid()) {
            // Access general information
            Pose3D botpose = result.getBotpose();
            double captureLatency = result.getCaptureLatency();
            double targetingLatency = result.getTargetingLatency();
            double parseLatency = result.getParseLatency();
            telemetry.addData("LL Latency", captureLatency + targetingLatency);
            telemetry.addData("Parse Latency", parseLatency);
            telemetry.addData("PythonOutput", java.util.Arrays.toString(result.getPythonOutput()));

            telemetry.addData("tx", result.getTx());
            telemetry.addData("txnc", result.getTxNC());
            telemetry.addData("ty", result.getTy());
            telemetry.addData("tync", result.getTyNC());

            telemetry.addData("Botpose", botpose.toString());

            // Access barcode results
            List<LLResultTypes.BarcodeResult> barcodeResults = result.getBarcodeResults();
            for (LLResultTypes.BarcodeResult br : barcodeResults) {
                telemetry.addData("Barcode", "Data: %s", br.getData());
            }

            // Access classifier results
            List<LLResultTypes.ClassifierResult> classifierResults = result.getClassifierResults();
            for (LLResultTypes.ClassifierResult cr : classifierResults) {
                telemetry.addData("Classifier", "Class: %s, Confidence: %.2f", cr.getClassName(), cr.getConfidence());
            }

            // Access detector results
            List<LLResultTypes.DetectorResult> detectorResults = result.getDetectorResults();
            for (LLResultTypes.DetectorResult dr : detectorResults) {
                telemetry.addData("Detector", "Class: %s, Area: %.2f", dr.getClassName(), dr.getTargetArea());
            }

            // Access fiducial results
            List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
            for (LLResultTypes.FiducialResult fr : fiducialResults) {
                telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());
            }

            // Access color results
            List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
            for (LLResultTypes.ColorResult cr : colorResults) {
                telemetry.addData("Color", "X: %.2f, Y: %.2f", cr.getTargetXDegrees(), cr.getTargetYDegrees());
            }
        } else {
            telemetry.addData("Limelight", "No data available");
        }

        telemetry.update();
    }
}
