/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import sun.security.provider.MD5;

import org.apache.commons.codec.digest.DigestUtils;
import java.io.*;
import java.util.*;

/**
 *
 * @author kyokuto
 */
public class NewMainDiskDirFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File fileOne = new File("/home/kyokuto/デスクトップ/pdfTest");
        File fileTwe = new File("/home/kyokuto/デスクトップ/pdfTest_2");
        
        NewMainDiskDirFile newMainDiskDirFile = new NewMainDiskDirFile();
        System.out.println(newMainDiskDirFile.calcMD5HashForDir(new File("/home/kyokuto/デスクトップ/pdfTest"), true));
        System.out.println(newMainDiskDirFile.calcMD5HashForDir(new File("/home/kyokuto/デスクトップ/pdfTest_2"), true));
        try {
            List<File> pathList = findAllFile("/home/kyokuto/デスクトップ/pdfTest");
            List<File> pathList2 = findAllFile("/home/kyokuto/デスクトップ/pdfTest_2");

            Path pathOne = Paths.get("/home/kyokuto/デスクトップ/pdfTest");
            Path pathTwe = Paths.get("/home/kyokuto/デスクトップ/pdfTest_2");
            System.out.println(pathOne.relativize(pathTwe).toString());
            System.out.println(pathOne.resolve(pathTwe).toString());
            System.out.println(pathOne.resolveSibling(pathTwe).toString());
            System.out.println(pathOne.normalize().toString());
            System.out.println(pathOne.relativize(pathTwe).toString());

            for (File pathText : pathList) {
                System.out.println(pathText.toString());
            }
            Iterator<File> iterator = pathList.iterator();
            Iterator<File> iterator2 = pathList2.iterator();
            while (iterator.hasNext()) {
                String one = iterator.next().getName();
                String two = iterator2.next().getName();

                System.out.println(one.equals(two) + " " + one + " " + two);
                if ((one.equals(two)) == false) {
                    System.out.println("false");
                };
            }
        } catch (IOException ex) {
            Logger.getLogger(NewMainDiskDirFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
I had the same requirement and chose my 'directory hash' to be an MD5 hash of the concatenated streams of all (non-directory) files within the directory. As crozin mentioned in comments on [a similar question][1], you can use `SequenceInputStream` to act as a stream concatenating a load of other streams. I'm using [Apache Commons Codec][2] for the MD5 algorithm.
Basically, you recurse through the directory tree, adding `FileInputStream` instances to a `Vector` for non-directory files. `Vector` then conveniently has the `elements()` method to provide the `Enumeration` that `SequenceInputStream` needs to loop through. To the MD5 algorithm, this just appears as one `InputStream`.
A gotcha is that you need the files presented in the same order every time for the hash to be the same with the same inputs. The `listFiles()` method in `File` doesn't guarantee an ordering, so I sort by filename.
I was doing this for SVN controlled files, and wanted to avoid hashing the hidden SVN files, so I implemented a flag to avoid hidden files.
The relevant basic code is as below. (Obviously it could be 'hardened'.)
     */
    public String calcMD5HashForDir(File dirToHash, boolean includeHiddenFiles) {

        assert (dirToHash.isDirectory());
        Vector<FileInputStream> fileStreams = new Vector<FileInputStream>();

        System.out.println("Found files for hashing:");
        collectInputStreams(dirToHash, fileStreams, includeHiddenFiles);

        SequenceInputStream seqStream
                = new SequenceInputStream(fileStreams.elements());

        try {
            String md5Hash = DigestUtils.md5Hex(seqStream);
            seqStream.close();
            return md5Hash;
        } catch (IOException e) {
            throw new RuntimeException("Error reading files to hash in "
                    + dirToHash.getAbsolutePath(), e);
        }

    }

    private void collectInputStreams(File dir,
            List<FileInputStream> foundStreams,
            boolean includeHiddenFiles) {

        File[] fileList = dir.listFiles();
        Arrays.sort(fileList, // Need in reproducible order
                new Comparator<File>() {
            public int compare(File f1, File f2) {
                return f1.getName().compareTo(f2.getName());
            }
        });

        for (File f : fileList) {
            if (!includeHiddenFiles && f.getName().startsWith(".")) {
                // Skip it
            } else if (f.isDirectory()) {
                collectInputStreams(f, foundStreams, includeHiddenFiles);
            } else {
                try {
                    System.out.println("\t" + f.getAbsolutePath());
                    foundStreams.add(new FileInputStream(f));
                } catch (FileNotFoundException e) {
                    throw new AssertionError(e.getMessage()
                            + ": file should never not be found!");
                }
            }
        }

    }

    // [1]: https
    // ://stackoverflow.com/questions/9169137
    //[2]: http
    //://commons.apache.org/proper/commons-codec
// 再帰ファイル探訪(下記URLのコメント参考)
// https://qiita.com/neko_the_shadow/items/0bcee7643144dccb137d
    public static List<File> findAllFile(String absolutePath) throws IOException {
        return Files.walk(Paths.get(absolutePath))
                .map(path -> path.toFile())
                .filter(file -> file.isFile())
                .collect(Collectors.toList());
    }

//    第1引数で指定したディレクトリ下を第2引数(optional)で指定した階層まで再帰的に探索します。
//    下記の例ではdir1ディレクトリから第2階層までのディレクトリ、ファイルを探索します。
//    https://qiita.com/rubytomato@github/items/6880eab7d9c76524d112    
    public static void pathsGetSample() {
        Path startDir = Paths.get("C:", "dir1");
        try {
            Files.walk(startDir, 2).forEach(System.out::println);

        } catch (IOException ex) {
            Logger.getLogger(NewMainDiskDirFile.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        // → C:\dir1
//        →C:\dir1\dir2
//        → C:\dir1\dir2\dir4
//        → C:\dir1\dir2\dir5
//        → C:\dir1\dir2\file2.txt
//        → C:\dir1\dir3
//        → C:\dir1\dir3\dir6
//        → C:\dir1\dir3\dir7
//        → C:\dir1\dir3\file3.csv
//        → C:\dir1\file1.txt
    }

    // ファイル内容が一致しているか判定する
    // https://qiita.com/Tanaaaaan/items/1aad361bda880e9d5fc3
    // 一番シンプルな方法
// 大きなサイズのファイル比較には使えないかも。。
    public boolean fileCompare(String fileA, String fileB) throws IOException {
        return Arrays.equals(Files.readAllBytes(Paths.get(fileA)), Files.readAllBytes(Paths.get(fileB)));
    }

// 例外処理を変えてみた
    public boolean fileCompare2(String fileA, String fileB) {
        boolean bRet = false;
        try {
            bRet = Arrays.equals(Files.readAllBytes(Paths.get(fileA)), Files.readAllBytes(Paths.get(fileB)));
        } catch (IOException e) {
        }
        return bRet;
    }

// ファイルのサイズを最初にチェックしてみた
    public boolean fileCompare3(String fileA, String fileB) {
        boolean bRet = false;
        try {
            if (new File(fileA).length() != new File(fileA).length()) {
                return bRet;
            }
            byte[] byteA = Files.readAllBytes(Paths.get(fileA));
            byte[] byteB = Files.readAllBytes(Paths.get(fileB));
            bRet = Arrays.equals(byteA, byteB);
            if (!bRet) {
                System.out.println(new String(byteA, "UTF-8"));
                System.out.println(new String(byteB, "UTF-8"));
            }
        } catch (IOException e) {
        }
        return bRet;
    }

}
