package com.innofied.innofiedassignment.UI.UserList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innofied.innofiedassignment.Model.UserObject;
import com.innofied.innofiedassignment.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mohit on 9/23/2019.
 */

public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<UserObject> userList;
    private Context context;

    private boolean isLoadingStarted = false;

    UserListAdapter(Context context) {
        this.context = context;
        userList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View view = inflater.inflate(R.layout.bottom_progress_bar, parent, false);
                viewHolder = new LoadingViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.user_cell, parent, false);
        viewHolder = new UserViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        UserObject userObject = userList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                UserViewHolder userViewHolder = (UserViewHolder) holder;
                userViewHolder.txtEmailId.setText(userObject.getEmailId());
                userViewHolder.txtName.setText(userObject.getFirstName() + " " + userObject.getLastName());
                Picasso.with(context)
                        .load(userObject.getImgPath())
                        .placeholder(R.drawable.app_logo)
                        .error(R.drawable.error_image)
                        .into(userViewHolder.imgUser);
                break;
            case LOADING:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return userList == null ? 0 : userList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == userList.size() - 1 && isLoadingStarted) ? LOADING : ITEM;
    }

    private void add(UserObject r) {
        userList.add(r);
        notifyItemInserted(userList.size() - 1);
    }

    void addAll(List<UserObject> moveResults) {
        for (UserObject result : moveResults) {
            add(result);
        }
    }

    void addLoadingFooter() {
        isLoadingStarted = true;
        add(new UserObject());
    }

    void removeLoadingFooter() {
        isLoadingStarted = false;

        int position = userList.size() - 1;
        UserObject result = getItem(position);

        if (result != null) {
            userList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private UserObject getItem(int position) {
        return userList.get(position);
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgUser)
        CircleImageView imgUser;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtEmailId)
        TextView txtEmailId;

        UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    protected class LoadingViewHolder extends RecyclerView.ViewHolder {
        LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
