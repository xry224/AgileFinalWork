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

public class Register extends Activity {
    private String verifyCode = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);
        init();
    }

    private void init() {
        Button button = findViewById(R.id.registerButton);
        Button sendVerify = findViewById(R.id.sendVerifyRE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newName = findViewById(R.id.new_username);
                EditText newPW = findViewById(R.id.new_password);
                EditText verify = findViewById(R.id.verifyCode);
                EditText email = findViewById(R.id.phoneOrEmail);
                String userName = newName.getText().toString();
                String password = newPW.getText().toString();
                String userVerifyCode = verify.getText().toString();
                String emailAdd = email.getText().toString();
                String result = LoginRelevantImpl.register(emailAdd, userName,password, userVerifyCode, verifyCode);
                if (emailAdd.equals("")){
                    Toast toast = Toast.makeText(Register.this, "请输入邮箱", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                if (userName.equals("") || password.equals("")){
                    Toast toast = Toast.makeText(Register.this, "请输入密码与用户名", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                if (userVerifyCode.equals("")){
                    Toast toast = Toast.makeText(Register.this, "请输入验证码", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                //显示结果
                Toast toast = Toast.makeText(Register.this, result, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                //延迟后转到登录页面
                if (result.equals("注册成功！")){
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Register.this,LoginActivity.class);
                            startActivity(intent); }
                    }, 1500);//1.5秒后执行Runnable中的run方法
                }
                }

        });
        sendVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = findViewById(R.id.phoneOrEmail);
                try {
                    verifyCode = LoginRelevantImpl.sendVerifyCode(email.getText().toString());
                    Toast toast = Toast.makeText(Register.this, "请前往您的邮箱查看验证码", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(Register.this, "验证码发送失败！", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }
}
