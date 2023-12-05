package com.example.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.room.dao.UserDao;
import com.example.room.database.AppDatabase;
import com.example.room.entity.User;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        // Crear usuarios
        User user1 = new User();
        user1.uid = 0;
        user1.firstName = "Daniela";
        user1.lastName = "Garcia";

        User user2 = new User();
        user2.uid = 1;
        user2.firstName = "Alejandro";
        user2.lastName = "Garcia";

        User user3 = new User();
        user3.uid = 2;
        user3.firstName = "Yesid";
        user3.lastName = "Mora";



        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Texto de vista
                TextView textViewHello = findViewById(R.id.textUser);

                //Eliminar users
                db.userDao().delete(user1);
                db.userDao().delete(user2);
                db.userDao().delete(user3);

                //Insertar users
                db.userDao().insertAll(user1, user2, user3);

                // Obtener la lista de usuarios y construir la cadena de texto
                List<User> users = db.userDao().getAll();
                StringBuilder usersText = new StringBuilder();
                for (User user : users) {
                    usersText.append("User: ").append(user.uid).append(" ").append(user.firstName).append(" ").append(user.lastName).append("\n");
                }

                // Mostrar la lista de usuarios en el TextView
                TextView textViewUsers = findViewById(R.id.textUser);
                textViewUsers.setText(usersText.toString());

            }
        });

    }
}