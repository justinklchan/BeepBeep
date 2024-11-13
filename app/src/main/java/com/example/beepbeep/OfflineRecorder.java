package com.example.beepbeep;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;

public class OfflineRecorder extends Thread {

    AudioRecord rec;
    int minbuffersize;
    boolean recording;
    int count;
    Context context;
    String filename;
    int fs;

    public OfflineRecorder(int microphone, int fs, int bufferLen, Context context, String filename) {
        this.context = context;
        this.filename = filename;
        this.fs = fs;

        minbuffersize = AudioRecord.getMinBufferSize(
                fs,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);

        rec = new AudioRecord(
                microphone,
                fs,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                minbuffersize);
        Constants.temp = new short[minbuffersize];
        Constants.samples = new short[bufferLen];
    }

    public void run() {
        int bytesread;

        rec.startRecording();
        recording = true;
        while (recording) {
            bytesread = rec.read(Constants.temp, 0, minbuffersize);
            for (int i = 0; i < bytesread; i++) {
                if (count >= Constants.samples.length) {
                    recording = false;
                    FileOperations.writeToDisk(context, filename);
                    break;
                } else {
                    Constants.samples[count] = Constants.temp[i];
                    count += 1;
                }
            }
        }
    }

    public void halt() {
        if (rec.getState() == AudioRecord.STATE_INITIALIZED ||
                rec.getState() == AudioRecord.RECORDSTATE_RECORDING) {
            rec.stop();
        }
        rec.release();
        recording = false;
        FileOperations.writeToDisk(context, filename);
    }
}
