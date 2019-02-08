package activity.rprs.com.daycaremain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.abdularis.civ.CircleImageView;

public class ParenthomeAdapter extends BaseAdapter{

    String[] result;
    Context context;
    int[] imageId;
    Boolean checked=true;
    private static LayoutInflater inflater = null;

    public ParenthomeAdapter(String[] result, Context context, int[] imageId) {
        this.result = result;
        this.context = context;
        this.imageId = imageId;
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
        ImageView imgs;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ParenthomeAdapter.Holder holder = new ParenthomeAdapter.Holder();
        View rowView;

        if (checked) {
            rowView = inflater.inflate(R.layout.teachbulklist, null);
            checked=false;
        }
        else {
            rowView = inflater.inflate(R.layout.parenthomelist, null);
            checked=true;
        }

        holder.tv = (TextView) rowView.findViewById(R.id.textView);
        //holder.img = (ImageView) rowView.findViewById(R.id.imageView1);
        holder.imgs = (ImageView) rowView.findViewById(R.id.imageView);

        holder.tv.setText(result[position]);
        holder.imgs.setImageResource(imageId[position]);
        //holder.imgs.setBackgroundColor(context.getResources().getColor(R.color.Aquamarine));

        holder.imgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rowView;
    }
}
