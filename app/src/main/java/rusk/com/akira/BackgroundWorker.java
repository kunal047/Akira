package rusk.com.akira;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import it.slyce.messaging.message.MessageSource;
import it.slyce.messaging.message.TextMessage;

import static rusk.com.akira.ChatPage.messages;
import static rusk.com.akira.ChatPage.slyceMessagingFragment;

public class BackgroundWorker extends AsyncTask <String, Void, String> {

    Context context;
    AlertDialog alertDialog;


    BackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        String type = params[0];

        String login_url = "http://192.168.1.36:5000/ask";
//        String login_url = "http://192.168.1.103:5000/data";
//        String register_url = "http://192.168.1.103/android_mysql/insert.php";



        if(type.equals("message")) {
            try {

                String user_text = params[1];


                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));


//                String post_data = "{\"message\" :" + "\"" + user_text + "\""+ "}";

                String post_data = URLEncoder.encode("user_text", "UTF-8")+"="+URLEncoder.encode(user_text, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String line;
                String result = "";
                while((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        else if (type.equals("register"))
//        {
//
//            try {
//
//                String u_name = params[1];
//                String u_email = params[2];
//                String u_phone = params[3];
//
//                URL url = new URL(register_url);
//                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                String post_data = URLEncoder.encode("user_name", "UTF-8")+"="+URLEncoder.encode(u_name, "UTF-8")+"&"+
//                        URLEncoder.encode("user_email", "UTF-8")+"="+URLEncoder.encode(u_email, "UTF-8")+"&"+
//                        URLEncoder.encode("user_phone", "UTF-8")+"="+URLEncoder.encode(u_phone, "UTF-8");
//
//                bufferedWriter.write(post_data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
//
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
//                String result = "";
//                String line;
//                while((line = bufferedReader.readLine()) != null) {
//                    result += line;
//                }
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return result;
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

//    SlyceMessagingFragment slyceMessagingFragment;

    @Override
    protected void onPostExecute(final String result) {

        String response;
        String url = "";
//        alertDialog.setMessage(result);
//        finalResult = result;
//        alertDialog.show();
        System.out.println("result " + result);
//        SlyceMessagingFragment slyceMessagingFragment = null;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String fileData = new String(result);
        GetResponse getResponse = gson.fromJson(fileData, GetResponse.class);
        System.out.println("\n\nResponse Object\n\n" + getResponse.getAnswer());

        // create JSON String from Object
        String jsonResponse = gson.toJson(getResponse);
        System.out.print("why %%%%%%%%%%%%%%%%%%%%%%%%% " + jsonResponse);

        if (getResponse.getStatus().equals("OK")) {
            response = getResponse.getAnswer();
            url = getResponse.getURL();
        } else {
            response = getResponse.getStatus();
        }

        TextMessage textMessage = new TextMessage();
        System.out.println("Response is ****************** " + response);
        textMessage.setText(response);
        textMessage.setDate(new Date().getTime());
        messages.add(textMessage);
        textMessage.setAvatarUrl("C:\\Users\\kunal\\Workspace\\AndroidStudioProjects\\Akira\\app\\src\\main\\res\\drawable\\icon.png");
        textMessage.setUserId("LP");
        textMessage.setSource(MessageSource.EXTERNAL_USER);
        slyceMessagingFragment.addNewMessage(textMessage);

        if (url != null) {
            textMessage = new TextMessage();
            textMessage.setText("If you want to read more about it\nGo to :- " + url);
            textMessage.setDate(new Date().getTime());
            messages.add(textMessage);
            textMessage.setUserId("LP");
            textMessage.setSource(MessageSource.EXTERNAL_USER);
            slyceMessagingFragment.addNewMessage(textMessage);
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}