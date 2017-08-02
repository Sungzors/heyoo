package com.phdlabs.sungwon.heyoo.structure.admessages;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.phdlabs.sungwon.heyoo.R;
import com.phdlabs.sungwon.heyoo.api.data.ChatTokenData;
import com.phdlabs.sungwon.heyoo.api.event.ChatTokenEvent;
import com.phdlabs.sungwon.heyoo.api.event.EventsManager;
import com.phdlabs.sungwon.heyoo.api.response.ChatTokenResponse;
import com.phdlabs.sungwon.heyoo.api.rest.HeyooEndpoint;
import com.phdlabs.sungwon.heyoo.api.rest.Rest;
import com.phdlabs.sungwon.heyoo.api.utility.HCallback;
import com.phdlabs.sungwon.heyoo.structure.core.BaseFragment;
import com.phdlabs.sungwon.heyoo.utility.BaseViewHolder;
import com.phdlabs.sungwon.heyoo.utility.Constants;
import com.phdlabs.sungwon.heyoo.utility.Preferences;
import com.phdlabs.sungwon.heyoo.utility.adapter.BaseListRecyclerAdapter;
import com.twilio.chat.CallbackListener;
import com.twilio.chat.Channel;
import com.twilio.chat.ChannelListener;
import com.twilio.chat.ChatClient;
import com.twilio.chat.ErrorInfo;
import com.twilio.chat.Member;
import com.twilio.chat.Message;
import com.twilio.chat.StatusListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import retrofit2.Call;

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

    private HeyooEndpoint mCaller;
    private Preferences mPref;
    private EventBus mEvents;

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
        return R.layout.fragment_chat;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler = findById(R.id.messagesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(layoutManager);
        mMessageEditText = findById(R.id.writeMessageEditText);
        mSendButton = findById(R.id.sendChatMessageButton);
        mCaller = Rest.getInstance().getHeyooEndpoint();
        mPref = new Preferences(getContext());
        mEvents = EventsManager.getInstance().getDataEventBus();
        mSendButton.setOnClickListener(this);
        setupRecycler();
        retrieveAccessTokenFromServer();
    }

    private void setupRecycler() {
        mAdapter = new BaseListRecyclerAdapter<Message, BaseViewHolder>() {
            @Override
            protected void onBindItemViewHolder(BaseViewHolder viewHolder, Message data, int position, int type) {
                bindItemViewHolder(viewHolder, data);
            }

            @Override
            protected BaseViewHolder viewHolder(LayoutInflater inflater, ViewGroup parent, int type) {
                return new BaseViewHolder(R.layout.card_view_message, inflater, parent);
            }
        };
        mAdapter.setItems(mMessages);
        mRecycler.setAdapter(mAdapter);
    }

    private void bindItemViewHolder(BaseViewHolder viewHolder, Message data) {
        String messageText = String.format("%s: %s", data.getAuthor(), data.getMessageBody());
        ((TextView)viewHolder.get(R.id.cvm_message)).setText(messageText);
    }

    private void retrieveAccessTokenFromServer() {
        String deviceId = Settings.Secure.getString(getBaseActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        ChatTokenData data = new ChatTokenData(deviceId);
        showProgress();
        Call<ChatTokenResponse> call = mCaller.getChatToken(mPref.getPreferenceString(Constants.PreferenceConstants.KEY_TOKEN, ""), data);
        call.enqueue(new HCallback<ChatTokenResponse, ChatTokenEvent>(mEvents) {
            @Override
            protected void onSuccess(ChatTokenResponse data) {
                mEvents.post(new ChatTokenEvent());
                String accessToken = data.getToken();

                ChatClient.Properties.Builder builder = new ChatClient.Properties.Builder();
                builder.setSynchronizationStrategy(ChatClient.SynchronizationStrategy.ALL);
                ChatClient.Properties props = builder.createProperties();
                ChatClient.create(getContext(), accessToken, props, mChatClientCallback);
                hideProgress();
            }
        });
    }

    private void loadChannels() {
        mChatClient.getChannels().getChannel("CH023f9403f276488988d6392ff925eda6", new CallbackListener<Channel>() {
            @Override
            public void onSuccess(Channel channel) {
                if (channel != null){
                    joinChannel(channel);
                } else {
                    mChatClient.getChannels().createChannel("CH023f9403f276488988d6392ff925eda6",
                            Channel.ChannelType.PUBLIC, new CallbackListener<Channel>() {
                                @Override
                                public void onSuccess(Channel channel) {
                                    if(channel != null){
                                        joinChannel(channel);
                                    }
                                }

                                @Override
                                public void onError(ErrorInfo errorInfo) {
                                    Log.e(TAG, "Error creating channel: " + errorInfo.getMessage());
                                }
                            });
                }
            }

            @Override
            public void onError(ErrorInfo errorInfo) {
                Log.e(TAG, "Error retrieval channel: " + errorInfo.getMessage());
            }
        });
    }

    private void joinChannel(final Channel channel) {
        Log.d(TAG, "Joining Channel: " + channel.getUniqueName());
        channel.join(new StatusListener() {
            @Override
            public void onSuccess() {
                mGeneralChannel = channel;
                Log.d(TAG, "Joined default channel");
                mGeneralChannel.addListener(mDefaultChannelListener);
            }

            @Override
            public void onError(ErrorInfo errorInfo) {
                Log.e(TAG, "Error joining channel: " + errorInfo.getMessage());
            }
        });
    }

    private CallbackListener<ChatClient> mChatClientCallback =
            new CallbackListener<ChatClient>() {
                @Override
                public void onSuccess(ChatClient chatClient) {
                    mChatClient = chatClient;
                    loadChannels();
                    Log.d(TAG, "Success creating Twilio Chat Client");
                }

                @Override
                public void onError(ErrorInfo errorInfo) {
                    Log.e(TAG, "Error creating Twilio Chat Client:" + errorInfo.getMessage());
                }
            };

    private ChannelListener mDefaultChannelListener = new ChannelListener() {

        @Override
        public void onMessageAdded(final Message message) {
            Log.d(TAG, "Message added");
            getBaseActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMessages.add(message);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onMessageUpdated(Message message) {
            Log.d(TAG, "Message updated: " + message.getMessageBody());
        }

        @Override
        public void onMessageDeleted(Message message) {
            Log.d(TAG, "Message deleted");
        }

        @Override
        public void onMemberAdded(Member member) {
            Log.d(TAG, "Member added: " + member.getIdentity());
        }

        @Override
        public void onMemberUpdated(Member member) {
            Log.d(TAG, "Member updated: " + member.getIdentity());
        }

        @Override
        public void onMemberDeleted(Member member) {
            Log.d(TAG, "Member deleted: " + member.getIdentity());
        }

        @Override
        public void onTypingStarted(Member member) {
            Log.d(TAG, "Started Typing: " + member.getIdentity());
        }

        @Override
        public void onTypingEnded(Member member) {
            Log.d(TAG, "Ended Typing: " + member.getIdentity());
        }

        @Override
        public void onSynchronizationChanged(Channel channel) {

        }
    };

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
