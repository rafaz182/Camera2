package com.smewise.camera2.utils;

import android.os.Process;
import android.util.Log;

import com.smewise.camera2.Config;
import com.smewise.camera2.manager.CameraController;

import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by wenzhe on 9/18/17.
 */

public class CameraThread extends Thread {
    private static final String TAG = Config.TAG_PREFIX + "CameraThread";
    public static final String OPEN_CAMERA = "open camera";
    public static final String CLOSE_CAMERA = "close camera";
    private volatile boolean mActive = true;
    private LinkedBlockingQueue<CameraJob> mQueue;

    public CameraThread() {
        mQueue = new LinkedBlockingQueue<>();
    }

    public void addCameraJob(CameraJob job) {
        if (!mQueue.offer(job)) {
            Log.e(TAG, "failed to add job");
        }
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_FOREGROUND);
        while (mActive) {
            if (mQueue.isEmpty()) {
                if (mActive) {
                    waitWithoutInterrupt(this);
                } else {
                    break;
                }
            } else {
                CameraJob job = mQueue.poll();
                if (job != null) {
                    if (OPEN_CAMERA.equals(job.jobType)) {
                        job.jobCallback.openCamera();
                    } else {
                        job.jobCallback.closeCamera();
                    }
                }
            }
        }
        // loop end
    }

    public synchronized void notifyJob() {
        notifyAll();
    }

    public synchronized void terminate() {
        mActive = false;
        mQueue.clear();
        notifyAll();
    }

    private synchronized void waitWithoutInterrupt(Object object) {
        try {
            object.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class CameraJob {
        public String jobType;
        public CameraController jobCallback;
    }

}
