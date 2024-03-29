package com.example.livecrickettvscores.Activities.Fragments;

import static com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils.loadInitialInterstitialAds;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livecrickettvscores.Activities.Adapters.FixturesAdapter;
import com.example.livecrickettvscores.Activities.Adapters.MatchNewsAdapter;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils;
import com.example.livecrickettvscores.Activities.FullScoreBoardActivity;
import com.example.livecrickettvscores.Activities.LiveMatchScoreBoardActivity;
import com.example.livecrickettvscores.Activities.PredictionsMainActivity;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.LatestNewsModel;
import com.example.livecrickettvscores.Activities.Utils.ConnectionDetector;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.SelectednewslayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class HomeFragment extends Fragment {
    RecyclerView rcl_featurednews, rcl_trendingnews, rcl_livematches;
    Context context;
    LinearLayout native_ads;
    ConnectionDetector cd;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    ImageView iv_nav;
    FrameLayout frameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();
        frameLayout = view.findViewById(R.id.frm_homefrag);
        cd = new ConnectionDetector(context);
        rcl_livematches = view.findViewById(R.id.rcl_livematches);
        rcl_trendingnews = view.findViewById(R.id.rcl_trendingnews);
        rcl_featurednews = view.findViewById(R.id.rcl_featurednews);
        //native_ads = view.findViewById(R.id.native_ads);
        navigationView = view.findViewById(R.id.mNavigationView);
        drawerLayout = view.findViewById(R.id.drawer);
        iv_nav = view.findViewById(R.id.iv_nav);

        toggle = new ActionBarDrawerToggle(requireActivity(), drawerLayout, R.string.opendrawer, R.string.closedrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        iv_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });

        /*navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.sidenav_news:
                        Toast.makeText(context, "News", Toast.LENGTH_SHORT).show();
                        drawerLayout.close();
                        break;
                    case R.id.sidenav_awards:
                        Toast.makeText(context, "awards", Toast.LENGTH_SHORT).show();
                        drawerLayout.close();
                        break;
                    case R.id.sidenav_prediction:
                        AdUtils.showInterstitialAd(requireActivity(), new AppInterfaces.InterStitialADInterface() {
                            @Override
                            public void adLoadState(boolean isLoaded) {
                                startActivity(new Intent(context, PredictionsMainActivity.class));
                                drawerLayout.close();
                            }
                        });
                        break;
                    case R.id.sidenav_rankings:
                        AdUtils.showInterstitialAd(requireActivity(), new AppInterfaces.InterStitialADInterface() {
                            @Override
                            public void adLoadState(boolean isLoaded) {
                                refreshFragment(new StatsFragment());
                                drawerLayout.close();
                            }
                        });

                        break;
                }
                return true;
            }
        });*/
        if (cd.isConnectingToInternet()) {
            callLiveMatchesAPI();
            callNewsAPI();
        }

        //AdUtils.showNativeAd(requireActivity(), Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), native_ads, false);
        return view;
    }

    private void refreshFragment(Fragment statsFragment) {
        frameLayout.removeAllViews();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frm_homefrag, statsFragment).commit();
    }

    private void callNewsAPI() {

        AppAsyncTasks.GetFeaturedNews getFeaturedNews = new AppAsyncTasks.GetFeaturedNews(Constants.CricketNewsURL, context, new AppInterfaces.WebScrappingInterface() {
            @Override
            public void getScrapedDocument(Elements document) {
                setUpNewsList(getFilteredNews(document));
                callSpotlightNewsApi(Constants.CricketSpotlightNewsURL);
            }
        });
        getFeaturedNews.execute();

    }

    private void callSpotlightNewsApi(String cricketSpotlightNewsURL) {
        AppAsyncTasks.GetFeaturedNews getFeaturedNews = new AppAsyncTasks.GetFeaturedNews(cricketSpotlightNewsURL, context, new AppInterfaces.WebScrappingInterface() {
            @Override
            public void getScrapedDocument(Elements document) {
                setUpSpotlightNewsList(getFilteredNews(document));
            }
        });
        getFeaturedNews.execute();
    }

    private void setUpSpotlightNewsList(LatestNewsModel filteredNews) {
        MatchNewsAdapter matchNewsAdapter = new MatchNewsAdapter(context, filteredNews.getNewsListDTO(), new AppInterfaces.NewsAdapterClick() {
            @Override
            public void getClickedNewsID(Integer Position) {
                AppAsyncTasks.GetNewsDetails getNewsDetails = new AppAsyncTasks.GetNewsDetails(filteredNews.getNewsListDTO().get(Position).getSource(), context, new AppInterfaces.NewsWebScrappingInterface() {
                    @Override
                    public void getScrapedDocument(Elements document, int state) {
                        if (state == 1) {
                            //Handle document for special cases
                            openBtmsforDiffScrapedData(filteredNews.getNewsListDTO().get(Position).getSource(), filteredNews.getNewsListDTO().get(Position).getPubTime(), filteredNews.getNewsListDTO().get(Position).getHline(), document);
                        } else {
                            openBottomSheetOfNewsDetails(filteredNews.getNewsListDTO().get(Position).getSource(), filteredNews.getNewsListDTO().get(Position).getPubTime(), filteredNews.getNewsListDTO().get(Position).getHline(), document);
                        }
                    }

                });
                getNewsDetails.execute();
            }
        });
        rcl_trendingnews.setLayoutManager(Global.getManagerWithOrientation(context, RecyclerView.VERTICAL));
        rcl_trendingnews.setAdapter(matchNewsAdapter);
    }

    private void setUpNewsList(LatestNewsModel filteredNews) {
        MatchNewsAdapter matchNewsAdapter = new MatchNewsAdapter(context, filteredNews.getNewsListDTO(), new AppInterfaces.NewsAdapterClick() {
            @Override
            public void getClickedNewsID(Integer Position) {
                AppAsyncTasks.GetNewsDetails getNewsDetails = new AppAsyncTasks.GetNewsDetails(filteredNews.getNewsListDTO().get(Position).getSource(), context, new AppInterfaces.NewsWebScrappingInterface() {
                    @Override
                    public void getScrapedDocument(Elements document, int state) {
                        if (state == 1) {
                            //Handle document for special cases
                            openBtmsforDiffScrapedData(filteredNews.getNewsListDTO().get(Position).getSource(), filteredNews.getNewsListDTO().get(Position).getPubTime(), filteredNews.getNewsListDTO().get(Position).getHline(), document);
                        } else {
                            openBottomSheetOfNewsDetails(filteredNews.getNewsListDTO().get(Position).getSource(), filteredNews.getNewsListDTO().get(Position).getPubTime(), filteredNews.getNewsListDTO().get(Position).getHline(), document);
                        }
                    }

                });
                getNewsDetails.execute();
            }
        });
        rcl_featurednews.setLayoutManager(Global.getManagerWithOrientation(context, RecyclerView.HORIZONTAL));
        rcl_featurednews.setAdapter(matchNewsAdapter);


    }

    private void openBtmsforDiffScrapedData(String newsSource, String pubTime, String hline, Elements document) {
        Global.sout("openBtmsforDiffScrapedData", "openBtmsforDiffScrapedData clicked");
        BottomSheetDialog btms = new BottomSheetDialog(context);
        String description = "";
        Elements textElements = document.select("p.cb-nws-para");
        SelectednewslayoutBinding binding = SelectednewslayoutBinding.inflate(LayoutInflater.from(context), null, false);
        btms.setContentView(binding.getRoot());

        binding.tvNewsheader.setText(hline);
        binding.tvUploadtime.setText(pubTime);
        Glide.with(context).load(Constants.CricBuzzBaseURL + document.select("img.cb-auth-img").attr("src")).error(R.drawable.defaultavatar).into(binding.ivUploader);
        Glide.with(context).load(Constants.CricBuzzBaseURL + document.select("div.cb-sptlt-hdr").select("div.cb-sptlt-sctn").select("img").attr("src")).error(R.drawable.news_default).into(binding.newsImage);

        binding.tvAuthor.setText(document.select("div.cb-sptlt-hdr").select("div.cb-spt-athr").text());

        for (int i = 0; i < textElements.size(); i++) {
            description = description + textElements.get(i).text();
        }

        binding.tvNewsdetails.setText(description);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btms.dismiss();
            }
        });

        binding.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, newsSource);
                startActivity(Intent.createChooser(intent, newsSource));

            }
        });
        binding.ivLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("text", newsSource);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(context, "Link is copied to clipboard", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Global.sout("Clipboard exception", e.getMessage());
                }
            }
        });


        btms.show();
    }

    private LatestNewsModel getFilteredNews(Elements document) {
        LatestNewsModel dataModel = new LatestNewsModel();
        ArrayList<LatestNewsModel.NewsListDTO> filteredNewsList = new ArrayList<>();
        for (int i = 0; i < document.size(); i++) {
            LatestNewsModel.NewsListDTO singleNewsDTO = new LatestNewsModel.NewsListDTO();
            singleNewsDTO.setHline(document.get(i).select("div.cb-col.cb-col-33.cb-pos-rel").select("a").attr("title"));
            singleNewsDTO.setImageURL(Constants.CricBuzzBaseURL + document.get(i).select("div.cb-col.cb-col-33.cb-pos-rel").select("img").attr("src"));
            singleNewsDTO.setPubTime(document.get(i).select("div.cb-col-67.cb-nws-lst-rt.cb-col.cb-col-text-container").select("span.cb-nws-time").text());
            singleNewsDTO.setSource(Constants.CricBuzzBaseURL + document.get(i).select("div.cb-col-67.cb-nws-lst-rt.cb-col.cb-col-text-container").select("a.cb-nws-hdln-ancr.text-hvr-underline").attr("href"));
            singleNewsDTO.setSubHeading(document.get(i).select("div.cb-col-67.cb-nws-lst-rt.cb-col.cb-col-text-container").select("div.cb-nws-intr").text());
            filteredNewsList.add(singleNewsDTO);
        }
        dataModel.setNewsListDTO(filteredNewsList);
        return dataModel;
    }


    private void openBottomSheetOfNewsDetails(String newsSource, String pubTime, String hline, Elements newsDetails) {

        Global.sout("openBottomSheetOfNewsDetails", "openBottomSheetOfNewsDetails clicked");
        BottomSheetDialog btms = new BottomSheetDialog(context);
        String newsTitle, newsDesc = "";
        SelectednewslayoutBinding binding = SelectednewslayoutBinding.inflate(LayoutInflater.from(context), null, false);
        btms.setContentView(binding.getRoot());
        binding.ivUploader.setImageResource(R.drawable.defaultavatar2);
        Glide.with(context).load(Constants.CricBuzzBaseURL + newsDetails.select("section.cb-news-img-section.horizontal-img-container").select("img").attr("src")).error(R.drawable.news_default).into(binding.newsImage);
        Elements description = newsDetails.select("section.cb-nws-dtl-itms");
        binding.ivUploader.setVisibility(View.VISIBLE);
        binding.tvAuthor.setText("CricBuzz");
        binding.tvNewsheader.setText(hline);
        binding.tvUploadtime.setText(pubTime);
        binding.tvUploadername.setVisibility(View.GONE);
        for (int i = 1; i < description.size(); i++) {
            newsDesc = newsDesc + description.get(i).select("p.cb-nws-para").text() + "\n";
        }
        binding.tvNewsdetails.setText(newsDesc);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btms.dismiss();
            }
        });

        binding.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, newsSource);
                    startActivity(Intent.createChooser(intent, newsSource));

                }
            });
            binding.ivLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("text", newsSource);
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(context, "Link is copied to clipboard", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Global.sout("Clipboard exception", e.getMessage());
                    }
                }
            });
        btms.show();
    }


    private void callLiveMatchesAPI() {

        AppAsyncTasks.CallFixturesDetails callFixturesDetails = new AppAsyncTasks.CallFixturesDetails(Constants.LIVEMATHCES, requireActivity(), new AppInterfaces.WebScrappingInterface() {
            @Override
            public void getScrapedDocument(Elements fetchedElements) {
                setUpMatchesListView(getFixturesData(fetchedElements));

            }
        });
        callFixturesDetails.execute();


    }

    private String filterText(String teamTwoScore) {
        String[] words = teamTwoScore.split("\\s+");
        Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
        String outputString = String.join(" ", uniqueWords);

        return outputString;
    }

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
            fixturesResponseModel.setMatches(matchList);
            fixturesResponseModelArrayList.add(fixturesResponseModel);

        }

        return fixturesResponseModelArrayList;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadInitialInterstitialAds(requireActivity());
    }

    private void setUpMatchesListView(ArrayList<FixturesResponseModel> fixturesResponseModel) {
        if (!Global.isArrayListNull(fixturesResponseModel)) {
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(RecyclerView.HORIZONTAL);
            rcl_livematches.setLayoutManager(manager);
            rcl_livematches.removeAllViews();

            FixturesAdapter fixturesAdapter = new FixturesAdapter(true, context, fixturesResponseModel.get(0).getMatches(), new AppInterfaces.NewsAdapterClick() {
                @Override
                public void getClickedNewsID(Integer newsID) {
                    //TODO to open the score of match
                    Constants.matchDTO = fixturesResponseModel.get(0).getMatches().get(newsID);
                    if (Constants.matchDTO.getSession().contains("won") || Constants.matchDTO.getSession().contains("draw") || Constants.matchDTO.getSession().contains("match over") || Constants.matchDTO.getSession().contains("abandoned") || Constants.matchDTO.getSession().contains("No result")) {
                        Constants.matchDTO.setMatchScoreLink(Constants.matchDTO.getMatchScoreLink().replace("live-cricket-score", "full-scoreboard"));
                        context.startActivity(new Intent(context, FullScoreBoardActivity.class));
                    } else if (Constants.matchDTO.getSession().contains("chose") || Constants.matchDTO.getSession().contains("lead") || Constants.matchDTO.getSession().contains("trail") || Constants.matchDTO.getSession().contains("need")) {
                        context.startActivity(new Intent(context, LiveMatchScoreBoardActivity.class));
                    } else if (Constants.matchDTO.getSession().contains("yet")) {
                        Toast.makeText(context, "Score are not available yet", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Score are not available...", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            rcl_livematches.setAdapter(fixturesAdapter);


        }
    }


}