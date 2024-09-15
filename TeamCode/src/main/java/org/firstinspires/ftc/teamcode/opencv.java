package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "OpenCV Testing")

public class opencv extends LinearOpMode {

    double cX = 0;
    double cY = 0;
    double width = 0;

    private OpenCvCamera webcam;  /* Use OpenCvCamera class from FTC SDK */
    private static final int CAMERA_WIDTH = 640; /* width  of wanted camera resolution */
    private static final int CAMERA_HEIGHT = 360; /* height of wanted camera resolution */

    /* Calculate the distance using the formula */
    public static final double objectWidth = 3.75;  /* Replace with the actual width of the object in real-world units */
    public static final double focalLength = 728;  /* Replace with the focal length of the camera in pixels */


    @Override
    public void runOpMode() {

        initOpenCV();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Coordinate", "(" + (int) cX + ", " + (int) cY + ")");
            telemetry.addData("Distance in Inch", (getDistance(width)));
            telemetry.update();

            /* The OpenCV pipeline automatically processes frames and handles detection */
        }

        /* Release resources */
        webcam.stopStreaming();
    }

    private void initOpenCV()
    {
        /* Create an instance of the camera */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        /* Use OpenCvCameraFactory class from FTC SDK to create camera instance */
        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        webcam.setPipeline(new ColorBlobDetectionPipeline());

        webcam.openCameraDevice();
    }

    class ColorBlobDetectionPipeline extends OpenCvPipeline {
        @Override
        public Mat processFrame(Mat frame) {
            /* Preprocess the frame to detect yellow regions */
            Mat colorMask = preprocessFrame(frame);

            /* Find contours of the detected yellow regions */
            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(colorMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            /* Find the largest yellow contour (blob) */
            MatOfPoint largestContour = findLargestContour(contours);

            if (largestContour != null) {
                /* Calculate the width of the bounding box */
                width = calculateWidth(largestContour);

                /* Calculate the centroid of the largest contour */
                Moments moments = Imgproc.moments(largestContour);
                cX = moments.get_m10() / moments.get_m00();
                cY = moments.get_m01() / moments.get_m00();
            }

            return frame;
        }

        private Mat preprocessFrame(Mat frame) {
            Mat hsvFrame = new Mat();
            Imgproc.cvtColor(frame, hsvFrame, Imgproc.COLOR_BGR2HSV);

            /* Intervalul de galben/verde, de saturatie si luminozitate (HSV) */
            Scalar lowerColor = new Scalar(100, 100, 100);
            Scalar upperColor = new Scalar(180, 255, 255);

            Mat colorMask = new Mat();
            Core.inRange(hsvFrame, lowerColor, upperColor, colorMask);

            Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
            Imgproc.morphologyEx(colorMask, colorMask, Imgproc.MORPH_OPEN, kernel);
            Imgproc.morphologyEx(colorMask, colorMask, Imgproc.MORPH_CLOSE, kernel);

            return colorMask;
        }

        private MatOfPoint findLargestContour(List<MatOfPoint> contours) {
            double maxArea = 0;
            MatOfPoint largestContour = null;

            for (MatOfPoint contour : contours) {
                double area = Imgproc.contourArea(contour);
                if (area > maxArea) {
                    maxArea = area;
                    largestContour = contour;
                }
            }

            return largestContour;
        }
        private double calculateWidth(MatOfPoint contour) {
            Rect boundingRect = Imgproc.boundingRect(contour);
            return boundingRect.width;
        }

    }
    private static double getDistance(double width){
        double distance = (objectWidth * focalLength) / width;
        return distance;
    }
}