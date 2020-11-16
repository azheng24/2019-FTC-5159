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

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;


@Autonomous(name = "Autonomous test webcam", group = "SDR")
//@Disabled
public class A_5159_test_1 extends LinearOpMode {


    /* Declare OpMode members. */
    Chassis_KLNavX_6109_v6 chassis = new Chassis_KLNavX_6109_v6();   // Use Omni-Directional drive system
    int factor;
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";
    int stack_size = 0;

    //vuforia

    private static final String VUFORIA_KEY =
            "Ab3BSsj/////AAABmfMLMKRqTERErt6+VUXQwLtV70Ianrk86jintedjL3dCHBG1EkM1Zhp3l7hqxi5V+TRnIXItuVCBpLq8Yj+xd/EJRYPQU0hw/l9BtoP45AAfxSIZgIQTTDwv+5fFOan1X+dqeNUsn2/kYJjILHK8DucffVJZMP5ohT/yB0F54PRBcOSGF1t0FToLbZXnNHNQDb13bLLLN5Gri2fbjpxLaFqlqm7slj7wp1MilbsGLaXr/FRfgcz1SWaCdk+LQa7Veal1RyrlCO+nLamxMpo5I0HsGekzJh6r1WI2BpuCiKhMYvT9DwaG6HtCVXF951I0Fbd8N1JgoCOBPHXvAWDXVhB04fnjp/+u8Sr4oWet8lwY";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;


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

        stack_size = find_stack(10000);

        if (stack_size == 0) {
            telemetry.addData("stack size: 0", "");
        } else if (stack_size == 1) {
            telemetry.addData("stack size: 1", "");
        } else if (stack_size == 4) {
            telemetry.addData("stack size: 4", "");
        }

        telemetry.update();
        sleep(1000);



        /*
        //drive backwards(forward) towards powershot
        chassis.moveRobot(this, "move backwards", 0.5, 1400, 180, 0, 10000);
        //shooting process
        //chassis.moveConveyor(this, "move conveyor", 1, 300, 10000);
        //chassis.moveLauncher(this, "move launcher", 1, 500, 10000);
        sleep(2000);

        //strafe left to align with 2nd powershot
        chassis.moveRobot(this, "strafe left", 0.2, 50, -90, 0, 10000);
        //shooting process
        //chassis.moveConveyor(this, "move conveyor", 1, 300, 10000);
        //chassis.moveLauncher(this, "move launcher", 1, 500, 10000);
        sleep(2000);


        //strafe left to align with 3rd powershot
        chassis.moveRobot(this, "strafe left", 0.2, 50, -90, 0, 10000);
        //shooting process
        //chassis.moveConveyor(this, "move conveyor", 1, 300, 10000);
        //chassis.moveLauncher(this, "move launcher", 1, 500, 10000);\
        sleep(2000);


        // move forward to launch line
        chassis.moveRobot(this, "move backwards", 0.5, 200, 180, 0, 10000);
        */
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    private int find_stack(int timeout) {

        initVuforia();
        initTfod();

        /* Activate Tensor Flow Object Detection. */

        if (tfod != null) {
            tfod.activate();
        }

        long lMarkMilliS = System.currentTimeMillis();
        while (((System.currentTimeMillis() - lMarkMilliS) < timeout) && opModeIsActive()) {
            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    // step through the list of recognitions and display boundary info.
                    int i = 0;

                    for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());

                        if (updatedRecognitions.size() == 1) {
                            if (recognition.getLabel() == "Single") {
                                tfod.shutdown();
                                return 1;
                            } else if (recognition.getLabel() == "Quad") {
                                tfod.shutdown();
                                return 4;
                            }
                        }
                    }

                    telemetry.update();
                }
            } else {
                telemetry.addData("tfod is null", "");
                telemetry.update();
            }
        }
        if (tfod != null) {
            tfod.shutdown();
        }
        return 0;
    }

}