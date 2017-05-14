package io.github.gmkbenjamin.free2fa;

import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.SharedPreferences;

import java.io.File;


public class BackupAgent extends BackupAgentHelper {
    static final String PREFS = "tokens_enc";


    // A key to uniquely identify the set of backup data
    static final String PREFS_BACKUP_KEY = "prefs";

    // Allocate a helper and add it to the backup agent
    @Override
    public void onCreate() {
        SharedPreferences prefs = getSharedPreferences("secret", MODE_PRIVATE);
        String password = prefs.getString("password", "");
        if(!password.isEmpty()){
            Crypto.encrypt(password, new File(getApplicationInfo().dataDir+"/shared_prefs/tokens.xml"),
                    new File(getApplicationInfo().dataDir+"/shared_prefs/tokens_enc.xml"));
            SharedPreferencesBackupHelper helper =
                    new SharedPreferencesBackupHelper(this, PREFS);
            addHelper(PREFS_BACKUP_KEY, helper);
        }
    }

}
