/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Slip;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;


/**
 *
 * @author
 */
public class StructSheet {

    private String mainTitle; //
    private String subTitle; //
    private String url; // アクセス先、もしくはプログラム名
    private String userName; // パスワードと対になるユーザー名
    private String scanType; //ランダム分割、順読み分割、循環更新、使い捨て更新など
    private String comment; //
    private String thisPassCode; //シートそのものを暗号化する時のパスコード
    private String randomType; // 文字種の分類
    private String randomText; // 具体的な文字指定
    private int passLength; // ひとつあたりの文字数
    private String passCodeA; // 
    private String passCodeB; // 

    private static final String FILE_SEPARATOR=System.getProperty("file.separator");; 
    /**
     *
     * @param mainTitle
     * @param subTitle
     * @param url
     * @param userName
     * @param scanType
     * @param comment
     * @param thisPassCode
     * @param passCode
     */
    /*
    public static String getPassCode(String randomType, String randomText, int passLength) {
        if (randomType == null) {
            System.out.println("Null!");
        } else {
            switch (randomType) {
                case "Letters":
                    if (randomText == null || randomText.trim() == "") { //機能していない
                        return "------------------------";
                    }
                    return RandomStringUtils.random(passLength, randomText);
                case "Alphanumeric":
                    return RandomStringUtils.randomAlphanumeric(passLength);
                case "Ascii":
                    return RandomStringUtils.randomAscii(passLength);
                case "Alphabetic":
                    return RandomStringUtils.randomAlphabetic(passLength);
                case "Numeric":
                    return RandomStringUtils.randomNumeric(passLength);
            }
        }

        return "";
    }
    */
    public void createPdf(
            String mainTitle,
            String subTitle,
            String url,
            String userName,
            //String scanType,
            String comment,
            //String thisPassCode,
            String passCodeA,
            String passCodeB,
            String fileDir,
            Boolean noBarCodePrint
    ) throws IOException, DocumentException, RuntimeException {
        Document document = null;
        try {
            // step 1
            document = new Document(PageSize.A4, 60, 50, 50, 35);
            // step 2
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileDir));
            // step 3
            document.open();
            // step 4
            PdfContentByte cb = writer.getDirectContent();
            /*
             Properties props = new Properties();
             String jarPath = System.getProperty("java.class.path");
             String dirPath = jarPath.substring(0, jarPath.lastIndexOf(File.separator)+1);
             FontFactory.registerDirectory("/res");
             FontFactory.register("ipag.ttf");
             Font ipaGothic = FontFactory.getFont("ipag", BaseFont.IDENTITY_H, 
             BaseFont.EMBEDDED, 10); //10 is the size

             InputStream is = getClass().getResourceAsStream("/res/ipag.ttf");
             */

            Properties props = new Properties();
            String jarPath = System.getProperty("java.class.path");
            String dirPath = jarPath.substring(0, jarPath.lastIndexOf(File.separator) + 1);
            System.out.println(jarPath);
            System.out.println(dirPath);
            System.out.println(System.getProperty("user.dir"));


            Font ipaGothic = new Font(BaseFont.createFont(System.getProperty("user.dir") + FILE_SEPARATOR+"res"+FILE_SEPARATOR+"ipag.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 11);

            Font ipaGothic14 = new Font(BaseFont.createFont(System.getProperty("user.dir") + FILE_SEPARATOR+"res"+FILE_SEPARATOR+"ipag.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 14);

            //表を作成(2列)
            PdfPTable pdfPTable = new PdfPTable(2);

            pdfPTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfPTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.getDefaultCell().setFixedHeight(150);

            pdfPTable.setWidthPercentage(100f);

            int pdfPTableWidth[] = {10, 90};
            pdfPTable.setWidths(pdfPTableWidth);

            PdfPCell cell_1_1 = new PdfPCell(new Paragraph("件名", ipaGothic));
            cell_1_1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell_1_1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_1_1.setFixedHeight(50);
            PdfPCell cell_1_2 = new PdfPCell(new Paragraph(mainTitle, ipaGothic));
            cell_1_2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell_1_2.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell cell_2_1 = new PdfPCell(new Paragraph("種別", ipaGothic));
            cell_2_1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell_2_1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cell_2_2 = new PdfPCell(new Paragraph(subTitle, ipaGothic));
            cell_2_2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell_2_2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_2_2.setFixedHeight(50);
            pdfPTable.addCell(cell_1_1);
            pdfPTable.addCell(cell_1_2);
            pdfPTable.addCell(cell_2_1);
            pdfPTable.addCell(cell_2_2);

            PdfPCell cellUrlKey = new PdfPCell(new Paragraph("位置", ipaGothic));
            cellUrlKey.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellUrlKey.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellUrlKey.setRowspan(2);
            pdfPTable.addCell(cellUrlKey);

            PdfPCell cellUrlValue = new PdfPCell(new Paragraph(url, ipaGothic));
            Chunk chunk = new Chunk(url, ipaGothic); // このやりかたでしか組まれた文字列の長さが測れない
            System.out.println("行の長さ"+chunk.getWidthPoint());
            cellUrlValue.setVerticalAlignment(Element.ALIGN_MIDDLE);
            if (chunk.getWidthPoint() > 410) { // 複数行になりそうな場合は左詰めで
                cellUrlValue.setHorizontalAlignment(Element.ALIGN_LEFT);
            } else {                 // そうでない場合は中央で
                cellUrlValue.setHorizontalAlignment(Element.ALIGN_CENTER);
            }

            cellUrlValue.setFixedHeight(50);
            pdfPTable.addCell(cellUrlValue);

            if (url.length() != 0 && !noBarCodePrint) {
                /* 日本語非対応
                 BarcodeQRCode qr = new BarcodeQRCode(url, 50, 50, null);
                 PdfPCell cellUrlValueQr = new PdfPCell(qr.getImage());
                 cellUrlValueQr.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 cellUrlValueQr.setHorizontalAlignment(Element.ALIGN_CENTER);
                 cellUrlValueQr.setFixedHeight(80);
                 pdfPTable.addCell(cellUrlValueQr);
                 */
                Image image = ZxingUti.getQRCode(url); // 日本語対応 SHIFT_JIS
                com.itextpdf.text.Image iTextImage = com.itextpdf.text.Image.getInstance(image, null);
                PdfPCell cell = new PdfPCell(iTextImage);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setFixedHeight(100);
                pdfPTable.addCell(cell); // 日本語対応 SIFT_JIS
            } else {
                PdfPCell cellUrlValueQr = new PdfPCell(new Paragraph("", ipaGothic));
                cellUrlValueQr.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellUrlValueQr.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellUrlValueQr.setFixedHeight(80);
                pdfPTable.addCell(cellUrlValueQr);
            }

            PdfPCell cellUserNameKey = new PdfPCell(new Paragraph("ユーザ", ipaGothic));
            cellUserNameKey.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellUserNameKey.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellUserNameKey.setRowspan(2);
            pdfPTable.addCell(cellUserNameKey);

            PdfPCell cellUserNameValue = new PdfPCell(new Paragraph(userName, ipaGothic14));
            cellUserNameValue.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellUserNameValue.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellUserNameValue.setFixedHeight(30);
            pdfPTable.addCell(cellUserNameValue);

            if (userName.length() != 0 && !noBarCodePrint) {
                Barcode128 code128 = new Barcode128();
                code128.setCode(userName);
                code128.setFont(ipaGothic.getBaseFont());
                code128.setBarHeight(40f);
                PdfPCell cellUserNameValueBc = new PdfPCell(code128.createImageWithBarcode(cb, null, null));
                cellUserNameValueBc.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellUserNameValueBc.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellUserNameValueBc.setFixedHeight(80);
                pdfPTable.addCell(cellUserNameValueBc);
            } else {
                PdfPCell cellUserNameValueBc = new PdfPCell(new Paragraph("---", ipaGothic));
                cellUserNameValueBc.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellUserNameValueBc.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellUserNameValueBc.setFixedHeight(80);
                pdfPTable.addCell(cellUserNameValueBc);
            }

            PdfPCell cellPassCodeKey = new PdfPCell(new Paragraph("符号", ipaGothic));
            cellPassCodeKey.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellPassCodeKey.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellPassCodeKey.setRowspan(3);
            pdfPTable.addCell(cellPassCodeKey);

            PdfPCell cellPassCodeValue = new PdfPCell(new Paragraph(passCodeA + passCodeB, ipaGothic14));
            cellPassCodeValue.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellPassCodeValue.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellPassCodeValue.setFixedHeight(30);
            pdfPTable.addCell(cellPassCodeValue);

            if (passCodeA.length() != 0 && !noBarCodePrint) {
                Barcode128 code128 = new Barcode128();
                code128.setCode(passCodeA);
                code128.setFont(ipaGothic.getBaseFont());
                code128.setBarHeight(40f);
                PdfPCell cellPassCodeA_Bc = new PdfPCell(code128.createImageWithBarcode(cb, null, null));
                cellPassCodeA_Bc.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellPassCodeA_Bc.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellPassCodeA_Bc.setFixedHeight(80);
                pdfPTable.addCell(cellPassCodeA_Bc);
            } else {
                PdfPCell cellPassCodeA_Bc = new PdfPCell(new Paragraph("---", ipaGothic));
                cellPassCodeA_Bc.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellPassCodeA_Bc.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellPassCodeA_Bc.setFixedHeight(80);
                pdfPTable.addCell(cellPassCodeA_Bc);
            }

            if (passCodeB.length() != 0 && !noBarCodePrint) {
                Barcode128 code128 = new Barcode128();
                code128.setCode(passCodeB);
                code128.setFont(ipaGothic.getBaseFont());
                code128.setBarHeight(40f);
                PdfPCell cellPassCodeB_Bc = new PdfPCell(code128.createImageWithBarcode(cb, null, null));
                cellPassCodeB_Bc.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellPassCodeB_Bc.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellPassCodeB_Bc.setFixedHeight(80);
                pdfPTable.addCell(cellPassCodeB_Bc);
            } else {
                PdfPCell cellPassCodeB_Bc = new PdfPCell(new Paragraph("---", ipaGothic));
                cellPassCodeB_Bc.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellPassCodeB_Bc.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellPassCodeB_Bc.setFixedHeight(80);
                pdfPTable.addCell(cellPassCodeB_Bc);
            }

            PdfPCell cellCommentKey = new PdfPCell(new Paragraph("摘要", ipaGothic));
            cellCommentKey.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellCommentKey.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(cellCommentKey);

            PdfPCell cellCommentValue = new PdfPCell(new Paragraph(comment, ipaGothic));
            cellCommentValue.setVerticalAlignment(Element.ALIGN_TOP);
            cellCommentValue.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellCommentValue.setFixedHeight(150);
            pdfPTable.addCell(cellCommentValue);

            PdfPCell cellIssueKey = new PdfPCell(new Paragraph("発行", ipaGothic));
            cellIssueKey.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellIssueKey.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(cellIssueKey);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String strDate = sdf.format(cal.getTime());
            PdfPCell cellIssueValue = new PdfPCell(new Paragraph(strDate, ipaGothic));
            cellIssueValue.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellIssueValue.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellIssueValue.setFixedHeight(20);
            pdfPTable.addCell(cellIssueValue);

            //表を文章に追加する
            document.add(pdfPTable);
            /*

             // CODE 128
             document.add(new Paragraph("件名 : " + mainTitle, ipaGothic));
             document.add(new Paragraph("分類 : " + subTitle, ipaGothic));
             document.add(new Paragraph("-------------------------------------------------------"));
             document.add(new Paragraph("発行 " + strDate));
             document.add(new Paragraph("-------------------------------------------------------"));


             BaseFont bf = BaseFont.createFont(BaseFont.COURIER, BaseFont.WINANSI, BaseFont.EMBEDDED);
             Font font = new Font(bf, 12);
             document.add(new Paragraph("指定", ipaGothic));
             document.add(new Paragraph(url, ipaGothic));
             code128.setCode(url);
             code128.setFont(bf);
             code128.setX(1);
             //document.add(code128.createImageWithBarcode(cb, null, null));

             document.add(new Paragraph("USER", ipaGothic));
             if (userName.length() != 0) {
             document.add(new Paragraph(userName, ipaGothic));
             code128.setCode(userName);
             code128.setFont(bf);
             code128.setBarHeight(40f);
             document.add(code128.createImageWithBarcode(cb, null, null));
             }

             document.add(new Paragraph("CODE", ipaGothic));
             if (passCode.length() != 0) {
             document.add(new Paragraph(passCode, ipaGothic));
             code128.setCode(passCode);
             code128.setFont(bf);
             document.add(code128.createImageWithBarcode(cb, null, null));
             }

             document.add(new Paragraph("摘要", ipaGothic));
             document.add(new Paragraph(comment, ipaGothic));
             */
            // step 5
            document.close();
        } catch (RuntimeException ex) {
            document.close();
            throw ex;

        }
    }

}
