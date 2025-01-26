package com.example.deem.utils;

import android.annotation.SuppressLint;
import android.net.Uri;

import androidx.media3.common.MediaItem;
import androidx.media3.datasource.DataSource;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;

import java.io.File;

public class VideoUtil {

    private static VideoUtil instance;

    private VideoUtil() {
    }

    public static VideoUtil getInstance() {
        if (instance == null)
            instance = new VideoUtil();

        return instance;
    }

    @SuppressLint("UnsafeOptInUsageError")
    public ProgressiveMediaSource getMediaSource(byte[] bytesVideo) {
        DataSource.Factory byteArrayDataSourceFactory = () -> new ByteArrayDataSource(bytesVideo);

        ProgressiveMediaSource.Factory mediaSourceFactory = new ProgressiveMediaSource.Factory(byteArrayDataSourceFactory);
        MediaItem mediaItem = MediaItem.fromUri(Uri.EMPTY);
        ProgressiveMediaSource mediaSource = mediaSourceFactory.createMediaSource(mediaItem);

        return mediaSource;
    }

    public MediaItem getMediaItem(File file) {
        return MediaItem.fromUri(Uri.fromFile(file));
    }
}
