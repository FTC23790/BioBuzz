package org.firstinspires.ftc.teamcode.Decode.Teles.Clanker;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Decode.Setup.ImportedStuffs.NO_TOUCH.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.Decode.SubSystems.Driving_System;
import org.firstinspires.ftc.teamcode.Decode.SubSystems.Scoring_System;

@Disabled
public class Clanker_Comp_Tele extends OpMode {
    GoBildaPinpointDriver odo;
    // makes odo object
    double oldTime = 0;
    // odo bit
    Driving_System DS = new Driving_System();
    // drive system object
    Scoring_System SS = new Scoring_System();
    // Intake/outtake object
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
    public void init() {
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
    public void loop() {

        Pose2D pos = odo.getPosition();
        XposCurrent = pos.getY(DistanceUnit.INCH);
        YposCurrent = pos.getX(DistanceUnit.INCH);
        RposCurrent = pos.getHeading(AngleUnit.RADIANS);
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
            DS.Drive_Grabber(LX1, LY1, RX1, PowerMod, RposCurrent );
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
        telemetry.addData("Odo_R", ((RposCurrent / (2 * Math.PI)) * 360));
        telemetry.addData("Power", PowerMod );
        telemetry.addData("OutSpeed", SS.OuttakeVel());
        // odo telemtery

        //telemetry moduel

    }
}
