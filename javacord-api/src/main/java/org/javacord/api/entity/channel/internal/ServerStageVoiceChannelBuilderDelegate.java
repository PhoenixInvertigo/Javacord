package org.javacord.api.entity.channel.internal;

import org.javacord.api.entity.channel.ServerStageVoiceChannel;

import java.util.concurrent.CompletableFuture;

public interface ServerStageVoiceChannelBuilderDelegate extends BasicServerVoiceChannelBuilderDelegate {

    /**
     * Set the topic of the server stage voice channel.
     *
     * @param topic The topic of the server stage voice channel.
     */
    void setTopic(String topic);

    /**
     * Creates the server stage voice channel.
     *
     * @return The created stage voice channel.
     */
    CompletableFuture<ServerStageVoiceChannel> create();
}
