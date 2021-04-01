package org.javacord.core.entity.channel;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.internal.BasicServerVoiceChannelBuilderDelegate;
import org.javacord.core.entity.server.ServerImpl;

public class BasicServerVoiceChannelBuilderDelegateImpl extends ServerChannelBuilderDelegateImpl
        implements BasicServerVoiceChannelBuilderDelegate {

    /**
     * The bitrate of the channel.
     */
    private Integer bitrate = null;

    /**
     * The userlimit of the channel.
     */
    private Integer userlimit = null;

    /**
     * The category of the channel.
     */
    private ChannelCategory category = null;

    /**
     * Creates a new server voice channel builder delegate.
     *
     * @param server The server of the server voice channel.
     */
    protected BasicServerVoiceChannelBuilderDelegateImpl(ServerImpl server) {
        super(server);
    }

    @Override
    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    @Override
    public void setUserlimit(int userlimit) {
        this.userlimit = userlimit;
    }

    @Override
    public void setCategory(ChannelCategory category) {
        this.category = category;
    }

    @Override
    protected void prepareBody(ObjectNode body) {
        super.prepareBody(body);

        if (bitrate != null) {
            body.put("bitrate", (int) bitrate);
        }
        if (userlimit != null) {
            body.put("user_limit", (int) userlimit);
        }
        if (category != null) {
            body.put("parent_id", category.getIdAsString());
        }
    }
}
