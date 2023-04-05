package com.example.livecrickettvscores.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livecrickettvscores.Activities.Adapters.MatchMainAdapater;
import com.example.livecrickettvscores.Activities.Adapters.UpcomingMatchAdapter;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Utils.ConnectionDetector;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;
import com.google.android.material.tabs.TabLayout;

import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class FixtureFragment extends Fragment {
    private RecyclerView rcl_fixtures;
    private ImageView iv_back;
    private TabLayout tablayout;
    Context context;
    LinearLayout native_ads;
    ConnectionDetector cd;

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
        cd =new ConnectionDetector(context);
        AdUtils.showNativeAd(requireActivity(), Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), native_ads, false);


        Global.sout("Fixture fragment ", "Fixture fragment initiated");
        setUptabs();
        initListeners();
        if (cd.isConnectingToInternet()) {
            callFixturesAPI(Constants.LIVEMATHCES);
        }

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
                                if (cd.isConnectingToInternet())
                                    callFixturesAPI(Constants.LIVEMATHCES);
                            }
                        });

                        break;
                    case 1:
                        AdUtils.showInterstitialAd(requireActivity(), new AppInterfaces.InterStitialADInterface() {
                            @Override
                            public void adLoadState(boolean isLoaded) {
                                if (cd.isConnectingToInternet())
                                    callUpComingMatchesTask(Constants.UPCOMINGMATCHES);
                            }
                        });

                        break;
                    case 2:
                        AdUtils.showInterstitialAd(requireActivity(), new AppInterfaces.InterStitialADInterface() {
                            @Override
                            public void adLoadState(boolean isLoaded) {
                                if (cd.isConnectingToInternet())
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
        AppAsyncTasks.CallUpComingDetails upComingDetails = new AppAsyncTasks.CallUpComingDetails(upcomingmatches, context, new AppInterfaces.WebScrappingInterface() {
            @Override
            public void getScrapedDocument(Elements upComingElements) {
                System.out.println("upcoming doc size>>>>>>>>>>>>>>" + upComingElements.size());
                setUpUpcomingMatches(getUpComingList(upComingElements));
            }
        });
        upComingDetails.execute();
    }

    private void setUpUpcomingMatches(ArrayList<FixturesResponseModel> upComingList) {
        if (!Global.isArrayListNull(upComingList)) {
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(RecyclerView.VERTICAL);
            rcl_fixtures.setLayoutManager(manager);
            rcl_fixtures.removeAllViews();
            UpcomingMatchAdapter adapter = new UpcomingMatchAdapter(getContext(), upComingList);
            rcl_fixtures.setAdapter(adapter);

           /* FixturesAdapter fixturesAdapter = new FixturesAdapter(getContext(), fixturesResponseModel);
            rcl_fixtures.setAdapter(fixturesAdapter);*/

        }
    }

    private ArrayList<FixturesResponseModel> getUpComingList(Elements upComingElements) {

        ArrayList<FixturesResponseModel> fixturesResponseModelArrayList = new ArrayList<>();

        FixturesResponseModel responseModel;

        Elements singleMatchElement;
        Elements singleTempMatchElement;
        for (int i = 0; i < upComingElements.size(); i++) {//4

            ArrayList<FixturesResponseModel.MatchesDTO> matchesDTOS = new ArrayList<>();
            responseModel = new FixturesResponseModel();
            responseModel.setMatchTitle(upComingElements.get(i).select("div.ds-flex.ds-justify-center.ds-mb-2").select("span").text());
            singleMatchElement = upComingElements.get(i).select("div.ds-border.ds-border-line.ds-rounded-xl.ds-overflow-hidden").select("a").attr("class", "ds-no-tap-higlight");
            singleTempMatchElement = upComingElements.get(i).select("div.ds-border.ds-border-line.ds-rounded-xl.ds-overflow-hidden").select("div[class=ds-w-2/5]");
            for (int j = 0; j < singleMatchElement.size(); j++) {
                FixturesResponseModel.MatchesDTO singleMatch = new FixturesResponseModel.MatchesDTO();
                singleMatch.setMatchTime(singleMatchElement.get(j).select("span[class=ds-text-compact-xs ds-font-bold ds-block ds--mb-1 ds-mt-[3px] ds-text-typo]").text());
                singleMatch.setTeamOne(singleMatchElement.get(j).select("div[class=ds-w-3/5]").select("p.ds-text-compact-s.ds-font-bold.ds--mb-1.ds-text-typo").text().replace("Int’l", ""));
                singleMatch.setMatchLocation(singleMatchElement.get(j).select("div[class=ds-w-3/5]").select("span[class=ds-text-tight-xs ds-text-typo-mid3]").text());
                singleMatch.setMatchType(singleMatchElement.get(j).select("div[class=ds-w-3/5]").select("p.ds-text-compact-s.ds-font-bold.ds--mb-1.ds-text-typo").select("span").text());
                matchesDTOS.add(singleMatch);
            }
            responseModel.setMatches(matchesDTOS);

            fixturesResponseModelArrayList.add(responseModel);
        }

        return fixturesResponseModelArrayList;

    }

    private void callFixturesAPI(String matchtype) {

        AppAsyncTasks.CallFixturesDetails callFixturesDetails = new AppAsyncTasks.CallFixturesDetails(matchtype, requireActivity(), new AppInterfaces.WebScrappingInterface() {
            @Override
            public void getScrapedDocument(Elements fetchedElements) {
                setUpMatchesListView(getFixturesData(fetchedElements));

            }
        });
        callFixturesDetails.execute();

    }

   /* private LiveData<List<FixturesResponseModel>> getMatches() {
        MutableLiveData<List<FixturesResponseModel>> mutableLiveData = new MutableLiveData<>();

        mutableLiveData.postValue(getFixturesData());


        return mutableLiveData;
    }*/

    private ArrayList<FixturesResponseModel> getFixturesData(Elements fixtureElements) {

        ArrayList<FixturesResponseModel> fixturesResponseModelArrayList = new ArrayList<>();
        for (int i = 0; i < fixtureElements.size(); i++) {//4
            Elements singleMatchElement = new Elements();
            singleMatchElement.addAll(fixtureElements.get(i).select("div.ds-border-b.ds-border-line"));
            FixturesResponseModel fixturesResponseModel = new FixturesResponseModel();
            ArrayList<FixturesResponseModel.MatchesDTO> matchList = new ArrayList<>();
            fixturesResponseModel.setMatchTitle(filterText(fixtureElements.get(i).select("span.ds-text-title-xs.ds-font-bold").text()));

            for (int j = 1; j < singleMatchElement.size(); j++) {//6
                FixturesResponseModel.MatchesDTO matchesDTO = new FixturesResponseModel.MatchesDTO();
                matchesDTO.setMatchTitle(singleMatchElement.get(j).select("div.ds-truncate").select("div.ds-text-tight-xs.ds-truncate.ds-text-typo-mid3").text());
                matchesDTO.setSession(singleMatchElement.get(j)/*.select("div.ds-relative")*/.select("p.ds-text-tight-s.ds-font-regular.ds-truncate.ds-text-typo").select("span").text());
                matchesDTO.setMatchScoreLink(singleMatchElement.get(j).select("div.ds-px-4.ds-py-3").select("a").attr("href"));
                Global.sout("mathscorelink >>>>"+i+">>>>>>",singleMatchElement.get(j).select("div.ds-px-4.ds-py-3").select("a").attr("href") );
                Elements teamScores = singleMatchElement.get(j).select("div.ci-team-score");
                for (int k = 0; k < teamScores.size(); k++) {//2
                    if (k == 0) {
                        matchesDTO.setTeamOne(teamScores.get(k).select("p").text());
                        matchesDTO.setTeamOneScore(teamScores.get(k).select("strong").text());
                    } else {
                        matchesDTO.setTeamTwo(teamScores.get(k).select("p").text());
                        matchesDTO.setTeamTwoScore(teamScores.get(k).select("strong").text());

                    }
                }
                matchList.add(matchesDTO);
            }
            //Collections.reverse(matchList);
            fixturesResponseModel.setMatches(matchList);
            fixturesResponseModelArrayList.add(fixturesResponseModel);

        }

        return fixturesResponseModelArrayList;
    }

    private String filterText(String teamTwoScore) {
        String[] words = teamTwoScore.split("\\s+");
        Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
        String outputString = String.join(" ", uniqueWords);

        return outputString;
    }


    private void setUpMatchesListView(ArrayList<FixturesResponseModel> fixturesResponseModel) {
        if (!Global.isArrayListNull(fixturesResponseModel)) {
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(RecyclerView.VERTICAL);
            rcl_fixtures.setLayoutManager(manager);
            rcl_fixtures.removeAllViews();
            MatchMainAdapater adapter = new MatchMainAdapater(getContext(), fixturesResponseModel, new AppInterfaces.NewsAdapterClick() {
                @Override
                public void getClickedNewsID(Integer newsID) {

                }
            });
            rcl_fixtures.setAdapter(adapter);

           /* FixturesAdapter fixturesAdapter = new FixturesAdapter(getContext(), fixturesResponseModel);
            rcl_fixtures.setAdapter(fixturesAdapter);*/

        }
    }

}

