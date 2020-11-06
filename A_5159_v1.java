/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@Autonomous(name = "Autonomous Skystone v1", group = "SDR")
//@Disabled
public class A_5159_v1 extends LinearOpMode {


    /* Declare OpMode members. */
    Chassis_KLNavX_6109_v6 chassis = new Chassis_KLNavX_6109_v6();   // Use Omni-Directional drive system

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the chassis
        chassis.initChassis(this, chassis.DIRECTCONNECT, true);
        chassis.resetMotorsTeleop(true);

        // Wait for the game to start (driver presses PLAY)
        // Prompt User
        telemetry.addData("Press Start", " >");
        telemetry.update();
        waitForStart();


        // run robot
        //run until the end of the match (driver presses STOP)

        moveRobot("move forward", 0.5, 1000, 0, 0, 5000);
        sleep(1000);
        moveRobot("move right", 0.5, 1000, 90, 0, 5000);
        sleep(1000);
        moveRobot("move backwards", 0.5, 1000, 180, 0, 5000);
        sleep(1000);
        moveRobot("move left", 0.5, 1000, -90, 0, 5000);
        sleep(1000);

        //send telemetry to driver station (no robot control)
        telemetry.addData("NavX:", "%s", chassis.getNavXStatus());
        if (chassis.isNavXConnected()) {
            telemetry.addData("Heading ", "%.2f",
                    chassis.getNavXYaw());
        } else {
            telemetry.addData("WARNING! ", "NavX not connected!i!");
        }
        telemetry.addData("Encoder", "LF:%.2f RF:%.2f",
                chassis.mtrLeftFront.getCurrentPosition(),  chassis.mtrRightFront.getCurrentPosition());
        telemetry.addData("       ", "LB:%.2f RB:%.2f",
                chassis.mtrLeftBack.getCurrentPosition(),  chassis.mtrRightBack.getCurrentPosition());
        telemetry.addData("MTR ", " LF %.2f RF %.2f",
                chassis.mtrLeftFront.getPower(), chassis.mtrRightFront.getPower());
        telemetry.addData("    ", " LB %.2f RB %.2f",
                chassis.mtrLeftBack.getPower(), chassis.mtrRightBack.getPower());
        telemetry.update();


        //SkyStone v5.2 incompatibility
        //if(chassis.isNavXConnected()) chassis.enableNavXYawPID(false);

        chassis.closeNavX();
        telemetry.addData("[]", "Shutting Down. Bye!");
        telemetry.update();
    }


    public void moveRobot(String msg, double speed, int distance, int desired_angle, double desired_yaw,
                             int timeout) {
        String msg2;


        long lMarkMilliS;
        //Left Back Motor
        chassis.mtrLeftBack.setDirection(DcMotor.Direction.REVERSE);// Positive input rotates counter clockwise
        chassis.mtrLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chassis.mtrLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        chassis.mtrLeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        chassis.mtrLeftBack.setTargetPosition(distance);
        chassis.mtrLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Left Forward Motor
        chassis.mtrLeftFront.setDirection(DcMotor.Direction.REVERSE);// Positive input rotates counter clockwise
        chassis.mtrLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chassis.mtrLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        chassis.mtrLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        chassis.mtrLeftFront.setTargetPosition(distance);
        chassis.mtrLeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Right Back Motor
        chassis.mtrRightBack.setDirection(DcMotor.Direction.FORWARD);// Positive input rotates counter clockwise
        chassis.mtrRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chassis.mtrRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        chassis.mtrRightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        chassis.mtrRightBack.setTargetPosition(distance);
        chassis.mtrRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Right Front Motor
        chassis.mtrRightFront.setDirection(DcMotor.Direction.FORWARD);// Positive input rotates counter clockwise
        chassis.mtrRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chassis.mtrRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        chassis.mtrRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        chassis.mtrRightFront.setTargetPosition(distance);
        chassis.mtrRightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (desired_angle == 0) {
            msg2 = "move forward";
            //Go forward
            chassis.mtrLeftBack.setPower(speed);
            chassis.mtrLeftFront.setPower(speed);
            chassis.mtrRightBack.setPower(speed);
            chassis.mtrRightFront.setPower(speed);
        } else if (desired_angle == 180) {
            msg2 = "move backwards";
            //go backwards
            chassis.mtrLeftBack.setPower(-speed);
            chassis.mtrLeftFront.setPower(-speed);
            chassis.mtrRightBack.setPower(-speed);
            chassis.mtrRightFront.setPower(-speed);
        } else if (desired_angle == 90) {
            msg2 = "move right";
            chassis.mtrLeftBack.setPower(-speed);
            chassis.mtrLeftFront.setPower(speed);
            chassis.mtrRightBack.setPower(speed);
            chassis.mtrRightFront.setPower(-speed);
        } else if (desired_angle == -90) {
            msg2 = "move left";
            chassis.mtrLeftBack.setPower(speed);
            chassis.mtrLeftFront.setPower(-speed);
            chassis.mtrRightBack.setPower(-speed);
            chassis.mtrRightFront.setPower(speed);

        } else {
            msg2 = "no movement";
        }



        lMarkMilliS=System.currentTimeMillis();
        while(((System.currentTimeMillis() - lMarkMilliS) < timeout) && opModeIsActive() &&
                        chassis.mtrLeftBack.isBusy() && chassis.mtrRightBack.isBusy() &&
                        chassis.mtrRightFront.isBusy() && chassis.mtrLeftFront.isBusy()) {
            telemetry.addData(msg2 + msg + " M pos:", "%d, %d, %d, %d",
                    chassis.mtrLeftBack.getCurrentPosition(), chassis.mtrRightBack.getCurrentPosition(),
                    chassis.mtrRightFront.getCurrentPosition(), chassis.mtrLeftFront.getCurrentPosition());
            telemetry.update();
        }
        chassis.mtrLeftBack.setPower(0);
        chassis.mtrLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        chassis.mtrRightBack.setPower(0);
        chassis.mtrRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        chassis.mtrLeftFront.setPower(0);
        chassis.mtrLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        chassis.mtrRightFront.setPower(0);
        chassis.mtrRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }



}


