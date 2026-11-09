package org.firstinspires.ftc.teamcode.Decode.Teles.Clanker_MK2.Decode.SubSystems;


import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Decode.Teles.Clanker_MK2.Decode.Setup.Motor_Servo_Setup.Cont_Servo_Setup;
import org.firstinspires.ftc.teamcode.Decode.Teles.Clanker_MK2.Decode.Setup.Motor_Servo_Setup.Drive_Motor_Setup;

public class MK2_OutReach_Scoring_System {

    //TODO: fill out the scoring system motor/servo IDs
    Drive_Motor_Setup CH0O = new Drive_Motor_Setup();  //Outtake
    Cont_Servo_Setup EHS1P = new Cont_Servo_Setup();    //Pass
    Drive_Motor_Setup EH3I = new Drive_Motor_Setup();    //Intake

    public double OutTPS;
    private double DriveTPS;
    private boolean Fire;
    private boolean Abort;
    private double OutTargetVel;
    private double OutVel;
    public void Score_MotorCal (HardwareMap hardwareMap) {
        CH0O.init(hardwareMap,"CH0Out");
        EHS1P.init(hardwareMap,"EHS1Pass");
        EH3I.init(hardwareMap,"EH3In");
        CH0O.setDirection(DcMotorSimple.Direction.FORWARD);
        EHS1P.setDirection(DcMotorSimple.Direction.FORWARD);
        EH3I.setDirection(DcMotorSimple.Direction.FORWARD);
        // setup
        OutTPS = 2800;
        OutTargetVel = 0.72 * OutTPS;   // comp power
        //OutTargetVel = 0.50 * OutTPS;   // outreach
        //OutTargetVel = 1 * OutTPS;  // warp speeeeeeed!
        DriveTPS = 2787.625;
        // 2,800 ticks per second for outtake motor
    }

    public void Scoring_Grabber (boolean fire ,boolean abort){
        Fire = fire;
        Abort = abort;
    }

    public void Scoring_Running () {
        OutVel = CH0O.getMotorVelocity();

        if ((Fire == true) && (OutVel >= ( 0.8 * OutTargetVel))) {
            EHS1P.setPower(1);
            EH3I.setMotorVelocity(1 * DriveTPS);
        } else if (Abort == true) {
            EHS1P.setPower(-0.5);
            EH3I.setMotorVelocity(-1 * DriveTPS);
        } else {
            EHS1P.setPower(-0.5);
            EH3I.setMotorVelocity(1* DriveTPS);
        }

        CH0O.setMotorVelocity(OutTargetVel);

    }

    public double OutmotorVel () {
        return OutVel;
    }
}
