package activity.rprs.com.daycaremain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class RegiEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi_email);

        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        int position = bundle.getInt("position");

        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
    }
}
