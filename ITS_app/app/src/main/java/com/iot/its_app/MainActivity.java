package com.iot.its_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore db;
    Spinner spinner;
    TextView numberTv;
    TextView speedTv;
    ListView listView;
    CustomAdapter customAdapter;
    ArrayList<VehiclePenalty> listPenalties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewByID();
        setupSpinner();
        setupListview();
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword("1751120071@sv.ut.edu.vn", "0989311700")
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d("TAG", "onSuccess: Login success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "onFailure: login faild");
            }
        });

//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    FirebaseFirestore db = FirebaseFirestore.getInstance();
//                    CollectionReference reference = db.collection("employee");
//                    reference.document("duy").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            String s = documentSnapshot.getString("nhat");
//                            Log.d("TAG", "onSuccess: "+s);
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d("TAG", "onFailure: "+e.getMessage());
//                        }
//                    });
//                }
//            });

        db = FirebaseFirestore.getInstance();
        DocumentReference reference = db.collection("vehicle_tracking").document("value");
        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                VehicleTracking vehicleTracking = value.toObject(VehicleTracking.class);
                speedTv.setText((int)(vehicleTracking.averageSpeed)+"");
                numberTv.setText(vehicleTracking.vehiclesCount+"");
                Log.d("TAG", "onEvent: "+vehicleTracking.averageSpeed);
                Log.d("TAG", "onEvent: "+vehicleTracking.vehiclesCount);
            }
        });
        setupListviewItems();
    }

    void findViewByID() {
        spinner = findViewById(R.id.main_spinner);
        numberTv = findViewById(R.id.vehicle_count);
        speedTv = findViewById(R.id.average_speed);
        listView = findViewById(R.id.listview);
    }

    void setupListview(){
        listPenalties = new ArrayList<VehiclePenalty>();
        customAdapter = new CustomAdapter(this, android.R.layout.simple_list_item_1, listPenalties);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TAG", "onItemClick: ");
                displayAlertDialog(listPenalties.get(i).speed+"");
            }
        });
    }

    void setupListviewItems(){
        CollectionReference collectionReference = db.collection("penalty");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                      VehiclePenalty vehiclePenalty = snapshot.toObject(VehiclePenalty.class);
                      listPenalties.add(vehiclePenalty);
                }
                Log.d("TAG", "onSuccessxxx: "+listPenalties.size());
                filter(listPenalties);
                customAdapter.setItems(listPenalties);
                Log.d("TAG", "setupListviewItemsonSuccess: "+listPenalties.size());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "setupListviewItems onFailure: ");
            }
        });
    }

    void setupSpinner() {
        String[] strings = new String[]{
                "duy", "duy2"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(strings);
        spinner.setAdapter(adapter);
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    void filter(ArrayList<VehiclePenalty> list){
        ArrayList<VehiclePenalty> list2 = new ArrayList<VehiclePenalty>();
      for(int i = 0; i < list.size()-1; i++ ){
          int s1 = list.get(i).speed;
          list2.add(list.get(i));
          Log.d("TAG", "filteritem: "+s1);
          for(int j = 1; j < list.size(); j++){
              int s2 = list.get(j).speed;
              if(s1 == s2){
                  list.remove(j);
                  j--;
              }
          }
      }

        Log.d("TAG", "filter:  "+list2.size());
        listPenalties = list2;
    }


    public void displayAlertDialog(String speed) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_dialog, null);
        final ImageView imageView = alertLayout.findViewById(R.id.cus_dia_img);
        Utils.getImage(speed + "", imageView);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);          
        alert.setTitle("Vé phạt");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Thôi", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Phạt", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // code for matching password
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}


