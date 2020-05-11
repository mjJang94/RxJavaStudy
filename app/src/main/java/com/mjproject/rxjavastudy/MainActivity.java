package com.mjproject.rxjavastudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mjproject.rxjavastudy.Mock.MockData;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class MainActivity extends AppCompatActivity {

    private CompositeDisposable mCompositeDisposable;
    private MockData mMockData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getMockData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mCompositeDisposable.dispose();
    }

    private void init() {
        mCompositeDisposable = new CompositeDisposable();
        mMockData = new MockData();
    }


    // api 통신으로부터 응답 온 data로 가정
    private MockData getMockData() {

        mMockData.setName("jmj");
        mMockData.setAge(27);
        mMockData.setJob("developer");
        mMockData.setEtc("Hello, World!");

        // data 모델이 null 이 아니라면 전달 시작
        if (mMockData != null)
        startSubscribe();


        return mMockData;
    }

    private void startSubscribe() {

        mCompositeDisposable.add(
                Observable.just(mMockData)
                        .filter(data -> data.getName().equals("jmj"))
                        .take(1)
                        .subscribeWith(new DisposableObserver<MockData>() {
                            @Override
                            public void onNext(MockData mockData) {

                                System.out.println("Name = "+ mockData.getName());
                                System.out.println("Age = "+ mockData.getAge());
                                System.out.println("Job = "+ mockData.getJob());
                                System.out.println("Etc = "+ mockData.getEtc());
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
