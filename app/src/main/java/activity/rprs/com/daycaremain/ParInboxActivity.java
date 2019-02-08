package activity.rprs.com.daycaremain;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

public class ParInboxActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listView;
    Context context;
    ParInboxAdapter adapter;
    ArrayList<ParInboxModel> modelist;

    TextView tvnext,tvprev,tvdate;

    Calendar cal;
    private int mYear, mMonth, mDay;
    SimpleDateFormat dateFormat,dateFormat2;
    String fDate,fDate2;
    ProgressDialog dialog;
    String url2="http://reichprinz.com/teaAndroid/Daycare/fetchinboxparent.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_par_inbox);

        context=this;
        listView=(ListView)findViewById(R.id.listvhome);

        tvprev=(TextView)findViewById(R.id.textView9);
        tvdate=(TextView)findViewById(R.id.textView10);
        tvnext=(TextView)findViewById(R.id.textView11);

        tvprev.setOnClickListener(this);
        tvdate.setOnClickListener(this);
        tvnext.setOnClickListener(this);

        cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        dateFormat2= new SimpleDateFormat("dd-MMM-yyyy");
        fDate=dateFormat.format(cal.getTime());
        fDate2=dateFormat2.format(cal.getTime());
        tvdate.setText(fDate2);
        modelist=new ArrayList<ParInboxModel>();

        datafromServer(fDate);

    }


    public void datafromServer(final String date){
        dialog = new ProgressDialog(context);
        dialog.setMessage("Please Wait....");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                dialog.dismiss();
                //Toast.makeText(context, "data "+string, Toast.LENGTH_SHORT).show();
                if (string.equals("No Result"))
                {
                    Toast.makeText(context, "No Activity available for this date", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        JSONArray fruitsArray = new JSONArray(string);
                        for (int i = 0; i < fruitsArray.length(); ++i) {
                            JSONObject jsonObject = fruitsArray.getJSONObject(i);
                            String id = jsonObject.getString("ID");
                            String Name = jsonObject.getString("Type");
                            String desc = jsonObject.getString("Notification");
                            String date = jsonObject.getString("Date");
                            ParInboxModel mm = new ParInboxModel(id,Name,desc,date);
                            modelist.add(mm);
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
                dialog.dismiss();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Something went wrong Check your Connection !");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        datafromServer(date);
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ParInboxActivity.this.finish();
                    }
                });
                alertDialogBuilder.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("date", date);
                params.put("Stud_Id", "1");
                return params;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);
    }

    public void dataListview(){
        if (modelist.size()>0) {
            listView.setVisibility(View.VISIBLE);
            adapter = new ParInboxAdapter(context, modelist);
            listView.setAdapter(adapter);
        }else {
            listView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView9:
                cal.add(Calendar.DAY_OF_MONTH,-1);
                updateset();
                break;
            case R.id.textView10:
                setDate();
                break;
            case R.id.textView11:
                cal.add(Calendar.DAY_OF_MONTH,1);
                updateset();
                break;
        }
    }

    public void updateset(){
        fDate2=dateFormat2.format(cal.getTime());
        tvdate.setText(fDate2);
        modelist.clear();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        datafromServer(dateFormat.format(cal.getTime()));
    }

    public void setDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        fDate=year+"-"+(monthOfYear + 1)+"-"+dayOfMonth;
                        cal.set(year,monthOfYear,dayOfMonth);
                        updateset();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}
