package org.izv.ad.psp1920retrofit.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.izv.ad.psp1920retrofit.model.data.Type;
import org.izv.ad.psp1920retrofit.model.rest.PokemonClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.izv.ad.psp1920retrofit.MainActivity.TAG;

public class Repository {

    private PokemonClient apiClient;

    private String url = "34.205.16.112";

    private List<Type> typeList = new ArrayList<>();
    private MutableLiveData<List<Type>> liveTypeList = new MutableLiveData();

    public Repository() {
       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + url + "/web/psp/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiClient = retrofit.create(PokemonClient.class);*/
       retrieveApiClient(url);
        fetchTypeList();
    }

    public void add(Type type) {
        Call<Long> call = apiClient.postType(type);
        call.enqueue(new Callback<Long>() {

            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                Log.v(TAG, response.body().toString());
                long resultado = response.body();
                if(resultado > 0) {
                    fetchTypeList();
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());

            }
        });
    }

    private void retrieveApiClient(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + url + "/web/psp/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiClient = retrofit.create(PokemonClient.class);
    }

    public void fetchTypeList() {
        Call<ArrayList<Type>> call = apiClient.getTypes();
        call.enqueue(new Callback<ArrayList<Type>>() {

            @Override
            public void onResponse(Call<ArrayList<Type>> call, Response<ArrayList<Type>> response) {
                Log.v(TAG, response.body().toString());
                typeList = response.body();
                liveTypeList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Type>> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
                typeList = new ArrayList<>();
            }
        });
    }

    public List<Type> getTypeList() {
        return typeList;
    }

    public LiveData<List<Type>> getLiveTypeList() {
        return liveTypeList;
    }

    public void setUrl(String url) {
        this.url = url;
        retrieveApiClient(url);
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + url + "/web/psp/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiClient = retrofit.create(PokemonClient.class);*/
    }
}