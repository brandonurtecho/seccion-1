package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private ImageButton imageButtonPhone;
    private EditText editTextWeb;
    private ImageButton imageButtonWeb;
    private ImageButton imageButtonCamera;

    private final int PHONE_CALL_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        editTextPhone = findViewById(R.id.editTextPhone);
        imageButtonPhone = findViewById(R.id.imageButtonPhone);
        editTextWeb = findViewById(R.id.editTextWeb);
        imageButtonWeb = findViewById(R.id.imageButtonWeb);
        imageButtonCamera = findViewById(R.id.imageButtonCamera);

        imageButtonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = editTextPhone.getText().toString();
                if(!phoneNumber.isEmpty()){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                    } else {
                        olderVersion(phoneNumber);
                    }
                } else {
                    Toast.makeText(ThirdActivity.this, "No hay un número de telefono", Toast.LENGTH_SHORT).show();
                }
            }

            private void olderVersion(String phoneNumber){
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber));
                if(checkPermission(Manifest.permission.CALL_PHONE)){
                    startActivity(intent);
                } else {
                    Toast.makeText(ThirdActivity.this, "Se declinó el acceso", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PHONE_CALL_CODE:
                String permission = permissions[0];
                int result = grantResults[0];
                if(permission.equals(Manifest.permission.CALL_PHONE)){
                    if(result == PackageManager.PERMISSION_GRANTED){
                        String phoneNumber = editTextPhone.getText().toString();
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber));
                        startActivity(intent);
                    } else {
                        Toast.makeText(ThirdActivity.this, "Se declinó el acceso", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private boolean checkPermission(String permission){
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
