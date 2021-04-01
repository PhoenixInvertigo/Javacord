package org.javacord.core.entity.channel;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.javacord.api.entity.channel.ServerStageVoiceChannel;
import org.javacord.api.entity.channel.internal.ServerStageVoiceChannelBuilderDelegate;
import org.javacord.core.entity.server.ServerImpl;
import org.javacord.core.util.rest.RestEndpoint;
import org.javacord.core.util.rest.RestMethod;
import org.javacord.core.util.rest.RestRequest;

import java.util.concurrent.CompletableFuture;

public class ServerStageVoiceChannelBuilderDelegateImpl
        extends BasicServerVoiceChannelBuilderDelegateImpl implements ServerStageVoiceChannelBuilderDelegate {

    /**
     * The topic of the server stage voice channel.
     */
    private String topic = null;

    /**
     * Creates a new server stage voice channel builder delegate.
     *
     * @param server The server of the server voice channel.
     */
    public ServerStageVoiceChannelBuilderDelegateImpl(ServerImpl server) {
        super(server);
    }

    @Override
    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public CompletableFuture<ServerStageVoiceChannel> create() {
        ObjectNode body = JsonNodeFactory.instance.objectNode();
        body.put("type", 13);

        super.prepareBody(body);

        if (topic != null) {
            body.put("topic", topic);
        }

        return new RestRequest<ServerStageVoiceChannel>(server.getApi(), RestMethod.POST, RestEndpoint.SERVER_CHANNEL)
                .setUrlParameters(server.getIdAsString())
                .setBody(body)
                .setAuditLogReason(reason)
                .execute(result -> server.getOrCreateServerStageVoiceChannel(result.getJsonBody()));
    }
}
