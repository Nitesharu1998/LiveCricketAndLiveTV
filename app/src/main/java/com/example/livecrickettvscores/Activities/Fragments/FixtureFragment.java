package com.example.livecrickettvscores.Activities.Fragments;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.APIControllers.FixturesAPIController;
import com.example.livecrickettvscores.Activities.Adapters.FixturesAdapter;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class FixtureFragment extends Fragment {
    private ArrayList<FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO> matchesDTOArrayList;
    private RecyclerView rcl_fixtures;
    private ImageView iv_back;
    private TabLayout tablayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fixture, container, false);

        tablayout=view.findViewById(R.id.tablayout);
        rcl_fixtures = view.findViewById(R.id.rcl_fixtures);
        iv_back = view.findViewById(R.id.iv_back);


        Global.sout("Fixture fragment ", "Fixture fragment initiated");
        setUptabs();
        initListeners();
        callFixturesAPI(Constants.LIVEMATHCES);
        return view;
    }

    private void setUptabs() {
        tablayout.addTab(tablayout.newTab().setText("Live"));
        tablayout.addTab(tablayout.newTab().setText("Upcoming"));
        tablayout.addTab(tablayout.newTab().setText("Recent"));
    }

    private void initListeners() {
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        callFixturesAPI(Constants.LIVEMATHCES);
                        break;
                    case 1:
                        callFixturesAPI(Constants.UPCOMINGMATCHES);
                        break;
                    case 2:
                        callFixturesAPI(Constants.RECENTMATCHES);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void callFixturesAPI(String matchtype) {
        FixturesAPIController fixtureapicontroller = new FixturesAPIController(getContext(), matchtype);
        fixtureapicontroller.callFixturesAPI(new AppInterfaces.FixturesInterface() {
            @Override
            public void getAllMatchesData(FixturesResponseModel fixturesResponseModel) {
                setUpMatchesListView(Global.filterMatchesList(fixturesResponseModel));
            }
        });
    }

    private void setUpMatchesListView(ArrayList<FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO> matchesDTOArrayList) {
        if (!Global.isArrayListNull(matchesDTOArrayList)) {
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(RecyclerView.VERTICAL);
            rcl_fixtures.setLayoutManager(manager);
            rcl_fixtures.removeAllViews();
            FixturesAdapter fixturesAdapter = new FixturesAdapter(getContext(), matchesDTOArrayList);
            rcl_fixtures.setAdapter(fixturesAdapter);

        }
    }

    public static ArrayList<FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO> filterMatchesList(FixturesResponseModel fixturesResponseModel) {
        try {
            matchesDTOArrayList = new ArrayList<>();
            ArrayList<FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO> seriesAdWrapperDTOS = new ArrayList<>();
            if (!Global.isArrayListNull(fixturesResponseModel.getTypeMatches())) {
                for (int i = 0; i < fixturesResponseModel.getTypeMatches().size(); i++) {
                    seriesAdWrapperDTOS.addAll(fixturesResponseModel.getTypeMatches().get(i).getSeriesAdWrapper());
                }
            }
            for (int i = 0; i < seriesAdWrapperDTOS.size(); i++) {
                if (seriesAdWrapperDTOS.get(i).getSeriesMatches() != null && !Global.isArrayListNull(seriesAdWrapperDTOS.get(i).getSeriesMatches().getMatches())) {
                    matchesDTOArrayList.addAll(seriesAdWrapperDTOS.get(i).getSeriesMatches().getMatches());

                }
            }

        } catch (Exception e) {
            Global.sout("Crash while processing the fixtures", e.getLocalizedMessage());
        }
        return matchesDTOArrayList;
    }

}

