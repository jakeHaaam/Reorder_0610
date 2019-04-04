/*package com.example.reorder;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private  String URL="http://asdasd/Register.php";
    private Map<String, String>parameters;

    public RegisterRequest(String userID, String userPassword, int userAge, Response.Listener<String> listener);
    {
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userAge", userAge+ "");
    }

}
*/