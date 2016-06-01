package readcard.lauway.com.readcard.request;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import me.ele.omniknight.common.tools.AppLogger;
import me.ele.omniknight.common.tools.ToastUtil;
import me.ele.omniknight.http.json.JsonMapper;
import readcard.lauway.com.readcard.net.AsyncHttpResponse;
import readcard.lauway.com.readcard.net.HttpRequestParams;
import readcard.lauway.com.readcard.net.LoadDataHandler;
import readcard.lauway.com.readcard.net.RequestClient;
import readcard.lauway.com.readcard.net.ResponseParseHandler;
import readcard.lauway.com.readcard.parser.BaseResponseParser;

/**
 * @param <E>
 * @param <T>
 * @author MobileXu
 */
public abstract class AbsAsyncHttpRequest<E, T> implements IAsyncHttpRequest<T> {

    public static final String JSON = "json";
    private Context mContext;
    private HttpRequestParams mParams;
    private ResponseParseHandler<T> mHandler;
    private String mPrefName;
    private String mCacheKey;

    public AbsAsyncHttpRequest(Context context) {
        mContext = context;
    }

    public void setParams(HttpRequestParams params) {
        mParams = params;
    }

    public abstract String getAPI();

    public abstract HttpMethod getHttpMethod();

    public void request(final ResponseParseHandler<T> handler) {
        request(null, null, handler);
    }

    protected Context getContext() {
        return mContext;
    }

    @Override
    public void request(String prefName, String cacheKey, final ResponseParseHandler<T> handler) {
        mPrefName = prefName;
        mCacheKey = cacheKey;
        mHandler = handler;
        if (getHttpMethod() == HttpMethod.GET) {
            doGet();
        } else {
            doPost();
        }
    }

    private void doGet() {
        if (isParamsEmpty(mParams)) {
            RequestClient.get(getURL(), mResponse);
        } else {
            RequestClient.get(getUrlWithParams(getURL(), mParams), mResponse);
        }
    }

    private void doPost() {
        if (isParamsEmpty(mParams)) {
            RequestClient.post(getURL(), mResponse);
        } else {
            RequestClient.post(getURL(), getPostJson(mParams), mResponse);
        }
    }

    private String getURL() {
        return getAPI();
    }

    private String getPostJson(HttpRequestParams mParams2) {
        try {
            JSONObject jsonObject = new JSONObject();
            Set<String> keySet = mParams2.keySet();
            for (String key : keySet) {
                jsonObject.put(key, mParams2.get(key));
            }
            AppLogger.e(getURL() + "-->post Json" + jsonObject.toString());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isParamsEmpty(HttpRequestParams params) {
        return params == null || params.isEmpty();
    }

    private String getUrlWithParams(String url, HttpRequestParams params) {
        Set<String> keySet = params.keySet();
        StringBuffer sb = new StringBuffer();
        for (String key : keySet) {
            sb.append("/").append(key + "=" + params.get(key));
        }
        Log.i("Get——Url", url + sb.toString());
        return url + sb.toString();
    }

    private AsyncHttpResponse mResponse = new AsyncHttpResponse(mContext, mPrefName, mCacheKey, new LoadDataHandler() {

        @Override
        public void onStart() {
            AppLogger.e("onRequestStart");
            mHandler.onStart();
        }

        @Override
        public void onLoadCaches(String data) {
            AppLogger.e("onLoadCaches");
            mHandler.onLoadCaches(parse(data));
        }

        @Override
        public void onSuccess(String data) {
            AppLogger.e("获得Json数据" + data);
            mHandler.onSuccess(parse(data));
        }

        @Override
        public void onFinished() {
            AppLogger.e("onFinished");
            mHandler.onFinished();
        }

        @Override
        public void onFailure(String error, String message) {
            AppLogger.e(error + "   " + message);
            mHandler.onFailure(error, message);
        }

    });

    private T parse(String responseData) {
        if (isNeedNativeData()) {
            try {
                return parseEntity(responseData);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        JSONObject jsonData;
        try {
            jsonData = new JSONObject(responseData);
            BaseResponseParser<String> brp = new BaseResponseParser<String>(jsonData);
            if (brp.isSucceed()) {
                if (isNeedData()) {
                    return parseEntity(brp.getData());
                } else {
                    return parseEntity(brp.getRecords());
                }
            } else {
                ToastUtil.showToastLong(mContext, brp.getErrorMessage());
                mHandler.onFailure(brp.getErrorCode(), brp.getErrorMessage());
            }
        } catch (JSONException e) {
            mHandler.onFailure("-1", e.getMessage());
        }
        return null;
    }


    protected void sendFailureMsg(String msg) {
        mHandler.onFailure("-2", msg);
    }


    @SuppressWarnings("unchecked")
    protected T getJsonObject(String parseData, Class<T> class1) {
        return (T) JsonMapper.toObject(parseData, class1);
    }

    @SuppressWarnings("unchecked")
    protected T getJsonObjects(String parseData, Class<T> class1) {
        return (T) JsonMapper.toObjects(parseData, class1);
    }

    // 是否获取data里面的数据，默认返回record里面的数据
    protected abstract boolean isNeedData();

    //是否获取最原始的数据，用于返回true，false
    protected boolean isNeedNativeData() {
        return false;
    }

    // 是否需要token
    protected abstract boolean isNeedToken();

    protected abstract T parseEntity(String parseData) throws JSONException;
}
