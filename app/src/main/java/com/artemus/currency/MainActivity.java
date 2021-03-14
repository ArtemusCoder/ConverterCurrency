package com.artemus.currency;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements bottom_sheet_main.BottomSheetListener, bottom_sheet_second.BottomSheetListener {

    int minus=0;
    TextView mainText;
    TextView currencyText;
    double numMain = 0.0;
    double numCurrency = 0.0;
    int level = 1;
    Byte mode = 1;
    double currency = 64;
    int numADD = 0;
    ProgressDialog pd;
    Vibrator vibrator;

    Button buttonOpenBottomMain;
    Button buttonOpenBottomSecond;

    String mode_currency_main = "usd";
    String mode_currency_second = "rub";

    String add_str = "&oq=INR+to+USD";

    String sourceData = "https://www.google.com/search?q=";

    SharedPref sharedpref;

    private BottomSheetBehavior sheetBehavior;
    private LinearLayout bottom_sheet;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if (sharedpref.loadNightMode()){
            setTheme(R.style.DarkTheme);
        }
        else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainText = findViewById(R.id.textview_main);
        currencyText = findViewById(R.id.textview_currency);
        Button btn0 = findViewById(R.id.button0);
        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);
        Button btn5 = findViewById(R.id.button5);
        Button btn6 = findViewById(R.id.button6);
        Button btn7 = findViewById(R.id.button7);
        Button btn8 = findViewById(R.id.button8);
        Button btn9 = findViewById(R.id.button9);
        Button delete = findViewById(R.id.button_del);
        Button dot = findViewById(R.id.button_dot);
        final Button clear = findViewById(R.id.button_cancel);
        TextView btnHit = findViewById(R.id.btnHit);
        Button btn_Click1 = findViewById(R.id.button_click_main);
        Button btn_Click2 = findViewById(R.id.button_click_currency);
        buttonOpenBottomMain = findViewById(R.id.button_currency_main);
        buttonOpenBottomSecond = findViewById(R.id.button_currency);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.appbar);
        toolbar.setTitle("Конвертер валют");
        setSupportActionBar(toolbar);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        buttonOpenBottomMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottom_sheet_main bottomSheetMain = new bottom_sheet_main();
                bottomSheetMain.show(getSupportFragmentManager(), "BottomSheetMain");
            }
        });

        buttonOpenBottomSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottom_sheet_second bottomSheetSecond = new bottom_sheet_second();
                bottomSheetSecond.show(getSupportFragmentManager(), "BottomSheetSecond");
            }
        });


        btn_Click1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Vibration();
                mode = 1;
                mainText.setText("1");
                mainText.setTextColor(getResources().getColor(R.color.blue_input));
                currencyText.setTextColor(Color.BLACK);
                currencyText.setText(fmt(1/currency));
                numCurrency = 0.0;
                numMain = 0.0;
                level = 1;
            }
        });

        btn_Click2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                Vibration();
                mode = 0;
                currencyText.setText("1");
                mainText.setText(fmt(1*currency));
                currencyText.setTextColor(getResources().getColor(R.color.blue_input));
                mainText.setTextColor(Color.BLACK);
                numCurrency = 0.0;
                numMain = 0.0;
                level = 1;
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibration();
                if (level>0){
                    minus = (int)numMain%10;
                    numMain = (numMain - minus)/10;
                    if (mode==1) {
                        mainText.setText(fmt(numMain));
                        numCurrency = numMain / currency;
                        currencyText.setText(fmt(numCurrency));
                    }else if(mode == 0){
                        currencyText.setText(fmt(numMain));
                        numCurrency = numMain * currency;
                        mainText.setText(fmt(numCurrency));
                    }
                }
            }
        });

        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibration();
                MyTask mt = new MyTask();
                mt.execute();
                clear_all();
            }


        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibration();
                clear_all();
            }
        });

        dot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Vibration();
                if (level != -1) {
                    mainText.append(".");
                    level=-1;
                }
            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibration();
                numADD = 0;
                if (level>-3)
                    add(numADD);            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibration();
                numADD = 1;
                if (level>-3)
                    add(numADD);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibration();
                numADD = 2;
                if (level>-3)
                    add(numADD);            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibration();
                numADD = 3;
                if (level>-3)
                    add(numADD);            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibration();
                numADD = 4;
                if (level>-3)
                    add(numADD);            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibration();
                numADD = 5;
                if (level>-3)
                    add(numADD);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibration();
                numADD = 6;
                if (level>-3)
                    add(numADD);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibration();
                numADD = 7;
                if (level>-3)
                    add(numADD);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibration();
                numADD = 8;
                if (level>-3)
                    add(numADD);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibration();
                numADD = 9;
                if (level>-3)
                    add(numADD);
            }
        });

    }

    void add(int x) {
        if (level > 0) {
            numMain = numMain * 10 + x;
        } else {
            numMain = numMain + (x * (Math.pow(10, level)));
            level--;
        }
        if (mode == 1) {
            mainText.setText(fmt(numMain));
            numCurrency = numMain / currency;
            currencyText.setText(fmt(numCurrency));
        }else{
            currencyText.setText(fmt(numMain));
            numCurrency = numMain * currency;
            mainText.setText(fmt(numCurrency));
        }
    }

    @SuppressLint("DefaultLocale")
    public static String fmt(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%.02f",d);
    }

    void clear_all()
    {
        level = 1;
        numMain = 0.0;
        numCurrency = 0.0;
        mainText.setText("0");
        currencyText.setText("0");
    }

    @Override
    public void onButtonClicked(String text, String name) {
        mode_currency_second = text;
        buttonOpenBottomMain.setText(name);
        MyTask mt = new MyTask();
        mt.execute();
        clear_all();

    }

    @Override
    public void onButtonClickedSecond(String text, String name) {
        mode_currency_main = text;
        buttonOpenBottomSecond.setText(name);
        MyTask mt = new MyTask();
        mt.execute();
        clear_all();
    }

    class MyTask extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("АГА, ПОДОЖДИТЕ СЕКУНДУ.....");
            pd.setCancelable(false);
            pd.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            Document doc = null;
            try {
                String link = sourceData + mode_currency_main + "+" + mode_currency_second + add_str;
                doc = Jsoup.connect(link).get();
            } catch (IOException e) {
                //Если не получилось считать
                e.printStackTrace();
            }

            if (doc!=null) {
                Element check = doc.select("span.DFlfde.SwHCTb").first();
                char[] num = check.text().toCharArray();
                for (int i = 0; i<check.text().length(); i++){
                    if (num[i] == ',')
                    {
                        num[i] = '.';
                    }
                }
                currency = Double.parseDouble(String.valueOf(num));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tools, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings_item) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    void Vibration()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            assert vibrator != null;
            vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }
}
