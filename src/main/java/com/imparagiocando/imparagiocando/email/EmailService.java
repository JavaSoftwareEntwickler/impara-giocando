package com.imparagiocando.imparagiocando.email;

import com.imparagiocando.imparagiocando.user.MyUser;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final SpringTemplateEngine templateEngine;


    public void sendEMail(MyUser user, String newToken) throws IOException {
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", user.getUsername());
        properties.put("confirmationUrl", "http://localhost:8090/activate-account");
        properties.put("activation_code", newToken);
        Context context = new Context(Locale.ITALY,properties);
        context.setVariables(properties);
        System.out.println("************************************");
        System.out.println(EmailTemplateName.MAIL_ACTIVATE_ACCOUNT.name());
        System.out.println("*************************************");
        String template = templateEngine.process(EmailTemplateName.MAIL_ACTIVATE_ACCOUNT.name(), context);

        Email from = new Email("info@portaleapp.com", "PortaleApp");
        String subject = "PortaleApp - Account activation";
        Email to = new Email("marchesini.maxmilliam@gmail.com");
        Content content = new Content("text/html", template.toString());
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (IOException ex) {
            throw ex;
        }
    }


}
