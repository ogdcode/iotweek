package tlor.iotweek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button byUserButton = (Button) findViewById(R.id.buttonByUser);
        Button byPassagesButton = (Button) findViewById(R.id.buttonLastPassages);
        byPassagesButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ByPassagesActivity.class);
                intent.putExtra("order", "MAIN");
                startActivity(intent);
            }
        });
        byUserButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), UsersActivity.class);
                startActivity(intent);

            }
        });



    }



}
