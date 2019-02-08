package activity.rprs.com.daycaremain;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.abdularis.civ.AvatarImageView;
import com.github.abdularis.civ.CircleImageView;

import java.util.ArrayList;

public class ChildsAdapter extends ArrayAdapter<TecAttendmodel> {

    Context context;
    ArrayList<TecAttendmodel> model;

    public ChildsAdapter(Context mainActivity, ArrayList<TecAttendmodel> model) {
        super(mainActivity,R.layout.childviewlist,model);
        context = mainActivity;
        this.model = model;
    }

    public class Holder {
        TextView tv;
        //ImageView img;
        AvatarImageView imgs;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final TecAttendmodel dataModel = getItem(position);
        ChildsAdapter.Holder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ChildsAdapter.Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.childviewlist, parent, false);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.textView1);
            //holder.img = (ImageView) rowView.findViewById(R.id.imageView1);
            viewHolder.imgs = (AvatarImageView) convertView.findViewById(R.id.imageView1);
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChildsAdapter.Holder) convertView.getTag();
            result=convertView;
        }


        viewHolder.tv.setText(dataModel.getName());
        //holder.imgs.setImageResource(imageId[position]);
        viewHolder.imgs.setText(dataModel.getName().substring(0,1));

        viewHolder.imgs.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MainActivity.class);
                DataClass.Stud_id= String.valueOf(dataModel.getId());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
