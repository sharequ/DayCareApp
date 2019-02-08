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
import android.widget.CheckBox;
import android.widget.CompoundButton;
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


public class WashroomDFragment extends Fragment implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView textViewtime,appbartextv;
    Calendar cal;
    SimpleDateFormat dateFormat,dateFormat2,timeFormat;
    Context context;
    String fTime,fDate,fDate2;
    private int  mHour, mMinute;
    String format,textnotif="Wet";
    ImageView edittimeimg;
    Button nappybtn,potybtn,accidentbtn,submitbtn;
    CheckBox checkBox1,checkBox2,checkBox3;
    ImageView imageVmor,imageVnoon,imageVnoon2,imageVnoon3,imageVeven,imageVnight;

    Boolean checknap=true,checkpot=false;

    ProgressDialog dialog;
    String url="http://reichprinz.com/teaAndroid/Daycare/addnappypoty.php";

    String Nappy_Wet="false", Nappy_Poop="false",Nappy_Dry="false", Potty_Pee="false"
            , Potty_Tried="false", Potty_Poop="false", Accident_Pee="false", Accident_Poop="false";

    private OnFragmentInteractionListener mListener;

    public WashroomDFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static WashroomDFragment newInstance(String param1, String param2) {
        WashroomDFragment fragment = new WashroomDFragment();
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
        View view= inflater.inflate(R.layout.fragment_washroom_d, container, false);

        context=getActivity();
        appbartextv=(TextView)getActivity().findViewById(R.id.maintextv);
        appbartextv.setText("Nappy Potty");

        textViewtime=(TextView)view.findViewById(R.id.textViewtimews);

        nappybtn=(Button)view.findViewById(R.id.button10);
        potybtn=(Button)view.findViewById(R.id.button11);
        accidentbtn=(Button)view.findViewById(R.id.button12);
        submitbtn=(Button)view.findViewById(R.id.button2);

        checkBox1=(CheckBox)view.findViewById(R.id.checkBoxws);
        checkBox2=(CheckBox)view.findViewById(R.id.checkBox2ws);
        checkBox3=(CheckBox)view.findViewById(R.id.checkBox3ws);

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

        nappybtn.setOnClickListener(this);
        potybtn.setOnClickListener(this);
        accidentbtn.setOnClickListener(this);
        submitbtn.setOnClickListener(this);

        checkBox1.setOnCheckedChangeListener(this);
        checkBox2.setOnCheckedChangeListener(this);
        checkBox3.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button10:
                nappybtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                potybtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                accidentbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                textnotif="Wet";
                checkBox1.setText("Wet");
                checkBox2.setText("Poop");
                checkBox3.setVisibility(View.VISIBLE);
                checkBox3.setText("Dry");
                checknap=true;checkpot=false;
                break;

            case R.id.button11:
                potybtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                nappybtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                accidentbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                textnotif="Pee";
                checkBox1.setText("Pee");
                checkBox2.setText("Poop");
                checkBox3.setVisibility(View.VISIBLE);
                checkBox3.setText("Tried");
                checknap=false;checkpot=true;
                break;

            case R.id.button12:
                accidentbtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                nappybtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                potybtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                textnotif="Pee";
                checkBox1.setText("Pee");
                checkBox2.setText("Poop");
                checkBox3.setVisibility(View.INVISIBLE);
                checknap=true;checkpot=true;
                break;

            case R.id.imageView2:
                setTime();
                break;

            case R.id.button2:
                if (checknap && checkpot){
                    Accident_Pee=String.valueOf(checkBox1.isChecked());
                    Accident_Poop=String.valueOf(checkBox2.isChecked());
                }else if (checknap && !checkpot){
                    Nappy_Wet=String.valueOf(checkBox1.isChecked());
                    Nappy_Poop=String.valueOf(checkBox2.isChecked());
                    Nappy_Dry=String.valueOf(checkBox3.isChecked());
                }else if (!checknap && checkpot){
                    Potty_Pee=String.valueOf(checkBox1.isChecked());
                    Potty_Poop=String.valueOf(checkBox2.isChecked());
                    Potty_Tried=String.valueOf(checkBox3.isChecked());
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
                params.put("Nappy_Wet", Nappy_Wet);
                params.put("Nappy_Poop", Nappy_Poop);
                params.put("Nappy_Dry", Nappy_Dry);
                params.put("Potty_Pee", Potty_Pee);
                params.put("Potty_Tried", Potty_Tried);
                params.put("Potty_Poop", Potty_Poop);
                params.put("Accident_Pee", Accident_Pee);
                params.put("Accident_Poop", Accident_Poop);
                params.put("time", mHour+":"+mMinute);
                params.put("text", "Nappy -"+textnotif);
                return params;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.checkBoxws:
                if (isChecked) {
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                }else {
                    checkBox2.setChecked(true);
                    textnotif=checkBox2.getText().toString();
                }
                break;
            case R.id.checkBox2ws:
                if (isChecked) {
                    checkBox1.setChecked(false);
                    checkBox3.setChecked(false);
                }else {
                    checkBox3.setChecked(true);
                    textnotif=checkBox3.getText().toString();
                }
                break;
            case R.id.checkBox3ws:
                if (isChecked) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                }else {
                    checkBox1.setChecked(true);
                    textnotif=checkBox1.getText().toString();
                }
                break;
        }
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
