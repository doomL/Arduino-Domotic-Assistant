package com.doomco.domoticassistant;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.support.constraint.ConstraintLayout;
import android.graphics.drawable.AnimationDrawable;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {


    TextToSpeech toSpeech;
    String testo = new String();

    static final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final String DEVICE_ADDRESS = "98:D3:32:30:B9:80"; //MAC Address of Bluetooth Module

    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getString("nome", "DEF") == "DEF")
            startActivity(new Intent(MainActivity.this, Main2Activity.class));

        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        checkPermission();

        final EditText editText = findViewById(R.id.editText);
        final TextView t1 = findViewById(R.id.textView3);
        t1.setText("Ciao" + " " + prefs.getString("nome", "DEF") + "!");
        if (BTinit()) {
            BTconnect();
        }
          outS("benvenuto"  );
//        outS("setMin" + '\r');
//        outS(prefs.getString("tempMin", "20"));
//        outS("setMax");
//        outS(prefs.getString("tempMax", "28"));


        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        toSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS)
                    toSpeech.setLanguage(Locale.ITALIAN);

            }
        });

        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null) {
                    editText.setText(matches.get(0).toLowerCase());
                    testo = matches.get(0);
                    try {
                        esegui(editText);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        findViewById(R.id.button).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();

                        editText.setHint("You will see input here");

                        break;

                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        editText.setText("");
                        editText.setHint("Listening...");
                        break;
                }
                return false;
            }
        });

        findViewById(R.id.imageButton).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));

                return true;
            }
        });
    }

    //    public void matcher(EditText e){
//        String regex = "";
//        String input = e.getText().toString();
//
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(input);
//
//        while (matcher.find())
//            System.out.println(matcher.group());
//    }
    private void esegui(EditText e) throws IOException {
        if (e.getText().toString().equals("sei un grande"))
            toSpeech.speak("Grazie, Grazie, eh,eh,eh,", TextToSpeech.QUEUE_FLUSH, null, "risp");
        else if (e.getText().toString().equals("ciao"))
            toSpeech.speak("Ciao anche a te", TextToSpeech.QUEUE_ADD, null, "risp");
        else if (e.getText().toString().equals("come ti chiami"))
            toSpeech.speak("Mi chiamo Lara", TextToSpeech.QUEUE_FLUSH, null, "risp");
        else if (e.getText().toString().equals("accendi la luce in cucina"))
            outS("cucinaOn");
        else if (e.getText().toString().equals("spegni la luce in cucina"))
            outS("cucinaOff");
        else if (e.getText().toString().equals("accendi la luce in salotto") || e.getText().toString().equals("accendi la luce in soggiorno"))
            outS("salottoOn");
        else if (e.getText().toString().equals("spegni la luce in salotto") || e.getText().toString().equals("spegni la luce in soggiorno"))
            outS("salottoOff");
        else if (e.getText().toString().equals("accendi la luce in Bagno"))
            outS("bagnoOn");
        else if (e.getText().toString().equals("spegni la luce in Bagno"))
            outS("bagnoOff");
        else if (e.getText().toString().equals("accendi la luce all'ingresso"))
            outS("ingressoOn");
        else if (e.getText().toString().equals("spegni la luce all'ingresso"))
            outS("ingressoOff");
        else if (e.getText().toString().equals("accendi la luce nel ripostiglio"))
            outS("ripostiglioOn");
        else if (e.getText().toString().equals("spegni la luce nel ripostiglio"))
            outS("ripostiglioOff");
        else if (e.getText().toString().equals("accendi la luce in camera") || e.getText().toString().equals("Accendi la luce in camera da letto"))
            outS("cameraOn");
        else if (e.getText().toString().equals("spegni la luce in camera") || e.getText().toString().equals("Spegni la luce in camera da letto"))
            outS("cameraOff");
        else if (e.getText().toString().equals("accendi la stufa ") || e.getText().toString().equals("fa freddo"))
            outS("stufaOn");
        else if (e.getText().toString().equals("spegni la stufa") || e.getText().toString().equals("fa caldo"))
            outS("stufaOff");
        else if (e.getText().toString().equals("accendi il ventilatore") || e.getText().toString().equals("fa caldo"))
            outS("ventilatoreOn");
        else if (e.getText().toString().equals("spegni il ventilatore") || e.getText().toString().equals("fa freddo"))
            outS("ventilatoreOff");

        else if (e.getText().toString().equals("quanti gradi ci sono")) {
            outS("temp");
            inS();
        } else
            toSpeech.speak("Non ho capito, potresti ripetere?", TextToSpeech.QUEUE_FLUSH, null, "risp");


    }

    // Create the Handler object (on the main thread by default)
    Handler handler = new Handler();
    // Define the code block to be executed
    private Runnable rilevazioneGas = new Runnable() {
        @Override
        public void run() {

            try {

                // int byteCount = inputStream.available();
                if (inputStream.available() > 0) {
                    byte[] rawBytes = new byte[inputStream.available()];
                    inputStream.read(rawBytes);
                    final String string = new String(rawBytes, "UTF-8");
                    toSpeech.speak(string, TextToSpeech.QUEUE_FLUSH, null, "risp");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }


            handler.postDelayed(this, 1000);
        }
    };
// Start the initial runnable task by posting through the handler

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (toSpeech != null) {
            toSpeech.stop();
            toSpeech.shutdown();
        }
    }

    private void inS() throws IOException {
        int byteCount = inputStream.available();
        if (byteCount > 0) {
            byte[] rawBytes = new byte[byteCount];
            inputStream.read(rawBytes);
            final String string = new String(rawBytes, "UTF-8");
            toSpeech.speak(string, TextToSpeech.QUEUE_FLUSH, null, "risp");
        }
    }

    private void outS(String i) {
        try {

            outputStream.write(i.toString().getBytes());
            handler.post(rilevazioneGas);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }

    public boolean BTinit() {
        boolean found = false;

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) //Checks if the device supports bluetooth
        {
            Toast.makeText(getApplicationContext(), "Device doesn't support bluetooth", Toast.LENGTH_SHORT).show();
        }

        if (!bluetoothAdapter.isEnabled()) //Checks if bluetooth is enabled. If not, the program will ask permission from the user to enable it
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        if (bondedDevices.isEmpty()) //Checks for paired bluetooth devices
        {
            Toast.makeText(getApplicationContext(), "Please pair the device first", Toast.LENGTH_SHORT).show();
        } else {
            for (BluetoothDevice iterator : bondedDevices) {
                if (iterator.getAddress().equals(DEVICE_ADDRESS)) {
                    device = iterator;
                    found = true;
                    break;
                }
            }
        }

        return found;
    }

    public boolean BTconnect() {
        boolean connected = true;

        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID); //Creates a socket to handle the outgoing connection
            socket.connect();

            Toast.makeText(getApplicationContext(),
                    "Connection to bluetooth device successful", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            connected = false;
        }

        if (connected) {
            try {
                outputStream = socket.getOutputStream(); //gets the output stream of the socket
                inputStream = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return connected;
    }
}
