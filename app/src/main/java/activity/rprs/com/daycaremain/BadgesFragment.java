package activity.rprs.com.daycaremain;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BadgesFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView textViewtime,appbartextv;
    Calendar cal;
    SimpleDateFormat dateFormat,dateFormat2,timeFormat;
    Context context;
    String fTime,fDate,fDate2,badgename;
    private int  mHour, mMinute;
    String format;
    ImageView edittimeimg;
    Button submit;

    GridView gridView;

    String [] names=new String []{
            "bdge_attendance",
            "bdge_classfirst",
            "bdge_congs",
            "bdge_discipline",
            "bdge_great",
            "bdge_punctual",
            "bdge_reading",
            "bdge_star",
            "bdge_teamwork",
            "bdge_welldone"
    };

    final int[] ICONS = new int[]{
            R.drawable.bdge_attendance,
            R.drawable.bdge_classfirst,
            R.drawable.bdge_congs,
            R.drawable.bdge_discipline,
            R.drawable.bdge_great,
            R.drawable.bdge_punctual,
            R.drawable.bdge_reading,
            R.drawable.bdge_star,
            R.drawable.bdge_teamwork,
            R.drawable.bdge_welldone
    };
    SimpleAdapter simpleAdapter;
    ProgressDialog dialog;
    String url="http://reichprinz.com/teaAndroid/Daycare/addbadges.php";

    private OnFragmentInteractionListener mListener;

    public BadgesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BadgesFragment newInstance(String param1, String param2) {
        BadgesFragment fragment = new BadgesFragment();
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
        View view= inflater.inflate(R.layout.fragment_badges, container, false);

        context=getActivity();
        appbartextv=(TextView)getActivity().findViewById(R.id.maintextv);
        appbartextv.setText("Badges");

        textViewtime=(TextView)view.findViewById(R.id.textViewmtime);

        edittimeimg=(ImageView) view.findViewById(R.id.imageView2);
        edittimeimg.setOnClickListener(this);
        submit=(Button)view.findViewById(R.id.button2);
        submit.setOnClickListener(this);
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

        gridView=(GridView)view.findViewById(R.id.gridview);

        ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
        for (int i=0;i<ICONS.length;i++)
        {
            HashMap<String,String> hashMap=new HashMap<>();//create a hashmap to store the data in key value pair
            hashMap.put("image",ICONS[i]+"");
            arrayList.add(hashMap);//add the hashmap into arrayList
        }
        String[] from={"image"};//string array
        int[] to={R.id.imageView};//int array of views id's
        simpleAdapter=new SimpleAdapter(context,arrayList,R.layout.badgeslist,from,to);//Create object and set the parameters for simpleAdapter
        gridView.setAdapter(simpleAdapter);
        gridView.setSelection(0);
        badgename=names[0];

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                badgename=names[position];
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.imageView2:
                setTime();
                break;
            case R.id.button2:
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
                params.put("time", mHour+":"+mMinute);
                params.put("badges", badgename);
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
