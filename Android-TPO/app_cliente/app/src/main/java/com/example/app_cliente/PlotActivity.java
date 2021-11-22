package com.example.app_cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;

public class PlotActivity extends AppCompatActivity {

    XYPlot plot;
    Number[] series1Numbers = {0,1.34,-2,3.23,4,5.2,6,7.1,8.9,9,5,10.5,9,5};
    final Number[] domainLabels = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
    Button showMainWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);
        plot = findViewById(R.id.plot);

        // Turn the above arrays into XYSeries
        // XYSeries series1 = new SimpleXYSeries(series1Numbers);
        // SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Series 1");
        XYSeries series1 = new SimpleXYSeries(Arrays.asList(series1Numbers),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Series 1");

        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED,Color.GREEN,null,null);

        series1Format.setInterpolationParams(new CatmullRomInterpolator.Params(10,
                CatmullRomInterpolator.Type.Centripetal));

        plot.addSeries(series1,series1Format);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round( ((Number)obj).floatValue() );
                return toAppendTo.append(domainLabels[i]);
            }

            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });

        PanZoom.attach(plot);
    }
}