package org.javacord.api.entity.channel.internal;

import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.ServerVoiceChannelBuilder;

import java.util.concurrent.CompletableFuture;

/**
 * This class is internally used by the {@link ServerVoiceChannelBuilder} to create server voice channels.
 * You usually don't want to interact with this object.
 */
public interface ServerVoiceChannelBuilderDelegate extends BasicServerVoiceChannelBuilderDelegate {

    /**
     * Creates the server voice channel.
     *
     * @return The created voice channel.
     */
    CompletableFuture<ServerVoiceChannel> create();

}
