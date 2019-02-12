package activity.rprs.com.daycaremain;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class TechomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //TextView textView1,textView2,textView3;
    Context context;

    ListView listView;
    String[] animalName={"Check In/Check Out","Attendance","Bulk Activity","Individual Activity","Apply Leave"};

    final int[] ICONS = new int[]{
            R.drawable.status_checkin,
            R.drawable.bdge_attendance,
            R.drawable.bdge_teamwork,
            R.drawable.bdge_congs,
            R.drawable.ic_action_date
    };
    ParenthomeAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TechomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TechomeFragment newInstance(String param1, String param2) {
        TechomeFragment fragment = new TechomeFragment();
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
        View view= inflater.inflate(R.layout.fragment_techome, container, false);

        context=getActivity();

        listView=(ListView)view.findViewById(R.id.listvhome);

        adapter=new ParenthomeAdapter(animalName,context,ICONS);
        listView.setAdapter(adapter);


        /*textView1=(TextView)view.findViewById(R.id.textView);
        textView2=(TextView)view.findViewById(R.id.textView2);
        textView3=(TextView)view.findViewById(R.id.textView3);

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,TecattendanceActivity.class);
                startActivity(intent);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,TecBulkActivity.class);
                startActivity(intent);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ChildsActivity.class);
                startActivity(intent);
            }
        });*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent=new Intent(context,TecCheckinActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1=new Intent(context,TecattendanceActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2=new Intent(context,TecBulkActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3=new Intent(context,ChildsActivity.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4=new Intent(context,TecattendanceActivity.class);
                        startActivity(intent4);
                        break;
                }
            }
        });

        return view;
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
