package org.firstinspires.ftc.teamcode.Decode.Setup.Motor_Servo_Setup;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Cont_Servo_Setup {
    private CRServo servo;

    public void init(HardwareMap hwMap, String servoID) {
        servo = hwMap.get(CRServo.class, servoID);
    }
    public void setDirection (CRServo.Direction direction) {
        servo.setDirection(direction);
    }
    public void setPower (double pow) {
        servo.setPower(pow);
    }
}
