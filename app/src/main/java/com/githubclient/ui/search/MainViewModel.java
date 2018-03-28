package com.githubclient.ui.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.githubclient.Application;
import com.githubclient.model.User;
import com.githubclient.repository.RepoRepository;
import com.githubclient.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by 1 on 3/27/2018.
 */

public class MainViewModel extends ViewModel{
    @Inject
    UserRepository userRepository;

    private Disposable disposable;

    private final MutableLiveData<List<User>> users = new MutableLiveData<>();

    private int currentPage = 1;

    private String criteria;


    public MainViewModel(){
        Application.getAppComponent().inject(this);
    }

    public LiveData<List<User>> searchUsers(String criteria){
        loadUsers(criteria);
        return users;
    }

    public LiveData<List<User>> getUsers(){
        return users;
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

    private void loadUsers(String criteria){
        this.criteria = criteria;
        if (disposable!=null){
            disposable.dispose();
        }
        disposable = userRepository.getUsers(criteria, currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users::setValue
                        ,error->{
                            error.getCause();
                        });
    }

    public void loadNextPage() {
//        if (currentPage<userRepository.getPagesAmount()) {
//            currentPage++;
//            loadUsers(criteria);
//        }
    }

    public void setSelectedUser(User user){
        userRepository.setSelectedUser(user);
    }

    public User getSelectedUser() {
        return userRepository.getSelectedUser();
    }
}
