package activity.rprs.com.daycaremain;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ParPaymentActivity extends AppCompatActivity {

    ListView listView;
    Context context;

    // Defined Array values to show in ListView
    String[] values = new String[] { "Pending Payment1",
            "Pending Payment2",
            "Pending Payment3"
    };

    String[] amount={"2500","1500","999"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_par_payment);

        context=this;
        listView=(ListView)findViewById(R.id.listvhome);


        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);*/

        ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
        for (int i=0;i<values.length;i++)
        {
            HashMap<String,String> hashMap=new HashMap<>();//create a hashmap to store the data in key value pair
            hashMap.put("name",values[i]);
            hashMap.put("amount",amount[i]+" /-.Rs");
            arrayList.add(hashMap);//add the hashmap into arrayList
        }
        String[] from={"name","amount"};//string array
        int[] to={R.id.textView,R.id.textView4};//int array of views id's
        SimpleAdapter simpleAdapter=new SimpleAdapter(context,arrayList,R.layout.parpaymentlist,from,to);//Create object and set the parameters for simpleAdapter
        listView.setAdapter(simpleAdapter);

    }
}
