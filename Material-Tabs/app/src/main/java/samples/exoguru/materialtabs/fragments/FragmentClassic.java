package samples.exoguru.materialtabs.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import samples.exoguru.materialtabs.R;
import samples.exoguru.materialtabs.adapters.RecyclerViewAdapter;
import samples.exoguru.materialtabs.handler.Constants;
import samples.exoguru.materialtabs.models.CategoryResult;
import samples.exoguru.materialtabs.models.SongStyleModel;
import samples.exoguru.materialtabs.servies.API_Classic;
import samples.exoguru.materialtabs.servies.API_POP;
import samples.exoguru.materialtabs.servies.API_Rock;
import samples.exoguru.materialtabs.servies.API_Services;

/**
 * Created by Edwin on 15/02/2015.
 */
public class FragmentClassic extends Fragment {
    API_Classic apiClassic;
    private CompositeSubscription subscription = new CompositeSubscription();

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.classic_tab,container,false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);




        recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(false);

        callData();
        return rootView;
    }
/*/
    @Override
    public void onResume() {
        super.onResume();
        subscription = RxUtils.getNewCompositeSubIfUnsubscribed(subscription);
    }

    @Override
    public void onPause() {
        super.onPause();
        RxUtils.unsubscribeIfNotNull(subscription);
    }
    /*/

    void callData(){
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Loading", "Please wait...", false, false);
        apiClassic= API_Services.createClassicAPI();
        subscription.add(apiClassic.getClassic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5000, TimeUnit.MILLISECONDS)
                .retry()
                .distinct()
                .subscribe(new Observer<CategoryResult>() {
                    @Override
                    public void onCompleted() {
                        Log.i("Retrofit", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Retrofit", "onCompleted");

                    }

                    @Override
                    public void onNext(CategoryResult categoryResult) {
                        Log.i("Retrofit", "onNext");
                        if (categoryResult.getResultCount() > 0 && recyclerView != null) {
                            recyclerViewAdapter = new RecyclerViewAdapter(categoryResult, R.layout.recycler_row, getActivity());
                            recyclerView.setAdapter(recyclerViewAdapter);
                            loading.hide();
                        }
                    }


                }));


    }
}
