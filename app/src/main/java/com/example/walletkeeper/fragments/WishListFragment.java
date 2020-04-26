package com.example.walletkeeper.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
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
import com.example.walletkeeper.IncomeActivity;
import com.example.walletkeeper.R;
import com.example.walletkeeper.packages.Database;
import com.example.walletkeeper.packages.WishMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WishListFragment extends Fragment {

    /**
     * 根视图节点
     */
    private View rootView;

    /**
     * 主要控件
     */
    private TextView expend, income, balanceView;
    private Button addIncome, addWish;
    private ListView listView;

    /**
     * 数据库操作对象
     */
    private SQLiteDatabase db;

    /**
     * 心愿列表
     */
    private List<WishMapper> wishMapperList = new ArrayList<>();

    /**
     * 支出的总数量 / 收入的总数量 / 剩余
     */
    private Integer expendValue = 0, incomeValue = 0, balance = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_wishlist, container, false);

            expend = rootView.findViewById(R.id.expend);
            income = rootView.findViewById(R.id.income);
            balanceView = rootView.findViewById(R.id.balance);

            addIncome = rootView.findViewById(R.id.add_income);
            addWish = rootView.findViewById(R.id.add_wish);
            listView = rootView.findViewById(R.id.listView);

            db = new Database(getActivity(), "account", null, 4).getWritableDatabase();

            addIncome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Objects.requireNonNull(getActivity()).startActivity(new Intent(getActivity(), IncomeActivity.class));
                }
            });
            addWish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View dialogView = LayoutInflater
                            .from(getActivity()).inflate(R.layout.layout_income_dialog, null);
                    final EditText editText1 = dialogView.findViewById(R.id.text);
                    final EditText editText2 = dialogView.findViewById(R.id.number);
                    new AlertDialog.Builder(getActivity()).setTitle("Please enter a wish")
                            .setView(dialogView)
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (editText1.getText().toString().length() <= 0) {
                                        Toast.makeText(getActivity(), "please enter", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (editText2.getText().toString().length() <= 0) {
                                        Toast.makeText(getActivity(), "please enter", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    ContentValues values = new ContentValues();
                                    values.put("insTime", System.currentTimeMillis());
                                    values.put("money", editText2.getText().toString());
                                    values.put("comment", editText1.getText().toString());
                                    db.insert("wish", null, values);
                                    values.clear();
                                    Toast.makeText(getActivity(), "added successfully", Toast.LENGTH_SHORT).show();
                                    initList();
                                }
                            }).setNegativeButton("cancel", null).show();
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    new AlertDialog.Builder(getActivity()).setTitle("system hint").setMessage("delete or not")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    db.execSQL("DELETE FROM wish WHERE id = " + wishMapperList.get(position).id);
                                    initList();
                                }
                            }).setNegativeButton("cancel", null).show();
                }
            });
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        rootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                initExpendValue();
                initList();
            }
        }, 500);
    }

    private void initExpendValue() {
        Cursor cursor = db.query("account",
                null, null, null, null, null, null);
        expendValue = 0;
        while (cursor.moveToNext()) {
            Integer money = cursor.getInt(cursor.getColumnIndex("money"));
            expendValue += money;
        }

        Cursor cursor2 = db.query("income",
                null, null, null, null, null, null);
        incomeValue = 0;
        while (cursor2.moveToNext()) {
            Integer money = cursor2.getInt(cursor2.getColumnIndex("money"));
            incomeValue += money;
        }

        balance = incomeValue - expendValue;

        expend.setText("expend: " + expendValue);
        income.setText("income: " + incomeValue);
        balanceView.setText("balance: " + balance);
    }

    private void initList() {
        wishMapperList.clear();
        Cursor cursor = db.query("wish",
                null, null, null, null, null, "insTime desc");

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex("id"));
            String insTime = cursor.getString(cursor.getColumnIndex("insTime"));
            Integer money = cursor.getInt(cursor.getColumnIndex("money"));
            String comment = cursor.getString(cursor.getColumnIndex("comment"));

            wishMapperList.add(new WishMapper(id, insTime, money, comment));
        }

        listView.setAdapter(new IncomeListAdapter());
    }

    @SuppressLint("SetTextI18n")
    class IncomeListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return wishMapperList.size();
        }

        @Override
        public Object getItem(int position) {
            return wishMapperList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return wishMapperList.get(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_wish_item, parent, false);
                viewHolder.title = convertView.findViewById(R.id.title);
                viewHolder.result = convertView.findViewById(R.id.result);
                viewHolder.progress = convertView.findViewById(R.id.progress);
                viewHolder.progressView = convertView.findViewById(R.id.progressView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String title = balance + " / " + wishMapperList.get(position).money;
            viewHolder.result.setText(title);
            viewHolder.title.setText(wishMapperList.get(position).comment);
            viewHolder.progress.setMax(wishMapperList.get(position).money);
            if (balance >= wishMapperList.get(position).money) {
                viewHolder.progress.setProgress(wishMapperList.get(position).money);
                viewHolder.progressView.setText("100%");
            } else if (balance >= 0) {
                viewHolder.progress.setProgress(balance);
                Float value = (Float.valueOf(balance) / wishMapperList.get(position).money);
                viewHolder.progressView.setText(new Float(value * 100).intValue() + "%");
            } else {
                viewHolder.progress.setProgress(0);
                viewHolder.progressView.setText("0%");
            }
            return convertView;
        }
    }

    private static class ViewHolder {
        TextView title;
        TextView result;
        ProgressBar progress;
        TextView progressView;
    }
}
