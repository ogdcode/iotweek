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

public class CardCheckRequest extends AsyncTask<Void, Void, ServerResponse> {

    private Context context;
    private CardCheckRequestInterface callback;
    private String cardId;

    public CardCheckRequest(Context c, CardCheckRequestInterface cb, String cardId) {
        this.context = c;
        this.callback = cb;
        this.cardId = cardId;
    }
    @Override
    protected ServerResponse doInBackground(Void... params) {
        try {
            ServerRequest request = new ServerRequest(ServerRequest.GET, ServerRequest.API_URL + "cards");
            request.addParameter("apiKey", ServerRequest.API_KEY);
            request.addParameter("fo", "true");
            request.addParameter("q", "{\"card_id\":\"" + cardId + "\"}");
            return request.send();
        } catch (NullPointerException ignored) {}
        return null;
    }
    protected void onPostExecute(ServerResponse response) {
        try {
            if (response != null) {
                JSONObject json = new JSONObject(response.getContent());
                String cardName = json.getString("card_name");
                if (callback != null)
                    callback.onCardChecked(cardId, cardName);
            } else {
                if(callback != null)
                    callback.onCardCheckFailure();
            }
        } catch (JSONException  | NullPointerException ignored) {
            callback.onCardChecked(cardId, null);
        }
    }

    public interface CardCheckRequestInterface {
        void onCardChecked(String cardId, String cardName);
        void onCardCheckFailure();
    }
}
