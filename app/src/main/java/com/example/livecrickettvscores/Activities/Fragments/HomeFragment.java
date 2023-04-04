package com.example.livecrickettvscores.Activities.Fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livecrickettvscores.Activities.Adapters.FixturesAdapter;
import com.example.livecrickettvscores.Activities.Adapters.MatchMainAdapater;
import com.example.livecrickettvscores.Activities.Adapters.MatchNewsAdapter;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.LatestNewsModel;
import com.example.livecrickettvscores.Activities.Utils.ConnectionDetector;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.SelectednewslayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();
        cd = new ConnectionDetector(context);
        rcl_livematches = view.findViewById(R.id.rcl_livematches);
        rcl_trendingnews = view.findViewById(R.id.rcl_trendingnews);
        rcl_featurednews = view.findViewById(R.id.rcl_featurednews);
        native_ads = view.findViewById(R.id.native_ads);
        if (cd.isConnectingToInternet()) {
            callLiveMatchesAPI();
            callNewsAPI();
        }

        AdUtils.showNativeAd(requireActivity(), Constants.adsJsonPOJO.getParameters().getNative_id().getDefaultValue().getValue(), native_ads, false);
        Global.sout("Home fragment ", "home fragment initiated");
        return view;
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
        BottomSheetDialog btms = new BottomSheetDialog(context);
        String description = "";
        Elements textElements = document.select("p.cb-nws-para");
        SelectednewslayoutBinding binding = SelectednewslayoutBinding.inflate(LayoutInflater.from(context), null, false);
        btms.setContentView(binding.getRoot());

        binding.tvNewsheader.setText(hline);
        binding.tvUploadtime.setText(pubTime);
        Glide.with(context).load(Constants.CricBuzzBaseURL + document.select("img.cb-auth-img").attr("src")).into(binding.ivUploader);
        Glide.with(context).load(Constants.CricBuzzBaseURL + document.select("div.cb-sptlt-hdr").select("div.cb-sptlt-sctn").select("img").attr("src")).into(binding.newsImage);

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
        if (true/*newsDetailsResponseModel.getContent() != null && newsDetailsResponseModel.getContent().size() > 0*/) {
            BottomSheetDialog btms = new BottomSheetDialog(context);
            String newsTitle, newsDesc = "";
            SelectednewslayoutBinding binding = SelectednewslayoutBinding.inflate(LayoutInflater.from(context), null, false);
            btms.setContentView(binding.getRoot());
            Glide.with(context).load(Constants.CricBuzzBaseURL + newsDetails.select("section.cb-news-img-section.horizontal-img-container").select("img").attr("src")).into(binding.newsImage);
//            binding.tvUploadername.setText(newsDetailsResponseModel.getCoverImage().getSource());
//            binding.tvNewsdetails.setText(Global.getTextFromDataModel(newsDetailsResponseModel.getContent()));
            Elements description = newsDetails.select("section.cb-nws-dtl-itms");
            binding.ivUploader.setVisibility(View.GONE);
            binding.tvAuthor.setText("CricBuzz");
            binding.tvNewsheader.setText(hline);
            binding.tvUploadtime.setText(pubTime);
            binding.tvUploadername.setVisibility(View.GONE);
            for (int i = 1; i < description.size(); i++) {
                newsDesc = newsDesc + description.get(i).select("p.cb-nws-para").text() + "\n";
            }
            binding.tvNewsdetails.setText(newsDesc);

//            binding.tvUploadtime.setText(DateUtil.getDateFromSeconds(Long.parseLong(newsDetailsResponseModel.getPublishTime())));
//            binding.tvAuthor.setText(newsDetailsResponseModel.getAuthors().get(0).getName());


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
        for (int i = 0; i < 1; i++) {//4
            Elements singleMatchElement = new Elements();
            singleMatchElement.addAll(fixtureElements.get(i).select("div.ds-border-b.ds-border-line"));
            FixturesResponseModel fixturesResponseModel = new FixturesResponseModel();
            ArrayList<FixturesResponseModel.MatchesDTO> matchList = new ArrayList<>();
            fixturesResponseModel.setMatchTitle(filterText(fixtureElements.get(i).select("span.ds-text-title-xs.ds-font-bold").text()));

            for (int j = 1; j < singleMatchElement.size(); j++) {//6
                FixturesResponseModel.MatchesDTO matchesDTO = new FixturesResponseModel.MatchesDTO();
                matchesDTO.setMatchTitle(singleMatchElement.get(j).select("div.ds-text-tight-xs.ds-truncate.ds-text-typo-mid3").select("a.ds-no-tap-higlight").text());
                matchesDTO.setSession(singleMatchElement.get(j).select("div.ds-relative").select("p.ds-text-tight-s.ds-font-regular.ds-truncate.ds-text-typo").select("span").text());

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

    private void setUpMatchesListView(ArrayList<FixturesResponseModel> fixturesResponseModel) {
        if (!Global.isArrayListNull(fixturesResponseModel)) {
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(RecyclerView.HORIZONTAL);
            rcl_livematches.setLayoutManager(manager);
            rcl_livematches.removeAllViews();

            FixturesAdapter fixturesAdapter = new FixturesAdapter(context, fixturesResponseModel.get(0).getMatches());
            rcl_livematches.setAdapter(fixturesAdapter);


        }
    }


}