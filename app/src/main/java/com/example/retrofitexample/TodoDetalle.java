package com.example.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TodoDetalle extends AppCompatActivity {
    private int idToDo;
    TextInputEditText idDetalle;
    TextInputEditText tituloDetalle;
    CheckBox chkDetalle;
    Button btn_eliminar;
    Button btn_modificar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detalle);

        idDetalle = (TextInputEditText)findViewById(R.id.idDetalle);
        tituloDetalle = (TextInputEditText)findViewById(R.id.tituliDetalle);
        chkDetalle = (CheckBox)findViewById(R.id.chkCompleteToDoDetail);
        btn_eliminar = (Button)findViewById(R.id.btnDeleteToDo);
        btn_modificar = (Button)findViewById(R.id.btnUpdateToDo);
        idToDo = getIntent().getIntExtra(MainActivity.ID_TODO,0);
        btn_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    modificar();

            }
        });

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    eliminar();

            }
        });

        detalle();
    }

    private void eliminar() {


        Retrofit retrofit = API.getRetrofitClient();
        TodoAPI api = retrofit.create(TodoAPI.class);
        Call<Todo> apiCall = api.deleteTodo(idToDo);

        apiCall.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                Toast.makeText(getApplicationContext(), "Se elimino", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No se elimino", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void modificar() {



        Retrofit retrofit = API.getRetrofitClient();
        TodoAPI api = retrofit.create(TodoAPI.class);
        Call<Todo> apiCall = api.updateTodo(idToDo,Integer.parseInt(idDetalle.getText().toString()), tituloDetalle.getText().toString(), chkDetalle.isChecked());

        apiCall.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                Toast.makeText(getApplicationContext(), "Se modifico correctamente", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Falla al momento de modificar", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void detalle() {

        Retrofit retrofit = API.getRetrofitClient();
        TodoAPI api = retrofit.create(TodoAPI.class);
        Call<Todo> apiCall = api.getToDo(idToDo);

        apiCall.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {

                idDetalle.setText(String.valueOf(response.body().getUserId()));
                tituloDetalle.setText(response.body().getTitle());
                chkDetalle.setChecked(response.body().getCompleted());
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {

            }
        });
    }
}