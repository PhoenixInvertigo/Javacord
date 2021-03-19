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
            UserImpl author = new UserImpl(api, packet.get("author"), (MemberImpl) null, null);

            PrivateChannel privateChannel = null;
            if (!author.isYourself()) {
                // create private channel if the author is the recipient of the channel
                privateChannel = new PrivateChannelImpl(api, channelId, author);
                // dispatch private channel create event
                PrivateChannelCreateEvent event = new PrivateChannelCreateEventImpl(privateChannel);
                api.getEventDispatcher().dispatchPrivateChannelCreateEvent(api, privateChannel.getRecipient(), event);
            }

            handle(privateChannel, packet);
            return;
        }

        api.getTextChannelById(channelId).ifPresent(channel -> handle(channel, packet));
    }

    private void handle(TextChannel channel, JsonNode packet) {
        Message message = api.getOrCreateMessage(channel, packet);
        MessageCreateEvent event = new MessageCreateEventImpl(message);

        Optional<Server> optionalServer = channel == null
                ? Optional.empty() // if channel is null there is no channel to get the server from
                : channel.asServerChannel().map(ServerChannel::getServer);
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
