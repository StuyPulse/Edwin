/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.stuypulse.robot.util;

import java.util.ArrayList;
import java.util.List;

import com.stuypulse.robot.Constants.Alignment;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
/**
 * Add your docs here.
 */
public class CVFuncs {
	public static boolean hasValidVertices(){
		double[][] data = Limelight.getVertices();
		return data.length > 0 && data[0].length > 3;
	}
    public static double txOffset(){
        double[][] data = Limelight.getVertices();
        double srx = 0;
        double slx = 0;
        if(data.length > 0 && data[0].length > 3){
            double middleX = (data[0][3] + data[0][2])/2;
            double right = data[0][1] - middleX;
            double left = data[0][0] - middleX;
            //length of top
            double s1x = data[0][1] - data[0][0];
            //length of left
            slx = data[0][3] - data[0][0];
            srx = data[0][1] - data[0][2];
            // System.out.println("\n\n\n--------\n"+"("+data[0][0]+","+data[1][0]+")"+
            //                     "("+data[0][1]+","+data[1][1]+")"+
            //                     "("+data[0][2]+","+data[1][2]+")"+
            //                     "("+data[0][3]+","+data[1][3]+")");
            // System.out.println(s1x+"|"+slx+"|"+srx+"|"+(slx-srx)+"\n-------");
        }
        return srx-slx;
	}
	public static double cvtAngleToDistance(double ty){
		if(Limelight.hasValidTarget()) {
            double goal_pitch = ty + Alignment.Measurements.Limelight.PITCH;
            double goal_height = Alignment.Measurements.GOAL_HEIGHT - Alignment.Measurements.Limelight.HEIGHT;
            double goal_dist = goal_height / Math.tan(Math.toRadians(goal_pitch))
					    	   - Alignment.Measurements.Limelight.DISTANCE;
			return goal_dist;
		}
		return -1;
	}
	public static double cvtPixelToAngle(double p){
		double cvtNormalize = (1/120) * (119.5 - p);
		//Vertical FOV is 45.7 deg
		double vph = 2.0*Math.tan(Math.toRadians(45.7 /2));
		double y = vph/2 * cvtNormalize;
		return Math.atan2(1,y);
	}
	public static double cvtYPixelToDistance(double p){
		return cvtAngleToDistance(cvtPixelToAngle(p));
	}
	public static double newOffset(){
		if(hasValidVertices()){
			double a = cvtAngleToDistance(Limelight.getTargetYAngle());
			System.out.println(a);
		}
		return 0.0;
	}
	public static Mat[] estimatePose() {
		List<Point3> objectPointsList = new ArrayList<Point3>();
		objectPointsList.add(new Point3(-19.625, 0, 0));
		objectPointsList.add(new Point3(19.625, 0, 0));
		objectPointsList.add(new Point3(9.82, 17, 0));
		objectPointsList.add(new Point3(-9.82, 17, 0));
		
		MatOfPoint3f objectPointsMat = new MatOfPoint3f();
        objectPointsMat.fromList(objectPointsList);
        		
        List<Point> imagePointsList = new ArrayList<Point>();
        double[][] data = Limelight.getVertices();
        if(data[0][0] == 0){
            return null;
        }
        for(int i = 0; i < 4; i++){
          imagePointsList.add(new Point(data[0][i], data[1][i]));
        }
		
		MatOfPoint2f imagePointsMat = new MatOfPoint2f();
		imagePointsMat.fromList(imagePointsList);
			
        MatOfDouble cameraMatrix = new MatOfDouble(2.5751292067328632e+02, 0., 1.5971077914723165e+02, 0.,2.5635071715912881e+02, 1.1971433393615548e+02, 0., 0., 1.);
		MatOfDouble distCoeffs = new MatOfDouble(2.9684613693070039e-01, -1.4380252254747885e+00, -2.2098421479494509e-03, -3.3894563533907176e-03, 2.5344430354806740e+00);
		Mat rvec = new Mat();
		Mat tvec = new Mat();
		
		if(Calib3d.solvePnP(objectPointsMat, imagePointsMat, cameraMatrix, distCoeffs, rvec, tvec)){
            return new Mat[] {rvec, tvec};
        }
        return null;
    }
	
    public static double[] getXYZ(){
        Mat[] stuff = estimatePose();
        double[] xyz = new double[3];
        if(stuff != null){
            xyz[0] = stuff[1].get(0, 0)[0];
            xyz[1] = stuff[1].get(0, 0)[1];
            xyz[2] = stuff[1].get(0, 0)[2];
            System.out.println(xyz[0]+","+xyz[1]+","+xyz[2]);
        }
        return xyz;
    }
}