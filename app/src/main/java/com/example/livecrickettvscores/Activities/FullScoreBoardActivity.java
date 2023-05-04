package com.example.livecrickettvscores.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.livecrickettvscores.Activities.Adapters.FullScoreBoardAdapter;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FullScoreBoardResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.Activities.Utils.StringUtils;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.ActivityFullScoreBoardBinding;
import com.google.android.material.tabs.TabLayout;

import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class FullScoreBoardActivity extends AppCompatActivity {
    ActivityFullScoreBoardBinding binding;
    FixturesResponseModel.MatchesDTO matchesDTO = Constants.matchDTO;
    FullScoreBoardResponseModel fullScoreBoardResponseModel;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_full_score_board);
        activity = FullScoreBoardActivity.this;
        setTabs();
        setTopUI();
        initListeners();
        if (!StringUtils.isNull(matchesDTO.getMatchScoreLink())) {
            Global.sout("matchlink", Constants.ESPNBaseURL + Constants.matchDTO.getMatchScoreLink());
            AppAsyncTasks.GetFinishedScoreBoard getFinishedScoreBoard = new AppAsyncTasks.GetFinishedScoreBoard(true, Constants.ESPNBaseURL + matchesDTO.getMatchScoreLink(), activity, new AppInterfaces.WebScrappingInterface() {
                @Override
                public void getScrapedDocument(Elements scoreBoardElements) {
                    setManOfTheMatch(Constants.ManOfTheMatchElements);
                    if (!scoreBoardElements.isEmpty()) {

                        fullScoreBoardResponseModel = setUpResponseModel(scoreBoardElements);
                        setUpFullScoreBoardList(getResponseModelForTeams(true));
                    } else {
                        Toast.makeText(activity, "The score data is not available now...", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }

                }
            });
            getFinishedScoreBoard.execute();
        }


    }

    private void initListeners() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.tabTeamNames.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        setUpFullScoreBoardList(getResponseModelForTeams(true));
                        break;
                    case 1:
                        setUpFullScoreBoardList(getResponseModelForTeams(false));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setManOfTheMatch(Elements manOfTheMatchElements) {
        if (!manOfTheMatchElements.isEmpty()) {
            String MOMScore = manOfTheMatchElements.select("span[class=ds-text-tight-s ds-font-regular]").text();
            if (MOMScore.contains("&")) {
                String[] momSplit = MOMScore.split("&");
                binding.tvMOMPlayerScore.setText(momSplit[0] + "\n&\n" + momSplit[1]);

            } else {
                binding.tvMOMPlayerScore.setText(manOfTheMatchElements.select("span[class=ds-text-tight-s ds-font-regular]").text());

            }
            Global.sout("manOfTheMatchElements\n", manOfTheMatchElements);
            binding.tvMOMPlayer.setText(StringUtils.toCamelCase(manOfTheMatchElements.select("div[class=ds-relative]").select("img").attr("alt").replace("-", " ")));

        }
    }

    private void setTopUI() {
        binding.tvMatch1score.setText(Constants.matchDTO.getTeamOneScore());
        binding.tvMatch2score.setText(Constants.matchDTO.getTeamTwoScore());
        binding.tvMatch1team.setText(Constants.matchDTO.getTeamOne());
        binding.tvMatch2team.setText(Constants.matchDTO.getTeamTwo());
        binding.tvMatchstatus.setText(Constants.matchDTO.getSession());
        binding.tvMatchtitle.setText(Constants.matchDTO.getMatchTitle());

        Glide.with(getApplicationContext()).load(Global.getFlagOfCountry(true,Constants.matchDTO.getTeamOne())).into(binding.ivTeam1);
        Glide.with(getApplicationContext()).load(Global.getFlagOfCountry(true,Constants.matchDTO.getTeamTwo())).into(binding.ivTeam2);
    }

    private FullScoreBoardResponseModel getResponseModelForTeams(boolean isTeamOne) {
        FullScoreBoardResponseModel responseModel = new FullScoreBoardResponseModel();
        if (fullScoreBoardResponseModel != null) {

            ArrayList<FullScoreBoardResponseModel.MatchInningDTO> inningDTOS = new ArrayList<>();
            FullScoreBoardResponseModel.MatchInningDTO singleInning = new FullScoreBoardResponseModel.MatchInningDTO();
            for (int j = 0; j < fullScoreBoardResponseModel.getMatchInningDTOS().size(); j++) {
                if (isTeamOne) {
                    if (j % 2 == 0) {
                        inningDTOS.add(fullScoreBoardResponseModel.getMatchInningDTOS().get(j));
                    }
                } else if (j % 2 != 0) {
                    inningDTOS.add(fullScoreBoardResponseModel.getMatchInningDTOS().get(j));
                }

            }
            responseModel.setMatchInningDTOS(inningDTOS);
        }
        return responseModel;
    }


    private void setTabs() {
        binding.tabTeamNames.addTab(binding.tabTeamNames.newTab().setText(Constants.matchDTO.getTeamOne()));
        binding.tabTeamNames.addTab(binding.tabTeamNames.newTab().setText(Constants.matchDTO.getTeamTwo()));
    }

    private void setUpFullScoreBoardList(FullScoreBoardResponseModel fullScoreBoardResponseModel) {
        binding.rclScoreMainList.setLayoutManager(Global.getManagerWithOrientation(activity, RecyclerView.VERTICAL));
        FullScoreBoardAdapter adapater = new FullScoreBoardAdapter(activity, fullScoreBoardResponseModel);
        binding.rclScoreMainList.setAdapter(adapater);
    }

    private FullScoreBoardResponseModel setUpResponseModel(Elements scoreBoardElements) {
        FullScoreBoardResponseModel responseModel = new FullScoreBoardResponseModel();
        ArrayList<FullScoreBoardResponseModel.MatchInningDTO> matchInningDTOArrayList = new ArrayList<>();
        for (int i = 0; i < scoreBoardElements.size(); i++) {//2
            Elements battingElements = scoreBoardElements.get(i).select("table[class=ds-w-full ds-table ds-table-md ds-table-auto  ci-scorecard-table]"); //4
            Elements bowlingElements = scoreBoardElements.get(i).select("table[class=ds-w-full ds-table ds-table-md ds-table-auto ]");//4
            FullScoreBoardResponseModel.MatchInningDTO matchInningDTO = new FullScoreBoardResponseModel.MatchInningDTO();
            matchInningDTO.setTeamName(scoreBoardElements.get(i).select("span[class=ds-text-title-xs ds-font-bold ds-capitalize]").text() + " " + scoreBoardElements.get(i).select("span[class=ds-text-compact-xs ds-font-regular]").text());
            matchInningDTO.setTeamFallOfWicket(scoreBoardElements.get(i).select("div[class=ds-text-tight-s ds-font-regular ds-leading-4]").select("span").text());
            matchInningDTO.setTeamTotal(scoreBoardElements.get(i).select("td[class=ds-font-bold ds-bg-fill-content-alternate ds-text-tight-m ds-min-w-max ds-flex ds-items-center !ds-pl-[100px]]").text() + "    " + scoreBoardElements.get(i).select("td[class=ds-font-bold ds-bg-fill-content-alternate ds-text-tight-m ds-min-w-max ds-text-right]").text());
            for (int j = 0; j < battingElements.size(); j++) {
                ArrayList<FullScoreBoardResponseModel.BatsmanDTO> batsmanList = new ArrayList<>();
                Elements singleBatsMan = battingElements.get(j).select("tr");
                for (int k = 1; k < singleBatsMan.size(); k++) {
                    batsmanList.add(getBatsMan(singleBatsMan.get(k).getAllElements()));
                }
                matchInningDTO.setBatsmanDTOS(batsmanList);
            }
            for (int j = 0; j < bowlingElements.size(); j++) {
                ArrayList<FullScoreBoardResponseModel.BowlerDTO> bowlerList = new ArrayList<>();
                Elements singleBowler = bowlingElements.get(j).select("tr");
                System.out.println("bowler element\n" + singleBowler.get(1));
                for (int k = 1; k < singleBowler.size(); k++) {
                    bowlerList.add(getBowler(singleBowler.get(0).getAllElements(), singleBowler.get(k).getAllElements()));
                }
                matchInningDTO.setBowlerDTOS(bowlerList);
            }

            matchInningDTOArrayList.add(matchInningDTO);
        }
        responseModel.setMatchInningDTOS(matchInningDTOArrayList);

        return filterResponseModel(responseModel);
    }
    private FullScoreBoardResponseModel filterResponseModel(FullScoreBoardResponseModel responseModel) {
        FullScoreBoardResponseModel localResponseModel = responseModel;
        ArrayList<FullScoreBoardResponseModel.BatsmanDTO> localBatsmanList;
        ArrayList<FullScoreBoardResponseModel.BowlerDTO> localBowlerList;
        for (int i = 0; i < responseModel.getMatchInningDTOS().size(); i++) {
            localBatsmanList = new ArrayList<>();
            localBowlerList = new ArrayList<>();
            for (int j = 0; j < responseModel.getMatchInningDTOS().get(i).getBatsmanDTOS().size(); j++) {
                if (!StringUtils.isNull(responseModel.getMatchInningDTOS().get(i).getBatsmanDTOS().get(j).getBatsmanName())) {
                    localBatsmanList.add(responseModel.getMatchInningDTOS().get(i).getBatsmanDTOS().get(j));
                }
            }
            for (int j = 0; j < responseModel.getMatchInningDTOS().get(i).getBowlerDTOS().size(); j++) {
                if (!StringUtils.isNull(responseModel.getMatchInningDTOS().get(i).getBowlerDTOS().get(j).getEcon())) {
                    localBowlerList.add(responseModel.getMatchInningDTOS().get(i).getBowlerDTOS().get(j));
                }
            }

            localResponseModel.getMatchInningDTOS().get(i).setBatsmanDTOS(localBatsmanList);
            localResponseModel.getMatchInningDTOS().get(i).setBowlerDTOS(localBowlerList);
        }
        fullScoreBoardResponseModel = localResponseModel;
        return localResponseModel;
    }

    private FullScoreBoardResponseModel.BowlerDTO getBowler(Elements AllElements, Elements singleBowler) {
        Elements AllElementsTest = AllElements.select("th[class=ds-w-0 ds-whitespace-nowrap ds-min-w-max ds-text-right]");

        FullScoreBoardResponseModel.BowlerDTO bowlerDTO = new FullScoreBoardResponseModel.BowlerDTO();
        Elements bowlerScore = singleBowler.select("td[class=ds-w-0 ds-whitespace-nowrap ds-min-w-max ds-text-right]");
        Elements bowlerScoreWicket = singleBowler.select("td[class=ds-w-0 ds-whitespace-nowrap ds-text-right]").select("strong");
        bowlerDTO.setBowlerName(singleBowler.select("a[class=ds-inline-flex ds-items-start ds-leading-none]").select("span").text());
        for (int i = 0; i < bowlerScore.size(); i++) {
            bowlerDTO.setO(bowlerScore.get(0).text());
            bowlerDTO.setM(bowlerScore.get(1).text());
            bowlerDTO.setR(bowlerScore.get(2).text());
            bowlerDTO.setW(bowlerScoreWicket.text());
            bowlerDTO.setEcon(bowlerScore.get(3).text());
        }
        return bowlerDTO;
    }

    private FullScoreBoardResponseModel.BatsmanDTO getBatsMan(Elements singleBatsman) {
        Elements batsmanScore = singleBatsman.select("td[class=ds-w-0 ds-whitespace-nowrap ds-min-w-max ds-text-right]");
        FullScoreBoardResponseModel.BatsmanDTO batsmanDTO = new FullScoreBoardResponseModel.BatsmanDTO();
        batsmanDTO.setBatsmanName(singleBatsman.select("td[class=ds-w-0 ds-whitespace-nowrap ds-min-w-max ds-flex ds-items-center ds-border-line-primary ci-scorecard-player-notout]").select("a").attr("title"));
        if (StringUtils.isNull(batsmanDTO.getBatsmanName())) {
            batsmanDTO.setBatsmanName(singleBatsman.select("td[class=ds-w-0 ds-whitespace-nowrap ds-min-w-max ds-flex ds-items-center]").select("a").select("span[class=ds-text-tight-s ds-font-medium ds-text-typo ds-underline ds-decoration-ui-stroke hover:ds-text-typo-primary hover:ds-decoration-ui-stroke-primary ds-block]").text());
        }
        batsmanDTO.setStatus(singleBatsman.select("td.ds-min-w-max").select("span").text());
        for (int i = 0; i < batsmanScore.size(); i++) {
            if (batsmanScore.size() == 6) {
                batsmanDTO.setR(batsmanScore.get(0).select("strong").text());
                batsmanDTO.setB(batsmanScore.get(1).text());
                batsmanDTO.setM(batsmanScore.get(2).select("strong").text());
                batsmanDTO.setFours(batsmanScore.get(3).text());
                batsmanDTO.setSixes(batsmanScore.get(4).text());
                batsmanDTO.setSR(batsmanScore.get(5).text());
            } else {
                batsmanDTO.setR(batsmanScore.get(0).select("strong").text());
                batsmanDTO.setB(batsmanScore.get(1).text());
                batsmanDTO.setM("-");
                batsmanDTO.setFours(batsmanScore.get(2).text());
                batsmanDTO.setSixes(batsmanScore.get(3).text());
                batsmanDTO.setSR(batsmanScore.get(4).text());
            }
        }


        return batsmanDTO;
    }

    private String filterText(String singleSplitString) {
        String[] words = singleSplitString.split(", ");
        Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
        return String.join(", ", uniqueWords);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}