package com.studying.jira_ai.ocr;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Tess4JOcrEngine implements OcrEngine {

  private final Tesseract tesseract;

  public Tess4JOcrEngine(
      @Value("${ocr.tessdata-path}") String tessdataPath,
      @Value("${ocr.languages:ukr+eng}") String languages
  ) {
    this.tesseract = new Tesseract();
    this.tesseract.setDatapath(tessdataPath);
    this.tesseract.setLanguage(languages);

    // Базові налаштування
    this.tesseract.setTessVariable("user_defined_dpi", "300");
    // PSM 6 = Assume a single uniform block of text (часто добре для скрінів)
    this.tesseract.setPageSegMode(6);
  }

  @Override
  public String ocrImage(byte[] imageBytes, String filename) {
    try {
      BufferedImage original = ImageIO.read(new ByteArrayInputStream(imageBytes));
      if (original == null) {
        return "";
      }

      BufferedImage pre = preprocess(original);
      String text = tesseract.doOCR(pre);
      return normalize(text);
    } catch (TesseractException te) {
      return "";
    } catch (Exception e) {
      return "";
    }
  }

  private BufferedImage preprocess(BufferedImage img) {
    // 1) upscale 1.6x
    double scale = 1.6;
    int w = (int) Math.round(img.getWidth() * scale);
    int h = (int) Math.round(img.getHeight() * scale);

    BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = scaled.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g.drawImage(img, 0, 0, w, h, null);
    g.dispose();

    // 2) grayscale + simple threshold
    BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
    Graphics2D g2 = out.createGraphics();
    g2.drawImage(scaled, 0, 0, null);
    g2.dispose();

    // simple binarization
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        int rgb = out.getRGB(x, y);
        int gray = rgb & 0xFF;
        int v = (gray < 160) ? 0 : 255;
        int bin = (v << 16) | (v << 8) | v;
        out.setRGB(x, y, (0xFF << 24) | bin);
      }
    }

    return out;
  }

  private String normalize(String s) {
    if (s == null) return "";
    String t = s.replace("\r\n", "\n").replace("\r", "\n");
    // прибрати надмірні порожні рядки
    t = t.replaceAll("\n{3,}", "\n\n");
    return t.trim();
  }
}
