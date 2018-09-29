package kola.com.justask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button edit;Button btn;
    ImageView imageView;EditText editText;
    TextView textView;
    DatabaseReference databaseReference;
    ListView listView;
    List<adder> listed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        edit = (Button) findViewById(R.id.edit);
        imageView = (ImageView) findViewById(R.id.imag);
        textView = (TextView) findViewById(R.id.user);
        editText=(EditText)findViewById(R.id.ques);
        listView=(ListView)findViewById(R.id.li);
        databaseReference=FirebaseDatabase.getInstance().getReference("entity");
        listed=new ArrayList<>();
        btn=(Button)findViewById(R.id.post);
        mAuth = FirebaseAuth.getInstance();loadinfo();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), profile.class));
            }
        });

    }
    public void ask(View view)//logged sign up checks if user is signed in
    {  FirebaseUser user = mAuth.getCurrentUser();
    if(mAuth==null){ startActivity(new Intent(getApplicationContext(), logged.class));}
        String name=user.getDisplayName();
        String que=editText.getText().toString().trim();String ans=" ";
            ;String ima=user.getPhotoUrl().toString();
        if(!TextUtils.isEmpty(que)){
        String id=    databaseReference.push().getKey();//get a ref to db
        adder add=new adder(id,name,que,ima,ans);
        databaseReference.child(id).setValue(add);//add user  to db
            Toast.makeText(getApplicationContext(),"ques added successfully",Toast.LENGTH_SHORT).show();editText.setText("");
        }
        else
        { Toast.makeText(getApplicationContext(),"Enter ques to be asked",Toast.LENGTH_SHORT).show();}



    }


    public void login(View view) {
        startActivity(new Intent(getApplicationContext(), logged.class));

    }

    public void inten(View view) {
        startActivity(new Intent(getApplicationContext(), login.class));
        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {//responds to any change in dtabase in terms of insert,del,
            //updates list view accordingly provides for data snapshot which is later changed into list view
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listed.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {adder a=snapshot.getValue(adder.class);
                listed.add(a);}
                list l=new list(MainActivity.this,listed);
                listView.setAdapter(l);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*  @Override
          protected void onStart() {
              super.onStart();
              if(mAuth.getCurrentUser()!=null)
              {  startActivity(new Intent(getApplicationContext(), MainActivity.class));}
          }*/
    private void loadinfo() {
        FirebaseUser user = mAuth.getCurrentUser();//loads info called in oncreate to displat scomnd div in main activity

        //  String dp=user.getDisplayName();
        //String college=user.getDisplayName();
        if (user != null) {
            if (user.getPhotoUrl() != null) { //String photo=user.getPhotoUrl().toString();
                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(imageView);
            }
            if (user.getDisplayName() != null) {
                textView.setText("USER NAME:"+user.getDisplayName());

            }


        }
    }
}



