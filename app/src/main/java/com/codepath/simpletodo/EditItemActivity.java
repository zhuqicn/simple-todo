package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {
  EditText etItem;
  int position;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_item);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    etItem = (EditText) findViewById(R.id.etItem);
    String value = getIntent().getStringExtra("value");
    etItem.setText(value);
    position = getIntent().getIntExtra("position", -1);
  }

  public void onSaveItem(View view) {
    Toast.makeText(getApplicationContext(), etItem.getText().toString(), Toast.LENGTH_SHORT);
    Intent data = new Intent();
    data.putExtra("position", position);
    data.putExtra("value", etItem.getText().toString());
    setResult(RESULT_OK, data);
    this.finish();
  }
}
