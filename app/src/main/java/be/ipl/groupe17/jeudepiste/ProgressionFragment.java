package be.ipl.groupe17.jeudepiste;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgressionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgressionFragment extends Fragment {
    private Activity activity;
    private ListView listview;


    public ProgressionFragment() {
        // Required empty public constructor
    }

    // source : http://stackoverflow.com/a/32088447
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thisView =  inflater.inflate(R.layout.fragment_progression, container, false);
        listview = (ListView) thisView.findViewById(R.id.listView_progression);
        return thisView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Epreuve> arrayList = new ArrayList<>(Model.getInstance().getEpreuves());
        EpreuveAdapter adapter = new EpreuveAdapter(activity, arrayList);

        listview.setAdapter(adapter);
    }

}
