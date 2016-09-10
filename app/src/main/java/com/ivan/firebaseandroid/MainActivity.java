package com.ivan.firebaseandroid;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private TextView nameTextView;
    //private TextView emailTextView;
    private TextView infoTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameTextView = (TextView)findViewById(R.id.nameTextView);
        //emailTextView = (TextView)findViewById(R.id.emailTextView);
        infoTextView = (TextView) findViewById(R.id.infoTextView);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String name = user.getDisplayName();
            String email = user.getEmail();

            nameTextView.setText(name);
            //emailTextView.setText(email);
            if(getIntent().getExtras() != null){
                for (String key: getIntent().getExtras().keySet()){
                    String value =  getIntent().getExtras().getString(key);
                    infoTextView.append("\n"+ key+":" + value);
                }
            }
        }else{
            ScreenLoginFacebook();
        }
    }

    private void ScreenLoginFacebook() {
        Intent intent = new Intent(this, LoginFacebookActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //metodo para cerrar sesion en facebook y firebase, asi mismo nos redirige al activity para iniciar sesión
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut(); //cerramos sesion en Firebase
        LoginManager.getInstance().logOut(); //cerramos session en Facebook
        ScreenLoginFacebook();

    }
}