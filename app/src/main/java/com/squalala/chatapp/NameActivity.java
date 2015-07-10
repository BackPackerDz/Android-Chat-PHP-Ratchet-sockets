package com.squalala.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squalala.chatapp.common.ChatConstant;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Back Packer
 * Date : 09/07/15
 */
public class NameActivity extends AppCompatActivity {

    @Bind(R.id.edit_name)
    EditText editName;

    @Bind(R.id.btnJoin)
    Button btnJoin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        ButterKnife.bind(this);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(getName())) {
                    Intent intent = new Intent(NameActivity.this, ChatActivity.class);
                    intent.putExtra(ChatConstant.TAG_NAME, getName());
                    startActivity(intent);
                    finish();
                }


            }
        });



    }

    private String getName() {
        return editName.getText().toString().trim();
    }




}
