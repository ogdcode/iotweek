package tlor.iotweek.network.request;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import tlor.iotweek.model.Attempt;
import tlor.iotweek.network.ServerRequest;
import tlor.iotweek.network.ServerResponse;

public class AttemptListRequest extends AsyncTask<Void, Void, ServerResponse> {

    private Context context;
    private AttemptListRequestInterface callback;

    public AttemptListRequest(Context c, AttemptListRequestInterface cb) {
        context = c;
        callback = cb;
    }
    @Override
    protected ServerResponse doInBackground(Void... params) {
        try {
            ServerRequest request = new ServerRequest(ServerRequest.GET, ServerRequest.API_URL + "attempts");
            request.addParameter("apiKey", ServerRequest.API_KEY);
            return request.send();
        } catch (NullPointerException ignored) {}
        return null;
    }
    protected void onPostExecute(ServerResponse response) {
        try {
            if (response != null) {
                ArrayList<Attempt> attempts = new ArrayList<>();
                JSONArray json = new JSONArray(response.getContent());
                for(int i=0; i<json.length(); i++) {
                    JSONObject entry = json.getJSONObject(i);
                    String cardId = entry.getString("card_id");
                    long timestamp = entry.getLong("timestamp");
                    boolean authorized = entry.getBoolean("authorized");
                    Attempt attempt = new Attempt(cardId, timestamp, authorized);
                    attempts.add(attempt);
                }
                if(callback != null)
                    Collections.reverse(attempts);
                    callback.onAttemptListReceived(attempts);
            } else {
                if(callback != null)
                    callback.onAttemptListFailure();
            }
        } catch (JSONException  | NullPointerException ignored) {
        }
    }

    public interface AttemptListRequestInterface {
        void onAttemptListReceived(ArrayList<Attempt> attempts);
        void onAttemptListFailure();
    }
}
