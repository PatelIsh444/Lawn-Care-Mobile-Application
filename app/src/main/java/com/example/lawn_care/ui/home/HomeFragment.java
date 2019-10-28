package com.example.lawn_care.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lawn_care.R;
import com.example.lawn_care.SignIn;
import com.example.lawn_care.dash;
import com.example.lawn_care.localUserInfo;
import com.example.lawn_care.viewYourProperties;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        String userType=com.example.lawn_care.localUserInfo.getUserType();
        View root;
        if(userType.equals("worker")){
            root = inflater.inflate(R.layout.fragment_search_jobs, container, false);
            showListings();
        }
        else if(userType.equals("owner")){
            root = inflater.inflate(R.layout.fragment_search_workers, container, false);
            showWorkers();
        }
        else{
            //change when admin stuff is added
            root = inflater.inflate(R.layout.fragment_search_workers, container, false);
        }
        return root;
    }

    private void showWorkers() {
        //get list of workers from database

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Finding Workers....");
        progressDialog.show();

        final String signin_url="http://10.0.2.2:80/scripts/viewAllWorkerProfiles.php";
        //stringRequest is an object that contains the request method, the url, and the parameters and the response
        StringRequest stringRequest=new StringRequest(Request.Method.POST, signin_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonResponse;
                        try {
                            jsonResponse=new JSONObject(response);
                            if(jsonResponse.getString("success")!="false"){
                                //gets linear layout from screen
                                LinearLayout linearLayout=getActivity().findViewById(R.id.LL_searchWorkersList);
                                //line between items
                                View V_line=new View(getActivity());
                                V_line.setBackgroundResource(R.color.BLACK);
                                V_line.setMinimumHeight(2);
                                linearLayout.addView(V_line);
                                //0 to len-1, the first index is the success check, the rest are listings, but the first address starts at 0, so listings go from 0 to n-1
                                for(int x = 0; x < jsonResponse.length()-1; x++){
                                    //create a new linear layout so each listing can be in a view, easier to do stuff with
                                    LinearLayout listingItem = new LinearLayout(getActivity());
                                    listingItem.setOrientation(LinearLayout.VERTICAL);
                                    //creates views for stuff shown on preview
                                    TextView TV_name=new TextView(getActivity());
                                    TextView TV_email=new TextView(getActivity());
                                    TextView TV_workOffered=new TextView(getActivity());
                                    //index the json based on the value in x. all the stuff in the json is technically a string, so you have to convert the integer to string to get the xth object
                                    //everything from 0 to n-1 can be stored as a jsonobject
                                    JSONObject currentWorker=jsonResponse.getJSONObject(String.valueOf(x));

                                    //get the fields i want to show in the views and format the strings how i want them to appear
                                    String name = currentWorker.getString("firstName")+" "+currentWorker.getString("lastName");
                                    String email = currentWorker.getString("workerEmail");
                                    String workOffered = currentWorker.getString("workOffered");
                                    workOffered=workOffered.replace("[","");
                                    workOffered=workOffered.replace("]","");
                                    workOffered=workOffered.replace("\"","");

                                    //set the view texts
                                    TV_name.setText(name);
                                    TV_name.setTextSize(25);
                                    TV_name.setTextColor(Color.BLACK);
                                    TV_name.setTypeface(null, Typeface.BOLD);

                                    TV_email.setText(email);
                                    TV_email.setTextSize(20);
                                    TV_email.setTextColor(Color.BLACK);

                                    TV_workOffered.setText(workOffered);
                                    TV_workOffered.setTextSize(16);
                                    TV_workOffered.setSingleLine();
                                    TV_workOffered.setEllipsize(TextUtils.TruncateAt.END);
                                    TV_workOffered.setTextColor(Color.BLACK);

                                    //add the views to the current linear layout
                                    listingItem.addView(TV_name);
                                    listingItem.addView(TV_email);
                                    listingItem.addView(TV_workOffered);

                                    //add clickable listing to go to their profile
                                    linearLayout.setClickable(true);
                                    linearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //PLACEHOLDER
                                            //SEND THEM TO THE PROFILE PAGE
                                            Intent intent= new Intent(getActivity(),dash.class);
                                            getActivity().startActivity(intent);
                                        }
                                    });

                                    //add the current linear layout to the main linear layout
                                    linearLayout.addView(listingItem);

                                    //add line between items
                                    V_line=new View(getActivity());
                                    V_line.setBackgroundResource(R.color.BLACK);
                                    V_line.setMinimumHeight(2);
                                    linearLayout.addView(V_line);
                                }
                                progressDialog.dismiss();
                                //programmatically add to list
                                /*
                                for each item in the json
                                    create frame
                                    place name, days, time in frame
                                    add button which is linked to their worker number (button will lead to profile page)
                                    add frame to list
                                 */

                                //save the data from the json into local variables
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Connection Failed")
                                .setNegativeButton("Try Again",null)
                                .create()
                                .show();
                        error.printStackTrace();
                        Log.e("VOLLEY", error.getMessage());
                        //requestQueue.stop();
                    }
                }){
            @Override
            //this function is written to get the parameters for posting
            protected Map<String,String> getParams(){
                Map<String,String> params= new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void showListings() {
        //get list of properties from database
        //programmatically add to list
        /*
        for each item in json
            create frame
                place address, work needed in frame
                add button which is linked to property number (button will lead to property page)
                add frame to list
         */
    }
}