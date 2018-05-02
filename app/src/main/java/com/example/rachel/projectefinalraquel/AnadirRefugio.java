package com.example.rachel.projectefinalraquel;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AnadirRefugio extends AppCompatActivity {

    private EditText etNombre,etLongitud,etLatitud;
    private Button btnNuevoRefugio;
    private DatabaseReference mRef;
    private Task<Void> mDatabase;
    private ImageView imageView;
    private Uri imgUri;
    //Referencia al storage de Firebase
    private StorageReference mStorage;
    private static final int GALLERY_INTENT=1;
    public static final String FB_STORAGE_PATH="image/";
    public static final String FB_DATABASE_PATH="image";

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
        imageView=(ImageView)findViewById(R.id.foto);
        //inicializamos la referencia al storage de Firebase
        mStorage= FirebaseStorage.getInstance().getReference();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent que abre la galeria de imágenes
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");// le indicamos que acepta todas las extensiones de imágenes
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //condición para verificar que obtuvimos correctamente la foto para subirla
        if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK){
            imgUri=data.getData();// obtenemos la foto como tipo de dato Uri
            imageView.setImageURI(imgUri);//visualizamos la imagen seleccionada en el imageView
            /*//creamos la carpeta fotos dentro del storage de Firebase
            StorageReference filePath=mStorage.child("fotos").child(uri.getLastPathSegment());//getLastPathsegment sirve para obtener el nombre de la foto
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AnadirRefugio.this,"Se subio exitosamente la foto",Toast.LENGTH_LONG).show();
                }
            });*/
        }
    }

    private void escribirNuevoRefugio() {
        etNombre=(EditText)findViewById(R.id.etNombre);
        etLongitud=(EditText)findViewById(R.id.etLongitud);
        etLatitud=(EditText)findViewById(R.id.etLatitud);
        final String nombre=etNombre.getText().toString();
        final String longitud=etLongitud.getText().toString();
        final String latitud=etLatitud.getText().toString();
        //creamos la carpeta fotos dentro del storage de Firebase
        StorageReference filePath=mStorage.child("fotos"+imgUri.getLastPathSegment());
        filePath.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AnadirRefugio.this,"Se subio exitosamente la foto",Toast.LENGTH_LONG).show();
                RefugioAnimal refugioAnimal=new RefugioAnimal(nombre,latitud,longitud,taskSnapshot.getDownloadUrl().toString());

                //Guardo la clase en firebase database
                mRef =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://finalprojectraquel.firebaseio.com/");
                String mId = nombre;
                //String uploadId=mRef.push().getKey();
                mDatabase = mRef.child("RefugiosAnimales").child(mId).setValue(refugioAnimal);

            }
        })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });

        //instancio un objeto refugioAnimal
        /*RefugioAnimal refugioAnimal=new RefugioAnimal(nombre,latitud,longitud,TaskSnapshot);
        mRef =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://finalprojectraquel.firebaseio.com/");
        String mId = nombre;
        mDatabase = mRef.child("RefugiosAnimales").child(mId).setValue(refugioAnimal);

        Toast toast = Toast.makeText(getApplicationContext(), "Refugio añadido correctamente", Toast.LENGTH_LONG);
        toast.show();*/

    }
}
