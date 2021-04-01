package org.javacord.core.entity.channel;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.internal.ServerVoiceChannelBuilderDelegate;
import org.javacord.core.entity.server.ServerImpl;
import org.javacord.core.util.rest.RestEndpoint;
import org.javacord.core.util.rest.RestMethod;
import org.javacord.core.util.rest.RestRequest;

import java.util.concurrent.CompletableFuture;

/**
 * The implementation of {@link ServerVoiceChannelBuilderDelegate}.
 */
public class ServerVoiceChannelBuilderDelegateImpl extends BasicServerVoiceChannelBuilderDelegateImpl
        implements ServerVoiceChannelBuilderDelegate {

    /**
     * Creates a new server voice channel builder delegate.
     *
     * @param server The server of the server voice channel.
     */
    public ServerVoiceChannelBuilderDelegateImpl(ServerImpl server) {
        super(server);
    }

    @Override
    public CompletableFuture<ServerVoiceChannel> create() {
        ObjectNode body = JsonNodeFactory.instance.objectNode();
        body.put("type", 2);
        super.prepareBody(body);

        return new RestRequest<ServerVoiceChannel>(server.getApi(), RestMethod.POST, RestEndpoint.SERVER_CHANNEL)
                .setUrlParameters(server.getIdAsString())
                .setBody(body)
                .setAuditLogReason(reason)
                .execute(result -> server.getOrCreateServerVoiceChannel(result.getJsonBody()));
    }
}
