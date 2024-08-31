package com.example.restful.datebase;

public class CacheStatusInfo {

    protected boolean ListNewsLoaded;
    protected boolean ListChatsLoaded;

    public CacheStatusInfo() {
        ListNewsLoaded = false;
    }

    public boolean isListNewsLoaded() {
        return ListNewsLoaded;
    }

    public boolean isListChatsLoaded() {
        return ListChatsLoaded;
    }
}
