/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utl;

import java.util.ArrayList;
import java.util.List;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Point;
import org.opencv.core.Point3;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfPoint3f;
import org.opencv.core.CvType;

public class CVFuncs {
	public static double txOffset() {
		double[][] data = Limelight.getVertices();
		double srx = 0;
		double slx = 0;

		if (data.length > 0 && data[0].length > 3) {
			// double middleX = (data[0][3] + data[0][2])/2;
			// double rightDistortion = data[0][1] - middleX;
			// double leftDistortion = data[0][0] - middleX;
			// length of top
			double s1x = data[0][1] - data[0][0];
			// length of left
			slx = data[0][3] - data[0][0];
			srx = data[0][1] - data[0][2];
			System.out.println("\n\n\n--------\n" + "(" + data[0][0] + "," + data[1][0] + ")" + "(" + data[0][1] + ","
					+ data[1][1] + ")" + "(" + data[0][2] + "," + data[1][2] + ")" + "(" + data[0][3] + "," + data[1][3]
					+ ")");
			System.out.println(s1x + "|" + slx + "|" + srx + "|" + (slx - srx) + "\n-------");
		}
		// if(Math.abs(slx - srx) < 1.5){
		// return 0;
		// }
		return (srx - slx);
	}

	public static double getDistanceToTarget() {
		if (!Limelight.hasAnyTarget())
			return 0;
		double kLimelightPitch = 28; // deg
		// double kLimelightAngle = 0; // deg
		// double goal_angle = Limelight.getTargetXAngle() + kLimelightAngle;
		double goal_pitch = Limelight.getTargetYAngle() + kLimelightPitch;
		double goal_height = 92 - 31;
		return goal_height / Math.tan(Math.toRadians(goal_pitch)) - 120;
	}

	public static Mat[] estimatePose() {
		System.loadLibrary("opencv_java347");

		// sets a mat of 3D points to 3D model vertices of the target
		// TODO the 3D model needs to be updated
		List<Point3> objectPointsList = new ArrayList<Point3>();
		objectPointsList.add(new Point3(-19.625, 0, 0));
		objectPointsList.add(new Point3(19.625, 0, 0));
		objectPointsList.add(new Point3(9.82, 17, 0));
		objectPointsList.add(new Point3(-9.82, 17, 0));
		MatOfPoint3f objectPointsMat = new MatOfPoint3f();
		objectPointsMat.fromList(objectPointsList);

		// sets a Mat of 2D points to the vertices from the Limelight
		double[][] data = Limelight.getVertices();
		if (data[0].length < 4 || data[0][0] == 0) {
			return null;
		}
		List<Point> imagePointsList = new ArrayList<Point>();
		for (int i = 0; i < 4; i++) {
			imagePointsList.add(new Point(data[0][i], data[1][i]));
		}
		MatOfPoint2f imagePointsMat = new MatOfPoint2f();
		imagePointsMat.fromList(imagePointsList);

		// sets a Mat to Limelight's intrinsic parameters (focal length and optical
		// center)
		Mat cameraMatrix = new Mat(3, 3, CvType.CV_64FC1);
		cameraMatrix.put(0, 0, 2.5751292067328632e+02);
		cameraMatrix.put(0, 1, 0);
		cameraMatrix.put(0, 2, 1.5971077914723165e+02);
		cameraMatrix.put(1, 0, 0);
		cameraMatrix.put(1, 1, 2.5635071715912881e+02);
		cameraMatrix.put(1, 2, 1.1971433393615548e+02);
		cameraMatrix.put(2, 0, 0);
		cameraMatrix.put(2, 1, 0);
		cameraMatrix.put(2, 2, 1);

		// sets a Mat to tangential distortion coefficients
		MatOfDouble distCoeffs = new MatOfDouble(2.9684613693070039e-01, -1.4380252254747885e+00,
				-2.2098421479494509e-03, -3.3894563533907176e-03, 2.5344430354806740e+00);

		// creates 2 3x1 mats of type float for rotation/ translation output
		// TODO figure out why depth of 2D matrices is 6
		Mat rvec = new Mat(3, 1, CvType.CV_64FC1);
		Mat tvec = new Mat(3, 1, CvType.CV_64FC1);

		if (Calib3d.solvePnP(objectPointsMat, imagePointsMat, cameraMatrix, distCoeffs, rvec, tvec)) {
			return new Mat[] { rvec, tvec };
		}
		return null;

	}

	public static double[] getRotation() {
		Mat[] data = estimatePose(); // data[0] has the rvec values
		double[] rXYZ = new double[3];

		if (data != null) { // initializes array rXYZ with rvec values
			rXYZ[0] = data[0].get(0, 0)[0];
			rXYZ[1] = data[0].get(1, 0)[0];
			rXYZ[2] = data[0].get(2, 0)[0];

			// TODO process raw rotation data
			System.out.println("R: " + rXYZ[0] + "," + rXYZ[1] + "," + rXYZ[2]);
		}
		return rXYZ;
	}

	public static double[] getTranslation() {
		Mat[] data = estimatePose(); // data[1] has the tvec values
		double[] tXYZ = new double[3];

		if (data != null) { // initializes array tXYZ with tvec
			tXYZ[0] = data[1].get(0, 0)[0];
			tXYZ[1] = data[1].get(1, 0)[0];
			tXYZ[2] = data[1].get(2, 0)[0];

			// TODO process raw translation data
			System.out.println("T: " + (Math.sqrt(Math.pow(tXYZ[0], 2) + Math.pow(tXYZ[2], 2))));
		}
		return tXYZ;
	}

	public static void main(String[] args) {
		getRotation();
		getTranslation();
	}
}
