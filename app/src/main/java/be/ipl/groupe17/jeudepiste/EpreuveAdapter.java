package be.ipl.groupe17.jeudepiste;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import java.util.List;

/**
 * Created by Alexandre on 26-11-15.
 */
public class EpreuveAdapter extends ArrayAdapter<Epreuve> {

    // View lookup cache
    private static class ViewHolder {
        CheckedTextView nom;
    }

    public EpreuveAdapter(Context context, List<Epreuve> list) {
        super(context, R.layout.epreuve_progression_item, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Epreuve epreuve = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.epreuve_progression_item, parent, false);
            viewHolder.nom = (CheckedTextView) convertView.findViewById(R.id.checkedTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.nom.setText(epreuve.getNum());
        viewHolder.nom.setChecked(epreuve.isCompleted());
        // Return the completed view to render on screen
        return convertView;
    }
}
