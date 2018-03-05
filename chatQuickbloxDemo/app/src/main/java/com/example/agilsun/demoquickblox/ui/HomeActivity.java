package com.example.agilsun.demoquickblox.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.agilsun.demoquickblox.R;
import com.example.agilsun.demoquickblox.adapter.ChatDialogAdapter;
import com.example.agilsun.demoquickblox.helper.OnItemClickListener;
import com.example.agilsun.demoquickblox.model.ChatDialogModel;
import com.example.agilsun.demoquickblox.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBPagedRequestBuilder;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnItemClickListener {

    private RecyclerView mRecy;
    private Button mBtnLogout,mBtnAdd;

    private ChatDialogAdapter adapter;
    private List<ChatDialogModel> mList = new ArrayList<>();
    private List<QBChatDialog> mDialog = new ArrayList<>();

    private int mUserid =0;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setUi();
        setRecycler();

        getUserId();

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(HomeActivity.this,SelectUserActivity.class);
                startActivity(add);
            }
        });
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QBUsers.signOut().performAsync(new QBEntityCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid, Bundle bundle) {
                        Toast.makeText(getApplicationContext(),"Logout Success!",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(QBResponseException e) {

                    }
                });
            }
        });
    }

    private void getUserId() {
        QBAuth.getSession().performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {
                Log.d("d", "onSuccess: ");
                mUserid = qbSession.getUserId();
            }

            @Override
            public void onError(QBResponseException e) {

            }
        });
    }

    private void loadData() {
        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        requestBuilder.setLimit(100);

        QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(
                new QBEntityCallback<ArrayList<QBChatDialog>>() {
                    @Override
                    public void onSuccess(ArrayList<QBChatDialog> result, Bundle params) {
                        int totalEntries = params.getInt("total_entries");
                        if (totalEntries>0){
                            mList.clear();
                            mDialog.clear();
                            for (final QBChatDialog q : result){
                                final List<Integer> list = new ArrayList<>();
                                for (Integer i : q.getOccupants()){
                                    if (i!=mUserid){
                                        list.add(i);
                                    }
                                }

                               getIdUserFr(q,list);

                            }
                            mDialog.addAll(result);
                            getUser();

                        }
                    }

                    @Override
                    public void onError(QBResponseException responseException) {

                    }
                });
    }

    private void getIdUserFr(final QBChatDialog q, final List<Integer> list) {
        final List<UserModel> listUserExternal = new ArrayList<>();

        for (Integer i : list){
            QBUsers.getUser(i).performAsync(new QBEntityCallback<QBUser>() {
                @Override
                public void onSuccess(QBUser qbUser, Bundle bundle) {
                    Log.d("TAG", "onSuccess: ");

                        mRef.child("user").child(qbUser.getExternalId()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                UserModel model = dataSnapshot.getValue(UserModel.class);
                                listUserExternal.add(model);

                                int count  = q.getUnreadMessageCount();
                                Log.d(count+"", "onSuccess: ");
                                ChatDialogModel chat = new ChatDialogModel(q.getDialogId(),listUserExternal.get(0).getName(),q.getPhoto(),list,count);
                                mList.add(chat);
                                adapter.setData(mList);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                }

                @Override
                public void onError(QBResponseException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void setRecycler() {
        adapter = new ChatDialogAdapter();
        mRecy.setAdapter(adapter);
        adapter.setListener(this);
        RecyclerView.LayoutManager fLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecy.setLayoutManager(fLayoutManager);
    }

    private void setUi() {
        mRecy = findViewById(R.id.recy);
        mBtnAdd = findViewById(R.id.btnAdd);
        mBtnLogout = findViewById(R.id.btnLogout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    private void getUser() {
        if (mList.size()>0){
            for (ChatDialogModel model : mList){
                QBPagedRequestBuilder pagedRequestBuilder = new QBPagedRequestBuilder();
                pagedRequestBuilder.setPage(1);
                pagedRequestBuilder.setPerPage(50);

                ArrayList<Integer> usersIds = new ArrayList<Integer>();
                usersIds.addAll(model.getOccupantsid());

                QBUsers.getUsersByIDs(usersIds, pagedRequestBuilder).performAsync(new QBEntityCallback<ArrayList<QBUser>>() {
                    @Override
                    public void onSuccess(ArrayList<QBUser> users, Bundle params) {
                        Log.d("TAG", "onSuccess: ");
                    }

                    @Override
                    public void onError(QBResponseException errors) {

                    }
                });
            }
        }
    }

    @Override
    public void onClick(int pos) {
        Intent chat = new Intent(HomeActivity.this,ChatActivity.class);
        chat.putExtra("Dialog",mDialog.get(pos));
        startActivity(chat);
    }
}
