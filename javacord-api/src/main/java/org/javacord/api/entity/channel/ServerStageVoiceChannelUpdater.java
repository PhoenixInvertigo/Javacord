package org.javacord.api.entity.channel;

import org.javacord.api.entity.DiscordEntity;
import org.javacord.api.entity.Permissionable;
import org.javacord.api.entity.channel.internal.ServerStageVoiceChannelUpdaterDelegate;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.util.internal.DelegateFactory;

import java.util.concurrent.CompletableFuture;

public class ServerStageVoiceChannelUpdater extends ServerChannelUpdater {
    
    /**
     * The server stage voice channel delegate used by this instance.
     */
    private final ServerStageVoiceChannelUpdaterDelegate delegate;

    /**
     * Creates a new server stage voice channel updater.
     *
     * @param channel The channel to update.
     */
    public ServerStageVoiceChannelUpdater(ServerStageVoiceChannel channel) {
        delegate = DelegateFactory.createServerStageVoiceChannelUpdaterDelegate(channel);
    }

    /**
     * Queues the topic to be updated.
     *
     * @param topic The new topic of the channel.
     * @return The current instance in order to chain call methods.
     */
    public ServerStageVoiceChannelUpdater setTopic(String topic) {
        delegate.setTopic(topic);
        return this;
    }

    /**
     * Queues the topic to be removed.
     *
     * @return The current instance in order to chain call methods.
     */
    public ServerStageVoiceChannelUpdater removeTopic() {
        delegate.removeTopic();
        return this;
    }

    /**
     * Queues the bitrate to be updated.
     *
     * @param bitrate The new bitrate of the channel.
     * @return The current instance in order to chain call methods.
     */
    public ServerStageVoiceChannelUpdater setBitrate(int bitrate) {
        delegate.setBitrate(bitrate);
        return this;
    }

    /**
     * Queues the user limit to be updated.
     *
     * @param userLimit The new user limit of the channel.
     * @return The current instance in order to chain call methods.
     */
    public ServerStageVoiceChannelUpdater setUserLimit(int userLimit) {
        delegate.setUserLimit(userLimit);
        return this;
    }

    /**
     * Queues the user limit to be removed.
     *
     * @return The current instance in order to chain call methods.
     */
    public ServerStageVoiceChannelUpdater removeUserLimit() {
        delegate.removeUserLimit();
        return this;
    }

    /**
     * Queues the category to be updated.
     *
     * @param category The new category of the channel.
     * @return The current instance in order to chain call methods.
     */
    public ServerStageVoiceChannelUpdater setCategory(ChannelCategory category) {
        delegate.setCategory(category);
        return this;
    }

    /**
     * Queues the category to be removed.
     *
     * @return The current instance in order to chain call methods.
     */
    public ServerStageVoiceChannelUpdater removeCategory() {
        delegate.removeCategory();
        return this;
    }

    @Override
    public ServerStageVoiceChannelUpdater setAuditLogReason(String reason) {
        delegate.setAuditLogReason(reason);
        return this;
    }

    @Override
    public ServerStageVoiceChannelUpdater setName(String name) {
        delegate.setName(name);
        return this;
    }

    @Override
    public ServerStageVoiceChannelUpdater setRawPosition(int rawPosition) {
        delegate.setRawPosition(rawPosition);
        return this;
    }

    @Override
    public <T extends Permissionable & DiscordEntity> ServerStageVoiceChannelUpdater addPermissionOverwrite(
            T permissionable, Permissions permissions) {
        delegate.addPermissionOverwrite(permissionable, permissions);
        return this;
    }

    @Override
    public <T extends Permissionable & DiscordEntity> ServerStageVoiceChannelUpdater removePermissionOverwrite(
            T permissionable) {
        delegate.removePermissionOverwrite(permissionable);
        return this;
    }

    @Override
    public CompletableFuture<Void> update() {
        return delegate.update();
    }
}
