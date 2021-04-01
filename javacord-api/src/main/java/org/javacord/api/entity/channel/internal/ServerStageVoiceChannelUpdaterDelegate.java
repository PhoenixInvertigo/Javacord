package org.javacord.api.entity.channel.internal;

public interface ServerStageVoiceChannelUpdaterDelegate extends ServerVoiceChannelUpdaterDelegate {

    /**
     * Queues the topic to be updated.
     *
     * @param topic The new topic of the channel.
     */
    void setTopic(String topic);

    /**
     * Queues the topic to be removed.
     */
    default void removeTopic() {
        setTopic(null);
    }
}
