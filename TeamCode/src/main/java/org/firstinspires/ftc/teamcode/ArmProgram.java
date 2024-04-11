package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.Range;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Set;

import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

import java.text.SimpleDateFormat;

import java.util.Date;


@TeleOp(name="ArmProgram", group="Teleop")
//Adapted

public class ArmProgram extends OpMode {

DcMotor lslide, rslide; //left and right lift






public void init() {

//LIFTS
lslide = hardwareMap.dcMotor.get("lslide");
rslide = hardwareMap.dcMotor.get("rslide");


 rslide.setDirection(DcMotorSimple.Direction.REVERSE);

//ENCODERS FOR LIFTS
        //use encoders for arms
        rslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //reset encoders
        rslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
 

}
public void loop() {


if (gamepad2.right_trigger==1){

                lslide.setTargetPosition(lslide.getCurrentPosition()+30); //blue number is speed and must match eachother
                rslide.setTargetPosition(rslide.getCurrentPosition()+30);
                lslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lslide.setPower(1);
                rslide.setPower(1);
            
        }
        else if (gamepad2.left_trigger==1){
                lslide.setTargetPosition(lslide.getCurrentPosition()-30);
                rslide.setTargetPosition(rslide.getCurrentPosition()-30);
                lslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lslide.setPower(1);
                rslide.setPower(1);
            

        }
       telemetry.addData("lslide pos",lslide.getCurrentPosition());
       telemetry.addData("rslide pos",rslide.getCurrentPosition());
       telemetry.update();
//Arm


 
        
     
        
        
 //   else if (gamepad1.left_bumper){ 
//        bruh.setPosition(1);}
//else{bruh.setPosition(.5);}


//boolean thisPosition = gamepad2.x;
//if(thisPosition && !lastPosition){
 //if(servoPosition ==.64){servoPosition = 1;}
// else{servoPosition =.64;}
}

//lastPosition = thisPosition;
//oof.setPosition(servoPosition);


//if(gamepad2.dpad_up){
 //armmotor.setTargetPosition(80);
 //armmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

 //armmotor.setPower(-1);

}

//if(gamepad2.left_bumper){
 //armmotor.setTargetPosition(-100);
 //armmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
 //armmotor.setPower(1);
//}
      
 //if(gamepad2.a){
 //armmotor.setTargetPosition(158);
 //armmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

 //armmotor.setPower(1);
//}

 //if(gamepad2.b){
 //armmotor.setTargetPosition(250);
 //armmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

 //armmotor.setPower(1);
//}

//if(gamepad2.right_bumper){
 //armmotor.setTargetPosition(353);
 //armmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
 
 //armmotor.setPower(1);
//}

/*if(gamepad2.left_bumper){
 //armmotor.setTargetPosition(armposition + 15);
 armmotor.getCurrentPosition(armposition + 15);
 armmotor.setPower(1);
}*/

//if(gamepad2.y){
 //armmotor.setPower(-1);
//}
      
//if (gamepad2.dpad_down){
//        armmotor.setPower(0);
 //       armmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//}


//if(gamepad2.dpad_right){
 //hex.setPower(1);
//}

//else if(gamepad2.dpad_left){
 //hex.setPower(-1);
 
//}

//else{hex.setPower(0);}
//}}
/*double Speed  = -gamepad1.left_stick_y;
 double Turn  = gamepad1.right_stick_x;
 double Strafe = gamepad1.left_stick_x;

double flmspeed = Speed + Turn + Strafe;
double frmspeed = Speed - Turn - Strafe;
double blmspeed = Speed - Turn + Strafe;
double brmspeed = Speed + Turn - Strafe;

flm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
frm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
blm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
brm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

if(gamepad1.right_trigger >=.3){
 flm.setPower(Range.clip(flmspeed, -.4, .4));
frm.setPower(Range.clip(frmspeed, -.4, .4));
 blm.setPower(Range.clip(blmspeed, -.4, .4));
  brm.setPower(Range.clip(brmspeed, -.4, .4));
}



else{
 flm.setPower(Range.clip(flmspeed, -1, 1));
 frm.setPower(Range.clip(frmspeed, -1, 1));
 blm.setPower(Range.clip(blmspeed, -1, 1));
 brm.setPower(Range.clip(brmspeed, -1, 1));

 /* flm = (flm.setPower(Range.clip, -1, 1));
  frm = (flm.setPower(Range.clip, -1, 1));
  blm = (blm.setPower(Range.clip, -1, 1));
  brm = (brm.setPower(Range.clip, -1, 1));*/

/*if(gamepad1.right_trigger >=.3){
 flm = (Range.clip(flm.setPower, -.5, .5));
 frm = (Range.clip(frm.setPower, -.5, .5));
 blm = (Range.clip(blm.setPower, -.5, .5));
 brm = (Range.clip(brm.setPower, -.5, .5));
}
else{
 flm = Range.clip(flm.setPower, -1, 1);
 frm = Range.clip(frm.setPower, -1, 1);
 blm = Range.clip(blm.setPower, -1, 1);
 brm = Range.clip(brm.setPower, -1, 1);
}*/
 /*double drive  = -gamepad1.left_stick_y*.7;
 double strafe = gamepad1.left_stick_x*.6;
 double twist  = gamepad1.right_stick_x*.5;

 double[] speeds = {
 (drive + strafe + twist),
 (drive - strafe - twist),
 (drive - strafe + twist),
 (drive + strafe - twist)
 };


  double max = Math.abs(speeds[0]);
        for(int i = 0; i < speeds.length; i++) {
            if ( max < Math.abs(speeds[i]) ) max = Math.abs(speeds[i]);
        }
        
         if (max > .5) {
            for (int i = 0; i < speeds.length; i++) speeds[i] /= max;
        }
        
        if(gamepad1.right_trigger){
        
        flm.setPower(Range.clip(speeds[0]));
        frm.setPower(Range.clips(peeds[1]));
        blm.setPower(Range.clip(speeds[2]));
        brm.setPower(Range.clip(speeds[3]));
    }
}
}*/

