package com.example.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button b1, b2, b3, a1, a2, a3, next1, b7, b8, b9, b10;
    TextView tv, atv1, atv2, atv3, tv4, tv5;
    EditText ET;

    Animation rotate;
    Animation scale;
    Animation translate;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        geo();
    }

    @SuppressLint("MissingPermission")
    private void geo() {
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);

        //Получаем сервис
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener mlocListener = new LocationListener(){
            public void onLocationChanged(Location location) {
                //Called when a new location is found by the network location provider.
                tv4.setText(String.valueOf(location.getLatitude()));
                tv5.setText(String.valueOf(location.getLongitude()));
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };
        //Подписываемся на изменения в показаниях датчика
       mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
        //Если gps включен, то ... , иначе вывести "GPS is not turned on..."
        if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            System.out.println("GPS is turned on...");
        } else {
            System.out.println("GPS is not turned on...");
        }
    }

    private void initViews() {
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        next1 = (Button) findViewById(R.id.next1);
        b7 = (Button) findViewById(R.id.b7);
        b8 = (Button) findViewById(R.id.b8);
        b9 = (Button) findViewById(R.id.b9);
        b10 = (Button) findViewById(R.id.b10);

        a1 = (Button) findViewById(R.id.a1);
        a2 = (Button) findViewById(R.id.a2);
        a3 = (Button) findViewById(R.id.a3);

        tv = (TextView) findViewById(R.id.tv);
        atv1 = (TextView) findViewById(R.id.atv1);
        atv2 = (TextView) findViewById(R.id.atv2);
        atv3 = (TextView) findViewById(R.id.atv3);

        ET = (EditText) findViewById(R.id.ET);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        a1.setOnClickListener(this);
        a2.setOnClickListener(this);
        a3.setOnClickListener(this);
        next1.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        b10.setOnClickListener(this);

        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        scale = AnimationUtils.loadAnimation(this, R.anim.scale);
        translate = AnimationUtils.loadAnimation(this, R.anim.translate);


        // DB
        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (v.getId()) {
            case R.id.b1:
                tv.setText("Hello my dear friend!");
                break;
            case R.id.b2:
                tv.setText("Bye, my dear friend!");
                break;
            case R.id.b3:
                startActivity(new Intent(this, Main2Activity.class));
                break;
            case R.id.a1:
                atv1.startAnimation(rotate);
                break;
            case R.id.a2:
                atv2.startAnimation(scale);
                break;
            case R.id.a3:
                atv3.startAnimation(translate);
                break;
            case R.id.next1:
                startActivity(new Intent(this, Main3Activity.class));
                break;
            case R.id.b7:
                SharedPreferences sp = getSharedPreferences("SP", Context.MODE_PRIVATE);
                Editor edit = sp.edit();
                int i = sp.getInt("cnt", 0);
                i += 1;
                edit.putInt("cnt", i);
                edit.commit();
                Toast t = Toast.makeText(this, Integer.toString(i), Toast.LENGTH_SHORT);
                t.show();
                break;
            case R.id.b8:
                Cursor c = db.query("mytable", null, null, null, null, null, null);
                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                String s = "";
                if (c.moveToFirst()) {
                    int nameColIndex = c.getColumnIndex("name");
                    do {
                    // переход на следующую строку
                    // а если следующей нет (текущая - последняя), то false - выходим из цикла
                        s += c.getString(nameColIndex) + "\n";
                    } while (c.moveToNext());
                }
                c.close();
                Toast t1 = Toast.makeText(this, s, Toast.LENGTH_SHORT);
                t1.show();
                break;
            case R.id.b9:
                cv.put("name", ET.getText().toString());
                db.insert("mytable", null, cv);
                break;
            case R.id.b10:
                db.delete("mytable", null, null);
                break;
            default:
                break;
        }
    }

    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // создаем таблицу с полями
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "name text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
