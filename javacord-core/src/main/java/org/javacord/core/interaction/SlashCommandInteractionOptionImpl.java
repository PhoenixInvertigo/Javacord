package org.javacord.core.interaction;

import com.fasterxml.jackson.databind.JsonNode;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Mentionable;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class SlashCommandInteractionOptionImpl implements SlashCommandInteractionOption {

    private final DiscordApi api;
    private final String name;
    private final String stringRepresentation;
    private final String stringValue;
    private final Integer intValue;
    private final Boolean booleanValue;
    private final Long userValue;
    private final Long channelValue;
    private final Long roleValue;
    private final Long mentionableValue;
    private final Double numberValue;

    private final List<SlashCommandInteractionOption> options;

    /**
     * Class constructor.
     *
     * @param api      The DiscordApi instance.
     * @param jsonData The json data of the option.
     */
    public SlashCommandInteractionOptionImpl(DiscordApi api, JsonNode jsonData) {
        this.api = api;
        name = jsonData.get("name").asText();
        JsonNode valueNode = jsonData.get("value");


        String stringRepresentation = null;
        String stringValue = null;
        Integer intValue = null;
        Boolean booleanValue = null;
        Long userValue = null;
        Long channelValue = null;
        Long roleValue = null;
        Long mentionableValue = null;
        Double numberValue = null;

        switch (jsonData.get("type").asInt()) {
            case 3:
                stringValue = valueNode.asText();
                stringRepresentation = stringValue;
                break;
            case 4:
                intValue = valueNode.asInt();
                stringRepresentation = String.valueOf(intValue);
                break;
            case 5:
                booleanValue = valueNode.asBoolean();
                stringRepresentation = String.valueOf(booleanValue);
                break;
            case 6:
                userValue = Long.parseLong(valueNode.asText());
                stringRepresentation = String.valueOf(userValue);
                break;
            case 7:
                channelValue = Long.parseLong(valueNode.asText());
                stringRepresentation = String.valueOf(channelValue);
                break;
            case 8:
                roleValue = Long.parseLong(valueNode.asText());
                stringRepresentation = String.valueOf(roleValue);
                break;
            case 9:
                mentionableValue = Long.parseLong(valueNode.asText());
                stringRepresentation = String.valueOf(mentionableValue);
                break;
            case 10:
                numberValue = valueNode.asDouble();
                stringRepresentation = String.valueOf(numberValue);
                break;
        }

        this.stringRepresentation = stringRepresentation;
        this.stringValue = stringValue;
        this.intValue = intValue;
        this.booleanValue = booleanValue;
        this.userValue = userValue;
        this.channelValue = channelValue;
        this.roleValue = roleValue;
        this.mentionableValue = mentionableValue;
        this.numberValue = numberValue;

        options = new ArrayList<>();
        if (jsonData.has("options") && jsonData.get("options").isArray()) {
            for (JsonNode optionJson : jsonData.get("options")) {
                options.add(new SlashCommandInteractionOptionImpl(api, optionJson));
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getStringRepresentationValue() {
        return Optional.ofNullable(stringRepresentation);
    }

    @Override
    public Optional<String> getStringValue() {
        return Optional.ofNullable(stringValue);
    }

    @Override
    public Optional<Integer> getIntValue() {
        return Optional.ofNullable(intValue);
    }

    @Override
    public Optional<Boolean> getBooleanValue() {
        return Optional.ofNullable(booleanValue);
    }

    @Override
    public Optional<User> getUserValue() {
        return Optional.ofNullable(userValue)
                .flatMap(api::getCachedUserById);
    }

    @Override
    public Optional<CompletableFuture<User>> requestUserValue() {
        return Optional.ofNullable(userValue)
                .map(api::getUserById);
    }

    @Override
    public Optional<ServerChannel> getChannelValue() {
        return Optional.ofNullable(channelValue)
                .flatMap(api::getServerChannelById);
    }

    @Override
    public Optional<Role> getRoleValue() {
        return Optional.ofNullable(roleValue)
                .flatMap(api::getRoleById);
    }

    @Override
    public Optional<Mentionable> getMentionableValue() {
        Optional<Mentionable> mentionable = Optional.empty();
        // No Optional#or() in Java 8 :(
        if (mentionableValue != null) {
            mentionable = api.getRoleById(mentionableValue).map(Mentionable.class::cast);
            if (mentionable.isPresent()) {
                return mentionable;
            }

            mentionable = api.getServerChannelById(mentionableValue).map(Mentionable.class::cast);
            if (mentionable.isPresent()) {
                return mentionable;
            }

            mentionable = api.getCachedUserById(mentionableValue).map(Mentionable.class::cast);
        }
        return mentionable;
    }

    @Override
    public Optional<Double> getNumberValue() {
        return Optional.ofNullable(numberValue);
    }

    @Override
    public Optional<CompletableFuture<Mentionable>> requestMentionableValue() {
        Optional<CompletableFuture<Mentionable>> cacheOptional = getMentionableValue()
                .map(CompletableFuture::completedFuture);
        if (cacheOptional.isPresent()) {
            return cacheOptional;
        }
        return Optional.ofNullable(mentionableValue)
                .map(api::getUserById).map(future -> future.thenApply(Mentionable.class::cast));
    }

    @Override
    public List<SlashCommandInteractionOption> getOptions() {
        return Collections.unmodifiableList(options);
    }
}
