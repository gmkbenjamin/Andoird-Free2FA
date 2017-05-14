package io.github.gmkbenjamin.free2fa;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.github.gmkbenjamin.free2fa.R;

import io.github.gmkbenjamin.free2fa.edit.BaseActivity;



public class SetPasswordActivity extends BaseActivity implements TextWatcher, View.OnClickListener{
    private EditText password;
    private EditText confirm_password;
    private Button cancel;
    private Button ok;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private String enc_password;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password);
        prefs = getSharedPreferences("secret", MODE_PRIVATE);
        editor = prefs.edit();
        enc_password = prefs.getString("password","");
        password = (EditText) findViewById(R.id.enc_password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        cancel = (Button) findViewById(R.id.password_cancel);
        ok = (Button) findViewById(R.id.password_save);
        if(!enc_password.isEmpty()){
            password.setText(enc_password);
            confirm_password.setText(enc_password);
        }
        password.addTextChangedListener(this);
        confirm_password.addTextChangedListener(this);
        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);
        if(!password.getText().toString().isEmpty())
            ok.setEnabled(true);
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(password.getText().toString().isEmpty() || !password.getText().toString()
                .equals(confirm_password.getText().toString()))
            ok.setEnabled(false);
        else
            ok.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.password_save:
                editor.putString("password", password.getText().toString());
                editor.commit();
                Toast.makeText(this, "Encryption password set", Toast.LENGTH_SHORT).show();

            case R.id.password_cancel:
                finish();
                break;
        }
    }
}
