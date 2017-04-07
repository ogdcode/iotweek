package tlor.iotweek.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import tlor.iotweek.R;
import tlor.iotweek.adapter.AttemptListAdapter;
import tlor.iotweek.adapter.CardListAdapter;
import tlor.iotweek.model.Attempt;
import tlor.iotweek.model.Card;
import tlor.iotweek.network.request.AttemptListRequest;
import tlor.iotweek.network.request.CardListRequest;

public class CardsFragment extends Fragment implements CardListRequest.CardListRequestnterface {

    private ListView listView;
    private ProgressBar loading;
    private SwipeRefreshLayout swipeContainer;
    private CardListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_cards, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        adapter = new CardListAdapter(getActivity());
        swipeContainer.setRefreshing(true);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });
        fetchData();
        return rootView;
    }

    private void fetchData() {
        Log.d(getClass().getName(), "Fetching data...");
        CardListRequest request = new CardListRequest(getActivity(), this);
        request.execute();
    }

    @Override
    public void onCardListReceived(ArrayList<Card> cards) {
        Log.d(getClass().getName(), "Received " + cards.size() + " results");
        adapter.clear();
        adapter.addAll(cards);
        listView.setAdapter(adapter);
        if(cards.size() == 0) {
            Toast.makeText(getActivity(), R.string.warning_no_results, Toast.LENGTH_SHORT).show();
        }
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onCardListFailure() {
        Log.d(getClass().getName(), "Request has failed");
        Toast.makeText(getActivity(), R.string.error_fetching_cards, Toast.LENGTH_SHORT).show();
        swipeContainer.setRefreshing(false);
    }
}
