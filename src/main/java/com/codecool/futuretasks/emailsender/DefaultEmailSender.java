package com.codecool.futuretasks.emailsender;

import com.codecool.futuretasks.Product;

public class DefaultEmailSender implements EmailSender {

    private static final EmailSendingResult SUCCESS = new EmailSendingResult(true);
    private static final EmailSendingResult FAILURE = new EmailSendingResult(false);

    @Override
    public EmailSendingResult sendEmailAboutProduct(final Product product) {
        try {
            final String formatted = "Pretending to send email about product: %s".formatted(product);
            System.out.println(formatted);
            return SUCCESS;
        } catch (Exception e)
        {
            // typically, sending email can always go wrong, for example when you lost connectivity to internet
            // or message failes to be parsed. Since it's only printing to console, no exception will happen.
            return FAILURE;
        }
    }
}
