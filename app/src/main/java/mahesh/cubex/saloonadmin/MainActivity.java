package mahesh.cubex.saloonadmin;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseReference;

    ArrayList<ApprovalRequestPojo> mReList;
    private NewsListAdapter mAdapter;
    private RecyclerView recyclerView;


    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mReList = new ArrayList<>();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait");
        mProgressDialog.setCancelable(false);


        recyclerView = (RecyclerView) findViewById(R.id.re_list);

        mAdapter = new NewsListAdapter(this, mReList);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);


        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("PaymentRequests");


    }

    @Override
    protected void onResume() {
        super.onResume();
        mProgressDialog.show();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProgressDialog.dismiss();
                mReList.clear();
                if (dataSnapshot != null) {
                    for (DataSnapshot dpst : dataSnapshot.getChildren()) {
                        mReList.add(dpst.getValue(ApprovalRequestPojo.class));
                    }
                    if (mReList.size()>0){
                        mAdapter = new NewsListAdapter(MainActivity.this,mReList);
                        recyclerView.setAdapter(mAdapter);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"No Requests Found",Toast.LENGTH_LONG).show();
                    }



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
