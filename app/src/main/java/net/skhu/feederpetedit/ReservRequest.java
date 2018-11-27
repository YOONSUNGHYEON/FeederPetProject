package net.skhu.feederpetedit;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class ReservRequest extends StringRequest {
    final static private String URL = "http://zc753951.cafe24.com/reserv.php";
    private Map<String, String> parameters;

    public ReservRequest(int feedGram, String feedDate, String feedTime, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("feedGram", feedGram+"");
        parameters.put("feedDate", feedDate);
        parameters.put("feedTime", feedTime);

    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}


