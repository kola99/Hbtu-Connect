package kola.com.justask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class profile extends AppCompatActivity {
ImageView imageView;EditText editText;EditText college;Button button;
private static final int CHOOSE_IMAGE=101;ProgressBar progressBar;
Uri uriimage;String profileurl;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editText=(EditText)findViewById(R.id.user);
        college=(EditText)findViewById(R.id.college);
        imageView=(ImageView)findViewById(R.id.image);
        button=(Button)findViewById(R.id.sv);
        progressBar=(ProgressBar)findViewById(R.id.prg);
        mAuth=FirebaseAuth.getInstance();
        loadinfo();
        profileurl=mAuth.getCurrentUser().getPhotoUrl().toString();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           imagechooser();
            }
        });
      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              saveinfo();

          }


      });


    }

    private void loadinfo() {
        FirebaseUser user=mAuth.getCurrentUser();

      //  String dp=user.getDisplayName();
        //String college=user.getDisplayName();
        if(user!=null)
        {
            if(user.getPhotoUrl()!=null)
            { //String photo=user.getPhotoUrl().toString();
                Glide.with(this)
            .load(user.getPhotoUrl().toString())
            .into(imageView);}
            if(user.getDisplayName()!=null){
                editText.setText(user.getDisplayName());

            }
            if(user.getPhoneNumber()!=null){college.setText(user.getPhoneNumber());}


        }


    }

    private void imagechooser()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Image"),CHOOSE_IMAGE);

    }
    private void saveinfo(){String name=college.getText().toString();//saves the info as set in edit profilr
    String user=editText.getText().toString();
    progressBar.setVisibility(View.VISIBLE);
    if(user.isEmpty()){editText.setError("Name Required");
    editText.requestFocus();return;}
        FirebaseUser username=mAuth.getCurrentUser();
    if(username!=null&&profileurl!=null) {
        UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()//created  user profile change class assign all data to object
                .setDisplayName(name)
                .setDisplayName(name)
                 .setDisplayName(name)
                .setPhotoUri(Uri.parse(profileurl))
                .build();

        username.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    //finish();
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"update successfull",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
        progressBar.setVisibility(View.GONE);
            }
        });
    }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//after image choosr sets bitmap to image view
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CHOOSE_IMAGE&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
      uriimage= data.getData();
        try {
            Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uriimage);
            imageView.setImageBitmap(bitmap);
            uploadfirebase();


        }

        catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onStart() {//if user hant logged in divert him to logged
        super.onStart();
        if(mAuth.getCurrentUser()==null)
        {  startActivity(new Intent(getApplicationContext(), logged.class));}

    }

    private void uploadfirebase() {//uplads the changes to firebase
  //upload to fb
        StorageReference pro= FirebaseStorage.getInstance().getReference("profile/"+System.currentTimeMillis()+".jpg");
       if(uriimage!=null)

       {progressBar.setVisibility(View.VISIBLE);
           pro.putFile(uriimage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
             progressBar.setVisibility(View.GONE);
             profileurl=taskSnapshot.getDownloadUrl().toString();
               }



           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   progressBar.setVisibility(View.GONE);
               }
           });




}}

}

