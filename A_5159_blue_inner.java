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

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;


@Autonomous(name = "Autonomous blue inner", group = "SDR")
//@Disabled
public class A_5159_blue_inner extends LinearOpMode {


    /* Declare OpMode members. */
    Chassis_KLNavX_6109_v6 chassis = new Chassis_KLNavX_6109_v6();   // Use Omni-Directional drive system
    int factor;
    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the chassis
        chassis.initChassis(this, chassis.DIRECTCONNECT, true);
        chassis.resetMotorsTeleop(true);
        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        // bLedOn represents the state of the LED.
        chassis.bLedOn = true;

        // get a reference to our ColorSensor object.
        chassis.colorSensor = hardwareMap.get(ColorSensor.class, "sensor_color");

        // Set the LED in the beginning
        chassis.colorSensor.enableLed(chassis.bLedOn);



        // Wait for the game to start (driver presses PLAY)
        // Prompt User
        telemetry.addData("Press Start", " >");
        telemetry.update();
        waitForStart();

        //drive backwards(forward) towards powershot
        chassis.moveRobot(this, "move backwards", 0.5, 1400, 180,0, 10000);
        //shooting process
        //chassis.moveConveyor(this, "move conveyor", 1, 300, 10000);
        //chassis.moveLauncher(this, "move launcher", 1, 500, 10000);
        sleep(2000);

        //strafe left to align with 2nd powershot
        chassis.moveRobot(this, "strafe left", 0.2,50, -90, 0, 10000);
        //shooting process
        //chassis.moveConveyor(this, "move conveyor", 1, 300, 10000);
        //chassis.moveLauncher(this, "move launcher", 1, 500, 10000);
        sleep(2000);


        //strafe left to align with 3rd powershot
        chassis.moveRobot(this, "strafe left", 0.2,50, -90, 0, 10000);
        //shooting process
        //chassis.moveConveyor(this, "move conveyor", 1, 300, 10000);
        //chassis.moveLauncher(this, "move launcher", 1, 500, 10000);\
        sleep(2000);


        // move forward to launch line
        chassis.moveRobot(this, "move backwards", 0.5, 200, 180,0, 10000);














        // LAST YEAR PROGRAM DO NOT TOUCH

        /*
        chassis.moveRobot(this,"move forward", 0.5, 1010, 0, 0, 10000);
        chassis.moveRobot(this, "strafe right", 0.5, 300, 90, 0, 10000);

        //detect stones
        if (chassis.detectSkystone(this)) {
            //if detect... do this
            //align robot to pick skystone
            chassis.moveRobot(this, "strafe left", 0.2,260, -90, 0, 10000);
            //small movement towards stones
            chassis.moveRobot(this,"move forward", 0.3, 400, 0, 0, 10000);
            //grab stones
            chassis.moveSrvo(this, "close", -1.0, 2600);
            sleep(500);
            //move back, so no crash central skybridge
            chassis.moveRobot(this,"move backwards", 0.75, 350, 180, 0, 10000);
            //strafe under skybridge
            chassis.moveRobot(this,"strafe right", 0.75, 2200, 90, 0, 8000);
            //go forward before releasing
            chassis.moveRobot(this,"move forward", 0.5, 600, 0, 0, 10000);
            //let go of stones
            chassis.moveSrvo(this, "open", 1.0, 2600);
//go back to not take stone
            chassis.moveRobot(this,"move backwards", 0.5, 600, 180, 0, 10000);
            //strafe to next block
            chassis.moveRobot(this,"strafe left", 0.75, 3450, -90, 0, 10000);
            //move forward to get bloc6
            chassis.moveRobot(this,"move forward", 0.3, 700, 0, 0, 10000);
            //grab second stone
            chassis.moveSrvo(this, "close", -1.0, 3200);
            sleep(500);
            //move back, so no crash central skybridge
            chassis.moveRobot(this,"move backwards", 0.5, 700, 180, 0, 10000);
            //strafe under skybridgee
            chassis.moveRobot(this,"strafe right", 0.75, 3300, 90, 0, 8000);
            //go forward before releasing
            chassis.moveRobot(this,"move forward", 0.5, 400, 0, 0, 10000);
            //let go of stones
            chassis.moveSrvo(this, "open", 1.0, 1500);
            //go back to not take stone
            chassis.moveRobot(this,"move backwards", 0.5, 400, 180, 0, 10000);
            //strafe under skybridge(5 pts)
            chassis.moveRobot(this,"strafe left", 0.5, 950, -90, 0, 10000);


        } else {
            //else move to next block
            chassis.moveRobot(this, "strafe left", 0.3, 330, -90, 0, 10000);
            if (chassis.detectSkystone(this)) {
                //set factor
                factor = 400;
                //if detect... do this
                //align robot to pick skystone
                chassis.moveRobot(this, "strafe left", 0.3, 230, -90, 0, 10000);
                //small movement towards stones
                chassis.moveRobot(this, "move forward", 0.3, 400, 0, 0, 10000);
                //grab stones
                chassis.moveSrvo(this, "close?", -1.0, 2600);
                sleep(500);
                //move back, so no crash central skybridge
                chassis.moveRobot(this, "move backwards", 0.75, 450, 180, 0, 10000);
                //strafe under skybridge
                chassis.moveRobot(this, "strafe right", 0.75, 1900 + factor, 90, 0, 10000);
                //go forward before releasing
                chassis.moveRobot(this, "move forward", 0.5, 600, 0, 0, 10000);
                //let go of stones
                chassis.moveSrvo(this, "open", 1.0, 2600);
                //go back to not take stone
                chassis.moveRobot(this, "move backwards", 0.5, 600, 180, 0, 10000);
                //strafe to next block
                chassis.moveRobot(this,"strafe left", 0.75, 3600, -90, 0, 10000);
                //move forward to get block
                chassis.moveRobot(this,"move forward", 0.3, 700, 0, 0, 10000);
                //grab second stone
                chassis.moveSrvo(this, "close", -1.0, 2800);
                sleep(500);
                //move back, so no crash central skybridge
                chassis.moveRobot(this,"move backwards", 0.5, 700, 180, 0, 10000);
                //strafe under skybridgee
                chassis.moveRobot(this,"strafe right", 0.75, 3600, 90, 0, 8000);
                //go forward before releasing
                chassis.moveRobot(this,"move forward", 0.5, 400, 0, 0, 10000);
                //let go of stones
                chassis.moveSrvo(this, "open", 1.0, 500);
                //go back to not take stone
                chassis.moveRobot(this,"move backwards", 0.75, 450, 180, 0, 10000);
                //strafe under skybridge(5 pts)
                chassis.moveRobot(this,"strafe left", 0.75, 550, -90, 0, 10000);


            } else {
                //else move to next block
                chassis.moveRobot(this, "strafe left", 0.3, 750, -90, 0, 10000);
                //set factor
                factor = 600;
                //if detect... do this
                //small movement towards stones
                chassis.moveRobot(this, "move forward", 0.3, 400, 0, 0, 10000);
                //grab stones
                chassis.moveSrvo(this, "close", -1.0, 2600);                sleep(1000);
                //move back, so no crash central skybridge
                chassis.moveRobot(this, "move backwards", 0.5, 450, 180, 0, 10000);
                //strafe under skybridge
                chassis.moveRobot(this, "strafe right", 0.5, 2200 + factor, 90, 0, 10000);
                //go forward before releasing
                chassis.moveRobot(this, "move forward", 0.5, 400, 0, 0, 10000);
                //let go of stones
                chassis.moveSrvo(this, "open", 1.0, 1500);
                //go back to not take stone
                chassis.moveRobot(this, "move backwards", 0.5, 400, 180, 0, 10000);
                //strafe to under skybridge
                chassis.moveRobot(this, "strafe left", 0.5, 800, -90, 0, 10000);
            }
        }

         */


        // Set the panel back to the default color
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });

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
