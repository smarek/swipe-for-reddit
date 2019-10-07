package mareksebera.cz.redditswipe.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import mareksebera.cz.redditswipe.R;
import mareksebera.cz.redditswipe.core.RedditItem;

public class CommentsFragment extends BottomSheetDialogFragment {

    public static CommentsFragment newInstance(RedditItem item) {
        Bundle args = new Bundle();
        args.putSerializable("item", item);
        CommentsFragment cf = new CommentsFragment();
        cf.setArguments(args);
        return cf;
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.comments_fragment, container, false);

        RedditItem item = (RedditItem) getArguments().getSerializable("item");

        return v;
    }
}
