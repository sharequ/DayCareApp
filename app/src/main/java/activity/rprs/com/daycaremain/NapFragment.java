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

public class NapFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button strbtn,endbtn,submitbtn;
    ImageView imgvmor,imgeven;

    TextView textViewtime,appbartextv;
    Calendar cal;
    SimpleDateFormat dateFormat,dateFormat2,timeFormat;
    Context context;
    String fTime,fDate,fDate2;
    private int  mHour, mMinute;
    String format,textnotif="Nap started";
    ImageView edittimeimg;
    String naptype1="Nap1_Start",naptype2="Nap1_End",naptype3="Nap2_Start",naptype4="Nap2_End",naptype;
    String napnote1="nap1note",napnote2="nap2note",napnote;
    Boolean btnstate=true,imgstate=true;

    ProgressDialog dialog;
    String url="http://reichprinz.com/teaAndroid/Daycare/addnap.php";
    EditText notetext;

    private OnFragmentInteractionListener mListener;

    public NapFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NapFragment newInstance(String param1, String param2) {
        NapFragment fragment = new NapFragment();
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
        View view= inflater.inflate(R.layout.fragment_nap, container, false);

        context=getActivity();
        appbartextv=(TextView)getActivity().findViewById(R.id.maintextv);
        appbartextv.setText("Nap");

        textViewtime=(TextView)view.findViewById(R.id.textViewtimews);

        strbtn=(Button)view.findViewById(R.id.button10);
        endbtn=(Button)view.findViewById(R.id.button11);
        submitbtn=(Button)view.findViewById(R.id.button2);

        imgvmor=(ImageView)view.findViewById(R.id.imageView6);
        imgeven=(ImageView)view.findViewById(R.id.imageView7);

        notetext=(EditText)view.findViewById(R.id.editText4);

        edittimeimg=(ImageView) view.findViewById(R.id.imageView2);
        edittimeimg.setOnClickListener(this);

        cal = Calendar.getInstance();
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);
        timeFormat = new SimpleDateFormat("hh:mm a");//"hh:mm:ss a"
        fTime=timeFormat.format(cal.getTime());
        dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        fDate=dateFormat.format(cal.getTime());
        dateFormat2= new SimpleDateFormat("dd-MMM-yyyy");
        fDate2=dateFormat2.format(cal.getTime());
        textViewtime.setText(fDate2+" "+fTime);

        napnote=napnote1;

        strbtn.setOnClickListener(this);
        endbtn.setOnClickListener(this);
        submitbtn.setOnClickListener(this);

        imgvmor.setOnClickListener(this);
        imgeven.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button10:
                strbtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                endbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                btnstate=true;
                textnotif="Nap started";
                break;

            case R.id.button11:
                endbtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                strbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                btnstate=false;
                textnotif="Nap end";
                break;

            case R.id.imageView6:
                imgvmor.setBackgroundColor(getResources().getColor(R.color.SaddleBrown));
                imgeven.setBackgroundColor(getResources().getColor(R.color.White));
                imgstate=true;
                napnote=napnote1;
                break;

            case R.id.imageView7:
                imgeven.setBackgroundColor(getResources().getColor(R.color.SaddleBrown));
                imgvmor.setBackgroundColor(getResources().getColor(R.color.White));
                imgstate=false;
                napnote=napnote2;
                break;

            case R.id.imageView2:
                setTime();
                break;

            case R.id.button2:
                if (imgstate && btnstate)
                    naptype=naptype1;
                else if (imgstate && !btnstate)
                    naptype=naptype2;
                else if (!imgstate && btnstate)
                    naptype=naptype3;
                else
                    naptype=naptype4;
                String note="";
                if (notetext.getText().toString().trim().length()>0){
                    note=notetext.getText().toString();
                }
                saveData(note);
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

    public void saveData(final String meal){
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
                //Toast.makeText(context,volleyError.toString(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Something went wrong Check your Connection !");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveData(meal);
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
                params.put("nap_type", naptype);
                params.put("note_type", napnote);
                params.put("nap_typev", mHour+":"+mMinute);
                params.put("note_typev", meal);
                params.put("text", textnotif);
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
