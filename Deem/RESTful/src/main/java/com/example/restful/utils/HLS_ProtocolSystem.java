package com.example.restful.utils;

import android.annotation.SuppressLint;

import androidx.media3.common.MediaItem;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.hls.HlsMediaSource;
import androidx.media3.ui.PlayerView;

import com.example.restful.api.APIManager;

import java.util.HashMap;
import java.util.Map;

public class HLS_ProtocolSystem {

    private static HLS_ProtocolSystem instance;

    private HLS_ProtocolSystem() {

    }

    public static HLS_ProtocolSystem getInstance() {
        if (instance == null)
            instance = new HLS_ProtocolSystem();
        return instance;
    }

    @SuppressLint("UnsafeOptInUsageError")
    public HlsMediaSource createHLSMediaSource(String url) {
        //настройка http протокола
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + APIManager.getManager().getJwtKey());

        DefaultHttpDataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory()
                .setDefaultRequestProperties(headers);
        HlsMediaSource.Factory hlsMediaSourceFactory = new HlsMediaSource.Factory(dataSourceFactory);

        // Создание HLS-источника
        /*HlsMediaSource.Factory hlsMediaSourceFactory = new HlsMediaSource.Factory(
              new DefaultHttpDataSource.Factory()
        );*/

        // Установка HLS медиа
        MediaItem mediaItem = MediaItem.fromUri(url);
        return hlsMediaSourceFactory.createMediaSource(mediaItem);
    }
}
