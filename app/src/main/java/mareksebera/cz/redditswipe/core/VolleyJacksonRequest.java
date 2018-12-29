package mareksebera.cz.redditswipe.core;

import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public abstract class VolleyJacksonRequest<T> extends Request<T> {

    private Class<T> targetClass;

    public VolleyJacksonRequest(String url, @Nullable Response.ErrorListener listener, Class<T> tClass) {
        super(Method.GET, url, listener);
        targetClass = tClass;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        ObjectMapper oMapper = new ObjectMapper();
        oMapper.enable(JsonParser.Feature.IGNORE_UNDEFINED);
        oMapper.enable(JsonParser.Feature.ALLOW_MISSING_VALUES);
        oMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return Response.success(oMapper.readValue(response.data, targetClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (IOException e) {
            Log.e("VJRT", "parse", e);
            return Response.error(new NetworkError(e));
        }
    }
}
