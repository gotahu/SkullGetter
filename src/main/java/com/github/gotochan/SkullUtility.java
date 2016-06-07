package com.github.gotochan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class SkullUtility
{
	
	/**
	 * jarファイルの中に格納されているテキストファイルを、jarファイルの外にコピーするメソッド<br/>
	 * WindowsだとS-JISで、MacintoshやLinuxだとUTF-8で保存されます。
	 *
	 * @param jarFile        jarファイル
	 * @param targetFile     コピー先
	 * @param sourceFilePath コピー元
	 * @author https://github.com/ucchyocean/
	 */
	public static void copyFileFromJar(File jarFile, File targetFile, String sourceFilePath)
	{
		JarFile jar = null;
		InputStream is = null;
		FileOutputStream fos = null;
		BufferedReader reader = null;
		BufferedWriter writer = null;
		
		File parent = targetFile.getParentFile();
		if (!parent.exists())
		{
			parent.mkdirs();
		}
		
		try
		{
			jar = new JarFile(jarFile);
			ZipEntry zipEntry = jar.getEntry(sourceFilePath);
			is = jar.getInputStream(zipEntry);
			
			fos = new FileOutputStream(targetFile);
			
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			writer = new BufferedWriter(new OutputStreamWriter(fos));
			
			String line;
			while ((line = reader.readLine()) != null)
			{
				writer.write(line);
				writer.newLine();
			}
			
		}
		catch (FileNotFoundException e)
		{
			SkullGetter.instance.getLogger().info(targetFile + "のコピーに失敗しました。");
			e.printStackTrace();
		}
		catch (IOException e)
		{
			SkullGetter.instance.getLogger().info(targetFile + "のコピーに失敗しました。");
			e.printStackTrace();
		}
		finally
		{
			if (jar != null)
			{
				try
				{
					jar.close();
				}
				catch (IOException e)
				{
					// do nothing.
				}
			}
			if (writer != null)
			{
				try
				{
					writer.flush();
					writer.close();
				}
				catch (IOException e)
				{
					// do nothing.
				}
			}
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					// do nothing.
				}
			}
			if (fos != null)
			{
				try
				{
					fos.flush();
					fos.close();
				}
				catch (IOException e)
				{
					// do nothing.
				}
			}
			if (is != null)
			{
				try
				{
					is.close();
				}
				catch (IOException e)
				{
					// do nothing.
				}
			}
		}
	}
}
