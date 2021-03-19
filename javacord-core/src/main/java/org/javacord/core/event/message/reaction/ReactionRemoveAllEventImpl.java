package org.javacord.core.event.message.reaction;

import org.javacord.api.DiscordApi;
import org.javacord.api.event.message.reaction.ReactionRemoveAllEvent;
import org.javacord.core.event.message.OptionalMessageEventImpl;

/**
 * The implementation of {@link ReactionRemoveAllEvent}.
 */
public class ReactionRemoveAllEventImpl extends OptionalMessageEventImpl implements ReactionRemoveAllEvent {

    /**
     * Creates a new reaction remove all event.
     *
     * @param api The discord api instance.
     * @param messageId The id of the message.
     * @param channelId The id of the text channel in which the message was sent.
     */
    public ReactionRemoveAllEventImpl(DiscordApi api, long messageId, long channelId) {
        super(api, messageId, channelId);
    }

}
