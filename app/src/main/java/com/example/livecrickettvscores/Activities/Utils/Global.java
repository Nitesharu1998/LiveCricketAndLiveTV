package com.example.livecrickettvscores.Activities.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.livecrickettvscores.Activities.FirebaseADHandlers.AdsJsonPOJO;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.CricketFlagsResponseModel;
import com.example.livecrickettvscores.Activities.Retrofit.ResponseModel.NewsDetailsResponseModel;
import com.example.livecrickettvscores.BuildConfig;
import com.example.livecrickettvscores.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public class Global {

    ProgressDialog progressDialog;
    private Context context;

    public Global(Context context) {
        this.context = context;
    }

    public static void showCustomStaticToast(Context context, String message) {

        if (context != null && !InputUtils.isNull(message)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static Date returnDate(String putDate) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            date = sdf.parse(putDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }

    public static String getCurrentDateandTime() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
    }





    public static void DsaResponseModel() {

    }

    public static String toCamelCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char previousChar = inputString.charAt(i - 1);
            if (previousChar == ' ') {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result = result + currentCharToUpperCase;
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
        }
        return result;
    }

    public static String toUpperCase(String inputString) {
        String result = "";
        for (int i = 0; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char currentCharToUpperCase = Character.toUpperCase(currentChar);
            result = result + currentCharToUpperCase;
        }
        return result;
    }

    public static String toLowerCase(String inputString) {
        String result = "";
        for (int i = 0; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char currentCharToLowerCase = Character.toLowerCase(currentChar);
            result = result + currentCharToLowerCase;
        }
        return result;
    }

    public static String toSentenceCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        boolean terminalCharacterEncountered = false;
        char[] terminalCharacters = {'.', '?', '!'};
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            if (terminalCharacterEncountered) {
                if (currentChar == ' ') {
                    result = result + currentChar;
                } else {
                    char currentCharToUpperCase = Character.toUpperCase(currentChar);
                    result = result + currentCharToUpperCase;
                    terminalCharacterEncountered = false;
                }
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
            for (int j = 0; j < terminalCharacters.length; j++) {
                if (currentChar == terminalCharacters[j]) {
                    terminalCharacterEncountered = true;
                    break;
                }
            }
        }
        return result;
    }

    public static void iconImage(Bitmap bitmap) {

    }




    public static int getCurrentVersionCode(Context pContext) {
        int currentAppVersion = 0;
        try {
            currentAppVersion = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentAppVersion;
    }

    public static String getCurrentAppVersionName(Context pContext) {
        String versionName = "";
        try {
            PackageInfo packageInfo = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static String getSerialnum(Context pContext) {
        String imeiNo = "";
        try {
            imeiNo = Settings.Secure.getString(pContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imeiNo;
    }

    public static boolean isArrayListNull(ArrayList arrayList) {
        try {
            if (arrayList == null) {
                return true;
            } else if (arrayList != null && arrayList.size() <= 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Elements getListOfPredictions() throws IOException {
        Document document = Jsoup.connect(Constants.PREDICTIONS_URL).get();
        return document.select("div.flex-container");
    }

    public static Elements getPredictionDetails(String predictionURL) throws IOException {
        Document document = Jsoup.connect(predictionURL).get();
        return document.select("div.single-tip");
    }


    public String convertNumberToPrice(String s) {
        Double price = Double.parseDouble(s);
        Locale locale = new Locale("en", "IN");
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        if (checkForApi14()) symbols.setCurrencySymbol("\u20B9"); // Don't use null.
        else symbols.setCurrencySymbol("\u20A8"); // Don't use null.
        formatter.setDecimalFormatSymbols(symbols);
        formatter.setMaximumFractionDigits(0);
        //MessageLogger.PrintMsg(formatter.format(price));
        s = formatter.format(price);
        return s;
    }

    public Boolean checkForApi11() {
        Boolean boolStatus = false;
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            boolStatus = true;
        } else {
            boolStatus = false;
        }
        return boolStatus;
    }

    public Boolean checkForApi14() {
        Boolean boolStatus = false;
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            boolStatus = true;
        } else {
            boolStatus = false;
        }
        return boolStatus;
    }

    public Boolean checkForApi21() {
        Boolean boolStatus = false;
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            boolStatus = true;
        } else {
            boolStatus = false;
        }
        return boolStatus;
    }



    public void showProgressDialog(Activity activity, String msg) {

        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage(msg);
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        try {
            if (progressDialog != null && !progressDialog.isShowing())
                if (!activity.isFinishing()) {
                    progressDialog.show();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProgressDialog(Context context, String msg) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(null);
        progressDialog.setMessage(msg);
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        try {
             /*if (progressDialog != null && !progressDialog.isShowing())
               progressDialog.show();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProgressDialog(Activity activity, String msg, boolean IsCancelable) {

        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle(null);
        progressDialog.setMessage(msg);
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(IsCancelable);

        try {
            if (progressDialog != null && !progressDialog.isShowing())

                if (!activity.isFinishing()) {
                    progressDialog.show();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgressDialog(Activity activity) {

        try {
            if (activity != null && !activity.isFinishing() && progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String formatDate(String currentFormat, String outputFormat, String date) {

        SimpleDateFormat curFormater = new SimpleDateFormat(currentFormat);
        SimpleDateFormat postFormater = new SimpleDateFormat(outputFormat);
        //SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");
        //SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(date);
            date = postFormater.format(dateObj);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public boolean checkValidation(String type, int length) {
        boolean result = false;
        if (type.equals("mobile")) {
            result = length != 10 ? false : true;
        } else if (type.equals("address")) {
            result = length <= 25 ? false : true;
        }

        return result;
    }

    public String checkJsonNullStringValue(JSONObject jsonObject, String key) {
        String value = "";
        try {
            value = jsonObject.isNull(key) ? "" : jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public void showalert_OK(String message, Context context) {

        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                    }
                });
        androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void showAlert_OK_WithTitle(String message, Context context, String title) {
        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
        alertDialogBuilder
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                    }
                });
        androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public interface OnBarcodeDialogSubmitClickListener {
        public void onSubmitButtonClicked(String barcode);
    }

    public static void sout(String TagToString, Object whatToPrint) {
        if (BuildConfig.DEBUG) {
            System.out.println(TagToString + " " + whatToPrint);
        }
    }

   /* public static ArrayList<FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO> filterMatchesList(FixturesResponseModel fixturesResponseModel) {
        ArrayList<FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO.SeriesMatchesDTO.MatchesDTO> matchesDTOArrayList = new ArrayList<>();
        try {

            ArrayList<FixturesResponseModel.TypeMatchesDTO.SeriesAdWrapperDTO> seriesAdWrapperDTOS = new ArrayList<>();
            if (!Global.isArrayListNull(fixturesResponseModel.getTypeMatches())) {
                for (int i = 0; i < fixturesResponseModel.getTypeMatches().size(); i++) {
                    seriesAdWrapperDTOS.addAll(fixturesResponseModel.getTypeMatches().get(i).getSeriesAdWrapper());
                }
            }
            for (int i = 0; i < seriesAdWrapperDTOS.size(); i++) {
                if (seriesAdWrapperDTOS.get(i).getSeriesMatches() != null && !Global.isArrayListNull(seriesAdWrapperDTOS.get(i).getSeriesMatches().getMatches())) {
                    matchesDTOArrayList.addAll(seriesAdWrapperDTOS.get(i).getSeriesMatches().getMatches());

                }
            }

        } catch (Exception e) {
            Global.sout("Crash while processing the fixtures", e.getLocalizedMessage());
        }
        return matchesDTOArrayList;
    }*/

    public static GlideUrl getTheImage(Context context, String imageID) {
        //TODO this method hits and return the imager response of api
        return new GlideUrl(EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_BASE)) + "get-image?id=" + imageID + "&p=de", new LazyHeaders.Builder().addHeader("X-RapidAPI-Key", EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_KEY))).addHeader("X-RapidAPI-Host", EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_HOST))).build());
    }

    public static boolean isClassNull(Object objectToCheck) {
        return objectToCheck == null;
    }

    public static RecyclerView.LayoutManager getManagerWithOrientation(Context context, int orientation) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(orientation);
        return manager;
    }

    public static RecyclerView.LayoutManager getGridLayoutManager(Context context, int spanCount) {
        GridLayoutManager manager = new GridLayoutManager(context, spanCount);
        manager.setOrientation(RecyclerView.VERTICAL);
        return manager;
    }

    public static String getTextFromDataModel(ArrayList<NewsDetailsResponseModel.ContentDTO> content) {
        String newsDetails = "";
        try {
            if (!Global.isArrayListNull(content)) {

                for (int i = 0; i < content.size(); i++) {
                    if (InputUtils.CheckEqualCaseSensitive(content.get(i).getContent().getContentType(), "text")) {
                        newsDetails = newsDetails + content.get(i).getContent().getContentValue() + "\n";
                    }
                }
            }
        } catch (Exception e) {
            Global.sout("something crashed while getting the text >>>>>>>>>>>>>>", e.getMessage());
        }
        Global.sout("news details string", newsDetails);
        return newsDetails.trim();
    }

    public static AdsJsonPOJO getAdsData(String json) {
        Type familyType = new TypeToken<AdsJsonPOJO>() {
        }.getType();
        return new Gson().fromJson(json, familyType);

    }

    public static CricketFlagsResponseModel setUpFlagsModel(String json) {
        return new Gson().fromJson(json, new TypeToken<CricketFlagsResponseModel>() {
        }.getType());
    }

    public static String getFlagOfCountry(boolean isMen, String teamName) {
        if (!Global.isArrayListNull(Constants.cricketFlagsModel.getFlagList()) && !Global.isClassNull(Constants.cricketFlagsModel)) {
            for (int i = 0; i < Constants.cricketFlagsModel.getFlagList().size(); i++) {
                if (isMen) {
                    if (Constants.cricketFlagsModel.getFlagList().get(i).getFlagName().contains(teamName)) {
                        return Constants.cricketFlagsModel.getFlagList().get(i).getFlagURL();
                    }
                } else {
                    if (teamName.contains(Constants.cricketFlagsModel.getFlagList().get(i).getFlagName())) {
                        return Constants.cricketFlagsModel.getFlagList().get(i).getFlagURL();
                    }
                }
            }
        }
        return "";
    }
    public static String filterText(String singleSplitString, String charToSplit, String charToJoin) {
        String[] words = singleSplitString.split(charToSplit);
        Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
        return String.join(charToJoin, uniqueWords);
    }
}
