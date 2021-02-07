package ru.orex.myweb;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.text.format.DateFormat;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BlockActivity extends AppCompatActivity {


    String locale;
    public WebView webView;
    ProgressBar progressBar;
    protected FrameLayout videoFrame;
    protected View mCustomView;
    BroadcastReceiver receiver;
    Boolean dopMenu = false;
    Boolean actionBar = false;
    Boolean actionBarAutomaticallyGone=true;
    public Activity activity;
    final Context context = this;
    protected WebChromeClient.CustomViewCallback mCustomViewCallback;
    boolean status;
    final String uAAndr11 = "Mozilla/5.0 (Linux; Android 11; SM-Galaxy Fold Build/011019) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110   Mobile Safari/537.36 YaApp_Android/9.30 YaSearchBrowser/9.30";
    final String uALinux = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36 Google Favicon X-REQUESTED-WITH:com.google.unity.ads";
    final String uaIphone = "Mozilla/5.0 (iPhone; CPU iPhone OS 13_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) FxiOS/21.0 Mobile/15E148 Safari/605.1.15 X-REQUESTED-WITH:com.google.unity.ads";
    final String uaMacintosh = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/601.2.4 (KHTML, like Gecko) Version/9.0.1 Safari/601.2.4 facebookexternalhit/1.1 Facebot Twitterbot/1.0 X-REQUESTED-WITH:com.google.unity.ads";
    final String uaWebOSSmartTV = "Mozilla/5.0 (Web0S; Linux/SmartTV) AppleWebKit/537.36 (KHTML, like Gecko) QtWebEngine/5.2.1 Chr0me/38.0.2125.122 Safari/537.36 LG Browser/8.00.00(LGE; 60UH6550-UB; 03.00.15; 1; DTV_W16N); webOS.TV-2016; LG NetCast.TV-2013 Compatible (LGE, 60UH6550-UB, wireless) X-REQUESTED-WITH:com.google.unity.ads";
    final String uaWin10 = "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36 Edge/12.0,gzip(gfe) X-REQUESTED-WITH:com.google.unity.ads";
    final String uaiPad = "Mozilla/5.0 (iPad; CPU OS 10_3_2 like Mac OS X) AppleWebKit/603.2.4 (KHTML, like Gecko) Version/10.0 Mobile/14F90 Safari/602.1 X-REQUESTED-WITH:com.google.unity.ads";
    final String uAAndr12Opera = "Mozilla/5.0 (Linux; Android 12;HUAWEI Mate Xs ) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.127  Mobile Safari/537.36 OPR/60.3.3004.55692";

    WebSettings webSettings;
    SharedPreferences sharedPreferences;

    ImageButton button1;
    ImageButton button2;
    ImageButton button3;
    ImageButton button4;
    ImageButton button5;
    ImageButton button6;
    LinearLayout imgLinearLayout;
    SharedPreferences sPref;
    int tab = 1;
    final String SAVED_TAB1 = "saved_tab1";
    final String SAVED_TAB1_TITLE = "saved_tab1_title";
    final String SAVED_TAB2 = "saved_tab2";
    final String SAVED_TAB2_TITLE = "saved_tab2_title";
    final String SAVED_TAB3 = "saved_tab3";
    final String SAVED_TAB3_TITLE = "saved_tab3_title";
    final String SAVED_TAB4 = "saved_tab4";
    final String SAVED_TAB4_TITLE = "saved_tab4_title";
    final String SAVED_TAB5 = "saved_tab5";
    final String SAVED_TAB5_TITLE = "saved_tab5_title";


    Boolean sound = true;
    Boolean darkStyles = false;
    Boolean grayStyles = false;
    Boolean nightStyles = false;
    Boolean negativeStyles=false;
    Boolean mAdBlock=true;
    boolean ad;



    int history = 0;
    boolean historySaved = false;
    final String SAVED_HISTORY1 = "saved_history1";
    final String SAVED_HISTORY1_TITLE = "saved_history1_title";
    final String SAVED_HISTORY2 = "saved_history2";
    final String SAVED_HISTORY2_TITLE = "saved_history2_title";
    final String SAVED_HISTORY3 = "saved_history3";
    final String SAVED_HISTORY3_TITLE = "saved_history3_title";
    final String SAVED_HISTORY4 = "saved_history4";
    final String SAVED_HISTORY4_TITLE = "saved_history4_title";
    final String SAVED_HISTORY5 = "saved_history5";
    final String SAVED_HISTORY5_TITLE = "saved_history5_title";
    final String SAVED_HISTORY6 = "saved_history6";
    final String SAVED_HISTORY6_TITLE = "saved_history6_title";
    final String SAVED_HISTORY7 = "saved_history7";
    final String SAVED_HISTORY7_TITLE = "saved_history7_title";
    final String FILEMYLOG = "myLogFile";

    public boolean isHistorySaved() {
        return historySaved;
    }

    public void setHistorySaved(boolean historySaved) {
        this.historySaved = historySaved;
    }

    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }



    public Boolean getactionBar() {
        return actionBar;
    }

    public void setActionBar(Boolean actionBar) {
        this.actionBar = actionBar;
    }

    public Boolean getActionBarAutomaticallyGone() {
        return actionBarAutomaticallyGone;
    }

    public void setActionBarAutomaticallyGone(Boolean actionBarAutomaticallyGone) {
        this.actionBarAutomaticallyGone = actionBarAutomaticallyGone;
    }
    public Boolean getmAdBlock() {
        return mAdBlock;
    }
    public void setmAdBlock(Boolean mAdBlock) {
        this.mAdBlock = mAdBlock;
    }

    public Boolean getDarkStyles() {
        return darkStyles;
    }
    public void setDarkStyles(Boolean darkStyles) {
        this.darkStyles = darkStyles;
    }
    public Boolean getGrayStyles() {
        return grayStyles;
    }
    public void setGrayStyles(Boolean grayStyles) {
        this.grayStyles = grayStyles;
    }
    public Boolean getNightStyles() {
        return nightStyles;
    }
    public void setNightStyles(Boolean nightStyles) {
        this.nightStyles = nightStyles;
    }
    public Boolean getNegativeStyles() {
        return negativeStyles;
    }
    public void setNegativeStyles(Boolean negativeStyles) {
        this.negativeStyles = negativeStyles;
    }


    void saveHistory(String string, String history) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(history, string);
        editor.commit();
    }

    String loadHistory(String stringHistory) {
        sPref = getPreferences(0);
        return sPref.getString(stringHistory, "");
    }


    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    String homePage;

    public int getTab() {
        return tab;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }

    void saveTab(String string, String stringTab) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(stringTab, string);
        editor.commit();
    }

    String loadTab(String stringTab) {
        sPref = getPreferences(0);
        return sPref.getString(stringTab, "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // showing the buttons
    public void imgButtonInvisible() {
        imgLinearLayout.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);
        button5.setVisibility(View.VISIBLE);
        button6.setVisibility(View.VISIBLE);
    }

    //hiding the buttons
    public void imgButtonGone() {
        imgLinearLayout.setVisibility(View.GONE);
        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3.setVisibility(View.GONE);
        button4.setVisibility(View.GONE);
        button5.setVisibility(View.GONE);
        button6.setVisibility(View.GONE);

    }

    // getting a link from MainActivity
    public String getLoadUrl() {
        String urlLoad = getIntent().getStringExtra("getLoadUrl");
        String stringUrlLoad = urlLoad;
        // if (stringUrlLoad != null) {
        // toast(stringUrlLoad);
        //  webView.loadUrl(urlLoad);
        // }
        return stringUrlLoad;
    }

    // output a text message
    public String toast(String stringToast) {
        Toast toast = Toast.makeText((Context) this.getApplicationContext(), (CharSequence) stringToast, (int) 1);
        toast.setGravity(17, 0, 0);
        toast.show();
        return stringToast;
    }

    public String toastImg(String stringToast) {
        Toast aa = Toast.makeText(getBaseContext(), stringToast, Toast.LENGTH_SHORT);
        ImageView cc = new ImageView(getBaseContext());
        cc.setImageResource(R.drawable.ic__add_book);
        aa.setView(cc);
        aa.show();
        return stringToast;
    }

    // download manager
    public void doDownload(String url, String fileName) {
        Uri uriOriginal = Uri.parse(url);
        try {
            Toast.makeText(this,getResources().getString(R.string.downloading) + fileName, Toast.LENGTH_LONG).show();
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
            //name to show while downloading
            request.setTitle(fileName);
            //description to show while downloading
            request.setDescription(getResources().getString(R.string.downloading) + fileName);
            //show on navigation
            request.setVisibleInDownloadsUi(true);
            final DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            dm.enqueue(request);
        } catch (Exception e) {
            Toast.makeText(this, getResources().getString(R.string.problem_downloading), Toast.LENGTH_SHORT).show();
            //Log.e("", "Problem downloading: " + uriOriginal, e);
        }
    }

    public void loadEnd() {
        Toast.makeText(this, getResources().getString(R.string.loadEnd), Toast.LENGTH_SHORT).show();
    }
    public void downloadOkeySound() {
        locale = getResources().getConfiguration().locale.getDisplayName();
        if(locale.equals("русский(Россия)")|| locale.startsWith("RU") || locale.startsWith("р")|| locale.startsWith("ru")){
        if (getSound()) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.donwload);
            mp.start();
        }
        }
    }
    public void errorSound() {
        locale = getResources().getConfiguration().locale.getDisplayName();
        if(locale.equals("русский(Россия)")|| locale.startsWith("RU") || locale.startsWith("р")|| locale.startsWith("ru")){
            if (getSound()) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.url);
            mp.start();
        }
        }
    }


    public Boolean getSound() {
        return sound;
    }

    public void setSound(Boolean sound) {
        this.sound = sound;
    }

    // vibration
    public void vibration(int sec) {
        if (getSound()) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(sec);
        }
    }

    boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {
            status = true;
            return true;
        } else {
            status = false;
            return false;
        }
    }

    public String search(String query) {
        String keywords = null;
        String loadString = null;
        try {
            keywords = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (keywords != null) {
            loadString=("https://duckduckgo.com/?q=" + keywords);
          //  loadString = "https://yandex.ru/search/?text=" + keywords;
            //loadString="https://www.google.com/?q=" + keywords;
        }
        return loadString;
    }

    public void saveBm(String urlPage, String urlTitle) {
        Intent intent = new Intent(this, SaveBmActivity.class);
        intent.putExtra("urlTitle", urlTitle);
        intent.putExtra("urlPage", urlPage);
        startActivity(intent);
    }

    public void htpp(String urlPage) {
        Intent intent = new Intent(this, HtppActivity.class);
        intent.putExtra("urlHtpp", urlPage);
        startActivity(intent);
    }

    public void goSBA() {
        Intent intentSVB = new Intent(this, SaveBmActivity.class);
        startActivityForResult(intentSVB, 1);
    }

    int numberBook(int number) {
        Toast.makeText(this, getResources().getString(R.string.number_book) + "" + number, Toast.LENGTH_SHORT).show();
        return number;
    }

    public static void restartActivity(Activity activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate();
        } else {
            activity.finish();
            activity.startActivity(activity.getIntent());
        }
    }
    // Screenshot
    private void openScreenshot(File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile((File) file), "image/*");
        this.startActivity(intent);
    }

    public void takeScreenShot() {
        DateFormat.format((CharSequence) "mm_ss", (Date) new Date());
        String string2 = "Screenshot_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Environment.getExternalStorageDirectory().toString());
            stringBuilder.append("/Pictures/Screenshots/");
            stringBuilder.append(string2);
            stringBuilder.append(".png");
            String string3 = stringBuilder.toString();
            toast(getResources().getString(R.string.screenshot_save) + string3);
            View view = this.getWindow().getDecorView().getRootView();
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap((Bitmap) view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            File file = new File(string3);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, (OutputStream) fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return;
        }
    }
    public String dialogHTPP(String dialog) {
        AlertDialog alertDialog = new AlertDialog.Builder((Context) this)
                //set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
                //set title
                .setTitle(R.string.app_name)
                //set message
                .setMessage(dialog)
                //set positive button
                .setPositiveButton(getString(R.string.positive_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        deleteFile (FILEMYLOG);
                    }
                })
                .show();
        return null;
    }

    /*
    public void donwl() {
        Intent intent = new Intent();
        intent.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(intent);
    }*/



    public void mClearCache() {
        File dir = this.getCacheDir();
        if (dir != null && dir.isDirectory()) {
            try {
                File[] children = dir.listFiles();
                if (children.length > 0) {
                    for (int i = 0; i < children.length; i++) {
                        File[] temp = children[i].listFiles();
                        for (int x = 0; x < temp.length; x++) {
                            temp[x].delete();
                        }
                    }
                }
            } catch (Exception e) {
               // Log.e("Cache", "failed cache clean");
            }
        }
    }

    void writeFile(String textFile,String fileName) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(fileName, MODE_APPEND)));
            bw.write(textFile);
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String readFile(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(fileName)));
            String str = "";
            while ((str = br.readLine()) != null) {
                // Log.d(LOG_TAG, str);
                return str;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    String dateReturn(){
        String time = new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss").format(new Date());
        return time;
    }




    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }

}




