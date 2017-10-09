package com.isakov.springboot.util;

import com.isakov.springboot.model.Publisher;
import com.isakov.springboot.to.PublisherTo;

public class PublisherUtil {
    private PublisherUtil() {
    }

    public static PublisherTo asTo(Publisher publisher) {
        return new PublisherTo(publisher.getId(), publisher.getName(), publisher.getPasswordHash());
    }

    public static Publisher createNewFromTo(PublisherTo newPublisher) {
        return new Publisher(newPublisher.getName(), newPublisher.getPassword(), "ROLE_USER");
    }

    public static Publisher prepareToSave(Publisher publisher) {
        publisher.setPasswordHash(PasswordUtil.encode(publisher.getPasswordHash()));
        return publisher;
    }
}
