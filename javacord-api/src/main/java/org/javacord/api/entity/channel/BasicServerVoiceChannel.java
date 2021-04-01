package org.javacord.api.entity.channel;

import org.javacord.api.audio.AudioConnection;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelAttachableListenerManager;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface BasicServerVoiceChannel extends ServerChannel, VoiceChannel, Categorizable,
        ServerVoiceChannelAttachableListenerManager {

    /**
     * Connects to the voice channel and disconnects any existing connections in the server.
     *
     * @return The audio connection.
     */
    CompletableFuture<AudioConnection> connect();

    /**
     * Gets the bitrate (int bits) of the channel.
     *
     * @return The bitrate of the channel.
     */
    int getBitrate();

    /**
     * Gets the user limit of the channel.
     *
     * @return The user limit.
     */
    Optional<Integer> getUserLimit();

    /**
     * Gets the ids of the users that are connected to this server voice channel.
     *
     * @return The ids of the users that are connected to this server voice channel.
     */
    Collection<Long> getConnectedUserIds();

    /**
     * Gets the users that are connected to this server voice channel.
     *
     * @return The users that are connected to this server voice channel.
     */
    Collection<User> getConnectedUsers();

    /**
     * Checks whether the user with the given id is connected to this channel.
     *
     * @param userId The id of the user to check.
     * @return Whether the user with the given id is connected to this channel or not.
     */
    boolean isConnected(long userId);

    /**
     * Checks whether the given user is connected to this channel.
     *
     * @param user The user to check.
     * @return Whether the given user is connected to this channel or not.
     */
    default boolean isConnected(User user) {
        return isConnected(user.getId());
    }

    @Override
    default Optional<? extends BasicServerVoiceChannel> getCurrentCachedInstance() {
        return Optional.empty();
    }

    @Override
    default CompletableFuture<? extends BasicServerVoiceChannel> getLatestInstance() {
        Optional<? extends BasicServerVoiceChannel> currentCachedInstance = getCurrentCachedInstance();
        if (currentCachedInstance.isPresent()) {
            return CompletableFuture.completedFuture(currentCachedInstance.get());
        } else {
            CompletableFuture<ServerVoiceChannel> result = new CompletableFuture<>();
            result.completeExceptionally(new NoSuchElementException());
            return result;
        }
    }
}
