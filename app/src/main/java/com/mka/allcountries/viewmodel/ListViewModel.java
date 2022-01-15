package com.mka.allcountries.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mka.allcountries.di.DaggerApiComponent;
import com.mka.allcountries.model.CountriesService;
import com.mka.allcountries.model.CountryModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {
    public MutableLiveData<List<CountryModel>> countries = new MutableLiveData<>();
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public void refresh() {
        fetchCountries();
    }

    @Inject
    public CountriesService service;

    public ListViewModel() {
        super();
        DaggerApiComponent.create().injectInstance(this);
    }

    private CompositeDisposable disposable = new CompositeDisposable();

    private void fetchCountries() {
        loading.setValue(true);

        disposable.add(service.getCountries()
                // allowing a brand new thread for task
                .subscribeOn(Schedulers.newThread())
                // ensuring the result gets shown on main thread
                .observeOn(AndroidSchedulers.mainThread())
                // making sure what is the actual disposable
                .subscribeWith(new DisposableSingleObserver<List<CountryModel>>() {

                    @Override
                    public void onSuccess(@NonNull List<CountryModel> countryModels) {
                        countries.setValue(countryModels);
                        loading.setValue(false);
                        countryLoadError.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        countryLoadError.setValue(true);
                        loading.setValue(false);
                        e.printStackTrace();
                    }
                })
        );

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
