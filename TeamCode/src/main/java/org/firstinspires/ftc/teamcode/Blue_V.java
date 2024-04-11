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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
 
 // NOTE: ASK DIMMIT
 
@Autonomous(name = "Blue_LV", group = "Autonomous")
//@Disabled

public class Blue_V extends LinearOpMode {
    
       DcMotor flw;
DcMotor frw;
DcMotor blw;
DcMotor brw;

//ARM
DcMotor ar; //arm rotation
Servo wr; //wrist

//LIFTS
DcMotor ll, rl;
        
        Servo lk;  // locks them in
Servo pd;
        
    
    int park = 2;
    private ElapsedTime     runtime = new ElapsedTime();
    
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
    private static final String TFOD_MODEL_FILE = "BlueProp.tflite";
    private static final String LABELS[] = {"BlueProp"};
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
        flw = hardwareMap.dcMotor.get("flw");
frw = hardwareMap.dcMotor.get("frw");
blw = hardwareMap.dcMotor.get("blw");
brw = hardwareMap.dcMotor.get("brw");

//ARM
ar = hardwareMap.dcMotor.get("ar");
wr = hardwareMap.servo.get("wr");

//LIFTS
ll = hardwareMap.dcMotor.get("ll");
rl = hardwareMap.dcMotor.get("rl");
pd = hardwareMap.servo.get("pd");
lk = hardwareMap.servo.get("lk");






        //pd = hardwareMap.servo.get("chute servo");
        //leftarm = hardwareMap.dcMotor.get("lam");
frw.setDirection(DcMotorSimple.Direction.REVERSE);
brw.setDirection(DcMotorSimple.Direction.REVERSE);


 rl.setDirection(DcMotorSimple.Direction.REVERSE);
        initTfod();
        
            while (isStarted()==false) {

                telemetryTfod();

                // Push telemetry to the Driver Station.
                telemetry.update();

                // Save CPU resources; can resume streaming when needed.
                

                // Share the CPU.
                
            }
        
        
        
        waitForStart();
        
        
        if(park==1){
        lk.setPosition(.7);
        pd.setPosition(.7);
        encoderDrive(SLOW_DRIVE_SPEED,-7,-7,-7,-7,5);
        sleep(500);
        encoderDrive(MED_DRIVE_SPEED,-6,6,6,-6,2);
        sleep(500);
        pd.setPosition(0);
        sleep(1000);
        encoderDrive(MED_DRIVE_SPEED,2,2,2,2,1);
        sleep(1000);
        encoderDrive(MED_DRIVE_SPEED,5.5,-5.5,5.5,-5.5,2);
        sleep(1000);
        ll.setTargetPosition(600); //blue number is speed and must match eachother
        rl.setTargetPosition(600);
        ll.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ll.setPower(1);
        rl.setPower(1);
        wr.setPosition(.40);
        ar.setTargetPosition(400);// +10
        ar.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ar.setPower(.5);
        sleep(2000);
        encoderDrive(MED_DRIVE_SPEED,-16,-16,-16,-16,5);
        sleep(2000);
        lk.setPosition(0);
        sleep(1000);
        encoderDrive(MED_DRIVE_SPEED,1,1,1,1,1);
        sleep(1000);
       encoderDrive(SLOW_DRIVE_SPEED,17,-17,-17,17,7);//aded
        sleep(1000);
       // encoderDrive(MED_DRIVE_SPEED,-100,-100,-100,-100,1);//aded

        
        }if(park==2){
        lk.setPosition(.7);
        pd.setPosition(.7);
        encoderDrive(SLOW_DRIVE_SPEED,-10,-10,-10,-10,11);
        sleep(500);
        pd.setPosition(0);
        sleep(1000);
        encoderDrive(MED_DRIVE_SPEED,2,2,2,2,1);
        sleep(500);
        encoderDrive(MED_DRIVE_SPEED,7.5,-7.5,7.5,-7.5,2);
        sleep(500);
        encoderDrive(MED_DRIVE_SPEED,2,-2,-2,2,1);
        sleep(1000);
        ll.setTargetPosition(500); //blue number is speed and must match eachother
        rl.setTargetPosition(500);
        ll.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ll.setPower(1);
        rl.setPower(1);
        wr.setPosition(.42);
        ar.setTargetPosition(400);// +10
        ar.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ar.setPower(.5);
        sleep(2000);
        encoderDrive(MED_DRIVE_SPEED,-16,-16,-16,-16,3);
        sleep(2000);
        lk.setPosition(0);
        sleep(1000);
        encoderDrive(MED_DRIVE_SPEED,1,1,1,1,1);
        sleep(1000);
        
         sleep(1000);
       encoderDrive(SLOW_DRIVE_SPEED,15,-15,-15,15,5);//adedTTTTTTTT
        
        }
        if(park==3){
        lk.setPosition(.7);//n lift
        pd.setPosition(.6);//lift
        encoderDrive(SLOW_DRIVE_SPEED,-9,-9,-9,-9,11); // forward
        sleep(500);//pause 
        encoderDrive(MED_DRIVE_SPEED,-7.3,7.3,-7.3,7.3,2); //turn 
        sleep(500); //sleep
        encoderDrive(SLOW_DRIVE_SPEED,-1.4,-1.4,-1.4,-1.4,1); // forward 2 all
        sleep(1000);//pause
        encoderDrive(SLOW_DRIVE_SPEED,-1.4,1.4,1.4,-1.4,1);
        pd.setPosition(0); //white
        sleep(1000); //pause
        encoderDrive(MED_DRIVE_SPEED,3,3,3,3,2); //back
        sleep(500);//pause perfected to here
        encoderDrive(MED_DRIVE_SPEED,14.3,-14.3,14.3,-14.3,2);// left turn
        sleep(1000);//pause
        encoderDrive(MED_DRIVE_SPEED,3,-3,-3,3,3);//left ORIGINAL 2
        sleep(1000); //pause
        ll.setTargetPosition(550); // lift
        rl.setTargetPosition(550);// lift
        ll.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ll.setPower(1);
        rl.setPower(1);
        wr.setPosition(.42); // wrist
        ar.setTargetPosition(400);// arm
        ar.setMode(DcMotor.RunMode.RUN_TO_POSITION); //arm rotation
        ar.setPower(.5);
        sleep(2000); //pause
        encoderDrive(MED_DRIVE_SPEED,-16,-16,-16,-16,3);
        sleep(2000);
        lk.setPosition(0); // grey
        sleep(1000);
        encoderDrive(MED_DRIVE_SPEED,1,1,1,1,1);
        sleep(1000);
        
         sleep(1000);
       encoderDrive(SLOW_DRIVE_SPEED,16,-16,-16,16 ,6);//aded
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
            builder.setCamera(hardwareMap.get(WebcamName.class, "19782Webcam"));
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
        //tfod.setMinResultConfidence(0.75f);

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
            newflmTarget =flw.getCurrentPosition() + (int)(flmInches * COUNTS_PER_INCH);
            newfrmTarget= frw.getCurrentPosition() + (int)(frmInches * COUNTS_PER_INCH);
            newblmTarget =blw.getCurrentPosition() + (int)(blmInches * COUNTS_PER_INCH);
            newbrmTarget =brw.getCurrentPosition() + (int)(brmInches * COUNTS_PER_INCH);
           
           
          //  newfrmTarget = frw.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
           // newlfmTarget = lfm.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
           // newblmTarget = blw.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            //newbrmTarget = brw.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            flw.setTargetPosition(newflmTarget);
            frw.setTargetPosition(newfrmTarget);
            blw.setTargetPosition(newblmTarget);
            brw.setTargetPosition(newbrmTarget);

            // Turn On RUN_TO_POSITION
            flw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            blw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            brw.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            flw.setPower(Math.abs(speed));
            frw.setPower(Math.abs(speed));
            blw.setPower(Math.abs(speed));
            brw.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                  (runtime.seconds() < timeoutS) &&
                   (flw.isBusy() && frw.isBusy() && blw.isBusy() && brw.isBusy())) {

              //   Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newflmTarget,  newfrmTarget, newblmTarget, newbrmTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                                          flw.getCurrentPosition(),
                                            frw.getCurrentPosition(), blw.getCurrentPosition(), brw.getCurrentPosition());
                telemetry.addData("flw Motor Power",flw.getPower());
                telemetry.addData("frw Motor Power",frw.getPower());
                telemetry.addData("blw Motor Power",blw.getPower());
                telemetry.addData("brw Motor Power",brw.getPower());
                telemetry.update();
            }

            // Stop all motion;
            flw.setPower(0);
            frw.setPower(0);
            blw.setPower(0);
            brw.setPower(0);

            // Turn off RUN_TO_POSITION
            flw.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frw.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            blw.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            brw.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
                             }
    private void telemetryTfod() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());
         if (currentRecognitions.size() == 0){
            park=1;
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
            //telemetry.addData("Park pos", park );
            if((x>320)){
                park = 3;
                
            }else if(x<320){
                park = 2;
                
            }
            
        }
        // end for() loop

    }   // end method telemetryTfod()

}   // end class
