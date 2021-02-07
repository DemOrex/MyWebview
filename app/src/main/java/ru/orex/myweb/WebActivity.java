package ru.orex.myweb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WebActivity extends BlockActivity {

    public static final String TAG;
    private static String[] PERMISSIONS_STORAGE;
    public ValueCallback<Uri[]> mUploadMessage;
    public String mCameraPhotoPath = null;
    private long size;

    
    // permission
    static {
        TAG = WebActivity.class.getSimpleName();
        PERMISSIONS_STORAGE = new String[]{"android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.CAMERA",
                "android.permission.ACCESS_COARSE_LOCATION",
                "android.permission.ACCESS_BACKGROUND_LOCATION",
                "android.permission.ACCESS_FINE_LOCATION",
                "android.permission.ACCESS_GPS",
                "android.permission.ACCESS_ASSISTED_GPS"};
    }

    // permission
    public static void verifyStoragePermissions(Activity activity) {
        int n = ActivityCompat.checkSelfPermission((Context) activity, (String) "android.permission.WRITE_EXTERNAL_STORAGE");
        int n2 = ActivityCompat.checkSelfPermission((Context) activity, (String) "android.permission.READ_EXTERNAL_STORAGE");
        int n3 = ActivityCompat.checkSelfPermission((Context) activity, (String) "android.permission.CAMERA");
        int n4 = ActivityCompat.checkSelfPermission((Context) activity, (String) "android.permission.ACCESS_COARSE_LOCATION");
        int n5 = ActivityCompat.checkSelfPermission((Context) activity, (String) "android.permission.ACCESS_BACKGROUND_LOCATION");
        int n6 = ActivityCompat.checkSelfPermission((Context) activity,
                (String) "android.permission.ACCESS_FINE_LOCATION");
        int n7 = ActivityCompat.checkSelfPermission((Context) activity,
                (String) "android.permission.ACCESS_GPS");
        int n8 = ActivityCompat.checkSelfPermission((Context) activity,
                (String) "android.permission.ACCESS_ASSISTED_GPS");
        if (n != 0 || n2 != 0 || n3 != 0 || n4 != 0 || n5 != 0 || n6 != 0 || n7 != 0 || n8 != 0) {
            ActivityCompat.requestPermissions((Activity) activity, (String[]) PERMISSIONS_STORAGE, (int) 1);
        }
    }


    public void onActivityResult(int n, int n2, Intent intent) {
        if (n == 1 && this.mUploadMessage != null) {
            try {
                this.size = new File(this.mCameraPhotoPath.replace((CharSequence) "file:", (CharSequence) "")).length();
            } catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error while opening image file");
                stringBuilder.append(exception.getLocalizedMessage());
               // Log.e((String) "Error!", (String) stringBuilder.toString());
            }
            if (intent != null || this.mCameraPhotoPath != null) {
                Integer n3 = 0;
                ClipData clipData = null;
                try {
                    ClipData clipData2;
                    clipData = clipData2 = intent.getClipData();
                } catch (Exception exception) {
                   // Log.e((String) "Error!", (String) exception.getLocalizedMessage());
                }
                if (clipData == null && intent != null && intent.getDataString() != null) {
                    n3 = intent.getDataString().length();
                } else if (clipData != null) {
                    n3 = clipData.getItemCount();
                }
                Uri[] arruri = new Uri[n3.intValue()];
                if (n2 == -1) {
                    if (this.size != 0L) {
                        String string2 = this.mCameraPhotoPath;
                        if (string2 != null) {
                            Uri[] arruri2 = new Uri[]{Uri.parse((String) string2)};
                            arruri = arruri2;
                        }
                    } else if (intent.getClipData() == null) {
                        Uri[] arruri3 = new Uri[]{Uri.parse((String) intent.getDataString())};
                        arruri = arruri3;
                    } else {
                        for (int i = 0; i < clipData.getItemCount(); ++i) {
                            arruri[i] = clipData.getItemAt(i).getUri();
                        }
                    }
                }
                this.mUploadMessage.onReceiveValue((Uri[]) arruri);
                this.mUploadMessage = null;
            }
            return;
        }
        super.onActivityResult(n, n2, intent);
        String stringBook = intent.getStringExtra("urlBook");
        if (stringBook.equals((Object) "")) {
            return;
        }
        toast(stringBook);
        webView.loadUrl(stringBook);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
        }

        //   we notify you about downloading the file
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    loadEnd();
                    downloadOkeySound();
                }
            }
        };



        imgLinearLayout = (LinearLayout) findViewById(R.id.imgLinerLayout);
        button1 = (ImageButton) findViewById(R.id.imgButton1);
        button2 = (ImageButton) findViewById(R.id.imgButton2);
        button3 = (ImageButton) findViewById(R.id.imgButton3);
        button4 = (ImageButton) findViewById(R.id.imgButton4);
        button5 = (ImageButton) findViewById(R.id.imgButton5);
        button6 = (ImageButton) findViewById(R.id.imgButton6);

        webView = (WebView) this.findViewById(R.id.myWeb_vie);
        progressBar = findViewById(R.id.progressBa);
        progressBar.setMax(100);
        videoFrame = (FrameLayout) findViewById(R.id.fullscreen_container);
        verifyStoragePermissions((Activity) this);
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        webSettings = webView.getSettings();



        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setBuiltInZoomControls(true);webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setGeolocationDatabasePath("/data/data/com.sogou.activit.src/databases/");
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setNeedInitialFocus(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setUserAgentString(null);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setEnableSmoothTransition(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);


        ConnectivityManager cm = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        if (cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }



        webView.setWebViewClient((WebViewClient) new MyClient());
        webView.setWebChromeClient((WebChromeClient) new MyChromeClient());
        getPrefs();
        if (getLoadUrl() != null) {
            webView.loadUrl(getLoadUrl());
        } else {
            webView.loadUrl(loadTab(SAVED_TAB1));
        }
        imgButtonGone();

          // toast( getResources().getConfiguration().locale.getDisplayName());

        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                vibration(100);
                dialogTab();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                vibration(100);
                webView.stopLoading();
                toast(getResources().getString(R.string.stop_loading));

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                vibration(100);
                webView.reload();
                toast(getResources().getString(R.string.reload_page));
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                vibration(100);
                webView.canGoForward();
                webView.goForward();
                toast(getResources().getString(R.string.page_forward));
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                vibration(100);
                webView.pageDown(false);
            }
        });
        button4.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                vibration(100);
                webView.pageDown(true);
                return false;
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                vibration(100);
                webView.pageUp(false);
            }
        });
        button5.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                vibration(100);
                webView.pageUp(true);
                return false;
            }
        });


// DownloadListener

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                final String fileName = URLUtil.guessFileName(url, contentDisposition, mimetype);
                final AlertDialog.Builder downloadDialog = new AlertDialog.Builder(WebActivity.this);
                downloadDialog.setTitle(getResources().getString(R.string.download_manager));
                downloadDialog.setMessage(getResources().getString(R.string.download_question) + '\n' + mimetype + '\n' + url + '\n' + contentLength );
                downloadDialog.setPositiveButton(getResources().getString(R.string.button_yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        doDownload(url, fileName);
                        dialogInterface.dismiss();
                        vibration(100);
                    }
                });
                downloadDialog.setNegativeButton(getResources().getString(R.string.button_no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        vibration(100);
                    }
                });
                downloadDialog.show();
            }
        });
              // adblock
        AdBlocker.init(this);

        }



    public class MyChromeClient extends WebChromeClient {


        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
            }
            mUploadMessage = filePathCallback;

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                } catch (IOException ex) {
                   // Log.e(TAG, "Unable to create Image File", ex);
                }
                if (photoFile != null) {
                    mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                } else {
                    takePictureIntent = null;
                }
            }

            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            contentSelectionIntent.setType("image/*");

            Intent[] intentArray;
            if (takePictureIntent != null) {
                intentArray = new Intent[]{takePictureIntent};
            } else {
                intentArray = new Intent[2];
            }

            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
            startActivityForResult(Intent.createChooser(chooserIntent, "Select images"), 1);

            return true;

        }

        // Geolocation
        public void onGeolocationPermissionsShowPrompt(final String string2, final GeolocationPermissions.Callback callback) {
           // Log.i((String) WebActivity.TAG, (String) "onGeolocationPermissionsShowPrompt()");
            if (webView.getTitle().equals((Object) "\u042f\u043d\u0434\u0435\u043a\u0441")) {
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder((Context) WebActivity.this);
            builder.setTitle((CharSequence) getResources().getString(R.string.geolocation));
            builder.setIcon(R.drawable.ic_baseline_nights_stay_24);
            builder.setMessage((CharSequence) getResources().getString(R.string.geo_mess)).setCancelable(true).setPositiveButton((CharSequence) getResources().getString(R.string.positive_button), new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialogInterface, int n) {
                    callback.invoke(string2, true, false);
                }
            }).setNegativeButton((CharSequence) getResources().getString(R.string.negative_button), new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialogInterface, int n) {
                    callback.invoke(string2, false, false);
                }
            });
            builder.create().show();
        }


        public boolean onJsConfirm(WebView webView, String url, String message, final JsResult jsResult) {
            new AlertDialog.Builder(activity)
                    .setTitle(getResources().getString(R.string.onjsconfirm))
                    .setMessage(message)
                    .setPositiveButton(
                            android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    jsResult.confirm();
                                }
                            }
                    )
                    .setNegativeButton(
                            android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    jsResult.cancel();
                                }
                            }
                    )
                    .create()
                    .show();
            return true;
        }


        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
            final EditText data = new EditText(view.getContext());
            AlertDialog.Builder b = new AlertDialog.Builder(view.getContext())
                    .setTitle(getResources().getString(R.string.app_name)+view.getTitle())
                    .setView(data)
                    .setMessage(message)
                    // .setOnCancelListener(new CancelListener(result)) // if this line is missing, WebView remains unresponsive after the dialog is shown and closed once
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm(data.getText().toString());
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    });

            b.show();

            return true;
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result)
        {
            final JsResult finalRes = result;
            new AlertDialog.Builder(view.getContext())
                    .setTitle(getResources().getString(R.string.onjsalert))
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new AlertDialog.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finalRes.confirm();
                                }
                            })
                    .setCancelable(false)
                    .create()
                    .show();
            return true;
        }



        // videoFrame
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            webView.setVisibility(View.GONE);
            mCustomViewCallback = callback;
            mCustomView = view;
            videoFrame.addView(view);
            videoFrame.setVisibility(View.VISIBLE);
            videoFrame.bringToFront();
            imgButtonGone();
            getSupportActionBar().hide();
        }

        //
        @Override
        public void onHideCustomView() {
            if (mCustomView != null) {
                videoFrame.setVisibility(View.GONE);
                videoFrame.removeAllViews();
                mCustomView = null;
                mCustomViewCallback.onCustomViewHidden();
                webView.setVisibility(View.VISIBLE);
                getSupportActionBar().show();
            }
        }

        // setProgress
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void onProgressChanged(WebView view, int progress) {
            progressBar.setProgress(progress);
            // enable-force-dark
            if(getNegativeStyles()){
            initRendering(NEGATIVE_COLOR);
            }else {
               // webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            }
            if (getDarkStyles()) {
                //injectCSSDark();
                initRendering(STYLE1_COLOR);
            }else {
               // webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            }
            if (getGrayStyles()) {
                //injectCSSGray();
                initRendering(STYLE2_COLOR);
            }else {
               // webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            }
            if (getNightStyles()) {
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                injectCSSNight();
            }
        }

                // adblock
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
           if(getmAdBlock()){
            WebView newWebView = (WebView) LayoutInflater.from(view.getContext()).inflate(R.layout.activity_web, null);
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(newWebView);
            resultMsg.sendToTarget();
            return true;} else {
               return false;
           }
        }


    }

    public class MyClient extends WebViewClient {


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (url.startsWith("http://")){
                toast(getResources().getString(R.string.htpp));
            }
            setTitle(url);
            progressBar.setVisibility(View.VISIBLE);
        }



        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse((String) url);
            if ((uri.getScheme().equals((Object) "market") || uri.getScheme().equals((Object) "market") || uri.getScheme().equals((Object) "geo"))) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(uri);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                toast(url);
            }

            if (url.startsWith("tel:") || url.startsWith("sms:") || url.startsWith("smsto:")
                    || url.startsWith("mms:") || url.startsWith("mmsto:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
                return true;
            }


            if (url.startsWith("http://") || url.startsWith("https://")) {

                if (isOnline()) {
                    view.loadUrl(url);
                }else {
                    vibration(1000);
                    toast(getResources().getString(R.string.internet_error));
                }
            } else {

                    locale = getResources().getConfiguration().locale.getDisplayName();
                    toast(getResources().getString(R.string.url_error));
                   if(locale.equals("русский(Россия)")|| locale.startsWith("RU") || locale.startsWith("р")|| locale.startsWith("ru")){
                    errorSound();}
                if (uri == null && uri.toString().equals((Object) "about:blank") && uri.toString().startsWith("about")) {
                    toast(getResources().getString(R.string.null_load));
                }
            }


            return true;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void onPageFinished(WebView view, String url) {
            progressBar.setVisibility(View.GONE);
            if (isOnline()) {
                setTitle(webView.getTitle());
            }

            if (getTab() == 1) {

                saveTab(webView.getUrl(), SAVED_TAB1);
                saveTab(webView.getTitle(), SAVED_TAB1_TITLE);
            }
            if (getTab() == 2) {
                saveTab(webView.getUrl(), SAVED_TAB2);
                saveTab(webView.getTitle(), SAVED_TAB2_TITLE);
            }
            if (getTab() == 3) {
                saveTab(webView.getUrl(), SAVED_TAB3);
                saveTab(webView.getTitle(), SAVED_TAB3_TITLE);
            }
            if (getTab() == 4) {
                saveTab(webView.getUrl(), SAVED_TAB4);
                saveTab(webView.getTitle(), SAVED_TAB4_TITLE);
            }
            if (getTab() == 5) {
                saveTab(webView.getUrl(), SAVED_TAB5);
                saveTab(webView.getTitle(), SAVED_TAB5_TITLE);
            }
            savedHistory(url);



            if(getActionBarAutomaticallyGone()==false){
            if(getactionBar()==false){
                setActionBar(true);
                getSupportActionBar().hide();
                imgButtonGone();}}


        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (!isOnline()) {
                vibration(1000);
                webView.loadUrl("file:///android_asset/internetnet.png");
                toast(getResources().getString(R.string.internet_error));
            }
        }
        /*
        public String[] getHttpAuthUsernamePassword(String host, String realm) {
            return null;
        }
           */
        /*
        @Override
        public void onReceivedHttpAuthRequest  (WebView view,
                                                HttpAuthHandler handler, String host, String realm){

            String[] up = view.getHttpAuthUsernamePassword(host, realm);
            toast("onReceivedHttpAuthRequest"+up);

        }*/

        private Map<String, Boolean> loadedUrls = new HashMap<>();

        @SuppressWarnings("deprecation")
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

            if(getmAdBlock()){
            if (!loadedUrls.containsKey(url)) {
                ad = AdBlocker.isAd(url);
                loadedUrls.put(url, ad);
            } else {
                ad = loadedUrls.get(url);
            }}
            return ad ? AdBlocker.createEmptyResource() :
                    super.shouldInterceptRequest(view, url);

        }


    }



    public void onBackPressed(){
        if (getactionBar()) {
            setActionBar(false);
            getSupportActionBar().show();
        }else { if (webView.canGoBack()) {
            webView.goBack();
        }else{new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.exit_from_app))
                .setMessage(getResources().getString(R.string.are_you_sure))
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        WebActivity.super.onBackPressed();
                    }
                }).create().show();
        }
        }
    }

  /*  protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }*/




    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.stopLoading();
        webView.clearHistory();
        webView.clearCache(true);
        webView.clearView();
        webView.freeMemory();
        webView.destroy();
        webView.removeAllViews();
        mClearCache();
        webView = null;
        //unregisterReceiver(DownloadManager);
    }



    @Override
    protected void onStart() {
        super.onStart();
        // getPrefsNew();
        getPrefs();
         /*
        //  double-tap.
        final GestureDetector gestureDetector   = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent event) {
                // TODO: Insert code to run on double-tap here.
                // getSupportActionBar().hide();
                actionBar = !actionBar;
                if (actionBar) {
                    getSupportActionBar().show();
                }
                if (actionBar == false) {
                    getSupportActionBar().hide();
                }
                // Consume the double-tap.
                return true;
            }
        });
  */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home:
                webView.loadUrl(getHomePage());
                if (getHomePage() == null || getHomePage().equals("")) {
                    toast(getResources().getString(R.string.choose_your_home_page));
                }
                return true;
            case R.id.menu_search:
                // webView.loadUrl( search("вася"));
                dialogSearch();
                return true;
            case R.id.menu_dop:
                dopMenu = !dopMenu;
                if (dopMenu) {
                    imgButtonInvisible();
                } else {
                    imgButtonGone();
                }
                return true;
            case R.id.menu_screenshot:
                webView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      takeScreenShot();
                    }
                }, 1500);
                if (getSound()) {
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.screenshot);
                    mp.start();
                }
                return true;
            case R.id.menu_share:
                String sendUrl = webView.getUrl();
                if (sendUrl != null && !sendUrl.equals("about:blank")) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_TEXT, sendUrl);
                    startActivity(Intent.createChooser(i, getResources().getString(R.string.share_url)));
                }
                return true;
            case R.id.menu_history:
                dialogHistory();
                return true;
            case R.id.menu_actionbar:
                if(getactionBar()==false){
                setActionBar(true);
                getSupportActionBar().hide();
                imgButtonGone();}
                return true;
            case R.id.menu_about_site:
                dialogHTPP("Website--------" + webView.getTitle() + ".........." + "Protocol--------" + webView.getOriginalUrl() + "............" + "Cookie--------" + CookieManager.getInstance().getCookie(webView.getUrl()));
                return true;
            case R.id.menu_add_bookmark:
                saveBm(webView.getUrl(), webView.getTitle());
                return true;
            case R.id.menu_htpp:
                  htpp(webView.getUrl());
                return true;
            case R.id.menu_bookmark:
                goSBA();
                return true;
            case R.id.menu_setting:
                Intent intent = new Intent(this, PrefActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_pdf:
                printPDF();
                return true;
            case R.id.menu_app_info:
                  appInfo();
                return true;
            case R.id.menu_exit:
                finish();
                return true;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogSearch() {

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompt, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
        mDialogBuilder.setView(promptsView);
      //  mDialogBuilder.setIcon(R.mipmap.ic_launcher);
        final EditText userInput = (EditText) promptsView.findViewById(R.id.input_text);
        userInput.requestFocus();

        mDialogBuilder
          //      .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(R.string.positive_button,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                toast(userInput.getEditableText().toString());
                                webView.loadUrl(search(userInput.getEditableText().toString()));

                                dialog.cancel();
                            }
                        })
                .setNegativeButton(R.string.negative_button,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        mDialogBuilder.show();
    }

    private void dialogTab() {
        CharSequence[] arrayTabs = new CharSequence[]{"TAB 1.---" + loadTab(SAVED_TAB1_TITLE), "TAB 2.---" + loadTab(SAVED_TAB2_TITLE), "TAB 3.---" + loadTab(SAVED_TAB3_TITLE), "TAB 4.---" + loadTab(SAVED_TAB4_TITLE), "TAB 5.---" + loadTab(SAVED_TAB5_TITLE)};
        AlertDialog.Builder builder = new AlertDialog.Builder((Context) this);
        builder.setItems(arrayTabs, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int n) {
                if (n == 0) {
                    setTab(1);
                    button6.setImageResource(R.drawable.ic__looks_one);
                    webView.loadUrl(loadTab(SAVED_TAB1));
                    webView.loadUrl(loadTab(SAVED_TAB1));

                }
                if (n == 1) {
                    setTab(2);
                    button6.setImageResource(R.drawable.ic_two);
                    webView.loadUrl(loadTab(SAVED_TAB2));

                }
                if (n == 2) {
                    setTab(3);
                    button6.setImageResource(R.drawable.ic_3);
                    webView.loadUrl(loadTab(SAVED_TAB3));

                }
                if (n == 3) {
                    setTab(4);
                    button6.setImageResource(R.drawable.ic_4);
                    webView.loadUrl(loadTab(SAVED_TAB4));

                }
                if (n == 4) {
                    setTab(5);
                    button6.setImageResource(R.drawable.ic_5);
                    webView.loadUrl(loadTab(SAVED_TAB5));
                }

            }


        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void dialogHistory() {

        CharSequence[] arrayHistory = new CharSequence[]{getResources().getString(R.string.dialog_1) + loadHistory(SAVED_HISTORY1_TITLE), Html.fromHtml("<font color='#FF7F27'>2.---</font>") + loadHistory(SAVED_HISTORY2_TITLE), "3.---" + loadHistory(SAVED_HISTORY3_TITLE), "4.---" + loadHistory(SAVED_HISTORY4_TITLE), "5.---" + loadHistory(SAVED_HISTORY5_TITLE), "6.---" + loadHistory(SAVED_HISTORY6_TITLE), "7.---" + loadHistory(SAVED_HISTORY7_TITLE)};
        AlertDialog.Builder builder = new AlertDialog.Builder((Context) this);
        builder.setItems(arrayHistory, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int n) {
                if (n == 0) {
                    webView.loadUrl(loadHistory(SAVED_HISTORY1));
                }
                if (n == 1) {
                    webView.loadUrl(loadHistory(SAVED_HISTORY2));
                }
                if (n == 2) {
                    webView.loadUrl(loadHistory(SAVED_HISTORY3));
                }
                if (n == 3) {
                    webView.loadUrl(loadHistory(SAVED_HISTORY4));
                }
                if (n == 4) {
                    webView.loadUrl(loadHistory(SAVED_HISTORY5));
                }
                if (n == 5) {
                    webView.loadUrl(loadHistory(SAVED_HISTORY6));
                }
                if (n == 6) {
                    webView.loadUrl(loadHistory(SAVED_HISTORY7));
                }
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    String savedHistory(String urlHistory) {
        setHistory(getHistory() + 1);
        if (getHistory() == 8) {
            setHistory(1);
        }
        if (getHistory() == 1) {
            saveHistory(webView.getUrl(), SAVED_HISTORY1);
            saveHistory(webView.getTitle(), SAVED_HISTORY1_TITLE);
        }
        if (getHistory() == 2) {
            saveHistory(webView.getUrl(), SAVED_HISTORY2);
            saveHistory(webView.getTitle(), SAVED_HISTORY2_TITLE);
        }
        if (getHistory() == 3) {
            saveHistory(webView.getUrl(), SAVED_HISTORY3);
            saveHistory(webView.getTitle(), SAVED_HISTORY3_TITLE);
        }
        if (getHistory() == 4) {
            saveHistory(webView.getUrl(), SAVED_HISTORY4);
            saveHistory(webView.getTitle(), SAVED_HISTORY4_TITLE);
        }
        if (getHistory() == 5) {
            saveHistory(webView.getUrl(), SAVED_HISTORY5);
            saveHistory(webView.getTitle(), SAVED_HISTORY5_TITLE);
        }
        if (getHistory() == 6) {
            saveHistory(webView.getUrl(), SAVED_HISTORY6);
            saveHistory(webView.getTitle(), SAVED_HISTORY6_TITLE);
        }
        if (getHistory() == 7) {
            saveHistory(webView.getUrl(), SAVED_HISTORY7);
            saveHistory(webView.getTitle(), SAVED_HISTORY7_TITLE);
        }
        return null;
    }
    private static final float[] STYLE2_COLOR = {
            -1.0f, 0, 0, 0, 255, // Red
            -1.0f, -1.0f, 0, 0, 255, // Green
            0, 0, -1.0f, 0, 255, // Blue
            0, 0, 0, 1.0f, 255     // Alpha
    };
    private static final float[] STYLE1_COLOR = {
            0, 0, 0, -1.0f, 255, // Red
            0, -1.0f, 0, 0, 255, // Green
            0, 0, -1.0f, 0, 255, // Blue
            0, 0, 0, 1.0f, 255     // Alpha
    };
    private static final float[] NEGATIVE_COLOR = {
            -1.0f, 0, 0, 0, 255, // Red
            0, -1.0f, 0, 0, 255, // Green
            0, 0, -1.0f, 0, 255, // Blue
            0, 0, 0, 1.0f, 0     // Alpha
    };
    private   void initRendering(float[] agr) {

        Paint paint = new Paint();
        ColorMatrix matrix = new ColorMatrix();
        matrix.set(agr);
        ColorMatrix gcm = new ColorMatrix();
        gcm.setSaturation(0);
        ColorMatrix concat = new ColorMatrix();
        concat.setConcat(matrix, gcm);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(concat);
        paint.setColorFilter(filter);
        // maybe sometime LAYER_TYPE_NONE would better?
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, paint);
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void injectCSSDark() {

        String codeDark = "javascript:(function() {" +
                "var node = document.createElement('style');" +
                "node.type = 'text/css';" +
                " node.innerHTML = 'body, label,th,p,a, td, tr,li,ul,span,table,h1,h2,h3,h4,h5,h6,h7,div,small {" +
                "     color: #deFFFFFF;" +
                "background-color:#232323;" +
                " } ';" +
                " document.head.appendChild(node);})();";

        webView.evaluateJavascript(codeDark, null);

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void injectCSSGray() {

        String codeGray = "javascript:(function() {" +
                "var node = document.createElement('style');" +
                "node.type = 'text/css';" +
                " node.innerHTML = 'body, label,th,p,a, td, tr,li,ul,span,table,h1,h2,h3,h4,h5,h6,h7,div,small {" +
                "     color: #2EB133;" +
                "background-color:#747070;" +
                " } ';" +
                " document.head.appendChild(node);})();";

        webView.evaluateJavascript(codeGray, null);

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void injectCSSNight() {

        String codeNight = "javascript:(function() {" +
                "var node = document.createElement('style');" +
                "node.type = 'text/css';" +
                " node.innerHTML = 'body, label,th,p,a, td, tr,li,ul,span,table,h1,h2,h3,h4,h5,h6,h7,div,small {" +
                "     color: #2EB133;" +
                "background-color:#232323;" +
                " } ';" +
                " document.head.appendChild(node);})();";

        webView.evaluateJavascript(codeNight, null);

    }


    public void getPrefs() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context) this);

        String listPrHomePage=sharedPreferences.getString("listPrefHomePage", "nr1");
       // String userHomePage=sharedPreferences.getString("userHomePage", "nr1");
        if(listPrHomePage.equals("1")){
            setHomePage("https://www.yandex.ru/");
        }
        if(listPrHomePage.equals("2")){
            setHomePage("https://www.google.com/");
        }
        if(listPrHomePage.equals("3")){
            setHomePage("https://www.mail.ru/");
        }
        if(listPrHomePage.equals("4")){
            setHomePage("https://www.duckduckgo.com/");
        }
        if(listPrHomePage.equals("5")){
            setHomePage(sharedPreferences.getString("userHomePage", "nr1"));
        }


        String listPrUserAgent=sharedPreferences.getString("listPrefUserAgent", "nr1");

        if(listPrUserAgent.equals("1")){
            webSettings.setUserAgentString(null);
        }
        if(listPrUserAgent.equals("2")){
            webSettings.setUserAgentString(uALinux);
        }
        if(listPrUserAgent.equals("3")){
            webSettings.setUserAgentString(uAAndr11);
        }
        if(listPrUserAgent.equals("4")){
            webSettings.setUserAgentString(uAAndr12Opera);
        }
        if(listPrUserAgent.equals("5")){
            webSettings.setUserAgentString(uaIphone);
        }
        if(listPrUserAgent.equals("6")){
            webSettings.setUserAgentString(uaiPad);
        }
        if(listPrUserAgent.equals("7")){
            webSettings.setUserAgentString(uaMacintosh);
        }
        if(listPrUserAgent.equals("8")){
            webSettings.setUserAgentString(uaWin10);
        }
        if(listPrUserAgent.equals("9")){
            webSettings.setUserAgentString(uaWebOSSmartTV);
        }
        if(listPrUserAgent.equals("10")){
            webSettings.setUserAgentString(sharedPreferences.getString("enterUserAgent", "nr1"));
        }

        String listPrWebTextSize=sharedPreferences.getString("listPrefWebTextSize", "nr1");
        if(listPrWebTextSize.equals("1")){
            webSettings.setTextSize(WebSettings.TextSize.SMALLEST);
        }
        if(listPrWebTextSize.equals("2")){
            webSettings.setTextSize(WebSettings.TextSize.SMALLER);
        }
        if(listPrWebTextSize.equals("3")){
            webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        }
        if(listPrWebTextSize.equals("4")){
            webSettings.setTextSize(WebSettings.TextSize.LARGER);
        }
        if(listPrWebTextSize.equals("5")){
            webSettings.setTextSize(WebSettings.TextSize.LARGEST);
        }

        String listPrWebColor=sharedPreferences.getString("listPrefWebColor", "nr1");
        if(listPrWebColor.equals("1")){
            setDarkStyles(true);
        }else {
            setDarkStyles(false);
        }
        if(listPrWebColor.equals("2")){
            setGrayStyles(true);
        }else {
            setGrayStyles(false);
        }
        if(listPrWebColor.equals("3")){
            setNightStyles(true);
        }else {
            setNightStyles(false);
        }
        if(listPrWebColor.equals("4")){
            setNegativeStyles(true);
        }else {
            setNegativeStyles(false);
        }
        if(listPrWebColor.equals("5")){
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }

        if (sharedPreferences.getBoolean("actionbar", false)) {
            setActionBarAutomaticallyGone(false);
        } else {
            setActionBarAutomaticallyGone(true);
        }

        if (sharedPreferences.getBoolean("javaScriptEnabled", false)) {
            this.webView.getSettings().setJavaScriptEnabled(false);
        } else {
            this.webView.getSettings().setJavaScriptEnabled(true);
        }

        if (sharedPreferences.getBoolean("img", false)) {
            webView.getSettings().setLoadsImagesAutomatically(false);
        } else {
            webView.getSettings().setLoadsImagesAutomatically(true);
        }
        if (sharedPreferences.getBoolean("cookie", false)) {
            CookieManager.getInstance().setAcceptCookie(false);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }
        if (sharedPreferences.getBoolean("sound", false)) {
            setSound(false);
        } else {
            setSound(true);
        }
        if (sharedPreferences.getBoolean("adblock", false)) {
           setmAdBlock(true);
        } else {
            setmAdBlock(false);
        }



    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void printPDF () {
        String title = webView.getUrl();
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printAdapter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            printAdapter = webView.createPrintDocumentAdapter(title);
        }
        Objects.requireNonNull(printManager).print(title, printAdapter, new PrintAttributes.Builder().build());
        sharedPreferences.edit().putBoolean("pdf_create", true).commit();
    }

    public void appInfo(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(WebActivity.this);
        alertDialog.setTitle(getString(R.string.app_name));
        alertDialog.setMessage(getString(R.string.app_info));
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setPositiveButton(getString(R.string.positive_button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.cancel();
            }
        });
        alertDialog.setNegativeButton(getString(R.string.button_git), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                webView.loadUrl("https://github.com/DemOrex/MyWeb.git");
                dialog.cancel();
            }
        });
        alertDialog.show();
    }


}

