package activity.rprs.com.daycaremain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.abdularis.civ.AvatarImageView;
import com.github.abdularis.civ.CircleImageView;

public class CustomAdapter extends BaseAdapter {

    String[] result;
    Context context;
    int[] imageId;
    private static LayoutInflater inflater = null;

    public CustomAdapter(Context mainActivity, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result = prgmNameList;
        context = mainActivity;
        imageId = prgmImages;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView tv;
        CircleImageView imgs;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.gridviewlist, null);
        holder.tv = (TextView) rowView.findViewById(R.id.textView1);
        //holder.img = (ImageView) rowView.findViewById(R.id.imageView1);
        holder.imgs = (CircleImageView) rowView.findViewById(R.id.imageView1);

        holder.tv.setText(result[position]);
        holder.imgs.setImageResource(imageId[position]);
        //holder.imgs.setBackgroundColor(context.getResources().getColor(R.color.Aquamarine));

        holder.imgs.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rowView;
    }
}
