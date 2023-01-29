package com.example.class261insertdataintodatabasewithphp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
//======================== Dhoradhori porbo==========================
    EditText edName, edMobile,edEmail;
    ProgressBar progressBar;
    Button buttonInsert;
    ListView listView;
    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

//***************************************************

    // ======= OnCreate bundle starts ======
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            // ==================== Porichoy Porbo Starts =======
            edName = findViewById(R.id.edName);
            edMobile = findViewById(R.id.edMobile);
            edEmail = findViewById(R.id.edEmail);
            progressBar = findViewById(R.id.progressBar);
            buttonInsert = findViewById(R.id.buttonInsert);
            listView = findViewById(R.id.listView);

            loadData();

            //************ Porichoy Porbo finished  *********

 //*********************   JSON Array Request finished ********************************




// ===================Button insert starts =========================================


        buttonInsert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String name = edName.getText().toString();
                String mobile = edMobile.getText().toString();
                String email = edEmail.getText().toString();
                String url = "https://testbdraian.000webhostapp.com/apps/data.php?n="+ name +"&m="+ mobile +"&e="+ email;



                progressBar.setVisibility(View.VISIBLE);
                // Request a string response from the provided URL.
                StringRequest request = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                progressBar.setVisibility(View.GONE);


                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("data entry done")
                                        .setMessage(response)
                                        .show();

                                loadData();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        Log.d("serverRes",error.toString());


                    }
                });



//                if(name.length()>0 && mobile.length()>0 && email.length()>0){
//                    // Add the request to the RequestQueue.
                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                     requestQueue.add(request);


//
//                } else edName.setError("Please Insert Your name");

            }
        }
        );
//************************ Button Insert Finished  ******************


    }

    //******* OnCreate bundle finished**********


// ======================Creating Adapter=====================================================
    public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.item,null);

            TextView tvId = myView.findViewById(R.id.tvId);
            TextView tvName = myView.findViewById(R.id.tvName);
            TextView tvMobile = myView.findViewById(R.id.tvMobile);
            TextView tvEmail = myView.findViewById(R.id.tvEmail);

            Button buttonUpdate = myView.findViewById(R.id.buttonUpdate);
            Button buttonDelete = myView.findViewById(R.id.buttonDelete);

            hashMap = arrayList.get(i);
            String id = hashMap.get("id");
            String name = hashMap.get("name");
            String mobile = hashMap.get("mobile");
            String email = hashMap.get("email");

            tvId.setText(id);
            tvName.setText(name);
            tvMobile.setText(mobile);
            tvEmail.setText(email);
            //------- Making button update functioning---------
                buttonUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //update data
                        String name = edName.getText().toString();
                        String mobile = edMobile.getText().toString();
                        String email = edEmail.getText().toString();

                        String url = "https://testbdraian.000webhostapp.com/apps/update.php?id"+
                                id+"&name="+name+"&mobile="+mobile+"&email="+email;

                        progressBar.setVisibility(View.VISIBLE);
                        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                progressBar.setVisibility(View.GONE);
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Server update")
                                        .setMessage(response)
                                        .show();
                                loadData();


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                        requestQueue.add(request);


                    }
                });

            //********************* button update finished *****************************************





            //============================= Button DELETE ========================


            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //update data


                    String url = "https://testbdraian.000webhostapp.com/apps/delete.php?id="+id;

                    progressBar.setVisibility(View.VISIBLE);
                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            progressBar.setVisibility(View.GONE);
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Server update")
                                    .setMessage(response)
                                    .show();
                            loadData();


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("deleteRes",error.toString());

                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                    requestQueue.add(request);


                }
            });

            //*************************** Button DELETE finished ***********************

            return myView;
        }
    }
//************** Adapter finished ****************


//======================   Creating a function =====================================================================

    private void loadData(){

        arrayList = new ArrayList<>();  // aikhane new arrayList call na korle abr aki data duibar load hoye  jabe, tai notun array list nisi

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://testbdraian.000webhostapp.com/apps/view.php";



// ========================  Jason ArrayRequest starts ============


        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);

                //========= For loop Starts =================
                for (int i=0; i<response.length(); i++){

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String mobile = jsonObject.getString("mobile");
                        String email = jsonObject.getString("email");

                        hashMap = new HashMap<>();
                        hashMap.put("id", id);
                        hashMap.put("name", name);
                        hashMap.put("mobile", mobile);
                        hashMap.put("email", email);
                        arrayList.add(hashMap);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                //************** For loop finished **********

                //========== calling adapter====
                if (arrayList.size()>0){

                    MyAdapter myAdapter = new MyAdapter();
                    listView.setAdapter(myAdapter);
                }
                //**** calling adapter ends *****
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        requestQueue.add(jsonArrayRequest);

    }

    //******************************  Function finished **********************************************



}