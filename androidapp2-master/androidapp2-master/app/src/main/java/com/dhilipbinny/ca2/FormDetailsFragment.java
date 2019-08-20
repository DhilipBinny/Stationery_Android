package com.dhilipbinny.ca2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FormDetailsFragment extends Fragment implements Async.IserverResponse {


    public  JSONObject response = null;
    public JSONArray data = null;

    public String formNumber;
    public String comments;
    public String approved_by;
    public String EmployeeName="...";
    public TableLayout tl;
    public View v;
    public String hod;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_formdetails, container, false);
        hod=MainActivity.getUserId();
        tl = (TableLayout) v.findViewById(R.id.main_table);


        TableRow tr_head = new TableRow(getActivity().getApplicationContext());
        tr_head.setId(10);
        tr_head.setBackgroundColor(Color.LTGRAY);
        tr_head.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        TextView label_date = new TextView(getActivity().getApplicationContext());
        label_date.setId(20);
        label_date.setText("Item Description");
        label_date.setTextSize(18);
        label_date.setTextColor(Color.BLACK);
        label_date.setPadding(5, 5, 5, 5);
        tr_head.addView(label_date);// add the column to the table row here

        TextView label_weight_kg = new TextView(getActivity().getApplicationContext());
        label_weight_kg.setId(21);// define id that must be unique
        label_weight_kg.setText("Quantity"); // set the text for the header
        label_weight_kg.setTextSize(18);
        label_weight_kg.setTextColor(Color.BLACK); // set the color
        label_weight_kg.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_weight_kg); // add the column to the table row here

        tl.addView(tr_head, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));


        // Table setup

        formNumber = this.getArguments().getString("FormNumber");

        TextView rtformtv = (TextView) v.findViewById(R.id.rtformtv);
        rtformtv.setText("Form Number : "  + formNumber);

        Button btnApprove = (Button) v.findViewById(R.id.btnApprove);
        Button btnReject = (Button) v.findViewById(R.id.btnReject);
        final EditText editTextComment = (EditText) v.findViewById(R.id.editTextComment);


        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comments=editTextComment.getText().toString();
                Utils util = new Utils();
                util.UpdateFormDetails(getActivity(),util.baseurl + "/hod/UpdateFormDetails",comments,formNumber,"rejected",hod);
                Toast.makeText(getActivity().getApplicationContext(), "Rejected " + formNumber, Toast.LENGTH_SHORT).show();
                loadFragment(new HomeFragment());
            }
        });

        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comments=editTextComment.getText().toString();
                Utils util = new Utils();
                util.UpdateFormDetails(getActivity(),util.baseurl + "/hod/UpdateFormDetails",comments,formNumber,"approved",hod);
                Toast.makeText(getActivity().getApplicationContext(), "Approved " + formNumber, Toast.LENGTH_SHORT).show();
                loadFragment(new HomeFragment());
            }
        });


        Command cmd = new Command(this,"get",new Utils().baseurl + "/hod/ReqformDetails?form_id="+formNumber,null);
        new Async().execute(cmd);

//        Utils u = new Utils();
//        try {
////            response = new JSONObject(  u.loadJSONFromAsset(getActivity() , "stationery_requests_details.json") );
//
//            response = u.loadJSONFromAPI( u.baseurl + "/hod/ReqformDetails?form_id="+formNumber);
//            data = response.getJSONArray("data");
//
//            for (int i = 0; i < data.length(); i++) {
//
//                JSONObject obj  = data.getJSONObject(i);
//                String desc = obj.getString("ItemNumber");
//                String qty = obj.getString("Quantity");
//                EmployeeName = obj.getString("EmployeeName");
//
//                // Create the table row
//                TableRow tr = new TableRow(getActivity().getApplicationContext());
//                tr.setId(100+i);
//                tr.setLayoutParams(new TableRow.LayoutParams(
//                        TableRow.LayoutParams.FILL_PARENT,
//                        TableRow.LayoutParams.WRAP_CONTENT));
//
//                //Create two columns to add as table data
//                // Create a TextView to add date
//                TextView labelDesc = new TextView(getActivity().getApplicationContext());
//                labelDesc.setId(200+i);
//                labelDesc.setText(desc);
//                labelDesc.setTextSize(17);
//                labelDesc.setPadding(2, 0, 5, 0);
//                labelDesc.setTextColor(Color.BLACK);
//                tr.addView(labelDesc);
//
//                TextView labelQty = new TextView(getActivity().getApplicationContext());
//                labelQty.setId(200+i);
//                labelQty.setText(qty);
//                labelQty.setTextSize(17);
//                labelQty.setTextColor(Color.BLACK);
//                tr.addView(labelQty);
//
//                // finally add this to the table row
//                tl.addView(tr, new TableLayout.LayoutParams(
//                        TableLayout.LayoutParams.FILL_PARENT,
//                        TableLayout.LayoutParams.WRAP_CONTENT));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        TextView emp_form = (TextView) v.findViewById(R.id.emp_form_details);
//        emp_form.setText("Employee #"  + EmployeeName);
//        Toast.makeText( getActivity().getApplicationContext(), MainActivity.getUserId() , Toast.LENGTH_LONG ).show();

//        Toast.makeText( getActivity().getApplicationContext(), MainActivity.getUserId() , Toast.LENGTH_LONG ).show();
        return v;

    }

    @Override
    public void onServerResponse(JSONObject result){

        if(result==null){

            return;
        }
        try {

            data = result.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {

                JSONObject obj  = data.getJSONObject(i);
                String desc = obj.getString("ItemDesc");
                String qty = obj.getString("Quantity");
                EmployeeName = obj.getString("EmployeeName");

                // Create the table row
                TableRow tr = new TableRow(getActivity().getApplicationContext());
                tr.setId(100+i);
                tr.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                //Create two columns to add as table data
                // Create a TextView to add date
                TextView labelDesc = new TextView(getActivity().getApplicationContext());
                labelDesc.setId(200+i);
                labelDesc.setText(desc);
                labelDesc.setTextSize(18);
                labelDesc.setPadding(5, 5, 5, 5);
                labelDesc.setTextColor(Color.BLACK);
                tr.addView(labelDesc);

                TextView labelQty = new TextView(getActivity().getApplicationContext());
                labelQty.setId(200+i);
                labelQty.setText(qty);
                labelQty.setTextSize(18);
                labelDesc.setPadding(5, 5, 5, 5);
                labelQty.setTextColor(Color.BLACK);
                tr.addView(labelQty);

                // finally add this to the table row
                tl.addView(tr, new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.FILL_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));

                TextView emp_form = (TextView) v.findViewById(R.id.emp_form_details);
                emp_form.setText("Employee : "  + EmployeeName);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
