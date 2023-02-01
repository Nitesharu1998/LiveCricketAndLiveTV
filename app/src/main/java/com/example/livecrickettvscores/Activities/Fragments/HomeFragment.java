package com.example.livecrickettvscores.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livecrickettvscores.Activities.APIControllers.FixturesAPIController;
import com.example.livecrickettvscores.Activities.APIControllers.HomeFragmentAPIController;
import com.example.livecrickettvscores.Activities.Adapters.FeaturedNewsAdapter;
import com.example.livecrickettvscores.Activities.Adapters.FixturesAdapter;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.NewsDetailsResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.NewsListResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.Activities.Utils.InputUtils;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.SelectednewslayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView rcl_featurednews, rcl_trendingnews, rcl_livematches;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initUI(container);
        callLiveMatchesAPI();
        callNewsAPI();
        Global.sout("Home fragment ", "home fragment initiated");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void callNewsAPI() {
        HomeFragmentAPIController homeFragmentAPIController = new HomeFragmentAPIController(Constants.NEWSCONSTANT, context);
        homeFragmentAPIController.callFeturedNewsAPI(new AppInterfaces.NewsInterface() {
            @Override
            public void getNewsList(NewsListResponseModel newsListResponseModel) {
                setUpFeaturedNewsList(newsListResponseModel);
            }
        });
    }

    private void setUpFeaturedNewsList(NewsListResponseModel newsListResponseModel) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        rcl_featurednews.setLayoutManager(manager);
        FeaturedNewsAdapter featuredNewsAdapter = new FeaturedNewsAdapter(context, newsListResponseModel.getNewsList(), new AppInterfaces.NewsAdapterClick() {
            @Override
            public void getClickedNewsID(Integer newsID) {
                callNewsDetailsAPI(newsID);
            }
        });
        rcl_featurednews.setAdapter(featuredNewsAdapter);
    }

    private void callNewsDetailsAPI(Integer newsID) {
        HomeFragmentAPIController homeFragmentAPIController = new HomeFragmentAPIController(0, context);//TODO no category was needed so 0 is used
        homeFragmentAPIController.callNewsDetailsAPI(new AppInterfaces.NewsDetailInterface() {
            @Override
            public void getNewsDetail(NewsDetailsResponseModel newsDetailsResponseModel) {
                openBottomSheetOfNewsDetails(newsDetailsResponseModel);
            }
        }, newsID);
    }

    private void openBottomSheetOfNewsDetails(NewsDetailsResponseModel newsDetailsResponseModel) {
        if (newsDetailsResponseModel.getContent() != null && newsDetailsResponseModel.getContent().size() > 0) {
            BottomSheetDialog btms = new BottomSheetDialog(context);
            SelectednewslayoutBinding binding = SelectednewslayoutBinding.inflate(LayoutInflater.from(context), null, false);
            btms.setContentView(binding.getRoot());
            Glide.with(context).load(Global.getTheImage(context, String.valueOf(newsDetailsResponseModel.getId()))).into(binding.newsImage);
            binding.tvNewssource.setText(newsDetailsResponseModel.getCoverImage().getSource());
            binding.tvNewsdetails.setText(getTextFromDataModel(newsDetailsResponseModel.getContent()));

            btms.show();
        }
    }

    private String getTextFromDataModel(ArrayList<NewsDetailsResponseModel.ContentDTO> content) {
        String newsDetails = "";
        try {
            if (!Global.isArrayListNull(content)) {

                for (int i = 0; i < content.size(); i++) {
                    if (InputUtils.CheckEqualCaseSensitive(content.get(i).getContent().getContentType(), "text")) {
                        newsDetails = newsDetails + content.get(i).getContent().getContentValue();
                    }
                }
            }
        } catch (Exception e) {
            Global.sout("something crashed while getting the text >>>>>>>>>>>>>>", e.getMessage());
        }
        return newsDetails.trim();
    }


    private void callLiveMatchesAPI() {
        FixturesAPIController fixturesAPIController = new FixturesAPIController(context, Constants.LIVEMATHCES);
        fixturesAPIController.callFixturesAPI(new AppInterfaces.FixturesInterface() {
            @Override
            public void getAllMatchesData(FixturesResponseModel fixturesResponseModel) {
                setLiveMatchesList(fixturesResponseModel);
            }
        });
    }

    private void setLiveMatchesList(FixturesResponseModel fixturesResponseModel) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        rcl_livematches.setLayoutManager(manager);
        FixturesAdapter adapter = new FixturesAdapter(context, Global.filterMatchesList(fixturesResponseModel));
        rcl_livematches.setAdapter(adapter);
    }

    private void initUI(ViewGroup container) {
        context = HomeFragment.this.getContext();
        rcl_livematches = container.findViewById(R.id.rcl_livematches);
        rcl_trendingnews = container.findViewById(R.id.rcl_trendingnews);
        rcl_featurednews = container.findViewById(R.id.rcl_featurednews);
    }
}