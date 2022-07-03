package com.example.projectrefill;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class firebasemessaging extends FirebaseMessagingService {

    private static  final String NOTIFICATION_CHANNEL_ID="MY_NOTIFICATION_CHANNEL_ID";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {


        super.onMessageReceived(message);

        String notificationtype=message.getData().get("notificationtype");
        if(notificationtype.equals("neworder")){
            String buyeruid=message.getData().get("buyeruid");
            String selleruid=message.getData().get("selleruid");
            String orderid=message.getData().get("orderid");
            String notificationtitle=message.getData().get("notificationtitile");
            String description=message.getData().get("notificationdescription");


        }

    }
}
