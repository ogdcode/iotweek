package tlor.iotweek;

import android.content.Intent;
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
import java.util.HashMap;

public class UsersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayList<String> list = new ArrayList<String>();
        //Get the data (see above)
        JSONArray json = Utils.getJSONfromURL("https://api.mlab.com/api/1/databases/iotweek-db/collections/cards?apiKey=gUBeJJih5LDEcH9jLV3duXYRmx-Rw7dJ");
        try{
            for(int i=0;i < json.length();i++){
                JSONObject c = json.getJSONObject(i);
                list.add(c.getString("card_name") + " (" + c.get("card_id") + ")");
            }
        }catch(JSONException e){
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        final ListView listview = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.e("Hop", listview.getItemAtPosition(position).toString());

                String s = listview.getItemAtPosition(position).toString();
                s = s.substring(s.indexOf("(") + 1);
                s = s.substring(0, s.indexOf(")"));

                Intent intent = new Intent(view.getContext(), ByPassagesActivity.class);
                intent.putExtra("order", s);
                startActivity(intent);
            }
        });
        listview.setAdapter(adapter);
    }
}
