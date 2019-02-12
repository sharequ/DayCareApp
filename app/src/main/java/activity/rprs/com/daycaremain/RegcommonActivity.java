package activity.rprs.com.daycaremain;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class RegcommonActivity extends AppCompatActivity {

    ListView listView;
    Context context;

    String[] animalName={"Login as Admin","Login as Teacher","Login as Parent"};

    final int[] ICONS = new int[]{
            R.drawable.baby,
            R.drawable.babyboy,
            R.drawable.babygirl
    };
    ParenthomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regcommon);

        context=this;
        listView=(ListView)findViewById(R.id.listvhome);

        adapter=new ParenthomeAdapter(animalName,context,ICONS);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1=new Intent(context,RegiEmailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("position",position);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });
    }
}
