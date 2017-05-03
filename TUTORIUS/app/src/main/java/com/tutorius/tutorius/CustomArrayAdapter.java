package com.tutorius.tutorius;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<Row> implements
        View.OnClickListener {

    private LayoutInflater layoutInflater;
    ArrayList<String> selectedStrings = new ArrayList<String>();

    public CustomArrayAdapter(Context context, List<Row> objects) {
        super(context, 0, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // holder pattern
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();

            convertView = layoutInflater.inflate(R.layout.listview_row, parent, false);
            holder.setTextViewTitle((TextView) convertView
                    .findViewById(R.id.textViewTitle));
            holder.setTextViewSubtitle((TextView) convertView
                    .findViewById(R.id.textViewSubtitle));
            holder.setCheckBox((CheckBox) convertView
                    .findViewById(R.id.checkBox));
            holder.setTextViewCancelada((TextView) convertView
                    .findViewById(R.id.textCancelada));
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final Row row = getItem(position);
        holder.getTextViewTitle().setText(row.getTitle());
        holder.getTextViewSubtitle().setText(row.getSubtitle());
        holder.getCheckBox().setTag(position);
        holder.getCheckBox().setChecked(row.isChecked());
        holder.getCheckBox().setOnClickListener(this);
        holder.getTextViewCancelada().setText(row.getCancelada());

        

        return convertView;
    }


    @Override
    public void onClick(View v) {

        CheckBox checkBox = (CheckBox) v;
        int position = (Integer) v.getTag();
        getItem(position).setChecked(checkBox.isChecked());

        /*String msg = this.getContext().getString(R.string.check_toast,
                position, checkBox.isChecked())+(position+1)+" "+checkBox.isChecked();
        Toast.makeText(this.getContext(), msg, Toast.LENGTH_SHORT).show();*/
    }

    static class Holder
    {
        TextView textViewTitle;
        TextView textViewSubtitle;
        CheckBox checkBox;
        TextView textCancelada;

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
        public CheckBox getCheckBox()
        {
            return checkBox;
        }
        public void setCheckBox(CheckBox checkBox)
        {
            this.checkBox = checkBox;
        }

        public TextView getTextViewCancelada()
        {
            return textCancelada;
        }
        public void setTextViewCancelada(TextView textViewCancelada)
        {
            this.textCancelada = textViewCancelada;
        }

    }
}