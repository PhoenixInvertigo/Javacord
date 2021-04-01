package org.javacord.core.entity.channel;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.javacord.api.entity.channel.ServerStageVoiceChannel;
import org.javacord.api.entity.channel.internal.ServerStageVoiceChannelUpdaterDelegate;

public class ServerStageVoiceChannelUpdaterDelegateImpl extends BasicServerVoiceChannelUpdaterDelegateImpl
        implements ServerStageVoiceChannelUpdaterDelegate {

    private String topic = null;

    private boolean updateTopic = false;

    /**
     * Creates a new server stage voice channel updater.
     *
     * @param channel The channel to update.
     */
    public ServerStageVoiceChannelUpdaterDelegateImpl(ServerStageVoiceChannel channel) {
        super(channel);
    }

    @Override
    public void setTopic(String topic) {
        this.topic = topic;
        this.updateTopic = true;
    }

    @Override
    protected boolean prepareUpdateBody(ObjectNode body) {
        boolean patchChannel = super.prepareUpdateBody(body);

        if (updateTopic) {
            body.put("topic", topic);
            patchChannel = true;
        }

        return patchChannel;
    }
}
