package org.firstinspires.ftc.teamcode.Decode.Teles.Clanker_MK2.Decode.Teles.Clanker_MK2;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Decode.Teles.Clanker_MK2.Decode.Setup.ImportedStuffs.NO_TOUCH.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.Decode.Teles.Clanker_MK2.Decode.SubSystems.MK2_Drive_System;
import org.firstinspires.ftc.teamcode.Decode.Teles.Clanker_MK2.Decode.SubSystems.MK2_Score_System;

@Disabled//(name = "Robot-Centric Comp Tele")
public class Clanker_MK2_Robot_Comp_Tele extends OpMode {
    GoBildaPinpointDriver odo;
    // makes odo object
    double oldTime = 0;
    // odo bit
    MK2_Drive_System DS = new MK2_Drive_System();
    // drive system object
    MK2_Score_System SS = new MK2_Score_System();
    // score system object
    double LX1;
    double LY1;
    double RX1;
    boolean BL1;
    boolean BR1;
    double PowerMod;
    boolean A1;
    boolean A2;
    boolean X2;
    boolean DU2;
    boolean DD2;
    boolean DU1;
    boolean DD1;
    boolean DL1;
    boolean DR1;
    double XposCurrent;
    double YposCurrent;
    double RposCurrent;

    @Override
    public void init() {
        DS.Drive_MotorCal(hardwareMap);
        SS.Score_MotorCal(hardwareMap);
        //Initilise HardwareMap setup


        odo = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        odo.setOffsets(0, 0, DistanceUnit.INCH);
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD
                , GoBildaPinpointDriver.EncoderDirection.FORWARD);
        // x dirention then y dierncton
        odo.resetPosAndIMU();
        // odo cal bits
        odo.setHeading( 0, AngleUnit.DEGREES);
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
        DU2 = gamepad2.dpad_up;
        DD2 = gamepad2.dpad_down;
        DU1 = gamepad1.dpad_up;
        DD1 = gamepad1.dpad_down;
        DL1 = gamepad1.dpad_left;
        DR1 = gamepad1.dpad_right;
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
            DS.Drive_Grabber(0, 1, 0, PowerMod, RposCurrent);
        } else if (DU1 == true) {
            DS.Drive_Grabber(0, -1, 0, PowerMod, RposCurrent);
        } else if (DL1 == true) {
            DS.Drive_Grabber(1, 0, 0, PowerMod, RposCurrent);
        } else if (DR1 == true) {
            DS.Drive_Grabber(-1, 0, 0, PowerMod, RposCurrent);
        } else {
            DS.Drive_Grabber(LX1, LY1, RX1, PowerMod, 0);
        }
        DS.Drive_Running();
        // drive system module

        SS.Scoring_Grabber( DU2 , DD2 , X2 , A2 );
        //pass forward , pass backward , intake backward , intake forward
        SS.Scoring_Running();
        // score system module

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
        telemetry.addData("Power", PowerMod);
        // input telmetry
        telemetry.addLine();
        telemetry.addData("Odo_X", XposCurrent);
        telemetry.addData("Odo_Y", YposCurrent);
        telemetry.addData("Odo_R", ((RposCurrent / (2 * Math.PI)) * 360));
        // odo bits
        telemetry.addLine();
        telemetry.addData("Outake Vel", (SS.OutmotorVel() / SS.OutTPS));
        // outake velocity as a %

    }
}
