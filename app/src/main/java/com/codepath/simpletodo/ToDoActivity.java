package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ToDoActivity extends AppCompatActivity {
  private final int EDIT_ITEM_REQUEST_CODE = 20;
  ArrayList<ToDoItem> items;
  ArrayAdapter<ToDoItem> itemsAdapter;
  ListView lvItems;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_to_do);

    lvItems = (ListView) findViewById(R.id.lvItems);
    readItems();
    itemsAdapter = new ArrayAdapter<>(this,
      android.R.layout.simple_list_item_1, items);
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
          Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
          i.putExtra("position", position);
          i.putExtra("value", items.get(position).name);
          startActivityForResult(i, EDIT_ITEM_REQUEST_CODE);
          return;
        }
      }
    );
  }

  public void onAddItem(View view) {
    EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    String itemText = etNewItem.getText().toString();
    ToDoItem item = new ToDoItem(itemText);
    item.save();
    itemsAdapter.add(item);
    etNewItem.setText("");
  }

  private void readItems() {
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");
    items = new ArrayList<ToDoItem>();
    List<ToDoItem> queryResults = new Select().from(ToDoItem.class).orderBy("Name ASC")
      .limit(100).execute();
    items.addAll(queryResults);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == EDIT_ITEM_REQUEST_CODE && resultCode == RESULT_OK) {
      int position = data.getIntExtra("position", -1);
      if (position < 0) {
        Toast.makeText(getApplicationContext(), "Invalid operation", Toast.LENGTH_SHORT).show();
        return;
      }
      String value = data.getStringExtra("value");
      items.get(position).name = value;
      items.get(position).save();
      itemsAdapter.notifyDataSetChanged();
    }
  }
}
