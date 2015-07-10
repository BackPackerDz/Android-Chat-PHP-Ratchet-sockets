package com.squalala.chatapp.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.squalala.chatapp.Message;
import com.squalala.chatapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Back Packer
 * Date : 09/07/15
 */
public class MessageViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.name) TextView txtName;
    @Bind(R.id.message) TextView txtMessage;

    public MessageViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bind(Message message) {
        txtName.setText(message.getName());
        txtMessage.setText(message.getMessage());
    }


}
