package activity.rprs.com.daycaremain;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class CommonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        int value = getIntent().getExtras().getInt("position");
        Fragment fragment=null;

        switch (value){
            case 0://Meal
                fragment=new MealFragment();
                break;
            case 1://Bottle
                fragment=new BottleFragment();
                break;
            case 2://Nap
                fragment=new NapFragment();
                break;
            case 3://play
                fragment=new CommonFragment();
                Bundle bundle = new Bundle();
                bundle.putString("value", "Play");
                fragment.setArguments(bundle);
                break;
            case 4://washroom potty
                fragment=new WashroomDFragment();
                break;
            case 5://medicine/temperature
                fragment=new TemperatureFragment();
                break;
            case 6://Learning
                fragment=new LearningFragment();
                Bundle bundles = new Bundle();
                bundles.putString("value", "single");
                fragment.setArguments(bundles);
                break;
            case 7://Photo
                fragment=new PhotoFragment();
                break;
            case 8://Badges
                fragment=new BadgesFragment();
                break;
        }

        if (fragment!=null) {
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.flContent, fragment);
            //tx.addToBackStack("TabFragment");
            tx.commit();
        }
    }

}
