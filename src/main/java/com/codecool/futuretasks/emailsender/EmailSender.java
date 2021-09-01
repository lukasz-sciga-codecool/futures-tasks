package com.codecool.futuretasks.emailsender;

import com.codecool.futuretasks.Product;

public interface EmailSender {
    EmailSendingResult sendEmailAboutProduct(final Product product);
}
