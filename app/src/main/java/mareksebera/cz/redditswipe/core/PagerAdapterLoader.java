package mareksebera.cz.redditswipe.core;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import mareksebera.cz.redditswipe.immutables.ImmutableGeneralListing;

public class PagerAdapterLoader {

    public interface LoaderCallback {
        void success(@NonNull ImmutableGeneralListing response);

        void failure(@NonNull VolleyError error);
    }

    public void loadMore(Context ctx, String url, String after, final LoaderCallback callback) {
        url = Uri.parse(url).buildUpon().clearQuery().appendQueryParameter("after", after).build().toString();

        load(ctx, url, callback);
    }

    public void load(Context ctx, String url, final LoaderCallback callback) {
        Volley.newRequestQueue(ctx).add(new VolleyJacksonRequest<ImmutableGeneralListing>(url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        }, ImmutableGeneralListing.class) {
            @Override
            protected void deliverResponse(ImmutableGeneralListing response) {
                if (callback != null) {
                    callback.success(response);
                }
            }
        });
    }

}
