package activity.rprs.com.daycaremain;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.abdularis.civ.AvatarImageView;

import java.util.ArrayList;

public class TecSigninAdapter extends ArrayAdapter<TecAttendmodel> {

    Context context;
    ArrayList<TecAttendmodel> model;

    // View lookup cache
    private static class ViewHolder {
        TextView tvname;
        ImageView imgcheck;
        AvatarImageView imgs;
    }

    public TecSigninAdapter(Context context, ArrayList<TecAttendmodel> model) {
        super(context, R.layout.tecattenlist,model);
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Get the data item for this position
        final TecAttendmodel dataModel = getItem(position);
        TecSigninAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new TecSigninAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.tecattenlist, parent, false);
            viewHolder.tvname = (TextView) convertView.findViewById(R.id.textView1);
            viewHolder.imgcheck = (ImageView) convertView.findViewById(R.id.imageView24);
            viewHolder.imgs = (AvatarImageView) convertView.findViewById(R.id.imageView1);;
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TecSigninAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.tvname.setText(dataModel.getName());
        viewHolder.imgs.setText(dataModel.getName().substring(0,1));
        if (dataModel.getState()){
            viewHolder.imgcheck.setVisibility(View.VISIBLE);
        }else {
            viewHolder.imgcheck.setVisibility(View.INVISIBLE);
        }
        if (dataModel.getIslogin()){
            viewHolder.imgcheck.setVisibility(View.VISIBLE);
            /*viewHolder.imgcheck.setColorFilter(ContextCompat.getColor(context, R.color.Lime),
                    PorterDuff.Mode.MULTIPLY)*/;
            viewHolder.imgcheck.setImageResource(R.drawable.ic_action_checkblue);
        }

        viewHolder.imgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataModel.islogin){

                }else {
                    if (dataModel.getState()) {
                        dataModel.setState(false);
                    } else {
                        dataModel.setState(true);
                    }
                    notifyDataSetChanged();
                }
            }
        });

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        if (model.get(position).getIslogin()){
            return false;
        }
        return super.isEnabled(position);
    }

}
