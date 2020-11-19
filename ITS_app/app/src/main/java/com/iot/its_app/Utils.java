package com.iot.its_app;


import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Utils {

    private static final String TAG = "Utils";

    public static void getImage(String speed, final ImageView imageView) {
        final String[] imagename = new String[1];
        StorageReference ref = FirebaseStorage.getInstance().getReference()
                                .child("penalty")
                                .child(speed+".jpg");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imagename[0] = uri.toString();
                Log.d(TAG, "onSuccess: " + imagename[0]);
                DownloadImageAsyn downloadImageAsyn = new DownloadImageAsyn(imageView);
                downloadImageAsyn.execute(imagename[0]);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: ");
            }
        });

    }
}
