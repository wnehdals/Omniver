package com.example.omniver;

import android.app.ActionBar;
import android.content.Intent;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.example.omniver.base.BottomNavigationActivity;
import com.example.omniver.evaluate_service.EvaluationActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CategoryActivity extends BottomNavigationActivity implements View.OnClickListener {
    private BottomNavListener bottomNavListener;
    private BottomNavigationView navView;
    public String currentPhotoPath;
    private String timeStamp;
    private Uri photoUri;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int SEND_CALCULATEACTIVITY = 2;

    private Button takePhotoButton;
    private Button tempClothDictionaryButton;
    private Button clothLogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        bottomNavListener = new BottomNavListener();
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(bottomNavListener);
        init();
    }

    public void init() {
        takePhotoButton = findViewById(R.id.take_photo_button);
        takePhotoButton.setOnClickListener(this);
        tempClothDictionaryButton = findViewById(R.id.recommended_list_button);
        tempClothDictionaryButton.setOnClickListener(this);
        clothLogButton = findViewById(R.id.cloth_log_button);
        clothLogButton.setOnClickListener(this);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_center);
        TextView title = findViewById(R.id.actionbar_title);
        title.setText("카테고리");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_photo_button:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        Log.e(this.toString(), "Error occurred while creating the File");
                    }

                    if (photoFile != null) {
                        photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);

                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
                break;
            case R.id.recommended_list_button:
                Intent recommendedListIntent = new Intent(this, RecommendedListActivity.class);
                startActivity(recommendedListIntent);
                break;
            case R.id.cloth_log_button:
                Intent clothLogIntent = new Intent(this, ClothLogActivity.class);
                startActivity(clothLogIntent);
                break;
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        Log.d(this.toString(), ".\n" + image.getAbsolutePath());
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Intent intent = new Intent(CategoryActivity.this, EvaluationActivity.class);
            intent.putExtra("imagePath", currentPhotoPath);
            intent.putExtra("date", timeStamp);
            startActivity(intent);


/*
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            ExifInterface exif = null;

            try {
                exif = new ExifInterface(currentPhotoPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int exifOrientation;
            int exifDegree;

            if (exif != null) {
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegrees(exifOrientation);
            } else {
                exifDegree = 0;
            }

            ((ImageView)findViewById(R.id.imageView)).setImageBitmap(rotate(bitmap, exifDegree));
 */

        }
    }

}
