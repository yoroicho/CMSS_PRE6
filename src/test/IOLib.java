package test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
//import static org.eclipse.persistence.sessions.SessionProfiler.Logging;

// http://dededemio.blog.fc2.com/blog-entry-58.html

public class IOLib {
	/**
	 * ディレクトリをfromからtoにコピーします<br>
	 * サブディレクトリの中身も全てコピーします<br>
	 * コピー先が存在する場合例外を出します．<br>
	 * @param from コピー元ディレクトリ名
	 * @param to コピー先ディレクトリ名
	 */
	public static void copy(String from, String to)
	{
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