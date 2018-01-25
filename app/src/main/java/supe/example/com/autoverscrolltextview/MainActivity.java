package supe.example.com.autoverscrolltextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private String[] StringList = {"我们", "是", "一个小测试", "效果看起来还好"};
    private AutoVerticalScrollTextView mTv;
    private boolean isRunning = true;
    private Integer mNummber = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = findViewById(R.id.tv);
        CountTime();

    }
    private void CountTime() {
//        if (mUpdateFiltrateBean.getSchoolType().equals("school")) {
        Observable
                .create(new Observable.OnSubscribe<Boolean>() {
                    @Override
                    public void call(Subscriber<? super Boolean> subscriber) {

                        while (isRunning) {
                            try {
                                Thread.sleep(3000);
                                subscriber.onNext(true);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                subscriber.onError(e);
                            }
                        }
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean && mTv != null) {
                            ++mNummber;
                            mTv.next();
                            mTv.setText(StringList[mNummber % StringList.length]);
                        }
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false ;
    }
}
