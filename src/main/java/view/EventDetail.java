package view;

import android.app.Activity;
import android.os.Bundle;
import com.example.agile.R;
import entity.Event;

public class EventDetail extends Activity {
    private static Event event = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);

    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        EventDetail.event = event;
    }
}

