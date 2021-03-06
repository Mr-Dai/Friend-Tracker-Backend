package com.wetrack.service.friend;

import org.junit.Before;

import javax.ws.rs.core.Response;

public abstract class FriendServiceTestWithFriendAdded extends FriendServiceTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();

        Response response = addFriend(robertPeng, windyChan, tokenOf(robertPeng));
        assertReceivedNonemptyMessage(response, 200);
        response = addFriend(robertPeng, mrDai, tokenOf(robertPeng));
        assertReceivedNonemptyMessage(response, 200);
        response = addFriend(windyChan, littleHearth, tokenOf(windyChan));
        assertReceivedNonemptyMessage(response, 200);
        response = addFriend(mrDai, littleHearth, tokenOf(mrDai));
        assertReceivedNonemptyMessage(response, 200);
    }

}
