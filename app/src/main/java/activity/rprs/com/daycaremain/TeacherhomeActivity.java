package activity.rprs.com.daycaremain;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class TeacherhomeActivity extends AppCompatActivity {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    Context context;

    final int[] ICONS = new int[]{
            R.drawable.ic_action_home,
            R.drawable.ic_action_alarm,
            R.drawable.ic_action_notification
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherhome);

        context=this;
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager(),this);
        adapter.addFragment(new TechomeFragment(), "Home",ICONS[0]);
        adapter.addFragment(new TecremindFragment(), "Reminder",ICONS[1]);
        adapter.addFragment(new TecNotificationFragment(), "Notification",ICONS[2]);
        adapter.addFragment(new TecChatFragment(), "Chat",ICONS[2]);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //for adding option menu item
        //toolbar.inflateMenu(R.menu.menu_additem);

        highLightCurrentTab(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                highLightCurrentTab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        /*toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.action_search)
                {
                    Intent intent=new Intent(getApplicationContext(),ItemAddActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });*/
    }

    private void highLightCurrentTab(int position) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(adapter.getTabView(i));
        }
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        assert tab != null;
        tab.setCustomView(null);
        tab.setCustomView(adapter.getSelectedTabView(position));
    }

}
