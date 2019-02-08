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

public class ParentHomeActivity extends AppCompatActivity {

    ListView listView;
    Context context;

    String[] animalName={"Inbox","Medicine","Note to teacher","Leave","Download Pdf Daily","Fee Payment"};

    final int[] ICONS = new int[]{
            R.drawable.ic_action_notificationred,
            R.drawable.status_medicine,
            R.drawable.status_notes,
            R.drawable.ic_action_date,
            R.drawable.pdfblack,
            R.drawable.rupee
    };
    ParenthomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);

        context=this;
        listView=(ListView)findViewById(R.id.listvhome);

        adapter=new ParenthomeAdapter(animalName,context,ICONS);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent1=new Intent(context,ParInboxActivity.class);
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2=new Intent(context,ParentsfeedActivity.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3=new Intent(context,ParChatteachActivity.class);
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4=new Intent(context,ParLeaveActivity.class);
                        startActivity(intent4);
                        break;
                    case 4:
                        Intent intent6=new Intent(context,ParPdfdownloadActivity.class);
                        startActivity(intent6);
                        break;
                    case 5:
                        Intent intent5=new Intent(context,ParPaymentActivity.class);
                        startActivity(intent5);
                        break;
                }
            }
        });
    }
}
