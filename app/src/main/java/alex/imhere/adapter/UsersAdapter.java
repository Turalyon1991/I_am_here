package alex.imhere.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import alex.imhere.R;

import alex.imhere.entity.User;

public class UsersAdapter extends ArrayAdapter<User> {
	private final int resourceId;
	private Context context;

	public UsersAdapter(Context context, int item_user, List<User> items) {
		super(context, item_user, items);

		this.context = context;
		this.resourceId = item_user;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View userView = convertView;
		if (userView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			userView = inflater.inflate(resourceId, parent, false);
		}

		User user = getItem(position);
		if (user != null)
		{
			fillViewWithUser(userView, user);
		}

		return userView;
	}

	private void fillViewWithUser(View userView, User user)
	{
		TextView tv_name = (TextView) userView.findViewById(R.id.tv_name);
		tv_name.setText(user.getName());

		TextView tv_singed_in_date = (TextView) userView.findViewById(R.id.tv_singed_in_date);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss (dd-MM-yyyy)");
		tv_singed_in_date.setText(sdf.format(user.getSignedInDate()));
	}
}