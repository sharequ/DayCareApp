package activity.rprs.com.daycaremain;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.abdularis.civ.AvatarImageView;

import java.util.ArrayList;

public class TecattedAdapter extends ArrayAdapter<TecAttendmodel> {

    Context context;
    ArrayList<TecAttendmodel> model;

    // View lookup cache
    private static class ViewHolder {
        TextView tvname;
        ImageView imgcheck;
        AvatarImageView imgs;
    }

    public TecattedAdapter( Context context, ArrayList<TecAttendmodel> model) {
        super(context, R.layout.tecattenlist,model);
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Get the data item for this position
        final TecAttendmodel dataModel = getItem(position);
        TecattedAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new TecattedAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.tecattenlist, parent, false);
            viewHolder.tvname = (TextView) convertView.findViewById(R.id.textView1);
            viewHolder.imgcheck = (ImageView) convertView.findViewById(R.id.imageView24);
            viewHolder.imgs = (AvatarImageView) convertView.findViewById(R.id.imageView1);;
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TecattedAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.tvname.setText(dataModel.getName());
        viewHolder.imgs.setText(dataModel.getName().substring(0,1));
        if (dataModel.getState()){
            viewHolder.imgcheck.setVisibility(View.VISIBLE);
        }else {
            viewHolder.imgcheck.setVisibility(View.INVISIBLE);
        }

        viewHolder.imgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataModel.getState()){
                    dataModel.setState(false);
                }else {
                    dataModel.setState(true);
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
