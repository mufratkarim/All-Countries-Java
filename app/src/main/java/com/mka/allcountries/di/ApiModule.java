package com.mka.allcountries.di;

import com.mka.allcountries.model.CountriesApi;
import com.mka.allcountries.model.CountriesService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    public static final String BASE_URL = "https://raw.githubusercontent.com/";

    // using DAGGER and the interface to build retrofit
    @Provides
    public CountriesApi providesCountriesApi() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                // converting the data as the country model
                .addConverterFactory(GsonConverterFactory.create())
                // rxjava to convert the data to single
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                // creating from the interface class
                .create(CountriesApi.class);
    }

    @Provides
    public CountriesService providesCountriesServiceForViewModel() {
        return CountriesService.getInstance();
    }
}
