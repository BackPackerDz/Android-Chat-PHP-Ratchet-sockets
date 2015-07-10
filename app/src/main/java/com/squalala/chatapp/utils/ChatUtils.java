package com.squalala.chatapp.utils;

import com.squalala.chatapp.Message;
import com.squalala.chatapp.common.ChatConstant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Back Packer
 * Date : 09/07/15
 */
public class ChatUtils {


    /**
     * Ici on parse le JSON reçu du serveur
     *
     */
    public static Message jsonToMessage(String json) {


        Message message = null;

        try {
            JSONObject jsonObject = new JSONObject(json);

            message = new Message(
                    jsonObject.getString(ChatConstant.TAG_NAME),
                    jsonObject.getString(ChatConstant.TAG_MESSAGE),
                    // On met "false" car ce n'est pas notre message
                    false
            );

            return message;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String messageToJson(Message message) {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put(ChatConstant.TAG_NAME, message.getName());
            jsonObject.put(ChatConstant.TAG_MESSAGE, message.getMessage());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }


}
