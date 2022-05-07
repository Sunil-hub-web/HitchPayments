package in.co.hitchpayments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import in.aabhasjindal.otptextview.OtpTextView;

public class LoginPage extends AppCompatActivity {

    Button btn_signin;
    TextView text_signUp,otpviewtext;
    EditText edit_EnterOTP,edit_MobileNo;
    TextInputLayout editEnterOTP,editMobileNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        btn_signin = findViewById(R.id.btn_signin);
        edit_EnterOTP = findViewById(R.id.edit_EnterOTP);
        edit_MobileNo = findViewById(R.id.edit_MobileNo);
        editEnterOTP = findViewById(R.id.editEnterOTP);
        editMobileNo = findViewById(R.id.editMobileNo);

        editEnterOTP.setVisibility(View.GONE);
        btn_signin.setText("Send OTp");


        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editEnterOTP.setVisibility(View.VISIBLE);
                editMobileNo.setVisibility(View.GONE);
                btn_signin.setText("Verifay Otp");

                Intent intent = new Intent(LoginPage.this,DashBoard.class);
                startActivity(intent);

            }
        });

    }
}