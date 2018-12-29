package mareksebera.cz.redditswipe.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import mareksebera.cz.redditswipe.R;

public class ImageItemFragment extends CommonItemFragment {

    private SimpleDraweeView drawee;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        drawee = v.findViewById(R.id.image_fragment_image);
        if (drawee != null) {
            drawee.setImageURI(item.DATA.getUrl());
        }

        return v;
    }
}
