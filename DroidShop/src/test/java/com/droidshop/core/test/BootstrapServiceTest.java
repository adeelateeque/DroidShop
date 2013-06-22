

package com.droidshop.core.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import android.accounts.OperationCanceledException;

import com.droidshop.api.BootstrapApi;
import com.droidshop.core.UserAgentProvider;
import com.droidshop.model.CheckIn;
import com.droidshop.model.News;
import com.droidshop.model.User;
import com.github.kevinsawicki.http.HttpRequest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit tests of {@link com.droidshop.api.BootstrapApi}
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class BootstrapServiceTest {

    /**
     * Create reader for string
     *
     * @param value
     * @return input stream reader
     * @throws IOException
     */
    private static BufferedReader createReader(String value) throws IOException {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(
                value.getBytes(HttpRequest.CHARSET_UTF8))));
    }

    @Mock
    private HttpRequest request;

    private BootstrapApi service;

    /**
     * Set up default mocks
     *
     * @throws IOException
     */
    @Before
    public void before() throws IOException {
        service = new BootstrapApi("foo", new UserAgentProvider()) {
            protected HttpRequest execute(HttpRequest request) throws IOException {
                return BootstrapServiceTest.this.request;
            }
        };
        doReturn(true).when(request).ok();
    }

    /**
     * Verify getting users with an empty response
     *
     * @throws IOException
     */
    @Test
    public void getUsersEmptyResponse() throws IOException {
        doReturn(createReader("")).when(request).bufferedReader();
        List<User> users = null;
		try
		{
			users = service.getUserApi().getUsers();
		}
		catch (OperationCanceledException e)
		{
			e.printStackTrace();
		}
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    /**
     * Verify getting news with an empty response
     *
     * @throws IOException
     */
    @Test
    public void getContentEmptyResponse() throws IOException {
        doReturn(createReader("")).when(request).bufferedReader();
        List<News> content = null;
		try
		{
			content = service.getNewsApi().getNews();
		}
		catch (OperationCanceledException e)
		{
			e.printStackTrace();
		}
        assertNotNull(content);
        assertTrue(content.isEmpty());
    }

    /**
     * Verify getting checkins with an empty response
     *
     * @throws IOException
     */
    @Test
    public void getReferrersEmptyResponse() throws IOException {
        doReturn(createReader("")).when(request).bufferedReader();
        List<CheckIn> referrers = null;
		try
		{
			referrers = service.getCheckInApi().getCheckIns();
		}
		catch (OperationCanceledException e)
		{
			e.printStackTrace();
		}
        assertNotNull(referrers);
        assertTrue(referrers.isEmpty());
    }
}
