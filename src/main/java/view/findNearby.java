package view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.agile.R;

public class findNearby extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_near);
        InitListener();
    }
    private void InitListener() {
        //底部导航栏
        RadioGroup navigate = findViewById(R.id.navigateGroup);
        navigate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                AlertDialog.Builder builder = new AlertDialog.Builder(findNearby.this);
                Intent intent = null;
                //直接使用setContentView(int viewID)会使所有view失效
                switch (checkedId){
                    case R.id.homePageButton:
                    {
                        intent = new Intent(findNearby.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.newEventButton:
                    {
                        intent = new Intent(findNearby.this, newEventActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.eventListButton:
                    {
                        intent = new Intent(findNearby.this, historyList.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.myAccountButton:
                    {
                        intent = new Intent(findNearby.this, myAccount.class);
                        startActivity(intent);
                        break;
                    }
                    default:
                    {
                        break;
                    }
                } //end of switch
                //startActivity(intent);
            }
        }); //end of RadioGroup checked changed listener
        // some other view....
    }// end of initListener
}
