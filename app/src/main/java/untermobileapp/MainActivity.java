package untermobileapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import untermobileapp.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);





        Button login_driver=(Button) findViewById(R.id.logindriver_button);

        login_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Intent intent= new Intent(view.getContext(),driver_login.class);
              startActivityForResult(intent,0);
              //  Toast.makeText(MainActivity.this, "Please enter in all required fields.",Toast.LENGTH_LONG).show();


            }
        });

        Button login_rider=(Button) findViewById(R.id.loginrider_button);

        login_rider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //       Intent intent=new Intent(view.getContext(),DriverActivity.class);
                //  //   startActivityForResult(intent,0);


            }
        });

        Button signup_rider=(Button) findViewById(R.id.button);

        signup_rider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //       Intent intent=new Intent(view.getContext(),DriverActivity.class);
                //  //   startActivityForResult(intent,0);


            }
        });


        Button signup_driver=(Button) findViewById(R.id.signupasdriver_button);

        signup_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(view.getContext(), driver_signup.class);
                startActivityForResult(intent,0);
            }
        });






















    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
