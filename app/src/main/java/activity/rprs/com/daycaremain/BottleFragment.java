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
import android.widget.SeekBar;
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


public class BottleFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SeekBar seekBar;
    TextView progresstext;

    TextView textViewtime,appbartextv;
    Calendar cal;
    SimpleDateFormat dateFormat,dateFormat2,timeFormat;
    Context context;
    String fTime,fDate,fDate2;
    private int  mHour, mMinute;
    String format,textnotif="Drink Water";
    ImageView edittimeimg;
    Button waterbtn,milkbtn,otherbtn,submit;
    int milkqt=0,milkn=0,waterqt=0,watern=1,otherqt=0,othern=0;
    int progressvalue=128;

    ProgressDialog dialog;
    String url="http://reichprinz.com/teaAndroid/Daycare/addbottle.php";

    private OnFragmentInteractionListener mListener;

    public BottleFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BottleFragment newInstance(String param1, String param2) {
        BottleFragment fragment = new BottleFragment();
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
        View view= inflater.inflate(R.layout.fragment_bottle, container, false);

        context=getActivity();
        appbartextv=(TextView)getActivity().findViewById(R.id.maintextv);
        appbartextv.setText("Bottle");

        submit=(Button)view.findViewById(R.id.button2);

        textViewtime=(TextView)view.findViewById(R.id.textViewmtime);

        waterbtn=(Button)view.findViewById(R.id.button8);
        milkbtn=(Button)view.findViewById(R.id.button9);
        otherbtn=(Button)view.findViewById(R.id.button10);

        waterbtn.setOnClickListener(this);
        milkbtn.setOnClickListener(this);
        otherbtn.setOnClickListener(this);
        submit.setOnClickListener(this);

        seekBar=(SeekBar)view.findViewById(R.id.seekBar);
        progresstext=(TextView)view.findViewById(R.id.textView20);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progresstext.setText(String.valueOf(i)+" ml");
                progressvalue=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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
            case R.id.button8:
                waterbtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                milkbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                otherbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                milkn=0;
                watern=1;
                othern=0;
                break;

            case R.id.button9:
                milkbtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                waterbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                otherbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                milkn=1;
                watern=0;
                othern=0;
                break;

            case R.id.button10:
                otherbtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                milkbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                waterbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                milkn=0;
                watern=0;
                othern=1;
                break;

            case R.id.imageView2:
                setTime();
                break;

            case R.id.button2:
                if (milkn==1) {
                    milkqt = progressvalue;
                    waterqt = 0;
                    otherqt=0;
                    textnotif="Drink Milk";
                }
                else if (watern==1){
                    waterqt = progressvalue;
                    milkqt = 0;
                    otherqt=0;
                    textnotif="Drink Water";
                }else {
                    otherqt = progressvalue;
                    milkqt = 0;
                    waterqt=0;
                    textnotif="Drink Other";
                }
                saveData();
                break;
        }
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

    public void saveData(){
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
                        saveData();
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
                params.put("milk", String.valueOf(milkqt));
                params.put("milkn", String.valueOf(milkn));
                params.put("water", String.valueOf(waterqt));
                params.put("watern", String.valueOf(watern));
                params.put("other", String.valueOf(otherqt));
                params.put("othern", String.valueOf(othern));
                params.put("time", mHour+":"+mMinute);
                params.put("text", textnotif+" ");
                return params;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);
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
