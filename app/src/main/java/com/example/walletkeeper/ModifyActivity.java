package com.example.walletkeeper;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.walletkeeper.packages.Database;
import com.example.walletkeeper.packages.Time;

public class ModifyActivity extends AppCompatActivity {

    private EditText money, comments;
    private Button button;
    private TextView date;

    private ImageView p11, p12, p13, p21, p22, p23, p31, p32, p33;
    private int choose;

    private Time time, time2;
    private String[] T;
    //数据库创建或打开
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        Bundle bundle = getIntent().getExtras();
        String bTime = bundle.getString("Time");
        String bComments = bundle.getString("Comments");
        String bMoney = "" + bundle.getInt("Money");
        int bType = bundle.getInt("Type");


        date = findViewById(R.id.date);
        money = findViewById(R.id.money);
        comments = findViewById(R.id.comments);
        button = findViewById((R.id.button));
        p11 = findViewById(R.id.p11);
        p12 = findViewById(R.id.p12);
        p13 = findViewById(R.id.p13);
        p21 = findViewById(R.id.p21);
        p22 = findViewById(R.id.p22);
        p23 = findViewById(R.id.p23);
        p31 = findViewById(R.id.p31);
        p32 = findViewById(R.id.p32);
        p33 = findViewById(R.id.p33);


        //初始化
        choose = bType;
        Origin();
        if (choose != 0) {
            if (choose == 1) {
                p11.setImageDrawable(getResources().getDrawable(R.drawable.try21));
            }
            if (choose == 2) {
                p12.setImageDrawable(getResources().getDrawable(R.drawable.try22));
            }
            if (choose == 3) {
                p13.setImageDrawable(getResources().getDrawable(R.drawable.try23));
            }
            if (choose == 4) {
                p21.setImageDrawable(getResources().getDrawable(R.drawable.try24));
            }
            if (choose == 5) {
                p22.setImageDrawable(getResources().getDrawable(R.drawable.try25));
            }
            if (choose == 6) {
                p23.setImageDrawable(getResources().getDrawable(R.drawable.try26));
            }
            if (choose == 7) {
                p31.setImageDrawable(getResources().getDrawable(R.drawable.try27));
            }
            if (choose == 8) {
                p32.setImageDrawable(getResources().getDrawable(R.drawable.try28));
            }
            if (choose == 9) {
                p33.setImageDrawable(getResources().getDrawable(R.drawable.try29));
            }
        }

        money.setText(bMoney);
        comments.setText(bComments);

        time = new Time();
        T = Time_to_intList(bTime);

        setTime();
        //单选图片
        p11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == p11) {
                    Origin();
                    if (choose != 1) {
                        choose = 1;
                        p11.setImageDrawable(getResources().getDrawable(R.drawable.try21));
                    } else {
                        choose = 0;
                    }
                }
            }
        });
        p12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == p12) {
                    Origin();
                    if (choose != 2) {
                        choose = 2;
                        p12.setImageDrawable(getResources().getDrawable(R.drawable.try22));
                    } else {
                        choose = 0;
                    }
                }
            }
        });
        p13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == p13) {
                    Origin();
                    if (choose != 3) {
                        choose = 3;
                        p13.setImageDrawable(getResources().getDrawable(R.drawable.try23));
                    } else {
                        choose = 0;
                    }
                }
            }
        });
        p21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == p21) {
                    Origin();
                    if (choose != 4) {
                        choose = 4;
                        p21.setImageDrawable(getResources().getDrawable(R.drawable.try24));
                    } else {
                        choose = 0;
                    }
                }
            }
        });
        p22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == p22) {
                    Origin();
                    if (choose != 5) {
                        choose = 5;
                        p22.setImageDrawable(getResources().getDrawable(R.drawable.try25));
                    } else {
                        choose = 0;
                    }
                }
            }
        });
        p23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == p23) {
                    Origin();
                    if (choose != 6) {
                        choose = 6;
                        p23.setImageDrawable(getResources().getDrawable(R.drawable.try26));
                    } else {
                        choose = 0;
                    }
                }
            }
        });
        p31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == p31) {
                    Origin();
                    if (choose != 7) {
                        choose = 7;
                        p31.setImageDrawable(getResources().getDrawable(R.drawable.try27));
                    } else {
                        choose = 0;
                    }
                }
            }
        });
        p32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == p32) {
                    Origin();
                    if (choose != 8) {
                        choose = 8;
                        p32.setImageDrawable(getResources().getDrawable(R.drawable.try28));
                    } else {
                        choose = 0;
                    }
                }
            }
        });
        p33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == p33) {
                    Origin();
                    if (choose != 9) {
                        choose = 9;
                        p33.setImageDrawable(getResources().getDrawable(R.drawable.try29));
                    } else {
                        choose = 0;
                    }
                }
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ModifyActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                time.year = year;
                                time.month = month + 1;
                                time.day = dayOfMonth;
                                setTime();
                            }
                        },
                        time.year, time.month - 1, time.day);
                datePickerDialog.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == button) {
                    String num = money.getText().toString();
                    if (choose == 0) {
                        String s = "Please choose consumption type!";
                        Toast.makeText(ModifyActivity.this, s, Toast.LENGTH_LONG).show();
                    } else if (num.equals("")) {
                        String s = "Please enter the consumption amount!";
                        Toast.makeText(ModifyActivity.this, s, Toast.LENGTH_LONG).show();
                    } else {
                        //time=new Time();
                        int m = Integer.parseInt(money.getText().toString());
                        String c = comments.getText().toString();
                        if (c.equals("")) {
                            c = "No comments.";
                        }
                        /*
                        String time=""+t.year+"年"+t.month+"月"+t.day+"日";
                        time=time+"\n"+t.hour+":"+t.minute+":"+t.second;
                        time=time+"\n"+money.getText()+"元\n"+comments.getText();
                        */
                        //打开数据库
                        database = new Database(ModifyActivity.this, "account", null, 4);
                        SQLiteDatabase db = database.getWritableDatabase();
                        String D = "year=? and " +
                                "month=? and " +
                                "day=? and " +
                                "hour=? and " +
                                "minute=? and " +
                                "second=?";
                        db.delete("account", D, T);
                        Insert(db, time, m, c);
                        Toast.makeText(ModifyActivity.this, "Modify successfully!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(getActivity(), time, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ModifyActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void Origin() {
        p11.setImageDrawable(getResources().getDrawable(R.drawable.try11));
        p12.setImageDrawable(getResources().getDrawable(R.drawable.try12));
        p13.setImageDrawable(getResources().getDrawable(R.drawable.try13));
        p21.setImageDrawable(getResources().getDrawable(R.drawable.try14));
        p22.setImageDrawable(getResources().getDrawable(R.drawable.try15));
        p23.setImageDrawable(getResources().getDrawable(R.drawable.try16));
        p31.setImageDrawable(getResources().getDrawable(R.drawable.try17));
        p32.setImageDrawable(getResources().getDrawable(R.drawable.try18));
        p33.setImageDrawable(getResources().getDrawable(R.drawable.try19));

    }

    private void Insert(SQLiteDatabase db, Time t, int m, String c) {
        ContentValues values = new ContentValues();
        values.put("year", t.year);
        values.put("month", t.month);
        values.put("day", t.day);
        values.put("hour", t.hour);
        values.put("minute", t.minute);
        values.put("second", t.second);
        values.put("money", m);
        values.put("type", choose);
        values.put("comment", c);
        db.insert("account", null, values);
        values.clear();
        //Toast.makeText(getActivity(),"good",Toast.LENGTH_LONG).show();
    }

    private void setTime() {
        String st = "" + time.year + " - " +
                String.format("%2d", time.month).replace(" ", "0") +
                " - " + String.format("%2d", time.day).replace(" ", "0");
        date.setText(st);
    }

    public String[] Time_to_intList(String str) {
        String l[] = new String[6];
        l[0] = str.substring(0, 4);
        l[1] = str.substring(5, 7);
        l[2] = str.substring(8, 10);
        l[3] = str.substring(11, 13);
        l[4] = str.substring(14, 16);
        l[5] = str.substring(17, 19);
        time.year = Integer.parseInt(l[0]);
        time.month = Integer.parseInt(l[1]);
        time.day = Integer.parseInt(l[2]);
        time.hour = Integer.parseInt(l[3]);
        time.minute = Integer.parseInt(l[4]);
        time.second = Integer.parseInt(l[5]);
        return l;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the aaction bar if it is present
        getMenuInflater().inflate(R.menu.cancel_modify_menu, menu);
        //getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_cancel:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }
}
