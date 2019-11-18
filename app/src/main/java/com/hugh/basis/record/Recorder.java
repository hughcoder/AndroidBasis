package com.hugh.basis.record;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;


import java.io.BufferedOutputStream;
import java.io.IOException;

public class Recorder {
    private short[] audioRecordBuffer;
    public static boolean isBigEnding = false;
    private int audioRecordBufferSize;
    private int realSampleDuration;
    private int realSampleNumberInOneDuration;
    private final static int toTransformLocationNumber = 3;
    private final static int receiveSuperEaCycleNumber = 10;
    //指定音频源 这个和MediaRecorder是相同的 MediaRecorder.AudioSource.MIC指的是麦克风
    private static final int mAudioSource = MediaRecorder.AudioSource.MIC;
    //指定采样率 （MediaRecoder 的采样率通常是8000Hz AAC的通常是44100Hz。 设置采样率为44100，目前为常用的采样率，官方文档表示这个值可以兼容所有的设置）
    private static final int mSampleRateInHz = 44100;
    //指定捕获音频的声道数目。在AudioFormat类中指定用于此的常量，单声道
    private static final int mChannalConfig = AudioFormat.CHANNEL_IN_MONO;
    //指定音频量化位数 ,在AudioFormaat类中指定了以下各种可能的常量。通常我们选择ENCODING_PCM_16BIT和ENCODING_PCM_8BIT PCM代表的是脉冲编码调制，
    // 它实际上是原始音频样本。因此可以设置每个样本的分辨率为16位或者8位，16位将占用更多的空间和处理能力,表示的音频也更加接近真实。
    private static final int mAudioFormat = AudioFormat.ENCODING_PCM_16BIT;

    private final static int sampleDuration = 100;

    private static final int recordSleepDuration = 500;

    //自定义 每160帧作为一个周期，通知一下需要进行编码
    private static final int FRAME_COUNT = 160;


    private double amplitude;

    private AudioRecord audioRecord = null;

    private RecordThread recordThread;

    public Recorder() {
        init();
    }

    private void init() {
        initAudioRecord();

        recordThread = new RecordThread();

        CommonThreadPool.getThreadPool().addCachedTask(recordThread);
    }

    private void initAudioRecord() {
        int audioRecordMinBufferSize = AudioRecord
                .getMinBufferSize(mSampleRateInHz, AudioFormat.CHANNEL_IN_MONO,
                        mAudioFormat);

        audioRecordBufferSize =
                mSampleRateInHz * mAudioFormat / (1000 / sampleDuration);

        if (audioRecordMinBufferSize > audioRecordBufferSize) {
            audioRecordBufferSize = audioRecordMinBufferSize;
        }

        /* Get number of samples. Calculate the buffer size
         * (round up to the factor of given frame size)
		 * 使能被整除，方便下面的周期性通知
		 * */
        int bytesPerFrame =mAudioFormat;
        int frameSize = audioRecordBufferSize / bytesPerFrame;

        if (frameSize % FRAME_COUNT != 0) {
            frameSize += (FRAME_COUNT - frameSize % FRAME_COUNT);
            audioRecordBufferSize = frameSize * bytesPerFrame;
        }

        audioRecordBuffer = new short[audioRecordBufferSize];

        double sampleNumberInOneMicrosecond = (double) mSampleRateInHz/ 1000;

        realSampleDuration = audioRecordBufferSize * 1000 /
                (mSampleRateInHz * mAudioFormat);

        realSampleNumberInOneDuration = (int) (sampleNumberInOneMicrosecond * realSampleDuration);

        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, mSampleRateInHz,
                AudioFormat.CHANNEL_IN_MONO,mAudioFormat, audioRecordBufferSize);
    }

    public void release() {
        if (recordThread != null) {
            recordThread.release();
        }
    }

    /**
     * Start recording. Create an encoding thread. Start record from this
     * thread.
     */
    public boolean startRecordVoice(String recordFileUrl) {
        try {
            recordThread.startRecordVoice(recordFileUrl);
            return true;
        } catch (Exception e) {
           Log.e("startRecordVoice",e.toString());
//            CommonFunction.showToast("初始化录音失败", "MP3Recorder");
        }

        return false;
    }

    public boolean stopRecordVoice() {
        if (recordThread != null) {
            recordThread.stopRecordVoice();
        }

        return true;
    }

    /**
     * 此计算方法来自samsung开发范例
     *
     * @param buffer   buffer
     * @param readSize readSize
     */
    private void calculateRealVolume(short[] buffer, int readSize) {
        int sum = 0;

        for (int index = 0; index < readSize; index++) {
            // 这里没有做运算的优化，为了更加清晰的展示代码
            sum += Math.abs(buffer[index]);
        }

        if (readSize > 0) {
            amplitude = sum / readSize;
        }
    }

    public int getVolume() {
        int volume = (int) (Math.sqrt(amplitude)) * 9 / 60;
        return volume;
    }

    private class RecordThread implements Runnable {
        private boolean running;
        private boolean recordVoice;

        private String recordFileUrl;

        public RecordThread() {
            running = true;
        }

        public void startRecordVoice(String recordFileUrl) throws IOException {
            if (!running) {
                return;
            }

            this.recordFileUrl = recordFileUrl;

            recordVoice = true;
        }

        public void stopRecordVoice() {
            recordVoice = false;
        }

        public void release() {
            running = false;
            recordVoice = false;
        }

        private void NoRecordPermission() {
            stopRecordVoice();
        }

        @Override
        public void run() {
            while (running) {
                if (recordVoice) {
                    audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                            mSampleRateInHz, AudioFormat.CHANNEL_IN_MONO,
                           mAudioFormat, audioRecordBufferSize);

                    try {
                        audioRecord.startRecording();
                    } catch (Exception e) {
                        NoRecordPermission();
                        continue;
                    }

                    BufferedOutputStream bufferedOutputStream = FileFunction
                            .GetBufferedOutputStreamFromFile(recordFileUrl);

                    while (recordVoice) {
                        int audioRecordReadDataSize =
                                audioRecord.read(audioRecordBuffer, 0, audioRecordBufferSize);

                        if (audioRecordReadDataSize > 0) {
                            calculateRealVolume(audioRecordBuffer, audioRecordReadDataSize);
                            if (bufferedOutputStream != null) {
                                try {
                                    byte[] outputByteArray = CommonFunction
                                            .GetByteBuffer(audioRecordBuffer,
                                                    audioRecordReadDataSize, false);
                                    bufferedOutputStream.write(outputByteArray);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            NoRecordPermission();
                            continue;
                        }
                    }

                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                        } catch (Exception e) {
                            Log.e("关闭录音输出数据流异常", e+"");
                        }
                    }

                    audioRecord.stop();
                    audioRecord.release();
                    audioRecord = null;
                }

                try {
                    Thread.sleep(recordSleepDuration);
                } catch (Exception e) {
                    Log.e("录制语音线程异常", e+"");
                }
            }
        }
    }
}