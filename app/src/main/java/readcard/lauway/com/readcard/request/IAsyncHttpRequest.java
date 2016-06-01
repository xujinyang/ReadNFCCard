package readcard.lauway.com.readcard.request;

import readcard.lauway.com.readcard.net.ResponseParseHandler;

/**
 * @param <T>
 * @author MobileXu
 */
public interface IAsyncHttpRequest<T> {

    /**
     * @param prefName
     * @param cacheKey
     * @param handler
     */
    public void request(String prefName, String cacheKey, ResponseParseHandler<T> handler);
}
