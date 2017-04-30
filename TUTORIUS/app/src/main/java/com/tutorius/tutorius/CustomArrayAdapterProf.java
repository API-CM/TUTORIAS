package com.tutorius.tutorius;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


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

            //agregado --
            holder.setImagenView((ImageView) convertView
            .findViewById(R.id.imageDisponibilidad));

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final Row row = getItem(position);
        holder.getTextViewTitle().setText(row.getTitle());
        holder.getTextViewSubtitle().setText(row.getSubtitle());
        //agregado---

        String x=row.getDisp().toString();


        if(x.contains("0"))
            holder.getImagenView().setImageResource(android.R.drawable.presence_busy);
        else{
            holder.getImagenView().setImageResource(android.R.drawable.presence_online);
        }

        return convertView;
    }

    static class Holder
    {
        TextView textViewTitle;
        TextView textViewSubtitle;
        ImageView imagenView;

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
        //modificado
        public ImageView getImagenView(){return imagenView;}
        public void setImagenView(ImageView imagenView){this.imagenView=imagenView;}
    }
}