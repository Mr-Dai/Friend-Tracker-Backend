package com.wetrack.client;

import com.google.gson.reflect.TypeToken;
import com.wetrack.client.model.Location;
import com.wetrack.client.test.MessageResponseTestHelper;
import com.wetrack.client.test.WeTrackClientTest;
import com.wetrack.util.ResourceUtils;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class LocationsUploadTest extends WeTrackClientTest {

    private Type locationListType = new TypeToken<List<Location>>(){}.getType();
    private MessageResponseTestHelper messageHelper = new MessageResponseTestHelper(200);

    private String username = "robert-peng";
    private String token = "Not matter";

    @Test
    public void testLocationUploadRequestFormat() throws InterruptedException {
        List<Location> testLocations =
                gson.fromJson(ResourceUtils.readResource("test_location_upload/example_locations.json"), locationListType);

        server.enqueue(new MockResponse().setResponseCode(200));
        client.uploadLocations(username, token, testLocations, messageHelper.callback());

        RecordedRequest request = server.takeRequest(3, TimeUnit.SECONDS);

        // Assert the request is sent as-is
        assertThat(request, notNullValue());
        assertThat(request.getMethod(), is("POST"));
        assertThat(request.getPath(), is("/users/" + username + "/locations"));

        List<Location> sentLocations =
                gson.fromJson(request.getBody().readUtf8(), LocationService.LocationsUploadRequest.class).getLocations();
        assertThat(sentLocations.size(), is(testLocations.size()));
        for (int i = 0; i < testLocations.size(); i++) {
            Location sentLocation = sentLocations.get(0);
            Location testLocation = testLocations.get(0);

            assertThat(sentLocation.getLatitude(), is(testLocation.getLatitude()));
            assertThat(sentLocation.getLongitude(), is(testLocation.getLongitude()));
            assertThat(sentLocation.getTime(), is(testLocation.getTime()));
        }
    }

    @Test
    public void testLocationUploadWithOkResponse() {
        server.enqueue(new MockResponse().setResponseCode(200)
                .setBody(ResourceUtils.readResource("test_location_upload/200.json")));
        client.uploadLocations(username, token, Collections.<Location>emptyList(), messageHelper.callback());

        messageHelper.assertReceivedSuccessfulMessage();
    }

    @Test
    public void testLocationUploadWithErrorResponse() {
        server.enqueue(new MockResponse().setResponseCode(401)
                .setBody(ResourceUtils.readResource("test_location_upload/401.json")));
        client.uploadLocations(username, token, Collections.<Location>emptyList(), messageHelper.callback());

        messageHelper.assertReceivedFailedMessage(401);
    }

}
