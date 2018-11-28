package mahesh.cubex.saloonadmin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewDetails extends AppCompatActivity {


    ImageView imgPreview;


    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;


    TextView email, phone, city, type;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        imgPreview = findViewById(R.id.img_preview);
        Glide.with(this).load(Constants.account.getImageUrl()).into(imgPreview);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait");
        mProgressDialog.setCancelable(false);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference(Constants.account.getCustomerType());

        email = findViewById(R.id.email);
        phone = findViewById(R.id.phoneNumber);
        city = findViewById(R.id.city);
        type = findViewById(R.id.accontType);

        type.setText("Account Type : "+Constants.account.getCustomerType().replace("_"," ").toUpperCase());


        mDatabaseReference.child(Constants.account.getCustomerID()).child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                email.setText("Email : "+dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabaseReference.child(Constants.account.getCustomerID()).child("city").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                city.setText("City : "+dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabaseReference.child(Constants.account.getCustomerID()).child("phoneno").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                phone.setText("Phone Number : "+dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void approve(View view) {
        mProgressDialog.show();
        mDatabaseReference.child(Constants.account.getCustomerID()).child("accepted").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mProgressDialog.dismiss();
                if (task.isSuccessful())
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ViewDetails.this);
                    alert.setMessage("Account approved");
                    alert.setCancelable(false);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            onBackPressed();
                        }
                    });
                    alert.show();

                    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference mref = mFirebaseDatabase.getReference("PaymentRequests");
                    mref.child(Constants.account.getCustomerID()).removeValue();

                }
                else {

                }
            }
        });
    }

    public void reject(View view) {
    }
}
