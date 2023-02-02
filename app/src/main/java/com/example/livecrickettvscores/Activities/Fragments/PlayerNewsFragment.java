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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livecrickettvscores.Activities.APIControllers.HomeFragmentAPIController;
import com.example.livecrickettvscores.Activities.Adapters.FeaturedNewsAdapter;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.NewsDetailsResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.NewsListResponseModel;
import com.example.livecrickettvscores.Activities.Utils.DateUtil;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.databinding.FragmentPlayerNewsBinding;
import com.example.livecrickettvscores.databinding.SelectednewslayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class PlayerNewsFragment extends Fragment {

    Integer playerID;
    FragmentPlayerNewsBinding binding;

    public PlayerNewsFragment(Integer playerID) {
        this.playerID = playerID;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlayerNewsBinding.inflate(LayoutInflater.from(getContext()), container, false);
        callPlayerNewsAPI();
        return binding.getRoot();
    }

    private void callPlayerNewsAPI() {
        HomeFragmentAPIController controller = new HomeFragmentAPIController(playerID, getContext());
        controller.callPlayerNewsAPI(new AppInterfaces.NewsInterface() {
            @Override
            public void getNewsList(NewsListResponseModel newsListResponseModel) {
                setUpNewsList(newsListResponseModel);
            }
        });
    }

    private void setUpNewsList(NewsListResponseModel newsListResponseModel) {
        binding.rclPlayernews.setLayoutManager(Global.getManagerWithOrientation(getContext(), RecyclerView.VERTICAL));
        FeaturedNewsAdapter adapter = new FeaturedNewsAdapter(getContext(), newsListResponseModel.getNewsList(), new AppInterfaces.NewsAdapterClick() {
            @Override
            public void getClickedNewsID(Integer newsID) {
                callNewsDetailsAPI(newsID);
            }
        });
        binding.rclPlayernews.setAdapter(adapter);
    }

    private void callNewsDetailsAPI(Integer newsID) {
        HomeFragmentAPIController homeFragmentAPIController = new HomeFragmentAPIController(0, getContext());//TODO no category was needed so 0 is used
        homeFragmentAPIController.callNewsDetailsAPI(new AppInterfaces.NewsDetailInterface() {
            @Override
            public void getNewsDetail(NewsDetailsResponseModel newsDetailsResponseModel) {
                openBottomSheetOfNewsDetails(newsDetailsResponseModel);
            }
        }, newsID);
    }

    private void openBottomSheetOfNewsDetails(NewsDetailsResponseModel newsDetailsResponseModel) {
        if (newsDetailsResponseModel.getContent() != null && newsDetailsResponseModel.getContent().size() > 0) {
            BottomSheetDialog btms = new BottomSheetDialog(getContext());
            SelectednewslayoutBinding binding = SelectednewslayoutBinding.inflate(LayoutInflater.from(getContext()), null, false);
            btms.setContentView(binding.getRoot());
            Glide.with(getContext()).load(Global.getTheImage(getContext(), String.valueOf(newsDetailsResponseModel.getId()))).into(binding.newsImage);
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
                        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("text", newsDetailsResponseModel.getAppIndex().getWebURL());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(getContext(), "Link is copied to clipboard", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Global.sout("Clipboard exception", e.getMessage());
                    }
                }
            });

            btms.show();
        }
    }
}