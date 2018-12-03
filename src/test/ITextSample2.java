package test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author http://dev.livetp.com/java/0302.html
 */
public class ITextSample2 {

    /**
     * @param args
     */
    static final String FILE_SEPARATOR = System.getProperty("file.separator");

    public static void main(String[] args) {
        ITextSample2 itext = new ITextSample2();
        itext.exeCreate();
    }

    /**
     * PDF作成する
     */
    void exeCreate() {
        CreatePdf createPdf = new CreatePdf();
        createPdf.execute();
    }

    /**
     * PDF作成インナークラス
     */
    class CreatePdf extends PdfPageEventHelper {

        //-----------------------------------
        // フォント
        //-----------------------------------
        /**
         * 平成明朝体
         */
        private static final String MINCHO = "HeiseiMin-W3";
        /**
         * 平成角ゴシック体.
         */
        private static final String GOTHIC = "HeiseiKakuGo-W5";

        /**
         * 横書き指定
         */
        private static final String HORIZONTAL = "UniJIS-UCS2-H";
        /**
         * 縦書き指定
         */
        private static final String VERTICAL = "UniJIS-UCS2-V";
        /**
         * 横書き指定 & 英数を半角幅で印字
         */
        private static final String HORIZONTAL_HW = "UniJIS-UCS2-HW-H";
        /**
         * 縦書き指定 & 英数を半角幅で印字
         */
        private static final String VERTICAL_HW = "UniJIS-UCS2-HW-V";

        /**
         * ベースフォント 明朝体
         */
        private BaseFont mincho = null;
        /**
         * ベースフォント ゴシック体
         */
        private BaseFont gothic = null;
        private Font mincho15 = null;

        /**
         * 全ページ数
         */
        private int pageCount = 0;
        /**
         * １ページリスト表示行数
         */
        private final int LIST_ROW = 18;

        /**
         * 明細一覧テーブルの列幅定義
         */
        float widthC[] = {10, 40, 10, 10, 15, 15};

        /**
         * 明細用データ
         */
        private String[][] data
                = {{"C-001", "ミシン", "1", "台", "1,000", "1,000"}, {
                    "C-002", "発電機・電動機", "1", "台", "1,000", "1,000"}, {
                    "C-003", "電球", "1", "個", "1,000", "1,000"}, {
                    "C-004", "鉄道貨車", "1", "両", "400,000", "400,000"}, {
                    "C-005", "自動車", "1", "台", "400,000", "400,000"}, {
                    "C-006", "船舶", "1", "隻", "1,000,000", "1,000,000"}, {
                    "C-007", "釘・ボルト・ナット・リベット類", "1", "t", "2,000", "2,000"}, {
                    "C-008", "毛糸", "1", "t", "2,000", "2,000"}, {
                    "C-009", "絹糸", "1", "t", "2,000", "2,000"}, {
                    "C-010", "人絹糸", "1", "t", "2,000", "2,000"}, {
                    "C-011", "スフ糸", "1", "t", "2,000", "2,000"}, {
                    "C-012", "セメント", "1", "t", "2,000", "2,000"}, {
                    "C-013", "板ガラス", "1", "-", "2,000", "2,000"}, {
                    "C-014", "陶磁器", "1", "-", "2,000", "2,000"}, {
                    "C-015", "真珠（天然・養殖）", "1", "㎏", "8,000", "8,000"}, {
                    "C-016", "かじき", "1", "㎏", "8,000", "8,000"}, {
                    "C-017", "まぐろ", "1", "㎏", "8,000", "8,000"}, {
                    "C-018", "するめ", "1", "㎏", "5,000", "5,000"}, {
                    "C-019", "まぐろ（かん・びん詰）", "1", "㎏", "5,000", "5,000"}, {
                    "C-020", "かつを", "1", "㎏", "5,000", "5,000"}
                };

        public void execute() {

            try {

                //Font ipaGothic14 = new Font(BaseFont.createFont(System.getProperty("user.dir") + FILE_SEPARATOR + "res" + FILE_SEPARATOR + "ipag.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 14);
                // 明朝体，ゴシック体フォントを設定
                mincho = BaseFont.createFont(MINCHO, HORIZONTAL_HW, false);
                gothic = BaseFont.createFont(GOTHIC, HORIZONTAL, false);
                mincho15 = new Font(mincho, 15);
                Font min = new Font(mincho, 12);
                Font goth = new Font(gothic, 12);

                // Documentオブジェクトを生成
                // A4横、左右上下の余白
                Document document
                        = new Document(PageSize.A4.rotate(), 40, 40, 180, 30);
                // PdfWriterオブジェクトを生成
                PdfWriter writer
                        = PdfWriter.getInstance(
                                document,
                                new FileOutputStream(System.getProperty("user.dir") + FILE_SEPARATOR + "res" + FILE_SEPARATOR + "test.pdf"));
                writer.setPageEvent(this);
                // ドキュメントをオープン
                document.open();

                // ページ数の算出
                pageCount = data.length % LIST_ROW == 0
                        ? data.length / LIST_ROW : data.length / LIST_ROW + 1;

                // 明細テーブル
                PdfPTable tbl_dt = null;

                // 明細行を埋める
                for (int i = 0; i < data.length; i++) {

                    if (i == 0 || i % LIST_ROW == 0) {
                        tbl_dt = new PdfPTable(6);
                        tbl_dt.setWidths(widthC);
                        tbl_dt.setWidthPercentage(100f);

                        PdfPCell cellC1 = new PdfPCell(new Phrase("コード", mincho15));
                        cellC1.setGrayFill(0.9f);
                        cellC1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell cellC2 = new PdfPCell(new Phrase("品目・摘要", mincho15));
                        cellC2.setGrayFill(0.9f);
                        cellC2.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell cellC3 = new PdfPCell(new Phrase("数量", mincho15));
                        cellC3.setGrayFill(0.9f);
                        cellC3.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell cellC4 = new PdfPCell(new Phrase("単位", mincho15));
                        cellC4.setGrayFill(0.9f);
                        cellC4.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell cellC5 = new PdfPCell(new Phrase("単価", mincho15));
                        cellC5.setGrayFill(0.9f);
                        cellC5.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell cellC6 = new PdfPCell(new Phrase("金額", mincho15));
                        cellC6.setGrayFill(0.9f);
                        cellC6.setHorizontalAlignment(Element.ALIGN_CENTER);

                        tbl_dt.addCell(cellC1);
                        tbl_dt.addCell(cellC2);
                        tbl_dt.addCell(cellC3);
                        tbl_dt.addCell(cellC4);
                        tbl_dt.addCell(cellC5);
                        tbl_dt.addCell(cellC6);
                    }

                    PdfPCell cell = new PdfPCell(new Phrase(data[i][0], min));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tbl_dt.addCell(cell);
                    cell = new PdfPCell(new Phrase(data[i][1], min));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    tbl_dt.addCell(cell);
                    cell = new PdfPCell(new Phrase(data[i][2], min));
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tbl_dt.addCell(cell);
                    cell = new PdfPCell(new Phrase(data[i][3], min));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tbl_dt.addCell(cell);
                    cell = new PdfPCell(new Phrase(data[i][4], min));
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tbl_dt.addCell(cell);
                    cell = new PdfPCell(new Phrase(data[i][5], min));
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tbl_dt.addCell(cell);

                    if (i != 0 && i % LIST_ROW == LIST_ROW - 1) {
                        // 改頁
                        document.add(tbl_dt);
                        document.newPage();
                    }
                }

                PdfPCell cellC7 = new PdfPCell(new Phrase("小計", goth));
                cellC7.setGrayFill(0.9f);
                cellC7.setColspan(5);
                PdfPCell cellC8 = new PdfPCell(new Phrase("1,858,000", min));
                cellC8.setHorizontalAlignment(Element.ALIGN_RIGHT);
                PdfPCell cellC9 = new PdfPCell(new Phrase("消費税等", goth));
                cellC9.setGrayFill(0.9f);
                cellC9.setColspan(5);
                PdfPCell cellC10 = new PdfPCell(new Phrase("92900", min));
                cellC10.setHorizontalAlignment(Element.ALIGN_RIGHT);
                PdfPCell cellC11 = new PdfPCell(new Phrase("合計", goth));
                cellC11.setGrayFill(0.9f);
                cellC11.setColspan(5);
                PdfPCell cellC12 = new PdfPCell(new Phrase("1,950,900", min));
                cellC12.setHorizontalAlignment(Element.ALIGN_RIGHT);

                // 備考
                PdfPCell cellC13 = new PdfPCell(new Phrase("備考", goth));
                cellC13.setGrayFill(0.9f);
                cellC13.setRowspan(3);
                cellC13.setVerticalAlignment(Element.ALIGN_TOP);
                PdfPCell cellC14 = new PdfPCell(new Phrase("http://livetp.com", goth));
                cellC14.setRowspan(3);
                cellC14.setColspan(5);
                cellC14.setHorizontalAlignment(Element.ALIGN_LEFT);

                tbl_dt.addCell(cellC7);
                tbl_dt.addCell(cellC8);
                tbl_dt.addCell(cellC9);
                tbl_dt.addCell(cellC10);
                tbl_dt.addCell(cellC11);
                tbl_dt.addCell(cellC12);
                tbl_dt.addCell(cellC13);
                tbl_dt.addCell(cellC14);

                document.add(tbl_dt);

                // ドキュメントをクローズ
                document.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 改ページ処理。 このメソッドはページ作成終了時に呼び出される
         *
         * @param writer ＰＤＦライターオブジェクト
         * @param doc ドキュメントオブジェクト
         */
        @Override
        public void onEndPage(PdfWriter writer, Document doc) {

            try {
                // -------------------------
                // タイトルテーブル
                // -------------------------
                float H_TABLE_COLUMN_WIDH[] = {1, 3.5f};
                PdfPTable htable = new PdfPTable(H_TABLE_COLUMN_WIDH.length);
                PdfPCell cell = null;
                // テーブルの設定
                htable.getDefaultCell().setPadding(0f);
                htable.getDefaultCell().setLeading(16f, 0f);
                // テーブル全体幅の設定
                htable.setTotalWidth(doc.getPageSize().getWidth() - 80);
                // テーブル列幅の設定
                htable.setWidths(H_TABLE_COLUMN_WIDH);
                // その他設定
                htable.getDefaultCell().setBorder(0);
                htable.setHorizontalAlignment(Element.ALIGN_CENTER);
                htable.setLockedWidth(true);

                // タイトル
                cell = new PdfPCell(new Phrase(getTitle(writer, doc),
                        new Font(gothic, 20, Font.BOLD)));
                cell.setColspan(H_TABLE_COLUMN_WIDH.length);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingBottom(20f);
                cell.setBorder(0);
                htable.addCell(cell);

                // 対象カスタマ
                cell = new PdfPCell(new Phrase("ＸＸ ＸＸ 様",
                        new Font(mincho, 15, Font.UNDERLINE)));
                cell.setColspan(H_TABLE_COLUMN_WIDH.length);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(0);
                htable.addCell(cell);
                htable.writeSelectedRows(0, -1, 40, 570, writer.getDirectContent());

                if (writer.getPageNumber() == 1) {
                    int htable1w[] = {30, 70};
                    PdfPTable htable1 = new PdfPTable(htable1w.length);
                    // テーブルの設定
                    htable1.setSpacingBefore(20);
                    htable1.getDefaultCell().setPadding(5);
                    // テーブル全体幅を設定
                    htable1.setTotalWidth(doc.getPageSize().getWidth() - 500);
                    // テーブル全体幅を設定（パーセント）
                    //htable1.setWidthPercentage(80f);
                    // 各セル幅の比率を設定（パーセント）
                    htable1.setWidths(htable1w);

                    // その他設定
                    htable1.getDefaultCell().setBorder(1);
                    htable1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    htable1.setLockedWidth(true);

                    // 件名
                    cell = new PdfPCell(new Phrase("件名", mincho15));
                    cell.setGrayFill(0.9f);
                    cell.setFixedHeight(30f);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    htable1.addCell(cell);

                    cell = new PdfPCell(new Phrase("あ1Il|_\\O0", mincho15));
                    cell.setFixedHeight(30f);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    htable1.addCell(cell);

                    // 金額
                    cell = new PdfPCell(new Phrase("合計", mincho15));
                    cell.setGrayFill(0.9f);
                    cell.setFixedHeight(30f);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    htable1.addCell(cell);

                    cell = new PdfPCell(new Phrase("￥1,950,900", mincho15));
                    cell.setFixedHeight(30f);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setPaddingRight(5f);
                    htable1.addCell(cell);
                    htable1.writeSelectedRows(0, -1, 250, 490, writer.getDirectContent());
                }

            } catch (Throwable e) {
                e.printStackTrace();
            }

        }

        /**
         * ヘッダタイトルを取得する。
         *
         * @param writer PDFライターオブジェクト
         * @param doc ドキュメントオブジェクト
         */
        private String getTitle(PdfWriter writer, Document doc) {
            StringBuffer sb = new StringBuffer();
            sb.append("請　求　書");
            sb.append("（");
            sb.append(writer.getPageNumber());
            sb.append("／");
            sb.append(pageCount);
            sb.append("ページ）");
            return sb.toString();
        }

    }
}
