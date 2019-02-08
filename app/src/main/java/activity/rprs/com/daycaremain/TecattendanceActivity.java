package activity.rprs.com.daycaremain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TecattendanceActivity extends AppCompatActivity implements View.OnClickListener{

    //GridView gv;
    //CheckBox checkBox;

    //ArrayList<TecAttendmodel> modelist;
    //TecattedAdapter adapter;

    //Boolean checkbstate=true;

    Context context;
    Activity activity;
    Button checkinbtn,checkoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tecattendance);

        context=this;
        activity=this;

        checkinbtn=(Button)findViewById(R.id.button13);
        checkoutbtn=(Button)findViewById(R.id.button14);

        checkinbtn.setOnClickListener(this);
        checkoutbtn.setOnClickListener(this);

        TecSigninFragment fragment = new TecSigninFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelaout, fragment);
        transaction.commit();

        /*checkBox=(CheckBox)findViewById(R.id.checkBox9);
        modelist= new ArrayList<>();

        TecAttendmodel mm1=new TecAttendmodel(1,"John",false);
        modelist.add(mm1);
        TecAttendmodel mm2=new TecAttendmodel(3,"Smith",false);
        modelist.add(mm2);
        TecAttendmodel mm3=new TecAttendmodel(4,"Vick",false);
        modelist.add(mm3);

        gv=(GridView) findViewById(R.id.gridview);
        adapter=new TecattedAdapter(context,modelist);
        gv.setAdapter(adapter);

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
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkbstate) {
                    for (int i = 0; modelist.size() > i; i++) {
                        TecAttendmodel mmmd = modelist.get(i);
                        mmmd.setState(isChecked);
                    }
                    adapter.notifyDataSetChanged();
                }else {

                }
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        Fragment fragment=null;
        switch (v.getId()){
            case R.id.button13:
                /*String str="ID ";
                for (int i=0;modelist.size()>i;i++){
                    TecAttendmodel model=modelist.get(i);
                    if (model.getState()){
                        str=str+model.getId()+",";
                    }
                }
                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();*/

                fragment = new TecSigninFragment();
                checkinbtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                checkoutbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                break;

            case R.id.button14:
                fragment = new TecSignoutFragment();
                checkoutbtn.setBackground(getResources().getDrawable(R.drawable.my_butfull));
                checkinbtn.setBackground(getResources().getDrawable(R.drawable.my_buttonblue));
                break;
        }
        if (fragment==null){}else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.framelaout, fragment);
            transaction.commit();
        }
    }
}
