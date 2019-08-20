package com.dhilipbinny.ca2;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class EditDelegateFragment extends Fragment implements Async.IserverResponse {

    public  ArrayList<String> items;
    public  ArrayList<String> items_index;
    public JSONArray emp_list = null;

    public JSONObject response = null;
    public JSONObject data = null;
    public String current_delegate=null;
    public String current_delegateid="1";
    public String c_from=null;
    public String c_to=null;
    public Spinner spinner;
    public EditText eText;
    public EditText etText;
    public Calendar from_date_selected=Calendar.getInstance();
    public Calendar to_date_selected= Calendar.getInstance();
    View v;
    public String delegate_to_assign = null;
    public String hod;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_editdelegate, null);
        hod = MainActivity.getUserId();

        items = new ArrayList<String>();
        items_index = new ArrayList<String>();

        Command cmd = new Command(this,"get",new Utils().baseurl + "/hod/GetDelegate?user_id="+hod,null);
        new Async().execute(cmd);

//        Utils util = new Utils();
//        try {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//
//            response = util.loadJSONFromAPI(util.baseurl+"/hod/GetDelegate?user_id=2");
//            data = response.getJSONObject("data");
//
//            current_delegate  = data.getString("current_delegate");
//            c_from = data.getString("c_from");
//            c_to = data.getString("c_to");
//
//            current_delegateid=data.getString("current_delegateid");
//
//            emp_list = response.getJSONArray("emp_list");
//            for (int i = 0; i < emp_list.length(); i++) {
//
//                JSONObject obj = emp_list.getJSONObject(i);
//                String id = obj.getString("EmployeeId");
//                String name = obj.getString("EmployeeName");
//
//
//                items.add(name);
//                items_index.add(id);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        eText = (EditText) v.findViewById(R.id.from_date);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                from_date_selected.set(year,monthOfYear,dayOfMonth);
                                eText.setText( new SimpleDateFormat("yyyy-MM-dd").format( from_date_selected.getTime()));

                                etText.setText(null);
                                etText.setEnabled(true);

                                System.out.println(new SimpleDateFormat("yyyy-MM-dd").format( from_date_selected.getTime()));
                                Toast.makeText(getActivity().getApplicationContext(), "date "+dayOfMonth+"/"+(monthOfYear + 1)+"/"+year, Toast.LENGTH_SHORT).show();

                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1);
                picker.show();
            }
        });

        etText = (EditText) v.findViewById(R.id.to_date);
        etText.setInputType(InputType.TYPE_NULL);
        etText.setEnabled(false);
        try {
            etText.setOnClickListener(new View.OnClickListener() {

                //                Calendar min_to_date = from_date_selected;
                @Override
                public void onClick(View v) {
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);
                    // date picker dialog
                    DatePickerDialog picker = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    to_date_selected.set(year,monthOfYear,dayOfMonth);
                                    etText.setText( new SimpleDateFormat("yyyy-MM-dd").format( to_date_selected.getTime()));

                                    Toast.makeText(getActivity().getApplicationContext(), "date "+dayOfMonth+"/"+(monthOfYear + 1)+"/"+year, Toast.LENGTH_SHORT).show();

                                }
                            }, year, month, day);
                    picker.getDatePicker().setMinDate(from_date_selected.getTimeInMillis());
                    picker.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button btnSub = (Button)v.findViewById(R.id.btndelegateAssign);
        Button btnCancel = (Button)v.findViewById(R.id.btnCancel);
        Button btnresetdelegate=(Button)v.findViewById(R.id.remove_delegate_btn);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                View view = MainActivity.navView.findViewById(R.id.navigation_dashboard);
                view.performClick();
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {

                if( eText.getText().toString().trim().equals("") )
                {
//                    eText.setError( "Please select the dates.." );
                    Toast.makeText(getActivity().getApplicationContext(), "Please select FROM Date", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("Please select From Date..");
                    builder1.show();

                }else if (etText.getText().toString().trim().equals("") ){
//                    etText.setError( "Please select the dates.." );
                    Toast.makeText(getActivity().getApplicationContext(), "Please select TO Date", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("Please select to Date..");
                    builder1.show();

                }
                else{
                    Utils ut =new Utils();
                    ut.UpdateDelegateDetails(getActivity(),ut.baseurl + "/hod/DelegateUpdate",delegate_to_assign,eText.getText().toString(),etText.getText().toString(), hod);
                    System.out.println("Updated successfully."+" : "+delegate_to_assign+" : "+eText.getText()+" : "+etText.getText());
                    Toast.makeText(getActivity().getApplicationContext(), "Updated successfully.", Toast.LENGTH_SHORT).show();

                    View view = MainActivity.navView.findViewById(R.id.navigation_editdelegate);
                    view.performClick();
                }

            }
        });

        btnresetdelegate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Removing Delegate")
                        .setMessage("Are you sure you want to Remove delegate?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils ut =new Utils();
                                ut.RemoveDelegateDetails(getActivity(),ut.baseurl + "/hod/Resetdelegate",hod);
                                Toast.makeText(getActivity().getApplicationContext(), "Reset Delegate Successful", Toast.LENGTH_SHORT).show();
                                View view = MainActivity.navView.findViewById(R.id.navigation_dashboard);
                                view.performClick();
                            }
                        }).setNegativeButton("No", null).show();


            }
        });


        return v;
    }

    @Override
    public void onServerResponse(JSONObject result){

        if(result==null){

            return;
        }
        try {
            data = result.getJSONObject("data");

            current_delegate  = data.getString("current_delegate");
            c_from = data.getString("c_from");
            c_to = data.getString("c_to");

            current_delegateid=data.getString("current_delegateid");

            emp_list = result.getJSONArray("emp_list");
            for (int i = 0; i < emp_list.length(); i++) {

                JSONObject obj = emp_list.getJSONObject(i);
                String id = obj.getString("EmployeeId");
                String name = obj.getString("EmployeeName");


                items.add(name);
                items_index.add(id);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView c_delegate = (TextView)v.findViewById(R.id.current_delegate);
        c_delegate.setText(current_delegate);
        TextView c_delegate_from = (TextView)v.findViewById(R.id.from_delegate);
        c_delegate_from.setText(c_from);
        TextView c_delegate_to = (TextView)v.findViewById(R.id.to_delegate);
        c_delegate_to.setText(c_to);

        spinner = (Spinner)v.findViewById(R.id.delegate_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
//        spinner.setSelection(items.indexOf(c_representative));
        spinner.setSelection(items_index.indexOf(current_delegateid));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = items.get(position);
                String selectedItemIndex = items_index.get(position);
                delegate_to_assign=String.valueOf(selectedItemIndex);
//                Toast.makeText(getActivity().getApplicationContext(),  String.valueOf(selectedItem) + " id= "+ String.valueOf(selectedItemIndex), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


}
