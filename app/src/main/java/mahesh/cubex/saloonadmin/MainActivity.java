package mahesh.cubex.saloonadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mReList = new ArrayList<>();


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

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mReList.clear();
                if (dataSnapshot != null) {
                    for (DataSnapshot dpst : dataSnapshot.getChildren()) {
                        mReList.add(dpst.getValue(ApprovalRequestPojo.class));
                    }

                    mAdapter = new NewsListAdapter(MainActivity.this,mReList);
                    recyclerView.setAdapter(mAdapter);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
