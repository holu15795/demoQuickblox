package com.example.agilsun.demoquickblox.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agilsun.demoquickblox.R;
import com.example.agilsun.demoquickblox.model.UserModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class MainActivity extends AppCompatActivity {
    private TextView mTxtSignUp;
    private Button mBtnLogin;
    private EditText mEdtEmail, mEdtPass;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setUi();

        mTxtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(signup);
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInput()){
                    final String email = mEdtEmail.getText().toString();
                    final String pass = mEdtPass.getText().toString();

                    mRef.child("user").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String da= dataSnapshot.getKey();
                            mRef.child("user").child(da).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    UserModel fModel = dataSnapshot.getValue(UserModel.class);
                                    if (fModel!=null){
                                        if (fModel.getEmail().equals(email) && fModel.getPass().equals(pass)){
                                            Toast.makeText(getApplicationContext(),"Login Ok",Toast.LENGTH_SHORT).show();

                                            final QBUser user = new QBUser(email, pass);
                                            user.setExternalId(fModel.getId());
                                            QBUsers.signIn(user).performAsync(new QBEntityCallback<QBUser>() {
                                                @Override
                                                public void onSuccess(QBUser qbUser, Bundle bundle) {
                                                    Toast.makeText(getApplicationContext(),"Ok QB",Toast.LENGTH_SHORT).show();
                                                    Intent home = new Intent(MainActivity.this,HomeActivity.class);
                                                    startActivity(home);
                                                }

                                                @Override
                                                public void onError(QBResponseException e) {

                                                }
                                            });

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(mEdtEmail.getText().toString())){
            return false;
        }
        if (TextUtils.isEmpty(mEdtPass.getText().toString())){
            return false;
        }
        return true;
    }

    private void setUi() {
        mTxtSignUp = findViewById(R.id.txtSignUp);
        mBtnLogin = findViewById(R.id.btnLogin);
        mEdtEmail = findViewById(R.id.edtEmail);
        mEdtPass = findViewById(R.id.edtPass);
    }


}
