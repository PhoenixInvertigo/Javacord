package org.javacord.core.util.handler.message.reaction;

import com.fasterxml.jackson.databind.JsonNode;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.reaction.ReactionRemoveAllEvent;
import org.javacord.core.entity.message.MessageImpl;
import org.javacord.core.event.message.reaction.ReactionRemoveAllEventImpl;
import org.javacord.core.util.event.DispatchQueueSelector;
import org.javacord.core.util.gateway.PacketHandler;

import java.util.Optional;

/**
 * Handles the message reaction remove all packet.
 */
public class MessageReactionRemoveAllHandler extends PacketHandler {

    /**
     * Creates a new instance of this class.
     *
     * @param api The api.
     */
    public MessageReactionRemoveAllHandler(DiscordApi api) {
        super(api, true, "MESSAGE_REACTION_REMOVE_ALL");
    }

    @Override
    public void handle(JsonNode packet) {
        long messageId = packet.get("message_id").asLong();
        long channelId = packet.get("channel_id").asLong();

        Optional<Message> message = api.getCachedMessageById(messageId);
        Optional<TextChannel> channel = api.getTextChannelById(channelId);

        message.ifPresent(msg -> ((MessageImpl) msg).removeAllReactionsFromCache());

        ReactionRemoveAllEvent event = new ReactionRemoveAllEventImpl(api, messageId, channelId);

        Optional<Server> optionalServer = channel.flatMap(TextChannel::asServerChannel).map(ServerChannel::getServer);
        api.getEventDispatcher().dispatchReactionRemoveAllEvent(
                optionalServer.map(DispatchQueueSelector.class::cast).orElse(api),
                messageId,
                optionalServer.orElse(null),
                channel.orElse(null),
                event);
    }

}
