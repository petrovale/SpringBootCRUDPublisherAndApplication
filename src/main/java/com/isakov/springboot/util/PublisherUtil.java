package com.isakov.springboot.util;

import com.isakov.springboot.model.Publisher;
import com.isakov.springboot.to.PublisherTo;

public class PublisherUtil {
    private PublisherUtil() {
    }

    public static PublisherTo asTo(Publisher publisher) {
        return new PublisherTo(publisher.getId(), publisher.getName(), publisher.getPasswordHash());
    }
}
