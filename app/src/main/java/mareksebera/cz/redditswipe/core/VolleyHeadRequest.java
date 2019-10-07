package mareksebera.cz.redditswipe.core;

import androidx.annotation.Nullable;

import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.List;

public abstract class VolleyHeadRequest extends Request<String> {

    public VolleyHeadRequest(String url, @Nullable Response.ErrorListener listener) {
        super(Method.HEAD, url, listener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        deliverHeaders(response.allHeaders);
        return Response.success("", HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {

    }

    public abstract void deliverHeaders(List<Header> allHeaders);
}
