package activity.rprs.com.daycaremain;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TecBulkActivity extends AppCompatActivity implements View.OnClickListener{

    GridView gv;
    CheckBox checkBox;
    Button addbtn;
    public Context context;
    Activity activity;

    Calendar cal;
    SimpleDateFormat dateFormat;
    String fDate;

    ArrayList<TecAttendmodel> modelist;
    TecattedAdapter adapter;
    Boolean checkbstate=true;

    ProgressDialog dialog;

    String url="http://reichprinz.com/teaAndroid/Daycare/fetchsignchild.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tec_bulk);

        context=this;
        activity=this;
        checkBox=(CheckBox)findViewById(R.id.checkBox9);
        addbtn=(Button)findViewById(R.id.button13);
        addbtn.setEnabled(false);
        addbtn.setOnClickListener(this);

        cal = Calendar.getInstance();
        dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        fDate=dateFormat.format(cal.getTime());

        modelist= new ArrayList<>();
        gv=(GridView) findViewById(R.id.gridview);

        datafromServer();

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TecAttendmodel mmmd=modelist.get(position);
                if (mmmd.getState()) {
                    mmmd.setState(false);
                    checkbstate=false;
                    checkBox.setChecked(false);
                    checkbstate=true;
                }
                else {
                    mmmd.setState(true);
                }
                modelist.set(position,mmmd);
                adapter.notifyDataSetChanged();
                checkState();
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkbstate){
                    for (int i = 0; modelist.size() > i; i++) {
                        TecAttendmodel mmmd = modelist.get(i);
                        mmmd.setState(isChecked);
                    }
                    if (modelist.size()>0) {
                        adapter.notifyDataSetChanged();
                        if (isChecked)
                            addbtn.setEnabled(true);
                        else
                            addbtn.setEnabled(false);
                    }
                }else {

                }
            }
        });
    }

    public void checkState(){
        for (int i=0;modelist.size()>i;i++){
            TecAttendmodel mmmd=modelist.get(i);
            if (mmmd.getState()){
                addbtn.setEnabled(true);
                break;
            }else {
                addbtn.setEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        DataClass.liststudids.clear();
        for (int i = 0; modelist.size() > i; i++) {
            TecAttendmodel mmmd = modelist.get(i);
            if (mmmd.getState()) {
                DataClass.liststudids.add(String.valueOf(mmmd.getId()));
            }
        }
        Intent intent=new Intent(context,TecBulklistActivity.class);
        startActivity(intent);
    }

    public void dataListview(){
        if (modelist.size()>0){
            adapter=new TecattedAdapter(context,modelist);
            gv.setAdapter(adapter);
        }else {
            gv.setVisibility(View.INVISIBLE);
            Toast.makeText(context, "No User Checked_in yet", Toast.LENGTH_SHORT).show();
        }
    }

    public void datafromServer(){
        dialog = new ProgressDialog(context);
        dialog.setMessage("Please Wait....");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                dialog.dismiss();
                //Toast.makeText(context, "data "+string, Toast.LENGTH_SHORT).show();
                if (string.equals("No Result"))
                {
                    Toast.makeText(context, "No User Checked in yet", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        JSONArray fruitsArray = new JSONArray(string);
                        for (int i = 0; i < fruitsArray.length(); ++i) {
                            JSONObject jsonObject = fruitsArray.getJSONObject(i);

                            int id = jsonObject.optInt("Stud_Id");
                            String Name = jsonObject.getString("Name");
                            if (jsonObject.isNull("Check_Out")) {
                                TecAttendmodel mm = new TecAttendmodel(id, Name, false);
                                modelist.add(mm);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                dataListview();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Toast.makeText(context, "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Something went wrong Check your Connection !");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        datafromServer();
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TecBulkActivity.this.finish();
                    }
                });
                alertDialogBuilder.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("date", fDate);
                return params;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);
    }

}
