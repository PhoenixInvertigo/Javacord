package org.javacord.core.util.handler.message;

import com.fasterxml.jackson.databind.JsonNode;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.channel.user.PrivateChannelCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.core.entity.channel.PrivateChannelImpl;
import org.javacord.core.entity.user.MemberImpl;
import org.javacord.core.entity.user.UserImpl;
import org.javacord.core.event.channel.user.PrivateChannelCreateEventImpl;
import org.javacord.core.event.message.MessageCreateEventImpl;
import org.javacord.core.util.event.DispatchQueueSelector;
import org.javacord.core.util.gateway.PacketHandler;
import org.javacord.core.util.rest.RestEndpoint;
import org.javacord.core.util.rest.RestMethod;
import org.javacord.core.util.rest.RestRequest;

import java.util.Optional;

/**
 * Handles the message create packet.
 */
public class MessageCreateHandler extends PacketHandler {

    /**
     * Creates a new instance of this class.
     *
     * @param api The api.
     */
    public MessageCreateHandler(DiscordApi api) {
        super(api, true, "MESSAGE_CREATE");
    }

    @Override
    public void handle(JsonNode packet) {
        String channelId = packet.get("channel_id").asText();

        // if the message isn't from a server and the private channel isn't cached
        // See https://github.com/discord/discord-api-docs/issues/2248
        if (!packet.hasNonNull("guild_id") && !api.getTextChannelById(channelId).isPresent()) {
            UserImpl recipient = new UserImpl(api, packet.get("author"), (MemberImpl) null, null);

            if (recipient.isYourself()) {
                // request private channel from discord
                new RestRequest<PrivateChannel>(api, RestMethod.GET, RestEndpoint.CHANNEL)
                        .setUrlParameters(channelId)
                        .execute(result ->
                                new PrivateChannelImpl(api, result.getJsonBody()))
                        .thenAccept(channel -> handle(channel, packet));
            } else {
                // create private channel
                PrivateChannel privateChannel = new PrivateChannelImpl(api, channelId, recipient);
                PrivateChannelCreateEvent event = new PrivateChannelCreateEventImpl(privateChannel);

                api.getEventDispatcher().dispatchPrivateChannelCreateEvent(api, recipient, event);

                handle(privateChannel, packet);
            }
            return;
        }

        api.getTextChannelById(channelId).ifPresent(channel -> handle(channel, packet));
    }

    private void handle(TextChannel channel, JsonNode packet) {
        Message message = api.getOrCreateMessage(channel, packet);
        MessageCreateEvent event = new MessageCreateEventImpl(message);

        Optional<Server> optionalServer = channel.asServerChannel().map(ServerChannel::getServer);
        MessageAuthor author = message.getAuthor();
        api.getEventDispatcher().dispatchMessageCreateEvent(
                optionalServer.map(DispatchQueueSelector.class::cast).orElse(api),
                optionalServer.orElse(null),
                channel,
                author.asUser().orElse(null),
                author.isWebhook() ? author.getId() : null,
                event);
    }

}
