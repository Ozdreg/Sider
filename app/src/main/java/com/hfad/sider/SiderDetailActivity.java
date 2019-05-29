package com.hfad.sider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SiderDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAMES = "Name";
    public static final String EXTRA_TYPE = "Type";
    public static final String EXTRA_ALC = "Alc";
    public static final String EXTRA_DES = "Des";
    public static final String EXTRA_CMP = "Cmp";
    public static final String EXTRA_COM = "Com";
    public static final String EXTRA_PRC = "Prc";
    public static final String EXTRA_SCR = "Scr";
    public static final String EXTRA_VOL = "Vol";
    public static final String  EXTRA_INT = "Int";
    public static final String  EXTRA_INDEX = "Index";


    String name,type,alc,des,cmp,com;
    Double prc,scr,vol;
    int i, index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sider_detail);

        // Подключаем toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Получаем данные из DryFragment
        Intent intent = getIntent();
        name = intent.getStringExtra(EXTRA_NAMES);
        type = intent.getStringExtra(EXTRA_TYPE);
        alc = intent.getStringExtra(EXTRA_ALC);
        des = intent.getStringExtra(EXTRA_DES);
        cmp = intent.getStringExtra(EXTRA_CMP);
        com = intent.getStringExtra(EXTRA_COM);
        prc = intent.getExtras().getDouble(EXTRA_PRC); // getDoubleExtra работает не аналогично этому...
        scr = intent.getExtras().getDouble(EXTRA_SCR);
        vol = intent.getExtras().getDouble(EXTRA_VOL);
        Log.v("TEST", "IMG " + i + " " + index);
        i = intent.getExtras().getInt(EXTRA_INT);
        index = intent.getExtras().getInt(EXTRA_INDEX);

        Log.v("TEST", "IMG " + i + " " + index);
        // Восстанавливаем сохраненную картинку
            Bitmap bitmap = new ImageSaver(this).
                setFileName("Sider" + index + ".png").
                setDirectoryName("images").
                load();

        // Текст с названием
        String nameSider = name;
        TextView textView = findViewById(R.id.sider_text);
        textView.setText(nameSider);

        // Картинка
        ImageView imageView = findViewById(R.id.sider_image);
        imageView.setImageBitmap(bitmap);

        // Создаем список
        ListView listView = findViewById(R.id.list);

        // Включаем данные в список
        final String[] infoSider = {"Тип:              " + type, "Описание:   " + des, "Алкоголь:    " + alc,  "Компания:   " + cmp, "Цена:       " + prc,
         "Оценка:   " + scr, "Объем:    " + vol, "Комментарий: " + com};

        // Загружаем их в адаптер списка
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, infoSider);

        //Подключаем адаптер к списку
        listView.setAdapter(adapter);
    }
}
