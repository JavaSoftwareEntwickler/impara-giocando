package com.imparagiocando.imparagiocando.auth;

import com.imparagiocando.imparagiocando.mail.EmailTemplateName;
import com.imparagiocando.imparagiocando.user.Token;
import com.imparagiocando.imparagiocando.user.TokenRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    public static final String CHAR_TOKEN = "0123456789";
    private final TokenRepository tokenRepository;
    private final MyUserRepository userRepository;
    private final SpringTemplateEngine templateEngine;

    public void sendValidationMail(MyUser user) throws IOException, MessagingException {
        var newToken = generateAndSaveActivationToken(user);

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
    private String generateAndSaveActivationToken(MyUser user) {
        // Generate a token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }
    private String generateActivationCode(int length) {
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(CHAR_TOKEN.length());
            codeBuilder.append(CHAR_TOKEN.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
    @Transactional
    public void activateAccount(String token) throws Exception {
        Token savedToken = tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationMail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been send to the same email address");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

}
