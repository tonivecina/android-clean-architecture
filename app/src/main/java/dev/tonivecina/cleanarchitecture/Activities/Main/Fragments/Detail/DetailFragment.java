package dev.tonivecina.cleanarchitecture.Activities.Main.Fragments.Detail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dev.tonivecina.cleanarchitecture.R;

/**
 * @author Toni Vecina on 6/7/17.
 */

public class DetailFragment extends Fragment {

    final private static String BUNDLE_ORIGIN = "origin";

    @SuppressWarnings("FieldCanBeLocal")
    private TextView mOriginTextView;

    public static DetailFragment get(final String origin) {
        DetailFragment fragment = new DetailFragment();

        Bundle args = new Bundle();
        args.putString(BUNDLE_ORIGIN, origin);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mOriginTextView = (TextView) view.findViewById(R.id.fragment_detail_textView);

        Bundle arguments = getArguments();

        if (arguments != null) {
            String origin = arguments.getString(BUNDLE_ORIGIN, null);
            mOriginTextView.setText(origin);
        }
    }
}
