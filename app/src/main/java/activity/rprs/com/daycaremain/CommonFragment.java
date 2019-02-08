package activity.rprs.com.daycaremain;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class CommonFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView textViewtime,appbartextv;
    Calendar cal;
    SimpleDateFormat dateFormat,dateFormat2,timeFormat;
    Context context;
    String fTime,fDate,fDate2,headtext;
    private int  mHour, mMinute;
    String format;
    ImageView edittimeimg;
    ProgressDialog dialog;
    Button submitbtn;
    EditText notetext;

    private OnFragmentInteractionListener mListener;

    public CommonFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CommonFragment newInstance(String param1, String param2) {
        CommonFragment fragment = new CommonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_common, container, false);

        headtext = getArguments().getString("value");
        context=getActivity();
        appbartextv=(TextView)getActivity().findViewById(R.id.maintextv);
        appbartextv.setText(headtext);

        textViewtime=(TextView)view.findViewById(R.id.textViewmtime);
        submitbtn=(Button)view.findViewById(R.id.button2);
        submitbtn.setOnClickListener(this);
        notetext=(EditText)view.findViewById(R.id.editText4);
        edittimeimg=(ImageView) view.findViewById(R.id.imageView2);
        edittimeimg.setOnClickListener(this);

        cal = Calendar.getInstance();
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);
        timeFormat = new SimpleDateFormat("hh:mm a");//"hh:mm:ss a"
        fTime=timeFormat.format(cal.getTime());
        dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        dateFormat2= new SimpleDateFormat("dd-MMM-yyyy");
        fDate=dateFormat.format(cal.getTime());
        fDate2=dateFormat2.format(cal.getTime());
        textViewtime.setText(fDate2+" "+fTime);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView2:
                setTime();
                break;
            case R.id.button2:
                String note="";
                if (notetext.getText().toString().trim().length()>0){
                    note=notetext.getText().toString();
                }
                if (headtext.equalsIgnoreCase("Play"))
                    saveDataPlay("Play "+note);
                if (headtext.equalsIgnoreCase("Play/cultural"))
                    saveDataPlay2("Play "+note,"http://reichprinz.com/teaAndroid/Daycare/addplaymultiple.php");
                else if (headtext.equalsIgnoreCase("Homework/Notes"))
                    saveDataPlay2("Home work "+note,"http://reichprinz.com/teaAndroid/Daycare/addhomework.php");
                break;
        }
    }

    public void saveDataPlay(final String strtext){
        String url="http://reichprinz.com/teaAndroid/Daycare/addplay.php";
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please Wait....");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                dialog.dismiss();
                //Toast.makeText(context, "data "+string, Toast.LENGTH_SHORT).show();
                if (string.equals("1")){
                    getActivity().finish();
                    //Intent intent=new Intent(context,)
                }else {
                    Toast.makeText(context, "Please Try again", Toast.LENGTH_SHORT).show();
                }
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
                        saveDataPlay(strtext);
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                });
                alertDialogBuilder.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("date", fDate);
                params.put("Stud_Id", DataClass.Stud_id);
                params.put("play", strtext);
                params.put("time", mHour+":"+mMinute);
                return params;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);
    }

    public void saveDataPlay2(final String strtext, final String url){
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please Wait....");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                dialog.dismiss();
                //Toast.makeText(context, "data "+string, Toast.LENGTH_SHORT).show();
                if (string.contains("1")){
                    getActivity().finish();
                    //Intent intent=new Intent(context,)
                }else {
                    Toast.makeText(context, "Please Try again", Toast.LENGTH_SHORT).show();
                }
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
                        saveDataPlay2(strtext,url);
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                });
                alertDialogBuilder.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("date", fDate);
                params.put("Stud_Id", DataClass.liststudids.toString());
                params.put("play", strtext);
                params.put("time", mHour+":"+mMinute);
                return params;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);
    }

    public void setTime(){
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay,
                                  int minute) {
                mHour=hourOfDay;
                mMinute=minute;
                if (hourOfDay == 0) {
                    hourOfDay += 12;
                    format = "AM";
                }
                else if (hourOfDay == 12) {
                    format = "PM";
                }
                else if (hourOfDay > 12) {
                    hourOfDay -= 12;
                    format = "PM";
                }
                else {
                    format = "AM";
                }
                String strminute="";
                if (minute==0)
                {
                    strminute="00";
                }else if (minute<10){
                    strminute="0"+minute;
                }else {
                    strminute=""+minute;
                }
                textViewtime.setText(fDate2+" "+hourOfDay + ":" + strminute+" "+format);
                fTime=hourOfDay + ":" + minute+" "+format;
            }
        }, mHour, mMinute,false);
        timePickerDialog.show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            /*throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
