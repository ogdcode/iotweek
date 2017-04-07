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
import tlor.iotweek.model.Attempt;
import tlor.iotweek.network.request.AttemptListRequest;

public class AttemptsFragment extends Fragment implements AttemptListRequest.AttemptListRequestInterface {

    private ListView listView;
    private ProgressBar loading;
    private SwipeRefreshLayout swipeContainer;
    private AttemptListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_attempts, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        adapter = new AttemptListAdapter(getActivity());
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
        AttemptListRequest request = new AttemptListRequest(getActivity(), this);
        request.execute();
    }

    @Override
    public void onAttemptListReceived(ArrayList<Attempt> attempts) {
        Log.d(getClass().getName(), "Received " + attempts.size() + " results");
        adapter.clear();
        adapter.addAll(attempts);
        listView.setAdapter(adapter);
        if(attempts.size() == 0) {
            Toast.makeText(getActivity(), R.string.warning_no_results, Toast.LENGTH_SHORT).show();
        }
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onAttemptListFailure() {
        Log.d(getClass().getName(), "Request has failed");
        Toast.makeText(getActivity(), R.string.error_fetching_attempts, Toast.LENGTH_SHORT).show();
        swipeContainer.setRefreshing(false);
    }
}
