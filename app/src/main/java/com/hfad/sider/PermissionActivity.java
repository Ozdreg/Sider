package com.hfad.sider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class PermissionActivity extends AppCompatActivity {

    // объявим константу, содержащую код, передаваемый в запрос на получение разрешения, для последующего отслеживания ответа пользователя на запрос
    private final int MY_PERMISSIONS_REQUEST_STORAGE = 100;
    boolean permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Проверка наличия разрешения на использование файлов
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            //  если разрешение не предоставлено
            // нужно ли показывать объяснение для чего приложению нужно требуемое разрешение?
            // shouldShowRequestPermissionRationale() возвращает true, если пользователь ранее уже отклонял запрос на предоставление разрешения
            // возвращает false если запрос разрешения происходит впервые или если пользователь в ответ на прежний запрос выставил опцию
            // Don't ask again в диалоговом окне запроса
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // асинхронно покажите окно с объяснением - для чего приложению нужно данное разрешение
                // не блокируйте данный поток в ожидании ответа пользователя!
                // после того, как пользователь закроет окно с объяснениями, запросите разрешение еще раз
            } else {
                // не требуется показывать объяснение. запрашиваем разрешение
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE);
            }
        } else {
            // разрешение уже было предоставлено
        }

        // Если уже получали разрешение, то запускаем MainActivity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    // вызывается после ответа пользователя на запрос разрешения
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                // если пользователь закрыл запрос на разрешение, не дав ответа, массив grantResults будет пустым
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Если получили разрешение, запускаем MainActivity
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    // разрешение было предоставлено
                    // выполните здесь необходимые операции для включения функциональности приложения, связанной с запрашиваемым разрешением
                } else {
                    // разрешение не было предоставлено
                    // выполните здесь необходимые операции для выключения функциональности приложения, связанной с запрашиваемым разрешением
                }
            }
        }
    }
}
