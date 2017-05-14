package io.github.gmkbenjamin.free2fa;

import android.app.backup.BackupAgent;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.content.SharedPreferences;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.IOException;


public class AutoBackup extends BackupAgent{
    String password = "";
    @Override
    public void onCreate(){


        //erase password before backup
        SharedPreferences pref = getSharedPreferences("secret", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("password", "");
        String password = (pref.getString("password", ""));
        editor.commit();
        if(!password.isEmpty()) {
            Crypto.encrypt(password, new File(getApplicationInfo().dataDir + "/shared_prefs/tokens.xml"), new File(getApplicationInfo().dataDir + "/shared_prefs/tokens_enc.xml"));
            new File(getApplicationInfo().dataDir + "/shared_prefs/tokens.xml").delete();
        }
        super.onCreate();
    }

    @Override
    public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data, ParcelFileDescriptor newState) throws IOException {

    }

    @Override
    public void onRestore(BackupDataInput data, int appVersionCode, ParcelFileDescriptor newState) throws IOException {

    }

    @Override
    public void onDestroy() {
        //restore password after backup
        SharedPreferences pref = this.getSharedPreferences("secret", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("password", password);
        editor.commit();
        if(!password.isEmpty() && new File(getApplicationInfo().dataDir + "/shared_prefs/tokens_enc.xml").exists()) {
            Crypto.decrypt(password, new File(getApplicationInfo().dataDir + "/shared_prefs/tokens_enc.xml"), new File(getApplicationInfo().dataDir + "/shared_prefs/tokens.xml"));
            new File(getApplicationInfo().dataDir + "/shared_prefs/tokens.xml").delete();
        }
        super.onDestroy();
    }
}
