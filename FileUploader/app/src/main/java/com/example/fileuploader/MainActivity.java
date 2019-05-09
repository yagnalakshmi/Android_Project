package com.example.fileuploader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {

    StorageReference myStorage;
    Button fetch, upload;
    ImageView image;
    private static final int GALLERY = 3;
    Uri uri;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myStorage = FirebaseStorage.getInstance().getReference();
        fetch = findViewById(R.id.fetch);
        upload = findViewById(R.id.upload);
        image = findViewById(R.id.image);
        progressDialog = new ProgressDialog(this);


        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetchFile();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage();
            }
        });
    }

    private void fetchFile(){

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        startActivityForResult(intent, GALLERY);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GALLERY) {

            uri = data.getData();
            image.setImageURI(uri);
        }


    }


    private void uploadImage(){


        progressDialog.setMessage("UPLOADING....");
        progressDialog.show();

        StorageReference imageRef = myStorage.child("Images/" + uri.getLastPathSegment() + ".jpg");
        imageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(MainActivity.this, "Image successfully uploaded", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(MainActivity.this, "Somehting went wrong", Toast.LENGTH_SHORT).show();

            }
        });


    }

}





