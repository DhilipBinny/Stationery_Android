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
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements Async.IserverResponse{

    public  JSONArray data = null;
    ArrayList<String> prueba ;
    ListView lstItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        lstItems = (ListView)v.findViewById(R.id.itemslist);


        Command cmd = new Command(this,"get",new Utils().baseurl + "/hod/getpendingreq?user_id="+MainActivity.getUserId(),null);
        new Async().execute(cmd);

        lstItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    System.out.println("Ignore header click");
                }else {
                    int pos = position-1;
                    try {
                        JSONObject jsonObject = data.getJSONObject(pos);
                        Toast.makeText(getContext(),"  FormNumber =  " +  jsonObject.getString("FormNumber"),
                                Toast.LENGTH_SHORT).show();

                        FormDetailsFragment fd = new FormDetailsFragment();

                        Bundle bundle = new Bundle();
                        String formnumber = jsonObject.getString("FormNumber");
                        bundle.putString("FormNumber", formnumber );
                        fd.setArguments(bundle);

                        loadFragment(fd);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });


        Toast.makeText( getActivity().getApplicationContext(), MainActivity.getUserId() , Toast.LENGTH_LONG ).show();
        return v;
    }

    @Override
    public void onServerResponse(JSONObject result){

        if(result==null){

            return;
        }
        try {
            prueba=new ArrayList<String>();
            String head = "...";
            prueba.add(  head );

            data = result.getJSONArray("data");

            System.out.println(data.length()+"**************************************");
            System.out.println(data.toString());

            for (int i = 0; i < data.length(); i++) {

                JSONObject obj  = data.getJSONObject(i);
                String message = "       " + obj.getString("FormNumber") + " " +
                        "             " + obj.getString("EmployeeName") + " " +
                        "             " + obj.getString("Status");
                prueba.add(  message );
                System.out.println("*********************************"+i+"******************");

            }
            System.out.println(prueba.size()+"**************************************");
            System.out.println(prueba.toString());
            ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,prueba);

            lstItems.setAdapter(allItemsAdapter);



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
