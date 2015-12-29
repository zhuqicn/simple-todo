package com.codepath.simpletodo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "ToDoItems")
public class ToDoItem extends Model {
  @Column(name = "Name")
  public String name;

  public ToDoItem() {
    super();
  }

  public ToDoItem(String name) {
    super();
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
