/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileDirController;

import common.SystemPropertiesItem;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author tokyo
 */
public class FileIO {

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    public static void createFullSet(
            String id,
            String divDateTime,
            Boolean isExsistId, // idが存在することが前提となっている場合true
            Boolean isExsistDivDateTime, // DivDateTimeが存在することが前提となっている場合true
            List<String> text) {
        // 用途的にSHIP_BASEが正しいかどうかは分からない。
        // これから定数名を整理する必要がある。
        String idPath = SystemPropertiesItem.SHIP_BASE + FILE_SEPARATOR + id;
        File idPathFile = new File(idPath);
        File divDateTimeFile = new File(idPath + FILE_SEPARATOR + divDateTime);
        if (idPathFile.exists()) { // Exsist すでにある
            if (isExsistId) {

            }
            // Retake or update mode.
            if (divDateTimeFile.exists()) {
                // Update mode.
            } else {
                // Retake mode.
            }
        } else {
            // New id mode.
            //makeUnderDirNamed(id,SystemPropertiesItem.SHIP_BASE);

        }
    }

    public static String makeFileUnderDirContAppend(String title, List<String> text, String parentPath) {
        if (parentPath.isEmpty()) {
            JOptionPane.showMessageDialog(null, "検査エラー" + title + "を作成する親ディレクトリ<" + parentPath + ">が見つかりません。");
            return null;
        } else {
            String createdFile = parentPath + System.getProperty("file.separator") + title;
            System.out.println(createdFile);
            Path targetPaths = Paths.get(createdFile);
            try {
                Files.createFile(targetPaths);
                Files.write(targetPaths, text, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
            } catch (NoSuchFileException ex) {
                JOptionPane.showMessageDialog(null, "例外発生" + title + "を作成する親ディレクトリ<" + parentPath + ">が見つかりません。" + ex.toString());
                Logger
                        .getLogger(FileIO.class
                                .getName()).log(Level.SEVERE, null, ex);
                return null;

            } catch (IOException ex) {
                Logger.getLogger(FileIO.class
                        .getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "例外発生 " + ex.toString());
                return null;
            }
            return createdFile;
        }
    }

    public static String makeUnderDirNamed(String parent, String child, Boolean isExistParent, Boolean isExistChild) {
        if (new File(parent).exists()) { // 親はなければならない
            String createdDir = parent + System.getProperty("file.separator") + child;
            if (new File(createdDir).exists()) { // 子は在ってはならない（今後はisExistChildで汎用性をもたせる）
                JOptionPane.showMessageDialog(null, "検査エラー" + child + "を作成する前に親ディレクトリ<" + parent + ">に見つかりました。");
                return null;
            } else {
                Path targetPaths = Paths.get(createdDir);
                try {
                    Files.createDirectory(targetPaths);
                } catch (NoSuchFileException ex) {
                    JOptionPane.showMessageDialog(null, "例外発生" + child + "か作成する親ディレクトリ<" + parent + ">に障害があります。" + ex.toString());
                    Logger.getLogger(FileIO.class
                            .getName()).log(Level.SEVERE, null, ex);
                    return null;

                } catch (IOException ex) {
                    Logger.getLogger(FileIO.class
                            .getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "例外発生 " + ex.toString());
                    return null;
                }
                return createdDir;
            }
        } else {
            JOptionPane.showMessageDialog(null, "検査エラー" + child + "を作成する親ディレクトリ<" + parent + ">が見つかりません。");
            return null;
        }
    }

    public static String makeUnderDirUUID(String header, String parentPath) {
        if (parentPath.isEmpty()) {
            JOptionPane.showMessageDialog(null, header + " : 親ディレクトリが見当たりません。");
            return null;
        } else {
            String file = UUID.randomUUID().toString();
            String createdDir = parentPath + System.getProperty("file.separator") + header + file;
            System.out.println(createdDir);
            Path targetPaths = Paths.get(createdDir);
            try {
                Files.createDirectory(targetPaths);
            } catch (NoSuchFileException ex) {
                JOptionPane.showMessageDialog(null, header + " : 指定された作成基盤<" + parentPath + ">が見つかりません。");
                Logger
                        .getLogger(FileIO.class
                                .getName()).log(Level.SEVERE, null, ex);
                return null;

            } catch (IOException ex) {
                Logger.getLogger(FileIO.class
                        .getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            return createdDir;
        }
    }
}
