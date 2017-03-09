package rusk.com.akira;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Date;

import it.slyce.messaging.SlyceMessagingFragment;
import it.slyce.messaging.listeners.UserSendsMessageListener;
import it.slyce.messaging.message.Message;
import it.slyce.messaging.message.MessageSource;
import it.slyce.messaging.message.TextMessage;

public class ChatPage extends AppCompatActivity {

    public static SlyceMessagingFragment slyceMessagingFragment;
    public static final String TAG = "";
    public static ArrayList<Message> messages = new ArrayList<>();
    TextMessage textMessage = null;





    //    private static int n = 0;


//    private static Message getRandomMessage() {
//        n++;
//        TextMessage textMessage = new TextMessage();
//         textMessage.setText(n + ""); // +  ": " + latin[(int) (Math.random() * 10)]);
//        textMessage.setDate(new Date().getTime());
//        if (Math.random() > 0.5) {
//            textMessage.setText(n  + " External User");
//            textMessage.setAvatarUrl("https://lh3.googleusercontent.com/-Y86IN-vEObo/AAAAAAAAAAI/AAAAAAAKyAM/6bec6LqLXXA/s0-c-k-no-ns/photo.jpg");
//            textMessage.setUserId("LP");
//            textMessage.setSource(MessageSource.EXTERNAL_USER);
//        } else {
//            textMessage.setText(n  + " Local User");
//            textMessage.setAvatarUrl("https://scontent-lga3-1.xx.fbcdn.net/v/t1.0-9/10989174_799389040149643_722795835011402620_n.jpg?oh=bff552835c414974cc446043ac3c70ca&oe=580717A5");
//            textMessage.setUserId("MP");
//            textMessage.setSource(MessageSource.LOCAL_USER);
//        }
//        return textMessage;
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(rusk.com.akira.R.layout.activity_chat_page);




        slyceMessagingFragment = (SlyceMessagingFragment) getFragmentManager().findFragmentById(rusk.com.akira.R.id.messaging_fragment);
        slyceMessagingFragment.setDefaultAvatarUrl("C:\\Users\\kunal\\Workspace\\AndroidStudioProjects\\Akira\\app\\src\\main\\res\\drawable\\icon.png");
        slyceMessagingFragment.setDefaultDisplayName("Matthew Page");
        slyceMessagingFragment.setDefaultUserId("uhtnaeohnuoenhaeuonthhntouaetnheuontheuo");
        final Button btnCab = (Button) findViewById(R.id.cab);
        final Button btnWiki = (Button) findViewById(R.id.wiki);
        final Button btnWolfram = (Button) findViewById(R.id.wolfram);
        final Button btnNearby = (Button) findViewById(R.id.nearby);




        final StringBuilder stringBuilder = new StringBuilder();
        btnCab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMessage = new TextMessage();
                textMessage.setText("Where do you want to go ?");
                textMessage.setDate(new Date().getTime());
                messages.add(textMessage);
                textMessage.setUserId("LP");
                textMessage.setSource(MessageSource.EXTERNAL_USER);
                slyceMessagingFragment.addNewMessage(textMessage);
                stringBuilder.insert(0, "cab:");
            }
        });
        btnWiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Wiki clicked", Toast.LENGTH_LONG).show();
                textMessage = new TextMessage();
                textMessage.setText("Enter your search query for Wikipedia...");
                textMessage.setDate(new Date().getTime());
                messages.add(textMessage);
                textMessage.setUserId("LP");
                textMessage.setSource(MessageSource.EXTERNAL_USER);
                slyceMessagingFragment.addNewMessage(textMessage);
                stringBuilder.insert(0, "wiki:");
            }
        });
        btnWolfram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMessage = new TextMessage();
                textMessage.setText("Enter your search query for WolframAlpha...");
                textMessage.setDate(new Date().getTime());
                messages.add(textMessage);
                textMessage.setUserId("LP");
                textMessage.setSource(MessageSource.EXTERNAL_USER);
                slyceMessagingFragment.addNewMessage(textMessage);
                stringBuilder.insert(0, "in:"); // for wolfram
            }
        });
        btnNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMessage = new TextMessage();
                textMessage.setText("Enter your search query to get anything nearby...");
                textMessage.setDate(new Date().getTime());
                messages.add(textMessage);
                textMessage.setUserId("LP");
                textMessage.setSource(MessageSource.EXTERNAL_USER);
                slyceMessagingFragment.addNewMessage(textMessage);
                stringBuilder.insert(0, "nearby:");
            }
        });



        slyceMessagingFragment.setOnSendMessageListener(new UserSendsMessageListener() {
            @Override
            public void onUserSendsTextMessage(String text) {

                String str_type = "message";


                Log.d("inf", "******************************** " + text);


                text = stringBuilder.append(text).toString();
                System.out.println("string is %%%%%%%%%%%%%%%%%%%%%%%% " + text);
//   Log.d(TAG, "onUserSendsTextMessage: ");
                BackgroundWorker backgroundWorker = new BackgroundWorker(ChatPage.this);

                backgroundWorker.execute(str_type, text);

                stringBuilder.setLength(0);
                // { message : "afdsjladskj " }
//                SendMessage sendMessage = new SendMes sage(text);


            }

            @Override
            public void onUserSendsMediaMessage(Uri imageUri) {
                Log.d("inf", "******************************** " + imageUri);

            }
        });

//        slyceMessagingFragment.setLoadMoreMessagesListener(new LoadMoreMessagesListener() {
//            @Override
//            public List<Message> loadMoreMessages() {
//        ArrayList<Message> messages = new ArrayList<>();
//
//                for (int i = 0; i < 50; i++)
//                    messages.add(getRandomMessage());
//
//                return messages;
//            }
//        });

//        slyceMessagingFragment.setMoreMessagesExist(true);




    }


}