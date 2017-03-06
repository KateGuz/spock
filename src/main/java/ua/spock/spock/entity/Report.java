package ua.spock.spock.entity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Report {
    private byte[] body;

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(body);
    }
}
