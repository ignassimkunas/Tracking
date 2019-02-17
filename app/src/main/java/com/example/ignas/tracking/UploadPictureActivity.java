package com.example.ignas.tracking;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class UploadPictureActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST_CODE = 1;
    public static boolean UploadActive = false;
    private int position;
    private Button upload;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ImageView imageView;
    private ProgressDialog mProgress;

    @Override
    public void onStart() {
        super.onStart();
        UploadActive = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        UploadActive = false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();

            }

        }

    }

    public void uploadImage() {

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);

        } else {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(intent, CAMERA_REQUEST_CODE);


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap;

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

            bitmap = (Bitmap) data.getExtras().get("data");

            mProgress.setMessage("Uploading...");

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            StorageReference ref = storageReference.child(ToPay.monthsList.get(position).year + ToPay.monthsList.get(position).name + firebaseAuth.getUid() + ".jpg");

            mProgress.show();

            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            if (bitmap != null) {

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data1 = baos.toByteArray();

                UploadTask uploadTask = ref.putBytes(data1);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(UploadPictureActivity.this, "Upload complete.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        Months months = ToPay.monthsList.get(position);
                        months.ifPaid = true;
                        databaseReference.child(months.id).setValue(months);

                        mProgress.dismiss();

                        startActivity(intent);

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadPictureActivity.this, "Upload unsuccessful", Toast.LENGTH_SHORT).show();

                        Months months = ToPay.monthsList.get(position);
                        months.ifPaid = false;
                        databaseReference.child(months.id).setValue(months);

                        mProgress.dismiss();
                    }
                });

            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_picture);

        LinearLayout relativeLayout = findViewById(R.id.relative);

        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        final Intent intent = getIntent();

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);

        }

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mProgress = new ProgressDialog(this);
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("month").child(firebaseAuth.getUid());

        position = intent.getIntExtra("position", 0);

        imageView = findViewById(R.id.imageView);
        imageView.setVisibility(View.INVISIBLE);

        upload = findViewById(R.id.upload);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Loading..");
        mProgress.show();

        storageReference.child(ToPay.monthsList.get(position).year + ToPay.monthsList.get(position).name + firebaseAuth.getUid() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageView.setVisibility(View.VISIBLE);

                upload.setText("Uploaded");
                upload.setClickable(false);
                upload.setTextColor(Color.parseColor("#FF9C9C9C"));

                Months months = ToPay.monthsList.get(position);
                months.ifPaid = true;
                databaseReference.child(months.id).setValue(months);

                ToPay.adapter.notifyDataSetChanged();

                Picasso.get().load(uri).transform(new RoundCornersTransformation(10, 5, true, true)).into(imageView);
                mProgress.dismiss();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                Months months = ToPay.monthsList.get(position);
                months.ifPaid = false;
                databaseReference.child(months.id).setValue(months);

                ToPay.adapter.notifyDataSetChanged();
                mProgress.dismiss();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage();

            }
        });
    }
}
