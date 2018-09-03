/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Slip;

import DB.UnitDTO;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Header;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import common.SystemPropertiesItem;
import java.awt.Desktop;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class StructUnitSlip {

    /**
     * Unitを出力するため専用。 他の情報と共に制作プランとして出力する事も視野に入れる。
     */
    /*
    private TextField textFieldId;
    private DatePicker datePickerClose;
    private DatePicker datePickerCut;
    private DatePicker datePickerEtd;
    private TextField textFieldMainTitleId;
    private TextArea textAreaTitle;
    private DatePicker datePickerMtg;
    private TextArea textAreaMainTitleName;
    private TextArea textAreaSeriesName;
    private TextArea textAreaOverallSeriesName;
    private TextField textFieldOverallSeriesId;
    private TextField textFieldSeriesId;
    private TextArea textAreaRemark;
    private TextArea textAreaCreator;
     */
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String UNIT_BASE = SystemPropertiesItem.SHIP_BASE;

    /**
     * @param unitDTO
     * @param overallSeriesId
     * @param overallSeriesName
     * @param seriesId
     * @param seriesName
     * @param mainTitleName
     * @throws java.io.IOException
     * @throws com.itextpdf.text.DocumentException
     */
    public static void creatSlip(
            UnitDTO unitDTO,
            String overallSeriesId,
            String overallSeriesName,
            String seriesId,
            String seriesName,
            String mainTitleName
    ) throws IOException, DocumentException, RuntimeException {
        Document document = null;
        // ZonedDateTime structZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        String fileFullDir
                = UNIT_BASE
                + FILE_SEPARATOR
                + String.valueOf(unitDTO.getId())
                + FILE_SEPARATOR
                + String.valueOf(unitDTO.getId())
                /*                
                ファイル名に日付けを入れれば世代管理できるが、時間が経過したあと
                UNIT名称だけでpdfを特定することが難しくなる。
                また、2018-08-15T15:16:10.708+09:00 ←このフォーマットパターン
                ではWindows環境でファイルを作成できなかった。
                + "_"
                // + DateTimeFormatter.ISO_OFFSET_DATE_TIME
                DateTimeFormatter.
                //        .format(ZonedDateTime.now(ZoneId.systemDefault()))
                 */
                + "-"
                + DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")
                       // .format(structZonedDateTime)
                        
                .format(unitDTO.getTimestamp().toLocalDateTime())
                + ".pdf";
        System.out.println("PDF dir " + fileFullDir);
        try {
            // step 1 紙の端ギリギリに印刷することもあるので、余白は0とする。
            document = new Document(PageSize.A4, 90, 90, 90, 90);
            // step 2 
            PdfWriter writer = PdfWriter.getInstance(
                    document,
                    new FileOutputStream(fileFullDir)
            );
            // step 3
            document.open();
            // step 4
            PdfContentByte cb = writer.getDirectContent();
            // タイトル設定、画面表示のみ表示された。
            document.addTitle(String.valueOf(unitDTO.getId())
                    +"@"
                   // + DateTimeFormatter.ISO_ZONED_DATE_TIME
                      + DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")
                            .format(unitDTO.getTimestamp().toLocalDateTime()));

            // 印刷するためのヘッダーとフッターを設定
            // https://stackoverflow.com/questions/19856583/how-to-add-header-and-footer-to-my-pdf-using-itext-in-java
            class HeaderFooterPageEvent extends PdfPageEventHelper {

                @Override
                public void onStartPage(PdfWriter writer, Document document) {
                    ColumnText.showTextAligned(writer.getDirectContent(),
                             Element.ALIGN_CENTER, new Phrase("Top Left"), 30, 800, 0);
                    ColumnText.showTextAligned(writer.getDirectContent(),
                             Element.ALIGN_CENTER, new Phrase("Top Right"), 550, 800, 0);
                }

                @Override
                public void onEndPage(PdfWriter writer, Document document) {
                    ColumnText.showTextAligned(writer.getDirectContent(),
                            Element.ALIGN_CENTER,
                            new Phrase(String.valueOf(unitDTO.getId())
                                    +"@"
                                    + DateTimeFormatter.ofPattern("yyyy'/'MM'/'dd'-'HH':'mm':'ss':'SSS")
                                            .format(unitDTO.getTimestamp().toLocalDateTime())), 150, 30, 0);
                    ColumnText.showTextAligned(writer.getDirectContent(),
                            Element.ALIGN_CENTER,
                            new Phrase("page " + document.getPageNumber()), 550, 30, 0);
                }
            }

            HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            // 最初のページはフッダーしか表示されたないが、そういう文化なのだろう。
            writer.setPageEvent(event);
            // ヘッダーフッターの作成行程終わり。

            // Properties props = new Properties();
            String jarPath = System.getProperty("java.class.path");
            String dirPath = jarPath.substring(0, jarPath.lastIndexOf(File.separator) + 1);
            System.out.println(jarPath);
            System.out.println(dirPath);
            System.out.println(System.getProperty("user.dir"));

            Font ipaGothic = new Font(BaseFont.createFont(System.getProperty("user.dir") + FILE_SEPARATOR + "res" + FILE_SEPARATOR + "ipag.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 11);

            Font ipaGothic14 = new Font(BaseFont.createFont(System.getProperty("user.dir") + FILE_SEPARATOR + "res" + FILE_SEPARATOR + "ipag.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 14);

            
            PdfPTable pdfPTable = new PdfPTable(2);

            pdfPTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfPTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.getDefaultCell().setFixedHeight(150);

            pdfPTable.setWidthPercentage(100f);
            
            
            int pdfPTableWidth[] // 2列の割合
                    = {10, 90};        
            pdfPTable.setWidths(pdfPTableWidth);
         

            PdfPCell cell_1_1 = new PdfPCell(new Paragraph("船社", ipaGothic));
            cell_1_1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell_1_1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_1_1.setFixedHeight(50);
            PdfPCell cell_1_2 = new PdfPCell(new Paragraph(
                    overallSeriesId+":"+overallSeriesName, ipaGothic));
            cell_1_2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell_1_2.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell cell_2_1 = new PdfPCell(new Paragraph("航路", ipaGothic));
            cell_2_1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell_2_1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_2_1.setFixedHeight(50);
            PdfPCell cell_2_2 = new PdfPCell(new Paragraph(
                    seriesId+":"+seriesName, ipaGothic));
            cell_2_2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell_2_2.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            PdfPCell cell_3_1 = new PdfPCell(new Paragraph("本船", ipaGothic));
            cell_3_1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell_3_1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_3_1.setFixedHeight(50);
            PdfPCell cell_3_2 = new PdfPCell(new Paragraph(
                    unitDTO.getMaintitleId()+":"+mainTitleName, ipaGothic));
                    cell_3_2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell_3_2.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            
         
            PdfPCell cell_4_1 = new PdfPCell(new Paragraph("UNIT", ipaGothic));
            cell_2_1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell_2_1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cell_4_2 = new PdfPCell(new Paragraph(unitDTO.toString(), ipaGothic));
            cell_2_2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell_2_2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_2_2.setFixedHeight(50);
            
            pdfPTable.addCell(cell_1_1);
            pdfPTable.addCell(cell_1_2);
            pdfPTable.addCell(cell_2_1);
            pdfPTable.addCell(cell_2_2);
            pdfPTable.addCell(cell_3_1);
            pdfPTable.addCell(cell_3_2);
            pdfPTable.addCell(cell_4_1);
            pdfPTable.addCell(cell_4_2);
            // pdfPTable.addCell(cell_1_2);

            /*
            pdfPTable.addCell(cell_2_1);
            pdfPTable.addCell(cell_2_2);

            PdfPCell cellUrlKey = new PdfPCell(new Paragraph("集約情報", ipaGothic));
            cellUrlKey.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellUrlKey.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellUrlKey.setRowspan(2);
            pdfPTable.addCell(cellUrlKey);

            PdfPCell cellUrlValue = new PdfPCell(new Paragraph(compData, ipaGothic));
            Chunk chunk = new Chunk(compData, ipaGothic); // このやりかたでしか組まれた文字列の長さが測れない
            System.out.println("行の長さ" + chunk.getWidthPoint());
            cellUrlValue.setVerticalAlignment(Element.ALIGN_MIDDLE);
            if (chunk.getWidthPoint() > 410) { // 複数行になりそうな場合は左詰めで
                cellUrlValue.setHorizontalAlignment(Element.ALIGN_LEFT);
            } else {                 // そうでない場合は中央で
                cellUrlValue.setHorizontalAlignment(Element.ALIGN_CENTER);
            }

            cellUrlValue.setFixedHeight(50);
            pdfPTable.addCell(cellUrlValue);
             */
            Boolean noBarCodePrint = false; // もしも付与の可否を行う場合のダミー
            if ((unitDTO.getMaintitleId() + unitDTO.getTitle()).length() != 0 && !noBarCodePrint) {
                Image image = ZxingUti.getQRCode(unitDTO.toString()); // 日本語対応 UTF-8
                com.itextpdf.text.Image iTextImage = com.itextpdf.text.Image.getInstance(image, null);
                PdfPCell cell = new PdfPCell(iTextImage);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setFixedHeight(100);
                pdfPTable.addCell(cell); // 日本語対応 UTF-8
            } else {
                PdfPCell cellUrlValueQr = new PdfPCell(new Paragraph("QRなし", ipaGothic));
                cellUrlValueQr.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellUrlValueQr.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellUrlValueQr.setFixedHeight(80);
                pdfPTable.addCell(cellUrlValueQr);
            }
            /*
            PdfPCell cellUserNameKey = new PdfPCell(new Paragraph("作成者", ipaGothic));
            cellUserNameKey.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellUserNameKey.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellUserNameKey.setRowspan(2);
            pdfPTable.addCell(cellUserNameKey);

            PdfPCell cellUserNameValue = new PdfPCell(new Paragraph(makerName, ipaGothic14));
            cellUserNameValue.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellUserNameValue.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellUserNameValue.setFixedHeight(30);
            pdfPTable.addCell(cellUserNameValue);

             */
            //pdfPTable.addCell("ID");
           
            // EANコードで作った方が数字に関しては最適化されているかも。
            if (String.valueOf(unitDTO.getId()).length() != 0 && !noBarCodePrint) {
                Barcode128 code128 = new Barcode128();
                code128.setCode(String.valueOf(unitDTO.getId()));
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
            /*
            PdfPCell cellPassCodeKey = new PdfPCell(new Paragraph("符号", ipaGothic));
            cellPassCodeKey.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellPassCodeKey.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellPassCodeKey.setRowspan(3);
            pdfPTable.addCell(cellPassCodeKey);

            PdfPCell cellPassCodeValue = new PdfPCell(
                    new Paragraph(id + "-" + divDateTime, ipaGothic14));
            cellPassCodeValue.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellPassCodeValue.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellPassCodeValue.setFixedHeight(30);
            pdfPTable.addCell(cellPassCodeValue);

            if (id.length() != 0 && !noBarCodePrint) {
                Barcode128 code128 = new Barcode128();
                code128.setCode(id);
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

            if (divDateTime.length() != 0 && !noBarCodePrint) {
                Barcode128 code128 = new Barcode128();
                code128.setCode(divDateTime);
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

            PdfPCell cellIssueKey = new PdfPCell(new Paragraph("印刷", ipaGothic));
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
             */
            //表を文章に追加する
            document.add(pdfPTable);

            // step 5
            document.close();

            System.out.println(fileFullDir + "を作成しました。");
        } catch (RuntimeException ex) {
            // document.close();
            throw ex;
        }
    }
}
