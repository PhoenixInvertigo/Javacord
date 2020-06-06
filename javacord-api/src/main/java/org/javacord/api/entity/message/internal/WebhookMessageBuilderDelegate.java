package org.javacord.api.entity.message.internal;

import org.javacord.api.entity.Icon;
import org.javacord.api.entity.Mentionable;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageDecoration;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.message.mention.AllowedMentions;
import org.javacord.api.entity.webhook.IncomingWebhook;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

/**
 * This class is internally used by the {@link MessageBuilder} to create messages.
 * You usually don't want to interact with this object.
 */
public interface WebhookMessageBuilderDelegate {

    /**
     * Appends code to the message.
     *
     * @param language The language, e.g. "java".
     * @param code The code.
     */
    void appendCode(String language, String code);

    /**
     * Appends a sting with or without decoration to the message.
     *
     * @param message The string to append.
     * @param decorations The decorations of the string.
     */
    void append(String message, MessageDecoration... decorations);

    /**
     * Appends a mentionable entity (usually a user or channel) to the message.
     *
     * @param entity The entity to mention.
     */
    void append(Mentionable entity);

    /**
     * Appends the string representation of the object (calling {@link String#valueOf(Object)} method) to the message.
     *
     * @param object The object to append.
     * @see StringBuilder#append(Object)
     */
    void append(Object object);

    /**
     * Appends a new line to the message.
     */
    void appendNewLine();

    /**
     * Sets the content of the message.
     * This method overwrites all previous content changes
     * (using {@link #append(String, MessageDecoration...)} for example).
     *
     * @param content The new content of the message.
     */
    void setContent(String content);

    /**
     * Adds the embed to the message.
     *
     * @param embed The embed to add.
     */
    void addEmbed(EmbedBuilder embed);

    /**
     * Adds the embeds to the message.
     *
     * @param embeds The embeds to add.
     */
    void addEmbeds(EmbedBuilder[] embeds);

    /**
     * Removes the embed from the message.
     *
     * @param embed The embed to remove.
     */
    void removeEmbed(EmbedBuilder embed);

    /**
     * Removes the embeds from the message.
     *
     * @param embeds The embeds to remove.
     */
    void removeEmbeds(EmbedBuilder[] embeds);

    /**
     * Sets if the message should be text to speech.
     *
     * @param tts Whether the message should be text to speech or not.
     */
    void setTts(boolean tts);

    /**
     * Sets the display name of the webhook.
     *
     * @param displayName The display name of the webhook.
     */
    void setDisplayName(String displayName);

    /**
     * Sets the display avatar of the webhook.
     *
     * @param avatarUrl The display avatar of the webhook.
     */
    void setDisplayAvatar(URL avatarUrl);

    /**
     * Sets the display avatar of the webhook.
     *
     * @param avatar The display avatar of the webhook.
     */
    void setDisplayAvatar(Icon avatar);

    /**
     * Adds a file to the message.
     *
     * @param image The image to add as an attachment.
     * @param fileName The file name of the image.
     * @see #addAttachment(BufferedImage, String)
     */
    void addFile(BufferedImage image, String fileName);

    /**
     * Adds a file to the message.
     *
     * @param file The file to add as an attachment.
     * @see #addAttachment(File)
     */
    void addFile(File file);

    /**
     * Adds a file to the message.
     *
     * @param icon The icon to add as an attachment.
     * @see #addAttachment(Icon)
     */
    void addFile(Icon icon);

    /**
     * Adds a file to the message.
     *
     * @param url The url of the attachment.
     * @see #addAttachment(URL)
     */
    void addFile(URL url);

    /**
     * Adds a file to the message.
     *
     * @param bytes The bytes of the file.
     * @param fileName The name of the file.
     * @see #addAttachment(byte[], String)
     */
    void addFile(byte[] bytes, String fileName);

    /**
     * Adds a file to the message.
     *
     * @param stream The stream of the file.
     * @param fileName The name of the file.
     * @see #addAttachment(InputStream, String)
     */
    void addFile(InputStream stream, String fileName);

    /**
     * Adds a spoiler attachment to the message.
     *
     * @param file The file to add as an attachment.
     */
    void addFileAsSpoiler(File file);

    /**
     * Adds a spoiler attachment to the message.
     *
     * @param icon The icon to add as an attachment.
     */
    void addFileAsSpoiler(Icon icon);

    /**
     * Adds a spoiler attachment to the message.
     *
     * @param url The url of the attachment.
     */
    void addFileAsSpoiler(URL url);

    /**
     * Adds an attachment to the message.
     *
     * @param image The image to add as an attachment.
     * @param fileName The file name of the image.
     */
    void addAttachment(BufferedImage image, String fileName);

    /**
     * Adds an attachment to the message.
     *
     * @param file The file to add as an attachment.
     */
    void addAttachment(File file);

    /**
     * Adds an attachment to the message.
     *
     * @param icon The icon to add as an attachment.
     */
    void addAttachment(Icon icon);

    /**
     * Adds an attachment to the message.
     *
     * @param url The url of the attachment.
     */
    void addAttachment(URL url);

    /**
     * Adds an attachment to the message.
     *
     * @param bytes The bytes of the file.
     * @param fileName The name of the file.
     */
    void addAttachment(byte[] bytes, String fileName);

    /**
     * Adds an attachment to the message.
     *
     * @param stream The stream of the file.
     * @param fileName The name of the file.
     */
    void addAttachment(InputStream stream, String fileName);

    /**
     * Adds a spoiler attachment to the message.
     *
     * @param file The file to add as an attachment.
     */
    void addAttachmentAsSpoiler(File file);

    /**
     * Adds a spoiler attachment to the message.
     *
     * @param icon The icon to add as an attachment.
     */
    void addAttachmentAsSpoiler(Icon icon);

    /**
     * Adds a spoiler attachment to the message.
     *
     * @param url The url of the attachment.
     */
    void addAttachmentAsSpoiler(URL url);

    /**
     * Controls the mention behavior.
     *
     * @param allowedMentions The mention object to specify which mention should ping.
     */
    void setAllowedMentions(AllowedMentions allowedMentions);

    /**
     * Gets the {@link StringBuilder} which is used to build the message.
     *
     * @return The StringBuilder which is used to build the message.
     */
    StringBuilder getStringBuilder();

    /**
     * Sends the message.
     *
     * @param webhook The webhook from which the message should be sent.
     * @return The sent message.
     */
    CompletableFuture<Message> send(IncomingWebhook webhook);
}