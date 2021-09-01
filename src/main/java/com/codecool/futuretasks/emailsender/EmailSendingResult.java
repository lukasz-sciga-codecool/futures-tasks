package com.codecool.futuretasks.emailsender;

import java.util.Objects;

public class EmailSendingResult {
    private final boolean isSuccess;

    public EmailSendingResult(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailSendingResult that = (EmailSendingResult) o;
        return isSuccess == that.isSuccess;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isSuccess);
    }
}
