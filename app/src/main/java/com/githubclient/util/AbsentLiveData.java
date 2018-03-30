package com.githubclient.util;

import android.arch.lifecycle.LiveData;

/**
 * Created by 1 on 3/30/2018.
 */

public class AbsentLiveData extends LiveData {
    private AbsentLiveData() {
        postValue(null);
    }
    public static <T> AbsentLiveData create() {
        return new AbsentLiveData();
    }
}
