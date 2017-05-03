package com.tutorius.tutorius;


public class Row
{
    private String title;

    private String subtitle;

    private String id;  //agregado --

    private String disp;    //disponibilidad del profesor ---

    private String cancelada;

    private boolean checked;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSubtitle()
    {
        return subtitle;
    }

    public void setSubtitle(String subtitle)
    {
        this.subtitle = subtitle;
    }

    public String getId(){return id;}   //agregado ---
    public void setId(String id){this.id=id;}    //agregado ---

    public String getDisp(){return disp;}   //agregado --
    public void setDisp(String disp){this.disp=disp;}   //agregado

    public String getCancelada()
    {
        return cancelada;
    }

    public void setCancelada(String cancelada)
    {
        this.cancelada = cancelada;
    }

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }

}