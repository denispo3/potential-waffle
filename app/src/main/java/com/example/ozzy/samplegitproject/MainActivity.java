package com.example.ozzy.samplegitproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Single;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    private CompositeSubscription compositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        compositeSubscription = new CompositeSubscription();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final Observable<NasaModel> nasaModelObservable = getLoadDataObserver()
                .cache();
        assert fab != null;
        fab.setOnClickListener(v -> compositeSubscription.add(nasaModelObservable.subscribe(new LoadDataSubscriber())));
    }

    @RxLogSubscriber
    private class LoadDataSubscriber extends Subscriber<NasaModel> {
        @Override
        public void onCompleted() {
            //Log.d("denis", "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            //Log.d("denis", "onError " + e.toString());
        }

        @Override
        public void onNext(NasaModel s) {
            //Log.d("denis", "onNext: " + s);
        }
    }

    @RxLogObservable
    private Observable<NasaModel> getLoadDataObserver() {
        return getApiService().loadData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }

    private APIService getApiService() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.nasa.gov/planetary/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService.class);
    }

    interface APIService {
        @GET("apod?api_key=NNKOjkoul8n1CH18TWA9gwngW1s1SmjESPjNoUFo")
        Observable<NasaModel> loadData();
    }
}
