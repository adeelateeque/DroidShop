package com.droidshop;

import java.util.Arrays;

import com.facebook.*;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FacebookFragment extends Fragment {
	
	private static final String TAG = "FacebookFragment";
	private UiLifecycleHelper uiHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_login, container, false);
		LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
		authButton.setFragment(this);
		authButton.setReadPermissions(Arrays.asList("user_likes", "user_status"));
	    return view;
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	    	Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
	            @Override
	            public void onCompleted(GraphUser user, Response response) {
	                if (user != null) {
	                    // Display the parsed user info
	                	Toast.makeText(getActivity(), user(user).toString(), Toast.LENGTH_LONG).show();
	                }
	            }
	        });
	    } else if (state.isClosed()) {
	    	
	    }
	}
	
	private String user(GraphUser user) {
		StringBuilder userInfo = new StringBuilder("");
		userInfo.append(String.format("Name: %s\n\n", user.getName()));
		return userInfo.toString();
	}
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	@Override
	public void onResume() {
	    super.onResume();
	 // For scenarios where the main activity is launched and user
	    // session is not null, the session state change notification
	    // may not be triggered. Trigger it if it's open/closed.
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }
	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();
	    uiHelper.onSaveInstanceState(outState);
	}

}
