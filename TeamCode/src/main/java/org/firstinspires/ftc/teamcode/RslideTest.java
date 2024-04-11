package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Set;

import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.text.SimpleDateFormat;

import java.util.Date;


@TeleOp(name="RslideTest", group="Teleop")


public class RslideTest extends OpMode {

DcMotor flm;
DcMotor frm;
DcMotor blm;
DcMotor brm;

DcMotor arm;
DcMotor rslide;
//DcMotor lslide;
DcMotor intake;



//Servo cs;
//Servo ws;
//Servo ps;

//boolean lastPositionClaw = false;
//boolean lastPositionTwist = false;


//double clawPosition = 0;
//double twistPosition = .7;



//boolean lastPosition = false;


//double servoPosition = .64;

//int armposition = 15;

public void init() {

flm = hardwareMap.dcMotor.get("flm");
frm = hardwareMap.dcMotor.get("frm");
blm = hardwareMap.dcMotor.get("blm");
brm = hardwareMap.dcMotor.get("brm");

arm = hardwareMap.dcMotor.get("arm");
//lslide = hardwareMap.dcMotor.get("lslide");
rslide = hardwareMap.dcMotor.get("rslide");
intake = hardwareMap.dcMotor.get("intake");

//cs = hardwareMap.get(Servo.class, "cs");
//ws = hardwareMap.get(Servo.class, "ws");
//ps = hardwareMap.get(Servo.class, "ps");

//flm.setDirection(DcMotorSimple.Direction.REVERSE);
//arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

//lslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//lslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

rslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
rslide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

flm.setDirection(DcMotorSimple.Direction.REVERSE);
blm.setDirection(DcMotorSimple.Direction.REVERSE);
//lslide.setDirection(DcMotorSimple.Direction.REVERSE);
//cs.setPosition(.6);
telemetry.addData("Status", "Initialized");
//telemetry.addData("Arianna", "<3 <3 <3 <3 <3 <3");
//ps.setPosition(.7);

}

public void loop() {



 double drive  = -gamepad1.left_stick_y*.7;
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
        
        
        flm.setPower(speeds[0]);
        frm.setPower(speeds[1]);
        blm.setPower(speeds[2]);
        brm.setPower(speeds[3]);
        
        
         if(gamepad2.dpad_up){
         rslide.setTargetPosition(rslide.getCurrentPosition()+80);
         rslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
         //lslide.setTargetPosition(lslide.getCurrentPosition()-80);
         //lslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rslide.setPower(.5);
        //lslide.setPower(.5);
        }
        
        
        
        if(gamepad2.dpad_down){
         rslide.setTargetPosition(rslide.getCurrentPosition()-80);
         rslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
         //lslide.setTargetPosition(lslide.getCurrentPosition()+80);
         //lslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rslide.setPower(.5);
        //lslide.setPower(.5);
         

        }
        
        if(gamepad2.left_bumper){
         
        arm.setPower(1);
        
        }
        if(gamepad2.right_bumper){
         
        arm.setPower(0);
        }
        /*boolean thisPositionClaw = gamepad2.x;
        if(thisPositionClaw && !lastPositionClaw){
        if(clawPosition ==0){clawPosition = 1;}
        else{clawPosition =0;}
        }
        lastPositionClaw = thisPositionClaw;
        
        boolean thisPositionTwist = gamepad2.b;
        if(thisPositionTwist && !lastPositionTwist){
        if(twistPosition ==.7){twistPosition = .6;}
        else{twistPosition =.6;}
        }
        lastPositionTwist = thisPositionTwist;

        ws.setPosition(twistPosition);
        cs.setPosition(clawPosition);
*/
        if(gamepad2.a){
        //ps.setPosition(0);
        }if(gamepad2.y){
        //ps.setPosition(1);
        }
        if(gamepad2.x){
         
        arm.setPower(1);
       
        }else{
         arm.setPower(0);
        }
        if(gamepad2.b){
        
        arm.setPower(-1);
        
        
        }else{
         arm.setPower(0);
        }
        if(gamepad2.dpad_up){
         rslide.setTargetPosition(rslide.getCurrentPosition()+80);
         //lslide.setTargetPosition(lslide.getCurrentPosition()+50);
         rslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
         //lslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
         //lslide.setPower(1);
         rslide.setPower(1);
         }if(gamepad2.dpad_down){
         rslide.setTargetPosition(rslide.getCurrentPosition()-80);
         //lslide.setTargetPosition(lslide.getCurrentPosition()-50);
         rslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
         //lslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
         //lslide.setPower(1);
         rslide.setPower(1);
        }
    }
}











