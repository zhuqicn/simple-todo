package com.codepath.simpletodo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

@Table(name = "ToDoItems")
public class ToDoItem extends Model {
  @Column(name = "Name")
  public String name;
  // 0-4, 0 is the highest
  @Column(name = "Priority")
  public int priority;
  // 0: normal; 1: done;
  @Column(name = "Status")
  public int status;
  @Column(name = "Detail")
  public String detail;
  @Column(name = "Due")
  public Date due;

  public ToDoItem() {
    super();
  }

  public ToDoItem(String name) {
    super();
    this.name = name;
    this.priority = 2;
  }

  public String getPriorityString() {
    String[] priorityStrings = {"URGENT", "HIGH", "NORMAL", "LOW", "BACKLOG"};
    if (this.priority > 4) {
      return "UNDEFINED";
    }
    return priorityStrings[this.priority];
  }

  @Override
  public String toString() {
    return this.name;
  }
}
