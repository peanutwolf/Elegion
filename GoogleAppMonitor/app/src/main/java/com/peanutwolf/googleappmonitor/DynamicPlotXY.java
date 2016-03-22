package com.peanutwolf.googleappmonitor;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.androidplot.Plot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class DynamicPlotXY extends ActionBarActivity {

    private class PlotUpdater implements Observer {
        Plot plot;

        public PlotUpdater(Plot plot) {
            this.plot = plot;
        }

        @Override
        public void update(Observable o, Object arg) {
            plot.redraw();
        }
    }

    private XYPlot dynamicPlot;
    private PlotUpdater mPlotUpdater;
    private ShakeListener mShaker;
    private DynamicDataSource data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_plot_xy);

        dynamicPlot = (XYPlot) findViewById(R.id.dynamicXYPlot);
        mPlotUpdater = new PlotUpdater(dynamicPlot);


        dynamicPlot.getGraphWidget().setDomainValueFormat(new DecimalFormat("0"));

        mShaker = new ShakeListener(this.getApplicationContext());
        data = new DynamicDataSource(mShaker);
        SimpleDynamicSeries sine1Series = new SimpleDynamicSeries(data, 0);

        LineAndPointFormatter formatter1 = new LineAndPointFormatter(
                Color.rgb(0, 0, 0), null, null, null);
        formatter1.getLinePaint().setStrokeJoin(Paint.Join.ROUND);
        formatter1.getLinePaint().setStrokeWidth(10);

        dynamicPlot.addSeries(sine1Series, formatter1);

        data.addObserver(mPlotUpdater);

        dynamicPlot.setRangeBoundaries(0, 4000, BoundaryMode.FIXED);
        dynamicPlot.setDomainBoundaries(0, 10, BoundaryMode.AUTO);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShaker.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShaker.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        data.removeObserver(mPlotUpdater);
    }
}


class DynamicDataSource implements ShakeListener.OnShakeListener {
    private ShakeListener mShaker;
    private int domainBoundaries = 10;
    private List<Integer> axisX;
    private List<Integer> axisY;
    private List<Integer> axisZ;
    private DataObservable notifier;

    class DataObservable extends Observable {
        @Override
        public void notifyObservers() {
            setChanged();
            super.notifyObservers();
        }
    }

    DynamicDataSource(ShakeListener shakeListener){
        mShaker = shakeListener;
        mShaker.setOnShakeListener(this);
        axisX = Collections.synchronizedList(new RangedLinkedList<Integer>(domainBoundaries));
        axisY = Collections.synchronizedList(new RangedLinkedList<Integer>(domainBoundaries));
        axisZ = Collections.synchronizedList(new RangedLinkedList<Integer>(domainBoundaries));
        notifier = new DataObservable();
    }

    @Override
    public void onShake(float values[]) {
        synchronized (axisX){
            axisX.add((int)values[0]);
            axisY.add((int)values[1]);
            axisZ.add((int)values[2]);
            notifier.notifyObservers();
        }
    }

    public int getSize(){
        return axisX.size();
    }

    public int getValue(int index, int axis){
        synchronized (axisX){
            switch (axis){
                case 0 : return axisX.get(index);
                case 1 : return axisY.get(index);
                case 2 : return axisZ.get(index);
                default: return 0;
            }
        }
    }

    public void addObserver(Observer observer) {
        notifier.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        notifier.deleteObserver(observer);
    }

}

class SimpleDynamicSeries implements XYSeries{

    DynamicDataSource mShaker;
    int axis;

    SimpleDynamicSeries(DynamicDataSource shakeListener, int axis){
        mShaker = shakeListener;
        this.axis = axis;
    }

    @Override
    public int size() {
        return mShaker.getSize();
    }

    @Override
    public Number getX(int index) {
        return index;
    }

    @Override
    public Number getY(int index) {
        return mShaker.getValue(index, axis);
    }

    @Override
    public String getTitle() {
        return "Accelerometer";
    }
}