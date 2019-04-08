package com.example.kashish.ravi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_WORLD_READABLE;

public class scaleSVG {

    private static final String TAG = "scaleSVG";
    boolean fileSaved = false;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

     int globalMinX = Integer.MAX_VALUE;
     int globalMinY = Integer.MAX_VALUE;
     int globalMaxX = Integer.MIN_VALUE;
     int globalMaxY = Integer.MIN_VALUE;

     ArrayList<String> otherStrings;
     ArrayList<ArrayList<Integer> > lines;
     ArrayList<ArrayList<Integer> > circles;
     ArrayList<ArrayList<Integer> > ellipses;

     double displayX = 1000.0;
     double displayY = 1000.0;
     int marginX = 40,marginY=40;

     int minusX,minusY ;
     double scale;

     void updateMaxMin(int maxX,int maxY,int minX,int minY) {
        if(globalMaxX < maxX)globalMaxX = maxX;
        if(globalMaxY < maxY)globalMaxY = maxY;
        if(globalMinX > minX)globalMinX = minX;
        if(globalMinY > minY)globalMinY = minY;


    }

      void writeFile(final Context context, String fileName){
//		String fileContent = "Hello Learner !! Welcome to howtodoinjava.com.";
          String largeString = "";
          File file = new File(fileName);
          FileOutputStream outputStream;
          FileInputStream inputStream;
          try {
              outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
//              outputStream.write(fileContents.getBytes());
//              for(int i=0;i<otherStrings.size()-1;i++) {
//                  outputStream.write(otherStrings.get(i).getBytes());
//                  outputStream.write("\n".getBytes());
//
//              }
              String others1 = "<?xml version=\"1.0\" standalone=\"no\"?>\n";
              String others2 = "<svg width=\'"+String.valueOf((int)displayX)+"\' height=\'"+String.valueOf((int)displayY)+"\' xmlns=\"http://www.w3.org/2000/svg\">\n";
              outputStream.write(others1.getBytes());
              outputStream.write(others2.getBytes());

              largeString += others1+others2;

              Log.d(TAG,"lines.length(): "+lines.size());

              for(int i=0;i<lines.size();i++) {
                  ArrayList<Integer> al = lines.get(i);

                  String s = "<line"+ " x1=\'"+String.valueOf(al.get(0))+"\' y1=\'"+String.valueOf(al.get(1))+"\' x2=\'"+String.valueOf(al.get(2))+"\' y2=\'"+String.valueOf(al.get(3))+ "\' stroke-width=\'2\' stroke=\'black\'/>\n";
                  largeString += s;
                  outputStream.write(s.getBytes());
              }

              for(int i=0;i<circles.size();i++) {
                  ArrayList<Integer> al = circles.get(i);

                  String s = "<circle"+ " cx=\'"+String.valueOf(al.get(0))+"\' cy=\'"+String.valueOf(al.get(1))+"\' r=\'"+String.valueOf(al.get(2))+"\' stroke-width=\'2\' stroke=\'black\'  fill=\'none\' stroke-opacity=\'2\' opacity=\'2\'/>\n";
                  largeString += s;
                  outputStream.write(s.getBytes());
              }

              for(int i=0;i<ellipses.size();i++) {
                  ArrayList<Integer> al = ellipses.get(i);

                  String s = "<ellipse"+ " cx=\'"+String.valueOf(al.get(0))+"\' cy=\'"+String.valueOf(al.get(1))+"\' rx=\'"+String.valueOf(al.get(2))+"\' ry=\'"+String.valueOf(al.get(3))+ "\' stroke-width=\'2\' stroke=\'black\'/>\n";
                  largeString += s;
                  outputStream.write(s.getBytes());
              }

              outputStream.write("</svg>".getBytes());

              outputStream.close();

              Log.d(TAG,"largeString: "+largeString);
//              File file =
//              inputStream = context.openFileInput(fileName);
//
//              FirebaseStorage storage = FirebaseStorage.getInstance();
////
//              StorageReference storageRef = storage.getReference();
//
//// Create a reference to "mountains.jpg"
//              StorageReference mountainsRef = storageRef.child(fileName);
//
////              final boolean filesaved = false;
//
//              UploadTask uploadTask = mountainsRef.putStream(inputStream);
//              uploadTask.addOnFailureListener(new OnFailureListener() {
//                  @Override
//                  public void onFailure(@NonNull Exception exception) {
//                      // Handle unsuccessful uploads
//                      Toast.makeText(context, "could not save file", Toast.LENGTH_SHORT).show();
//                  }
//              }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                  @Override
//                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                      // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
//                      // ...
//                      fileSaved = true;
//                      Toast.makeText(context, "file saved", Toast.LENGTH_SHORT).show();
////                      return true;
//                  }
//              });
//              inputStream.close();
          } catch (IOException e) {
              e.printStackTrace();
          }

//          BufferedWriter writer;
//        try {
//            writer = new BufferedWriter(new FileWriter(fileName));
//
//
//            writer.write(otherStrings.get(otherStrings.size()-1));
//
////			writer.write(fileContent);
//            writer.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
        }

     void scaleImage() {
        scale = Double.min((displayX-2*marginX)/(globalMaxX-globalMinX), (displayY-2*marginY)/(globalMaxY-globalMinY));

        minusX = globalMinX - marginX;
        minusY = globalMinY - marginY;

        for(ArrayList<Integer> al: lines) {
            int x1 = al.get(0);
            int y1 = al.get(1);
            int x2 = al.get(2);
            int y2 = al.get(3);

            x1-=minusX;
            x2-=minusX;
            y1-=minusY;
            y2-=minusY;

            x1 *= scale;
            x1 -= marginX*(scale-1);

            x2 *= scale;
            x2 -= marginX*(scale-1);

            y1 *= scale;
            y1 -= marginY*(scale-1);

            y2 *= scale;
            y2 -= marginY*(scale-1);

            al.set(0, x1);
            al.set(1, y1);
            al.set(2, x2);
            al.set(3, y2);
        }

        for(ArrayList<Integer> al: circles) {
            int cx = al.get(0);
            int cy = al.get(1);
            int r = al.get(2);

            cx -= minusX;
            cx *= scale;
            cx -= marginX*(scale-1);

            cy -= minusY;
            cy *= scale;
            cy -= marginY*(scale-1);

            r *= scale;

            al.set(0, cx);
            al.set(1, cy);
            al.set(2, r);
        }
    }

    public void scale(Context context,String fileName,int width, int height) {



        displayX = width;
        displayY = height;
//        String fileName = "Circle.svg";
        File file = new File(fileName);
        otherStrings = new ArrayList<>();
        ellipses = new ArrayList<>();
        circles = new ArrayList<>();
        lines = new ArrayList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));

            String st;
            while ((st = br.readLine()) != null) {
//                System.out.println(st);
//		    System.out.println(st);

                if(st.contains("svg")){

                }
                else if(st.contains("line")) {
                    st.replace('<', ' ');
                    st = st.replaceAll("\\s+", "");
                    st.replaceFirst("line", "");
                    int x1pos = st.indexOf("x1");
                    int x2pos = st.indexOf("x2");
                    int y1pos = st.indexOf("y1");
                    int y2pos = st.indexOf("y2");

                    //              String [] b = st.split("=");
                    int x1 = Integer.parseInt(st.substring(x1pos + 4, y1pos - 1));
                    int y1 = Integer.parseInt(st.substring(y1pos + 4, x2pos - 1));
                    int x2 = Integer.parseInt(st.substring(x2pos + 4, y2pos - 1));
                    char ch = st.charAt(y2pos + 3);
                    int index = st.indexOf(ch, y2pos + 4);
                    int y2 = Integer.parseInt(st.substring(y2pos + 4, index));


                    int maxY = Integer.max(y1, y2);
                    int maxX = Integer.max(x1, x2);
                    int minX = Integer.min(x1, x2);
                    int minY = Integer.min(y1, y2);

                    ArrayList<Integer> al = new ArrayList<>();
                    al.add(x1);
                    al.add(y1);
                    al.add(x2);
                    al.add(y2);


                    System.out.println(al);
                    updateMaxMin(maxX, maxY, minX, minY);

//	               = new ArrayList<>();
//	              al.add(maxX);
                    lines.add(al);

                }
                else if(st.contains("circle")) {
                    st.replace('<', ' ');
                    st = st.replaceAll("\\s+", "");
                    st.replaceFirst("circle", "");
                    //			  st = st.replaceAll("\\s+", "");

                    int cxpos = st.indexOf("cx=");
                    int cypos = st.indexOf("cy=");
                    int rpos = st.indexOf("r=");

                    //              Toast.makeText(this, "rPos: "+String.valueOf(rpos),Toast.LENGTH_SHORT).show();

                    int cx = Integer.parseInt(st.substring(cxpos + 4, cypos - 1));
                    int cy = Integer.parseInt(st.substring(cypos + 4, rpos - 1));
                    char ch = st.charAt(rpos + 2);
                    int index = st.indexOf(ch, rpos + 3);

                    //              Log.d("rpos and ch: ",String.valueOf(rpos)+" "+ ch);


                    int r = Integer.parseInt(st.substring(rpos + 3, index));

                    int maxX = cx+r;
                    int maxY = cy+r;
                    int minY = cy-r;
                    int minX = cx-r;

                    ArrayList<Integer> al = new ArrayList<>();
                    al.add(cx);
                    al.add(cy);
                    al.add(r);
//		    		al.add(y2);

                    updateMaxMin(maxX, maxY, minX, minY);
                    circles.add(al);


                }
                else if(st.contains("ellipse")) {
                    st.replace('<', ' ');
                    st = st.replaceAll("\\s+", "");
                    st.replaceFirst("ellipse", "");

                    int cxpos = st.indexOf("cx=");
                    int cypos = st.indexOf("cy=");
                    int rxpos = st.indexOf("rx=");
                    int rypos = st.indexOf("ry=");

                    //              Toast.makeText(this, "rPos: "+String.valueOf(rpos),Toast.LENGTH_SHORT).show();

                    int cx = Integer.parseInt(st.substring(cxpos + 4, cypos - 1));
                    int cy = Integer.parseInt(st.substring(cypos + 4, rxpos - 1));
                    int rx = Integer.parseInt(st.substring(rxpos + 4, rypos - 1));
                    char ch = st.charAt(rypos + 3);
                    int index = st.indexOf(ch, rypos + 4);

                    //              Log.d("rpos and ch: ",String.valueOf(rpos)+" "+ ch);


                    int ry = Integer.parseInt(st.substring(rypos + 4, index));

                    int maxX = cx+rx;
                    int maxY = cy+ry;
                    int minY = cy-ry;
                    int minX = cx-rx;
                    updateMaxMin(maxX, maxY, minX, minY);

                    ArrayList<Integer> al = new ArrayList<>();
                    al.add(cx);
                    al.add(cy);
                    al.add(rx);
                    al.add(ry);

                    ellipses.add(al);

                    //			  st = st.replaceAll("\\s+", "");
                }
                else {
                    otherStrings.add(st);
                }
//		  String[] sa = st.split(" ");
//		  for(int i=0;i<sa.length;i++) {
//			  String s = sst;
//			  if(s.equals("line")) {
//				  for(int j=i+1;j<sa.length;j++) {
//
//				  }
//			  }
//			  else if(s.equalsIgnoreCase("circle")){
//
//			  }
            }

            scaleImage();
            writeFile(context,fileName);



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
