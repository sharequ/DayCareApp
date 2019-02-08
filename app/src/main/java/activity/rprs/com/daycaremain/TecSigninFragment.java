package activity.rprs.com.daycaremain;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ProgressBar;
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
import java.util.List;
import java.util.Map;


public class TecSigninFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    GridView gv;
    CheckBox checkBox;
    Button addbtn;

    ArrayList<TecAttendmodel> modelist;
    TecSigninAdapter adapter;

    Boolean checkbstate=true;
    Context context;
    Calendar cal;
    private int  mHour, mMinute;
    String temp_class;
    SimpleDateFormat dateFormat;
    String fDate;

    ProgressDialog dialog;
    String url="http://reichprinz.com/teaAndroid/Daycare/checkinstud.php";

    String url2="http://reichprinz.com/teaAndroid/Daycare/fetchchilds.php";
    List<String> listsetid;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TecSigninFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TecSigninFragment newInstance(String param1, String param2) {
        TecSigninFragment fragment = new TecSigninFragment();
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
        View view= inflater.inflate(R.layout.fragment_tec_signin, container, false);

        context=getActivity();
        checkBox=(CheckBox)view.findViewById(R.id.checkBox9);
        modelist= new ArrayList<>();

        cal = Calendar.getInstance();
        // Get Current Time
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);
        dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        fDate=dateFormat.format(cal.getTime());

        addbtn=(Button)view.findViewById(R.id.button18);
        addbtn.setEnabled(false);
        addbtn.setOnClickListener(this);

        listsetid = new ArrayList<String>();
        gv=(GridView)view. findViewById(R.id.gridview);

        datafromServer();

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TecAttendmodel mmmd=modelist.get(position);
                if (mmmd.getState()) {
                    mmmd.setState(false);
                    checkbstate=false;
                    checkBox.setChecked(false);
                    checkbstate=true;
                }
                else {
                    mmmd.setState(true);
                }
                modelist.set(position,mmmd);
                adapter.notifyDataSetChanged();
                checkState();
                temp_class=mmmd.getClassname();
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkbstate && modelist.size()>0) {
                    for (int i = 0; modelist.size() > i; i++) {
                        TecAttendmodel mmmd = modelist.get(i);
                        if (!mmmd.getIslogin())
                            mmmd.setState(isChecked);
                    }
                    adapter.notifyDataSetChanged();
                    if (isChecked)
                        addbtn.setEnabled(true);
                    else
                        addbtn.setEnabled(false);
                }else {

                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button18:
                setlistids();
                saveData();
                break;
        }
    }

    public void checkState(){
        for (int i=0;modelist.size()>i;i++){
            TecAttendmodel mmmd=modelist.get(i);
            if (mmmd.getState()){
                addbtn.setEnabled(true);
                break;
            }else {
                addbtn.setEnabled(false);
            }
        }
    }

    public void setlistids(){
        for (int i = 0; modelist.size() > i; i++) {
            TecAttendmodel mmmd = modelist.get(i);
            if (!mmmd.getIslogin() && mmmd.getState()) {
                listsetid.add(String.valueOf(mmmd.getId()));
            }
        }
    }

    public void dataListview(){
        if (modelist.size()>0){
            adapter=new TecSigninAdapter(context,modelist);
            gv.setAdapter(adapter);
        }else {
            gv.setVisibility(View.INVISIBLE);
        }
    }

    public void datafromServer(){
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please Wait....");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST,url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                //progressBars.setVisibility(View.INVISIBLE);
                dialog.dismiss();
                //Toast.makeText(context, "data "+string, Toast.LENGTH_SHORT).show();
                if (string.equals("No Result"))
                {
                    Toast.makeText(context, "Something went wrong try after some time", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        JSONArray fruitsArray = new JSONArray(string);
                        for (int i = 0; i < fruitsArray.length(); ++i) {
                            JSONObject jsonObject = fruitsArray.getJSONObject(i);
                            int id = jsonObject.optInt("Stud_Id");
                            String Name = jsonObject.getString("Name");
                            String classame = jsonObject.getString("Stud_Class");
                            Boolean tempchk=true;
                            if (jsonObject.isNull("Activity_Id"))
                                tempchk=false;
                            TecAttendmodel mm = new TecAttendmodel(id,Name,classame,false,tempchk);
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
                        datafromServer();
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
                params.put("DaycareId", "1");
                return params;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);
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
                //Toast.makeText(context, "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
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
                params.put("Stud_Id", listsetid.toString());
                params.put("Stud_Class", temp_class);
                params.put("Check_In", mHour+":"+mMinute);
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
