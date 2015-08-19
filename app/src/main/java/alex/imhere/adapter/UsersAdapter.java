package alex.imhere.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.joda.time.Period;

import java.util.List;

import alex.imhere.R;

import alex.imhere.layer.server.Session;
import alex.imhere.service.TimeFormatter;

public class UsersAdapter extends ArrayAdapter<Session> {
	private final int resourceId;
	private Context context;

	public UsersAdapter(Context context, int item_user, List<Session> items) {
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

		Session session = getItem(position);
		if (session != null)
		{
			fillViewWithUser(userView, session);
		}

		return userView;
	}

	private void fillViewWithUser(View userView, Session session)
	{
		TextView tv_name = (TextView) userView.findViewById(R.id.tv_name);
		tv_name.setText(session.getUdid());

		TextView tv_singed_in_date = (TextView) userView.findViewById(R.id.tv_singed_in_date);

		String result = new TimeFormatter().durationToMSString( session.getLifetime() );

		tv_singed_in_date.setText( result );
	}
}
