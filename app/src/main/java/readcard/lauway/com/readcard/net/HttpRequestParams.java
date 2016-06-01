package readcard.lauway.com.readcard.net;

import java.util.HashMap;

import readcard.lauway.com.readcard.request.JsonUtil;


/**
 * HTTP 请求的参数
 * 
 * <p>继承了  HashMap&lt;String, Object&gt;</p>
 * <p>复写了 toString() 方法， 返回JSON</p>
 * @author lwz
 *
 */
public class HttpRequestParams extends HashMap<String, Object> {

	private static final long serialVersionUID = -3736319677853216129L;

	@Override
	public String toString() {
		return JsonUtil.map2JsonObj(this);
	}
}
