package activity.rprs.com.daycaremain;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegiPhoneActivity extends AppCompatActivity implements View.OnClickListener {

    int position;

    Button submit;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi_phone);

        context=this;
        submit=(Button)findViewById(R.id.button20);
        submit.setOnClickListener(this);

        Bundle bundle=getIntent().getExtras();
        position=bundle.getInt("position");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button20:
                if (position==0){
                    //Intent intent=new Intent(context,)
                }else if (position==1){
                    Intent intent=new Intent(context,TeacherhomeActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(context,ParentHomeActivity.class);
                    startActivity(intent);
                }
                break;
        }

    }
}
