package org.firstinspires.ftc.teamcode.Decode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Decode.Setup.Motor_Servo_Setup.Drive_Motor_Setup;



public class Driving_System {
    private double MathX;
    private double DriveX;
    private double MathY;
    private double DriveY;
    private double DriveR;
    private double MathIMUY;
    private double Theta;
    private double NTheta;
    private double rad;
    private double robotAngle;
    private double Power;
    private double RawFR;
    private double RawBR;
    private double RawFL;
    private double RawBL;
    private double MaxRaw;
    private double FR;
    private double BR;
    private double FL;
    private double BL;
    private double DriveTPS;

    Drive_Motor_Setup EH1FR = new Drive_Motor_Setup();
    // FR motor object
    Drive_Motor_Setup EH0BR = new Drive_Motor_Setup();
    // BR motor object
    Drive_Motor_Setup CH1FL = new Drive_Motor_Setup();
    // FL motor object
    Drive_Motor_Setup CH0BL = new Drive_Motor_Setup();
    // FR motor object

        //varible asighment
    public void Drive_MotorCal (HardwareMap hardwareMap) {
        EH1FR.init(hardwareMap, "EH1FrontRight");
        EH0BR.init(hardwareMap, "EH0BackRight");
        CH1FL.init(hardwareMap, "CH1FrontLeft");
        CH0BL.init(hardwareMap, "CH0BackLeft");
        EH1FR.setDirection(DcMotorSimple.Direction.REVERSE);
        EH0BR.setDirection(DcMotorSimple.Direction.FORWARD);
        CH1FL.setDirection(DcMotorSimple.Direction.FORWARD);
        CH0BL.setDirection(DcMotorSimple.Direction.REVERSE);
        //setup block
        DriveTPS = 2787.625;
        // 2,787.625 ticks per second for drive motors
    }

    public void Drive_Grabber (double StickX, double StickY, double StickR, double PowerMod, double IMUY) {
        MathX = StickX;
        MathY = StickY;
        DriveR = StickR;
        MathIMUY = IMUY;
        Power = PowerMod;
        // feed in info from opmode
    }
    private void Drive_Maths () {
        robotAngle = MathIMUY;
        Theta = Math.atan2(MathY, MathX);
        rad = Math.hypot(MathY, MathX);
        NTheta = AngleUnit.normalizeRadians(Theta - robotAngle);
        DriveY = rad * Math.sin(NTheta);
        DriveX = rad * Math.cos(NTheta);
        // field centraic moduel

        RawFR = +1 * (DriveY - DriveX - DriveR);
        RawBR = -1 * (DriveY + DriveX - DriveR);
        RawFL = +1 * (DriveY + DriveX + DriveR);
        RawBL = -1 * (DriveY - DriveX + DriveR);
        MaxRaw = 1;
        //Dircetion matrix

        if (MaxRaw < Math.abs(RawFR)) {
            MaxRaw = Math.abs(RawFR);
        }
        if (MaxRaw < Math.abs(RawBR)) {
            MaxRaw = Math.abs(RawBR);
        }
        if (MaxRaw < Math.abs(RawFL)) {
            MaxRaw = Math.abs(RawFL);
        }
        if (MaxRaw < Math.abs(RawBL)) {
            MaxRaw = Math.abs(RawBL);
        }
        //speed matrix
    }

    public void Drive_Running () {
        Drive_Maths();
        FR = ( (RawFR / MaxRaw) * Power);
        BR = ( (RawBR / MaxRaw) * Power);
        FL = ( (RawFL / MaxRaw) * Power);
        BL = ( (RawBL / MaxRaw) * Power);
        EH1FR.setMotorVelocity(FR * DriveTPS);
        EH0BR.setMotorVelocity(BR * DriveTPS);
        CH1FL.setMotorVelocity(FL * DriveTPS);
        CH0BL.setMotorVelocity(BL * DriveTPS);
    }

}
