package tlor.iotweek.network.request;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tlor.iotweek.model.Card;
import tlor.iotweek.network.ServerRequest;
import tlor.iotweek.network.ServerResponse;

public class CardListRequest extends AsyncTask<Void, Void, ServerResponse> {

    private Context context;
    private CardListRequestnterface callback;

    public CardListRequest(Context c, CardListRequestnterface cb) {
        context = c;
        callback = cb;
    }
    @Override
    protected ServerResponse doInBackground(Void... params) {
        try {
            ServerRequest request = new ServerRequest(ServerRequest.GET, ServerRequest.API_URL + "cards");
            request.addParameter("apiKey", ServerRequest.API_KEY);
            return request.send();
        } catch (NullPointerException ignored) {}
        return null;
    }
    protected void onPostExecute(ServerResponse response) {
        try {
            if (response != null) {
                ArrayList<Card> cards = new ArrayList<>();
                JSONArray json = new JSONArray(response.getContent());
                for(int i=0; i<json.length(); i++) {
                    JSONObject entry = json.getJSONObject(i);
                    String cardId = entry.getString("card_id");
                    String cardName = entry.getString("card_name");
                    Card card = new Card(cardId, cardName);
                    cards.add(card);
                }
                if(callback != null)
                    callback.onCardListReceived(cards);
            } else {
                if(callback != null)
                    callback.onCardListFailure();
            }
        } catch (JSONException  | NullPointerException ignored) {
        }
    }

    public interface CardListRequestnterface {
        void onCardListReceived(ArrayList<Card> cards);
        void onCardListFailure();
    }
}
