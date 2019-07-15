package com.example.girirajkishor.sharedcontactprovider;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Map;

public class MainActivity extends AppCompatActivity{  // this class will add data to sharedpreferences
    public int cnt=0;                                   // for having different keys for sharedpreference

    public EditText etMain;
    public Button btnShare;
    public Button btnAdd;
    public TextView tvMain;
    String Typed="-----------------------\n";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etMain=findViewById(R.id.etMain);
        btnShare=findViewById(R.id.btnShare);
        btnAdd=findViewById(R.id.btnAdd);
        tvMain=findViewById(R.id.tvMain);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "New Contact Added", Toast.LENGTH_SHORT)
                        .show();
                    addName();
                Map<String, ?> map=  getPreferences(MODE_PRIVATE).getAll();
                for (String key : map.keySet()){
                    Typed=Typed+map.get(key)+"\n";
                }
                Typed=Typed+ ("\n-----------------------");
                     cnt++;
            tvMain.setText(Typed);
            Typed="---------------------------\n";            }
        });
        btnAdd.setOnClickListener(

                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToProvider() // function that will set up the
                // contentprovider and sends sharedpref's data
                ;
            }
        });
    }

    public void addName() {      // storing data in sharedpreference
        SharedPreferences sp1 = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor=sp1.edit();
        String name = etMain.getText().toString();
        editor.putString(("arg" + cnt), name);
        editor.apply();
    }

    public void sendDataToProvider(){

        ContentValues values = new ContentValues();
        Map<String,?> mp=  getPreferences(MODE_PRIVATE).getAll(); // get all sharedpref's data so far
        for (String key : mp.keySet()){
                values.put(ContactProvider.key,key);
                if(mp.get(key) instanceof  Integer)
                    values.put((ContactProvider.value),(String)mp.get(key));
                else if(mp.get(key) instanceof Boolean)
                values.put(ContactProvider.value,(Boolean) mp.get(key));
               else if(mp.get(key) instanceof Double)
                values.put(ContactProvider.value,(Double) mp.get(key));
               else
                values.put(ContactProvider.value,(String)mp.get(key));
            getContentResolver().insert(ContactProvider.CONTENT_URL, values);
        }
    }
}
