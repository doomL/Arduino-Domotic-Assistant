package com.doomco.domoticassistant;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    public SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        findViewById(R.id.button2).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SharedPreferences.Editor editor= sharedPreferences.edit();
                final EditText editText2 = findViewById(R.id.editText2);
                final EditText editText3 = findViewById(R.id.editText3);
                final EditText editText4 = findViewById(R.id.editText4);
                editor.putString("nome",editText2.getText().toString());
                editor.putString("tempMin",editText3.getText().toString());
                editor.putString("tempMax",editText4.getText().toString());
                editor.commit();

                Intent mStartActivity = new Intent(getApplicationContext(), MainActivity.class);
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(),mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1, mPendingIntent);
                System.exit(0);
                return false;
            }
        });
    }
}