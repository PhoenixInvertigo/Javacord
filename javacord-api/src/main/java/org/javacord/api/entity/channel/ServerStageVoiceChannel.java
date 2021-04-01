package org.javacord.api.entity.channel;

import org.javacord.api.listener.channel.server.voice.ServerStageVoiceChannelAttachableListenerManager;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ServerStageVoiceChannel extends BasicServerVoiceChannel,
        ServerStageVoiceChannelAttachableListenerManager {

    @Override
    default ChannelType getType() {
        return ChannelType.SERVER_STAGE_VOICE_CHANNEL;
    }

    /**
     * Gets the topic of this.
     *
     * @return The topic of this channel.
     */
    Optional<String> getTopic();

    /**
     * Creates an updater for this channel.
     *
     * @return An updater for this channel.
     */
    default ServerStageVoiceChannelUpdater createUpdater() {
        return new ServerStageVoiceChannelUpdater(this);
    }

    /**
     * Updates the topic of the channel.
     *
     * <p>If you want to update several settings at once, it's recommended to use the
     * {@link ServerStageVoiceChannelUpdater} from {@link #createUpdater()} which provides a better performance!
     *
     * @param topic The new topic of the channel.
     * @return A future to check if the update was successful.
     */
    default CompletableFuture<Void> updateTopic(String topic) {
        return createUpdater().setTopic(topic).update();
    }

    /**
     * Updates the bitrate of the channel.
     *
     * <p>If you want to update several settings at once, it's recommended to use the
     * {@link ServerStageVoiceChannelUpdater} from {@link #createUpdater()} which provides a better performance!
     *
     * @param bitrate The new bitrate of the channel.
     * @return A future to check if the update was successful.
     */
    default CompletableFuture<Void> updateBitrate(int bitrate) {
        return createUpdater().setBitrate(bitrate).update();
    }

    /**
     * Updates the user limit of the channel.
     *
     * <p>If you want to update several settings at once, it's recommended to use the
     * {@link ServerStageVoiceChannelUpdater} from {@link #createUpdater()} which provides a better performance!
     *
     * @param userLimit The new user limit of the channel.
     * @return A future to check if the update was successful.
     */
    default CompletableFuture<Void> updateUserLimit(int userLimit) {
        return createUpdater().setUserLimit(userLimit).update();
    }

    /**
     * Removes the user limit of the channel.
     *
     * <p>If you want to update several settings at once, it's recommended to use the
     * {@link ServerStageVoiceChannelUpdater} from {@link #createUpdater()} which provides a better performance!
     *
     * @return A future to check if the update was successful.
     */
    default CompletableFuture<Void> removeUserLimit() {
        return createUpdater().removeUserLimit().update();
    }

    /**
     * {@inheritDoc}
     *
     * <p>If you want to update several settings at once, it's recommended to use the
     * {@link ServerStageVoiceChannelUpdater} from {@link #createUpdater()} which provides a better performance!
     *
     * @param category The new category of the channel.
     * @return A future to check if the update was successful.
     */
    default CompletableFuture<Void> updateCategory(ChannelCategory category) {
        return createUpdater().setCategory(category).update();
    }

    /**
     * {@inheritDoc}
     *
     * <p>If you want to update several settings at once, it's recommended to use the
     * {@link ServerStageVoiceChannelUpdater} from {@link #createUpdater()} which provides a better performance!
     *
     * @return A future to check if the update was successful.
     */
    default CompletableFuture<Void> removeCategory() {
        return createUpdater().removeCategory().update();
    }

    @Override
    default Optional<? extends BasicServerVoiceChannel> getCurrentCachedInstance() {
        return getApi().getServerById(getServer().getId()).flatMap(server -> server.getStageVoiceChannelById(getId()));
    }

}
