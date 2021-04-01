package org.javacord.core.entity.channel;

import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.internal.ServerVoiceChannelUpdaterDelegate;

/**
 * The implementation of {@link ServerVoiceChannelUpdaterDelegate}.
 */
public class ServerVoiceChannelUpdaterDelegateImpl extends BasicServerVoiceChannelUpdaterDelegateImpl {

    /**
     * Creates a new server voice channel updater.
     *
     * @param channel The channel to update.
     */
    public ServerVoiceChannelUpdaterDelegateImpl(ServerVoiceChannel channel) {
        super(channel);
    }
}
