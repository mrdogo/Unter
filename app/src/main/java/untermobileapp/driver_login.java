package untermobileapp;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import untermobileapp.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class driver_login extends Activity {


    private static final String SERVICE_URL = "http://cs6387-unterapp.rhcloud.com/webapi/login/driver/";

    private static final String TAG = "AndroidRESTClientActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);





        Button login_driver=(Button) findViewById(R.id.dlogin_login);

        login_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // get controls


                EditText drv_username = (EditText) findViewById(R.id.dlogin_username);

                EditText drv_password = (EditText) findViewById(R.id.dlogin_password);


                String drv_username_string = drv_username.getText().toString();
                String drv_password_string = drv_password.getText().toString();


                if (drv_username_string == null || drv_username_string.isEmpty() ||

                        drv_password_string == null || drv_password_string.isEmpty()
                        )


                {


                    // Toast.makeText(driver_login.this, "Please enter in all required fields.", Toast.LENGTH_LONG).show();
                } else {

                    WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, driver_login.this, "Posting data...");
                    wst.execute(new String[] { SERVICE_URL });


                }


            }
        });



    }


    public void handleResponse(String response) {}

    private class WebServiceTask extends AsyncTask<String, Integer, String> {

        public static final int POST_TASK = 1;
        public static final int GET_TASK = 2;

        private static final String TAG = "WebServiceTask";

        // connection timeout, in milliseconds (waiting to connect)
        private static final int CONN_TIMEOUT = 3000;

        // socket timeout, in milliseconds (waiting for data)
        private static final int SOCKET_TIMEOUT = 5000;

        // private int taskType = GET_TASK;

        private int taskType = POST_TASK;
        private Context mContext = null;
        private String processMessage = "Processing...";

        // private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

        private ProgressDialog pDlg = null;

        public WebServiceTask(int taskType, Context mContext, String processMessage) {

            this.taskType = taskType;
            this.mContext = mContext;
            this.processMessage = processMessage;
        }


        private void showProgressDialog() {

            pDlg = new ProgressDialog(mContext);
            pDlg.setMessage(processMessage);
            pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDlg.setCancelable(false);
            pDlg.show();

        }

        @Override
        protected void onPreExecute() {
            Log.w("app", "onPreExecute\n");
            //   hideKeyboard();
            showProgressDialog();

        }

        @Override
        protected String doInBackground(String... urls) {
            Log.w("app", "doInBackground\n");
            String url = urls[0];
            String result = "";

            HttpResponse response = doResponse(url);

            if (response == null) {
                //   // Toast.makeText(driver_login.this, "Wrong Input: Please Check your input !", Toast.LENGTH_SHORT).show();

                return result;
            } else {

                try {

                    result = inputStreamToString(response.getEntity().getContent());


                    Log.w("app", "imhere\n");
                    if (result.indexOf("rror") != -1){

                        Log.w("app", "Wrong Input: Please Check your input !\n");
                        Log.w("app", result);
                        // Toast.makeText(driver_login.this, "Wrong Input: Please Check your input !", Toast.LENGTH_SHORT).show();
                        // // Toast.makeText(driver_login.this, "Button Clicked", Toast.LENGTH_SHORT).show();

                    }else {  Log.w("app", "go to the next screen\n");

                         Intent intent = new Intent(mContext,  driver_status.class);
                          startActivity(intent);


                    }


                    Log.w("app", "continue\n");

                } catch (IllegalStateException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);

                } catch (IOException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);
                }

            }

            return result;
        }

        @Override
        protected void onPostExecute(String response) {
            Log.w("app", "onPostExecute\n");
            //  handleResponse(response);
            pDlg.dismiss();

        }

        // Establish connection and socket (data retrieval) timeouts
        private HttpParams getHttpParams() {
            Log.w("app", "getHttpParams\n");
            HttpParams htpp = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
            HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

            return htpp;
        }

        private HttpResponse doResponse(String url) {
            //


            // DefaultHttpClient
            HttpClient httpclient = new DefaultHttpClient(getHttpParams());


            HttpResponse response = null;

            try {
                switch (taskType) {

                    case POST_TASK:
                        HttpPost httppost = new HttpPost(url);
                        Log.w("app", "enter_post\n");
                        //  Log.w("app", params.toString());
                        // Add parameters

                        //   httppost.setEntity(new UrlEncodedFormEntity(params));

                        JSONObject jsonObj = new JSONObject();
                        //  jsonObj.put("driverId",               "7"   );
                        jsonObj.put("username",               "Sufiffs1"   );
                        jsonObj.put("password",               "Sufiffs1"    );

                        Log.w("app", jsonObj.toString());

// Create the POST object and add the parameters

                        StringEntity entity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
                        entity.setContentType("application/json");
                        httppost.setEntity(entity);


                        response = httpclient.execute(httppost);

                        break;
                    case GET_TASK:
                        Log.w("app", "doResponse2\n");
                        HttpGet httpget = new HttpGet(url);
                        response = httpclient.execute(httpget);
                        break;
                }
            } catch (Exception e) {

                // Log.e(TAG, e.getLocalizedMessage(), e);
                // Toast.makeText(driver_login.this, "Duplication: Please Check your input !", Toast.LENGTH_SHORT).show();
            }

            return response;
        }

        private String inputStreamToString(InputStream is) {
            Log.w("app", "inputStreamToString\n");
            String line = "";
            StringBuilder total = new StringBuilder();

            // Wrap a BufferedReader around the InputStream
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                // Read response until the end
                while ((line = rd.readLine()) != null) {
                    total.append(line);
                    Log.w("app", line);
                }
            } catch (IOException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
            }

            // Return full string
            return total.toString();
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.menu.menu_rider_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
