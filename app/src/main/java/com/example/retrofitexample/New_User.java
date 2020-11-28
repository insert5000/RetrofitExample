package com.example.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class New_User extends AppCompatActivity {




    Button btnAddToDo;
    TextView tv_id;
    TextView tv_title;
    CheckBox chk1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__user);


        btnAddToDo = (Button)findViewById(R.id.btn_submit);
        tv_id = (TextView)findViewById(R.id.et_id);
        tv_title = (TextView)findViewById(R.id.tvTitle);
        chk1 = (CheckBox) findViewById(R.id.checkBox);




        btnAddToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    agregarNuevo();

            }
        });
    }



    private void agregarNuevo() {


        Retrofit retrofit = API.getRetrofitClient();
        TodoAPI api = retrofit.create(TodoAPI.class);

        Call<Todo> apiCall = api.agregar(Integer.parseInt(tv_id.getText()
                .toString()), tv_title.getText().toString(), chk1.isChecked());

        apiCall.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                Toast.makeText(getApplicationContext(), "Se agrego correctamente el nuevo regitro", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No se pudo agregar", Toast.LENGTH_LONG).show();

            }
        });
    }






}