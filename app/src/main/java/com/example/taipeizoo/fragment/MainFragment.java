package com.example.taipeizoo.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.taipeizoo.R;
import com.example.taipeizoo.adapter.AreaRecyclerAdapter;
import com.example.taipeizoo.controller.AreaInfoController;
import com.example.taipeizoo.model.Area;
import com.example.taipeizoo.observer.UserDataRepository;
import com.example.taipeizoo.view.WaitProgressDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static com.example.taipeizoo.webservice.OkManager.AREADATA;

public class MainFragment extends Fragment
        implements AdapterView.OnItemClickListener, Observer {
    private ArrayList<Area> areaList;

    private AreaRecyclerAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Activity mActivity;

    private AreaInfoController controller;

    private Observable mUserDataRepositoryObservable;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserDataRepositoryObservable = UserDataRepository.getInstance();
        areaList = UserDataRepository.getInstance().getAreaListData();
        mUserDataRepositoryObservable.addObserver(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mUserDataRepositoryObservable.deleteObserver(this);
    }

    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (controller == null) {
            controller = new AreaInfoController(MainFragment.this);
        }

        if (areaList != null) {
            controller.setData(areaList);
        }

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mActivity != null) {
            mActivity.setTitle(R.string.app_name);
        }

        adapter = new AreaRecyclerAdapter(mActivity, this);
        adapter.setHasStableIds(true);

        RecyclerView recyclerView = requireView().findViewById(R.id.recyclerView);
        // 設定LayoutManager為LinearLayout
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = requireView().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setRefreshing(false);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            UserDataRepository.getInstance().asyncGetPlantJsonData();
        });

        if (controller.getData() != null) {
            adapter.refreshData(controller);
        } else {
            WaitProgressDialog.showProgressDialog(mActivity, getString(R.string.loading_data));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (getActivity() != null) {
            try {
                Fragment plantFragment = new AreaFragment();
                Bundle plantBundle = new Bundle();
                plantBundle.putSerializable(AREADATA, controller.getData().get(i));
                plantFragment.setArguments(plantBundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainLayout, plantFragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            } catch (Exception ignored) {

            }
        }
    }

    public void refreshRecyclerView() {
        if (controller.getData() != null) {
            adapter.refreshData(controller);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        WaitProgressDialog.closeDialog();

        if (o instanceof UserDataRepository) {
            UserDataRepository userDataRepository = (UserDataRepository) o;

            if (userDataRepository.getAreaListData() != null) {
                controller.setData(userDataRepository.getAreaListData());
                controller.updateView();
            }

        }
    }
}