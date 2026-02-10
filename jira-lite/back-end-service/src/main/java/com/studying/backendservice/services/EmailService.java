package com.studying.backendservice.services;

import com.studying.backendservice.models.Comment;
import com.studying.backendservice.models.Task;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender mailSender;

  public void sendTaskCreatedNotification(Task task) {
    String to = task.getInitiator().getEmail();
    String subject = "Ваша задача створена";
    String link = "http://localhost:4200/task/" + task.getId();
    String html = "<h3>Ваша задача створена</h3>" +
        "<p>Назва: <b>" + task.getSummary() + "</b></p>" +
        "<p>Опис: " + task.getDescription() + "</p>" +
        "<p><a href=\"" + link + "\">Відкрити задачу</a></p>";

    sendHtmlEmail(to, subject, html);
  }

  public void sendNewCommentNotification(Task task, Comment comment) {
    String to = task.getInitiator().getEmail();
    String subject = "Новий коментар до задачі";
    String link = "http://localhost:4200/task/" + task.getId();
    String html = "<h3>Новий коментар до вашої задачі</h3>" +
        "<p><b>" + comment.getAuthor().getUsername() + "</b> написав:</p>" +
        "<blockquote>" + comment.getDescription() + "</blockquote>" +
        "<p><a href=\"" + link + "\">Переглянути задачу</a></p>";

    sendHtmlEmail(to, subject, html);
  }

  public void sendHtmlEmail(String to, String subject, String htmlContent) {
    MimeMessage message = mailSender.createMimeMessage();

    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(htmlContent, true); // true = HTML

      mailSender.send(message);
    } catch (MessagingException e) {
      throw new RuntimeException("Не вдалося надіслати лист", e);
    }
  }
}

