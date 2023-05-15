package com.example.livecrickettvscores.Activities.Fragments;

import static com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdUtils.loadInitialInterstitialAds;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livecrickettvscores.Activities.Adapters.FeaturedNewsAdapter;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.NewsListResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;
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
    public void onResume() {
        super.onResume();
        loadInitialInterstitialAds(requireActivity());
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlayerNewsBinding.inflate(inflater, container, false);
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
                        openBottomSheetOfNewsDetails(newsListResponseModel.getNewsList().get(newsID).getStory().getPubTime(),newsListResponseModel.getNewsList().get(newsID).getStory().getSource(), document.select("div.cb-col.cb-col-100.cb-bg-white"));
                    }
                });
                callNewsDetails.execute();
            }
        });
        binding.rclPlayernews.setAdapter(adapter);
    }


    private void openBottomSheetOfNewsDetails(String date,String source, Elements newsElements) {
        BottomSheetDialog btms = new BottomSheetDialog(requireContext());
        try {
            Elements newsdetailsElements = newsElements.select("section[class=cb-spt-news-dtl-itms cb-sptlt-sctn]");
            if (newsdetailsElements.isEmpty()){
                newsdetailsElements = newsElements.select("section[class=cb-nws-dtl-itms]");
            }

            StringBuilder newsDetails = new StringBuilder();
            SelectednewslayoutBinding binding = SelectednewslayoutBinding.inflate(LayoutInflater.from(requireContext()), null, false);
            btms.setContentView(binding.getRoot());
            Glide.with(requireContext()).load("https://www.cricbuzz.com" + newsElements.select("section.cb-news-img-section").select("meta").attr("itemprop","url")).error(R.drawable.news_default).into(binding.newsImage);
            Glide.with(requireContext()).load("https://www.cricbuzz.com" + newsElements.select("div.cb-nws-sub-txt").select("img").attr("src")).error(R.drawable.defaultavatar).into(binding.ivUploader);
            binding.tvUploadername.setText(newsElements.select("div.cb-nws-sub-txt").select("span").attr("itemprop", "name").text()/*.select("div.cb-spt-athr").text()*/);
            binding.tvNewsheader.setText(newsElements.select("h1[class=nws-dtl-hdln]").text());
            binding.tvUploadtime.setText(date);
            binding.tvAuthor.setText(Global.filterText(newsElements.select("div[class=cb-nws-sub-txt]").select("a[class=cb-text-link]").select("span").attr("itemprop","name").text()," ", " "));
            binding.tvAuthor.setVisibility(View.VISIBLE);
            for (int i = 0; i < newsdetailsElements.size(); i++) {
                newsDetails.append(newsdetailsElements.get(i).select("p.cb-nws-para").text()).append("\n");
            }
            binding.tvNewsdetails.setText(newsDetails.toString().trim());

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
                        ClipboardManager clipboardManager = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("text", source);
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(getContext(), "Link is copied to clipboard", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Global.sout("Clipboard exception", e.getMessage());
                    }
                }
            });

            binding.ivShare.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, source);
                startActivity(Intent.createChooser(intent, source));
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        btms.show();
    }
}
