package mareksebera.cz.redditswipe.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;

import mareksebera.cz.redditswipe.R;
import mareksebera.cz.redditswipe.fresco.ZoomableDraweeView;

public class ImageItemFragment extends CommonItemFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        ZoomableDraweeView drawee = v.findViewById(R.id.image_fragment_image);
        if (drawee != null) {
            drawee.setController(Fresco.newDraweeControllerBuilder()
                    .setUri(item.DATA.getUrl())
                    .setAutoPlayAnimations(true)
                    .build());
        }

        return v;
    }
}
