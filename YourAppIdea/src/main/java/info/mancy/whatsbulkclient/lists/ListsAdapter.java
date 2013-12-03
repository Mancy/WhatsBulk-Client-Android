package info.mancy.whatsbulkclient.lists;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import info.mancy.whatsbulkclient.R;

import java.util.List;

public class ListsAdapter extends ArrayAdapter<Lists> {

	public ListsAdapter(Context context, int textViewResourceId,
			List<Lists> objects) {
		super(context, textViewResourceId, objects);
		
	}

	public View getView(int position, View view, ViewGroup viewGroup) {

		View updateView ;
		ViewHolder viewHolder ;
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			updateView = inflater.inflate(R.layout.lists_listitem, null);
			
			viewHolder = new ViewHolder();
			
			//Name
			viewHolder.listNameView = (TextView) updateView
					.findViewById(R.id.list_name);
			//From
			viewHolder.listCountView = (TextView) updateView
					.findViewById(R.id.list_count);
			// ETA
			viewHolder.listIdView = (TextView) updateView
					.findViewById(R.id.list_id);
			updateView.setTag(viewHolder);
		} else {
			updateView = view;
			viewHolder =  (ViewHolder) updateView.getTag();
		}

		Lists lists = (Lists) getItem(position);
		viewHolder.listNameView.setText(lists.getName());
		viewHolder.listCountView.setText(String.valueOf(lists.getCount()));
		viewHolder.listIdView.setText(String.valueOf(lists.getId()));

		return updateView;
	}
	
	
	private static class ViewHolder {
		public TextView listNameView ;
		public TextView listCountView ;
		public TextView listIdView ;
	}
}
