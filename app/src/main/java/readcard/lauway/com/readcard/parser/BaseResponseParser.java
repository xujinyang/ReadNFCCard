package readcard.lauway.com.readcard.parser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 基础响应解析类
 * 
 * @author lwz
 * 
 * @param <T>
 *            待返回数据的 类型
 */
public class BaseResponseParser<T> extends AbsResponseParser<JSONObject, T> {

    private final String RESP_JSON_KEY_RET = "success";
    private final String RESP_JSON_KEY_ERROR_CODE = "errorcode";
    private final String RESP_JSON_KEY_MSG = "exception";
    private final String RESP_JSON_KEY_DATA = "data";

    private String mResultCode;

    public BaseResponseParser(JSONObject responseData) throws JSONException {
        super(responseData);
        mResultCode = responseData.getString(RESP_JSON_KEY_RET);
    }

    @Override
    public boolean isSucceed() throws JSONException {
        return getResultCode().equals("true") ? true : false;
    }

    @Override
    public String getData() throws JSONException {
        return ((JSONObject) responseData.get(RESP_JSON_KEY_DATA)).toString();
    }

    public String getRecords() throws JSONException {
        return ((JSONObject) responseData.get(RESP_JSON_KEY_DATA)).get("records").toString();
    }

    @Override
    public String getResultCode() throws JSONException {
        return mResultCode;
    }

    @Override
    public String getErrorMessage() throws JSONException {
        return responseData.getString(RESP_JSON_KEY_MSG);
    }

    @Override
    public String getErrorCode() throws JSONException {
        return responseData.getString(RESP_JSON_KEY_ERROR_CODE);
    }
}
