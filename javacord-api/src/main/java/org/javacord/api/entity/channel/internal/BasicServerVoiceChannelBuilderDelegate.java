package org.javacord.api.entity.channel.internal;

import org.javacord.api.entity.channel.ChannelCategory;

public interface BasicServerVoiceChannelBuilderDelegate extends ServerChannelBuilderDelegate {

    /**
     * Sets the bitrate of the channel.
     *
     * @param bitrate The bitrate of the channel.
     */
    void setBitrate(int bitrate);

    /**
     * Sets the user limit of the channel.
     *
     * @param userlimit The user limit of the channel.
     */
    void setUserlimit(int userlimit);

    /**
     * Sets the category of the channel.
     *
     * @param category The category of the channel.
     */
    void setCategory(ChannelCategory category);
}
