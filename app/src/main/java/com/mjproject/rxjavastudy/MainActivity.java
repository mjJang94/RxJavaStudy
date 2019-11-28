package com.mjproject.rxjavastudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.mjproject.rxjavastudy.Mock.FacticiousModel;

import java.util.function.Function;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class MainActivity extends AppCompatActivity {

    private CompositeDisposable mCompositeDisposable;
    private FacticiousModel mFacticiousModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getFakeData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mCompositeDisposable.dispose();
    }

    private void init() {
        mCompositeDisposable = new CompositeDisposable();
        mFacticiousModel = new FacticiousModel();
    }

    private void getFakeData() {

        mFacticiousModel.setName("장민종");
        mFacticiousModel.setAge(26);
        mFacticiousModel.setJob("안드로이드");

        if (mFacticiousModel != null){
            startMethod();
        }

    }

    private FacticiousModel getFakeData2() {

        mFacticiousModel.setName("장민종");
        mFacticiousModel.setAge(26);
        mFacticiousModel.setJob("안드로이드");
        mFacticiousModel.setEtc("golego");


        return mFacticiousModel;
    }

    private void startMethod() {

        mCompositeDisposable.add(
                Observable.just(mFacticiousModel)
                        .filter(data -> data.getName().equals("장민종"))
                        .map(data -> getFakeData2())
                        .take(1)
                        .subscribeWith(new DisposableObserver<FacticiousModel>() {
                            @Override
                            public void onNext(FacticiousModel facticiousModel) {

                                Log.e("Name = ", facticiousModel.getName() + "");
                                Log.e("Age = ", facticiousModel.getAge() + "");
                                Log.e("Job = ", facticiousModel.getJob() + "");
                                if (facticiousModel.getEtc() != null){
                                    Log.e("Etc = ", facticiousModel.getEtc() + "");
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {

                                System.out.println("구독 종료");
                            }
                        })
        );
    }
}
