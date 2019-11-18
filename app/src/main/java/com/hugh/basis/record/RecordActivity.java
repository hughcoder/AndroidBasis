package com.hugh.basis.record;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioRecord;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hugh.basis.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by chenyw on 2019-09-30.
 */
public class RecordActivity extends AppCompatActivity {
    private Button mBtnCompose;
    private Button mBtnRecord;
    private Button mBtnDelete;
    private Button mBtnPlayOld;
    private Button mBtnPlayNew;
    private TextView mTvHint ;
    private Button mBtnStop;

    private boolean recording;
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private int mBufferSizeInBytes;

    // 声明 AudioRecord 对象
    private AudioRecord mAudioRecord = null;
    private String StorageDirectoryPath;
    private String tempVoicePcmUrl;
    private String musicFileUrl;
    private String decodeFileUrl;
    private String composeVoiceUrl;
    private long recordStartTime;

    private Recorder mRecorder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
//        //初始化数据，计算最小缓冲区
//        mBufferSizeInBytes = AudioRecord.getMinBufferSize(mSampleRateInHz, mChannalConfig, mAudioFormat);
//        //创建AudioRecorder对象
//        mAudioRecord = new AudioRecord(mAudioSource, mSampleRateInHz, mChannalConfig, mAudioFormat, mBufferSizeInBytes);
        mBtnRecord = findViewById(R.id.btn_record);
        mBtnCompose = findViewById(R.id.btn_compound);
        mBtnDelete = findViewById(R.id.btn_delete);
        mBtnPlayOld = findViewById(R.id.btn_play_old);
        mBtnPlayNew = findViewById(R.id.btn_play_new);
        mBtnStop = findViewById(R.id.btn_stop);
        initFile();
        initData();
//        mRecorder = new Recorder();

        mBtnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(RecordActivity.this,
                        Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RecordActivity.this,
                            new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
                } else {
                    startRecord();
                }
            }
        });

        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecordVoice();
            }
        });

    }

    private void initFile(){
        if (!FileFunction.IsExitsSdcard()) {
            StorageDirectoryPath= RecordActivity.this.getFilesDir().getAbsolutePath();
        } else {
            StorageDirectoryPath =
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.audio.chenyw/";
        }

//        Variable.ErrorFilePath = Variable.StorageDirectoryPath + "error.txt";

        CreateDirectory(StorageDirectoryPath);

        tempVoicePcmUrl = StorageDirectoryPath + "tempVoice.pcm";
        musicFileUrl = StorageDirectoryPath + "musicFile.mp3";
        decodeFileUrl = StorageDirectoryPath + "decodeFile.pcm";
        composeVoiceUrl = StorageDirectoryPath + "composeVoice.mp3";
    }

    private void initData(){
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_RECORD_AUDIO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startRecord();
            } else {
                Toast.makeText(this, "没有权限", Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void startRecord() {
        recording = startRecordVoice(tempVoicePcmUrl);
        if(recording){
            mBtnRecord.setText("点击结束录音");
        }else{
            Log.e("aaa","出现未知错误");
        }
    }

    private boolean startRecordVoice(String recordFileUrl) {
        if (CommonFunction.isEmpty(recordFileUrl)) {
            CommonFunction.showToast("无法录制语音，请检查您的手机存储", "VoiceRecorder");
            return false;
        }

        File recordFile = new File(recordFileUrl);
        recordStartTime = System.currentTimeMillis();

        if (!recordFile.exists()) {
            try {
                recordFile.createNewFile();
            } catch (IOException e) {
                Log.e("建立语音文件异常" + ":打印error数据异常", e.toString());
                CommonFunction.showToast("建立语音文件异常", "VoiceRecorder");
                return false;
            }
        }

        if(mRecorder == null){
            mRecorder = new Recorder();
        }
        return mRecorder.startRecordVoice(recordFileUrl);
    }

    public void stopRecordVoice() {
        if (recording) {
            boolean recordVoiceSuccess = mRecorder.stopRecordVoice();
            long recordDuration = System.currentTimeMillis() - recordStartTime;

            recording = false;

            if (recordDuration < 1000) {
                recordVoiceSuccess = false;
            }

//            if (!recordVoiceSuccess) {
//                CommonFunction.showToast("录音太短", "VoiceRecorder");
//
//                if (voiceRecorderInterface != null) {
//                    voiceRecorderInterface.recordVoiceFail();
//                }
//
//                FileFunction.DeleteFile(recordFileUrl);
//                return;
//            }
//
//            if (voiceRecorderInterface != null) {
//                voiceRecorderInterface.recordVoiceFinish();
//            }
        }
    }

    private void CreateDirectory(String path) {
        File dir = new File(path);

        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

}
