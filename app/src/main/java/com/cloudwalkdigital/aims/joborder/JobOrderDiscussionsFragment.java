package com.cloudwalkdigital.aims.joborder;


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
import com.cloudwalkdigital.aims.data.model.Discussion;
import com.github.bassaer.chatmessageview.models.Message;
import com.github.bassaer.chatmessageview.models.User;
import com.github.bassaer.chatmessageview.utils.ChatBot;
import com.github.bassaer.chatmessageview.views.ChatView;
import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Random;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobOrderDiscussionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobOrderDiscussionsFragment extends Fragment {
    @Inject
    Gson gson;

    public String TAG = "JOBORDERDISCUSSION";

    private ChatView mChatView;

    public JobOrderDiscussionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JobOrderDiscussionsFragment.
     */
    public static JobOrderDiscussionsFragment newInstance() {
        JobOrderDiscussionsFragment fragment = new JobOrderDiscussionsFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App) getActivity().getApplication()).getNetComponent().inject(this);

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

//        int yourId = 1;
//        Bitmap yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_1);
//        String yourName = "Emily";

        final User me = new User(myId, myName, myIcon);
//        final User you = new User(yourId, yourName, yourIcon);

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

                //Receive message
//                final Message receivedMessage = new Message.Builder()
//                        .setUser(you)
//                        .setRightMessage(false)
//                        .setMessageText(ChatBot.talk(me.getName(), message.getMessageText()))
//                        .build();

                // This is a demo bot
                // Return within 3 seconds
//                int sendDelay = (new Random().nextInt(4) + 1) * 1000;
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mChatView.receive(receivedMessage);
//                    }
//                }, sendDelay);
            }

        });

        return view;
    }

    private void connectToSocketServer() {
        String channel = "jo.1";

        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        Pusher pusher = new Pusher("dbbbc3a5c9b47ac1b3bd", options);
        Channel pChannel = pusher.subscribe(channel);

        pChannel.bind("new.message", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                System.out.println(data);
                newMessage(data);
            }
        });

        pusher.connect();
    }

    private void newMessage(String json) {
        Discussion discussion = gson.fromJson(json, Discussion.class);

        //Receive message
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
