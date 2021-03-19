package org.javacord.core.event.message;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.OptionalMessageEvent;

import java.util.Optional;

/**
 * The implementation of {@link OptionalMessageEvent}.
 */
public abstract class OptionalMessageEventImpl extends MessageEventImpl implements OptionalMessageEvent {

    /**
     * The message of the event. Might be <code>null</code>.
     */
    private final Message message;

    /**
     * Creates a new optional message event.
     *
     * @param api The discord api instance.
     * @param messageId The id of the message.
     * @param channelId The id of the text channel in which the message was sent.
     */
    public OptionalMessageEventImpl(DiscordApi api, long messageId, long channelId) {
        super(api, messageId, channelId);
        message = api.getCachedMessageById(messageId).orElse(null);
    }

    @Override
    public Optional<Message> getMessage() {
        return Optional.ofNullable(message);
    }

}
