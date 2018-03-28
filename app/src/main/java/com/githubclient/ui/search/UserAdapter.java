package com.githubclient.ui.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.githubclient.databinding.UserItemLayoutBinding;
import com.githubclient.model.User;

import java.util.ArrayList;

/**
 * Created by 1 on 3/27/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private ArrayList<User> users;
    private Context context;
    private final UserAdapterOnItemClickHandler clickHandler;

    public interface UserAdapterOnItemClickHandler {
        void onItemClick(UserViewHolder vh, User user);
    }

    private interface UserAdapterInternalOnItemClickHandler {
        void onItemClick(UserViewHolder vh);
    }

    private final UserAdapterInternalOnItemClickHandler internalHandler = new UserAdapterInternalOnItemClickHandler() {
        @Override
        public void onItemClick(UserViewHolder vh) {
            int adapterPosition = vh.getAdapterPosition();
            notifyItemChanged(adapterPosition);
            clickHandler.onItemClick(vh, users.get(adapterPosition));
        }
    };

    public UserAdapter(@NonNull Context context, @NonNull UserAdapterOnItemClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        this.context = context;
    }

    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UserItemLayoutBinding binding = UserItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new UserViewHolder(binding, internalHandler);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, final int position) {
        holder.bind(users.get(position));
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final UserItemLayoutBinding binding;
        UserAdapterInternalOnItemClickHandler listener;

        private UserViewHolder(UserItemLayoutBinding binding, UserAdapterInternalOnItemClickHandler internalHandler) {
            super(binding.getRoot());
            this.listener = internalHandler;
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
        }

        public void bind(User user) {
            binding.setUser(user);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(this);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (users != null) {
            return users.size();
        } else {
            return 0;
        }
    }

    public void updateUsers(ArrayList<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }
}
