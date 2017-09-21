/*
 * Copyright (C) 2017 Bastian Oppermann
 * 
 * This file is part of Javacord.
 * 
 * Javacord is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser general Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 * 
 * Javacord is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package de.btobastian.javacord.utils.handler.server;

import de.btobastian.javacord.DiscordApi;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.impl.ImplServer;
import de.btobastian.javacord.events.server.ServerMemberRemoveEvent;
import de.btobastian.javacord.listeners.server.ServerMemberRemoveListener;
import de.btobastian.javacord.utils.PacketHandler;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the guild member remove packet.
 */
public class GuildMemberRemoveHandler extends PacketHandler {

    /**
     * Creates a new instance of this class.
     *
     * @param api The api.
     */
    public GuildMemberRemoveHandler(DiscordApi api) {
        super(api, true, "GUILD_MEMBER_REMOVE");
    }

    @Override
    public void handle(JSONObject packet) {
        api.getServerById(packet.getString("guild_id"))
                .map(server -> (ImplServer) server)
                .ifPresent(server -> {
                    User user = api.getOrCreateUser(packet.getJSONObject("user"));
                    server.removeMember(user);

                    ServerMemberRemoveEvent event = new ServerMemberRemoveEvent(api, server, user);

                    List<ServerMemberRemoveListener> listeners = new ArrayList<>();
                    listeners.addAll(server.getServerMemberRemoveListeners());
                    listeners.addAll(user.getServerMemberRemoveListeners());
                    listeners.addAll(api.getServerMemberRemoveListeners());

                    dispatchEvent(listeners, listener -> listener.onServerMemberRemove(event));
                });
    }

}