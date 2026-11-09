package org.firstinspires.ftc.teamcode.Decode.Setup.Motor_Servo_Setup;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive_Motor_Setup {

    private DcMotorEx motorEx;
    public void init(HardwareMap hwMap, String motorID) {
        motorEx = hwMap.get(DcMotorEx.class, motorID);
        motorEx.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorEx.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void setMotorVelocity (double V) {
        motorEx.setVelocity(V);
    }
    public double getMotorVelocity () {
        return motorEx.getVelocity();
    }
    public void setMotorPower (double pow) {
        motorEx.setPower(pow);
    }
    public double getMotorPower () {
        return motorEx.getPower();
    }
    public void setDirection(DcMotorSimple.Direction direction){
        motorEx.setDirection(direction);
    }
    public int CurrentPos(){
        return motorEx.getCurrentPosition();
    }
}
