/* Copyright (c) 2019 FIRST. All rights reserved.
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

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

/*
 * This OpMode illustrates the basics of TensorFlow Object Detection,
 * including Java Builder structures for specifying Vision parameters.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */
@Autonomous(name = "RedRight", group = "Autonomous")

public class BlueLeft extends LinearOpMode {
    
       DcMotor flm;   //   front left motor
        DcMotor frm;  //     front right motor
        DcMotor blm;  //     back left motor
        DcMotor brm;  //       back right motor

        DcMotor rslide;
        DcMotor lslide;
        DcMotor arm; 
        
        Servo twist;
        Servo gate;
        Servo auton;
        
    
    int park = 2;
    private ElapsedTime     runtime = new ElapsedTime();
    
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
    private static final String TFOD_MODEL_FILE = "RedCastle.tflite";
    private static final String LABELS[] = {"RedCastle"};
    static final double COUNTS_PER_MOTOR_REV = 383.6*2;
    static final double DRIVE_GEAR_REDUCTION = 2.0;
    static final double WHEEL_DIAMETER_INCHES = 4.0;
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION)/(WHEEL_DIAMETER_INCHES*3.1415);
    
    static final double SLOW_DRIVE_SPEED = 0.1;
    static final double FAST_DRIVE_SPEED = 0.8;
    static final double MED_DRIVE_SPEED = 0.5;
    static final double MY_DRIVE_SPEED = 0.5;
    static final double STRAFE_DRIVE_SPEED = 0.65;
    static final double EXTRA_FAST_SPEED = 1;
    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {
        flm = hardwareMap.dcMotor.get("flm");
frm = hardwareMap.dcMotor.get("frm");
blm = hardwareMap.dcMotor.get("blm");
brm = hardwareMap.dcMotor.get("brm");

arm = hardwareMap.dcMotor.get("arm");
rslide= hardwareMap.dcMotor.get("rslide");
lslide  = hardwareMap.dcMotor.get("lslide");




gate = hardwareMap.get(Servo.class, "gate");
twist = hardwareMap.get(Servo.class, "twist");
auton = hardwareMap.get(Servo.class, "auton");


        //auton = hardwareMap.servo.get("chute servo");
        //leftarm = hardwareMap.dcMotor.get("lam");
blm.setDirection(DcMotor.Direction.REVERSE);
flm.setDirection(DcMotor.Direction.REVERSE);

rslide.setDirection(DcMotorSimple.Direction.REVERSE);

rslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
rslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

lslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
lslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//leftarm.setDirection(DcMotor.Direction.REVERSE);
        initTfod();
        
            while (isStarted()==false) {

                telemetryTfod();

                // Push telemetry to the Driver Station.
                telemetry.update();

                // Save CPU resources; can resume streaming when needed.
                

                // Share the CPU.
                
            }
        
        //telemetryTfod();
        //telemetry.uautonate();
        // Wait for the DS start button to be touched.
        //telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        //telemetry.addData(">", "Touch Play to start OpMode");
        //telemetryTfod();
        /*List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());
        
        telemetry.addData("Park Pos", park);
        telemetry.addData("x Position", x);
        telemetry.addData("y Position", y);
        telemetry.uautonate();*/
        
        waitForStart();
        //leftarm.setTargetPosition(leftarm.getCurrentPosition()+100);
         //leftarm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
         //leftarm.setPower(1);
        
        //encoderDrive(SLOW_DRIVE_SPEED,5,5,5,5,3);
        //sleep(500);
        if(park==1){
          auton.setPosition(.5);
          gate.setPosition(1);
        sleep(500);
        encoderDrive(SLOW_DRIVE_SPEED,-9,-9,-9,-9,5.5);
        sleep(500);
        encoderDrive(SLOW_DRIVE_SPEED,-7.2,7.2,-7.2,7.2,6);
        sleep(500);
        // encoderDrive(SLOW_DRIVE_SPEED,-1.2,1.2,1.2,-1.2,.8);
        //sleep(500);
        encoderDrive(SLOW_DRIVE_SPEED,-2,-2,-2,-2,2);
        sleep(1000);
        //encoderDrive(SLOW_DRIVE_SPEED,1,1,1,1,1);
        //sleep(1000);
        //encoderDrive(SLOW_DRIVE_SPEED,1,-1,1,-1,1);
        //sleep(500);
       // encoderDrive(SLOW_DRIVE_SPEED,-2,-2,-2,-2,1);
        //sleep(500);
        //encoderDrive(SLOW_DRIVE_SPEED,1,-1,-1,1,.95);
        //sleep(2000);
        //encoderDrive(SLOW_DRIVE_SPEED,-10,-10,-10,-10,4);
        //encoderDrive(SLOW_DRIVE_SPEED,.5,.5,.5,.5,1);
        //sleep(100);
        auton.setPosition(1);
        sleep(1000);
        encoderDrive(MED_DRIVE_SPEED,3,3,3,3,2);
        sleep(500);
        encoderDrive(MED_DRIVE_SPEED,13,-13,13,-13,7);
        sleep(500);
        lslide.setTargetPosition(500);
        lslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lslide.setPower(1);
        rslide.setTargetPosition(500);
        rslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rslide.setPower(1);
        sleep(1000);
        twist.setPosition(.3);
        arm.setTargetPosition(250);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(1);
        sleep(500);
        
        encoderDrive(MED_DRIVE_SPEED,-14,-14,-14,-14,7);
        
        sleep(2000);
        gate.setPosition(0);
        sleep(700);
        encoderDrive(MED_DRIVE_SPEED,2,2,2,2,1);
        sleep(500);
        //encoderDrive(SLOW_DRIVE_SPEED,5,5,5,5,3);
        //sleep(500);
        
        
        
        }if(park==2){
        auton.setPosition(.5);
        sleep(500);
        gate.setPosition(1);
        sleep(500);
        encoderDrive(SLOW_DRIVE_SPEED,-10.7,-10.7,-10.7,-10.7,6);
        sleep(500);
        encoderDrive(SLOW_DRIVE_SPEED,1,-1,-1,1,.8);
        sleep(1000);
         //encoderDrive(SLOW_DRIVE_SPEED,-1,1,-1,1,1);
        //sleep(500);
       // encoderDrive(SLOW_DRIVE_SPEED,1,-1,-1,1,1);
       // sleep(500);
        //encoderDrive(SLOW_DRIVE_SPEED,-10,-10,-10,-10,4);
        auton.setPosition(1);
        sleep(500);
        encoderDrive(SLOW_DRIVE_SPEED,3,3,3,3,3);
        sleep(500);
        encoderDrive(SLOW_DRIVE_SPEED,-2,2,2,-2,1);
        sleep(500);
        encoderDrive(SLOW_DRIVE_SPEED,7.5,-7.5,7.5,-7.5,6.5);
        sleep(500);
        encoderDrive(SLOW_DRIVE_SPEED,2,-2,-2,2,1.2);
        sleep(500);
        lslide.setTargetPosition(500);
        lslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lslide.setPower(1);
        rslide.setTargetPosition(500);
        rslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rslide.setPower(1);
        sleep(1000);
        twist.setPosition(.3);
        arm.setTargetPosition(250);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(1);
        sleep(500);
        
        encoderDrive(MED_DRIVE_SPEED,-14.4,-14.4,-14.4,-14.4,5.4);
         twist.setPosition(.3);
        sleep(1000);
        gate.setPosition(0);
        sleep(700);
        encoderDrive(MED_DRIVE_SPEED,2,2,2,2,1);
        sleep(500);

        }
        if(park==3){
        auton.setPosition(.5);
        gate.setPosition(1);
        sleep(500);
        encoderDrive(SLOW_DRIVE_SPEED,-7,-7,-7,-7,4.5);
        sleep(500);
        encoderDrive(SLOW_DRIVE_SPEED,-2.8,2.8,2.8,-2.8,5);
        sleep(1500);
        //encoderDrive(SLOW_DRIVE_SPEED,-10,-10,-10,-10,4);
        auton.setPosition(1);
        sleep(1000);
        encoderDrive(SLOW_DRIVE_SPEED,5,5,5,5,2);
        sleep(500);
        encoderDrive(SLOW_DRIVE_SPEED,-3,3,3,-3,1);
        sleep(1000);
         encoderDrive(FAST_DRIVE_SPEED,5.7,-5.7,5.7,-5.7,2);
        sleep(500);
        lslide.setTargetPosition(500);
        lslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lslide.setPower(1);
        rslide.setTargetPosition(500);
        rslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rslide.setPower(1);
        sleep(1000);
        twist.setPosition(.3);
        arm.setTargetPosition(250);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(1);
        sleep(500);
        
        encoderDrive(MED_DRIVE_SPEED,-14,-14,-14,-14,2.5);
        
        sleep(2000);
        
        sleep(1500);
        gate.setPosition(0);
        sleep(700);
        encoderDrive(MED_DRIVE_SPEED,2,2,2,2,1);
        sleep(500);
        
        
        
        }
        
        

        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();

    }   // end runOpMode()

    /**
     * Initialize the TensorFlow Object Detection processor.
     */
    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()
        .setModelFileName(TFOD_MODEL_FILE)
        .setModelLabels(LABELS)
        .setIsModelTensorFlow2(true)
        .setIsModelQuantized(true)
        .setModelInputSize(300)
        .setModelAspectRatio(16.0 / 9.0)

            // Use setModelAssetName() if the TF Model is built in as an asset.
            // Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
            //.setModelAssetName(TFOD_MODEL_ASSET)
            //.setModelFileName(TFOD_MODEL_FILE)

            //.setModelLabels(LABELS)
            //.setIsModelTensorFlow2(true)
            //.setIsModelQuantized(true)
            //.setModelInputSize(300)
            //.setModelAspectRatio(16.0 / 9.0)

            .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "15151webcam"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableCameraMonitoring(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        tfod.setMinResultConfidence(0.7f);

        // Disable or re-enable the TFOD processor at any time.
        //visionPortal.setProcessorEnabled(tfod, true);

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
     public void encoderDrive(double speed,
                             double flmInches, double frmInches, double blmInches, double brmInches,
                             double timeoutS) {
        int newflmTarget;
        int newfrmTarget;
        int newblmTarget;
        int newbrmTarget;
       

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newflmTarget =flm.getCurrentPosition() + (int)(flmInches * COUNTS_PER_INCH);
            newfrmTarget= frm.getCurrentPosition() + (int)(frmInches * COUNTS_PER_INCH);
            newblmTarget =blm.getCurrentPosition() + (int)(blmInches * COUNTS_PER_INCH);
            newbrmTarget =brm.getCurrentPosition() + (int)(brmInches * COUNTS_PER_INCH);
           
           
          //  newfrmTarget = frm.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
           // newlfmTarget = lfm.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
           // newblmTarget = blm.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            //newbrmTarget = brm.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            flm.setTargetPosition(newflmTarget);
            frm.setTargetPosition(newfrmTarget);
            blm.setTargetPosition(newblmTarget);
            brm.setTargetPosition(newbrmTarget);

            // Turn On RUN_TO_POSITION
            flm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            blm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            brm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            flm.setPower(Math.abs(speed));
            frm.setPower(Math.abs(speed));
            blm.setPower(Math.abs(speed));
            brm.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                  (runtime.seconds() < timeoutS) &&
                   (flm.isBusy() && frm.isBusy() && blm.isBusy() && brm.isBusy())) {

              //   Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newflmTarget,  newfrmTarget, newblmTarget, newbrmTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                                          flm.getCurrentPosition(),
                                            frm.getCurrentPosition(), blm.getCurrentPosition(), brm.getCurrentPosition());
                telemetry.addData("flm Motor Power",flm.getPower());
                telemetry.addData("frm Motor Power",frm.getPower());
                telemetry.addData("blm Motor Power",blm.getPower());
                telemetry.addData("brm Motor Power",brm.getPower());
                telemetry.update();
            }

            // Stop all motion;
            flm.setPower(0);
            frm.setPower(0);
            blm.setPower(0);
            brm.setPower(0);

            // Turn off RUN_TO_POSITION
            flm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            blm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            brm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
                             }
    private void telemetryTfod() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());
        if(currentRecognitions.size()==0){
            park =1;
        }
        telemetry.addData("Park pos", park );

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
            
            if((x>320)){
                park = 3;
                
            }else if(x<320){
                park = 2;
                
            }
            
        }   // end for() loop

    }   // end method telemetryTfod()

}   // end class
