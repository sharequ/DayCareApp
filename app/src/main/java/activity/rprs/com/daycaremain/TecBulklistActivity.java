package activity.rprs.com.daycaremain;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class TecBulklistActivity extends AppCompatActivity {

    ListView listView;
    Context context;

    String[] animalName={"Homework/Notes","Play/cultural Activity","Learning"};

    final int[] ICONS = new int[]{
            R.drawable.status_notes,
            R.drawable.status_play,
            R.drawable.status_learning
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tec_bulklist);

        context=this;
        listView=(ListView)findViewById(R.id.listvhome);

        ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
        for (int i=0;i<animalName.length;i++)
        {
            HashMap<String,String> hashMap=new HashMap<>();//create a hashmap to store the data in key value pair
            hashMap.put("name",animalName[i]);
            hashMap.put("image",ICONS[i]+"");
            arrayList.add(hashMap);//add the hashmap into arrayList
        }
        String[] from={"name","image"};//string array
        int[] to={R.id.textView,R.id.imageView};//int array of views id's
        SimpleAdapter simpleAdapter=new SimpleAdapter(context,arrayList,R.layout.teachbulklist,from,to);//Create object and set the parameters for simpleAdapter
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(context,TecbulkfraglistActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }
}
