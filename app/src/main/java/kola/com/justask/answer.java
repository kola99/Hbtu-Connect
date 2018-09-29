package kola.com.justask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class answer extends AppCompatActivity {
TextView tx;Button btn;EditText ed;FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        tx=(TextView)findViewById(R.id.textView) ;
        Intent intent=getIntent();
        final String user=intent.getStringExtra("user");
        final String id=intent.getStringExtra("id");
        final String image=intent.getStringExtra("image");
        btn=(Button)findViewById(R.id.but);ed=(EditText)findViewById(R.id.editText2);
        final String ans=intent.getStringExtra("ans");
        mAuth = FirebaseAuth.getInstance();

        final String ques=intent.getStringExtra("ques");
       final String use=mAuth.getCurrentUser().getDisplayName();

      tx.setText("Question:"+ques);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
     //  update(id,user,ques,image,ed.getText().toString());
                   final String a=ans+"\n"+use+" answer:  "+ed.getText().toString();
        update(id,user,ques,image,a);

            }
        });
    }
    private boolean update(final String id, final String name, final String ques, final String image, final String ans) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("entity").child(id);

     databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             dataSnapshot.getRef().child("ans").setValue(ans);
             Toast.makeText(getApplicationContext(),"added successfully",Toast.LENGTH_SHORT).show();
         }

         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
     });





        return false;
    }
}
