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
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name = "Autonomous red buildsite", group = "SDR")
//@Disabled
public class A_5159_red_buildsite extends LinearOpMode {


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
        //elevator will start x distance high
        chassis.moveElev(this,"move elevator", 0.5, 220, 10000);
        chassis.moveRobot(this,"move forward", 0.3, 1130, 0, 0, 10000);
        //strafe to middle of foundation
        chassis.moveRobot(this,"strafe right", 0.3, 400, 90, 0, 10000);
        //go to foundation
        chassis.moveRobot(this,"move forward", 0.3, 140, 0, 0, 10000);
        //hook foundation
        chassis.moveElev(this,"move elevator", -0.1, 500, 3000);
        //pull foundation
        chassis.moveRobot(this,"move backwards", 0.3, 1600, 180, 0, 10000);
        //stop dragging foundation
        chassis.moveElev(this,"move elevator", 0.5,  450, 10000);
        //move out of foundation
        chassis.moveRobot(this,"strafe left", 0.3, 800, -90, 0, 10000);

        chassis.moveRobot(this,"strafe left", 0.3, 700, -90, 0, 10000);
        chassis.moveRobot(this,"move forward", 0.2, 50, 0, 0, 10000);

        //make sure elevator does not hit skybridge
        chassis.moveElev(this,"move elevator", -0.1, 500, 10000);
        //park under bridge
        chassis.moveRobot(this,"strafe left ", 0.3, 700, -90, 0, 10000);

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



}


