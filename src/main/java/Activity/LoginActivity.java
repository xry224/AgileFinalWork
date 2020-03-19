package Activity;

import Bean.FileGetter;
import DataBase.DataCenter;
import DataBase.Invariant;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.agile.R;
import Bean.User;
import ServerLogic.LoginRelevantImpl;

import java.util.Objects;

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Invariant.rootPath = Objects.requireNonNull(this.getExternalFilesDir("")).getAbsolutePath() + "/";
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        FileGetter.login();
        /*Thread databaseconn = new Thread(runnable);
        databaseconn.start();
        try {
            databaseconn.join();
        } catch (InterruptedException e) {
            e.printStackTrace(System.out);
        }*/
        final Button loginBtn = findViewById(R.id.logInButton);
        //登录按钮监听器
        loginBtn.setOnClickListener(new View.OnClickListener() {
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
                }
                else {
                    Toast toast = Toast.makeText(LoginActivity.this, "获取数据中，请稍候", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    User user = LoginRelevantImpl.login(uname, pwd);
                    if (user == null) {
                        toast = Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    else {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        DataCenter.loginUser = user;
                        startActivity(intent);
                    }
                }
            }
        });
        Button register = findViewById(R.id.newUser);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Register.class);
                startActivity(intent);
            }
        });
        Button resetpw = findViewById(R.id.forgetpw);
        resetpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPW.class);
                startActivity(intent);
            }
        });
    }
}
/*

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                String driver = "com.mysql.jdbc.Driver";
                Class.forName(driver);
                String url = "jdbc:mysql://172.19.240.244:3306/agile?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=false";
                //String url="jdbc:mysql://127.0.0.1:3306/agile?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
                String username = "root";
                String password = "123456Lsg.";
                //String password = "19971031";
                Connection conn = DriverManager.getConnection(url, username, password);
                if (conn != null){
                    DataBase.connection = conn;
                    DataCenter.currentMainEventList = new getDataImpl().getEventList(DataCenter.expectedEventListSize);
                    DataCenter.merchantList = new getDataImpl().getMerchantList(DataCenter.expectedMerchantListSize);
                }
            }
            catch (Exception e){
                e.printStackTrace(System.out);
            }
        }
    };
 */