package info.mancy.whatsbulkclient.friends;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.mancy.android.ui.fragment.MasterDetailFragmentHelper;
import info.mancy.android.ui.fragment.MasterDetailFragments;
import info.mancy.whatsbulkclient.R;

public class FriendMainFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setRetainInstance(false);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends, container, false);

        MasterDetailFragments currentFragments = MasterDetailFragmentHelper
                .getCurrentFragments(R.id.friendmain_fragment,
                        R.id.frienddetail_fragment, FriendDetailFragment.class,
                        getChildFragmentManager());
        if ( currentFragments.master == null ) {
            currentFragments.master = FriendListFragment.newInstance();
        }

        MasterDetailFragmentHelper.initFragments(currentFragments, R.id.friendmain_fragment,
                R.id.frienddetail_fragment, getResources().getConfiguration(), getChildFragmentManager());

		return view ;
	}
}
