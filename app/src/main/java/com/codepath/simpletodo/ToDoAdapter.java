package com.codepath.simpletodo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;

public class ToDoAdapter extends ArrayAdapter {
  private ToggleToDoItemStatusListener callback;

  public ToDoAdapter(Context context, ArrayList<ToDoItem> items) {
    super(context, 0, items);
    if (context instanceof ToggleToDoItemStatusListener) {
      this.callback = (ToggleToDoItemStatusListener) context;
    } else {
      Log.wtf("ToDoAdapter", "Caller should implement ToggleToDoItemStatusListener");
    }
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

    // Update value of each view.
    tvName.setText(item.name);
    tvPriority.setText(item.getPriorityString());
    if (item.due != null) {
      tvDue.setText(DateFormat.getDateInstance().format(item.due));
    }
    cbDone.setChecked(item.status == 1);

    // Handle click on the checkbox.
    final int itemPosition = position;
    final int itemStatus = 1 - item.status;
    cbDone.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        callback.onToggleToDoItemStatus(itemPosition, itemStatus);
      }
    });
    return view;
  }

  public interface ToggleToDoItemStatusListener {
    void onToggleToDoItemStatus(int position, int status);
  }
}
