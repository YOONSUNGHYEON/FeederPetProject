package net.skhu.feederpetedit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class FeedRecordListAdapter extends BaseAdapter {

    private Context context;
    private List<FeedRecord> feedRecordList;

    public FeedRecordListAdapter(Context context, List<FeedRecord> feedRecordList)
    {
        this.context = context;
        this.feedRecordList = feedRecordList;
    }
    @Override
    public int getCount() {
        return feedRecordList.size();
    }

    @Override
    public Object getItem(int position) {
        return feedRecordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context,R.layout.feed_record,null);
        TextView feedDateTimeText = (TextView)v.findViewById(R.id.feedDateTimeText);
        TextView feedAmountText = (TextView)v.findViewById(R.id.feedAmountText);


        feedDateTimeText.setText(feedRecordList.get(position).getFeedRegdate());
        feedAmountText.setText(feedRecordList.get(position).getFeedAmount());


        v.setTag(feedRecordList.get(position).getUserID());
        return v; //하나의 사용자에대한 뷰
    }

}
