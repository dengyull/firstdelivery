package com.example.yuxuan.supermario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.common.internal.AccountType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class create_owner_account extends AppCompatActivity {


    private Button BackMainButton;

    DatabaseReference databaseAccounts;
    EditText createAccAccName;
    EditText createAccAccPassword;
    EditText createAccReAccPassword;
    MyAccountType types = MyAccountType.homeOwners;
    Button buttonAddAccount;
    ListView listViewAccounts;

    List<Account> accounts;

    protected void onCreate(Bundle savedInstanceState) {
        databaseAccounts = FirebaseDatabase.getInstance().getReference("Accounts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_owner_account);

        createAccAccName = (EditText) findViewById(R.id.createAccAccName);
        createAccAccPassword = (EditText) findViewById(R.id.createAccAccPassword);
        createAccReAccPassword = (EditText) findViewById(R.id.createAccReAccPassword);
        //types = (MyAccountType) findViewById(R.id.)
        //buttonAddAccount = (Button) findViewById(R.id.createAccCreateBtn);

        accounts = new ArrayList<>();

        BackMainButton=(Button)findViewById(R.id.createAccCreateBtn);

        //adding an onclicklistener to button
        BackMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**addAccount();
                OnBackMainActivity();**/
                if(createAccAccPassword.getText().toString().equals(createAccReAccPassword.getText().toString())){
                    if(checkEmail()){
                        addAccount();
                        OnBackMainActivity();
                    }
                    else{
                        OnBackMainActivityFailEmail();
                    }
                }
                else{
                    OnBackMainActivityFail();
                }

            }
        });
    }

    public void OnBackMainActivity(){
        Toast.makeText(this,"Successfully created an Account",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivityForResult(intent,0);
    }
    public void OnBackMainActivityFail(){
        Toast.makeText(this,"Fail to created an Account",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(getApplicationContext(),ChooseAccountTypeActivity.class);
        startActivityForResult(intent,0);
    }
    public void OnBackMainActivityFailEmail(){
        Toast.makeText(this,"Invaild Email",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(getApplicationContext(),ChooseAccountTypeActivity.class);
        startActivityForResult(intent,0);
    }

    private void addAccount() {

        //Toast.makeText(this, "NOT IMPLEMENTED YET", Toast.LENGTH_LONG).show();
        String username = createAccAccName.getText().toString().trim();
        String password = createAccAccPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(username)){
            String id = databaseAccounts.push().getKey();
            Account account = new Account(username,password,types);

            databaseAccounts.child(id).setValue(account);
            createAccAccName.setText("");
            createAccAccPassword.setText("");
            Toast.makeText(this, "Account added", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_LONG).show();
        }
    }
    private void updateAccount(String username, String password) {

        //Toast.makeText(getApplicationContext(), "NOT IMPLEMENTED YET", Toast.LENGTH_LONG).show();
        DatabaseReference dA = FirebaseDatabase.getInstance().getReference("Accounts").child(username);
        Account product = new Account(username, password, types);
        dA.setValue(product);
        Toast.makeText(getApplicationContext(),"Account Updated", Toast.LENGTH_LONG).show();
    }

    private boolean deleteAccount(String id) {

        //Toast.makeText(getApplicationContext(), "NOT IMPLEMENTED YET", Toast.LENGTH_LONG).show();
        DatabaseReference dA = FirebaseDatabase.getInstance().getReference("Accounts").child(id);
        dA.removeValue();
        Toast.makeText(getApplicationContext(),"Account Delected",Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean checkUsername(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Accounts");

        return true;

    }

    private boolean checkEmail(){
        String validate =

                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+";

        String email = createAccAccName.getText().toString();
        Matcher matcher = Pattern.compile(validate).matcher(email);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

}
