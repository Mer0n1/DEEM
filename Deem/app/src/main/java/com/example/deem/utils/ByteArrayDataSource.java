package com.example.deem.utils;

import android.net.Uri;

import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DataSpec;
import androidx.media3.datasource.TransferListener;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ByteArrayDataSource implements DataSource {

    private final byte[] data;
    private ByteArrayInputStream inputStream;

    public ByteArrayDataSource(byte[] data) {
        this.data = data;
    }

    @Override
    public void addTransferListener(TransferListener transferListener) {}

    @Override
    public long open(DataSpec dataSpec) throws IOException {
        inputStream = new ByteArrayInputStream(data);
        return data.length; // Total size
    }

    @Override
    public int read(byte[] buffer, int offset, int readLength) throws IOException {
        return inputStream.read(buffer, offset, readLength);
    }

    @Override
    public Uri getUri() {
        return null; // No actual URI since it's byte data
    }

    @Override
    public void close() throws IOException {
        if (inputStream != null) {
            inputStream.close();
            inputStream = null;
        }
    }
}
