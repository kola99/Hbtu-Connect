package kola.com.justask;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class login extends AppCompatActivity  {
//    EditText mail=(EditText)findViewById(R.id.mail);
  //  EditText pass=(EditText)findViewById(R.id.pass);

//    TextView back=(TextView)findViewById(R.id.back);
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button sign=(Button)findViewById(R.id.sign);
        setContentView(R.layout.activity_login);mAuth=FirebaseAuth.getInstance();
//     sign.setOnClickListener(this);
    }

    public void Click(View v) {
        switch(v.getId())
        {
            case R.id.sign:
               register();break;

            case R.id.back:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));



        }
    }


  public void register(){//this is sign up
      EditText mail=(EditText)findViewById(R.id.mail);
       EditText pass=(EditText)findViewById(R.id.pass);
        String email=mail.getText().toString().trim();
        String pas=pass.getText().toString().trim();
      final ProgressBar progressBar=(ProgressBar)findViewById(R.id.prg);
        if(email.isEmpty()){mail.setError("Email is required");
        mail.requestFocus();return;}
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){mail.setError("Please enter valid mail");
        mail.requestFocus();return;}
        if(pas.isEmpty()){mail.setError("Password is required");
            mail.requestFocus();return;}
            if(pas.length()<6)
            {pass.setError("minimum length is 6");
            pass.requestFocus();return;}
            progressBar.setVisibility(View.VISIBLE);progressBar.requestFocus();
            mAuth.createUserWithEmailAndPassword(email,pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        Toast.makeText(getApplicationContext(),"User Registered", Toast.LENGTH_SHORT).show();}
                        else
                    {if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {Toast.makeText(getApplicationContext(),"You are already regisered",Toast.LENGTH_SHORT).show();}
                    }
                }
            });


      }


    }



