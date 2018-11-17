package net.skhu.feederpetedit;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PetInfoRequest extends StringRequest {
    final static private String URL = "http://zc753951.cafe24.com/PetInfo.php";
    private Map<String, String> parameters;

    public PetInfoRequest(int petType, String petName, String userID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("petType", petType+"");
        parameters.put("petName", petName);
        parameters.put("userID", userID);


    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
