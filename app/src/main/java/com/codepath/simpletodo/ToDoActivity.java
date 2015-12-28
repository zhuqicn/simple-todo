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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ToDoActivity extends AppCompatActivity {
  private final int EDIT_ITEM_REQUEST_CODE = 20;
  ArrayList<String> items;
  ArrayAdapter<String> itemsAdapter;
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
          items.remove(position);
          itemsAdapter.notifyDataSetChanged();
          writeItems();
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
          i.putExtra("value", items.get(position));
          startActivityForResult(i, EDIT_ITEM_REQUEST_CODE);
          return;
        }
      }
    );
  }

  public void onAddItem(View view) {
    EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    String itemText = etNewItem.getText().toString();
    itemsAdapter.add(itemText);
    etNewItem.setText("");
    writeItems();
  }

  private void readItems() {
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");
    try {
      items = new ArrayList<String>(FileUtils.readLines(todoFile));
    } catch (IOException e) {
      items = new ArrayList<String>();
    }
  }

  private void writeItems() {
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");
    try {
      FileUtils.writeLines(todoFile, items);
    } catch (IOException e) {
      e.printStackTrace();
    }
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
      items.set(position, value);
      itemsAdapter.notifyDataSetChanged();
      writeItems();
    }
  }
}
