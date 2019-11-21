package se.mau.eliasmoltedo.p1.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

import se.mau.eliasmoltedo.p1.CategoryIn;
import se.mau.eliasmoltedo.p1.Controller;
import se.mau.eliasmoltedo.p1.R;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeAddFragment extends Fragment {
    private Controller controller;
    private EditText etTitle;
    private EditText etAmount;
    private Spinner spinnerCategory;
    private TextView tvShowDate;
    private Button btnAddIncome;
    private Button btnSelectDate;
    private DatePickerFragment datePickerFragment;
    private DatePickerDialog dpd;
    private Date date;
    private Calendar c;
    private String tempString;


    public IncomeAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_income_add, container, false);
        initComponents(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState != null){
            etTitle.setText(savedInstanceState.getString("etTitleText"));
        }
    }



//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString("etTitleText", etTitle.getText().toString());
//    }

    public void initComponents(View view){
        this.etTitle = view.findViewById(R.id.etIncomeTitle);
        this.etAmount = view.findViewById(R.id.etIncomeAmount);
        this.spinnerCategory = view.findViewById(R.id.spinnerIncome);
        this.tvShowDate = view.findViewById(R.id.tvShowInDate);
        this.btnAddIncome = view.findViewById(R.id.btnIncomeAdd);
        this.btnSelectDate = view.findViewById(R.id.btnSelectInDate);

        spinnerCategory.setAdapter(new ArrayAdapter<CategoryIn>(getActivity(), android.R.layout.simple_list_item_1, CategoryIn.values()));


        btnSelectDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String s = dayOfMonth + "-" + month + "-" + year;

                        date = new Date(year, month, dayOfMonth);
                        tvShowDate.setText(s);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        btnAddIncome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(c == null){
                    Toast.makeText(getContext(), "Du behöver välja datum", Toast.LENGTH_LONG).show();

                }else {
                    controller.addIncome(generateId(), date, etTitle.getText().toString(), (CategoryIn) spinnerCategory.getSelectedItem(), etAmount.getText().toString());
                    controller.setFragment("IncomeFragment");
                }
            }
        });
    }

//    private void setTextToTextView(String dateString){
//        tvShowDate.setText(dateString);
//    }

    private String generateId(){
        String id = UUID.randomUUID().toString();
        return id;
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

}
