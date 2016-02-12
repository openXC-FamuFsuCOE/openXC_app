package com.example.dport.ford;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    public static final Integer[] images = { R.drawable.focus,
            R.drawable.fusion,
            R.drawable.mustang};

    public static final String EXTRA = "Year_Ford";
    ArrayList<Car> mCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         mCars = new ArrayList<>();

        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        Car car4 = new Car();
        Car car5 = new Car();
        Car car6 = new Car();
        Car car7 = new Car();

        car7.setModel("Escape");
        car6.setModel("Transit");
        car5.setModel("Expedition");
        car4.setModel("F-150");
        car3.setModel("Mustang");
        car2.setModel("Fusion");
        car1.setModel("Focus");

        mCars.add(car1);
        mCars.add(car2);
        mCars.add(car3);
        mCars.add(car4);
        mCars.add(car5);
        mCars.add(car6);
        mCars.add(car7);

        car1.setImage(R.drawable.focus);
        car2.setImage(R.drawable.fusion);
        car3.setImage(R.drawable.mustang);
        car4.setImage(R.drawable.f150);
        car5.setImage(R.drawable.expedition);
        car6.setImage(R.drawable.transit);
        car7.setImage(R.drawable.escape);


        car1.setDescription("Driving this car is one intense experience");
        car2.setDescription("Use voice commands to stay connected and adjust your climate control.");
        car3.setDescription("With a Mustang, you feel one thing: power.");
        car4.setDescription("Best selling brand of trucks for 39 years and counting");
        car5.setDescription("Be unstoppable");
        car6.setDescription("Durable. Dependable. Built Ford tough");
        car7.setDescription("Go see for yourself");


        ListView listView = (ListView)findViewById(R.id.car_list);
        listView.setAdapter(new ModelAdapter(mCars));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Car car = mCars.get(position);
                Intent i = new Intent(MainActivity.this, YearListActivity.class);
                i.putExtra(MainActivity.EXTRA, car);
                startActivity(i);
            }
        });
    }

    private class ModelAdapter extends ArrayAdapter<Car> {
        public ModelAdapter(ArrayList<Car> model) {
            super(MainActivity.this, R.layout.car_list_row, R.id.car_row_name, model);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            Car car = getItem(position);

            TextView nameTextView = (TextView)convertView.findViewById(R.id.car_row_name);
            ImageView pic = (ImageView)convertView.findViewById(R.id.icon);
            TextView descTextView = (TextView)convertView.findViewById(R.id.desc);
            descTextView.setText(car.getDescription());
            nameTextView.setText(car.getModel());
            pic.setImageResource(car.getImage());

            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
