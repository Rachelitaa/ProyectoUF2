package com.example.rachel.projectefinalraquel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AnadirRefugio extends AppCompatActivity {

    private EditText etNombre,etLongitud,etLatitud;
    private Button btnNuevoRefugio;
    private DatabaseReference mRef;
    private Task<Void> mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_refugio);
        btnNuevoRefugio=(Button)findViewById(R.id.btnNuevoRefugio);
        btnNuevoRefugio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escribirNuevoRefugio();
            }
        });


    }

    private void escribirNuevoRefugio() {
        etNombre=(EditText)findViewById(R.id.etNombre);
        etLongitud=(EditText)findViewById(R.id.etLongitud);
        etLatitud=(EditText)findViewById(R.id.etLatitud);
        String nombre=etNombre.getText().toString();
        String longitud=etLongitud.getText().toString();
        String latitud=etLatitud.getText().toString();
        //instancio un objeto refugioAnimal
        RefugioAnimal refugioAnimal=new RefugioAnimal(nombre,longitud,latitud);
        mRef =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://finalprojectraquel.firebaseio.com/");
        String mId = nombre;
        mDatabase = mRef.child("RefugiosAnimales").child(mId).setValue(refugioAnimal);
        Toast toast = Toast.makeText(getApplicationContext(), "Refugio añadido correctamente", Toast.LENGTH_LONG);
        toast.show();

    }
}
