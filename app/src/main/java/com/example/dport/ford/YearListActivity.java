package com.example.dport.ford;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class YearListActivity extends AppCompatActivity {

    OpenXC_Type mOpenXC;
    public static final String EXTRA = "Year_Ford";
    private ArrayList<Year> mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_year_list);

        Car car = (Car)getIntent().getSerializableExtra(EXTRA);
        TextView year_list = (TextView)findViewById(R.id.car_name);
        ImageView car_year = (ImageView)findViewById(R.id.year_photo);
        year_list.setText(car.getModel());
        car_year.setImageResource(car.getImage());


        mYear = new ArrayList<>();


        for (int i = 0; i < 8; i++){
            Year year = new Year();
            year.setYear(2008 + i);
            mYear.add(year);

        }
        ListView listView2 = (ListView)findViewById(R.id.list_view_year);
        listView2.setAdapter(new YearAdapter(mYear));
        mOpenXC = new OpenXC_Type();
        switch (car.getModel()){
            case "Focus":
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("YearListActivity"," This is the focus ");

                        if(position == 3 || position == 1 || position == 0 || position == 2)
                        {
                            mOpenXC.setType("Type1");
                            Log.d("YearListActivity","Type1");
                        }
                         else if (  position == 4 || position == 5 || position == 6 || position == 7)
                        {
                            mOpenXC.setType("Type4");
                            Log.d("YearListActivity", "Type4");
                        }

                    }
                });

                break;
            case "Fusion":
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("YearListActivity"," It is the fusion ");

                        if(position == 1 || position == 0 || position == 2)
                        {
                            mOpenXC.setType("Type1a");
                            Log.d("YearListActivity","Type1a");
                        }
                        else if (position == 3 ||  position == 4)
                        {
                            mOpenXC.setType("Type1");
                            Log.d("YearListActivity","Type1");
                        }
                        else if (position == 5 || position == 6 || position == 7)
                        {
                            mOpenXC.setType("Type3");
                            Log.d("YearListActivity","Type3");
                        }


                    }
                });

                break;
            case "Mustang":
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("YearListActivity"," It stang ");


                        if(position == 1 || position == 0)
                        {
                            mOpenXC.setType("Type1a");
                            Log.d("YearListActivity","Type1a");
                        }
                        else if (position == 5  || position == 3 ||  position == 4 || position == 2)
                        {
                            mOpenXC.setType("Type1");
                            Log.d("YearListActivity","Type1");
                        }
                        else if ( position == 6 || position == 7)
                        {
                            mOpenXC.setType("Type3");
                            Log.d("YearListActivity","Type3");
                        }


                    }
                });

                break;
            case "F-150":
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("YearListActivity"," It truck ");
                        if(position == 1 || position == 2 || position == 3)
                        {
                            mOpenXC.setType("Type1a");
                            Log.d("YearListActivity","Type1a");
                        }
                        else if (position == 5 ||  position == 4 || position ==6)
                        {
                            mOpenXC.setType("Type1");
                            Log.d("YearListActivity","Type1");
                        }
                        else if (position == 7)
                        {
                           mOpenXC.setType("Type3");
                            Log.d("YearListActivity","Type3");
                        }

                    }
                });

                break;
            case "Expedition":
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("YearListActivity"," It big truck ");


                        if ( position == 3)
                        {
                            mOpenXC.setType("Type1");
                            Log.d("YearListActivity","Type1");
                        }
                        else if (position == 4 || position == 5 || position == 6)
                        {
                            mOpenXC.setType("Type3");
                            Log.d("YearListActivity","Type3");
                        }

                    }
                });

                break;
            case "Transit":
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("YearListActivity"," work thingy ");

                    }
                });

                break;
            case "Escape":
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("YearListActivity"," nobody cares ");

                        if(position == 1 || position == 0 || position == 2)
                        {
                          mOpenXC.setType("Type1a");
                            Log.d("YearListActivity","Type1a");
                        }
                        else if (position == 3 ||  position == 4)
                        {
                          mOpenXC.setType("Type1");
                            Log.d("YearListActivity","Type1");
                        }
                        else if (position == 5 || position == 6)
                        {
                          mOpenXC.setType("Type3");
                            Log.d("YearListActivity","Type3");
                        }


                    }
                });

                break;
            default: Log.d("YearListActivity","i don't think it worked");
        }
   listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Year year = mYear.get(position);
            Intent i = new Intent(YearListActivity.this, Signals_view.class);
            i.putExtra(YearListActivity.EXTRA, year);
            startActivity(i);
        }

    });


    }
    private class YearAdapter extends ArrayAdapter<Year> {
        public YearAdapter(ArrayList<Year> model) {
            super(YearListActivity.this, R.layout.year_list_row, R.id.year_row, model);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            Year year = getItem(position);


            TextView descTextView = (TextView)convertView.findViewById(R.id.year_row);
            descTextView.setText(year.getYear().toString());


            return convertView;
        }
    }

}

