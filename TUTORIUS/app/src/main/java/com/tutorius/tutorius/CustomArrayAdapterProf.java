package com.tutorius.tutorius;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Custom adapter - "View Holder Pattern".
 *
 */
public class CustomArrayAdapterProf extends ArrayAdapter<Row>{

    private LayoutInflater layoutInflater;

    public CustomArrayAdapterProf(Context context, List<Row> objects) {
        super(context, 0, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // holder pattern
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();

            convertView = layoutInflater.inflate(R.layout.profesor_item, parent, false);
            holder.setTextViewTitle((TextView) convertView
                    .findViewById(R.id.nombreProf));
            holder.setTextViewSubtitle((TextView) convertView
                    .findViewById(R.id.deptProf));
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final Row row = getItem(position);
        holder.getTextViewTitle().setText(row.getTitle());
        holder.getTextViewSubtitle().setText(row.getSubtitle());

        return convertView;
    }

    static class Holder
    {
        TextView textViewTitle;
        TextView textViewSubtitle;

        public TextView getTextViewTitle()
        {
            return textViewTitle;
        }

        public void setTextViewTitle(TextView textViewTitle)
        {
            this.textViewTitle = textViewTitle;
        }

        public TextView getTextViewSubtitle()
        {
            return textViewSubtitle;
        }

        public void setTextViewSubtitle(TextView textViewSubtitle)
        {
            this.textViewSubtitle = textViewSubtitle;
        }
    }
}