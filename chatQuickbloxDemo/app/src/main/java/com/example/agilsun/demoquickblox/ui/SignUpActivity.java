package com.example.agilsun.demoquickblox.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agilsun.demoquickblox.R;
import com.example.agilsun.demoquickblox.model.UserModel;
import com.example.agilsun.demoquickblox.helper.Helper;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class SignUpActivity extends AppCompatActivity {

    private EditText mEdtEmail,mEdtPass,mEdtRePass,mEdtName;
    private Button mBtnSignUp;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        setUi();

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInit()){

                    String email = mEdtEmail.getText().toString();
                    String name = mEdtName.getText().toString();
                    String pass = mEdtPass.getText().toString();

                    String id = Helper.getToken(9);

                    UserModel md = new UserModel(id,email,pass,name);
                    mRef.child("user").child(id).setValue(md);
                    Toast.makeText(getApplicationContext(),"Ok",Toast.LENGTH_SHORT).show();
                    final QBUser user = new QBUser(email, pass);
                    user.setExternalId(id);
                    QBUsers.signUp(user).performAsync(new QBEntityCallback<QBUser>() {
                        @Override
                        public void onSuccess(QBUser qbUser, Bundle bundle) {

                            Toast.makeText(getApplicationContext(),"Ok QB",Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onError(QBResponseException e) {
                            Toast.makeText(getApplicationContext(),"Err QB",Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    Toast.makeText(getApplicationContext(),"Lỗi",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void logUser(FirebaseUser user) {
        Log.d("uid", user.getUid());
        Log.d("email", user.getEmail());

        finish();
    }

    private boolean checkInit() {
        if (TextUtils.isEmpty(mEdtEmail.getText().toString())){
            return false;
        }
        if (TextUtils.isEmpty(mEdtName.getText().toString())){
            return false;
        }
        if (TextUtils.isEmpty(mEdtPass.getText().toString())){
            return false;
        }
        if (TextUtils.isEmpty(mEdtRePass.getText().toString())){
            return false;
        }
        if (!mEdtRePass.getText().toString().equals(mEdtPass.getText().toString())){
            return false;
        }
        return true;
    }

    private void setUi() {
        mEdtEmail = findViewById(R.id.edtEmail);
        mEdtName = findViewById(R.id.edtName);
        mEdtPass = findViewById(R.id.edtPass);
        mEdtRePass = findViewById(R.id.edtRePass);
        mBtnSignUp = findViewById(R.id.btnSignUp);
    }
}
