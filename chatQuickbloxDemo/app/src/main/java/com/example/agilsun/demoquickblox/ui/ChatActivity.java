package com.example.agilsun.demoquickblox.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.agilsun.demoquickblox.R;
import com.example.agilsun.demoquickblox.adapter.MessageAdapter;
import com.example.agilsun.demoquickblox.model.MessageModel;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.request.QBMessageGetBuilder;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private EditText mEdtChatInput;
    private Button mBtnSend;
    private RecyclerView mRecy;

    private QBChatDialog qbDialog;
    private int idUserRecipinet ;
    private int idUser;

    private MessageAdapter adapter;
    private List<MessageModel> mList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_activity);

        setUi();
        qbDialog = (QBChatDialog) getIntent().getSerializableExtra("Dialog");
        Log.d(qbDialog.getDialogId(), "onCreate: ");

        initRecy();
        getListMessageHis();

        getUserId();

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mEdtChatInput.getText())){
                    QBChatMessage chatMessage = new QBChatMessage();
                    chatMessage.setSaveToHistory(true);

                    QBChatMessage msg = new QBChatMessage();
                    msg.setBody(mEdtChatInput.getText().toString());

                    msg.setDialogId(qbDialog.getDialogId());
                    for (Integer i : qbDialog.getOccupants()){
                        if (i!=idUser){
                            msg.setRecipientId(i);
                        }
                    }

                    QBRestChatService.createMessage(msg, true).performAsync(new QBEntityCallback<QBChatMessage>() {
                        @Override
                        public void onSuccess(QBChatMessage message, Bundle bundle) {
                            Log.d("TAG", "onSuccess: ");
                        }

                        @Override
                        public void onError(QBResponseException e) {
                            Log.d("TAG", "onError: ");
                        }
                    });
                }
            }
        });



    }

    private void getUserId() {
        QBAuth.getSession().performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {
                idUser = qbSession.getUserId();
            }

            @Override
            public void onError(QBResponseException e) {

            }
        });
    }

    private void initRecy() {
        adapter = new MessageAdapter();
        mRecy.setAdapter(adapter);
        RecyclerView.LayoutManager fLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecy.setLayoutManager(fLayoutManager);
        if (idUser==0){
            getUserId();
        }
        else {
            adapter.setUserId(idUser);
        }
    }

    private void setUi() {
        mEdtChatInput = findViewById(R.id.edtContentChat);
        mBtnSend = findViewById(R.id.btnSendChat);
        mRecy = findViewById(R.id.mRecyclerMessage);
    }

    private void getListMessageHis() {
        QBChatDialog chatDialog = new QBChatDialog(qbDialog.getDialogId());

        QBMessageGetBuilder messageGetBuilder = new QBMessageGetBuilder();
        messageGetBuilder.setLimit(500);

        QBRestChatService.getDialogMessages(chatDialog, messageGetBuilder).performAsync(new QBEntityCallback<ArrayList<QBChatMessage>>() {
            @Override
            public void onSuccess(ArrayList<QBChatMessage> qbChatMessages, Bundle bundle) {
                Log.d("TAG", "onSuccess: ");
                for (QBChatMessage ms : qbChatMessages){
                    MessageModel model = new MessageModel(ms.getId(),ms.getBody(),ms.getSenderId(),ms.getRecipientId());
                    mList.add(model);
                }
                adapter.setData(mList);

            }

            @Override
            public void onError(QBResponseException e) {

            }
        });
    }
}
