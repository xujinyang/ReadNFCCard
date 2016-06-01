package readcard.lauway.com.readcard.net;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;


/**
 * 异步 HTTP 请求响应类
 * <p/>
 * <p>
 * 所以有异步 HTTP 请求响应类都要继承该类
 * </p>
 *
 * @author lwz
 */
public class AsyncHttpResponse extends AsyncHttpResponseHandler {
    /**
     * 自定义异步加载请求类
     */
    private LoadDataHandler mHandler;
    private Context mContext;
    private String mPrefName;
    private String mCacheDataKey;
    private boolean mIsCache;// 是否需要缓存

    /**
     * 如果不需要缓存，建议使用
     *
     * @param context      上下文对象
     * @param prefName     preferences 的名字
     * @param cacheDataKey 缓存数据在 preferences 中的键；可为 null，表示该响应数据不需要存储在本地缓存
     * @param handler      加载数据句柄对象
     */
    public AsyncHttpResponse(Context context, String prefName, String cacheDataKey, LoadDataHandler handler) {

        mHandler = handler;
        mContext = context;
        mPrefName = prefName;
        mCacheDataKey = cacheDataKey;
        mIsCache = (mPrefName != null && mCacheDataKey != null);
    }

    /**
     * @param context 上下文对象
     * @param handler 加载数据句柄对象
     */
    public AsyncHttpResponse(Context context, LoadDataHandler handler) {
        this(context, null, null, handler);
    }

    @Override
    public void onStart() {
        mHandler.onStart();
        // 先从缓存中加载数据(如果有的话)
        if (mIsCache) {
            String cacheData = getDataFromCache(mCacheDataKey);
            if (cacheData != null) {
                mHandler.onLoadCaches(cacheData);
            }
        }
    }

    // 请求成功
    @Override
    public void onSuccess(String data) {
        if (data != null) {
            if (mIsCache) {
                storeDataToCache(mCacheDataKey, data);
            }
            mHandler.onSuccess(data);
        }
    }

    /**
     * 解析数据方法
     *
     * @return 解析好的字符串对象，若返回 null， 会调用响应出错方法
     */
    // public abstract String parserData(String data);
    @Override
    public void onFailure(Throwable arg0, String message) {
        onFailure(arg0.getMessage(), message);
    }

    /**
     * 响应结果出错
     *
     * @param error
     * @param message
     */
    public void onFailure(String error, String message) {
        mHandler.onFailure(error, message);
    }

    @Override
    public void onFinish() {
        mHandler.onFinished();
    }

    /**
     * 存储数据到本地缓存
     *
     * @param key   键
     * @param value 值
     */
    private void storeDataToCache(String key, String value) {
    }

    /**
     * 得到本地缓存数据中
     *
     * @param key 键
     * @return 返回值
     */
    private String getDataFromCache(String key) {
        return "";
    }
}
