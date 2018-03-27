package com.githubclient.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.githubclient.databinding.UserItemLayoutBinding;
import com.githubclient.model.User;

import java.util.ArrayList;

/**
 * Created by 1 on 3/27/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private ArrayList<User> users;
    private IOnUserClickListener listener;
    private Context context;


    public class UserViewHolder extends RecyclerView.ViewHolder {

        private final UserItemLayoutBinding binding;

        private UserViewHolder(UserItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(v -> {
                if (listener!=null){
                    listener.userSelected(this.binding.getUser());
                }
            });
        }

        public void bind(User user) {
            binding.setUser(user);
            binding.executePendingBindings();
        }
    }

    public UserAdapter(Context context, IOnUserClickListener listener) {
        this.listener = listener;
        this.context = context;
    }

    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UserItemLayoutBinding binding = UserItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, final int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        if (users != null) {
            return users.size();
        } else {
            return 0;
        }
    }

    public void updateUsers(ArrayList<User> users){
        this.users = users;
        notifyDataSetChanged();
    }

    public interface IOnUserClickListener {
        void userSelected(User user);
    }
}
