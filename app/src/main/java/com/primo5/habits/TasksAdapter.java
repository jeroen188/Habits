package com.primo5.habits;


        import android.app.Activity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;



public class TasksAdapter extends ArrayAdapter{
    private LayoutInflater inflater;

    public TasksAdapter(Activity activity, String[] items){
        super(activity, R.layout.activity_daily_detail, items);
        inflater = activity.getWindow().getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        return inflater.inflate(R.layout.activity_daily_detail, parent, false);
    }
}