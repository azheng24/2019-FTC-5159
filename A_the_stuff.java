/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;



/**
 * This file illustrates the concept of driving a path based on encoder counts.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByTime;
 *
 *  This code ALSO requires that the drive Motors have been configured such that a positive
 *  power command moves them forwards, and causes the encoders to count UP.
 *
 *   The desired path in this example is:
 *   - Drive forward for 48 inches
 *   - Spin right for 12 Inches
 *   - Drive Backwards for 24 inches
 *   - Stop and close the claw.
 *
 *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
 *  that performs the actual movement.
 *  This methods assumes that each movement is relative to the last stopping place.
 *  There are other ways to perform encoder based moves, but this method is probably the simplest.
 *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="autonomous_test", group="TestBed")
//@Disabled
public class A_the_stuff extends LinearOpMode {

    /* Declare OpMode members. */
    Chassis_KLNavX_6109_v6 robot       = new Chassis_KLNavX_6109_v6();
    private ElapsedTime     runtime = new ElapsedTime();


    static final double     COUNTS_PER_MOTOR_REV    = 1441 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.5;
    static final double     TURN_SPEED              = 0.5;


    // gyro
    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY = "AUdC2JX/////AAABmYzC4fOZtkR+uSbgRJxi+YsAgdFtHf7LVsnX/rlPhcIGzmj0TxqPs7HQr1byHuUlvB5RzEbxHUO2L5/+e9Qzyiz/4MTcaNAUmZDOpcnxgk2YParx+gRQAVfnHooudH1rQMvnAzzUH5LIpz0LIp350qMTsGHfCtV4xyQXs6BMvLNgIWEuGm73fb0Nl1F7l8cH6v8nVavWY9Vov+Y8CYYx3ewBOt/7YyfsEcdXLFdmZSkzAw3vbsoe4SApVCCT9MW3aQmZaiARqKEvt4S8JDJa+vB2ihyTPPb16+kgwgc2VqrJ5C607IyZdz3iUqPfeBY2EnS7HYVC00ao9ltjbKAbC0F9mxQ0lXRGTRke5c12IC3n";

    //private static final int pos_middle = 1;
    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() throws InterruptedException {
        long lMarkMilliS=System.currentTimeMillis();
        RelicRecoveryVuMark vuMark=null;
        boolean bTimeOut,bExit,bDistValid;
        String strColor="none";
        float fRedPct,fBluePct;

        float fHeading;
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        //robot.initHardware(this);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        robot.mtrLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mtrRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mtrLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mtrRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.mtrLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mtrRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mtrLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mtrRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                robot.mtrLeftBack.getCurrentPosition(),
                robot.mtrLeftFront.getCurrentPosition(),
                robot.mtrRightFront.getCurrentPosition(),
                robot.mtrRightBack.getCurrentPosition());
        telemetry.update();

        // A timer helps provide feedback while calibration is taking place
        ElapsedTime timer = new ElapsedTime();

        // If you're only interested int the IntegratingGyroscope interface, the following will suffice.
        // gyro = hardwareMap.get(IntegratingGyroscope.class, "navx");

        // The gyro automatically starts calibrating. This takes a few seconds.
        telemetry.log().add("Gyro Calibrating. Do Not Move!");

        // Wait until the gyro calibration is complete
        timer.reset();
        while (robot.navxMicro.isCalibrating()) {
            telemetry.addData("calibrating", "%s", Math.round(timer.seconds()) % 2 == 0 ? "|.." : "..|");
            telemetry.update();
            Thread.sleep(50);
        }
        telemetry.log().clear();
        telemetry.log().add("Gyro Calibrated.");
        telemetry.clear();
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        // move robot down
        //elevator(7500, 50);


        encoderStrafe("hi", 0.25, 400, 20.0);
        sleep(500);
        encoderDrive( "hi", 0.25, -500, -500, 20.0);
        sleep(500);
        encoderStrafe("hi", 0.25, -400, 20.0);
        sleep(500);
        encoderDrive("hi", 0.25, 400, 400, 20.0);

        telemetry.addData("heading1", "%.1f", getGyro());
        telemetry.update();
        sleep(1);



        //robot.leftClaw.setPosition(1.0);            // S4: Stop and close the claw.
        //robot.rightClaw.setPosition(0.0);
        //sleep(1000);     // pause for servos to move

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public double getGyro() {
        Orientation angles;

        angles = robot.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return AngleUnit.DEGREES.normalize(AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle));
    }
    public void encoderDrive(String msg, double speed,
                             int leftInches, int rightInches,
                             double timeout) {

        long lMarkMilliS;

        //Left Back Motor
        robot.mtrLeftBack.setDirection(DcMotor.Direction.REVERSE);// Positive input rotates counter clockwise
        robot.mtrLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mtrLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mtrLeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.mtrLeftBack.setTargetPosition(leftInches);
        robot.mtrLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.mtrLeftBack.setPower(speed);

        //Left Forward Motor
        robot.mtrLeftFront.setDirection(DcMotor.Direction.REVERSE);// Positive input rotates counter clockwise
        robot.mtrLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mtrLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mtrLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.mtrLeftFront.setTargetPosition(leftInches);
        robot.mtrLeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.mtrLeftFront.setPower(speed);

        //Right Back Motor
        robot.mtrRightBack.setDirection(DcMotor.Direction.FORWARD);// Positive input rotates counter clockwise
        robot.mtrRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mtrRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mtrRightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.mtrRightBack.setTargetPosition(rightInches);
        robot.mtrRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.mtrRightBack.setPower(speed);

        //Right Front Motor
        robot.mtrRightFront.setDirection(DcMotor.Direction.FORWARD);// Positive input rotates counter clockwise
        robot.mtrRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mtrRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mtrRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.mtrRightFront.setTargetPosition(rightInches);
        robot.mtrRightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.mtrRightFront.setPower(speed);

        lMarkMilliS=System.currentTimeMillis();
        while(((System.currentTimeMillis() - lMarkMilliS) < timeout*1000) && opModeIsActive() && robot.mtrLeftBack.isBusy() &&
                robot.mtrRightBack.isBusy() && robot.mtrLeftFront.isBusy() && robot.mtrRightFront.isBusy()) {
            telemetry.addData("Motor pos:", " leftBack: %d, rightBack: %d, leftFront: %d, rightFront: %d",
                    robot.mtrLeftBack.getCurrentPosition(), robot.mtrRightBack.getCurrentPosition(), robot.mtrLeftFront.getCurrentPosition(), robot.mtrRightFront.getCurrentPosition());
            telemetry.update();
        }
        robot.mtrLeftBack.setPower(0);
        robot.mtrRightBack.setPower(0);
        robot.mtrLeftFront.setPower(0);
        robot.mtrRightFront.setPower(0);


/*
        int newLeftTarget;
        int newRightTarget;



        // Ensure that the opmode is still active
        if (opModeIsActive()) {


            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.mtrLeftBack.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.mtrRightBack.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.mtrLeftBack.setTargetPosition(newLeftTarget);
            robot.mtrRightBack.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.mtrLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.mtrRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.mtrLeftBack.setPower(Math.abs(speed));
            robot.mtrRightBack.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                   (runtime.seconds() < timeout) &&
                   (robot.mtrLeftBack.isBusy() && robot.mtrRightBack.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                                            robot.mtrLeftBack.getCurrentPosition(),
                                            robot.mtrRightBack.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.mtrLeftBack.setPower(0);
            robot.mtrRightBack.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.mtrLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.mtrRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
*/

    }
    public void encoderStrafe(String msg, double speed,
                             int distance,
                             double timeout) {
        long lMarkMilliS;


        //Left Back Motor
        robot.mtrLeftBack.setDirection(DcMotor.Direction.REVERSE);// Positive input rotates counter clockwise
        robot.mtrLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mtrLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mtrLeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.mtrLeftBack.setTargetPosition(distance);
        robot.mtrLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.mtrLeftBack.setPower(-speed);

        //Left Forward Motor
        robot.mtrLeftFront.setDirection(DcMotor.Direction.FORWARD);// Positive input rotates counter clockwise
        robot.mtrLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mtrLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mtrLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.mtrLeftFront.setTargetPosition(distance);
        robot.mtrLeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.mtrLeftFront.setPower(speed);

        //Right Back Motor
        robot.mtrRightBack.setDirection(DcMotor.Direction.REVERSE);// Positive input rotates counter clockwise
        robot.mtrRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mtrRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mtrRightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.mtrRightBack.setTargetPosition(distance);
        robot.mtrRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.mtrRightBack.setPower(speed);

        //Right Front Motor
        robot.mtrRightFront.setDirection(DcMotor.Direction.FORWARD);// Positive input rotates counter clockwise
        robot.mtrRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mtrRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mtrRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.mtrRightFront.setTargetPosition(distance);
        robot.mtrRightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.mtrRightFront.setPower(-speed);

        lMarkMilliS = System.currentTimeMillis();
        while (((System.currentTimeMillis() - lMarkMilliS) < timeout * 1000) && opModeIsActive() && robot.mtrLeftBack.isBusy() &&
                robot.mtrRightBack.isBusy() && robot.mtrLeftFront.isBusy() && robot.mtrRightFront.isBusy()) {
            telemetry.addData("Motor pos:", " leftBack: %d, rightBack: %d, leftFront: %d, rightFront: %d",
                    robot.mtrLeftBack.getCurrentPosition(), robot.mtrRightBack.getCurrentPosition(), robot.mtrLeftFront.getCurrentPosition(), robot.mtrRightFront.getCurrentPosition());
            telemetry.update();
        }
    }

    // elevator
    public void elevator(int distance, int timeout){
        long lMarkMilliS=System.currentTimeMillis();
        boolean bTimeOut,bExit,bDistValid;
        robot.mtrElev.setDirection(DcMotor.Direction.REVERSE);// Positive input rotates counter clockwise
        robot.mtrElev.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.mtrElev.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.mtrElev.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.mtrElev.setTargetPosition(distance);
        robot.mtrElev.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lMarkMilliS=System.currentTimeMillis();
        bTimeOut=false;
        robot.mtrElev.setPower(0.75);
        while((!bTimeOut)&&(opModeIsActive())&& robot.mtrElev.isBusy()){
            telemetry.addData("Elevator position:", "el:%d", robot.mtrElev.getCurrentPosition());
            telemetry.update();
            if ((System.currentTimeMillis() - lMarkMilliS) > (timeout*1000)) bTimeOut = true;
        }
        robot.mtrElev.setPower(0);
    }
}
