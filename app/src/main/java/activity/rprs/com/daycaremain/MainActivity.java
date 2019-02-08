package activity.rprs.com.daycaremain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gv;
    Context context;
    Activity activity;
    public static String [] prgmNameList={"Meal","Bottle","Nap","Play","Nappy Potty",
            "Medicine/Temperature","Learning","Photo","Badges"};

    public static int [] prgmImages={R.drawable.status_food,
            R.drawable.status_notes,
            R.drawable.status_nap,
            R.drawable.status_play,
            R.drawable.status_nappypotty,
            R.drawable.status_medicine,
            R.drawable.status_learning,
            R.drawable.status_photos,
            R.drawable.status_badges};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;
        activity=this;

        gv=(GridView) findViewById(R.id.gridview);
        gv.setAdapter(new CustomAdapter(context, prgmNameList,prgmImages));

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(context,CommonActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }
}
