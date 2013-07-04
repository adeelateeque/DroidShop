package com.droidshop.core.test;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.droidshop.api.ApiProvider;
import com.droidshop.model.User;

/**
 * Unit tests of client API
 */
public class BootstrapApiClientUtilTest {

    @Test
    @Ignore("Requires the API to use basic authentication. Parse.com api does not. See BootstrapApi for more info.")
    public void shouldCreateClient() throws Exception {
        List<User> users = new ApiProvider().getApi("demo@androidbootstrap.com", "foobar").getUserApi().getUsers();

        assertThat(users.get(0).getUsername(), notNullValue());
    }
}
