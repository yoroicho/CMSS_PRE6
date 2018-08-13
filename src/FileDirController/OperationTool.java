package FileDirController;

import DB.UnitDTO;
import common.SystemPropertiesItem;
import test.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
//import static org.eclipse.persistence.sessions.SessionProfiler.Logging;

// http://dededemio.blog.fc2.com/blog-entry-58.html
public class OperationTool {

    /**
     * 新たにUNITを格納するディレクトリを作成する<br>
     * 一番外側のガワとlogファイルを作る。<br>
     * 同名ディレクトリが存在する場合は例外を出します．<br>
     *
     * @param UnitDTO 情報取得用
     */
    public static Boolean createUnitDir(UnitDTO unitDTO) {
        List<String> text = new ArrayList<String>();
        text.add("ID: " + String.valueOf(unitDTO.getId()));
        if (unitDTO.getClose() != null) {
            text.add("CLOSE: " + unitDTO.getClose().toString());
        } else {
            text.add("CLOSE: NO SET.");
        }
        text.add("MAIN TITLE ID: " + unitDTO.getMaintitleId());
        text.add("TITLE: " + unitDTO.getTitle());
        if (unitDTO.getMtg() != null) {
            text.add("MTG: " + unitDTO.getMtg().toString());
        } else {
            text.add("MTG: NO SET.");
        }
        if (unitDTO.getCut() != null) {
            text.add("CUT: " + unitDTO.getCut().toString());
        } else {
            text.add("CUT: NO SET.");
        }
        if (unitDTO.getEtd() != null) {
            text.add("ETD: " + unitDTO.getEtd().toString());
        } else {
            text.add("ETD: NO SET.");
        }
        text.add("REMARK: " + unitDTO.getRemark());
        text.add("TEMPLATE ID: " + String.valueOf(unitDTO.getTemplateId()));
        text.add("VERSION ID: " + String.valueOf(unitDTO.getVersionId()));
        text.add("--- " + ZonedDateTime.now(ZoneId.systemDefault()).toString() + " ---");

        String unitBase = SystemPropertiesItem.SHIP_BASE;
        String unitDir = unitBase + System.getProperty("file.separator") + String.valueOf(unitDTO.getId());
        String logFile = unitDir + System.getProperty("file.separator") + String.valueOf(unitDTO.getId());
        try {
            if (Paths.get(unitDir).toFile().isDirectory()) {
                // すでにディレクトリがある場合は何もしない。本当はモードと照合すべき。
            } else {
                Files.createDirectory(Paths.get(unitDir)); // Unitのフォルダを作る。
            }
            if (Paths.get(logFile).toFile().isFile()) {
                // すでにファイルがある場合は何もしない。本当はモードと照合すべき。
            } else {
                Files.createFile(Paths.get(logFile)); // UnitのフォルダにlogFileを作る。 
            }
            Files.write(Paths.get(logFile), text, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(OperationTool.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    /**
     * ディレクトリをfromからtoにコピーします<br>
     * サブディレクトリの中身も全てコピーします<br>
     * コピー先が存在する場合例外を出します．<br>
     *
     * @param from コピー元ディレクトリ名
     * @param to コピー先ディレクトリ名
     */
    public static void copy(String from, String to) {
        //コピー元
        final Path fromPath = Paths.get(from);
        //コピー先
        final Path toPath = Paths.get(to);

        //FileVisitorの定義
        FileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                //ディレクトリをコピーする
                Files.copy(dir, toPath.resolve(fromPath.relativize(dir)), StandardCopyOption.COPY_ATTRIBUTES);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                //ファイルをコピーする
                Files.copy(file, toPath.resolve(fromPath.relativize(file)), StandardCopyOption.COPY_ATTRIBUTES);
                return FileVisitResult.CONTINUE;
            }
        };

        //ファイルツリーを辿ってFileVisitorの動作をさせる
        try {
            Files.walkFileTree(fromPath, visitor);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // Logging.logger.severe(e.getMessage());  //エラーメッセージ
            e.printStackTrace();
        }
    }
}
