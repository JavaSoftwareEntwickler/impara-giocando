package com.imparagiocando.imparagiocando.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {
    MAIL_ACTIVATE_ACCOUNT("mail_activate_account");
    private final String value;
    EmailTemplateName(String value) {
        this.value = value;
    }
}
