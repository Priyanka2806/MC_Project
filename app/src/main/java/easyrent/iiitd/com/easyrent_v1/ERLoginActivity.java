package easyrent.iiitd.com.easyrent_v1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;


import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ERLoginActivity extends AppCompatActivity {

    private Button loginButton;
    private Button signupButton;
    private Button googleSignInButton;
    private EditText emailText;
    private EditText passwordText;
    private EditText newEmailText;
    private EditText newPasswordText;
    private EditText newContactText;
    private EditText newUsernameText;
    private Switch loginSwitch;

    int RC_SIGN_IN = 1;
    String savedName= null;
    String savedPassword= null;
    private static final String PREF_NAME = "myDetails";

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

        googleSignInButton = (Button)findViewById(R.id.sign_in_button);


        SharedPreferences value = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        savedName = value.getString("LoginId", null);
        savedPassword = value.getString("Password", null);

        if(savedName!=null && savedPassword!=null) {
            emailText.setText(savedName);
            passwordText.setText(savedPassword);
        }


        //-----------------------------Google Sign in -----------------------------------------------




        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        final GoogleApiClient obj= new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener(){
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                // connection failed, should be handled
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        //---------------------------------------------------------------------------------------------






        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                loginButton.setEnabled(false);
                final ProgressDialog progressDialog = new ProgressDialog(ERLoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();
                final String emailId = emailText.getText().toString();
                final String password = passwordText.getText().toString();

                //Writing details in shared preferences.
                loginSwitch = (Switch)findViewById(R.id.switch1);
                if(loginSwitch.isChecked())
                {
                    SharedPreferences data = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = data.edit();
                    editor.clear();
                    editor.putString("LoginId", emailId);
                    editor.putString("Password", password);
                    editor.commit();
                }
                else{
                    SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                    preferences.edit().remove("LoginId").commit();
                    preferences.edit().remove("Password").commit();

                    emailText.setText("");
                    passwordText.setText("");
                }
                new android.os.Handler().postDelayed(new Runnable() {
                    public void run() {
                    // On complete call either onLoginSuccess or onLoginFailed
                           if(emailId.equals("riya@gmail.com") && password.equals("riya123")){
                           Intent intent = new Intent(getApplicationContext(), RentPostActivity.class);
                           startActivity(intent);
                           }
                           else
                           {
                                Toast.makeText(getApplicationContext(), "Invalid Credentials!!", Toast.LENGTH_LONG).show();
                           }
                            loginButton.setEnabled(true);
                            progressDialog.dismiss();
                    }
                }, 3000);

            }
        });



        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!validate()) {
                    Toast.makeText(getApplicationContext(), "Enter all the details correctly!!", Toast.LENGTH_LONG).show();
                    signupButton.setEnabled(true);
                    return;
                }

                signupButton.setEnabled(false);
//
                final ProgressDialog progressDialog = new ProgressDialog(ERLoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Creating Account...");
                progressDialog.show();
//
                new android.os.Handler().postDelayed(new Runnable() {
                 public void run() {
                     signupButton.setEnabled(true);
                     Toast.makeText(getApplicationContext(), "Details entered to the database. Login to continue!!", Toast.LENGTH_LONG).show();
                     progressDialog.dismiss();
                 }
                }, 3000);


                String emailId = "";
                String password = "";
                String username = "";
                String contact = "";

                emailId = newEmailText.getText().toString();
                password = newPasswordText.getText().toString();
                username = newUsernameText.getText().toString();
                contact = newContactText.getText().toString();
                //int id1=4;
                if (emailId.equals("") || password.equals("") || username.equals("") || contact.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter all the details!!", Toast.LENGTH_LONG).show();
                } else {
                    //Store these details in Database.

                    new JSONTask().execute("jdbc:mysql://192.168.0.105:3307/test");


                }
            }
        });


        //----------------------------- Google Sign-in ------------------------------------------------

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(obj);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });



        //-------------------------------------------------------------------------------------------------




        if(savedInstanceState!=null)
        {
            //If the activity is being restored, the saved state of activity is taken to recreated it.

            emailText.setText(savedInstanceState.getString("loginfield"));
            passwordText.setText(savedInstanceState.getString("passfield"));
            newEmailText.setText(savedInstanceState.getString("emailfield"));
            newPasswordText.setText(savedInstanceState.getString("passwordfield"));
            newContactText.setText(savedInstanceState.getString("contactfield"));
            newUsernameText.setText(savedInstanceState.getString("usernamefield"));

        }
        else{

            emailText.setText("");
            passwordText.setText("");
            newEmailText.setText("");
            newPasswordText.setText("");
            newContactText.setText("");
            newUsernameText.setText("");

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        //loginButton = (Button) findViewById(R.id.btn_login);
        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
       // signupButton = (Button)findViewById(R.id.btn_signup);
        newEmailText = (EditText)findViewById(R.id.input_new_email);
        newPasswordText = (EditText)findViewById(R.id.input_new_password);
        newContactText = (EditText)findViewById(R.id.input_new_contact);
        newUsernameText = (EditText)findViewById(R.id.input_new_username);

        savedInstanceState.putString("loginfield", emailText.getText().toString());
        savedInstanceState.putString("passfield", passwordText.getText().toString());
        savedInstanceState.putString("emailfield", newEmailText.getText().toString());
        savedInstanceState.putString("passwordfield", newPasswordText.getText().toString());
        savedInstanceState.putString("contactfield", newContactText.getText().toString());
        savedInstanceState.putString("usernamefield",  newUsernameText.getText().toString());

        //To save current state details of the activity.
    }



    //--------------------------------------------ggogle sign in ------------------------------------------------------


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("Login Activity", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String displayName = acct.getDisplayName();
            String google_id = acct.getEmail();
            emailText.setText(google_id);
        }
    }


    //--------------------------------------------------------------------------------------


    

    public boolean validate() {
        boolean valid1 = true, valid2 = true, valid3=true, valid4 = true;
        final boolean valid;
        String emailId = "";
        String password = "";
        String username = "";
        String contact = "";
        emailId = newEmailText.getText().toString();
        password = newPasswordText.getText().toString();
        username = newUsernameText.getText().toString();
        contact = newContactText.getText().toString();
        Drawable errorIcon = getResources().getDrawable(R.drawable.error_icon);
        Drawable successIcon = getResources().getDrawable(R.drawable.success_icon);
         //To validate this email id that it exists, we will send a confirmation mail to this id, and if it do not exist, the mail will bounce back.
         //If it exists then username need to enter the verification code send to this id, for completing the sign up process.
        if (emailId.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
             newEmailText.setError("Enter a valid email address", errorIcon);
            valid1 = false;
           } else {
                        newEmailText.setError(null, successIcon);
                    }

                       if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                      newPasswordText.setError("Between 4 and 10 alphanumeric characters",errorIcon);
                       valid2 = false;
                   } else {
                        newPasswordText.setError(null, successIcon);
                   }

                       if (username.isEmpty() || username.length() < 3) {
                       newUsernameText.setError("At least 3 characters");
                        valid3 = false;
                   } else {
                      newUsernameText.setError(null, successIcon);
                  }

                      if (contact.isEmpty() ||  contact.length() < 10 || contact.length() >10 ) {
                        newContactText.setError("10 digits contact number");
                       valid4 = false;
                    } else {
                        newContactText.setError(null, successIcon);
                   }


                              if(valid1 && valid2 && valid3 && valid4) {
                        valid = true;
                       signupButton.setEnabled(true);
                      //Toast.makeText(getApplicationContext(), "Details entered to the database. Login to continue!!", Toast.LENGTH_LONG).show();
                          }
            else{
                 valid=false;
              //Toast.makeText(getApplicationContext(), "Enter all the details!!", Toast.LENGTH_LONG).show();
                         }
               return valid;
          }

    //----------------------------------------------DB Class----------------------------------------------------------

    public class JSONTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {    //loads the content of web page whose url is given
            HttpURLConnection connection=null;
            BufferedReader reader=null;

            //URL url=new URL(strings[0]);
            //connection=(HttpURLConnection)url.openConnection();//to open url connection to the given url
            String reg_url="jdbc:mysql://192.168.0.105:3307/test";
            String uname="test123";
            String pass="test";

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn= DriverManager.getConnection(reg_url,uname,pass);

                Statement stmt=conn.createStatement();
                String str="Insert into test1 values('abc','def');";
                int res=stmt.executeUpdate(str);
                Log.d("value of res: ",String.valueOf(res));
                if(res>0)
                    return "successful insertion";
                else
                    return "no";

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }



            return null;


        }



        @Override
        protected void onPostExecute(String s) {    //the received content is then displayed in the textView
            super.onPostExecute(s);
            //txtData.setText(s);
            Log.d("inside opPost",s);
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
        }
    }








}
