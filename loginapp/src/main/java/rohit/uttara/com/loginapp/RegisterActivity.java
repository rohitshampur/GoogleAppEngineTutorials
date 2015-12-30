package rohit.uttara.com.loginapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rohit.uttara.backend.loginBeanApi.LoginBeanApi;
import com.rohit.uttara.backend.loginBeanApi.model.LoginBean;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {
    private String username,password;
    private EditText mUserNameEt,mPasswordEt;
    private LoginBean bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUserNameEt = (EditText) findViewById(R.id.userEt);
        mPasswordEt = (EditText) findViewById(R.id.passEt);


    }
    public void register(View v){
        username = mUserNameEt.getText().toString();
        password = mPasswordEt.getText().toString();
        new RegisterToServer().execute();
    }
    class RegisterToServer extends AsyncTask<Void,Void,String>{
        private ProgressDialog dialog;
        private LoginBeanApi api;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(RegisterActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Registering....");
            dialog.show();

        }

        @Override
        protected String doInBackground(Void... params) {
            api = CloudEndPointBuilder.getEndPoint();

            try {
                api.register(username,password);
                return "success";

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(RegisterActivity.this,"Registration failed",Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            Toast.makeText(RegisterActivity.this,"Registration success ,Id = "+s,Toast.LENGTH_LONG).show();
        }
    }
}
