package org.firstinspires.ftc.teamcode.Decode.Teles.Clanker_MK2.Decode.Teles.Clanker_MK2;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Decode.Teles.Clanker_MK2.Decode.Setup.ImportedStuffs.NO_TOUCH.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.Decode.Teles.Clanker_MK2.Decode.SubSystems.MK2_Drive_System;
import org.firstinspires.ftc.teamcode.Decode.Teles.Clanker_MK2.Decode.SubSystems.MK2_OutReach_Scoring_System;

@TeleOp(name = "OutReach Mode")
public class Clanker_MK2_OutReach extends OpMode {
    GoBildaPinpointDriver odo;
    // makes odo object
    double oldTime = 0;
    // odo bit
    MK2_Drive_System DS = new MK2_Drive_System();
    // drive system object
    MK2_OutReach_Scoring_System SS = new MK2_OutReach_Scoring_System();
    // score system object
    double LX1;
    double LY1;
    double RX1;
    double PowerMod;
    double DriveYaw;
    boolean DU1;
    boolean DD1;
    boolean X1;
    boolean X1Current;
    boolean X1Previous;
    boolean X1Output;
    boolean A1;
    boolean A1Current;
    boolean A1Previous;
    boolean A1Output;
    boolean State;
    double RposCurrent;

    @Override
    public void init() {
        DS.Drive_MotorCal(hardwareMap);
        SS.Score_MotorCal(hardwareMap);
        //Initilise HardwareMap setup
        State = true;


        odo = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        odo.setOffsets(0, 0 , DistanceUnit.INCH);
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
        RposCurrent = pos.getHeading(AngleUnit.RADIANS);
        //feeding the odo positions into code varibles
        // it calls strafe Y and X is frward backard, so calling Y X and X Y
        // to use X as horoziontal strafe and Y forward Backward

        LX1 = -1 * gamepad1.left_stick_x;
        LY1 = +1 * gamepad1.left_stick_y;
        RX1 = -1 * gamepad1.right_stick_x;
        DU1 = gamepad1.dpad_up;
        DD1 = gamepad1.dpad_down;
        X1 = gamepad1.x;
        A1 = gamepad1.a;
        // gamepad setting


        X1Previous = X1Current;
        X1Current = X1;
        if ((X1Current == true) && (X1Previous == false)) {
            X1Output = true;
        } else {X1Output = false;}

        A1Previous = A1Current;
        A1Current = A1;
        if ((A1Current == true) && (A1Previous == false)) {
            A1Output = true;
        } else {A1Output = false;}


        if (A1Output == true) {
            State = false;
        } else if (X1Output == true) {
            State = true;
        }
        if (State == true) {
            DriveYaw = RposCurrent;
            // field Centric
        } else if (State == false) {
            DriveYaw = 0;
            // robot Centric
        }


        PowerMod = 0.5;
        DS.Drive_Grabber(LX1, LY1, RX1, PowerMod, DriveYaw);
        DS.Drive_Running();
        // drive system module

        SS.Scoring_Grabber(DU1 , DD1);
        //Fire , Abort
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
        // input telmetry
        telemetry.addLine();
        telemetry.addData("Odo_R_Deg", ((RposCurrent / (2 * Math.PI)) * 360));
        telemetry.addData("Odo_R_Rads", RposCurrent);
        telemetry.addData("DriveYaw", DriveYaw);
        // odo bits

    }
}
