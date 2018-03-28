package com.githubclient.ui.details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.githubclient.databinding.RepoItemLayoutBinding;
import com.githubclient.model.Repo;

import java.util.ArrayList;

/**
 * Created by 1 on 3/27/2018.
 */

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

    private ArrayList<Repo> repositories;
    private Context context;

    public RepoAdapter(Context context) {
        this.context = context;
    }

    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RepoItemLayoutBinding binding = RepoItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new RepoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, final int position) {
        holder.bind(repositories.get(position));
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder {

        private final RepoItemLayoutBinding binding;

        private RepoViewHolder(RepoItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Repo repository) {
            binding.setRepo(repository);
            binding.executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        if (repositories != null) {
            return repositories.size();
        } else {
            return 0;
        }
    }

    public void updateRepos(ArrayList<Repo> repositories) {
        this.repositories = repositories;
        notifyDataSetChanged();
    }

}
