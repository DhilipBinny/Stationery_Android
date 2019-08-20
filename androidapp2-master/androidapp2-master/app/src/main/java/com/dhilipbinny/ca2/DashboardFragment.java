package com.dhilipbinny.ca2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        View v = inflater.inflate(R.layout.fragment_dashboard, null);
        Button pending = (Button) v.findViewById(R.id.pending);
        Button editdept = (Button)v.findViewById(R.id.editdept);
        Button history =(Button) v.findViewById(R.id.history);
        Button delegate = (Button)v.findViewById(R.id.delegate);

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = MainActivity.navView.findViewById(R.id.title_stationery_request);
                view.performClick();
            }
        });
        editdept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = MainActivity.navView.findViewById(R.id.navigation_editdepartment);
                view.performClick();
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                View view = MainActivity.navView.findViewById(R.id.title_stationery_request);
//                view.performClick();
                Fragment fragment = new HistFragment();
                loadFragment(fragment);

            }
        });
        delegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = MainActivity.navView.findViewById(R.id.navigation_editdelegate);
                view.performClick();
            }
        });

        return v;
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

