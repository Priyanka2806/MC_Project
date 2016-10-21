package easyrent.iiitd.com.easyrent_v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ERLoginActivity extends AppCompatActivity {

    private Button loginButton;
    private Button signupButton;
    private EditText emailText;
    private EditText passwordText;
    private EditText newEmailText;
    private EditText newPasswordText;
    private EditText newContactText;
    private EditText newUsernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erlogin);

        loginButton = (Button) findViewById(R.id.btn_login);
        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        signupButton = (Button)findViewById(R.id.btn_signup);
        newEmailText = (EditText)findViewById(R.id.input_new_email);
        newPasswordText = (EditText)findViewById(R.id.input_new_password);
        newContactText = (EditText)findViewById(R.id.input_new_contact);
        newUsernameText = (EditText)findViewById(R.id.input_new_username);


        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String emailId = emailText.getText().toString();
                String password = passwordText.getText().toString();
                if(emailId.equals("riya@gmail.com") && password.equals("riya123")){
                    Intent intent = new Intent(getApplicationContext(), RentPostActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Invalid Credentials!!", Toast.LENGTH_LONG).show();
                }

            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String emailId = "";
                String password = "";
                String username = "";
                String contact = "";

                emailId = newEmailText.getText().toString();
                password = newPasswordText.getText().toString();
                username = newUsernameText.getText().toString();
                contact = newContactText.getText().toString();

                if(emailId.equals("") || password.equals("") || username.equals("") || contact.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter all the details!!", Toast.LENGTH_LONG).show();
                }
                else{
                    //Store these details in Database.
                }


            }
        });
    }
}