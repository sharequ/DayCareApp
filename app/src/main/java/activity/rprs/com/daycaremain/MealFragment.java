package activity.rprs.com.daycaremain;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class MealFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView textViewtime,appbartextv;
    ImageView imageVmor,imageVnoon,imageVeven,edittimeimg;
    Button submit;
    CheckBox checkBall,checkBmost,checkBhalf,checkBsome,checkBnone;
    EditText mealname;

    Calendar cal;
    SimpleDateFormat dateFormat,dateFormat2,timeFormat;
    Context context;
    String fTime,fDate,fDate2,qntstr;
    private int  mHour, mMinute;
    String format;

    String meal_type,meal_quantity,meal_time;

    ProgressDialog dialog;
    String url="http://reichprinz.com/teaAndroid/Daycare/addmeal.php";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MealFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MealFragment newInstance(String param1, String param2) {
        MealFragment fragment = new MealFragment();
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
        View view= inflater.inflate(R.layout.fragment_meal, container, false);

        context=getActivity();
        appbartextv=(TextView)getActivity().findViewById(R.id.maintextv);
        appbartextv.setText("Add Meal");

        textViewtime=(TextView)view.findViewById(R.id.textViewmtime);
        mealname=(EditText)view.findViewById(R.id.editText);
        submit=(Button)view.findViewById(R.id.button);

        checkBall=(CheckBox)view.findViewById(R.id.checkBox);
        checkBmost=(CheckBox)view.findViewById(R.id.checkBox2);
        checkBhalf=(CheckBox)view.findViewById(R.id.checkBox3);
        checkBsome=(CheckBox)view.findViewById(R.id.checkBox6);
        checkBnone=(CheckBox)view.findViewById(R.id.checkBox7);

        imageVmor=(ImageView)view.findViewById(R.id.imageView8);
        imageVnoon=(ImageView)view.findViewById(R.id.imageView9);
        imageVeven=(ImageView)view.findViewById(R.id.imageView10);
        //imageVnight=(ImageView)view.findViewById(R.id.imageView11);

        edittimeimg=(ImageView) view.findViewById(R.id.imageView2);
        edittimeimg.setOnClickListener(this);

        cal = Calendar.getInstance();
        // Get Current Time
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);
        timeFormat = new SimpleDateFormat("hh:mm a");//"hh:mm:ss a"
        fTime=timeFormat.format(cal.getTime());
        dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        dateFormat2= new SimpleDateFormat("dd-MMM-yyyy");
        fDate=dateFormat.format(cal.getTime());
        fDate2=dateFormat2.format(cal.getTime());
        textViewtime.setText(fDate2+" "+fTime);

        imageVmor.setOnClickListener(this);
        imageVnoon.setOnClickListener(this);
        imageVeven.setOnClickListener(this);
        //imageVnight.setOnClickListener(this);
        meal_type="Meal1_Type";meal_quantity="Meal1_Quantity";meal_time="Meal1_Time";
        submit.setOnClickListener(this);

        qntstr=checkBall.getText().toString();

        checkBall.setOnClickListener(this);
        checkBmost.setOnClickListener(this);
        checkBhalf.setOnClickListener(this);
        checkBsome.setOnClickListener(this);
        checkBnone.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView8:
                imageVmor.setBackgroundColor(getResources().getColor(R.color.SaddleBrown));
                imageVnoon.setBackgroundColor(getResources().getColor(R.color.White));
                imageVeven.setBackgroundColor(getResources().getColor(R.color.White));
                //imageVnight.setBackgroundColor(getResources().getColor(R.color.White));
                meal_type="Meal1_Type";meal_quantity="Meal1_Quantity";meal_time="Meal1_Time";
                break;

            case R.id.imageView9:
                imageVmor.setBackgroundColor(getResources().getColor(R.color.White));
                imageVnoon.setBackgroundColor(getResources().getColor(R.color.SaddleBrown));
                imageVeven.setBackgroundColor(getResources().getColor(R.color.White));
                //imageVnight.setBackgroundColor(getResources().getColor(R.color.White));
                meal_type="Meal2_Type";meal_quantity="Meal2_Quantity";meal_time="Meal2_Time";
                break;

            case R.id.imageView10:
                imageVmor.setBackgroundColor(getResources().getColor(R.color.White));
                imageVnoon.setBackgroundColor(getResources().getColor(R.color.White));
                imageVeven.setBackgroundColor(getResources().getColor(R.color.SaddleBrown));
                //imageVnight.setBackgroundColor(getResources().getColor(R.color.White));
                meal_type="Meal3_Type";meal_quantity="Meal3_Quantity";meal_time="Meal3_Time";
                //Meal3_Type,Meal3_Quantity,Meal3_Time
                break;

            /*case R.id.imageView11:
                imageVmor.setBackgroundColor(getResources().getColor(R.color.White));
                imageVnoon.setBackgroundColor(getResources().getColor(R.color.White));
                imageVeven.setBackgroundColor(getResources().getColor(R.color.White));
                imageVnight.setBackgroundColor(getResources().getColor(R.color.SaddleBrown));
                break;*/

            case R.id.imageView2:
                setTime();
                break;

            case R.id.button:
                if (mealname.getText().toString().trim().length()<=0){
                    mealname.setError("Please Write Meal name");
                    mealname.requestFocus();
                }else {
                    saveData(mealname.getText().toString());
                }

                break;

            case R.id.checkBox:
                if (checkBall.isChecked()) {
                    qntstr=checkBall.getText().toString();
                    checkBmost.setChecked(false);
                    checkBhalf.setChecked(false);
                    checkBsome.setChecked(false);
                    checkBnone.setChecked(false);
                } else {
                    checkBmost.setChecked(true);
                }
                break;

            case R.id.checkBox2:
                if (checkBmost.isChecked()) {
                    qntstr=checkBmost.getText().toString();
                    checkBall.setChecked(false);
                    checkBhalf.setChecked(false);
                    checkBsome.setChecked(false);
                    checkBnone.setChecked(false);
                } else {
                    checkBhalf.setChecked(true);
                }
                break;

            case R.id.checkBox3:
                if (checkBhalf.isChecked()) {
                    qntstr=checkBhalf.getText().toString();
                    checkBall.setChecked(false);
                    checkBmost.setChecked(false);
                    checkBsome.setChecked(false);
                    checkBnone.setChecked(false);
                } else {
                    checkBsome.setChecked(true);
                }
                break;

            case R.id.checkBox6:
                if (checkBsome.isChecked()) {
                    qntstr=checkBsome.getText().toString();
                    checkBall.setChecked(false);
                    checkBmost.setChecked(false);
                    checkBhalf.setChecked(false);
                    checkBnone.setChecked(false);
                } else {
                    checkBnone.setChecked(true);
                }
                break;

            case R.id.checkBox7:
                if (checkBnone.isChecked()) {
                    qntstr=checkBnone.getText().toString();
                    checkBall.setChecked(false);
                    checkBmost.setChecked(false);
                    checkBsome.setChecked(false);
                    checkBhalf.setChecked(false);
                } else {
                    checkBall.setChecked(true);
                }
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
                params.put("meal_type", meal_type);
                params.put("meal_quantity", meal_quantity);
                params.put("meal_time", meal_time);
                params.put("meal_typev", meal);
                params.put("meal_quantityv", qntstr);
                params.put("meal_timev", mHour+":"+mMinute);
                params.put("meal_text", "had "+qntstr+" in Lunch "+meal);
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
