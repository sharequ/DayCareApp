package activity.rprs.com.daycaremain;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TecbulkfraglistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tecbulkfraglist);

        int value = getIntent().getExtras().getInt("position");
        Fragment fragment=null;

        switch (value) {
            case 0://Homework/Notes
                fragment = new CommonFragment();
                Bundle bundle = new Bundle();
                bundle.putString("value", "Homework/Notes");
                fragment.setArguments(bundle);
                break;
            case 1://Play/cultural
                fragment = new CommonFragment();
                Bundle bundl = new Bundle();
                bundl.putString("value", "Play/cultural");
                fragment.setArguments(bundl);
                break;
            case 2://Learning
                fragment = new LearningFragment();
                Bundle bundles = new Bundle();
                bundles.putString("value", "muliple");
                fragment.setArguments(bundles);
                break;
        }
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.flContent, fragment);
        //tx.addToBackStack("TabFragment");
        tx.commit();
    }
}
