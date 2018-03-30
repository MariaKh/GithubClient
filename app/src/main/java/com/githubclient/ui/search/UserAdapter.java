package com.githubclient.ui.search;


import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.githubclient.databinding.UserItemLayoutBinding;
import com.githubclient.model.User;

import java.util.Objects;

/**
 * Created by 1 on 3/27/2018.
 */
public class UserAdapter extends PagedListAdapter<User, RecyclerView.ViewHolder> {

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
            clickHandler.onItemClick(vh, getCurrentList().get(adapterPosition));
        }
    };

    public UserAdapter(Context context, @NonNull UserAdapterOnItemClickHandler clickHandler) {
        super(UserDiffCallback);
        this.context = context;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserItemLayoutBinding binding = UserItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new UserViewHolder(binding, internalHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((UserViewHolder) holder).bind(getItem(position));
    }


    private static DiffUtil.ItemCallback<User> UserDiffCallback = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };

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

}
