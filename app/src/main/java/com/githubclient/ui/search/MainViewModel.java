package com.githubclient.ui.search;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.githubclient.Application;
import com.githubclient.data.NetworkState;
import com.githubclient.model.User;
import com.githubclient.repository.UserRepository;
import com.githubclient.util.AbsentLiveData;

import javax.inject.Inject;

/**
 * Created by 1 on 3/27/2018.
 */
public class MainViewModel extends ViewModel {

    LiveData<PagedList<User>> userList;

    @Inject
    UserRepository userRepository;


    public MainViewModel() {
        Application.getAppComponent().inject(this);
        userList = AbsentLiveData.create();
    }

    public void startSearch(LifecycleOwner lifecycleOwner, String query) {
        if (userList!=null) {
            userList.removeObservers(lifecycleOwner);
        }
        userList = userRepository.startSearch(query);
    }

    public LiveData<NetworkState> getNetworkState() {
        return userRepository.getNetworkState();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        userRepository.dispose();
    }

    public void setSelectedUser(User user) {
        userRepository.setSelectedUser(user);
    }

}
