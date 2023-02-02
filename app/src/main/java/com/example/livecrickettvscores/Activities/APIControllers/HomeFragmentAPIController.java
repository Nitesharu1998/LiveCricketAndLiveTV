package com.example.livecrickettvscores.Activities.APIControllers;

import android.content.Context;
import android.widget.Toast;

import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.GetAPIInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.NewsDetailsResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.NewsListResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.RetroFit_APIClient;
import com.example.livecrickettvscores.Activities.Utils.ConstantsMessages;
import com.example.livecrickettvscores.Activities.Utils.EncryptionUtils;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentAPIController {
    Integer categoryID;
    Context context;
    Global global;

    public HomeFragmentAPIController(Integer categoryID, Context context) {
        this.categoryID = categoryID;
        this.context = context;
        this.global = new Global(context);
    }

    public void callFeturedNewsAPI(AppInterfaces.NewsInterface newsInterface) {
        global.showProgressDialog(context, ConstantsMessages.PLEASE_WAIT);
        GetAPIInterfaces apiInterfaces = RetroFit_APIClient.getInstance().getClient(context, EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_BASE)), EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_KEY)), EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_HOST))).create(GetAPIInterfaces.class);
        Call<NewsListResponseModel> call = apiInterfaces.getNewsList(categoryID);
        call.enqueue(new Callback<NewsListResponseModel>() {
            @Override
            public void onResponse(Call<NewsListResponseModel> call, Response<NewsListResponseModel> response) {
                global.hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    newsInterface.getNewsList(response.body());
                }
            }

            @Override
            public void onFailure(Call<NewsListResponseModel> call, Throwable t) {
                global.hideProgressDialog();
                Toast.makeText(context, ConstantsMessages.SomethingWentWrong, Toast.LENGTH_SHORT).show();
                Global.sout("newslist api failure", t.getLocalizedMessage());
            }
        });
    }

    public void callNewsDetailsAPI(AppInterfaces.NewsDetailInterface newsInterface, int newsID) {
        global.showProgressDialog(context, ConstantsMessages.PLEASE_WAIT);
        GetAPIInterfaces apiInterfaces = RetroFit_APIClient.getInstance().getClient(context, EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_BASE)), EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_KEY)), EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_HOST))).create(GetAPIInterfaces.class);
        Call<NewsDetailsResponseModel> call = apiInterfaces.getNewsDetails(newsID);
        call.enqueue(new Callback<NewsDetailsResponseModel>() {
            @Override
            public void onResponse(Call<NewsDetailsResponseModel> call, Response<NewsDetailsResponseModel> response) {
                global.hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    newsInterface.getNewsDetail(response.body());
                }
            }

            @Override
            public void onFailure(Call<NewsDetailsResponseModel> call, Throwable t) {
                global.hideProgressDialog();
                Toast.makeText(context, ConstantsMessages.SomethingWentWrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callPlayerNewsAPI(AppInterfaces.NewsInterface newsInterface) {
        global.showProgressDialog(context, ConstantsMessages.PLEASE_WAIT);
        GetAPIInterfaces apiInterfaces = RetroFit_APIClient.getInstance().getClient(context, EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_BASE)), EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_KEY)), EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_HOST))).create(GetAPIInterfaces.class);
        Call<NewsListResponseModel> call = apiInterfaces.getPlayerNews(categoryID);
        call.enqueue(new Callback<NewsListResponseModel>() {
            @Override
            public void onResponse(Call<NewsListResponseModel> call, Response<NewsListResponseModel> response) {
                global.hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    newsInterface.getNewsList(response.body());
                }
            }

            @Override
            public void onFailure(Call<NewsListResponseModel> call, Throwable t) {
                global.hideProgressDialog();
                Toast.makeText(context, ConstantsMessages.SomethingWentWrong, Toast.LENGTH_SHORT).show();
                Global.sout("newslist api failure", t.getLocalizedMessage());
            }
        });
    }
}
