package tlor.iotweek;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ByPassagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_passages);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        JSONArray json;

        json = Utils.getJSONfromURL("https://api.mlab.com/api/1/databases/iotweek-db/collections/attempts?apiKey=gUBeJJih5LDEcH9jLV3duXYRmx-Rw7dJ"); //&s={"timestamp": -1}

        ArrayList<String> list = new ArrayList<String>();
        //Get the data (see above)
        if(getIntent().getStringExtra("order").equals("MAIN"))
            json = Utils.getJSONfromURL("https://api.mlab.com/api/1/databases/iotweek-db/collections/attempts?apiKey=gUBeJJih5LDEcH9jLV3duXYRmx-Rw7dJ"); //&s={"timestamp": -1}
        else
            json = Utils.getJSONfromURL("https://api.mlab.com/api/1/databases/iotweek-db/collections/attempts?apiKey=gUBeJJih5LDEcH9jLV3duXYRmx-Rw7dJ&q={\"card_id\"%20:%20\"" + getIntent().getStringExtra("order") +"\"}"); //&s={"timestamp": -1}


        try{
            for(int i=0;i < json.length();i++){
                JSONObject c = json.getJSONObject(i);
                Log.e("log_tag", "" + i);
                if(c.toString().contains("card"))
                    list.add(c.getString("card_id") + " (" + new Date(c.getLong("timestamp")*1000) + ")");
                else
                    list.add(c.getString("id") + " (" + new Date(c.getLong("timestamp")*1000) + ")");
            }
        }catch(JSONException e){
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        Collections.reverse(list);

        final ListView listview = (ListView) findViewById(R.id.listViewPassages);



        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);




        listview.setAdapter(adapter);
    }
}
