package com.example.dport.ford;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.view.LayoutInflater;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.openxc.VehicleManager;
import com.openxc.measurements.EngineSpeed;
import com.openxc.measurements.FuelConsumed;
import com.openxc.measurements.FuelLevel;
import com.openxc.measurements.Measurement;
import com.openxc.measurements.Odometer;
import com.openxc.measurements.VehicleSpeed;

import java.util.Random;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class Signals_view extends AppCompatActivity {

    private static final String TAG = "StarterActivity";

    private VehicleManager mVehicleManager;
    private TextView mEngineSpeedView;
    private TextView mFuelConsumedView;
    private TextView mFuelLevelView;
    private TextView mOdometerView;
    private TextView mVehicleSpeedView;
    private final android.os.Handler mHandler =  new android.os.Handler();
    private Runnable mTimer1;
    private Runnable mTimer2;
    private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;
    private double graph2LastXValue = 5d;
    private double speed=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_signals_view);

        // grab a reference to the engine speed text object in the UI, so we can
        // manipulate its value later from Java code
        mEngineSpeedView = (TextView) findViewById(com.example.dport.ford.R.id.engine_speed);
        mFuelConsumedView = (TextView) findViewById(com.example.dport.ford.R.id.fuel_consumed);
        mFuelLevelView = (TextView) findViewById(com.example.dport.ford.R.id.fuel_level);
        mOdometerView = (TextView) findViewById(com.example.dport.ford.R.id.odometer);
        mVehicleSpeedView = (TextView) findViewById(com.example.dport.ford.R.id.vehicle_speed);




        GraphView graph1 = (GraphView) findViewById(R.id.graph1);
        GraphView graph2 = (GraphView) findViewById(R.id.graph2);
        GraphView graph3 = (GraphView) findViewById(R.id.graph3);

        mSeries1 = new LineGraphSeries<>();
        mSeries2 = new LineGraphSeries<>();

        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(100);

        // LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
               /* new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)*/

        // });
        graph1.addSeries(mSeries1);
        graph2.addSeries(mSeries2);

    }




    @Override
    public void onPause() {
        super.onPause();
        // When the activity goes into the background or exits, we want to make
        // sure to unbind from the service to avoid leaking memory
        if(mVehicleManager != null) {
            Log.i(TAG, "Unbinding from Vehicle Manager");
            // Remember to remove your listeners, in typical Android
            // fashion.
            mVehicleManager.removeListener(EngineSpeed.class,
                    mSpeedListener);
            mVehicleManager.removeListener(FuelConsumed.class,
                    mFuelConsumedListener);
            mVehicleManager.removeListener(FuelLevel.class,
                    mFuelLevelListener);
            mVehicleManager.removeListener(Odometer.class,
                    mOdometerListener);
            mVehicleManager.removeListener(VehicleSpeed.class,
                    mVehicleSpeedListener);

            unbindService(mConnection);
            mVehicleManager = null;

            mHandler.removeCallbacks(mTimer1);
            mHandler.removeCallbacks(mTimer2);

        }
    }


    private DataPoint[] generateData() {
        int count = 30;
        DataPoint[] values = new DataPoint[count];
        for (int i=0; i<count; i++) {
            double f = mRand.nextDouble()*0.15+0.3;
            double y = Math.sin(i*f+2) + mRand.nextDouble()*0.3;
            DataPoint v = new DataPoint(i, y);
            values[i] = v;
        }
        return values;
    }

    double mLastRandom = 2;
    Random mRand = new Random();
    private double getRandom() {
        return mLastRandom += mRand.nextDouble()*0.5 - 0.25;
    }







    @Override
    public void onResume() {
        super.onResume();

        mTimer1 = new Runnable() {
            @Override
            public void run() {
                mSeries1.resetData(generateData());
                mHandler.postDelayed(this, 300);
            }
        };
        mHandler.postDelayed(mTimer1,300);

        mTimer2 = new Runnable() {
            @Override
            public void run() {
                graph2LastXValue += 1d;
                mSeries2.appendData(new DataPoint(graph2LastXValue, speed), true, 40);
                mHandler.postDelayed(this, 200);
            }
        };
        mHandler.postDelayed(mTimer2, 1000);


        // When the activity starts up or returns from the background,
        // re-connect to the VehicleManager so we can receive updates.
        if(mVehicleManager == null) {
            Intent intent = new Intent(this, VehicleManager.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }


    /* This is an OpenXC measurement listener object - the type is recognized
     * by the VehicleManager as something that can receive measurement updates.
     * Later in the file, we'll ask the VehicleManager to call the receive()
     * function here whenever a new EngineSpeed value arrives.
     */
    EngineSpeed.Listener mSpeedListener = new EngineSpeed.Listener() {
        @Override
        public void receive(Measurement measurement) {
            // When we receive a new EngineSpeed value from the car, we want to
            // update the UI to display the new value. First we cast the generic
            // Measurement back to the type we know it to be, an EngineSpeed.
            final EngineSpeed speed = (EngineSpeed) measurement;
            // In order to modify the UI, we have to make sure the code is
            // running on the "UI thread" - Google around for this, it's an
            // important concept in Android.
            Signals_view.this.runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    mEngineSpeedView.setText("Engine speed (RPM): "
                            + speed.getValue().doubleValue());
                }
            });
        }
    };

    FuelConsumed.Listener mFuelConsumedListener = new FuelConsumed.Listener() {
        @Override
        public void receive(Measurement measurement) {
            // When we receive a new EngineSpeed value from the car, we want to
            // update the UI to display the new value. First we cast the generic
            // Measurement back to the type we know it to be, an EngineSpeed.
            final FuelConsumed fuelCons = (FuelConsumed) measurement;
            // In order to modify the UI, we have to make sure the code is
            // running on the "UI thread" - Google around for this, it's an
            // important concept in Android.
            Signals_view.this.runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    mFuelConsumedView.setText("Fuel Consumed : "
                            + fuelCons.getValue().doubleValue());
                }
            });
        }
    };

    FuelLevel.Listener mFuelLevelListener = new FuelLevel.Listener() {
        @Override
        public void receive(Measurement measurement) {
            // When we receive a new EngineSpeed value from the car, we want to
            // update the UI to display the new value. First we cast the generic
            // Measurement back to the type we know it to be, an EngineSpeed.
            final FuelLevel fuelLev = (FuelLevel) measurement;
            // In order to modify the UI, we have to make sure the code is
            // running on the "UI thread" - Google around for this, it's an
            // important concept in Android.
            Signals_view.this.runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    mFuelLevelView.setText("Fuel Level: "
                            + fuelLev.getValue().doubleValue());
                }
            });
        }
    };

    Odometer.Listener mOdometerListener = new Odometer.Listener() {
        @Override
        public void receive(Measurement measurement) {
            // When we receive a new EngineSpeed value from the car, we want to
            // update the UI to display the new value. First we cast the generic
            // Measurement back to the type we know it to be, an EngineSpeed.
            final Odometer miles = (Odometer) measurement;
            // In order to modify the UI, we have to make sure the code is
            // running on the "UI thread" - Google around for this, it's an
            // important concept in Android.
            Signals_view.this.runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    mOdometerView.setText("Miles Driven: "
                            + miles.getValue().doubleValue());
                }
            });
        }
    };

    VehicleSpeed.Listener mVehicleSpeedListener = new VehicleSpeed.Listener() {
        @Override
        public void receive(Measurement measurement) {
            // When we receive a new EngineSpeed value from the car, we want to
            // update the UI to display the new value. First we cast the generic
            // Measurement back to the type we know it to be, an EngineSpeed.
            final VehicleSpeed carSpeed = (VehicleSpeed) measurement;
            // In order to modify the UI, we have to make sure the code is
            // running on the "UI thread" - Google around for this, it's an
            // important concept in Android.
            Signals_view.this.runOnUiThread(new Runnable() {
                public void run() {
                    // Finally, we've got a new value and we're running on the
                    // UI thread - we set the text of the EngineSpeed view to
                    // the latest value
                    mVehicleSpeedView.setText("Vehicle Speed: "
                            + carSpeed.getValue().doubleValue());
                    speed = carSpeed.getValue().doubleValue();
                }
            });
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        // Called when the connection with the VehicleManager service is
        // established, i.e. bound.
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            Log.i(TAG, "Bound to VehicleManager");
            // When the VehicleManager starts up, we store a reference to it
            // here in "mVehicleManager" so we can call functions on it
            // elsewhere in our code.
            mVehicleManager = ((VehicleManager.VehicleBinder) service)
                    .getService();

            // We want to receive updates whenever the EngineSpeed changes. We
            // have an EngineSpeed.Listener (see above, mSpeedListener) and here
            // we request that the VehicleManager call its receive() method
            // whenever the EngineSpeed changes
            mVehicleManager.addListener(EngineSpeed.class, mSpeedListener);
            mVehicleManager.addListener(FuelConsumed.class, mFuelConsumedListener);
            mVehicleManager.addListener(FuelLevel.class, mFuelLevelListener);
            mVehicleManager.addListener(Odometer.class, mOdometerListener);
            mVehicleManager.addListener(VehicleSpeed.class, mVehicleSpeedListener);
        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            Log.w(TAG, "VehicleManager Service  disconnected unexpectedly");
            mVehicleManager = null;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}