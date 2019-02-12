package activity.rprs.com.daycaremain;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ParentsfeedActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textViewdate;
    Calendar cal;
    SimpleDateFormat dateFormat,dateFormat2;
    Context context;
    String fDate,fDate2,format;
    EditText mdname,mdtimes,note;
    Button submit;

    ProgressDialog dialog;
    String url="http://reichprinz.com/teaAndroid/Daycare/paraddmedicine.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parentsfeed);

        context=this;

        textViewdate=(TextView)findViewById(R.id.textViewmdate);
        mdname=(EditText)findViewById(R.id.editText2);
        mdtimes=(EditText)findViewById(R.id.editText3);
        note=(EditText)findViewById(R.id.editText4);

        submit=(Button)findViewById(R.id.button);
        submit.setOnClickListener(this);

        cal = Calendar.getInstance();
        dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        dateFormat2= new SimpleDateFormat("dd-MMM-yyyy");
        fDate=dateFormat.format(cal.getTime());
        fDate2=dateFormat2.format(cal.getTime());
        textViewdate.setText(fDate2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:

                if (mdname.getText().toString().trim().length()<=0){
                    mdname.setError("Please Enter Medicine name");
                    mdname.requestFocus();
                }else if (mdtimes.getText().toString().trim().length()<=0){
                    mdtimes.setError("Please Enter Medicine name");
                    mdtimes.requestFocus();
                }else {
                    String notes="";
                    if (note.getText().toString().trim().length() > 0) {
                        notes = note.getText().toString();
                    }
                    saveData(notes);
                }
                break;
        }
    }


    public void saveData(final String str){
        final String medicine=mdname.getText().toString();
        final String medicinecount=mdtimes.getText().toString();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Please Wait....");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                dialog.dismiss();
                //Toast.makeText(context, "data "+string, Toast.LENGTH_SHORT).show();
                if (string.equals("1")){
                    ParentsfeedActivity.this.finish();
                    //Intent intent=new Intent(context,)
                }else {
                    Toast.makeText(context, "Please Try again", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                Toast.makeText(context,volleyError.toString(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Something went wrong Check your Connection !");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveData(str);
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ParentsfeedActivity.this.finish();
                    }
                });
                alertDialogBuilder.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("date", fDate);
                params.put("Stud_Id", "1");
                params.put("Name", medicine);
                params.put("Notimes", medicinecount);
                params.put("notes", str);
                return params;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);
    }
}
