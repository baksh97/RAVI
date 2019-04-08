package com.example.kashish.ravi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.VibrationEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    String TAG = "MainActivity";
    private Spinner s;
    public static Vector<Vector<int[]>> corners;
    public static List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<String>();
//        list.add("square");
//        list.add("rectangle");
//        list.add("equilateral triangle");
//        list.add("isoceles triangle");
//        list.add("scalene triangle");
//        list.add("pentagon");
        list.add("Complex1");
        list.add("Complex2");
        list.add("Circle");
        list.add("Rectangle");
//        list.add("Semicircle");
        list.add("Square");
        list.add("Star");
        list.add("Triangle");
        list.add("Ellipse");

        final ArrayList<String> files = new ArrayList<>();
        files.add("Complex1.svg");
        files.add("1_svg_1.svg");
        files.add("Circle.svg");
        files.add("Rectangle.svg");
//        files.add("Semicircle.svg");
        files.add("squre.svg");
        files.add("Star.svg");
        files.add("triangle.svg");
        files.add("Ellipse.svg");


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Toast.makeText(this, "Height: "+height+" and width: "+width, Toast.LENGTH_SHORT).show();
        Log.e(TAG,"Height: "+height+" and width: "+width);

        scaleSVG ss = new scaleSVG();
        ss.scale(this, "Complex1.svg",width,height);
        ss.scale(this, "Complex2.svg",width,height);
        ss.scale(this, "Circle.svg",width,height);
        ss.scale(this, "Rectangle.svg",width,height);
        ss.scale(this, "squre.svg",width,height);
        ss.scale(this, "Star.svg",width,height);
        ss.scale(this, "traingle.svg",width,height);
        ss.scale(this, "Ellipse.svg",width,height);

        s = (Spinner) findViewById(R.id.spinner);
        s.setPrompt("Choose a diagram");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(dataAdapter);

        Button b = (Button) findViewById(R.id.button_showImage);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(MainActivity.this, showImage.class);
                n.putExtra("file",files.get(s.getSelectedItemPosition()));
                startActivity(n);
            }
        });

//        corners = new Vector<>();
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(new InputStreamReader(getAssets().open("Cordinates.txt")));
//            String         line = null;
//            StringBuilder  stringBuilder = new StringBuilder();
//            while((line = reader.readLine()) != null) {
//                Log.d("Line ",line);
//                String[] a = line.split(" ");
//                Vector<int[]> c = new Vector<>();
//                for(int i=2;i<a.length;i++){
//                    int[] cor = new int[2];
//                    String[] stringCor = a[i].split(",");
//                    cor[0] = Integer.parseInt(stringCor[0]);
//                    cor[1] = Integer.parseInt(stringCor[1]);
//                    c.add(cor);
//                }
//                corners.add(c);
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//// Vibrate for 500 milliseconds
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    vib.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
//                } else {
//                    //deprecated in API 26
//                    vib.vibrate(500);
//                }
//            }
//        });


    }
}
