package com.mka.allcountries;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.mka.allcountries.model.CountriesService;
import com.mka.allcountries.model.CountryModel;
import com.mka.allcountries.viewmodel.ListViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class ListViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    CountriesService service;

    @InjectMocks
    ListViewModel listViewModel = new ListViewModel();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    private Single<List<CountryModel>> testSingle;

    @Test
    public void getCountriesSuccess() {
        CountryModel country = new CountryModel("Bangladesh", "Dhaka", "Flag");
        List<CountryModel> countryList = new ArrayList<>();
        countryList.add(country);

        testSingle = Single.just(countryList);
        Mockito.when(service.getCountries()).thenReturn(testSingle);

        listViewModel.refresh();

        Assert.assertEquals(1, listViewModel.countries.getValue().size());
        Assert.assertEquals(false, listViewModel.countryLoadError.getValue());
        Assert.assertEquals(false, listViewModel.loading.getValue());

    }

    @Test
    public void getCountriesError() {
        testSingle = Single.error(new Throwable());
        Mockito.when(service.getCountries()).thenReturn(testSingle);

        listViewModel.refresh();
        Assert.assertEquals(true, listViewModel.countryLoadError.getValue());
        Assert.assertEquals(false, listViewModel.loading.getValue());


    }

    @Before
    public void setupScheduler() {
        Scheduler immediate = new Scheduler() {
            @NonNull
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run, true);
            }
        };
        // Testing the new thread
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        // Testing on the main thread
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);

    }


}
