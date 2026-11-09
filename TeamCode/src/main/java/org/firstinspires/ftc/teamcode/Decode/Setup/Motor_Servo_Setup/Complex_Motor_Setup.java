package org.firstinspires.ftc.teamcode.Decode.Setup.Motor_Servo_Setup;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Complex_Motor_Setup {
    private DcMotorEx motorEx;
    public void init(HardwareMap hwMap, String motorID) {
        motorEx = hwMap.get(DcMotorEx.class, motorID);
        motorEx.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorEx.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void setMotorVelocity (double V) {
        motorEx.setVelocity(V);
    }
    public void setDirection(DcMotorSimple.Direction direction){
        motorEx.setDirection(direction);
    }
    public void motorMode(DcMotor.RunMode mode) {
        motorEx.setMode(mode);
    }
    public int CurrentPos(){
        return motorEx.getCurrentPosition();
    }
    public void TargetPos(int hight){
        motorEx.setTargetPosition(hight);
    }
}
