package com.example.emotisense.emotisense.cameraLibrary;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.example.emotisense.emotisense.Fbinfo;
import com.example.emotisense.emotisense.UserProfile;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.InputMismatchException;
import java.util.List;


/**
 * Created by joseph on 3/29/15.
 */
public class TakePicture extends Activity implements OnPictureTaken {
    private String TAG = "TakePicture";
    private CustomCamera mCustomCamera;
    private String url = "http://trinity.cc.gt.atl.ga.us/emotisense/apps/addphoto.php";
    private static String userName = "10206242692930240";

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        D("Photo time, smile XD");
        mCustomCamera = new CustomCamera(TakePicture.this);
        mCustomCamera.setPictureTakenListner(this);
        //To start the front camera use this
        mCustomCamera.startCameraFront();
    }

    @Override
    public void pictureTaken(Bitmap bitmap, File file) {
        D("Let's take a picture XD");

        File f = new File(getApplicationContext().getCacheDir(), "tImage.png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String ret = postPage(url, f, true);
	long end = System.currentTimeMillis();
        D("Returned " + ret);
	Log.d("PERF", "End: " + end);
        finish();
    }

    public String postPage(String url, File data, boolean returnAddr) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;

        //D("User id " + UserProfile.getUserName());

        httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.RFC_2109);
        builder.addBinaryBody("photo", data);
        //builder.addTextBody("user", );
        builder.addTextBody("user", Fbinfo.getInstance().mSelfUID);
    //builder.addTextBody("user", "10152831267363095");
        System.out.println(Fbinfo.getInstance().mSelfUID);
//    builder.addTextBody("user", UserProfile.getUserName());
        httpPost.setEntity(builder.build());

        try {
            response = httpClient.execute(httpPost,localContext);
            D("Returned " + inputToString(response.getEntity().getContent()));
        } catch (ClientProtocolException e) {
            System.out.println("HTTPHelp : ClientProtocolException : "+e);
        } catch (IOException e) {
            System.out.println("HTTPHelp : IOException : "+e);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response.getStatusLine().toString();
    }

    public String inputToString(InputStream in) throws Exception{
        InputStreamReader is = new InputStreamReader(in);
        StringBuilder sb=new StringBuilder();
        BufferedReader br = new BufferedReader(is);
        String read = br.readLine();
        while(read != null) {
            sb.append(read);
            read = br.readLine();
        }
        return sb.toString();
    }


    public void D(String log) {
        Log.d(TAG, log);
    }
}
