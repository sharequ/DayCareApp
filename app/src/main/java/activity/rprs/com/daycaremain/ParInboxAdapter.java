package activity.rprs.com.daycaremain;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ParInboxAdapter extends ArrayAdapter<ParInboxModel> {

    ArrayList<ParInboxModel> models;
    Context context;

    public ParInboxAdapter(Context context, ArrayList<ParInboxModel> models) {
        super(context, R.layout.parinboxlist,models);
        this.context=context;
        this.models=models;
    }

    public class ViewHolder{
        TextView description,time;
        ImageView imageView;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ParInboxAdapter.ViewHolder holder;
        ParInboxModel obj=models.get(position);

        View result;

        if (convertView==null){
            holder=new ParInboxAdapter.ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.parinboxlist,parent,false);
            holder.description=(TextView)convertView.findViewById(R.id.textView);
            holder.time=(TextView)convertView.findViewById(R.id.textViewtime);
            holder.imageView=(ImageView)convertView.findViewById(R.id.imageView);
            result=convertView;
            convertView.setTag(holder);
        }else {
            holder = (ParInboxAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }

        holder.description.setText(obj.getDesc());
        holder.time.setText(obj.getDate());
        if (obj.getType().equals("Meal"))
            holder.imageView.setImageResource(R.drawable.status_food);
        else if (obj.getType().equals("Badge"))
            holder.imageView.setImageResource(R.drawable.status_badges);
        else if (obj.getType().equals("Bottle"))
            holder.imageView.setImageResource(R.drawable.status_notes);
        else if (obj.getType().equals("Nap"))
            holder.imageView.setImageResource(R.drawable.status_nap);
        else if (obj.getType().equals("Play"))
            holder.imageView.setImageResource(R.drawable.status_play);
        else if (obj.getType().equals("Nappy"))
            holder.imageView.setImageResource(R.drawable.status_nappypotty);
        else if (obj.getType().equals("Learning"))
            holder.imageView.setImageResource(R.drawable.status_learning);
        else if (obj.getType().equals("Home Work"))
            holder.imageView.setImageResource(R.drawable.status_notes);
        else if (obj.getType().equals("checked in"))
            holder.imageView.setImageResource(R.drawable.status_checkin);
        else if (obj.getType().equals("checked out"))
            holder.imageView.setImageResource(R.drawable.status_checkout);


        return convertView;
    }
}
