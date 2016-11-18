package com.wetrack.client;

import com.wetrack.client.model.User;
import com.wetrack.client.test.MessageResponseHelper;
import com.wetrack.client.test.WeTrackClientTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.json.JSONObject;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class UserUpdateTest extends WeTrackClientTest {

    private String token = "Notmatter";

    private MessageResponseHelper messageHelper = new MessageResponseHelper(200);

    @Test
    public void testUserUpdateRequestFormat() throws Exception {
        String testResponseBody = readResource("test_user_update/200.json");
        MockResponse testResponse = new MockResponse().setResponseCode(200).setBody(testResponseBody);
        server.enqueue(testResponse);

        String testUserStr = readResource("test_user_update/example_user.json");
        User testUser = gson.fromJson(testUserStr, User.class);
        client.updateUser(testUser.getUsername(), token, testUser, messageHelper.callback());

        // Assert the request is sent as-is
        RecordedRequest request = server.takeRequest(3, TimeUnit.SECONDS);
        assertThat(request, notNullValue());
        assertThat(request.getMethod(), is("PUT"));
        assertThat(request.getPath(), is("/users/" + testUser.getUsername() + "?token=" + token));
        assertThat(request.getBody().readUtf8(), is(gson.toJson(testUser)));
    }

    @Test
    public void testUserUpdateOnOkResponse() throws Exception {
        String testResponseBody = readResource("test_user_update/200.json");
        MockResponse testResponse = new MockResponse().setResponseCode(200).setBody(testResponseBody);
        server.enqueue(testResponse);

        String testUserStr = readResource("test_user_update/example_user.json");
        User testUser = gson.fromJson(testUserStr, User.class);

        client.updateUser(testUser.getUsername(), token, testUser, messageHelper.callback());

        messageHelper.assertReceivedSuccessfulMessage();
        JSONObject testResponseJson = new JSONObject(testResponseBody);
        assertThat(messageHelper.getReceivedMessage(), is(testResponseJson.optString("message")));
    }

    @Test
    public void testUserUpdateOnErrorResponse() throws Exception {
        String testResponseBody = readResource("test_user_update/404.json");
        MockResponse testResponse = new MockResponse().setResponseCode(404).setBody(testResponseBody);
        server.enqueue(testResponse);

        String testUserStr = readResource("test_user_update/example_user.json");
        User testUser = gson.fromJson(testUserStr, User.class);

        client.updateUser(testUser.getUsername(), token, testUser, messageHelper.callback());

        messageHelper.assertReceivedFailedMessage(404);
        JSONObject testResponseJson = new JSONObject(testResponseBody);
        assertThat(messageHelper.getReceivedMessage(), is(testResponseJson.optString("message")));
    }

}
