package template.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FileTool {
	public static String readFile(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, "utf-8");
	}

	public static Set<String> readFileLineByLine(String path) {
		Set<String> a = new HashSet<>();
		try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
			String line;
			while ((line = br.readLine()) != null) {
				a.add(line.trim());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}


	public synchronized static void writeFile(String path, String text, Boolean shouldOverride) {

		File fout = createFolders(path, true);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fout, shouldOverride);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (text != null) {
			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			try {
				bw.write(text);
				bw.newLine();
				bw.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("File created at " + path);
	}

	synchronized public static void writeFile(String path, String text) {

		File fout = createFolders(path, true);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fout, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (text != null) {
			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			try {
				bw.write(text);

				bw.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("File created at " + path);
	}

	private static File createFolders(String path, boolean file) {
		File fout = new File(path);
		if (!fout.exists()) {
			fout.getParentFile().mkdirs();
			try {
				if (file)
					fout.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fout;
	}

	public static Set<String> readLines(String path, int numberLinesPerFile) {
		Set<String> resultSet = new HashSet<>();
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
			int takeCounter = 0;
			int part = 1;
			StringBuilder resultString = new StringBuilder();
			BufferedReader br = null;

			File file = new File(path);
			FileReader fr = new FileReader(file); // java.io.FileReader
			br = new BufferedReader(fr); // java.io.BufferedReader
			String line;
			while ((line = br.readLine()) != null) {

				if (takeCounter++ < numberLinesPerFile) {
					System.out.println(takeCounter);
					resultString.append(line);
				} else {
					FileTool.writeFile(path + "part" + part++, resultString.toString());
					resultString = new StringBuilder();
					takeCounter = 0;
				}
			}
			// note that Scanner suppresses exceptions
			if (sc.ioException() != null) {
				throw sc.ioException();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (sc != null) {
				sc.close();
			}
		}
		return resultSet;
	}


}