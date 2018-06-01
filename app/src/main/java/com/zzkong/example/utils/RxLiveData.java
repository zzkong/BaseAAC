package com.zzkong.example.utils;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zzkong.example.BuildConfig;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.AtomicReference;


/**
 * Created by Kilnn on 2017/12/28.
 */

public class RxLiveData<T> {

    private static final String TAG = "RxLiveData";

    public static class State {
        public final Throwable throwable;

        private State(Throwable throwable) {
            this.throwable = throwable;
        }

        public boolean isStart() {
            return this == START;
        }

        public boolean isComplete() {
            return this == COMPLETE;
        }

        public boolean isCancel() {
            return this == CANCEL;
        }

        public boolean isError() {
            return this.throwable != null;
        }
    }

    public abstract static class StateObserver implements Observer<State> {
        protected RxLiveData rxLiveData;
    }

    private static final State START = new State(null);
    private static final State COMPLETE = new State(null);
    private static final State CANCEL = new State(null);

    private final MutableLiveData<T> mData;
    /**
     * Null for reset,
     * {@link #START} for start,
     * {@link #COMPLETE} for complete,
     * {@link #CANCEL} for cancel,
     * Others for error
     */
    private final MutableLiveData<State> mState;//State ,true for start ,false for completed,null for reset

    private final AtomicReference<LiveDataSubscriber> mSubscriber;

//    private boolean mStrictMainThread = true;

    /**
     * 创建RxLiveData
     */
    public RxLiveData() {
        mData = new MutableLiveData<>();
        mState = new MutableLiveData<>();
        mSubscriber = new AtomicReference<>();
    }

//    /**
//     * Publisher的Observe都在main线程回调
//     *
//     * @param strict
//     */
//    public void setStrictMainThread(boolean strict) {
//        mStrictMainThread = strict;
//    }

    /**
     * 需要注意的是，一个RxLiveData最好只执行一种类型的操作，如只执行登录操作，或只执行注册操作。
     *
     * @param publisher
     * @param force     是否强制执行，一般情况下，如果是界面自动加载，一般使用false，如果是主动主动请求，一般为true
     */
    public void execute(@NonNull Publisher<T> publisher, boolean force) {
        if (force) {
            cancel();
        } else {
            if (mSubscriber.get() != null
                    || mData.getValue() != null) return;
        }
        LiveDataSubscriber s = new LiveDataSubscriber();
        mSubscriber.set(s);
        publisher.subscribe(s);
    }

    public void cancel() {
        LiveDataSubscriber s = mSubscriber.getAndSet(null);
        if (s != null) {
            s.cancelSubscription();
        }
    }

    final class LiveDataSubscriber extends AtomicReference<Subscription>
            implements Subscriber<T> {

        @Override
        public void onSubscribe(Subscription s) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onSubscribe");
                MainThreadAssert.assertMainThread();
            }
            if (compareAndSet(null, s)) {
                s.request(Long.MAX_VALUE);
                setState(START);
            } else {
                s.cancel();
                setState(CANCEL);
            }
        }

        @Override
        public void onNext(T item) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onNext");
                MainThreadAssert.assertMainThread();
            }
            setData(item);
        }

        @Override
        public void onError(final Throwable ex) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onError");
                MainThreadAssert.assertMainThread();
            }
            mSubscriber.compareAndSet(this, null);
            ex.printStackTrace();
            setState(new State(ex));
        }

        @Override
        public void onComplete() {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onComplete");
                MainThreadAssert.assertMainThread();
            }
            mSubscriber.compareAndSet(this, null);
            setState(COMPLETE);
        }

        public void cancelSubscription() {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "cancelSubscription");
                MainThreadAssert.assertMainThread();
            }
            Subscription s = get();
            if (s != null) {
                s.cancel();
                setState(CANCEL);
            }
        }
    }

    public void observeData(@NonNull LifecycleOwner owner, @NonNull final Observer<T> observer) {
        mData.observe(owner, observer);
    }

    public void observeState(@NonNull LifecycleOwner owner, @NonNull final Observer<State> observer) {
        autoInjectStateObserver(observer);
        mState.observe(owner, observer);
    }

    public void observeDataForever(@NonNull final Observer<T> observer) {
        mData.observeForever(observer);
    }

    public void observeStateForever(@NonNull final Observer<State> observer) {
        autoInjectStateObserver(observer);
        mState.observeForever(observer);
    }

    public void removeDataObservers(@NonNull final LifecycleOwner owner) {
        mData.removeObservers(owner);
    }

    public void removeStateObservers(@NonNull final LifecycleOwner owner) {
        mState.removeObservers(owner);
    }

    public void removeDataObserver(@NonNull final Observer<T> observer) {
        mData.removeObserver(observer);
    }

    public void removeStateObserver(@NonNull final Observer<State> observer) {
        mState.removeObserver(observer);
    }

    private void autoInjectStateObserver(@NonNull final Observer<State> observer) {
        if (observer instanceof StateObserver) {
            if (((StateObserver) observer).rxLiveData != null) {
                throw new IllegalStateException("不允许重复使用StateObserver");
            }
            ((StateObserver) observer).rxLiveData = this;
        }
    }

    private void setData(@Nullable T data) {
        if (MainThreadAssert.isMainThread()) {
            mData.setValue(data);
        } else {
            mData.postValue(data);
        }
    }

    private void setState(@Nullable State state) {
        if (MainThreadAssert.isMainThread()) {
            mState.setValue(state);
        } else {
            mState.postValue(state);
        }
    }

    public void clearData() {
        setData(null);
    }

    public void clearError() {
        State state = mState.getValue();
        if (state != null && state.isError()) {
            setState(null);
        }
    }

}
