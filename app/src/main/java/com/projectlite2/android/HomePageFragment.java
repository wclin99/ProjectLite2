package com.projectlite2.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class HomePageFragment extends Fragment {

    View mView;
    Toolbar toolBar;
    RecyclerView mRecyclerView;
    ProjectCardAdapter mAdapter;


    private ArrayList<ProjectCard> projectList = new ArrayList<ProjectCard>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.home_page_fragment, container, false);
        toolBar = mView.findViewById(R.id.toolBar);
        toolBar.inflateMenu(R.menu.menu_add_project);
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //initRV();

        addProjects();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView = mView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ProjectCardAdapter(projectList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnKotlinItemClickListener(new ProjectCardAdapter.IKotlinItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                MyApplication.showToast(projectList.get(position).getName());
            }
        });

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_project,menu);
    }
    /*
     * 初始化列表数据
     */

    private void addProjects() {
        projectList.add(new ProjectCard("信息与交互设计", true, 25));
        projectList.add(new ProjectCard("用户体验设计", false, 30));
        projectList.add(new ProjectCard("产品设计方法学", false, 90));
        projectList.add(new ProjectCard("交互设计专题（一）", true, 10));
        projectList.add(new ProjectCard("产品设计专题", false, 60));
        projectList.add(new ProjectCard("信息与交互设计", true, 25));
        projectList.add(new ProjectCard("用户体验设计", false, 20));
        projectList.add(new ProjectCard("产品设计方法学", false, 90));
        projectList.add(new ProjectCard("交互设计专题（一）", true, 10));
        projectList.add(new ProjectCard("产品设计专题", false, 60));

    }
}
