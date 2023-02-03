package com.example.livecrickettvscores.Activities.APIControllers;

import android.content.Context;
import android.widget.Toast;

import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.GetAPIInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.RetroFit_APIClient;
import com.example.livecrickettvscores.Activities.Utils.ConstantsMessages;
import com.example.livecrickettvscores.Activities.Utils.EncryptionUtils;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FixturesAPIController {
    Context context;
    String matchtype;
    Global global;

    public FixturesAPIController(Context context, String matchtype) {
        this.context = context;
        this.matchtype = matchtype;
        this.global = new Global(context);
    }

    public void callFixturesAPI(AppInterfaces.FixturesInterface fixturesInterface) {
        global.showProgressDialog(context, ConstantsMessages.PLEASE_WAIT);
        GetAPIInterfaces apiInterfaces = RetroFit_APIClient.getInstance().getClient(context, EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_BASE)), EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_KEY)), EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_HOST))).create(GetAPIInterfaces.class);
        Call<FixturesResponseModel> callFixtures = apiInterfaces.getFixturesAsPerType(matchtype);
        callFixtures.enqueue(new Callback<FixturesResponseModel>() {
            @Override
            public void onResponse(Call<FixturesResponseModel> call, Response<FixturesResponseModel> response) {
                global.hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    fixturesInterface.getAllMatchesData(response.body());
                }

            }

            @Override
            public void onFailure(Call<FixturesResponseModel> call, Throwable t) {
                Global.sout("Fixtures api failure", t.getMessage());
                global.hideProgressDialog();
                Toast.makeText(context, ConstantsMessages.SomethingWentWrong, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
