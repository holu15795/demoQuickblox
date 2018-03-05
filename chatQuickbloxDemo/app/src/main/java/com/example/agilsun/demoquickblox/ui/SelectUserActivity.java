package com.example.agilsun.demoquickblox.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.agilsun.demoquickblox.R;
import com.example.agilsun.demoquickblox.adapter.UserAdapter;
import com.example.agilsun.demoquickblox.helper.OnItemClickListener;
import com.example.agilsun.demoquickblox.model.UserModel;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.chat.utils.DialogUtils;
import com.quickblox.core.Consts;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBPagedRequestBuilder;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.List;

public class SelectUserActivity extends AppCompatActivity implements OnItemClickListener {

    private RecyclerView mRecy;

    private UserAdapter adapter;
    private int mUserId = 0;

    private List<UserModel> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        setUi();
        setRecy();

        QBAuth.getSession().performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {
                Log.d("d", "onSuccess: ");
                mUserId = qbSession.getUserId();
            }

            @Override
            public void onError(QBResponseException e) {

            }
        });

        QBPagedRequestBuilder pagedRequestBuilder = new QBPagedRequestBuilder();
        pagedRequestBuilder.setPage(1);
        pagedRequestBuilder.setPerPage(50);

        QBUsers.getUsers(pagedRequestBuilder).performAsync(new QBEntityCallback<ArrayList<QBUser>>() {
            @Override
            public void onSuccess(ArrayList<QBUser> users, Bundle params) {

                Log.i("ff", "totalPages: " + params.getInt(Consts.TOTAL_ENTRIES));
                List<UserModel> model = new ArrayList<>();
                for (QBUser qb : users){
                    if (qb.getId() != mUserId){
                        UserModel m = new UserModel(String.valueOf(qb.getId()),qb.getFullName());
                        model.add(m);
                    }
                }
                if (model.size()>0){
                    mList.addAll(model);
                    adapter.setData(model);
                }
            }

            @Override
            public void onError(QBResponseException errors) {

            }
        });


    }

    private void setRecy() {
        adapter = new UserAdapter();
        mRecy.setAdapter(adapter);
        adapter.setListener(this);
        RecyclerView.LayoutManager fLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecy.setLayoutManager(fLayoutManager);
    }

    private void setUi() {
        mRecy = findViewById(R.id.mRecySelect);
    }

    @Override
    public void onClick(int pos) {
        ArrayList<Integer> occupantIdsList = new ArrayList<Integer>();
        occupantIdsList.add(Integer.valueOf(mList.get(pos).getId()));

        QBChatDialog dialog = new QBChatDialog();
        dialog.setType(QBDialogType.PRIVATE);
        dialog.setOccupantsIds(occupantIdsList);


        QBChatDialog dia = DialogUtils.buildDialog(null, QBDialogType.PRIVATE, occupantIdsList);

        QBRestChatService.createChatDialog(dia).performAsync(new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog result, Bundle params) {
                Log.d("TAG", "onSuccess: ");
                finish();
            }

            @Override
            public void onError(QBResponseException responseException) {

            }
        });
    }
}
