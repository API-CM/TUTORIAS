package com.tutorius.tutorius;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Custom adapter - "View Holder Pattern".
 *
 * @author danielme.com
 *
 */
public class CustomArrayAdapterDept extends ArrayAdapter<Row>{

    private LayoutInflater layoutInflater;

    public CustomArrayAdapterDept(Context context, List<Row> objects) {
        super(context, 0, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // holder pattern
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();

            convertView = layoutInflater.inflate(R.layout.departamento_item, parent, false);
            holder.setTextViewTitle((TextView) convertView
                    .findViewById(R.id.nombreDept));
            holder.setTextViewSubtitle((TextView) convertView
                    .findViewById(R.id.siglaDept));
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