package com.example.dport.ford;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dport on 1/15/2016.
 */
public class ImageTextListBaseAdapterActivity extends Activity implements AdapterView.OnItemClickListener {


    public static final String[] titles = new String[] { "Focus",
            "Fusion", "Mustang" };

    public static final String[] descriptions = new String[] {
            "Driving this car is one intense experience",
            "Use voice commands to stay connected, get directions and adjust your climate control.",
            "Citrus Fruit",
    };

    public static final Integer[] images = {
            R.drawable.focus, R.drawable.fusion, R.drawable.mustang,
            R.drawable.escape, R.drawable.expedition, R.drawable.f150, R.drawable.transit};

    ListView listView;
    List<RowItem> rowItems;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rowItems = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            RowItem item = new RowItem(images[i], titles[i], descriptions[i]);
            rowItems.add(item);
        }

        listView = (ListView) findViewById(R.id.car_list);
        CustomBaseAdapter adapter = new CustomBaseAdapter(this, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Item " + (position + 1) + ": " + rowItems.get(position),
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}