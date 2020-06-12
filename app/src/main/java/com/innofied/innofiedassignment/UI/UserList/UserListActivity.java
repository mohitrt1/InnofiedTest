package com.innofied.innofiedassignment.UI.UserList;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.innofied.innofiedassignment.Business.network.RetrofitService;
import com.innofied.innofiedassignment.Business.network.UserListAPI;
import com.innofied.innofiedassignment.Model.UserListData;
import com.innofied.innofiedassignment.Model.UserObject;
import com.innofied.innofiedassignment.R;
import com.innofied.innofiedassignment.Utils.CheckNetwork;
import com.innofied.innofiedassignment.Utils.PaginationScrollListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListActivity extends AppCompatActivity {

    private static final int API_DELAY = 1000;

    @BindView(R.id.recUsers)
    RecyclerView recUserList;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.txtError)
    TextView txtError;

    private Context context;
    private RetrofitService service;
    private int currentPageIndex =1;
    private int count = 5;
    private int totalPages=0;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    private UserListAdapter userListAdapter;
    private LinearLayoutManager linearLayoutManager;

    private MenuItem menuInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list_activity);
        ButterKnife.bind(this);
        context=this;

        menuInternet = null;
        setData();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPageIndex = 1;
                totalPages = 0;
                isLastPage= false;
                isLoading=false;
                setData();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void setData(){
        userListAdapter = new UserListAdapter(context);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recUserList.setLayoutManager(linearLayoutManager);
        recUserList.setAdapter(userListAdapter);

        recUserList.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPageIndex += 1;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (CheckNetwork.isInternetAvailable(context))
                            loadNextPage();
                        else
                            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_LONG).show();
                    }
                }, API_DELAY);
            }

            @Override
            public int getTotalPageCount() {
                return totalPages;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        service = UserListAPI.getClient(context).create(RetrofitService.class);
        if (CheckNetwork.isInternetAvailable(context)) {
            loadFirstPage();
            txtError.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            txtError.setVisibility(View.VISIBLE);
        }
    }

    private Call<UserListData> callUserListAPI() {
        return service.getUserList(currentPageIndex, count);
    }

    private List<UserObject> fetchUserList(Response<UserListData> response) {
        UserListData userListData = response.body();
        totalPages = userListData.getTotalPages();
        return userListData.getUserList();
    }

    private void loadFirstPage() {
        callUserListAPI().enqueue(new Callback<UserListData>() {
            @Override
            public void onResponse(Call <UserListData> call, Response <UserListData> response) {

                List<UserObject> userList = fetchUserList(response);
                progressBar.setVisibility(View.GONE);
                userListAdapter.addAll(userList);

                if (currentPageIndex <= totalPages) userListAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call <UserListData> call, Throwable throwable) {
                // handle error
                throwable.printStackTrace();
                Toast.makeText(context, R.string.error_while_loading_data, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadNextPage() {
        callUserListAPI().enqueue(new Callback<UserListData>() {
            @Override
            public void onResponse(Call<UserListData> call, Response<UserListData> response) {
                userListAdapter.removeLoadingFooter();
                isLoading = false;

                List<UserObject> results = fetchUserList(response);
                userListAdapter.addAll(results);

                if (currentPageIndex != totalPages) userListAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<UserListData> call, Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(context, R.string.error_while_loading_data, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_dash, menu);
        menuInternet = menu.findItem(R.id.menu_no_internet);
        if (CheckNetwork.isInternetAvailable(context)){
            menuInternet.setVisible(false);
        } else {
            menuInternet.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_no_internet:
                Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
