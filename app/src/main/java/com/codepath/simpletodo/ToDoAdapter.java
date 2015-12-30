package com.codepath.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoAdapter extends ArrayAdapter {
  public ToDoAdapter(Context context, ArrayList<ToDoItem> items) {
    super(context, 0, items);
  }

  @Override
  public View getView(int position, View view, ViewGroup parent) {

    if (view == null) {
      view = LayoutInflater.from(getContext()).inflate(R.layout.lv_items, parent, false);
    }
    ToDoItem item = (ToDoItem) getItem(position);
    TextView tvName = (TextView) view.findViewById(R.id.tvName);
    TextView tvPriority = (TextView) view.findViewById(R.id.tvPriority);
    TextView tvDue = (TextView) view.findViewById(R.id.tvDue);
    CheckBox cbDone = (CheckBox) view.findViewById(R.id.cbDone);

    tvName.setText(item.name);
    tvPriority.setText(item.getPriorityString());
    if (item.due != null) {
      tvDue.setText(item.due.toString());
    }
    cbDone.setChecked(item.status == 1);
    return view;
  }
}
