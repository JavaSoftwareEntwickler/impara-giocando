package com.imparagiocando.imparagiocando.mail;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    MAIL_ACTIVATE_ACCOUNT("mail_activate_account");

    private final String name;
    EmailTemplateName(String name) {
        this.name = name;
    }
}
