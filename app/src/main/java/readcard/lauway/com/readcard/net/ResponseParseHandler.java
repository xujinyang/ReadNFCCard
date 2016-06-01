package readcard.lauway.com.readcard.net;

public class ResponseParseHandler<T> {
    /**
     * 开始请求时调用
     */
    public void onStart() {
    };

    /**
     * 缓存数据解析后调用
     * 
     * @param result
     *            得到指定类型的结果
     */
    public void onLoadCaches(T result) {
    };

    /**
     * 网络请求成功并且成功解析后调用
     * 
     * @param result
     *            得到指定类型的结果
     */
    public void onSuccess(T result) {
    };

    /**
     * 当调用服务器接口获取数据失败或解析失败，调用这个方法
     * 
     * @param error
     *            出错原因
     * @param message
     *            出错原因描述
     */
    public void onFailure(String error, String message) {
    };

    /**
     * 请求完成时调用
     */
    public void onFinished() {
    };
}
