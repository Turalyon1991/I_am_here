package alex.imhere.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import alex.imhere.R;
import alex.imhere.activity.model.AbstractModel;
import alex.imhere.activity.model.ImhereModel;
import alex.imhere.adapter.UsersAdapter;
import alex.imhere.fragment.view.AbstractView;
import alex.imhere.fragment.view.UpdatingViewTimer;
import alex.imhere.layer.server.Session;

public class UsersFragment extends ListFragment implements AbstractView {
	private UsersAdapter usersAdapter;
	private List<Session> readOnlyUsers = new ArrayList<>();
	private ImhereModel model;

	private Handler uiHandler;
	private UpdatingViewTimer updatingViewTimer;

	public static UsersFragment newInstance() {
		UsersFragment fragment = new UsersFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setListAdapter(usersAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		uiHandler = new Handler();
		updatingViewTimer = new UpdatingViewTimer(uiHandler, this);
		updatingViewTimer.start();

		return inflater.inflate(R.layout.fragment_users, container);
	}


	@Override
	public void onDataUpdate() {
		usersAdapter.notifyDataSetChanged();
	}

	@Override
	public void setModel(AbstractModel abstractModel) {
		model = (ImhereModel) abstractModel;
		model.addEventListener(this, this);

		readOnlyUsers = model.getOnlineUsersSet();
		usersAdapter = new UsersAdapter(getActivity(), R.layout.item_user, readOnlyUsers);
	}
}
