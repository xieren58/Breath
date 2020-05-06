package com.zkp.breath.audiorecord;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.blankj.utilcode.util.PathUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 获取权限
 * 初始化获取每一帧流的Size
 * 初始化音频录制AudioRecord
 * 开始录制与保存录制音频文件
 * 停止录制
 * 给音频文件添加头部信息,并且转换格式成wav
 * 释放AudioRecord,录制流程完毕
 */
public class AudioRecordUtils {


    private Integer mRecordBufferSize;
    private AudioRecord mAudioRecord;
    String externalAppCachePath = PathUtils.getExternalAppCachePath();
    File pcmFile = new File(externalAppCachePath, "audioRecord.pcm");
    File wavFile = new File(externalAppCachePath, "audioRecord_handler.wav");

    //获取每一帧的字节流大小
    private void initMinBufferSize() {
        mRecordBufferSize = AudioRecord.getMinBufferSize(
                44100
                , AudioFormat.CHANNEL_IN_MONO
                , AudioFormat.ENCODING_PCM_16BIT);

    }

    // 初始化音频录制AudioRecord，参数2-参数4要与getMinBufferSize（）方法中保持一致。
    private void initAudioRecord() {
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC
                , 44100
                , AudioFormat.CHANNEL_IN_MONO
                , AudioFormat.ENCODING_PCM_16BIT
                , mRecordBufferSize);
    }


    private boolean mWhetherRecord;

    private void startRecord() {
        mWhetherRecord = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                mAudioRecord.startRecording();//开始录制
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(pcmFile);
                    byte[] bytes = new byte[mRecordBufferSize];
                    while (mWhetherRecord) {
                        mAudioRecord.read(bytes, 0, bytes.length);//读取流
                        fileOutputStream.write(bytes);
                        fileOutputStream.flush();

                    }
                    Log.e("AudioRecordUtils", "run: 暂停录制");
                    mAudioRecord.stop();//停止录制
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    addHeadData();//添加音频头部信息并且转成wav格式
//                    mAudioRecord.release();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    mAudioRecord.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void addHeadData() {
        PcmToWavUtil pcmToWavUtil = new PcmToWavUtil(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        pcmToWavUtil.pcmToWav(pcmFile.toString(), wavFile.toString());
    }

    private void release() {
        mAudioRecord.release();
    }

}
