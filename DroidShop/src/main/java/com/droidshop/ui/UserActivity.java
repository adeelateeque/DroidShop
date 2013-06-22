package com.droidshop.ui;

import static com.droidshop.core.Constants.Extra.USER;

import javax.inject.Inject;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.InjectView;

import com.droidshop.R;
import com.droidshop.core.AvatarLoader;
import com.droidshop.model.User;
import com.droidshop.ui.core.BootstrapActivity;

/** Activity to display user details */
public class UserActivity extends BootstrapActivity {

    @InjectView(R.id.iv_avatar) protected ImageView avatar;
    @InjectView(R.id.tv_name) protected TextView name;

    @Inject protected AvatarLoader avatarLoader;

    protected User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_view);

        if(getIntent() != null && getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().getSerializable(USER);
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        avatarLoader.bind(avatar, user);
        name.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
    }
}