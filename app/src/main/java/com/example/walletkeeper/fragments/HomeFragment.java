package com.example.walletkeeper.fragments;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.walletkeeper.R;
import com.example.walletkeeper.packages.Database;
import com.example.walletkeeper.packages.Time;

public class HomeFragment extends Fragment {

    private EditText money, comments;
    private Button button;
    private TextView date;

    private ImageView p11, p12, p13, p21, p22, p23, p31, p32, p33;
    private int choose;

    private Time time;


    //数据库创建或打开
    private Database database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        date = getActivity().findViewById(R.id.date);
        money = getActivity().findViewById(R.id.money);
        comments = getActivity().findViewById(R.id.comments);
        button = getActivity().findViewById((R.id.button));
        p11 = getActivity().findViewById(R.id.p11);
        p12 = getActivity().findViewById(R.id.p12);
        p13 = getActivity().findViewById(R.id.p13);
        p21 = getActivity().findViewById(R.id.p21);
        p22 = getActivity().findViewById(R.id.p22);
        p23 = getActivity().findViewById(R.id.p23);
        p31 = getActivity().findViewById(R.id.p31);
        p32 = getActivity().findViewById(R.id.p32);
        p33 = getActivity().findViewById(R.id.p33);
        //初始化
        Origin();
        time = new Time();
        setTime();

        choose = 0;
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                time.year = year;
                                time.month = month + 1;
                                time.day = dayOfMonth;
                                setTime();
                            }
                        },
                        time.year, time.month, time.day);
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
                        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                    } else if (num.equals("")) {
                        String s = "Please enter the consumption amount!";
                        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
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
                        database = new Database(getActivity(), "account", null, 4);
                        SQLiteDatabase db = database.getWritableDatabase();
                        Insert(db, time, m, c);
                        Toast.makeText(getActivity(), "Add successfully!", Toast.LENGTH_LONG).show();
                        //Toast.makeText(getActivity(), time, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void setTime() {
        String st = "" + time.year + " - " +
                String.format("%2d", time.month).replace(" ", "0") +
                " - " + String.format("%2d", time.day).replace(" ", "0");
        date.setText(st);
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

}


