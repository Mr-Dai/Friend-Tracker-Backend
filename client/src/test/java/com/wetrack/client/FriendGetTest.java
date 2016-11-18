package com.wetrack.client;

import com.wetrack.client.model.User;
import com.wetrack.client.test.EntityResponseHelper;
import com.wetrack.client.test.WeTrackClientTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FriendGetTest extends WeTrackClientTest {

    private EntityResponseHelper<List<User>> entityHelper = new EntityResponseHelper<>(gson);

    @Test
    public void testFriendGetRequestFormat() throws InterruptedException {
        MockResponse response = new MockResponse().setResponseCode(200)
                .setBody(readResource("test_friend_get/200.json"));
        server.enqueue(response);

        client.getUserFriendList(robertPeng.getUsername(), dummyToken, entityHelper.callback(200));
        RecordedRequest request = server.takeRequest(3, TimeUnit.SECONDS);
        assertThat(request.getMethod(), is("GET"));
        assertThat(request.getPath(), is("/users/" + robertPeng.getUsername() + "/friends?token=" + dummyToken));
        assertThat(request.getBody().readUtf8().isEmpty(), is(true));
    }

}
