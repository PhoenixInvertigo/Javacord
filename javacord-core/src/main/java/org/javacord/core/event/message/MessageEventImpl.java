package org.javacord.core.event.message;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageEvent;
import org.javacord.core.entity.emoji.UnicodeEmojiImpl;
import org.javacord.core.event.EventImpl;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * The implementation of {@link MessageEvent}.
 */
public abstract class MessageEventImpl extends EventImpl implements MessageEvent {

    /**
     * The id of the message.
     */
    private final long messageId;

    /**
     * The id of the text channel in which the message was sent.
     */
    private final long channelId;

    /**
     * Creates a new message event.
     *
     * @param message The message.
     */
    public MessageEventImpl(Message message) {
        this(message.getApi(), message.getId(), message.getChannelId());
    }

    /**
     * Creates a new message event.
     *
     * @param api The discord api instance.
     * @param messageId The id of the message.
     * @param channelId The text channel in which the message was sent.
     */
    public MessageEventImpl(DiscordApi api, long messageId, long channelId) {
        super(api);
        this.messageId = messageId;
        this.channelId = channelId;
    }

    @Override
    public long getMessageId() {
        return messageId;
    }

    @Override
    public long getChannelId() {
        return channelId;
    }

    @Override
    public Optional<Server> getServer() {
        return getChannel().flatMap(TextChannel::asServerChannel).map(ServerChannel::getServer);
    }

    @Override
    public CompletableFuture<Void> deleteMessage() {
        return deleteMessage(null);
    }

    @Override
    public CompletableFuture<Void> deleteMessage(String reason) {
        return Message.delete(getApi(), getChannelId(), getMessageId(), reason);
    }

    @Override
    public CompletableFuture<Message> editMessage(String content) {
        return Message.edit(getApi(), getChannelId(), getMessageId(), content, null);
    }

    @Override
    public CompletableFuture<Message> editMessage(EmbedBuilder embed) {
        return Message.edit(getApi(), getChannelId(), getMessageId(), null, embed);
    }

    @Override
    public CompletableFuture<Message> editMessage(String content, EmbedBuilder embed) {
        return Message.edit(getApi(), getChannelId(), getMessageId(), content, embed);
    }

    @Override
    public CompletableFuture<Void> addReactionToMessage(String unicodeEmoji) {
        return Message.addReaction(getApi(), getChannelId(), getMessageId(), unicodeEmoji);
    }

    @Override
    public CompletableFuture<Void> addReactionToMessage(Emoji emoji) {
        return Message.addReaction(getApi(), getChannelId(), getMessageId(), emoji);
    }

    @Override
    public CompletableFuture<Void> addReactionsToMessage(Emoji... emojis) {
        return CompletableFuture.allOf(Arrays.stream(emojis)
                                               .map(this::addReactionToMessage)
                                               .toArray(CompletableFuture[]::new));
    }

    @Override
    public CompletableFuture<Void> addReactionsToMessage(String... unicodeEmojis) {
        return addReactionsToMessage(Arrays.stream(unicodeEmojis)
                                             .map(UnicodeEmojiImpl::fromString)
                                             .toArray(Emoji[]::new));
    }

    @Override
    public CompletableFuture<Void> removeAllReactionsFromMessage() {
        return Message.removeAllReactions(getApi(), getChannelId(), getMessageId());
    }

    @Override
    public CompletableFuture<Void> removeReactionByEmojiFromMessage(User user, Emoji emoji) {
        return Reaction.removeUser(getApi(), getChannelId(), getMessageId(), emoji, user.getId());
    }

    @Override
    public CompletableFuture<Void> removeReactionByEmojiFromMessage(User user, String unicodeEmoji) {
        return removeReactionByEmojiFromMessage(user, UnicodeEmojiImpl.fromString(unicodeEmoji));
    }

    @Override
    public CompletableFuture<Void> removeReactionByEmojiFromMessage(Emoji emoji) {
        return Reaction.getUsers(getApi(), getChannelId(), getMessageId(), emoji)
                .thenCompose(users -> CompletableFuture.allOf(
                        users.stream()
                                .map(user -> Reaction.removeUser(getApi(), getChannelId(),
                                        getMessageId(), emoji, user.getId()))
                                .toArray(CompletableFuture[]::new)));
    }

    @Override
    public CompletableFuture<Void> removeReactionByEmojiFromMessage(String unicodeEmoji) {
        return removeReactionByEmojiFromMessage(UnicodeEmojiImpl.fromString(unicodeEmoji));
    }

    @Override
    public CompletableFuture<Void> removeReactionsByEmojiFromMessage(User user, Emoji... emojis) {
        return CompletableFuture.allOf(Arrays.stream(emojis)
                .map(emoji -> removeReactionByEmojiFromMessage(user, emoji))
                .toArray(CompletableFuture[]::new));
    }

    @Override
    public CompletableFuture<Void> removeReactionsByEmojiFromMessage(User user, String... unicodeEmojis) {
        return removeReactionsByEmojiFromMessage(
                user, Arrays.stream(unicodeEmojis)
                        .map(UnicodeEmojiImpl::fromString)
                        .toArray(Emoji[]::new));
    }

    @Override
    public CompletableFuture<Void> removeReactionsByEmojiFromMessage(Emoji... emojis) {
        return CompletableFuture.allOf(
                Arrays.stream(emojis)
                        .map(this::removeReactionByEmojiFromMessage)
                        .toArray(CompletableFuture[]::new));
    }

    @Override
    public CompletableFuture<Void> removeReactionsByEmojiFromMessage(String... unicodeEmojis) {
        return removeReactionsByEmojiFromMessage(Arrays.stream(unicodeEmojis)
                                                         .map(UnicodeEmojiImpl::fromString)
                                                         .toArray(Emoji[]::new));
    }

    @Override
    public CompletableFuture<Void> removeOwnReactionByEmojiFromMessage(Emoji emoji) {
        return removeReactionByEmojiFromMessage(getApi().getYourself(), emoji);
    }

    @Override
    public CompletableFuture<Void> removeOwnReactionByEmojiFromMessage(String unicodeEmoji) {
        return removeOwnReactionByEmojiFromMessage(UnicodeEmojiImpl.fromString(unicodeEmoji));
    }

    @Override
    public CompletableFuture<Void> removeOwnReactionsByEmojiFromMessage(Emoji... emojis) {
        return removeReactionsByEmojiFromMessage(getApi().getYourself(), emojis);
    }

    @Override
    public CompletableFuture<Void> removeOwnReactionsByEmojiFromMessage(String... unicodeEmojis) {
        return removeOwnReactionsByEmojiFromMessage(Arrays.stream(unicodeEmojis)
                                                            .map(UnicodeEmojiImpl::fromString)
                                                            .toArray(Emoji[]::new));
    }

    @Override
    public CompletableFuture<Void> pinMessage() {
        return Message.pin(getApi(), getChannelId(), getMessageId());
    }

    @Override
    public CompletableFuture<Void> unpinMessage() {
        return Message.unpin(getApi(), getChannelId(), getMessageId());
    }

}
