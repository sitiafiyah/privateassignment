package id.sch.smktelkom_mlg.privateassignment.xirpl512.movietime;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;

import id.sch.smktelkom_mlg.privateassignment.xirpl512.movietime.adapter.TopRatedAdapter;
import id.sch.smktelkom_mlg.privateassignment.xirpl512.movietime.model.Result;
import id.sch.smktelkom_mlg.privateassignment.xirpl512.movietime.model.SourcesResponse;
import id.sch.smktelkom_mlg.privateassignment.xirpl512.movietime.service.GsonGetRequest;
import id.sch.smktelkom_mlg.privateassignment.xirpl512.movietime.service.VolleySingleton;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopRateFragment extends Fragment {

    ArrayList<Result> mList = new ArrayList<>();
    TopRatedAdapter mAdapter;

    public TopRateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_rate, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.toprecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new TopRatedAdapter(this.getActivity(), mList);
        recyclerView.setAdapter(mAdapter);

        downloadDataTop();
    }

    private void downloadDataTop() {
        String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=b2ea1cf1dc03305e403107d496aae9d1";

        GsonGetRequest<SourcesResponse> myRequest = new GsonGetRequest<SourcesResponse>
                (url, SourcesResponse.class, null, new Response.Listener<SourcesResponse>() {

                    @Override
                    public void onResponse(SourcesResponse response) {
                        Log.d("FLOW", "onResponse: " + (new Gson().toJson(response)));

                        mList.addAll(response.results);
                        mAdapter.notifyDataSetChanged();
                    }


                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("FLOW", "onErrorResponse: ", error);
                    }
                });
        VolleySingleton.getInstance(this.getActivity()).addToRequestQueue(myRequest);
    }
}
