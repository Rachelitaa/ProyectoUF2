package com.example.rachel.projectefinalraquel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView RefugiosAnimales;
    private Button button2;
    private Button button3;
    public ArrayList<RefugioAnimal>todosLosrefugios=new ArrayList<RefugioAnimal>();
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
                ImageView imageview=view.findViewById(R.id.imagen);
                String imagenString=model.getUrl();
                Glide.with(getApplicationContext()).load(imagenString).into(imageview);
                //Bitmap imagenBitmap=StringToBitMap(imagenString);
                //imagen.setImageBitmap(imagenBitmap);
                refugioAnimal =new RefugioAnimal(model.getNombre(),model.getLatitud(),model.getLongitud());
                todosLosrefugios.add(refugioAnimal);
            }
        };
        lvrefugios.setAdapter(adapter);
        lvrefugios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?>adapterView, View view, int position, long id) {
                RefugioAnimal refugioAnimal=(RefugioAnimal)adapterView.getItemAtPosition(position);

                Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("refugioAnimal",refugioAnimal);
                startActivity(intent);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MapsActivity2.class);
                intent.putExtra("todosLosrefugios",todosLosrefugios);
                startActivity(intent);
            }
        });
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AnadirRefugio.class);

                startActivity(intent);
            }
        });
    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }





}
