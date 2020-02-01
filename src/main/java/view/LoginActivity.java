package view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.agile.R;
import entity.User;

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button login = findViewById(R.id.logInButton);
        //登录按钮监听器
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入内容，转为字符串
                EditText userName = findViewById(R.id.userName);
                EditText passwd = findViewById(R.id.password);
                String uname = userName.getText().toString();
                String pwd = passwd.getText().toString();
                if (uname.equals("") || uname.equals(" ") || pwd.equals("") || pwd.equals(" ")) {
                    //for debug
                    //this method should change later
                    Toast toast = Toast.makeText(LoginActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    //startActivity(intent);
                }
                else if (!judgeUser(uname, pwd))
                {
                    Toast toast = Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else
                {
                    User user = User.getUser(uname, pwd);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    parseData(intent, user);
                    startActivity(intent);
                }
            }
        });
        Button register = findViewById(R.id.newUser);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(LoginActivity.this, "注册新用户！", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }
    private void parseData(Intent intent, User user) {

    }
    public boolean judgeUser(String userName, String passwd) {

        return true;
    }
}
