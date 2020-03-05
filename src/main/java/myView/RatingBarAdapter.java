package myView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import com.example.agile.R;

import java.util.List;
import java.util.Map;

public class RatingBarAdapter extends SimpleAdapter {
    private LayoutInflater mInflater;
    private double[] ranks;
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
    public RatingBarAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to, double[] rank) {
        super(context, data, resource, from, to);
        mInflater = LayoutInflater.from(context);
        ranks = rank;
    }
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.shop_list_item, parent, false);
        }
        RatingBar ratingBar = convertView.findViewById(R.id.shopTotalRank);
        double rank = ranks[position];
        ratingBar.setRating((float) rank);
        return super.getView(position, convertView, parent);
    }
}
