package com.example.livecrickettvscores.Activities.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.APIControllers.FixturesAPIController;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class FixtureFragment extends Fragment {
    private FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO.MatchInfoDTO matchInfoDTO;
    private FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO.MatchScoreDTO matchScoreDTO;
    private FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO matchesDTO;
    private ArrayList<FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO> matchesDTOArrayList = new ArrayList<>();
    private TabItem recenttab, upcomingtab, livetab;
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
                matchesDTOArrayList = setUpMatchesList(fixturesResponseModel);
            }
        });
    }

    private ArrayList<FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO> setUpMatchesList(FixturesResponseModel fixturesResponseModel) {
        try {
            if (Global.CheckArrayList(fixturesResponseModel.getTypeMatches())) {
                for (int i = 0; i < fixturesResponseModel.getTypeMatches().size(); i++) {
                    if (Global.CheckArrayList(fixturesResponseModel.getTypeMatches().get(i).getSeriesAdWrapper())) {
                        for (int j = 0; j < fixturesResponseModel.getTypeMatches().get(i).getSeriesAdWrapper().size(); j++) {
                            if (Global.CheckArrayList(fixturesResponseModel.getTypeMatches().get(i).getSeriesAdWrapper().get(j).getSeriesMatches().getMatches())) {
                                for (int k = 0; k < fixturesResponseModel.getTypeMatches().get(i).getSeriesAdWrapper().get(j).getSeriesMatches().getMatches().size(); k++) {
                                    matchInfoDTO = new FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO.MatchInfoDTO();
                                    matchScoreDTO = new FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO.MatchScoreDTO();
                                    matchesDTO = new FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO();

                                    matchInfoDTO = fixturesResponseModel.getTypeMatches().get(i).getSeriesAdWrapper().get(j).getSeriesMatches().getMatches().get(k).getMatchInfo();
                                    matchScoreDTO = fixturesResponseModel.getTypeMatches().get(i).getSeriesAdWrapper().get(j).getSeriesMatches().getMatches().get(k).getMatchScore();

                                    matchesDTO.setMatchInfo(matchInfoDTO);
                                    matchesDTO.setMatchScore(matchScoreDTO);
                                    matchesDTOArrayList.add(matchesDTO);
                                }
                            }
                        }
                    }

                }
            }

        } catch (Exception e) {
            Global.sout("Crash while processing the fixtures", e.getLocalizedMessage());
        }
        return matchesDTOArrayList;
    }

}

