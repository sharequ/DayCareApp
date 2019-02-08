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


public class LearningFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button readbtn,langbtn,motorbtn,cogbtn,socbtn,submitbtn;
    Context context;

    TextView textViewtime,appbartextv;
    Calendar cal;
    SimpleDateFormat dateFormat,dateFormat2,timeFormat;
    String fTime,fDate,fDate2,headtext,btntext;
    private int  mHour, mMinute;
    String format;
    ImageView edittimeimg;
    ProgressDialog dialog;
    String url="http://reichprinz.com/teaAndroid/Daycare/addlearning.php";
    String url2="http://reichprinz.com/teaAndroid/Daycare/addlearningmulti.php";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LearningFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LearningFragment newInstance(String param1, String param2) {
        LearningFragment fragment = new LearningFragment();
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
        View view=inflater.inflate(R.layout.fragment_learning, container, false);
        context=getActivity();

        headtext = getArguments().getString("value");
        appbartextv=(TextView)getActivity().findViewById(R.id.maintextv);
        appbartextv.setText("Learning");

        textViewtime=(TextView)view.findViewById(R.id.textViewmtime);
        submitbtn=(Button)view.findViewById(R.id.button2);
        submitbtn.setOnClickListener(this);

        readbtn=(Button)view.findViewById(R.id.button3);
        langbtn=(Button)view.findViewById(R.id.button4);
        motorbtn=(Button)view.findViewById(R.id.button5);
        cogbtn=(Button)view.findViewById(R.id.button6);
        socbtn=(Button)view.findViewById(R.id.button7);

        btntext=readbtn.getText().toString();
        readbtn.setOnClickListener(this);
        langbtn.setOnClickListener(this);
        motorbtn.setOnClickListener(this);
        cogbtn.setOnClickListener(this);
        socbtn.setOnClickListener(this);

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
            case R.id.button3:
                readbtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                langbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                motorbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                cogbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                socbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                btntext=readbtn.getText().toString();
                break;
            case R.id.button4:
                readbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                langbtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                motorbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                cogbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                socbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                btntext=langbtn.getText().toString();
                break;
            case R.id.button5:
                readbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                langbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                motorbtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                cogbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                socbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                btntext=motorbtn.getText().toString();
                break;
            case R.id.button6:
                readbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                langbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                motorbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                cogbtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                socbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                btntext=socbtn.getText().toString();
                break;
            case R.id.button7:
                readbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                langbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                motorbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                cogbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                socbtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                btntext=socbtn.getText().toString();
                break;
            case R.id.imageView2:
                setTime();
                break;

            case R.id.button2:
                String note="";
                if (headtext.equalsIgnoreCase("single"))
                    saveDataPlay();
                else
                    saveDataPlay2();
                break;
        }
    }

    public void saveDataPlay(){
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
                        saveDataPlay();
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
                params.put("learning", btntext);
                params.put("time", mHour+":"+mMinute);
                return params;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);
    }

    public void saveDataPlay2(){
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please Wait....");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,url2, new Response.Listener<String>() {
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
                        saveDataPlay2();
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
                params.put("learning", btntext);
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
