package com.example.livecrickettvscores.Activities.Fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livecrickettvscores.Activities.Adapters.FeaturedNewsAdapter;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.NewsListResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.databinding.FragmentPlayerNewsBinding;
import com.example.livecrickettvscores.databinding.SelectednewslayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jsoup.select.Elements;

import java.util.ArrayList;


public class PlayerNewsFragment extends Fragment {

    Integer playerID;
    FragmentPlayerNewsBinding binding;
    String playerURL;

    public PlayerNewsFragment(String playerURL) {
        this.playerURL = playerURL;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlayerNewsBinding.inflate(LayoutInflater.from(getContext()), container, false);
        //callPlayerNewsAPI();

        AppAsyncTasks.CallClickedPlayerDetails getPlayerNews = new AppAsyncTasks.CallClickedPlayerDetails(playerURL, requireContext(), new AppInterfaces.WebScrappingInterface() {
            @Override
            public void getScrapedDocument(Elements document) {
                setUpNewsList(filterNewsModel(document.select("div.cb-col.cb-tpc-hdr.cb-col-100").get(0).getElementsByClass("cb-col cb-col-50 cb-plyr-artcl cb-pos-rel")));
            }
        });
        getPlayerNews.execute();

        return binding.getRoot();
    }

    private NewsListResponseModel filterNewsModel(Elements select) {
        NewsListResponseModel model = new NewsListResponseModel();

        ArrayList<NewsListResponseModel.NewsListDTO> newsListDTOArrayList = new ArrayList<>();

        for (int i = 0; i < select.size(); i++) {
            NewsListResponseModel.NewsListDTO.StoryDTO storyDTO = new NewsListResponseModel.NewsListDTO.StoryDTO();
            NewsListResponseModel.NewsListDTO singleDTO = new NewsListResponseModel.NewsListDTO();
            storyDTO.setImageURL("https://www.cricbuzz.com" + select.get(i).select("div.cb-col.cb-col-33.cb-pos-rel").select("img").attr("src"));
            storyDTO.setHline(select.get(i).select("a").attr("title"));
            storyDTO.setSource("https://www.cricbuzz.com" + select.get(i).select("a").attr("href"));
            storyDTO.setPubTime(select.get(i).select("div.cb-col.cb-pht-wrp.cb-col-67").select("div.text-gray.cb-font-12").text());
            singleDTO.setStory(storyDTO);
            newsListDTOArrayList.add(singleDTO);
        }
        model.setNewsList(newsListDTOArrayList);


        return model;
    }


    private void setUpNewsList(NewsListResponseModel newsListResponseModel) {
        binding.rclPlayernews.setLayoutManager(Global.getManagerWithOrientation(getContext(), RecyclerView.VERTICAL));
        FeaturedNewsAdapter adapter = new FeaturedNewsAdapter(1, getContext(), newsListResponseModel.getNewsList(), new AppInterfaces.NewsAdapterClick() {
            @Override
            public void getClickedNewsID(Integer newsID) {
                AppAsyncTasks.CallNewsDetails callNewsDetails = new AppAsyncTasks.CallNewsDetails(newsListResponseModel.getNewsList().get(newsID).getStory().getSource(), requireContext(), new AppInterfaces.WebScrappingInterface() {
                    @Override
                    public void getScrapedDocument(Elements document) {
                        openBottomSheetOfNewsDetails(newsListResponseModel.getNewsList().get(newsID).getStory().getSource(), document.select("div.cb-col.cb-col-100.cb-bg-white"));
                    }
                });
                callNewsDetails.execute();
            }
        });
        binding.rclPlayernews.setAdapter(adapter);
    }


    private void openBottomSheetOfNewsDetails(String source, Elements newsElements) {
        BottomSheetDialog btms = new BottomSheetDialog(getContext());

        try {
            Elements newsdetailsElements = newsElements.select("section.cb-spt-news-dtl-itms.cb-sptlt-sctn").nextAll();
            String newsDetails = "";
            SelectednewslayoutBinding binding = SelectednewslayoutBinding.inflate(LayoutInflater.from(getContext()), null, false);
            btms.setContentView(binding.getRoot());
            Glide.with(getContext()).load("https://www.cricbuzz.com" + newsElements.select("section.cb-news-img-section").select("img").attr("src")).into(binding.newsImage);
            Glide.with(getContext()).load("https://www.cricbuzz.com" + newsElements.select("div.cb-nws-sub-txt").select("img").attr("src")).into(binding.ivUploader);
            binding.tvUploadername.setText(newsElements.select("div.cb-nws-sub-txt").select("div.cb-spt-athr").text());
            binding.tvNewsheader.setText(newsElements.select("div.cb-sptlt-hdr").select("h1.spt-nws-dtl-hdln").text());
            binding.tvUploadername.setText(newsElements.select("div.cb-sptlt-hdr").select("div.cb-nws-sub-txt").select("div.cb-spt-athr").text());
            binding.tvAuthor.setVisibility(View.GONE);
            for (int i = 0; i < newsdetailsElements.size(); i++) {
                newsDetails = newsDetails + newsdetailsElements.get(i).select("p.cb-nws-para").text() + "\n";
            }
            binding.tvNewsdetails.setText(newsDetails.trim());


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
                        ClipData clipData = ClipData.newPlainText("text", source);
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(getContext(), "Link is copied to clipboard", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Global.sout("Clipboard exception", e.getMessage());
                    }
                }
            });

            binding.ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  /*Create an ACTION_SEND Intent*/
                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    /*This will be the actual content you wish you share.*/
                    /*The type of the content is text, obviously.*/
                    intent.setType("text/plain");
                    /*Applying information Subject and Body.*/
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, source);
                    /*Fire!*/
                    startActivity(Intent.createChooser(intent, source));

                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        btms.show();
    }
}
