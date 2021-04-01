package org.javacord.api.entity.channel;

import org.javacord.api.entity.DiscordEntity;
import org.javacord.api.entity.Permissionable;
import org.javacord.api.entity.channel.internal.ServerStageVoiceChannelBuilderDelegate;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.server.Server;
import org.javacord.api.util.internal.DelegateFactory;

import java.util.concurrent.CompletableFuture;

/**
 * This class is used to create new server stage voice channels.
 */
public class ServerStageVoiceChannelBuilder extends ServerChannelBuilder {

    /**
     * The server text channel delegate used by this instance.
     */
    private final ServerStageVoiceChannelBuilderDelegate delegate;

    /**
     * Creates a new server stage voice channel builder.
     *
     * @param server The server of the server stage voice channel.
     */
    public ServerStageVoiceChannelBuilder(Server server) {
        delegate = DelegateFactory.createServerStageVoiceChannelBuilderDelegate(server);
    }

    @Override
    public ServerStageVoiceChannelBuilder setAuditLogReason(String reason) {
        delegate.setAuditLogReason(reason);
        return this;
    }

    @Override
    public ServerStageVoiceChannelBuilder setName(String name) {
        delegate.setName(name);
        return this;
    }


    /**
     * Sets the category of the channel.
     *
     * @param category The category of the channel.
     * @return The current instance in order to chain call methods.
     */
    public ServerStageVoiceChannelBuilder setCategory(ChannelCategory category) {
        delegate.setCategory(category);
        return this;
    }

    /**
     * Sets the bitrate of the channel.
     *
     * @param bitrate The bitrate of the channel.
     * @return The current instance in order to chain call methods.
     */
    public ServerStageVoiceChannelBuilder setBitrate(int bitrate) {
        delegate.setBitrate(bitrate);
        return this;
    }

    /**
     * Sets the user limit of the channel.
     *
     * @param userlimit The user limit of the channel.
     * @return The current instance in order to chain call methods.
     */
    public ServerStageVoiceChannelBuilder setUserlimit(int userlimit) {
        delegate.setUserlimit(userlimit);
        return this;
    }

    /**
     * Sets the topic of the channel.
     *
     * @param topic The topic of the channel.
     * @return The current instance in order to chain call methods.
     */
    public ServerStageVoiceChannelBuilder setTopic(String topic) {
        delegate.setTopic(topic);
        return this;
    }

    @Override
    public <T extends Permissionable & DiscordEntity> ServerStageVoiceChannelBuilder addPermissionOverwrite(
            T permissionable, Permissions permissions) {
        delegate.addPermissionOverwrite(permissionable, permissions);
        return this;
    }

    @Override
    public <T extends Permissionable & DiscordEntity> ServerStageVoiceChannelBuilder removePermissionOverwrite(
            T permissionable) {
        delegate.removePermissionOverwrite(permissionable);
        return this;
    }

    /**
     * Creates the server stage voice channel.
     *
     * @return The created stage voice channel.
     */
    public CompletableFuture<ServerStageVoiceChannel> create() {
        return delegate.create();
    }

}
