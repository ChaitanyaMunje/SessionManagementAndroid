package com.gtappdevelopers.sessionmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    //variables for our buttons.
    Button viewPDFbtn, generatePDFbtn;
    //declaring width and height for our PDF file.
    int pageHeight = 1120;
    int pagewidth = 792;
    //creating a bitmap variable for storing our images
    Bitmap bmp, scaledbmp;
    //constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializing our variables.
        viewPDFbtn = findViewById(R.id.idBtnViewPDF);
        generatePDFbtn = findViewById(R.id.idBtnGeneratePDF);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.gfgimage);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false);

        //below code is used for checking our permissions.
        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

        //setting onclick listner for our button.
        viewPDFbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //caling method to view our generated PDF file.
                viewPDF();
            }
        });

        generatePDFbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling method to generate our PDF file.
                generatePDF();
            }
        });


    }

    private void viewPDF() {
        //below line is for creating a variable for file which we have to open on button click.
        File file = new File(Environment.getExternalStorageDirectory(), "GFG.pdf");
        //after creating a variable for file we are opening that file via an intent to view.
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //below line is used to set uri for a file which we have to open.
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        //below line is for setting no flags to our activity.
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        //intent 1 is used for selecting a chooser with which we have to open our file.
        Intent intent1 = Intent.createChooser(intent, "Open With");
        try {
            //we are passing startActivity to open our file.
            startActivity(intent1);
        } catch (ActivityNotFoundException e) {
            //handling error case if file isnot found.
            e.printStackTrace();
            // Instruct the user to install a PDF reader here, or something
        }


    }

    private void generatePDF() {
        //creating an object variable for our PDF document.
        PdfDocument pdfDocument = new PdfDocument();
        //two variables for paint "paint" is used for drawaing shapes and we will use "title" for adding text in our PDF file.
        Paint paint = new Paint();
        Paint title = new Paint();
        //we are ading page info to our PDF file in hich we will be passing our pageWidth,pageHeight and number of pages and after that
        //we are calling it to create our PDF.
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
        //below line is used for setting start page for our PDF file.
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
        //creating a variable for canvas from our page of PDF.
        Canvas canvas = myPage.getCanvas();
        // below line is used to draw our image on our PDF file.
        //the first paramenter of our drawbitmap method is
        //our bitmap
        //second parameter is position from left
        //third parameter is position from top and last one is our variable for paint.
        canvas.drawBitmap(scaledbmp, 56, 40, paint);

        //below line is used for adding typeface for our text which we will be adding in our PDF file.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        //below line is used for setting text size which we will be displaying in our PDF file.
        title.setTextSize(15);
        //below line is sued for setting color of our text inside our PDF file.
        title.setColor(ContextCompat.getColor(this, R.color.purple_200));
        //below line is used to draw text in our PDF file.
        //the first parameter is our text, second parameter is position from start, third parameter is position from top
        //and then we are passing our variable of paint which is title.
        canvas.drawText("A portal for IT professionals.", 209, 100, title);
        canvas.drawText("Geeks for Geeks", 209, 80, title);

        //similarly we are creating another text and in this we are aligning this text to center of our PDF file.
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setColor(ContextCompat.getColor(this, R.color.purple_200));
        title.setTextSize(15);
        //below line is used for setting our text to center of PDF.
        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("This is sample document which we have created.", 396, 560, title);

        //after adding all attributes to our PDF file we will be finising our page.
        pdfDocument.finishPage(myPage);
        //below line is used to set the name of our PDF file and its path.
        File file = new File(Environment.getExternalStorageDirectory(), "GFG.pdf");

        try {
            //after creating a file name we will write our PDF file to that location.
            pdfDocument.writeTo(new FileOutputStream(file));
            //below line is to perint toast message on completion of PDF geneation.
            Toast.makeText(MainActivity.this, "PDF file generated succesfully.", Toast.LENGTH_SHORT).show();


        } catch (IOException e) {
            //below line is used to handle error
            e.printStackTrace();
        }
        //after storing our pdf to that location we are closing our PDF file.
        pdfDocument.close();


    }

    private boolean checkPermission() {
        //checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        //requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                //after requesting permissions we are showing users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

    }
}