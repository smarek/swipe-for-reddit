package mareksebera.cz.redditswipe.core;

import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.IOException;
import java.util.List;

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
        oMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        oMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            return Response.success(oMapper.readValue(response.data, targetClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (MismatchedInputException e) {
            try {
                List list = oMapper.readValue(response.data, oMapper.getTypeFactory().constructCollectionType(List.class, targetClass));
                return Response.success(targetClass.cast(list.get(0)), HttpHeaderParser.parseCacheHeaders(response));
            } catch (IOException ee) {
                Log.e("VolleyJacksonRequest<T>", "MismatchedInputException", e);
                Log.e("VolleyJacksonRequest<T>", "List of requested objects failed too", ee);
                return Response.error(new NetworkError(ee));
            }
        } catch (IOException e) {
            return Response.error(new NetworkError(e));
        }
    }
}
