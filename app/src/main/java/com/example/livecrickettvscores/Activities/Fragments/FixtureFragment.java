package com.example.livecrickettvscores.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
                                callFixturesAPI(Constants.UPCOMINGMATCHES);
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
            public void getScrapedDocument(Elements document) {
                //setUpMatchesListView();
                getFixturesData(document.select("div.cb-col.cb-col-100.cb-rank-tabs").get(0).getElementsByClass("cb-col cb-col-100 cb-plyr-tbody cb-rank-hdr cb-lv-main"));
            }
        });
        callFixturesDetails.execute();

    }

    private FixturesResponseModel getFixturesData(Elements fixtureElements) {
        Elements singleMatchElement;
        FixturesResponseModel fixturesResponseModel = new FixturesResponseModel();
        FixturesResponseModel.MatchesDTO matchesDTO = new FixturesResponseModel.MatchesDTO();
        ArrayList<FixturesResponseModel.MatchesDTO> matchList = new ArrayList<>();
        for (int i = 0; i < fixtureElements.size(); i++) {
            singleMatchElement = fixtureElements.get(i).getElementsByClass("cb-mtch-lst cb-col cb-col-100 cb-tms-itm");
            for (int j = 0; j < singleMatchElement.size(); j++) {
                matchesDTO.setMatchTitle(singleMatchElement.get(j).select("div.cb-col-100.cb-col.cb-schdl.cb-billing-plans-text").select("h3.cb-lv-scr-mtch-hdr.inline-block")
                        .select("a").text());

                matchesDTO.setMatchDate(singleMatchElement.get(j).select("div.cb-col-100.cb-col.cb-schdl.cb-billing-plans-text").select("h3.cb-lv-scr-mtch-hdr.inline-block")
                        .select("div.text-gray").select("span.ng-if").text());
                matchesDTO.setMatchTime(singleMatchElement.get(j).select("div.cb-col-100.cb-col.cb-schdl.cb-billing-plans-text").select("h3.cb-lv-scr-mtch-hdr.inline-block")
                        .select("div.text-gray").select("span.ng-bind").text());
                matchesDTO.setTeamOne(singleMatchElement.get(j).select("div.cb-col-100.cb-col.cb-schdl").select("a").select("div.cb-scr-wll-chvrn.cb-lv-scrs-col ")
                        .select("div.cb-hmscg-bwl-txt.cb-ovr-flo ").select("div.cb-ovr-flo.cb-hmscg-tm-nm").text());
                matchesDTO.setTeamTwo(singleMatchElement.get(j).select("div.cb-col-100.cb-col.cb-schdl").select("a").select("div.cb-scr-wll-chvrn.cb-lv-scrs-col ")
                        .select("div.cb-hmscg-bat-txt ").select("div.cb-ovr-flo.cb-hmscg-tm-nm").text());
            }
        }


        return fixturesResponseModel;
    }


    /*private void setUpMatchesListView(ArrayList<FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO> matchesDTOArrayList) {
        if (!Global.isArrayListNull(matchesDTOArrayList)) {
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(RecyclerView.VERTICAL);
            rcl_fixtures.setLayoutManager(manager);
            rcl_fixtures.removeAllViews();
            FixturesAdapter fixturesAdapter = new FixturesAdapter(getContext(), matchesDTOArrayList);
            rcl_fixtures.setAdapter(fixturesAdapter);

        }
    }*/

}

