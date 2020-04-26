package com.example.walletkeeper.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.walletkeeper.ModifyActivity;
import com.example.walletkeeper.R;
import com.example.walletkeeper.packages.Database;

import java.util.Arrays;

public class DashboardFragment extends Fragment {


    ListView mListView;
    ListAdapter mListAdapter;

    //数据库创建
    private Database database;
    private SQLiteDatabase db;

    private Button button1;


    String[] Time = {};
    String[] Comments = {};
    String[] Money = {};
    int[] Type = {};
    int[] typeNum = {};

    // 搜索设置
    int all = 0;
    int Syear = 0;
    int Smonth = 0;
    int Sday = 0;
    String[] Years;
    String[] Months;
    Spinner ySpinner;
    Spinner mSpinner;

    final int[] flags = {R.drawable.try11, R.drawable.try11, R.drawable.try12,
            R.drawable.try13, R.drawable.try14, R.drawable.try15, R.drawable.try16,
            R.drawable.try17, R.drawable.try18, R.drawable.try19};


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        database = new Database(getActivity(), "account", null, 4);
        db = database.getReadableDatabase();
        all = 0;

        Years = getResources().getStringArray(R.array.years_array);
        Months = getResources().getStringArray(R.array.months_array);
        ySpinner = (Spinner) getActivity().findViewById(R.id.spinnerYear);
        mSpinner = (Spinner) getActivity().findViewById(R.id.spinnerMonth);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Years);
        ySpinner.setAdapter(adapter1);
        ySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0,
                                               View arg1, int arg2, long arg3) {
                        int index = arg0.getSelectedItemPosition();
                        if (index != 0) {
                            Syear = 2021 - index;
                        } else {
                            Syear = 0;
                        }
                        if (Smonth != 0 || Syear != 0) {
                            all = 1;
                        } else {
                            all = 0;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        Syear = 0;
                    }
                });
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Months);
        mSpinner.setAdapter(adapter2);
        mSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0,
                                               View arg1, int arg2, long arg3) {
                        int index = arg0.getSelectedItemPosition();
                        Smonth = index;
                        if (Smonth != 0 || Syear != 0) {
                            all = 1;
                        } else {
                            all = 0;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        Smonth = 0;
                    }
                });

        button1 = getActivity().findViewById(R.id.button1);
        show_data();
        //从数据库查询获取所有数据
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == button1) {
                    show_data();
                }
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this record?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getActivity(), Time[position], Toast.LENGTH_LONG).show();
                                String t[] = Time_to_intList(Time[position]);
                                String D = "year=? and " +
                                        "month=? and " +
                                        "day=? and " +
                                        "hour=? and " +
                                        "minute=? and " +
                                        "second=?";
                                db.delete("account", D, t);
                                Toast.makeText(getActivity(), "Delete successfully!", Toast.LENGTH_LONG).show();
                                show_data();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Do you want to modify this record?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getActivity(), "typeNum[position]", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getActivity(), ModifyActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("Time", Time[position]);
                                bundle.putInt("Money", Integer.parseInt(Money[position]));
                                bundle.putInt("Type", typeNum[position]);
                                bundle.putString("Comments", Comments[position]);
                                intent.putExtras(bundle);

                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }

        });


    }

    public void show_data() {
        Time = new String[0];
        Comments = new String[0];
        Money = new String[0];
        Type = new int[0];
        typeNum = new int[0];

        int ok = 0;
        String timeOrder = "year desc, month desc, day desc, hour desc, minute desc, second desc";
        //String timeOrder="money ASC";
        String selection = null;
        //String sss=""+all+" "+Syear+" "+Smonth;
        //Toast.makeText(getActivity(), sss, Toast.LENGTH_LONG).show();
        if (all != 0) {
            if (Syear != 0 && Smonth != 0) {
                selection = "year='" + Syear + "' and month='" + Smonth + "'";
            } else if (Syear != 0) {
                selection = "year='" + Syear + "'";
            } else if (Smonth != 0) {
                selection = "month='" + Smonth + "'";
            }
        }
        Cursor cursor = db.query("account", null, selection, null, null, null, timeOrder);
        boolean succeed = cursor.moveToFirst();  //从数据库第一行开始查
        //Toast.makeText(getActivity(), "222", Toast.LENGTH_LONG).show();
        while (succeed) {
            ok = 1;
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            int hour = cursor.getInt(cursor.getColumnIndex("hour"));
            int minute = cursor.getInt(cursor.getColumnIndex("minute"));
            int second = cursor.getInt(cursor.getColumnIndex("second"));

            String money = "- " + cursor.getInt(cursor.getColumnIndex("money"));
            String comments = cursor.getString(cursor.getColumnIndex("comment"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            //String s = "" + money + "元" + "\n" + comments + "\n" + "type=" + type;
            //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            //String t=""+year+"-"+month+"-"+day;
            String t = "" + year + "-" + String.format("%2d", month).replace(" ", "0") +
                    "-" + String.format("%2d", day).replace(" ", "0") +
                    " " + String.format("%2d", hour).replace(" ", "0") +
                    ":" + String.format("%2d", minute).replace(" ", "0") +
                    ":" + String.format("%2d", second).replace(" ", "0");
            Time = Arrays.copyOf(Time, Time.length + 1);
            Time[Time.length - 1] = t;
            Comments = Arrays.copyOf(Comments, Comments.length + 1);
            Comments[Comments.length - 1] = comments;
            Money = Arrays.copyOf(Money, Money.length + 1);
            Money[Money.length - 1] = money;
            Type = Arrays.copyOf(Type, Type.length + 1);
            Type[Type.length - 1] = flags[type];
            typeNum = Arrays.copyOf(typeNum, typeNum.length + 1);
            typeNum[typeNum.length - 1] = type;
            //查完一条之后调用cursor.moveToNext()把cursor的位置移动到下一条
            if (cursor.isLast()) {
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();
        mListView = getActivity().findViewById(R.id.listView);
        mListAdapter = new
                com.example.walletkeeper.packages.ListAdapter(getActivity(), Time, Comments, Money, Type);
        mListView.setAdapter(mListAdapter);
        if (ok == 0) {
            Toast.makeText(getActivity(), "no recoird!", Toast.LENGTH_LONG).show();
        }
    }

    public String[] Time_to_intList(String str) {
        String l[] = new String[6];
        l[0] = str.substring(0, 4);
        l[1] = str.substring(5, 7);
        l[2] = str.substring(8, 10);
        l[3] = str.substring(11, 13);
        l[4] = str.substring(14, 16);
        l[5] = str.substring(17, 19);
        return l;
    }
}
