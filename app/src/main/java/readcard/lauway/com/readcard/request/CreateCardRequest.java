package readcard.lauway.com.readcard.request;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import readcard.lauway.com.readcard.net.API;


public class CreateCardRequest extends AbsAsyncHttpRequest<JSONArray, Boolean> {
    public static String KEY_PARAM_JSON = "json";

    public CreateCardRequest(Context context) {
        super(context);
    }

    @Override
    public String getAPI() {
        return API.BASE_URL;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    protected Boolean parseEntity(String parseData) {
        Log.i("取消绑定卡", parseData);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(parseData);
            boolean success = jsonObject.getBoolean("success");
            if (success) {
                return true;
            } else {
                sendFailureMsg(jsonObject.getString("exception"));
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    protected boolean isNeedNativeData() {
        return true;
    }

    @Override
    protected boolean isNeedData() {
        return true;
    }

    @Override
    protected boolean isNeedToken() {
        return true;
    }

}
