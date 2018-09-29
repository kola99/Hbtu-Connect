package kola.com.justask;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class logged extends AppCompatActivity {
    FirebaseAuth mAuth;EditText mail;EditText pass;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);
        mAuth=FirebaseAuth.getInstance();
    }
    public void hell(View view) {
   mail=(EditText)findViewById(R.id.editText);
   pass=(EditText)findViewById(R.id.pass);
   progressBar=(ProgressBar)findViewById(R.id.prg);
        String email=mail.getText().toString().trim();
        String pas=pass.getText().toString().trim();        final ProgressBar progressBar=(ProgressBar)findViewById(R.id.prg);

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

        mAuth.signInWithEmailAndPassword(email,pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) { progressBar.setVisibility(View.GONE);

                if(task.isSuccessful()){
                   // finish();
                    Intent intent=new Intent(getApplicationContext(),profile.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(getApplicationContext(),"login successfull", Toast.LENGTH_SHORT).show();
                    startActivity(intent);}

                else
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}






