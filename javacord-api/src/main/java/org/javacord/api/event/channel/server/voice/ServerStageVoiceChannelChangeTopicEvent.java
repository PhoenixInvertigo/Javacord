package org.javacord.api.event.channel.server.voice;

import org.javacord.api.entity.channel.ServerStageVoiceChannel;
import org.javacord.api.event.channel.VoiceChannelEvent;
import org.javacord.api.event.server.ServerEvent;

public interface ServerStageVoiceChannelChangeTopicEvent extends ServerEvent, VoiceChannelEvent {

    @Override
    ServerStageVoiceChannel getChannel();

    /**
     * Gets the new topic of the channel.
     *
     * @return The new topic of the channel.
     */
    String getNewTopic();

    /**
     * Gets the old topic of the channel.
     *
     * @return The old topic of the channel.
     */
    String getOldTopic();
}
