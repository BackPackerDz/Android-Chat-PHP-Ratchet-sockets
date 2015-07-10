package com.squalala.chatapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squalala.chatapp.adapter.MessagesAdapter;
import com.squalala.chatapp.common.ChatConstant;
import com.squalala.chatapp.utils.ChatUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;


public class ChatActivity extends AppCompatActivity {

    private WebSocketConnection mConnection = new WebSocketConnection();

    private static final String TAG = ChatActivity.class.getSimpleName();

    private MessagesAdapter messagesAdapter;
    private ArrayList<Message> messages = new ArrayList<Message>();

    private String myName;

    @Bind(R.id.editMessage)
    EditText editMessage;

    @Bind(R.id.btnSendMessage)
    Button btnSendMessage;

    @Bind(R.id.recyclerView_messages)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ButterKnife.bind(this);

        // On récupère le nom entré par l'utilisateur
        myName = getIntent().getStringExtra(ChatConstant.TAG_NAME);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        messagesAdapter = new MessagesAdapter(messages);

        recyclerView.setAdapter(messagesAdapter);

        try {

            mConnection.connect(ChatConstant.URL, new WebSocketHandler() {

                @Override
                public void onOpen() {
                    Log.d(TAG, "Connexion réussi à : " + ChatConstant.URL);
                }

                @Override
                public void onTextMessage(String payload) {
                    Log.d(TAG, " " + payload);

                    messages.add(ChatUtils.jsonToMessage(payload));
                    messagesAdapter.notifyDataSetChanged();

                    scrollToBottom();
                }

                @Override
                public void onClose(int code, String reason) {
                    Log.d(TAG, "Connexion perdu");
                }
            });

        } catch (WebSocketException e) {
            e.printStackTrace();
        }


        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                 * On vérifie que notre edittext n'est pas vide
                 */
                if (!TextUtils.isEmpty(getMessage())) {

                    // On met "true" car c'est notre message
                    Message message = new Message(myName, getMessage(), true);

                    String json = ChatUtils.messageToJson(message);

                    // On envoie notre message
                    mConnection.sendTextMessage(json);

                    // On ajoute notre message à notre list
                    messages.add(message);

                    // On notifie notre adapter
                    messagesAdapter.notifyDataSetChanged();

                    scrollToBottom();

                    // On efface !
                    editMessage.setText("");
                }

            }
        });

    }

    /**
     * Scroller notre recyclerView en bas
     */
    private void scrollToBottom() {
        recyclerView.scrollToPosition(messages.size() - 1);
    }

    private String getMessage() {
        return editMessage.getText().toString().trim();
    }


}
