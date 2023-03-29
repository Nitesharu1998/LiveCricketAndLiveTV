package com.example.livecrickettvscores.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.Adapters.FixturesAdapter;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;
import com.google.android.material.tabs.TabLayout;

import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class FixtureFragment extends Fragment {
    //    private ArrayList<FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO> matchesDTOArrayList;
    private RecyclerView rcl_fixtures;
    private ImageView iv_back;
    private TabLayout tablayout;
    Context context;
    LinearLayout native_ads;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fixture, container, false);
        context = view.getContext();
        tablayout = view.findViewById(R.id.tablayout);
        rcl_fixtures = view.findViewById(R.id.rcl_fixtures);
        native_ads = view.findViewById(R.id.native_ads);
        iv_back = view.findViewById(R.id.iv_back);
        AdUtils.showNativeAd(requireActivity(), Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), native_ads, false);


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
                        AdUtils.showInterstitialAd(requireActivity(), new AppInterfaces.InterStitialADInterface() {
                            @Override
                            public void adLoadState(boolean isLoaded) {
                                callFixturesAPI(Constants.LIVEMATHCES);
                            }
                        });

                        break;
                    case 1:
                        AdUtils.showInterstitialAd(requireActivity(), new AppInterfaces.InterStitialADInterface() {
                            @Override
                            public void adLoadState(boolean isLoaded) {
                                callUpComingMatchesTask(Constants.UPCOMINGMATCHES);
                            }
                        });

                        break;
                    case 2:
                        AdUtils.showInterstitialAd(requireActivity(), new AppInterfaces.InterStitialADInterface() {
                            @Override
                            public void adLoadState(boolean isLoaded) {
                                callFixturesAPI(Constants.RECENTMATCHES);
                            }
                        });

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

    private void callUpComingMatchesTask(String upcomingmatches) {
    }

    private void callFixturesAPI(String matchtype) {
        /*FixturesAPIController fixtureapicontroller = new FixturesAPIController(context, matchtype);
        fixtureapicontroller.callFixturesAPI(new AppInterfaces.FixturesInterface() {
            @Override
            public void getAllMatchesData(FixturesResponseModel fixturesResponseModel) {
                setUpMatchesListView(Global.filterMatchesList(fixturesResponseModel));
            }
        });*/

        AppAsyncTasks.CallFixturesDetails callFixturesDetails = new AppAsyncTasks.CallFixturesDetails(matchtype, requireActivity(), new AppInterfaces.WebScrappingInterface() {
            @Override
            public void getScrapedDocument(Elements fetchedElements) {
                getFixturesData(matchtype, fetchedElements);

            }
        });
        callFixturesDetails.execute();

    }

    private FixturesResponseModel getFixturesData(String matchtype, Elements fixtureElements) {
        Elements singleMatchElement = new Elements();
        FixturesResponseModel fixturesResponseModel = new FixturesResponseModel();
        FixturesResponseModel.MatchesDTO matchesDTO;
        ArrayList<FixturesResponseModel.MatchesDTO> matchList = new ArrayList<>();
        for (int i = 0; i < fixtureElements.size(); i++) {//4
            singleMatchElement.addAll(fixtureElements.get(i).select("div.ds-border-b.ds-border-line"));

            for (int j = 1; j < singleMatchElement.size(); j++) { //6
                //Log.e( "getFixturesData: ", singleMatchElement.get(i).select("").toString());
                matchesDTO=new FixturesResponseModel.MatchesDTO();
                String matchTitle = singleMatchElement.get(j).select("div.ds-text-tight-xs.ds-truncate.ds-text-typo-mid3").select("a.ds-no-tap-higlight").text();
                String matchStatus = singleMatchElement.get(j).select("div.ds-relative").
                        select("p").select("span").text();
                matchesDTO.setMatchTitle(matchTitle);
                matchesDTO.setSession(matchStatus);

                /*String matchType = singleMatchElement.get(j).select("div.cb-mtch-lst-info").text();*/ //TODO bhetla tr baghu

               /* System.out.println("Title and time and location: " + matchTitle);
                System.out.println("status: " + matchStatus);
                System.out.println("---------------------------");*/
                Elements teamScores = singleMatchElement.get(i).select("div.ci-team-score");
                for (int k = 0; k < teamScores.size(); k++) {//2
                    if (k == 0) {
                        /*System.out.println("teamName1" + teamScores.get(k).select("p").text());
                        System.out.println("score1" + teamScores.get(k).select("span").text());*/
                        matchesDTO.setTeamOne(teamScores.get(k).select("p").text());
                        matchesDTO.setTeamOneScore(teamScores.get(k).select("span").text());
                    } else {
                        matchesDTO.setTeamTwo(teamScores.get(k).select("p").text());
                        matchesDTO.setTeamTwoScore(teamScores.get(k).select("span").text());
                        /*System.out.println("teamName2" + teamScores.get(k).select("p").text());
                        System.out.println("score2" + teamScores.get(k).select("span").text());*/
                    }
                }
                matchList.add(matchesDTO);

            }

        }Collections.reverse(matchList);
        fixturesResponseModel.setMatches(matchList);
        return fixturesResponseModel;
    }

    private String filterText(String teamTwoScore) {
        String[] words = teamTwoScore.split("\\s+");
        Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
        String outputString = String.join(" ", uniqueWords);

        return outputString;
    }


    private void setUpMatchesListView(FixturesResponseModel fixturesResponseModel) {
        if (!Global.isArrayListNull(fixturesResponseModel.getMatches())) {
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(RecyclerView.VERTICAL);
            rcl_fixtures.setLayoutManager(manager);
            rcl_fixtures.removeAllViews();
            FixturesAdapter fixturesAdapter = new FixturesAdapter(getContext(), fixturesResponseModel.getMatches());
            rcl_fixtures.setAdapter(fixturesAdapter);

        }
    }

}

