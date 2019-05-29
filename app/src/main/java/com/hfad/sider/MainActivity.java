package com.hfad.sider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.view.MenuItemCompat;
import android.support.design.widget.NavigationView;
import android.widget.ImageView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.amitshekhar.utils.DatabaseHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {



    Bitmap[] decodedByte;
    String[] arrayBase; // массив для строки base64
    byte[] arrayByte; // массив для полученной картинки в base64 string->byte
    String[] arrayName;
    String[] arrayType;
    String[] arrayAlc;
    String[] arrayDes;
    String[] arrayCmp;
    String[] arrayCom;
    Double[] arrayPrc;
    Double[] arrayScr;
    Double[] arrayVol;

    int dry,sweet,sdry,ssweet,other;

    int index[];
    int i = 0;
    Sider sider;


    //Переменные для работы с БД
    private SiderDatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        // Заменяем на панель инструментов
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Связывание SectionsPagerAdapter с ViewPager
        SectionsPagerAdapter pagerAdapter =
                new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        //Связывание ViewPager с TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        // Создаем бургер
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer);
        // Добавляем бургер на панель
        drawer.addDrawerListener(toggle);
        // Анимация нажатия на бургер
        toggle.syncState();

        // Подготовка к работе с БД
        mDBHelper = new SiderDatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }



        // Cчитывание с базы
        Cursor cursor = mDb.rawQuery("SELECT * FROM Sider", null);

        // Массивы для получаемых данных из БД
        arrayName = new String[cursor.getCount()];
        arrayType = new String[cursor.getCount()];
        arrayAlc = new String[cursor.getCount()];
        arrayDes = new String[cursor.getCount()];
        arrayCmp = new String[cursor.getCount()];
        arrayCom = new String[cursor.getCount()];
        arrayPrc = new Double[cursor.getCount()];
        arrayScr = new Double[cursor.getCount()];
        arrayVol = new Double[cursor.getCount()];
        arrayBase = new String[cursor.getCount()];
        decodedByte = new Bitmap[cursor.getCount()];
        index = new int[cursor.getCount()];

        // Считывание данных из БД
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            arrayName[i] = cursor.getString(1);
            arrayType[i] = cursor.getString(2);
                if (arrayType[i].equals("Сухой")) dry++;
                if (arrayType[i].equals("Полусухой")) sdry++;
                if (arrayType[i].equals("Полусладкий")) ssweet++;
                if (arrayType[i].equals("Сладкий")) sweet++;
                if (arrayType[i].equals("Остальные")) other++;
            arrayAlc[i] = cursor.getString(3);
            arrayDes[i] = cursor.getString(4);
            arrayCmp[i] = cursor.getString(5);
            arrayCom[i] = cursor.getString(6);
            arrayPrc[i] = cursor.getDouble(7);
            arrayScr[i] = cursor.getDouble(8);
            arrayVol[i] = cursor.getDouble(9);
            arrayBase[i] = cursor.getString(10);

            arrayByte = Base64.decode(arrayBase[i], Base64.DEFAULT);
            decodedByte[i] = BitmapFactory.decodeByteArray(arrayByte, 0, arrayByte.length);

            // Сохраняем картинку на SD
            new ImageSaver(MainActivity.this).
                    setFileName("Sider"+ i + ".png").
                    setDirectoryName("images").
                    save(decodedByte[i]);

            index[i] = i;

            sider = new Sider(arrayName, arrayType, arrayAlc, arrayDes, arrayCmp, arrayCom, arrayPrc, arrayScr, arrayVol,decodedByte, index);
            Log.v("TEST","IMAGE " + arrayByte[i]);
            i++;
            cursor.moveToNext();
        }
        cursor.close();
        mDb.close();

    }





    Sider getSider() {
        return  sider;
    }

    int getMaxInt() { return i; }
    int getDry() { return dry; }
    int getSweet() { return sweet; }
    int getSdry() { return sdry; }
    int getSsweet() { return ssweet; }
    int getOther() { return other; }


    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Количество вкладок в viewpager
        @Override
        public int getCount() {
            return 5;
        }

        // Какой фрагмент должен выводиться на каждой странице
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new DryFragment();
                case 1:
                    return new SemiDryFragment();
                case 2:
                    return new SemiSweetFragment();
                case 3:
                    return new SweetFragment();
                case 4:
                    return new OtherFragment();
            }
            return null;
        }

        // Заголовки вкладок
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.dry);
                case 1:
                    return getResources().getText(R.string.semiDry);
                case 2:
                    return getResources().getText(R.string.semiSweet);
                case 3:
                    return getResources().getText(R.string.sweet);
                case 4:
                    return getResources().getText(R.string.other);
            }
            return null;
        }
    }


}
