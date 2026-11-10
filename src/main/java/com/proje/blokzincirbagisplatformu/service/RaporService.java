package com.proje.blokzincirbagisplatformu.service;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class RaporService {

    /**
     * Belirli bir bağış veya işlem için txHash içeren PDF dekontu üretir.
     */
    public byte[] bagisDekontuOlustur(String donorName, String campaignTitle, double amount, String txHash) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("BLOKZİNCİR TABANLI BAĞIŞ TAKİP PLATFORMU"));
            document.add(new Paragraph("------------------------------------------------------------------------"));
            document.add(new Paragraph("DEKONT / İŞLEM ONAY BELGESİ"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Bağışçı: " + donorName));
            document.add(new Paragraph("Kampanya: " + campaignTitle));
            document.add(new Paragraph("Bağış Tutarı: " + amount + " ETH / Test Token"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Blokzincir İşlem Özeti (TxHash):"));
            document.add(new Paragraph(txHash));
            document.add(new Paragraph("Doğrulama Bağlantısı: https://sepolia.etherscan.io/tx/" + txHash));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Bu belge sistem tarafından otomatik olarak üretilmiştir."));

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("PDF dekontu oluşturulurken hata meydana geldi: " + e.getMessage());
        }

        return out.toByteArray();
    }
}