package com.johnakins.tickets.services.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.johnakins.tickets.domain.entity.QrCode;
import com.johnakins.tickets.domain.entity.Ticket;
import com.johnakins.tickets.domain.enums.QrCodeStatusEnum;
import com.johnakins.tickets.exceptions.eventTicketExceptions.QrCodeGenerationException;
import com.johnakins.tickets.repositories.QrCodeRepository;
import com.johnakins.tickets.services.QrCodeService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class QrCodeServiceImpl implements QrCodeService {

    private static final Logger log = LoggerFactory.getLogger(QrCodeServiceImpl.class);
    private final QRCodeWriter qrCodeWriter;
    private QrCodeRepository qrCodeRepository;
    private static final int QR_HEIGHT = 300;
    private static final int QR_WIDTH = 300;

    @Override
    public QrCode generateQrCode(Ticket ticket) {
        try{
            UUID uniqueId = UUID.randomUUID();
            String qrCodeImage = generateQrCodeImage(uniqueId);

            QrCode qrCode = new QrCode();
            qrCode.setId(uniqueId);
            qrCode.setStatus(QrCodeStatusEnum.ACTIVE);
            qrCode.setValue(qrCodeImage);
            qrCode.setTicket(ticket);

            return qrCodeRepository.saveAndFlush(qrCode);
        } catch (IOException | WriterException ex){
            throw new QrCodeGenerationException(ex);
        }
    }

    @Override
    public byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId) {
        QrCode qrCode = qrCodeRepository.findByTicketIdAndTicketPurchaseId(ticketId, userId)
                .orElseThrow(QrCodeGenerationException::new);
        try {
            return Base64.getDecoder().decode(qrCode.getValue());
        } catch (IllegalArgumentException ex) {
            log.error("Invalid base64 QR Code for ticket ID: {}", ticketId, ex);
            throw new QrCodeGenerationException();
        }
    }

    private String generateQrCodeImage(UUID uniqueId) throws WriterException, IOException {
        BitMatrix bitMatrix = qrCodeWriter.encode(
                uniqueId.toString(),
                BarcodeFormat.QR_CODE,
                QR_WIDTH,
                QR_HEIGHT);

        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            ImageIO.write(qrCodeImage, "PNG", baos);
            byte[] imageBytes = baos.toByteArray();

            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }
}
