package myView;
import Activity.MessageActivity;
import Bean.Message;
import DataBase.DataCenter;
import ServerLogic.EventRelevantImpl;
import ServerLogic.getDataImpl;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.example.agile.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageAdapter extends SimpleAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<HashMap<String, Object>> itemList;
    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public MessageAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to, ArrayList<HashMap<String, Object>> itemList) {
        super(context, data, resource, from, to);
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final int index = position;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.message_list_item, parent, false);
        }
        Button accept = convertView.findViewById(R.id.accept);
        Button reject = convertView.findViewById(R.id.reject);
        final ArrayList<Message> messageList = new getDataImpl().getMessage(DataCenter.loginUser.getMessageBox());
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = messageList.get(index);
                EventRelevantImpl.handleMessage(message, DataCenter.loginUser, true);
                MessageActivity.addItem(new getDataImpl().getMessage(DataCenter.loginUser.getMessageBox()), itemList);
                notifyDataSetChanged();
                Toast toast = Toast.makeText(context, "已同意申请", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = messageList.get(index);
                EventRelevantImpl.handleMessage(message, DataCenter.loginUser, false);
                MessageActivity.addItem(new getDataImpl().getMessage(DataCenter.loginUser.getMessageBox()), itemList);
                notifyDataSetChanged();
                Toast toast = Toast.makeText(context, "已拒绝申请", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        return super.getView(position, convertView, parent);
    }
}

