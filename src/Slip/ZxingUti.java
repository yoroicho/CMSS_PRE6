package Slip;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author 00499
 */
public class ZxingUti {

    //QRコード生成時のエンコーディング
    private static final String ENCODING = "UTF-8";

    public static void main(String[] args) {
        try {
            String contents = "髙渡邊爻";
            BarcodeFormat format = BarcodeFormat.QR_CODE;
            int width = 160;
            int height = 160;

            Hashtable hints = new Hashtable();
            //hints.put(EncodeHintType.CHARACTER_SET, "shift_jis");
            hints.put(EncodeHintType.CHARACTER_SET, ENCODING);
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);

            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(contents, format, width, height, hints);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ImageIO.write(image, "png", new File("barcode.png"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public static Image getQRCode(String contents) {
        BufferedImage image = null;
        try {
            //String contents = "日本語表示日本語表示日本語表示日本語表示日本語表示";
            BarcodeFormat format = BarcodeFormat.QR_CODE;
            int width = 1;
            int height = 1;

            Hashtable hints = new Hashtable();
            //hints.put(EncodeHintType.CHARACTER_SET, "shift_jis");
            hints.put(EncodeHintType.CHARACTER_SET, ENCODING);
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);

            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(contents, format, width, height, hints);
            image = MatrixToImageWriter.toBufferedImage(bitMatrix);

            //ImageIO.write(image, "png", new File("barcode.png"));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return (Image) image;
    }
}
