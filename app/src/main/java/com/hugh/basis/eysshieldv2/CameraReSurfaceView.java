package com.hugh.basis.eysshieldv2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.media.FaceDetector;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hugh.basis.eyeshield.FaceDetectionThread;
import com.hugh.basis.eyeshield.measurement.Point;

import java.util.List;

/**
 * Created by chenyw on 2019-11-27.
 */
public class CameraReSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private CameraManager mCameraManager;
    private Handler mHandler;
    /**
     * Represents the standard height of a peace of a4 paper e.g. 29.7cm
     */
    public static final int CALIBRATION_DISTANCE_A4_MM = 294;

    public static final int CALIBRATION_MEASUREMENTS = 10;

    public static final int AVERAGE_THREASHHOLD = 5;

    /**
     * Measured distance at calibration point
     */
    private float _distanceAtCalibrationPoint = -1;

    private float _currentAvgEyeDistance = -1;

    // private int _facesFoundInMeasurement = -1;

    /**
     * in cm
     */
    private float _currentDistanceToFace = -1;

    private FaceDetector.Face _foundFace = null;

    private int _threashold = CALIBRATION_MEASUREMENTS;

    private FaceDetectionThread _currentFaceDetectionThread;

    private List<Point> _points;

    protected final Paint _middlePointColor = new Paint();
    protected final Paint _eyeColor = new Paint();
    // private boolean _measurementStartet = false;
    private boolean _calibrated = false;
    private boolean _calibrating = false;
    private int _calibrationsLeft = -1;

    public CameraReSurfaceView(Context context) {
        super(context);
//        initCamera();
    }

    public CameraReSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCamera();
    }

    public CameraReSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        initCamera();
    }

    private void initCamera() {
        _middlePointColor.setARGB(100, 200, 0, 0);
        _middlePointColor.setStyle(Paint.Style.FILL);
        _middlePointColor.setStrokeWidth(2);

        _eyeColor.setColor(Color.GREEN);

        mHolder = getHolder();
        mHolder.addCallback(this);

        mCameraManager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //打开相机预览
            String cameraidList[] = mCameraManager.getCameraIdList();
            mHandler = new Handler();
            mCameraManager.openCamera("1", new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    Log.e("aaa","onOpened");

                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {

                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {

                }
            }, mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        synchronized (this) {
            // This allows us to make our own drawBitmap
            this.setWillNotDraw(false);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}
