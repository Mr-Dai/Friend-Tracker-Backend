package com.wetrack;

import com.wetrack.client.test.MessageResponseHelper;
import com.wetrack.test.WeTrackIntegrateTestWithChatCreated;
import org.junit.Before;
import org.junit.Test;

public class ChatMemberDeletingTest extends WeTrackIntegrateTestWithChatCreated {

    private MessageResponseHelper messageHelper = new MessageResponseHelper(200);

    @Before
    public void setUp() throws Exception {
        super.setUp();

        addFriendWithAssertion(robertPeng, littleHearth);
        addChatMemberWithAssertion(chatIdOf(robertFamily), tokenOf(robertPeng), windyChan, littleHearth);
    }

    @Test
    public void testRemovingMember() {
        client.removeChatMember(chatIdOf(robertFamily), tokenOf(robertPeng), windyChan.getUsername(), messageHelper.callback());
        messageHelper.assertReceivedSuccessfulMessage();
        assertChatMember(chatIdOf(robertFamily), tokenOf(robertPeng), 3, robertPeng, mrDai, littleHearth);
    }

    @Test
    public void testRemovingMemberWithEmptyToken() {
        client.removeChatMember(chatIdOf(robertFamily), "", windyChan.getUsername(), messageHelper.callback());
        messageHelper.assertReceivedFailedMessage(400);
    }

    @Test
    public void testRemovingMemberWithInvalidToken() {
        client.removeChatMember(chatIdOf(robertFamily), tokenOf(robertPeng), windyChan.getUsername(), messageHelper.callback());
        messageHelper.assertReceivedSuccessfulMessage();
        client.removeChatMember(chatIdOf(robertFamily), tokenOf(windyChan), littleHearth.getUsername(), messageHelper.callback());
        messageHelper.assertReceivedFailedMessage(401);
    }

}
