package com.droidshop.ui;

import java.util.List;

import android.text.TextUtils;
import android.view.LayoutInflater;

import com.droidshop.R;
import com.droidshop.core.AvatarLoader;
import com.droidshop.model.User;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

/**
 * Adapter to display a list of traffic items
 */
public class UserListAdapter extends SingleTypeAdapter<User> {

	private final AvatarLoader avatarLoader;

    /**
     * @param inflater
     * @param items
     */
    public UserListAdapter(LayoutInflater inflater, List<User> items, AvatarLoader avatarLodaer) {
        super(inflater, R.layout.user_list_item);

        this.avatarLoader = avatarLodaer;
        setItems(items);
    }

    /**
     * @param inflater
     */
    public UserListAdapter(LayoutInflater inflater, AvatarLoader avatarLoader) {
        this(inflater, null, avatarLoader);

    }

    @Override
    public long getItemId(final int position) {
        final String id = getItem(position).getObjectId();
        return !TextUtils.isEmpty(id) ? id.hashCode() : super
                .getItemId(position);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.iv_avatar, R.id.tv_name };
    }

    @Override
    protected void update(int position, User user) {

        avatarLoader.bind(imageView(0), user);

        setText(1, String.format("%1$s %2$s", user.getFirstName(), user.getLastName()));
    }

}
