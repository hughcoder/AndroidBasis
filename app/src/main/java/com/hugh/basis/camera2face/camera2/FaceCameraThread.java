package com.hugh.basis.camera2face.camera2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.util.Log;
import android.util.Size;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class FaceCameraThread extends Thread {

	public static final String FACEDETECTIONTHREAD_TAG = "FaceDetectionThread_Tag";

	private Face _currentFace;
	private final byte[] _data;
	private final Size _previewSize;
	private Bitmap _currentFrame;
	private boolean mIsVertical;

	public FaceCameraThread(final byte[] data, final Size previewSize,boolean isVertical) {
		_data = data;
		_previewSize = previewSize;
		mIsVertical = isVertical;
	}

	public Face getCurrentFace() {
		return _currentFace;
	}

	public Bitmap getCurrentFrame() {
		return _currentFrame;
	}

	/**
	 * bla bla bla
	 */
	@Override
	public void run() {

		long t = System.currentTimeMillis();

		YuvImage yuvimage = new YuvImage(_data, ImageFormat.NV21,
				_previewSize.getWidth(), _previewSize.getHeight(), null);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		if (!yuvimage.compressToJpeg(new Rect(0, 0, _previewSize.getWidth(),
				_previewSize.getHeight()), 100, baos)) {

			Log.e("Camera", "compressToJpeg failed");

		}

		Log.i("Timing", "Compression finished: "
				+ (System.currentTimeMillis() - t));
		t = System.currentTimeMillis();

		BitmapFactory.Options bfo = new BitmapFactory.Options();
		bfo.inPreferredConfig = Bitmap.Config.RGB_565;

		_currentFrame = BitmapFactory.decodeStream(new ByteArrayInputStream(
				baos.toByteArray()), null, bfo);

		Log.i("Timing", "Decode Finished: " + (System.currentTimeMillis() - t));
		t = System.currentTimeMillis();

		// Rotate the so it siuts our portrait mode
		Matrix matrix = new Matrix();
		if(mIsVertical){
			matrix.postRotate(90);
			matrix.preScale(-1, 1);  //Android内置人脸识别的图像必须是头在上,所以要做旋转变换
			// We rotate the same Bitmap
			_currentFrame = Bitmap.createBitmap(_currentFrame, 0, 0,
					_previewSize.getWidth(), _previewSize.getHeight(), matrix, false);
		}
		Log.e("run","previewSize.width"+_previewSize.getWidth()+"_previewSize.height"+_previewSize.getHeight());
		Log.i("Timing",
				"Rotate, Create finished: " + (System.currentTimeMillis() - t));
		t = System.currentTimeMillis();

		if (_currentFrame == null) {
			Log.e(FACEDETECTIONTHREAD_TAG, "Could not decode Image");
			return;
		}

//		Log.e("aaa","_currentFrame.getWidth()"+_currentFrame.getWidth());
//		Log.e("aaa","_currentFrame.getHeight(),"+_currentFrame.getHeight());
		FaceDetector d = new FaceDetector(_currentFrame.getWidth(),
				_currentFrame.getHeight(), 1);

		Face[] faces = new Face[1];
		d.findFaces(_currentFrame, faces);

		Log.i("Timing",
				"FaceDetection finished: " + (System.currentTimeMillis() - t));
		t = System.currentTimeMillis();

		_currentFace = faces[0];
		Log.d(FACEDETECTIONTHREAD_TAG, "Found: " + faces[0] + " Faces");
		if(faces[0]!=null){
			Log.e("aaa","faces[0].eyesDistance()"+faces[0].eyesDistance());
		}
	}
}
