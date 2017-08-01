package com.phdlabs.sungwon.heyoo.structure.admessages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.utility.BaseViewHolder;
import com.phdlabs.sungwon.heyoo.utility.adapter.BaseListRecyclerAdapter;
import com.twilio.chat.Channel;
import com.twilio.chat.ChatClient;
import com.twilio.chat.ErrorInfo;
import com.twilio.chat.Message;
import com.twilio.chat.StatusListener;

import java.util.ArrayList;

/**
 * Created by SungWon on 8/1/2017.
 */

public class MessageFragment extends BaseFragment<MessageContract.Controller>
        implements MessageContract.View, View.OnClickListener{

    private final String TAG = "MessageFragment";

    private RecyclerView mRecycler;
    private BaseListRecyclerAdapter<Message, BaseViewHolder> mAdapter;
    private ArrayList<Message> mMessages = new ArrayList<>();

    private EditText mMessageEditText;
    private Button mSendButton;

    private ChatClient mChatClient;

    private Channel mGeneralChannel;


    public static MessageFragment newInstance(){
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected MessageContract.Controller createController() {
        return new MessageController(this);
    }

    @Override
    protected int layoutId() {
        return 0;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler = findById(R.id.messagesRecyclerView);
        mMessageEditText = findById(R.id.writeMessageEditText);
        mSendButton = findById(R.id.sendChatMessageButton);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sendChatMessageButton:
                if(mGeneralChannel != null){
                    String messageBody = mMessageEditText.getText().toString();
                    Message message = mGeneralChannel.getMessages().createMessage(messageBody);
                    Log.d(TAG, "Message Created: " + messageBody);
                    mGeneralChannel.getMessages().sendMessage(message, new StatusListener() {
                        @Override
                        public void onSuccess() {
                            getBaseActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mMessageEditText.setText("");
                                }
                            });
                        }

                        @Override
                        public void onError(ErrorInfo errorInfo) {
                            Log.e(TAG, "Error sending message: " + errorInfo.getMessage());
                        }
                    });
                }
                break;
        }
    }
}
