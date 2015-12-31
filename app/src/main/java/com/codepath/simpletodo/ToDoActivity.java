package com.codepath.simpletodo;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.activeandroid.query.Select;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ToDoActivity extends AppCompatActivity
  implements EditItemFragment.FinishEditToDoItemListener,
  ToDoAdapter.ToggleToDoItemStatusListener
{
  ArrayList<ToDoItem> items;
  ToDoAdapter itemsAdapter;
  ListView lvItems;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_to_do);

    lvItems = (ListView) findViewById(R.id.lvItems);
    readItems();
    itemsAdapter = new ToDoAdapter(this, items);
    lvItems.setAdapter(itemsAdapter);
    setupListViewListener();
  }

  private void setupListViewListener() {
    // Long click to remove item.
    lvItems.setOnItemLongClickListener(
      new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
          items.get(position).delete();
          items.remove(position);
          itemsAdapter.notifyDataSetChanged();
          return true;
        }
      }
    );
    // Click to edit item.
    lvItems.setOnItemClickListener(
      new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          FragmentManager fm = getFragmentManager();
          EditItemFragment editItemFragment = EditItemFragment.newInstance(items.get(position),
            position);
          editItemFragment.show(fm, "fragment_edit_todo_item");
          return;
        }
      }
    );
  }

  public void onAddItem(View view) {
    FragmentManager fm = getFragmentManager();
    EditItemFragment editItemFragment = EditItemFragment.newInstance(null, -1);
    editItemFragment.show(fm, "fragment_edit_todo_item");
  }

  private void readItems() {
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");
    items = new ArrayList<ToDoItem>();
    List<ToDoItem> queryResults = new Select().from(ToDoItem.class)
      .orderBy("Priority ASC, Due ASC, Name ASC").limit(100).execute();
    items.addAll(queryResults);
  }

  @Override
  public void onFinishEditToDoItem(ToDoItem item, int position) {
    if (position < 0) {
      // Add item.
      item.save();
      items.add(item);
    } else {
      // Edit item.
      items.get(position).CopyFrom(item);
      items.get(position).save();
    }
    itemsAdapter.notifyDataSetChanged();
  }

  @Override
  public void onToggleToDoItemStatus(int position, int status) {
    items.get(position).status = status;
    items.get(position).save();
    itemsAdapter.notifyDataSetChanged();
  }
}
