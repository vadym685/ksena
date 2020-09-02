package com.company.ksena.entity.server_constants;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "KSENA_SERVER_CONSTANTS")
@Entity(name = "ksena_ServerConstants")
public class ServerConstants extends StandardEntity {
    private static final long serialVersionUID = -8031588345643048385L;

    @Column(name = "GOOGLE_URL")
    protected String googleUrl;

    @Column(name = "GOOGLE_TOKEN")
    protected String googleToken;

    public String getGoogleToken() {
        return googleToken;
    }

    public void setGoogleToken(String googleToken) {
        this.googleToken = googleToken;
    }

    public String getGoogleUrl() {
        return googleUrl;
    }

    public void setGoogleUrl(String googleUrl) {
        this.googleUrl = googleUrl;
    }
}