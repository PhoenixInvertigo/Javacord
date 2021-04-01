package org.javacord.core.event.channel.server.voice;

import org.javacord.api.entity.channel.ServerStageVoiceChannel;
import org.javacord.api.event.channel.server.voice.ServerStageVoiceChannelChangeTopicEvent;
import org.javacord.core.event.server.ServerEventImpl;

public class ServerStageVoiceChannelChangeTopicEventImpl extends ServerEventImpl
        implements ServerStageVoiceChannelChangeTopicEvent {

    /**
     * The channel of the event.
     */
    protected final ServerStageVoiceChannel channel;

    /**
     * The new topic of the channel.
     */
    private final String newTopic;

    /**
     * The old topic of the channel.
     */
    private final String oldTopic;

    /**
     * Creates a new server stage voice channel change topic event.
     *
     * @param channel The server stage voice channel of the event.
     * @param newTopic The new topic of the channel.
     * @param oldTopic The old topic of the channel.
     */

    public ServerStageVoiceChannelChangeTopicEventImpl(ServerStageVoiceChannel channel, String newTopic,
                                                       String oldTopic) {
        super(channel.getServer());
        this.channel = channel;
        this.newTopic = newTopic;
        this.oldTopic = oldTopic;
    }

    @Override
    public String getNewTopic() {
        return newTopic;
    }

    @Override
    public String getOldTopic() {
        return oldTopic;
    }

    @Override
    public ServerStageVoiceChannel getChannel() {
        return channel;
    }

}
