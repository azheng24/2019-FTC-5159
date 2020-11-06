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
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name = "Autonomous test", group = "SDR")
//@Disabled
public class A_5159_test extends LinearOpMode {
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


        //testRobot();

        // run robot
        //run until the end of the match (driver presses STOP)
        //elevator will start x distance high
        moveRobot("move forward", 0.2, 2000, 0, 0, 10000);
        //strafe to middle of foundation
        moveRobot("move backwards", 0.2, 2000, 180, 0, 10000);
        moveRobot("strafe right", 0.2, 2000, 90, 0, 10000);
        moveRobot("strafe left", 0.2, 2000, -90, 0, 10000);
        //chassis.srvoLoader.setPosition(1);
        //sleep(1000);


        /*
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

         */
    }
    long lMarkMilliS;



    public void moveRobot(String msg, double speed, int distance, int desired_angle, double desired_yaw,
                             int timeout) {
        String msg2;

        //Left Back Motor
        chassis.mtrLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chassis.mtrLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Left Forward Motor
        chassis.mtrLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chassis.mtrLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Right Back Motor
        chassis.mtrRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chassis.mtrRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //Right Front Motor
        chassis.mtrRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chassis.mtrRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        lMarkMilliS=System.currentTimeMillis() + timeout;
        while((System.currentTimeMillis() < lMarkMilliS) &&
                        Math.abs(chassis.mtrLeftBack.getCurrentPosition()) < distance &&
                        opModeIsActive()
                        ) {
            double heading = chassis.getNavXYaw();
            if (desired_angle == 0) {
                msg2 = "move forward";
                //Go forward
                chassis.mtrLeftBack.setPower(speed*(1-heading/100));
                chassis.mtrLeftFront.setPower(speed*(1-heading/100));
                chassis.mtrRightBack.setPower(speed*(1+heading/100));
                chassis.mtrRightFront.setPower(speed*1+heading/100);
            } else if (desired_angle == 180) {
                msg2 = "move backwards";
                //go backwards
                chassis.mtrLeftBack.setPower(-speed*(1+heading/100));
                chassis.mtrLeftFront.setPower(-speed*(1+heading/100));
                chassis.mtrRightBack.setPower(-speed*(1-heading/100));
                chassis.mtrRightFront.setPower(-speed*(1-heading/100));
            } else if (desired_angle == 90) {
                msg2 = "move right";
                chassis.mtrLeftBack.setPower(-speed*(1+heading/100));
                chassis.mtrLeftFront.setPower(speed*(1-heading/100));
                chassis.mtrRightBack.setPower(speed*(1+heading/100));
                chassis.mtrRightFront.setPower(-speed*(1-heading/100));
            } else if (desired_angle == -90) {
                msg2 = "move left";
                chassis.mtrLeftBack.setPower(speed*(1-heading/100));
                chassis.mtrLeftFront.setPower(-speed*(1+heading/100));
                chassis.mtrRightBack.setPower(-speed*(1-heading/100));
                chassis.mtrRightFront.setPower(speed*(1+heading/100));
            } else {
                msg2 = "no movement";
            }
            telemetry.addData(msg2 + " "+ msg + " M pos:", "%.2f, %d, %d, %d, %d", heading,
                    chassis.mtrLeftBack.getCurrentPosition(), chassis.mtrRightBack.getCurrentPosition(),
                    chassis.mtrRightFront.getCurrentPosition(), chassis.mtrLeftFront.getCurrentPosition());
            telemetry.update();
        }
        chassis.mtrLeftBack.setPower(0);
        chassis.mtrRightBack.setPower(0);
        chassis.mtrLeftFront.setPower(0);
        chassis.mtrRightFront.setPower(0);

    }
    public void moveElev(String msg, double speed, int distance, int timeout) {
        lMarkMilliS=System.currentTimeMillis();
        //Elevator Motor (encoder)
        chassis.mtrElev.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chassis.mtrElev.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        chassis.mtrElev.setPower(speed);

        lMarkMilliS=System.currentTimeMillis() + timeout;
        while((System.currentTimeMillis() < lMarkMilliS) && opModeIsActive()
                && Math.abs(chassis.mtrElev.getCurrentPosition()) < distance
        ) {
            telemetry.addData(  msg + " Elev pos:", "%d",
                    chassis.mtrElev.getCurrentPosition());
            telemetry.update();
        }
        chassis.mtrElev.setPower(0);
    }

    public void testRobot() {

        //Left Back Motor
        chassis.mtrLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chassis.mtrLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Left Forward Motor
        chassis.mtrLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chassis.mtrLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Right Back Motor
        chassis.mtrRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chassis.mtrRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //Right Front Motor
        chassis.mtrRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chassis.mtrRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double speed = 0.2;

        chassis.mtrLeftBack.setPower(speed);
        telemetry.addData( "LeftBack", "%d", 0);
        telemetry.update();
        sleep(2000);
        chassis.mtrLeftBack.setPower(0);

        chassis.mtrLeftFront.setPower(speed);
        telemetry.addData( "LeftFront", "%d", 0);
        telemetry.update();
        sleep(2000);
        chassis.mtrLeftFront.setPower(0);

        chassis.mtrRightBack.setPower(speed);
        telemetry.addData( "RightBack", "%d", 0);
        telemetry.update();
        sleep(2000);
        chassis.mtrRightBack.setPower(0);

        chassis.mtrRightFront.setPower(speed);
        telemetry.addData( "RightFront", "%d", 0);
        telemetry.update();
        sleep(2000);
        chassis.mtrRightFront.setPower(0);

    }


}