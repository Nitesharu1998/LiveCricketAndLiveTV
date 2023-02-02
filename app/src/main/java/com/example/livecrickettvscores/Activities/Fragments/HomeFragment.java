package com.example.livecrickettvscores.Activities.Fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.example.livecrickettvscores.Activities.Utils.DateUtil;
import com.example.livecrickettvscores.Activities.Utils.Global;
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();
        rcl_livematches = view.findViewById(R.id.rcl_livematches);
        rcl_trendingnews = view.findViewById(R.id.rcl_trendingnews);
        rcl_featurednews = view.findViewById(R.id.rcl_featurednews);
        callLiveMatchesAPI();
        callNewsAPI();
        Global.sout("Home fragment ", "home fragment initiated");
        return view;
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
        FeaturedNewsAdapter featuredNewsAdapter = new FeaturedNewsAdapter(context, filterNewsList(newsListResponseModel.getNewsList()), new AppInterfaces.NewsAdapterClick() {
            @Override
            public void getClickedNewsID(Integer newsID) {
                callNewsDetailsAPI(newsID);
            }
        });
        rcl_featurednews.setAdapter(featuredNewsAdapter);
    }

    private ArrayList<NewsListResponseModel.NewsListDTO> filterNewsList(ArrayList<NewsListResponseModel.NewsListDTO> newsList) {
        ArrayList<NewsListResponseModel.NewsListDTO> newsListFiltered = new ArrayList<>();
        for (int i = 0; i < newsList.size(); i++) {
            if (!Global.isClassNull(newsList.get(i).getStory())) {
                newsListFiltered.add(newsList.get(i));
            }
        }

        return newsListFiltered;
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
            binding.tvUploadername.setText(newsDetailsResponseModel.getCoverImage().getSource());
            binding.tvNewsdetails.setText(Global.getTextFromDataModel(newsDetailsResponseModel.getContent()));
            binding.tvNewsheader.setText(newsDetailsResponseModel.getHeadline());
            binding.tvUploadtime.setText(DateUtil.getDateFromSeconds(Long.parseLong(newsDetailsResponseModel.getPublishTime())));
            binding.tvAuthor.setText(newsDetailsResponseModel.getAuthors().get(0).getName());

            binding.ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btms.dismiss();
                }
            });
            binding.ivLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("text", newsDetailsResponseModel.getAppIndex().getWebURL());
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
        FixturesAPIController fixturesAPIController = new FixturesAPIController(context, Constants.LIVEMATHCES);
        fixturesAPIController.callFixturesAPI(new AppInterfaces.FixturesInterface() {
            @Override
            public void getAllMatchesData(FixturesResponseModel fixturesResponseModel) {
                setLiveMatchesList(fixturesResponseModel);
            }
        });
    }

    private void setLiveMatchesList(FixturesResponseModel fixturesResponseModel) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        rcl_livematches.setLayoutManager(manager);
        FixturesAdapter adapter = new FixturesAdapter(context, Global.filterMatchesList(fixturesResponseModel));
        rcl_livematches.setAdapter(adapter);
    }

}