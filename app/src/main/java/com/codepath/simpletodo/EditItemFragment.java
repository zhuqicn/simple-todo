package com.codepath.simpletodo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


public class EditItemFragment extends DialogFragment {
  private static final String ARG_PARAM0 = "position";
  private static final String ARG_PARAM1 = "name";
  private static final String ARG_PARAM2 = "priority";
  private static final String ARG_PARAM3 = "status";
  private static final String ARG_PARAM4 = "detail";
  private static final String ARG_PARAM5 = "due";

  private ToDoItem item;
  private int position;
  private EditText etName;
  private DatePicker dpDue;
  private EditText etDetail;
  private Spinner spPriority;
  private Spinner spStatus;
  private CheckBox cbStatus;

  public static EditItemFragment newInstance(ToDoItem item, int position) {
    EditItemFragment fragment = new EditItemFragment();
    if (position < 0) {
      item = new ToDoItem("");
    }
    Bundle args = new Bundle();
    args.putInt(ARG_PARAM0, position);
    args.putString(ARG_PARAM1, item.name);
    args.putInt(ARG_PARAM2, item.priority);
    args.putInt(ARG_PARAM3, item.status);
    args.putString(ARG_PARAM4, item.detail);
    if (item.due == null) {
      args.putString(ARG_PARAM5, "");
    } else {
      args.putString(ARG_PARAM5, DateFormat.getDateInstance().format(item.due));
    }
    fragment.setArguments(args);
    return fragment;
  }

  public EditItemFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      position = getArguments().getInt(ARG_PARAM0);
      item = new ToDoItem(getArguments().getString(ARG_PARAM1));
      item.priority = getArguments().getInt(ARG_PARAM2);
      item.status = getArguments().getInt(ARG_PARAM3);
      item.detail = getArguments().getString(ARG_PARAM4);
      String dateStr = getArguments().getString(ARG_PARAM5);
      try {
        item.due = DateFormat.getDateInstance().parse(dateStr);
      } catch (ParseException e) {
        item.due = null;
      }
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_edit_item, container, false);
    Button btnSave = (Button) view.findViewById(R.id.btnSave);
    btnSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onSave();
      }
    });
    Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
    btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });
    etName = (EditText) view.findViewById(R.id.etName);
    dpDue = (DatePicker) view.findViewById(R.id.dpDue);
    etDetail = (EditText) view.findViewById(R.id.etDetail);

    spPriority = (Spinner) view.findViewById(R.id.spPriority);
    ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(getActivity(),
      R.array.priority_array, R.layout.support_simple_spinner_dropdown_item);
    spPriority.setAdapter(priorityAdapter);

    spStatus = (Spinner) view.findViewById(R.id.spStatus);
    ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(getActivity(),
      R.array.status_array, R.layout.support_simple_spinner_dropdown_item);
    spStatus.setAdapter(statusAdapter);

    // Update the view with correct ToDoItem values.
    etName.setText(item.name);
    if (item.due != null) {
      dpDue.init(item.due.getYear() + 1900, item.due.getMonth(), item.due.getDate(), null);
    }
    spPriority.setSelection(item.priority);
    spStatus.setSelection(item.status);
    etDetail.setText(item.detail);

    // Set dialog title.
    if (position < 0) {
      getDialog().setTitle(R.string.add_todo_title);
    } else {
      getDialog().setTitle(R.string.edit_todo_title);
    }
    return view;
  }

  public void onSave() {
    item.name = etName.getText().toString();
    // TODO: Avoid using Date
    item.due = new Date(dpDue.getYear() - 1900, dpDue.getMonth(), dpDue.getDayOfMonth());
    item.detail = etDetail.getText().toString();
    item.priority = spPriority.getSelectedItemPosition();
    item.status = spStatus.getSelectedItemPosition();

    FinishEditToDoItemListener activity = (FinishEditToDoItemListener) getActivity();
    activity.onFinishEditToDoItem(item, position);
    this.dismiss();
  }

  public interface FinishEditToDoItemListener {
    void onFinishEditToDoItem(ToDoItem item, int position);
  }
}
