package com.githubclient.ui.details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.githubclient.Application;
import com.githubclient.model.Repo;
import com.githubclient.model.User;
import com.githubclient.repository.RepoRepository;
import com.githubclient.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by 1 on 3/28/2018.
 */
@Singleton
public class DetailsViewModel extends ViewModel {
    @Inject
    UserRepository userRepository;
    @Inject
    RepoRepository repoRepository;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<List<Repo>> repos = new MutableLiveData<>();


    public DetailsViewModel(){
        Application.getAppComponent().inject(this);
    }


    public LiveData<List<Repo>> getRepos(){
        loadRepos(userRepository.getSelectedUser().getLogin());
        return repos;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }

    private void loadRepos(String login){
        disposables.add(repoRepository.getRepositories(login)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repos::setValue
                        ,error->{

                        })
        );
    }

    public User getSelectedUser() {
        return userRepository.getSelectedUser();
    }
}
