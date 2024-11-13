package com.example.beepbeep;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.AsyncTask;

public class Worker  extends AsyncTask<Void, Void, Void> {
    double vol;
    Context context;
    int fs;
    int length;
    String fname;
    int delay;
    public Worker(Context context, double vol, int length, int fs, String fname, int delay) {
        this.vol=vol;
        this.length = length;
        this.context = context;
        this.fs = fs;
        this.fname = fname;
        this.delay = delay;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        Constants.startButton.setEnabled(true);
//        Constants.stopButton.setEnabled(false);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        short[] chirp = Chirp.generateChirpSpeaker(2000, 6000, 0.05, Constants.fs, 0);

        OfflineRecorder rec = new OfflineRecorder(MediaRecorder.AudioSource.DEFAULT,fs,fs*length, context, fname);
        rec.start();

        try {
            Thread.sleep(delay*1000);
            AudioSpeaker speaker = new AudioSpeaker(context, chirp, fs);
            speaker.play(vol,0);
            int total_time=10;
            int remaining_time = total_time-delay;
            Thread.sleep(remaining_time*1000);
            speaker.track1.stop();
        }
        catch(Exception e){
        }
        rec.halt();

        return null;
    }
}