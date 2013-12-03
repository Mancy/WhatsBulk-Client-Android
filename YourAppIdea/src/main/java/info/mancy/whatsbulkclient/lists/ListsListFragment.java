package info.mancy.whatsbulkclient.lists;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import info.mancy.android.network.volley.GsonRequest;
import info.mancy.whatsbulkclient.BuildConfig;
import info.mancy.whatsbulkclient.R;
import info.mancy.whatsbulkclient.YourApplication;
import info.mancy.whatsbulkclient.tutorial.sync.TutorialSyncHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import javax.inject.Inject;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

//http://www.flightradar24.com/AirportInfoService.php?airport=ORY&type=in
//LFRS
public class ListsListFragment extends Fragment implements AdapterView.OnItemClickListener {

	private Menu optionsMenu;

	private RequestQueue requestQueue;
	
	private boolean requestRunning = false;

	private String currentMode = "in" ;

    private ListsAdapter listsAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG) {
		    Log.i(YourApplication.LOG_TAG, "ListsListFragment.onCreate");
        }
		
		//setRetainInstance(true);
		setHasOptionsMenu(true);
		
		this.listsAdapter = new ListsAdapter(this.getActivity(),
				R.id.list_name, new ArrayList<Lists>());

		this.requestQueue = Volley.newRequestQueue(this.getActivity());
		this.startRequest();
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Log.i(YourApplication.LOG_TAG, "ListsListFragment.onCreateView");
        }
        View view = inflater.inflate(R.layout.lists_listfragment, container, false);
        ListView listView = (ListView) view.findViewById(R.id.lists_listview);
        listView.setAdapter(this.listsAdapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (BuildConfig.DEBUG) {
            Log.i(YourApplication.LOG_TAG, "ListsListFragment.onCreateOptionsMenu");
        }
		this.optionsMenu = menu;
		inflater.inflate(R.menu.lists_menu, menu);
		
		MenuItem modeMenuItem =  menu.findItem(R.id.lists_menuMode);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(modeMenuItem).findViewById(R.id.lists_mode_spinner);
		if ( this.currentMode.equals("in")) {
			spinner.setSelection(0);
		}
		else {
			spinner.setSelection(1);
		}
		
		spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long row) {
				
				ListsListFragment.this.cancelRequests();
				if ( position == 0 ) {
                    ListsListFragment.this.currentMode = "in";
				}
				else {
                    ListsListFragment.this.currentMode = "out";
				}
                ListsListFragment.this.startRequest();
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				
			}
		});
		
		if ( this.requestRunning) {
			this.setRefreshActionButtonState(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.lists_menuRefresh:
			this.startRequest();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void startRequest() {
        if (BuildConfig.DEBUG) {
            Log.i(YourApplication.LOG_TAG, "ListsListFragment.startRequest");
        }
		if ( !requestRunning ) {
            ListsListFragment.this.requestRunning = true ;
			
			String url = getString(R.string.lists_rest_url);
			GsonRequest<Lists[]> jsObjRequest = new GsonRequest<Lists[]>(
					Method.GET, url,
                    Lists[].class, null,
					this.createAirportRequestSuccessListener(),
					this.createAirportRequestErrorListener());
			jsObjRequest.setShouldCache(false);
			this.setRefreshActionButtonState(true);
			this.requestQueue.add(jsObjRequest);
		}
		else if (BuildConfig.DEBUG) {
            Log.i(YourApplication.LOG_TAG, "  request is already running");
		}
	}
	
	private void cancelRequests() {
		this.requestQueue.cancelAll(this);
	}

	public void setRefreshActionButtonState(final boolean refreshing) {
		if (optionsMenu != null) {
			final MenuItem refreshItem = optionsMenu
					.findItem(R.id.lists_menuRefresh);
			if (refreshItem != null) {
				if (refreshing) {
                    MenuItemCompat.setActionView(refreshItem, R.layout.actionbar_indeterminate_progress);
					//refreshItem
					//		.setActionView(R.layout.actionbar_indeterminate_progress);
				} else {
                    MenuItemCompat.setActionView(refreshItem, null);
					//refreshItem.setActionView(null);
				}
			}
		}
	}

	private Response.Listener<Lists[]> createAirportRequestSuccessListener() {
		return new Response.Listener<Lists[]>() {
			@Override
			public void onResponse(Lists[] response) {
                if (BuildConfig.DEBUG) {
                    Log.i(YourApplication.LOG_TAG, "AirportListFragment.onResponse");
                }
                ListsAdapter adapter = (ListsAdapter) ListsListFragment.this.listsAdapter;
				adapter.clear();
                adapter.addAll(response);
                /*for( Flight flight: response) {
                    adapter.add(flight);
                }*/
                //adapter.addAll((ArrayList) response);
                /*for (int i=0;i<((ArrayList) response).size() ; i++) {
                    adapter.addAll(((Object) response).get(i));
                }*/
                //adapter.addAll((Flight[]) response);
                /*for( Object[] flight: response) {
                    adapter.add(flight);
                }*/
				adapter.notifyDataSetChanged();
                ListsListFragment.this.setRefreshActionButtonState(false);
                ListsListFragment.this.requestRunning = false ;
			}
		};
	}

	private Response.ErrorListener createAirportRequestErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
                if (BuildConfig.DEBUG) {
                    Log.i(YourApplication.LOG_TAG, "ListsListFragment.onErrorResponse");
                }
                ListsListFragment.this.setRefreshActionButtonState(false);
                ListsListFragment.this.requestRunning = false ;
				
				Crouton.makeText(
                        ListsListFragment.this.getActivity(),
					//getString(R.string.error_retrievingdata),
                        error.getMessage(),
					Style.ALERT).show();
				
			}
		};
	}

    @Override
    public void onDestroy() {
        if (BuildConfig.DEBUG) {
            Log.i(YourApplication.LOG_TAG, "ListsListFragment.onDestroy");
        }
        this.cancelRequests();
        Crouton.cancelAllCroutons();
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
