package com.dhilipbinny.ca2;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class EditDepartmentFragment extends Fragment implements Async.IserverResponse{
    public  ArrayList<String> items;
    public  ArrayList<String> items_index;
    public  JSONObject response = null;
    public  JSONObject response_ = null;
    public JSONArray data = null;
    public JSONArray data1 = null;
    public JSONObject data_ = null;
    public  Spinner spinner;
    public  RadioGroup radioGroup;
    public String current_selected_collection_point;
    View v;

    public int collectionpoint;
    public String representative;

    public int c_collectionpoint= 1;
    public String c_representative=null;
    public String c_collectionrepname="....";
    public String c_collectionpointname="....";
    public String hod;
    public String hodname;
//    public  View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.fragment_editdepartment, container, false);
        hod= MainActivity.getUserId();
        hodname = MainActivity.getUser();
        Command cmd = new Command(this,"get",new Utils().baseurl + "/hod/GetDeptInfo?user_id="+hod,null);
        new Async().execute(cmd);
//        Utils util = new Utils();
//        try {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//
//            response_ = util.loadJSONFromAPI(util.baseurl+"/hod/GetDeptInfo?user_id=2");
//            data_ = response_.getJSONObject("data");
//            c_collectionpointname = data_.getString("collectionptname");
//            c_representative = data_.getString("collectionrep");
//            representative=c_representative;
//
//            c_collectionrepname = data_.getString("collectionrepname");
//            c_collectionpoint = Integer.parseInt(data_.getString("collectionpt"));
//            collectionpoint=c_collectionpoint;
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        TextView ccp =(TextView)v.findViewById(R.id.current_collection_point);
//        ccp.setText(c_collectionpointname);
//        TextView crep =(TextView)v.findViewById(R.id.currentrepname);
//        crep.setText(c_collectionrepname);

        spinner = (Spinner)v.findViewById(R.id.spinner);
        radioGroup = (RadioGroup) v.findViewById(R.id.radioGrp);
//        ((RadioButton)radioGroup.getChildAt(c_collectionpoint-1)).setChecked(true);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioBtn1:
                        current_selected_collection_point = "Stationery Store - Admin Building";
                        collectionpoint=1;
                        break;
                    case R.id.radiobtn2:
                        current_selected_collection_point = "Management School";
                        collectionpoint=2;
                        break;
                    case R.id.radiobtn3:
                        current_selected_collection_point = "Medical School";
                        collectionpoint=3;
                        break;
                    case R.id.radiobtn4:
                        current_selected_collection_point = "Engineering School";
                        collectionpoint=4;
                        break;
                    case R.id.radiobtn5:
                        current_selected_collection_point = "Science School";
                        collectionpoint=5;
                        break;
                    case R.id.radiobtn6:
                        current_selected_collection_point = "University Hospital";
                        collectionpoint=6;
                        break;

                }
            }
        });

        // Get employeee

//        Utils u = new Utils();
//        try {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
////            response = new JSONObject(u.loadJSONFromAsset(getActivity(), "department_employee.json"));
//            response = u.loadJSONFromAPI( u.baseurl + "/hod/GetDeptEmployees?user_id=2");
//            data = response.getJSONArray("data");
//
//            for (int i = 0; i < data.length(); i++) {
//
//                JSONObject obj = data.getJSONObject(i);
//                String id = obj.getString("EmployeeId");
//                String name = obj.getString("EmployeeName");
//
//                items.add(name);
//                items_index.add(id);
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }




//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items);
//        spinner.setAdapter(adapter);
////        spinner.setSelection(items.indexOf(c_representative));
//        System.out.println(items_index.indexOf(c_representative));
//        spinner.setSelection(items_index.indexOf(c_representative));
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItem = items.get(position);
//                String selectedItemIndex = items_index.get(position);
//                representative=String.valueOf(selectedItemIndex);
//                Toast.makeText(getActivity().getApplicationContext(),  String.valueOf(selectedItem) + " id= "+ String.valueOf(selectedItemIndex), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        TextView headname = (TextView)v.findViewById(R.id.headname);
//        TextView current_collection_point = (TextView)v.findViewById(R.id.current_collection_point);
//
//        headname.setText( MainActivity.getUserId() );

        Button btnSub = (Button)v.findViewById(R.id.btnSub);
        Button btnCancel = (Button)v.findViewById(R.id.btnCancel);

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

//                current_selected_collection_point , selectedItemIndex
                Utils ut =new Utils();
                ut.UpdateDeptDetails(getActivity(),ut.baseurl + "/hod/DeptUpdate",representative,String.valueOf(collectionpoint),hod);
                Toast.makeText(getActivity().getApplicationContext(), "Updated successfully."+" : "+collectionpoint+" : "+representative, Toast.LENGTH_SHORT).show();

                View view = MainActivity.navView.findViewById(R.id.navigation_dashboard);
                view.performClick();

            }
        });

        return v;
    }


    public void onServerResponse(JSONObject result){

        if(result==null){

            return;
        }
        try {
            items = new ArrayList<String>();
            items_index = new ArrayList<String>();

            data_ = result.getJSONObject("data");
            c_collectionpointname = data_.getString("collectionptname");
            c_representative = data_.getString("collectionrep");
            representative=c_representative;

            c_collectionrepname = data_.getString("collectionrepname");
            c_collectionpoint = Integer.parseInt(data_.getString("collectionpt"));
            collectionpoint=c_collectionpoint;


            data1= result.getJSONArray("data1");

            for (int i = 0; i < data1.length(); i++) {

                JSONObject obj = data1.getJSONObject(i);
                String id = obj.getString("EmployeeId");
                String name = obj.getString("EmployeeName");

                items.add(name);
                items_index.add(id);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items);
            spinner.setAdapter(adapter);
//        spinner.setSelection(items.indexOf(c_representative));
            System.out.println(items_index.indexOf(c_representative));
            spinner.setSelection(items_index.indexOf(c_representative));

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = items.get(position);
                    String selectedItemIndex = items_index.get(position);
                    representative=String.valueOf(selectedItemIndex);
//                    Toast.makeText(getActivity().getApplicationContext(),  String.valueOf(selectedItem) + " id= "+ String.valueOf(selectedItemIndex), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            TextView headname = (TextView)v.findViewById(R.id.headname);
            TextView current_collection_point = (TextView)v.findViewById(R.id.current_collection_point);

            headname.setText( MainActivity.getUser() );
            TextView ccp =(TextView)v.findViewById(R.id.current_collection_point);
            ccp.setText(c_collectionpointname);
            TextView crep =(TextView)v.findViewById(R.id.currentrepname);
            crep.setText(c_collectionrepname);

            ((RadioButton)radioGroup.getChildAt(c_collectionpoint-1)).setChecked(true);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
