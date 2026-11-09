package org.firstinspires.ftc.teamcode.Decode.Teles.Clanker_MK2.Decode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Decode.Teles.Clanker_MK2.Decode.SubSystems.MK2_Drive_System;


@Autonomous(name = "Basic Auto")
public class MK2_Basic_Auto extends LinearOpMode {
    MK2_Drive_System DS = new MK2_Drive_System();
    @Override
    public void runOpMode() {
        DS.Drive_MotorCal(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            DS.Drive_Grabber(0,-1,0,0.7,0);
            DS.Drive_Running();
            sleep(600);
            DS.Drive_Grabber(0,0,0,0,0);
            DS.Drive_Running();
            sleep(300);
            break;
        }
    }
}
