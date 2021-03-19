package org.javacord.core.util.handler.message.reaction;

import com.fasterxml.jackson.databind.JsonNode;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.core.entity.emoji.UnicodeEmojiImpl;
import org.javacord.core.entity.message.MessageImpl;
import org.javacord.core.entity.server.ServerImpl;
import org.javacord.core.entity.user.Member;
import org.javacord.core.entity.user.MemberImpl;
import org.javacord.core.event.message.reaction.ReactionAddEventImpl;
import org.javacord.core.util.event.DispatchQueueSelector;
import org.javacord.core.util.gateway.PacketHandler;

import java.util.Optional;

/**
 * Handles the message reaction add packet.
 */
public class MessageReactionAddHandler extends PacketHandler {

    /**
     * Creates a new instance of this class.
     *
     * @param api The api.
     */
    public MessageReactionAddHandler(DiscordApi api) {
        super(api, true, "MESSAGE_REACTION_ADD");
    }

    @Override
    public void handle(JsonNode packet) {
        long messageId = packet.get("message_id").asLong();
        long userId = packet.get("user_id").asLong();

        long channelId = packet.get("channel_id").asLong();
        Optional<TextChannel> channelOptional = api.getTextChannelById(channelId);

        Optional<Server> server = channelOptional.flatMap(TextChannel::asServerChannel).map(ServerChannel::getServer);
        Member member = null;
        if (packet.hasNonNull("member")) {
            member = new MemberImpl(api, (ServerImpl) server.orElse(null), packet.get("member"), null);
        }

        Emoji emoji;
        JsonNode emojiJson = packet.get("emoji");
        if (!emojiJson.has("id") || emojiJson.get("id").isNull()) {
            emoji = UnicodeEmojiImpl.fromString(emojiJson.get("name").asText());
        } else {
            emoji = api.getKnownCustomEmojiOrCreateCustomEmoji(emojiJson);
        }

        Optional<Message> message = api.getCachedMessageById(messageId);
        message.ifPresent(msg -> ((MessageImpl) msg).addReaction(emoji, userId == api.getYourself().getId()));

        ReactionAddEvent event = new ReactionAddEventImpl(api, messageId, channelId, emoji, userId, member);

        api.getEventDispatcher().dispatchReactionAddEvent(
                server.map(DispatchQueueSelector.class::cast).orElse(api),
                messageId,
                server.orElse(null),
                channelOptional.orElse(null),
                userId,
                event);
    }

}
