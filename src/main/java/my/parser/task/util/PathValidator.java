package my.parser.task.util;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * @author victor
 *
 */
public class PathValidator {
	public static boolean isValid(final String path) {
		if (path.isEmpty())
			return false;
		
		WeakReference<File> file = new WeakReference<File>(new File(path));
		if (!file.get().exists() || !file.get().isDirectory())
			return false;
		
		return true;
	}
}
