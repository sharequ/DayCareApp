package activity.rprs.com.daycaremain;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RegiEmailActivity extends AppCompatActivity implements View.OnClickListener {

    Button submit;
    Context context;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi_email);

        context=this;
        submit=(Button)findViewById(R.id.button20);
        submit.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        position = bundle.getInt("position");

        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v==submit){
            Intent intent=new Intent(context,RegiPhoneActivity.class);
            Bundle bundle=new Bundle();
            bundle.putInt("position",position);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
