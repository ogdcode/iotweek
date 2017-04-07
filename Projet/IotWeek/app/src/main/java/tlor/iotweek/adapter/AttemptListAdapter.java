package tlor.iotweek.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import tlor.iotweek.R;
import tlor.iotweek.model.Attempt;

public class AttemptListAdapter extends ArrayAdapter<Attempt> {


    public AttemptListAdapter(Context context) {
        super(context, R.layout.cell_attempt);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if(row == null)
        {
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_attempt, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView)row.findViewById(R.id.imageView);
            holder.title = (TextView)row.findViewById(R.id.titleTextView);
            holder.subtitle = (TextView) row.findViewById(R.id.subtitleTextView);
            row.setTag(holder);
        } else {
            holder = (ViewHolder)row.getTag();
        }

        Attempt a = getItem(position);
        holder.title.setText(a.getCardId());
        Date date = new Date(a.getTimestamp());
        String relativeTime = (String) DateUtils.getRelativeTimeSpanString(date.getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
        holder.subtitle.setText(relativeTime);
        holder.image.setImageResource(a.isAuthorized() ? R.drawable.ic_check_black_24dp : R.drawable.ic_error_outline_black_24dp);
        return row;
    }

    static class ViewHolder {
        TextView title;
        TextView subtitle;
        ImageView image;
    }
}
