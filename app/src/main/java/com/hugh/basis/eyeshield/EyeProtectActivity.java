package com.hugh.basis.eyeshield;

import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.hugh.basis.R;
import com.hugh.basis.eyeshield.messages.MeasurementStepMessage;
import com.hugh.basis.eyeshield.messages.MessageHUB;
import com.hugh.basis.eyeshield.messages.MessageListener;
import com.hugh.basis.eysshieldv2.EyeProtectReActivity;


public class EyeProtectActivity extends Activity implements MessageListener {

	public static final String CAM_SIZE_WIDTH = "intent_cam_size_width";
	public static final String CAM_SIZE_HEIGHT = "intent_cam_size_height";
	public static final String AVG_NUM = "intent_avg_num";
	public static final String PROBANT_NAME = "intent_probant_name";

	private CameraSurfaceView _mySurfaceView;
	Camera _cam;

	private final static DecimalFormat _decimalFormater = new DecimalFormat(
			"0.0");

	private float _currentDevicePosition;

	private int _cameraHeight;
	private int _cameraWidth;
	private int _avgNum;

	TextView _currentDistanceView;
	Button _calibrateButton;
	private Button mBtnCameraStart;

	/**
	 * Abusing the media controls to create a remote control
	 */
	// ComponentName _headSetButtonReceiver;
	// AudioManager _audioManager;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.measurement_activity);

		_mySurfaceView = (CameraSurfaceView) findViewById(R.id.surface_camera);
		_currentDistanceView = (TextView) findViewById(R.id.currentDistance);
		_calibrateButton = (Button) findViewById(R.id.calibrateButton);
		mBtnCameraStart  = findViewById(R.id.camera_start);
		mBtnCameraStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(
						(int) (0.95 * EyeProtectActivity.this.getResources().getDisplayMetrics().widthPixels),
						(int) (0.6 * EyeProtectActivity.this.getResources().getDisplayMetrics().heightPixels));

				layout.setMargins(0, (int) (0.05 * EyeProtectActivity.this.getResources()
						.getDisplayMetrics().heightPixels), 0, 0);

				_mySurfaceView.setLayoutParams(layout);
				int rotation = EyeProtectActivity.this.getWindowManager().getDefaultDisplay().getRotation();
				Log.e("rotation",rotation+"");
				MessageHUB.get().registerListener(EyeProtectActivity.this);
				// _audioManager.registerMediaButtonEventReceiver(_headSetButtonReceiver);

				// 1 for front cam. No front cam ? Not my fault!
				_cam = Camera.open(1);
				Camera.Parameters param = _cam.getParameters();

				// Find the best suitable camera picture size for your device. Competent
				// research has shown that a smaller size gets better results up to a
				// certain point.
				// http://ieeexplore.ieee.org/xpl/login.jsp?tp=&arnumber=6825217&url=http%3A%2F%2Fieeexplore.ieee.org%2Fiel7%2F6816619%2F6825201%2F06825217.pdf%3Farnumber%3D6825217
				List<Size> pSize = param.getSupportedPictureSizes();//返回支持的图像列表  即宽、高
				double deviceRatio = (double) EyeProtectActivity.this.getResources().getDisplayMetrics().widthPixels
						/ (double) EyeProtectActivity.this.getResources().getDisplayMetrics().heightPixels;  //获取屏幕尺寸的宽高比

				Log.e("onResume","widthPixels"+EyeProtectActivity.this.getResources().getDisplayMetrics().widthPixels+"heightPixels"+EyeProtectActivity.this.getResources().getDisplayMetrics().heightPixels);
				Size bestSize = pSize.get(0);
				double bestRation = (double) bestSize.width / (double) bestSize.height; //第一个宽高比

				for (Size size : pSize) {   //找一个最相近的宽高比
					double sizeRatio = (double) size.width / (double) size.height;

					if (Math.abs(deviceRatio - bestRation) > Math.abs(deviceRatio
							- sizeRatio)) {
						bestSize = size;
						bestRation = sizeRatio;
					}
				}
				_cameraHeight = bestSize.height; //得到最适合图片的宽高
				_cameraWidth = bestSize.width;

				Log.d("PInfo", _cameraWidth + " x " + _cameraHeight);

//				param.setPreviewSize(pSize.get(0).width, pSize.get(0).height); //设置预览界面尺寸
//				_cam.setParameters(param);

				_mySurfaceView.setCamera(_cam); //往surfaceView传入camera
				_mySurfaceView.isStart = true;
			}
		});

		// _audioManager = (AudioManager) this
		// .getSystemService(Context.AUDIO_SERVICE);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();

		MessageHUB.get().unregisterListener(this);

		// _audioManager
		// .unregisterMediaButtonEventReceiver(_headSetButtonReceiver);

		resetCam();
	}

	/**
	 * Sets the current eye distance to the calibration point.
	 * 
	 * @param v
	 */
	public void pressedCalibrate(final View v) {

		if (!_mySurfaceView.isCalibrated()) {

			_calibrateButton.setBackgroundResource(R.drawable.yellow_button);
			_mySurfaceView.calibrate();
		}
	}

	public void pressedReset(final View v) {

		if (_mySurfaceView.isCalibrated()) {

			_calibrateButton.setBackgroundResource(R.drawable.red_button);
			_mySurfaceView.reset();
		}
	}

	public void onShowMiddlePoint(final View view) {
		// Is the toggle on?
		boolean on = ((Switch) view).isChecked();
		_mySurfaceView.showMiddleEye(on);
	}

	public void onShowEyePoints(final View view) {
		// Is the toggle on?
		boolean on = ((Switch) view).isChecked();
		_mySurfaceView.showEyePoints(on);
	}

	/**
	 * Update the UI values.
	 *
	 */
	public void updateUI(final MeasurementStepMessage message) {
         Log.e("aaa","-------_currentDistanceView"+message.getDistToFace());
		_currentDistanceView.setText(_decimalFormater.format(message
				.getDistToFace()) + " cm");

		float fontRatio = message.getDistToFace() / 29.7f;

		_currentDistanceView.setTextSize(fontRatio * 20);

	}

	private void resetCam() {
		_mySurfaceView.reset();

		if(_cam!=null){
			_cam.stopPreview();
			_cam.setPreviewCallback(null);
			_cam.release();
		}
	}

	@Override
	public void onMessage(final int messageID, final Object message) {

		switch (messageID) {

		case MessageHUB.MEASUREMENT_STEP:
			updateUI((MeasurementStepMessage) message);
			break;

		case MessageHUB.DONE_CALIBRATION:

			_calibrateButton.setBackgroundResource(R.drawable.green_button);

			break;
		default:
			break;
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	}
}