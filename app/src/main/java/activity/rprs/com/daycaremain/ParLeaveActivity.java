package activity.rprs.com.daycaremain;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class ParLeaveActivity extends AppCompatActivity implements View.OnClickListener{

    Spinner leavespin;
    RelativeLayout relativeLayout1,relativeLayout2,relativeLayout3;

    Button sickbtn,fewerbtn,emergbtn,otherbtn,applybtn;
    TextView todate,fromdate,onedate;
    ImageView edittimeimg,edittimeimg2,edittimeimg3,imageup,imagesetv;
    SimpleDateFormat dateFormat,dateFormat2;

    private int mYear, mMonth, mDay;
    String fDate,endate,fDate2,btntext;
    Context context;
    Calendar c,c2;

    Bitmap bitmap;
    ProgressDialog progressDialog,dialog;
    String ServerUploadPath ="http://reichprinz.com/teaAndroid/Daycare/applyleaveimg.php";
    String url="http://reichprinz.com/teaAndroid/Daycare/applyleave.php";
    boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_par_leave);

        context=this;

        leavespin=(Spinner)findViewById(R.id.spinner);

        relativeLayout1=(RelativeLayout)findViewById(R.id.relativenew1);
        relativeLayout2=(RelativeLayout)findViewById(R.id.relativenew2);
        relativeLayout3=(RelativeLayout)findViewById(R.id.relativenew3);

        sickbtn=(Button)findViewById(R.id.button10);
        fewerbtn=(Button)findViewById(R.id.button11);
        emergbtn=(Button)findViewById(R.id.button12);
        otherbtn=(Button)findViewById(R.id.button13);
        applybtn=(Button)findViewById(R.id.button2);

        todate=(TextView)findViewById(R.id.textViewmtime);
        fromdate=(TextView)findViewById(R.id.textViewmtimes);
        onedate=(TextView)findViewById(R.id.textVietime);

        edittimeimg=(ImageView) findViewById(R.id.imageView2);
        edittimeimg2=(ImageView) findViewById(R.id.imageView22);
        edittimeimg3=(ImageView) findViewById(R.id.imageView);
        imageup=(ImageView) findViewById(R.id.imageView4);
        imagesetv=(ImageView)findViewById(R.id.imageView5);
        imagesetv.setVisibility(View.GONE);
        edittimeimg.setOnClickListener(this);
        edittimeimg2.setOnClickListener(this);
        edittimeimg3.setOnClickListener(this);
        imageup.setOnClickListener(this);

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        dateFormat2= new SimpleDateFormat("dd-MM-yyyy");
        fDate=dateFormat.format(c.getTime());
        c2=Calendar.getInstance();
        c2.add(Calendar.DAY_OF_MONTH,1);
        endate=dateFormat.format(c2.getTime());
        fDate2=dateFormat2.format(c.getTime());

        leavespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        relativeLayout1.setVisibility(View.GONE);
                        relativeLayout2.setVisibility(View.VISIBLE);
                        relativeLayout3.setVisibility(View.GONE);
                        fDate=dateFormat.format(c.getTime());
                        endate=fDate;
                        onedate.setText("Today");
                        break;
                    case 1:
                        relativeLayout1.setVisibility(View.VISIBLE);
                        relativeLayout2.setVisibility(View.GONE);
                        relativeLayout3.setVisibility(View.GONE);
                        fDate=dateFormat.format(c.getTime());
                        endate=dateFormat.format(c2.getTime());
                        fromdate.setText("Tomorrow");
                        todate.setText("Today");
                        break;
                    case 2:
                        relativeLayout1.setVisibility(View.GONE);
                        relativeLayout2.setVisibility(View.VISIBLE);
                        relativeLayout3.setVisibility(View.VISIBLE);
                        fDate=dateFormat.format(c.getTime());
                        endate=fDate;
                        onedate.setText("Today");
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btntext=sickbtn.getText().toString();
        sickbtn.setOnClickListener(this);
        fewerbtn.setOnClickListener(this);
        emergbtn.setOnClickListener(this);
        otherbtn.setOnClickListener(this);
        applybtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button10://sick
                sickbtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                fewerbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                emergbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                otherbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                btntext=sickbtn.getText().toString();
                break;
            case R.id.button11://fever
                sickbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                fewerbtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                emergbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                otherbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                btntext=fewerbtn.getText().toString();
                break;
            case R.id.button12://emergency
                sickbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                fewerbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                emergbtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                otherbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                btntext=emergbtn.getText().toString();
                break;
            case R.id.button13://other
                sickbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                fewerbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                emergbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                otherbtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                btntext=otherbtn.getText().toString();
                break;
            case R.id.button2://apply leave
                if (bitmap!=null)
                    ImageUploadToServerFunction();
                else
                    saveData();
                break;
            case R.id.imageView2:
                setDate(todate,true);
                break;
            case R.id.imageView22:
                setDate(fromdate,false);
                break;
            case R.id.imageView:
                setDate(onedate,true);
                break;
            case R.id.imageView4:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
                break;
        }
    }

    public void setDate(final TextView textView,final boolean state){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String day=checkSingledigit(dayOfMonth);
                        String mont=checkSingledigit((monthOfYear + 1));
                        textView.setText(day + "-" + (mont) + "-" + year);
                        if (state) {
                            fDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            endate=fDate;
                        }
                        else {
                            endate=year+"-"+(monthOfYear + 1)+"-"+dayOfMonth;}
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public String checkSingledigit(int number){
        if (number<=9)
            return "0"+number;
        else
            return String.valueOf(number);
    }

    public void saveData(){
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
                    ParLeaveActivity.this.finish();
                    //Intent intent=new Intent(context,)
                }else {
                    Toast.makeText(context, "Please Try again", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                //Toast.makeText(context,volleyError.toString(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Something went wrong Check your Connection !");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveData();
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ParLeaveActivity.this.finish();
                    }
                });
                alertDialogBuilder.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Stud_Id", "0");
                params.put("strdate", fDate);
                params.put("enddate", endate);
                params.put("reason", btntext);
                return params;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                bitmap=getResizedBitmap(bitmap,800);
                imagesetv.setVisibility(View.VISIBLE);
                imagesetv.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void ImageUploadToServerFunction(){

        ByteArrayOutputStream byteArrayOutputStreamObject ;
        byteArrayOutputStreamObject = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();
        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(context,"Image is Uploading..","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
                // Dismiss the progress dialog after done uploading.
                progressDialog.dismiss();
                // Printing uploading success message coming from server on android app.
                if (string1.equals("1"))
                {
                    ParLeaveActivity.this.finish();
                }else {
                    Toast.makeText(context,"Please Try again",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            protected String doInBackground(Void... params) {
                ParLeaveActivity.ImageProcessClass imageProcessClass = new ParLeaveActivity.ImageProcessClass();
                HashMap<String,String> HashMapParams = new HashMap<String,String>();
                HashMapParams.put("Stud_Id", "1");
                HashMapParams.put("strdate", fDate);
                HashMapParams.put("enddate", endate);
                HashMapParams.put("reason", btntext);
                HashMapParams.put("image_path", ConvertImage);
                HashMapParams.put("image_name", "Leave1"+fDate2);
                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);
                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url;
                HttpURLConnection httpURLConnectionObject ;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject ;
                BufferedReader bufferedReaderObject ;
                int RC ;
                url = new URL(requestURL);
                httpURLConnectionObject = (HttpURLConnection) url.openConnection();
                httpURLConnectionObject.setReadTimeout(19000);
                httpURLConnectionObject.setConnectTimeout(19000);
                httpURLConnectionObject.setRequestMethod("POST");
                httpURLConnectionObject.setDoInput(true);
                httpURLConnectionObject.setDoOutput(true);
                OutPutStream = httpURLConnectionObject.getOutputStream();
                bufferedWriterObject = new BufferedWriter(
                        new OutputStreamWriter(OutPutStream, "UTF-8"));
                bufferedWriterObject.write(bufferedWriterDataFN(PData));
                bufferedWriterObject.flush();
                bufferedWriterObject.close();
                OutPutStream.close();
                RC = httpURLConnectionObject.getResponseCode();
                if (RC == HttpsURLConnection.HTTP_OK) {
                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));
                    stringBuilder = new StringBuilder();
                    String RC2;
                    while ((RC2 = bufferedReaderObject.readLine()) != null){
                        stringBuilder.append(RC2);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }
        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {
            StringBuilder stringBuilderObject;
            stringBuilderObject = new StringBuilder();
            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilderObject.append("&");
                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));
                stringBuilderObject.append("=");
                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }
            return stringBuilderObject.toString();
        }
    }

}
