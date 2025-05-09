package phos.fri.aiassistant.net;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import phos.fri.aiassistant.entity.ApiResponse;

public class RxUtils {
    /**
     * 统一处理响应：过滤 code != 0 的业务异常
     */
    public static <T> ObservableTransformer<ApiResponse<T>, T> handleResponse() {
        return upstream -> upstream.flatMap(resp -> {
            if (resp.isSuccess()) {
                return Observable.just(resp.getData());
            } else {
                // 自定义业务异常
                return Observable.error(new ApiException(resp.getCode(), resp.getMessage()));
            }
        });
    }

    /**
     * 线程切换：IO 线程订阅，主线程观察
     */
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

