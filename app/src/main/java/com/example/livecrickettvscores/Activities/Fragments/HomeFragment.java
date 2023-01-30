package com.example.livecrickettvscores.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.livecrickettvscores.Activities.APIControllers.MatchesAPIController;
import com.example.livecrickettvscores.Activities.Adapters.LiveMatchesAdapter;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;

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
        Global.sout("Home fragment ", "home fragment initiated");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void callLiveMatchesAPI() {
        MatchesAPIController controller = new MatchesAPIController();
       /* controller.calLiveMatchesAPI(context, new AppInterfaces.LiveMatchInterface() {
            @Override
            public void getLiveMatchesResponseModel(FixturesResponseModel liveMatchesResponseModel) {
                if (liveMatchesResponseModel.getData().size() > 0){
                    setLiveMatchesList(liveMatchesResponseModel);
                }
            }
        });
*/

    }

    private void setLiveMatchesList(FixturesResponseModel fixturesResponseModel) {
        LinearLayoutManager manager= new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        /*LiveMatchesAdapter adapter = new LiveMatchesAdapter(fixturesResponseModel.getData());*/
    }

    private void initUI(ViewGroup container) {
        context = HomeFragment.this.getContext();
        rcl_livematches = container.findViewById(R.id.rcl_livematches);
        rcl_trendingnews = container.findViewById(R.id.rcl_trendingnews);
        rcl_featurednews = container.findViewById(R.id.rcl_featurednews);
    }
}