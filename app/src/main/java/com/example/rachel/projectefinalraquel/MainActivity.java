package com.example.rachel.projectefinalraquel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView RefugiosAnimales;
    private Button button2;
    private Button button3;
    public static RefugioAnimal refugioAnimal;
    FirebaseListAdapter<RefugioAnimal>adapter;
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    //creamos la referencia a la BD
    //DatabaseReference mdatabaseReference= FirebaseDatabase.getInstance().getReference();
    //DatabaseReference mRootchild=mdatabaseReference.child("RefugiosAnimales").child("Happy pigs").child("nombre");
    //DatabaseReference mRootchild2=mdatabaseReference.child("RefugiosAnimales").child("Happy pigs").child("longitud");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RefugiosAnimales = (TextView) findViewById(R.id.tvNombre);
        button2 = (Button) findViewById(R.id.button2);
        ListView lvrefugios = (ListView)findViewById(R.id.lvrefugios);
        DatabaseReference query = FirebaseDatabase.getInstance()
                .getReference()
                .child("RefugiosAnimales");
        FirebaseListOptions<RefugioAnimal> options = new FirebaseListOptions.Builder<RefugioAnimal>()
                .setQuery(query,RefugioAnimal.class)
                .setLayout(R.layout.lv_refugios)
                .build();
        adapter = new FirebaseListAdapter<RefugioAnimal>(options){
            @Override
            protected void populateView(View view, RefugioAnimal model, int position) {
                TextView tvName = view.findViewById(R.id.tvrefugios);
                tvName.setText(model.getNombre());
                refugioAnimal =new RefugioAnimal(model.getNombre(),model.getLatitud(),model.getLongitud());

            }
        };
        lvrefugios.setAdapter(adapter);

        /*button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
                //intent.putExtra("RefugiosAnimales",mRootchild.toString());
                startActivity(intent);
            }
        });*/
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AnadirRefugio.class);

                startActivity(intent);
            }
        });
    }





}
