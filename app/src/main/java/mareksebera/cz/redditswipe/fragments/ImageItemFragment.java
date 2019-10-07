package mareksebera.cz.redditswipe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;

import mareksebera.cz.redditswipe.R;
import mareksebera.cz.redditswipe.fresco.DefaultZoomableController;
import mareksebera.cz.redditswipe.fresco.ZoomableController;
import mareksebera.cz.redditswipe.fresco.ZoomableDraweeView;

public class ImageItemFragment extends CommonItemFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        assert v != null;
        ZoomableDraweeView drawee = v.findViewById(R.id.image_fragment_image);
        if (drawee != null) {
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setTapToRetryEnabled(true)
                    .setUri(item.DATA.getUrl())
                    .setAutoPlayAnimations(true)
                    .build();
            GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
                    .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                    .setProgressBarImage(new ProgressBarDrawable())
                    .build();
            ZoomableController zoomable = DefaultZoomableController.newInstance();

            drawee.setController(controller);
            drawee.setHierarchy(hierarchy);
            drawee.setZoomableController(zoomable);
        }

        return v;
    }
}
