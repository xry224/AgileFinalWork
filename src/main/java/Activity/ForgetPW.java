package Activity;

import ServerLogic.LoginRelevantImpl;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.agile.R;

public class ForgetPW extends Activity {
    private String verifyCode = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
        Button resetbutton = findViewById(R.id.ForgetPWButton);
        Button verify = findViewById(R.id.sendVerifyPW);
        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = findViewById(R.id.FGEmail);
                EditText newPW = findViewById(R.id.reset_pw);
                EditText verifycode = findViewById(R.id.reset_verify);
                EditText confirmPW = findViewById(R.id.confirm_pw);
                String emailAddress = email.getText().toString();
                String newPassword = newPW.getText().toString();
                String fgVerify = verifycode.getText().toString();
                String confirm = confirmPW.getText().toString();
                if (emailAddress.equals("")){
                    Toast toast = Toast.makeText(ForgetPW.this, "请输入邮箱", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                if (newPassword.equals("") || confirm.equals("")){
                    Toast toast = Toast.makeText(ForgetPW.this, "请输入密码", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                if (fgVerify.equals("")){
                    Toast toast = Toast.makeText(ForgetPW.this, "请输入验证码", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                if (!LoginRelevantImpl.resetPassword(emailAddress, fgVerify, verifyCode, newPassword, confirm)){
                    Toast toast = Toast.makeText(ForgetPW.this, "验证码或密码错误", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else {
                    Toast toast = Toast.makeText(ForgetPW.this, "密码重置成功！", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    //延迟后转到登录页面
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(ForgetPW.this,LoginActivity.class);
                                startActivity(intent);
                            }
                        }, 1500);//1.5秒后执行Runnable中的run方法
                }

            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = findViewById(R.id.FGEmail);
                try {
                    verifyCode = LoginRelevantImpl.sendVerifyCode(email.getText().toString());
                    Toast toast = Toast.makeText(ForgetPW.this, "请前往您的邮箱查看验证码", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(ForgetPW.this, "验证码发送失败！", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }
}
