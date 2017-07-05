package com.cloudwalkdigital.aims.joborder;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudwalkdigital.aims.App;
import com.cloudwalkdigital.aims.R;
import com.cloudwalkdigital.aims.data.APIService;
import com.cloudwalkdigital.aims.data.model.Discussion;
import com.cloudwalkdigital.aims.userselection.UserSelectionActivity;
import com.cloudwalkdigital.aims.utils.SessionManager;
import com.github.bassaer.chatmessageview.models.Message;
import com.github.bassaer.chatmessageview.models.User;
import com.github.bassaer.chatmessageview.utils.ChatBot;
import com.github.bassaer.chatmessageview.views.ChatView;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobOrderDiscussionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobOrderDiscussionsFragment extends Fragment {
    @Inject Gson gson;
    @Inject SharedPreferences sharedPreferences;
    @Inject SessionManager sessionManager;
    @Inject Retrofit retrofit;

    public String TAG = "JOBORDERDISCUSSION";
    public List<Discussion> discussions;
    public Integer jobOrderId;

    private ChatView mChatView;
    public com.cloudwalkdigital.aims.data.model.User currentUser;

    public JobOrderDiscussionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JobOrderDiscussionsFragment.
     */
    public static JobOrderDiscussionsFragment newInstance(List<Discussion> discussions, Integer jobOrderId) {
        Gson converter = new Gson();
        String messages = converter.toJson(discussions);

        JobOrderDiscussionsFragment fragment = new JobOrderDiscussionsFragment();

        Bundle args = new Bundle();
        args.putString("discussions", messages);
        args.putInt("jobOrderId", jobOrderId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App) getActivity().getApplication()).getNetComponent().inject(this);

        if (getArguments() != null) {
            String messages = getArguments().getString("discussions");
            Type type = new TypeToken<List<Discussion>>(){}.getType();
            discussions = gson.fromJson(messages, type);

            jobOrderId = getArguments().getInt("jobOrderId");
        }

        currentUser = sessionManager.getUserInformation();

        connectToSocketServer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job_order_discussions, container, false);

        //User id
        int myId = 0;
        //User icon
        Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);
        //User name
        String myName = "Michael";

        final User me = new User(currentUser.getId(), currentUser.getProfile().getName(), myIcon);

        mChatView = (ChatView) view.findViewById(R.id.chat_view);

        //Set UI parameters if you need
        mChatView.setRightBubbleColor(ContextCompat.getColor(getContext(), R.color.green500));
        mChatView.setLeftBubbleColor(Color.WHITE);
        mChatView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.background_pattern));
        mChatView.setSendButtonColor(ContextCompat.getColor(getContext(), R.color.cyan900));
        mChatView.setSendIcon(R.drawable.ic_action_send);
        mChatView.setRightMessageTextColor(Color.WHITE);
        mChatView.setLeftMessageTextColor(Color.BLACK);
        mChatView.setUsernameTextColor(Color.WHITE);
        mChatView.setSendTimeTextColor(Color.WHITE);
        mChatView.setDateSeparatorColor(Color.WHITE);
        mChatView.setInputTextHint("Type a message...");
        mChatView.setMessageMarginTop(15);
        mChatView.setMessageMarginBottom(0);

        //Click Send Button
        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new message
                Message message = new Message.Builder()
                        .setUser(me)
                        .setRightMessage(true)
                        .setMessageText(mChatView.getInputText())
                        .hideIcon(true)
                        .build();
                //Set to chat view
                mChatView.send(message);
                //Reset edit text
                mChatView.setInputText("");

                sendMessageRemote(message.getMessageText());
            }

        });

        initPreviousMessages();

        return view;
    }

    private void sendMessageRemote(String message) {
        APIService service = retrofit.create(APIService.class);

        Call<Discussion> call = service.sendMessage(message, currentUser.getId(), jobOrderId,currentUser.getApiToken());
        call.enqueue(new Callback<Discussion>() {
            @Override
            public void onResponse(Call<Discussion> call, Response<Discussion> response) {
                Log.i(TAG, response.toString());
            }

            @Override
            public void onFailure(Call<Discussion> call, Throwable t) {

            }
        });
    }

    private void connectToSocketServer() {
        String channel = "jo."+jobOrderId;

        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        Pusher pusher = new Pusher("dbbbc3a5c9b47ac1b3bd", options);
        Channel pChannel = pusher.subscribe(channel);

        pChannel.bind("App\\Events\\NewDiscussion", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                System.out.println(data);
                Discussion discussion = gson.fromJson(data, Discussion.class);

                if (discussion.getUser().getId() != currentUser.getId()) {
                    newMessage(discussion);
                }
            }
        });

        pusher.connect();
    }

    private void initPreviousMessages() {
        if (discussions.size() <= 0) {
            return;
        }

        for (Discussion discussion : discussions) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss", Locale.ENGLISH);
            Calendar createdAt = Calendar.getInstance();

            try {
                createdAt.setTime(sdf.parse(discussion.getCreatedAt()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //Receive message
            Bitmap yourIcon = getOptimizedBitmap(R.drawable.face_1);
            com.cloudwalkdigital.aims.data.model.User user = discussion.getUser();
            User sender = new User(user.getId(), user.getProfile().getName(), yourIcon);

            final Message receivedMessage = new Message.Builder()
                    .setUser(sender)
                    .setRightMessage(currentUser.getId() == user.getId())
                    .setCreatedAt(createdAt)
                    .setMessageText(discussion.getMessage())
                    .build();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mChatView.receive(receivedMessage);
                }
            });
        }
    }

    private void newMessage(Discussion discussion) {
        //Receive message
        try {
            Bitmap yourIcon = getOptimizedBitmap(R.drawable.face_1);
            com.cloudwalkdigital.aims.data.model.User user = discussion.getUser();
            User sender = new User(user.getId(), user.getProfile().getName(), yourIcon);
            final Message receivedMessage = new Message.Builder()
                    .setUser(sender)
                    .setRightMessage(false)
                    .setMessageText(discussion.getMessage())
                    .build();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    playNotification();
                    mChatView.receive(receivedMessage);
                }
            });
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private Bitmap getOptimizedBitmap(int drawableId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(getResources(), drawableId);
        BitmapFactory.decodeResource(getResources(), drawableId, options);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
//        options.inDither = true;
        options.inSampleSize= calculateInSampleSize(options, 50, 50);

        return BitmapFactory.decodeResource(getResources(), drawableId, options);
    }

    private void playNotification() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getActivity().getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
