package com.example.rachel.projectefinalraquel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private TextView RefugiosAnimales;
    private Button button2;
    private Button button3;
    //creamos la referencia a la BD
    DatabaseReference mdatabaseReference= FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRootchild=mdatabaseReference.child("RefugiosAnimales").child("nombre");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RefugiosAnimales=(TextView)findViewById(R.id.tvNombre);
        button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("RefugiosAnimales",mRootchild.toString());
                startActivity(intent);
            }
        });
        button3=(Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AnadirRefugio.class);
                intent.putExtra("RefugiosAnimales",mRootchild.toString());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRootchild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String texto=dataSnapshot.getValue().toString();//obtenemos el texto del textView y lo transformamos a String
                RefugiosAnimales.setText(texto);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
