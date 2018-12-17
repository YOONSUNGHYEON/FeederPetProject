package net.skhu.feederpetedit;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FeedRequest extends StringRequest {
    final static private String URL = "http://zc753951.cafe24.com/feederRecord.php";
    private Map<String, String> parameters;

    public FeedRequest(String userID, int feedAmount, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("feedAmount", feedAmount+"");

    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
