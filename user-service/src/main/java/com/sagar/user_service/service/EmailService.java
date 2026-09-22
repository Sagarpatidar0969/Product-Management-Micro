package com.sagar.user_service.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EmailService {

    @Value("${sendgrid.api-key}")
    private String apiKey;

    @Value("${sendgrid.from-email}")
    private String fromEmail;

    public void sendSaleEmail(List<String> recipients, String saleName) {

        try {

            // Remove null, empty and duplicate emails
            List<String> uniqueRecipients = recipients.stream()
                    .filter(email -> email != null && !email.isBlank())
                    .map(String::trim)
                    .distinct()
                    .toList();

            if (uniqueRecipients.isEmpty()) {
                throw new RuntimeException("No valid email recipients found");
            }

            Email from = new Email(fromEmail);

            String html = """
                    <html>
                    <body>

                    <h2>Big Billion Sale Started!</h2>

                    <p>Hello,</p>

                    <p>
                    Our <b>%s</b> has started.
                    </p>

                    <p>
                    Get exciting offers and discounts
                    on our products.
                    </p>

                    <br>

                    <p>Happy Shopping!</p>

                    </body>
                    </html>
                    """.formatted(saleName);

            Content content = new Content(
                    "text/html",
                    html
            );

            Mail mail = new Mail();

            mail.setFrom(from);

            mail.setSubject("Big Billion Sale Started!");

            mail.addContent(content);

            Personalization personalization = new Personalization();

// Fixed/verified email in TO
            personalization.addTo(
                    new Email(fromEmail)
            );

// ALL customers in BCC
            for (String recipient : uniqueRecipients) {

                personalization.addBcc(
                        new Email(recipient)
                );
            }

            mail.addPersonalization(personalization);

            // Send email
            SendGrid sendGrid = new SendGrid(apiKey);

            Request request = new Request();

            request.setMethod(Method.POST);

            request.setEndpoint("mail/send");

            request.setBody(mail.build());

            Response response = sendGrid.api(request);

            System.out.println(
                    "SendGrid Status Code: "
                            + response.getStatusCode()
            );

            System.out.println(
                    "SendGrid Response: "
                            + response.getBody()
            );

            if (response.getStatusCode() >= 400) {

                throw new RuntimeException(
                        "SendGrid Error: "
                                + response.getBody()
                );
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to send sale email",
                    e
            );
        }
    }
}