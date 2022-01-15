package com.mka.allcountries.model;

import com.mka.allcountries.di.DaggerApiComponent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CountriesService {

    private static CountriesService INSTANCE;

    @Inject
    public CountriesApi api;

    public CountriesService() {
        DaggerApiComponent.create().injectRetrofit(this);
    }

    // Singleton
    public static CountriesService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CountriesService();
        }
        return INSTANCE;
    }

    // Method that enables directly call from other classes without calling the interface
    public Single<List<CountryModel>> getCountries() {
        return api.getCountries();
    }


}
