package org.firstinspires.ftc.teamcode.Decode.SubSystems;


import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Decode.Setup.Motor_Servo_Setup.Cont_Servo_Setup;
import org.firstinspires.ftc.teamcode.Decode.Setup.Motor_Servo_Setup.Drive_Motor_Setup;
public class Scoring_System {
    Cont_Servo_Setup CH0IN = new Cont_Servo_Setup();
    // change object name baseded on port
    Drive_Motor_Setup EH3OUT = new Drive_Motor_Setup();
    Cont_Servo_Setup EHS1P = new Cont_Servo_Setup();
    public double OutTPS;
    private boolean InTake_Button;
    private boolean OutTake_Button;
    private boolean BigPass;
    private boolean MiniPass;
    private boolean Back;
    public void Score_MotorCal (HardwareMap hardwareMap) {
        CH0IN.init(hardwareMap,"CHS0Intake");
        EH3OUT.init(hardwareMap,"EH3OutTake");
        EHS1P.init(hardwareMap,"EHS1Pass");
        CH0IN.setDirection(DcMotorSimple.Direction.REVERSE);
        EH3OUT.setDirection(DcMotorSimple.Direction.REVERSE);
        EHS1P.setDirection(DcMotorSimple.Direction.FORWARD);
        // setup
        OutTPS = 2800;
        // 2,800 ticks per second for outtake motor
    }

    public void Scoring_Grabber (boolean Pass, boolean Pass_back) {
        BigPass = Pass;
        Back = Pass_back;
    }

    public void Scoring_Running () {

        if (BigPass == true) {
            EHS1P.setPower(1);
        } else if (Back == true) {
            EHS1P.setPower(-1);
        } else {
            EHS1P.setPower(0);
        }

        CH0IN.setPower(1);
        // Intake Power to full
    }
    public void Scoring_Mini_In () {
        CH0IN.setPower(1);
        // Intake Power to full
    }
    public void OuttakeSeting (double Percent) {
        EH3OUT.setMotorVelocity(OutTPS * Percent);
        // Outake "Power" to fed in double
    }
    public double OuttakeVel () {
        return EH3OUT.getMotorVelocity();
    }
}
