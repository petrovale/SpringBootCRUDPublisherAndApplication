package com.isakov.springboot;

import com.isakov.springboot.model.Publisher;
import com.isakov.springboot.to.PublisherTo;
import com.isakov.springboot.util.PublisherUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import static java.util.Objects.requireNonNull;

public class AuthorizedPublisher extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private PublisherTo publisherTo;

    public AuthorizedPublisher(Publisher publisher) {
        super(publisher.getName(), publisher.getPasswordHash(), true, true, true, true,
                AuthorityUtils.createAuthorityList(publisher.getRole()));
        this.publisherTo = PublisherUtil.asTo(publisher);
    }

    public static AuthorizedPublisher safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedPublisher) ? (AuthorizedPublisher) principal : null;
    }

    public static AuthorizedPublisher get() {
        AuthorizedPublisher authorizedUser = safeGet();
        requireNonNull(authorizedUser, "No authorized user found");
        return authorizedUser;
    }

    public long getId() {
        return publisherTo.getId();
    }

    public static long id() {
        return get().publisherTo.getId();
    }

    @Override
    public String toString() {
        return publisherTo.toString();
    }
}
