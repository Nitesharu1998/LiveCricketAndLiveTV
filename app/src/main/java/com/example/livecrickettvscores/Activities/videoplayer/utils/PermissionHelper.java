package com.example.livecrickettvscores.Activities.videoplayer.utils;

import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.livecrickettvscores.Activities.videoplayer.VideoPlayerActivity;


public class PermissionHelper {
    public static boolean checkPermission(String permission, int requestCode, VideoPlayerActivity musicPlayerActivity) {
        if (ContextCompat.checkSelfPermission(musicPlayerActivity, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(musicPlayerActivity, new String[] { permission }, requestCode);
            return false;
        } else {
            return true;
        }
    }
}
