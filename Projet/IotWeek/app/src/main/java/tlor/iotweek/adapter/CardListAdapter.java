package tlor.iotweek.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import tlor.iotweek.R;
import tlor.iotweek.model.Card;

public class CardListAdapter extends ArrayAdapter<Card> {


    public CardListAdapter(Context context) {
        super(context, R.layout.cell_card);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if(row == null)
        {
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_card, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView)row.findViewById(R.id.titleTextView);
            holder.subtitle = (TextView) row.findViewById(R.id.subtitleTextView);
            row.setTag(holder);
        } else {
            holder = (ViewHolder)row.getTag();
        }

        Card c = getItem(position);
        holder.title.setText(c.getCardName());
        holder.subtitle.setText(c.getCardId());
        return row;
    }

    static class ViewHolder {
        TextView title;
        TextView subtitle;
    }
}
