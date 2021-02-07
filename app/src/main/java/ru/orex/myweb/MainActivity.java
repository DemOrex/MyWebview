package ru.orex.myweb;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    SharedPreferences mFirstPrefs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirstPrefs = getSharedPreferences("ru.orex.myweb", MODE_PRIVATE);
        if (mFirstPrefs.getBoolean("firstrun", true)) {
            dialogFirst(getString(R.string.first_run)+getString(R.string.app_info));
            mFirstPrefs.edit().putBoolean("firstrun", false).commit();
        }else {
            handleIntent(this.getIntent());
        }
    }


    private void handleIntent(Intent intent) {
        Uri uri = this.getIntent().getData();
        if (uri != null) {
            String stringGetLoadUrl = uri.toString();
            Intent intentGetLoadUrl = new Intent((Context)this, WebActivity.class);
            intentGetLoadUrl.putExtra("getLoadUrl", stringGetLoadUrl);
            this.startActivity(intentGetLoadUrl);
            this.finish();
            return;
        }
        this.startActivity(new Intent((Context)this, WebActivity.class));
        this.finish();
    }
    public String dialogFirst(String dialog) {
        AlertDialog alertDialog = new AlertDialog.Builder((Context) this)
                .setIcon(android.R.mipmap.sym_def_app_icon)
                .setTitle(R.string.app_name)
                .setMessage(dialog)
                .setPositiveButton(getString(R.string.positive_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        nextActivity();
                    }
                })
                .show();
        return null;
    }
 public void nextActivity(){
     Intent intent = new Intent(this, WebActivity.class);
     startActivity(intent);
     finish();
 }
}