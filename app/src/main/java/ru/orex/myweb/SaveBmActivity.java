package ru.orex.myweb;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

public class SaveBmActivity extends BlockActivity {
    String saveUrl,saveUrlTitle;
    SharedPreferences sPref;
    ListView myList;


    final String SAVED_BOOK1 = "saved_book1";
    final String SAVED_BOOK1_TITLE = "saved_book1_title";
    final String SAVED_BOOK2 = "saved_book2";
    final String SAVED_BOOK2_TITLE = "saved_book2_title";
    final String SAVED_BOOK3 = "saved_book3";
    final String SAVED_BOOK3_TITLE = "saved_book3_title";
    final String SAVED_BOOK4 = "saved_book4";
    final String SAVED_BOOK4_TITLE = "saved_book4_title";
    final String SAVED_BOOK5 = "saved_book5";
    final String SAVED_BOOK5_TITLE = "saved_book5_title";
    final String SAVED_BOOK6 = "saved_book6";
    final String SAVED_BOOK6_TITLE = "saved_book6_title";
    final String SAVED_BOOK7 = "saved_book7";
    final String SAVED_BOOK7_TITLE = "saved_book7_title";
    final String SAVED_BOOK8 = "saved_book8";
    final String SAVED_BOOK8_TITLE = "saved_book8_title";
    final String SAVED_BOOK9 = "saved_book9";
    final String SAVED_BOOK9_TITLE = "saved_book9_title";
    final String SAVED_BOOK10 = "saved_book10";
    final String SAVED_BOOK10_TITLE = "saved_book10_title";
    final String SAVED_BOOK11 = "saved_book11";
    final String SAVED_BOOK11_TITLE = "saved_book11_title";
    final String SAVED_BOOK12 = "saved_book12";
    final String SAVED_BOOK12_TITLE = "saved_book12_title";
    final String SAVED_BOOK13 = "saved_book13";
    final String SAVED_BOOK13_TITLE = "saved_book13_title";
    final String SAVED_BOOK14 = "saved_book14";
    final String SAVED_BOOK14_TITLE = "saved_book14_title";
    final String SAVED_BOOK15 = "saved_book15";
    final String SAVED_BOOK15_TITLE = "saved_book15_title";
    final String SAVED_BOOK16 = "saved_book16";
    final String SAVED_BOOK16_TITLE = "saved_book16_title";
    final String SAVED_BOOK17 = "saved_book17";
    final String SAVED_BOOK17_TITLE = "saved_book17_title";
    final String SAVED_BOOK18 = "saved_book18";
    final String SAVED_BOOK18_TITLE = "saved_book18_title";
    final String SAVED_BOOK19 = "saved_book19";
    final String SAVED_BOOK19_TITLE = "saved_book19_title";
    final String SAVED_BOOK20 = "saved_book20";
    final String SAVED_BOOK20_TITLE = "saved_book20_title";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bm);


        final String[] myBookTitle = {"1--" + loadBook(SAVED_BOOK1_TITLE), "2--" + loadBook(SAVED_BOOK2_TITLE), "3--" + loadBook(SAVED_BOOK3_TITLE), "4--" + loadBook(SAVED_BOOK4_TITLE), "5--" + loadBook(SAVED_BOOK5_TITLE), "6--" + loadBook(SAVED_BOOK6_TITLE),
                "7--" + loadBook(SAVED_BOOK7_TITLE), "8--" + loadBook(SAVED_BOOK8_TITLE), "9--" + loadBook(SAVED_BOOK9_TITLE), "10--" + loadBook(SAVED_BOOK10_TITLE), "11--" + loadBook(SAVED_BOOK11_TITLE), "12--" + loadBook(SAVED_BOOK12_TITLE),
                "13--" + loadBook(SAVED_BOOK13_TITLE), "14--" + loadBook(SAVED_BOOK14_TITLE), "15--" + loadBook(SAVED_BOOK15_TITLE), "16--" + loadBook(SAVED_BOOK16_TITLE), "17--" + loadBook(SAVED_BOOK17_TITLE),
                "18--" + loadBook(SAVED_BOOK18_TITLE), "19--" + loadBook(SAVED_BOOK19_TITLE), "20--" + loadBook(SAVED_BOOK20_TITLE)};


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        saveUrl = intent.getStringExtra("urlPage");
        saveUrlTitle = intent.getStringExtra("urlTitle");

        setTitle(getResources().getString(R.string.books));

       if (saveUrl != null) {
            if (loadBook(SAVED_BOOK1).equals("")) {
                saveBook(saveUrlTitle, SAVED_BOOK1_TITLE);
                saveBook(saveUrl, SAVED_BOOK1);
                numberBook(1);
                finish();
            } else if (loadBook(SAVED_BOOK2) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK2_TITLE);
                saveBook(saveUrl, SAVED_BOOK2);
                numberBook(2);
                finish();
            } else if (loadBook(SAVED_BOOK3) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK3_TITLE);
                saveBook(saveUrl, SAVED_BOOK3);
                numberBook(3);
                finish();
            } else if (loadBook(SAVED_BOOK4) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK4_TITLE);
                saveBook(saveUrl, SAVED_BOOK4);
                numberBook(4);
                finish();
            } else if (loadBook(SAVED_BOOK5) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK5_TITLE);
                saveBook(saveUrl, SAVED_BOOK5);
                numberBook(5);
                finish();
            } else if (loadBook(SAVED_BOOK6) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK6_TITLE);
                saveBook(saveUrl, SAVED_BOOK6);
                numberBook(6);
                finish();
            } else if (loadBook(SAVED_BOOK7) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK7_TITLE);
                saveBook(saveUrl, SAVED_BOOK7);
                numberBook(7);
                finish();
            } else if (loadBook(SAVED_BOOK8) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK8_TITLE);
                saveBook(saveUrl, SAVED_BOOK8);
                numberBook(8);
                finish();
            } else if (loadBook(SAVED_BOOK9) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK9_TITLE);
                saveBook(saveUrl, SAVED_BOOK9);
                numberBook(9);
                finish();
            } else if (loadBook(SAVED_BOOK10) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK10_TITLE);
                saveBook(saveUrl, SAVED_BOOK10);
                numberBook(10);
                finish();
            } else if (loadBook(SAVED_BOOK11) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK11_TITLE);
                saveBook(saveUrl, SAVED_BOOK11);
                numberBook(11);
                finish();
            } else if (loadBook(SAVED_BOOK12) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK12_TITLE);
                saveBook(saveUrl, SAVED_BOOK12);
                numberBook(12);
                finish();
            } else if (loadBook(SAVED_BOOK13) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK13_TITLE);
                saveBook(saveUrl, SAVED_BOOK13);
                numberBook(13);
                finish();
            } else if (loadBook(SAVED_BOOK14) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK14_TITLE);
                saveBook(saveUrl, SAVED_BOOK14);
                numberBook(14);
                finish();
            } else if (loadBook(SAVED_BOOK15) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK15_TITLE);
                saveBook(saveUrl, SAVED_BOOK15);
                numberBook(15);
                finish();
            } else if (loadBook(SAVED_BOOK16) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK16_TITLE);
                saveBook(saveUrl, SAVED_BOOK16);
                numberBook(16);
                finish();
            } else if (loadBook(SAVED_BOOK17) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK17_TITLE);
                saveBook(saveUrl, SAVED_BOOK17);
                numberBook(17);
                finish();
            } else if (loadBook(SAVED_BOOK18) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK18_TITLE);
                saveBook(saveUrl, SAVED_BOOK18);
                numberBook(18);
                finish();
            } else if (loadBook(SAVED_BOOK19) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK19_TITLE);
                saveBook(saveUrl, SAVED_BOOK19);
                numberBook(19);
                finish();
            } else if (loadBook(SAVED_BOOK20) == "") {
                saveBook(saveUrlTitle, SAVED_BOOK20_TITLE);
                saveBook(saveUrl, SAVED_BOOK20);
                numberBook(20);
                finish();
            } else {
                toast(getString(R.string.book));
                finish();
            }

        }

        myList = (ListView) findViewById(R.id.myListView);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, myBookTitle);




        myList.setAdapter(adapter);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (position == 0) {
                    sendBook(loadBook(SAVED_BOOK1));}
                if (position == 1) {
                    sendBook(loadBook(SAVED_BOOK2));}
                if (position == 2) {
                    sendBook(loadBook(SAVED_BOOK3));}
                if (position == 3) {
                    sendBook(loadBook(SAVED_BOOK4));}
                if (position == 4) {
                    sendBook(loadBook(SAVED_BOOK5));}
                if (position == 5) {
                    sendBook(loadBook(SAVED_BOOK6));}
                if (position == 6) {
                    sendBook(loadBook(SAVED_BOOK7));}
                if (position == 7) {
                    sendBook(loadBook(SAVED_BOOK8));}
                if (position == 8) {
                    sendBook(loadBook(SAVED_BOOK9));}
                if (position == 9) {
                    sendBook(loadBook(SAVED_BOOK10));}
                if (position == 10) {
                    sendBook(loadBook(SAVED_BOOK11));}
                if (position == 11) {
                    sendBook(loadBook(SAVED_BOOK12));}
                if (position == 12) {
                    sendBook(loadBook(SAVED_BOOK13));}
                if (position == 13) {
                    sendBook(loadBook(SAVED_BOOK14));}
                if (position == 14) {
                    sendBook(loadBook(SAVED_BOOK15));}
                if (position == 15) {
                    sendBook(loadBook(SAVED_BOOK16));}
                if (position == 16) {
                    sendBook(loadBook(SAVED_BOOK17));}
                if (position == 17) {
                    sendBook(loadBook(SAVED_BOOK18));}
                if (position == 18) {
                    sendBook(loadBook(SAVED_BOOK19));}
                if (position == 19) {
                    sendBook(loadBook(SAVED_BOOK20));}
            }
        });

      /*  myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String str1 = Integer.toString(position);
                // String result = String.valueOf(myBookUrl[position]);
                toast(myBookTitle[position]);
              //  for (int i = 0; i < myBookUrl.length; i++) {
                  //  String strToPrint = "book[" + i + "]=" + myBookUrl[i];
               // }
                toast(str1);
                if (position == 0) {
                    sendBook(loadBook(SAVED_BOOK1));
                }
            }
        });*/

       myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                // TODO Auto-generated method stub

                AlertDialog alertDialog = new AlertDialog.Builder((Context)SaveBmActivity.this)
                        //set icon
                        .setIcon(android.R.drawable.ic_dialog_alert)
                       //set title
                        .setTitle(R.string.app_name)
                      //set message
                        .setMessage(getString(R.string.book_delete))
                       //set positive button
                        .setPositiveButton(getString(R.string.button_yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked
                             if(pos==0){saveBook("",SAVED_BOOK1);
                                 saveBook("",SAVED_BOOK1_TITLE);
                             restartActivity(SaveBmActivity.this);}
                                if(pos==1){saveBook("",SAVED_BOOK2);
                                    saveBook("",SAVED_BOOK2_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                                if(pos==2){saveBook("",SAVED_BOOK3);
                                    saveBook("",SAVED_BOOK3_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                                if(pos==3){saveBook("",SAVED_BOOK4);
                                    saveBook("",SAVED_BOOK4_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                                if(pos==4){saveBook("",SAVED_BOOK5);
                                    saveBook("",SAVED_BOOK5_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                                if(pos==5){saveBook("",SAVED_BOOK6);
                                    saveBook("",SAVED_BOOK6_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                                if(pos==6){saveBook("",SAVED_BOOK7);
                                    saveBook("",SAVED_BOOK7_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                                if(pos==7){saveBook("",SAVED_BOOK8);
                                    saveBook("",SAVED_BOOK8_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                                if(pos==8){saveBook("",SAVED_BOOK9);
                                    saveBook("",SAVED_BOOK9_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                                if(pos==9){saveBook("",SAVED_BOOK10);
                                    saveBook("",SAVED_BOOK10_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                                if(pos==10){saveBook("",SAVED_BOOK11);
                                    saveBook("",SAVED_BOOK11_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                                if(pos==11){saveBook("",SAVED_BOOK12);
                                    saveBook("",SAVED_BOOK12_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                                if(pos==12){saveBook("",SAVED_BOOK13);
                                    saveBook("",SAVED_BOOK13_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                                if(pos==13){saveBook("",SAVED_BOOK14);
                                    saveBook("",SAVED_BOOK14_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                                if(pos==14){saveBook("",SAVED_BOOK15);
                                    saveBook("",SAVED_BOOK15_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                                if(pos==15){saveBook("",SAVED_BOOK16);
                                    saveBook("",SAVED_BOOK16_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                                if(pos==16){saveBook("",SAVED_BOOK17);
                                    saveBook("",SAVED_BOOK17_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                                if(pos==17){saveBook("",SAVED_BOOK18);
                                    saveBook("",SAVED_BOOK18_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                                if(pos==18){saveBook("",SAVED_BOOK19);
                                    saveBook("",SAVED_BOOK19_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                                if(pos==19){saveBook("",SAVED_BOOK20);
                                    saveBook("",SAVED_BOOK20_TITLE);
                                    restartActivity(SaveBmActivity.this);}
                            }
                        })
                        //set negative button
                        .setNegativeButton(getString(R.string.button_no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked

                            }
                        })
                        .show();



                return true;
            }
        });
    }

    void saveBook(String string, String  history) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(history, string);
        editor.commit();
    }
    String loadBook(String stringHistory) {
        sPref = getPreferences(0);
        return sPref.getString(stringHistory, "");
    }

    public void sendBook(String urlBook) {
        Intent intent = new Intent();
        intent.putExtra("urlBook", urlBook);
        setResult(RESULT_OK, intent);
        finish();

    }
    @Override
    public void onBackPressed() {
        sendBook(loadBook(""));
    }

    protected void onDestroy() {
        super.onDestroy();
        saveUrl=null;
        saveUrlTitle=null;
    }

}