package com.example.projectrefill;

import android.app.Activity;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class firebasenotificationsendertesting {

    String userfcmtoken;
    String title;
    String body;
    Context mContext;
    Activity mActivity;

    private RequestQueue requestQueue;
    private final String posturl="https://fcm.googleapis.com/fcm/send";
    private final String fcmServerKey="AAAAlTw2js0:APA91bFBgYACMCJezb_oH6LQy7nEv62b0sH1mqss3WWeaePPZNZnLGkYc8gKmGiKWCfOrQTJEomGz0BJvXl-b-E88isaNZadn8lB6R8-EK_TOe3iqj5IchN9fSdDAnlbJKBdS41xZAKb";

    public firebasenotificationsendertesting(String userfcmtoken, String title, String body, Context mContext, Activity mActivity) {
        this.userfcmtoken = userfcmtoken;
        this.title = title;
        this.body = body;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    public void sendnotifications(){

        requestQueue= Volley.newRequestQueue(mActivity);
        JSONObject mainobj=new JSONObject();
        try {
            mainobj.put("to",userfcmtoken);
            JSONObject notiobject=new JSONObject();
            notiobject.put("title",title);
            notiobject.put("body",body);
            notiobject.put("icon",R.drawable.logo);

            mainobj.put("notification",notiobject);

            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, posturl,mainobj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //after sending fcm start order details activity

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //if failed sending fcm

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    //put required headers
                    Map<String,String> headers=new HashMap<>();
                    headers.put("Content_Type","application/json");
                    headers.put("Authorization","key="+constantsforuse.FCM_KEY);

                    return headers;
                }
            };
            Volley.newRequestQueue(mActivity.getApplicationContext()).add(jsonObjectRequest);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
