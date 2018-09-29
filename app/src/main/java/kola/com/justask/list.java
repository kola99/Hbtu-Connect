package kola.com.justask;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by acer on 3/22/2018.
 */

public class list extends ArrayAdapter<adder> {

    private Activity context;
    private List<adder> lis;FirebaseAuth mAuth;Button btn;TextView t;


    public list(Activity context, List<adder> lis) {
        super(context,R.layout.customize,lis);
        this.context = context;
        this.lis = lis;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        View listitem=inflater.inflate(R.layout.customize,null,true);
        TextView tx=(TextView)listitem.findViewById(R.id.user);
        final EditText ed=(EditText)listitem.findViewById(R.id.ques);
         btn=(Button)listitem.findViewById(R.id.post);
        final adder ad=lis.get(position);
        final String id=ad.getId();// .load(user.getPhotoUrl().toString())
      t=(TextView)listitem.findViewById(R.id.ans);
       ImageView im=(ImageView)listitem.findViewById(R.id.imag);String s="";
       // final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("entity").child(id).child(ad.getans());
       //databaseReference.orderByChild(ad.getans());
        //while(databaseReference!=null)
        //{s+=databaseReference.g}//loads all the questions andd answers

        Glide.with(getContext())
                .load(ad.getImage())
                .into(im);
        tx.setText(ad.getName());
        ed.setText("Can u Answer: "+ad.getQues());t.setText("Answers:->"+"\n"+ad.getans());


       btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),answer.class);intent.putExtra("ans",ad.getans());
                intent.putExtra("image",ad.getImage());
                intent.putExtra("ques", ad.getQues());intent.putExtra("user",ad.getName());intent.putExtra("id",id);
                context.startActivity(intent);

            }
        });


        return listitem;

    }
}
