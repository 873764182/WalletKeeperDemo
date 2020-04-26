package com.example.walletkeeper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.walletkeeper.packages.Database;
import com.example.walletkeeper.packages.IncomeMapper;

import java.util.ArrayList;
import java.util.List;

public class IncomeActivity extends AppCompatActivity {

    /**
     * 上下文对象
     */
    private Activity activity;

    /**
     * 显示总收入
     */
    private TextView textView;

    /**
     * 数据列表
     */
    private ListView listView;

    /**
     * 数据库操作对象
     */
    private SQLiteDatabase db;

    /**
     * 列表实际数据
     */
    private List<IncomeMapper> incomeMapperList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        this.activity = this;
        textView = findViewById(R.id.tvMoney);
        listView = findViewById(R.id.listView);

        db = new Database(this, "account", null, 4).getWritableDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }

    @SuppressLint("SetTextI18n")
    private void initList() {
        incomeMapperList.clear();

        Cursor cursor = db.query("income",
                null, null, null, null, null, "insTime desc");

        Integer total = 0;

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex("id"));
            String insTime = cursor.getString(cursor.getColumnIndex("insTime"));
            String updTime = cursor.getString(cursor.getColumnIndex("updTime"));
            Integer money = cursor.getInt(cursor.getColumnIndex("money"));
            String comment = cursor.getString(cursor.getColumnIndex("comment"));

            incomeMapperList.add(new IncomeMapper(id, insTime, updTime, money, comment));

            total += money;
        }

        textView.setText("Total income: " + total.toString());

        listView.setAdapter(new IncomeListAdapter());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(activity).setTitle("system hint").setMessage("delete or not")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.execSQL("DELETE FROM income WHERE id = " + incomeMapperList.get(position).id);
                                initList();
                            }
                        }).setNegativeButton("cancel", null).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    class IncomeListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return incomeMapperList.size();
        }

        @Override
        public Object getItem(int position) {
            return incomeMapperList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return incomeMapperList.get(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getApplication()).inflate(R.layout.layout_income_item, parent, false);
                viewHolder.title = convertView.findViewById(R.id.title);
                viewHolder.result = convertView.findViewById(R.id.result);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.title.setText(incomeMapperList.get(position).comment);
            viewHolder.result.setText("+" + incomeMapperList.get(position).money);
            return convertView;

        }
    }

    private static class ViewHolder {
        TextView title;
        TextView result;
    }

    public void doAdd(View view) {
        View dialogView = LayoutInflater
                .from(getApplication()).inflate(R.layout.layout_income_dialog, null);
        final EditText editText1 = dialogView.findViewById(R.id.text);
        final EditText editText2 = dialogView.findViewById(R.id.number);
        new AlertDialog.Builder(this).setTitle("Please enter a income")
                .setView(dialogView)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (editText1.getText().toString().length() <= 0) {
                            Toast.makeText(getApplication(), "please enter", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (editText2.getText().toString().length() <= 0) {
                            Toast.makeText(getApplication(), "please enter", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ContentValues values = new ContentValues();
                        values.put("insTime", System.currentTimeMillis());
                        values.put("updTime", System.currentTimeMillis());
                        values.put("money", editText2.getText().toString());
                        values.put("comment", editText1.getText().toString());
                        db.insert("income", null, values);
                        values.clear();
                        Toast.makeText(getApplication(), "added successfully", Toast.LENGTH_SHORT).show();
                        initList();
                    }
                }).setNegativeButton("cancel", null).show();
    }
}
