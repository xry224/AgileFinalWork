package myView;

import Activity.MerchantDetail;
import Bean.Comment;
import Bean.Merchant;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.example.agile.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommentAdapter extends SimpleAdapter {
    private LayoutInflater mInflater;
    private ArrayList<Comment> commentList;
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
    public CommentAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to, double[] rank) {
        super(context, data, resource, from, to);
        mInflater = LayoutInflater.from(context);
        //ranks = rank;
    }
    public CommentAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to, double[] rank, ArrayList<Comment> comments) {
        super(context, data, resource, from, to);
        mInflater = LayoutInflater.from(context);
        //ranks = rank;
        commentList = comments;
    }
    public CommentAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to, ArrayList<Comment> comments) {
        super(context, data, resource, from, to);
        mInflater = LayoutInflater.from(context);
        commentList = comments;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.comment_list_item, parent, false);
        }
        final Comment comment = commentList.get(position);

        TextView positiveRank = convertView.findViewById(R.id.positiveRank);
        TextView negativeView = convertView.findViewById(R.id.negativeRank);
        positiveRank.setText(Integer.toString(comment.getPositive()));
        negativeView.setText(Integer.toString(comment.getNegative()));
        //set rank of rating bar;
        RatingBar ratingBar = convertView.findViewById(R.id.userRank);
        ratingBar.setRating((float) comment.getRank());
        return super.getView(position, convertView, parent);
    }
}
