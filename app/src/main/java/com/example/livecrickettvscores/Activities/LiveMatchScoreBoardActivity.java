package com.example.livecrickettvscores.Activities;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.livecrickettvscores.Activities.AppInterface.AppInterfaces;
import com.example.livecrickettvscores.Activities.Retrofit.AppAsyncTasks;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.FixturesResponseModel;
import com.example.livecrickettvscores.Activities.Utils.Constants;
import com.example.livecrickettvscores.Activities.Utils.Global;
import com.example.livecrickettvscores.R;
import com.example.livecrickettvscores.databinding.ActivityLiveMatchScoreBoardBinding;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LiveMatchScoreBoardActivity extends AppCompatActivity {
    FixturesResponseModel.MatchesDTO matchDetails = Constants.matchDTO;
    Activity activity;
    ActivityLiveMatchScoreBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_live_match_score_board);
        activity = LiveMatchScoreBoardActivity.this;

        if (!Global.isClassNull(matchDetails)) {
            callLiveMatchDetails(true);
        }

    }

    private void initiateDelayForRefresh() {
        Thread mSplashThread = new Thread() {
            @Override
            public void run() {
                synchronized (this) {
                    try {
                        wait(30000);
                        callLiveMatchDetails(false);
                    } catch (InterruptedException e) {
                        Global.sout("refresh error", e.getLocalizedMessage());
                    }
                }
            }

        };
        mSplashThread.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void callLiveMatchDetails(boolean showLoader) {
        AppAsyncTasks.GetLiveScoreBoard getLiveScoreBoard = new AppAsyncTasks.GetLiveScoreBoard(showLoader, Constants.ESPNBaseURL + matchDetails.getMatchScoreLink(), activity, new AppInterfaces.WebScrappingInterface() {
            @Override
            public void getScrapedDocument(Elements document) {
                if (!document.isEmpty()) {
                    setUpTopUi(document.get(0));
                    setUpScores(document.get(1));
                    initiateDelayForRefresh();
                }

            }
        });
        getLiveScoreBoard.execute();
    }

    private void setUpTopUi(Element element) {
        binding.tvMatchtitle.setText(matchDetails.getMatchTitle());
        binding.tvMatchstatus.setText(element.select("p[class=ds-text-tight-m ds-font-regular ds-truncate ds-text-typo]").select("span").text());
        Elements teamScoreElements = element.select("div[class=ci-team-score ds-flex ds-justify-between ds-items-center ds-text-typo ds-mb-2]");
        System.out.println("teamScoreElements >>>>>>>\n" + teamScoreElements.size());

        for (int i = 0; i < teamScoreElements.size(); i++) {
            if (i == 0) {
                binding.tvMatch1team.setText(teamScoreElements.get(0).select("div[class=ds-flex ds-items-center ds-min-w-0 ds-mr-1]").select("a").text());
                Glide.with(activity).load(Global.getFlagOfCountry(teamScoreElements.get(0).select("div[class=ds-flex ds-items-center ds-min-w-0 ds-mr-1]").select("a").text())).into(binding.ivTeam1);
                binding.tvMatch1score.setText(teamScoreElements.get(0).select("span[class=ds-text-compact-s ds-mr-0.5]").text() + teamScoreElements.get(0).select("strong").text());
            } else {
                binding.tvMatch2team.setText(teamScoreElements.get(1).select("div[class=ds-flex ds-items-center ds-min-w-0 ds-mr-1]").select("a").text());
                Glide.with(activity).load(teamScoreElements.get(1).select("div[class=ds-flex ds-items-center ds-min-w-0 ds-mr-1]").select("a").text()).into(binding.ivTeam2);
                binding.tvMatch2score.setText(teamScoreElements.get(1).select("span[class=ds-text-compact-s ds-mr-0.5]").text() + teamScoreElements.get(0).select("strong").text());

            }
        }

    }

    private void setUpScores(Element element) {
        Elements batmanAndBowlerElements = element.select("table[class=ds-w-full ds-table ds-table-md ds-table-auto ]").select("tbody[class=ds-text-right]");
        Elements batsmanElements = batmanAndBowlerElements.get(0).select("tr");
        Elements bowlerElements = batmanAndBowlerElements.get(1).select("tr");

        setUpBatsmanUI(batsmanElements);
        setUpBowlerUI(bowlerElements);
    }

    private void setUpBowlerUI(Elements bowlerElements) {
        Elements bowlerOneScoreElements = bowlerElements.get(0).getAllElements();
        Elements bowlerTwoScoreElements = bowlerElements.get(1).getAllElements();

        Elements bowlerOneScores = bowlerOneScoreElements.select("td[class=ds-min-w-max]");
        Elements bowlerTwoScores = bowlerTwoScoreElements.select("td[class=ds-min-w-max]");

        binding.bowler1.setText(bowlerOneScoreElements.select("a[class=ds-inline-flex ds-items-start ds-leading-none]").select("span").text());
        binding.bowler1Over.setText(bowlerOneScores.get(0).text());
        binding.bowler1Maiden.setText(bowlerOneScores.get(1).text());
        binding.bowler1Runs.setText(bowlerOneScores.get(2).text());
        binding.bowler1Wikets.setText(bowlerOneScoreElements.select("td[class=ds-min-w-max ds-font-bold]").text());
        binding.bowler1Economy.setText(bowlerOneScores.get(3).text());
        binding.bowler2.setText(bowlerTwoScoreElements.select("a[class=ds-inline-flex ds-items-start ds-leading-none]").select("span").text());

        binding.bowler2.setText(bowlerTwoScoreElements.select("a[class=ds-inline-flex ds-items-start ds-leading-none]").select("span").text());
        binding.bowler2Over.setText(bowlerTwoScores.get(0).text());
        binding.bowler2Maiden.setText(bowlerTwoScores.get(1).text());
        binding.bowler2Runs.setText(bowlerTwoScores.get(2).text());
        binding.bowler2Wikets.setText(bowlerTwoScoreElements.select("td[class=ds-min-w-max ds-font-bold]").text());
        binding.bowler2Economy.setText(bowlerTwoScores.get(3).text());
        binding.bowler2.setText(bowlerTwoScoreElements.select("a[class=ds-inline-flex ds-items-start ds-leading-none]").select("span").text());
    }

    private void setUpBatsmanUI(Elements batsmanElements) {
        Elements batsmanOneScoreElements = batsmanElements.get(0).getAllElements();
        Elements batsmanTwoScoreElements = batsmanElements.get(1).getAllElements();
        Elements batsmanOneScores = batsmanOneScoreElements.select("td[class=ds-min-w-max]");
        Elements batsmanTwoScores = batsmanTwoScoreElements.select("td[class=ds-min-w-max]");
        binding.batman1.setText(batsmanOneScoreElements.select("a[class=ds-inline-flex ds-items-start ds-leading-none]").select("span").text());
        binding.batman1Runs.setText(batsmanOneScores.get(0).select("strong").text());
        binding.batman1Balls.setText(batsmanOneScores.get(1).text());
        binding.batman1Fours.setText(batsmanOneScores.get(2).text());
        binding.batman1Six.setText(batsmanOneScores.get(3).text());
        binding.batman1Strike.setText(batsmanOneScores.get(4).text());

        binding.batman2.setText(batsmanTwoScoreElements.select("a[class=ds-inline-flex ds-items-start ds-leading-none]").select("span").text());
        binding.batman2Runs.setText(batsmanTwoScores.get(0).select("strong").text());
        binding.batman2Balls.setText(batsmanTwoScores.get(1).text());
        binding.batman2Fours.setText(batsmanTwoScores.get(2).text());
        binding.batman2Six.setText(batsmanTwoScores.get(3).text());
        binding.batman2Strike.setText(batsmanTwoScores.get(4).text());

    }


}


