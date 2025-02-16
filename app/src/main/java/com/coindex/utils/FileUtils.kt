package com.coindex.utils

import android.content.Context
import java.io.*

object FileUtils {

    /**
     * 在应用的私有目录下创建自定义文件夹
     * @param context 上下文
     * @param folderName 自定义文件夹名称
     * @return 自定义文件夹的File对象
     */
    fun createFolder(context: Context, folderName: String): File {
        val folder = File(context.filesDir, folderName)
        if (!folder.exists()) {
            folder.mkdirs()
        }
        return folder
    }

    /**
     * 将数据保存到自定义文件夹中的文件
     * @param context 上下文
     * @param folderName 自定义文件夹名称
     * @param fileName 文件名
     * @param data 要保存的数据
     */
    fun saveDataToFile(context: Context, folderName: String, fileName: String, data: String) {
        val folder = createFolder(context, folderName)
        val file = File(folder, fileName)
        try {
            FileOutputStream(file).use { outputStream ->
                BufferedWriter(OutputStreamWriter(outputStream)).use { writer ->
                    writer.write(data)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 从自定义文件夹中的文件读取数据
     * @param context 上下文
     * @param folderName 自定义文件夹名称
     * @param fileName 文件名
     * @return 文件内容
     */
    fun readDataFromFile(context: Context, folderName: String, fileName: String): String {
        val folder = createFolder(context, folderName)
        val file = File(folder, fileName)
        val content = StringBuilder()
        try {
            FileInputStream(file).use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).use { reader ->
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        content.append(line).append("\n")
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return content.toString()
    }

    /**
     * 删除自定义文件夹中的文件
     * @param context 上下文
     * @param folderName 自定义文件夹名称
     * @param fileName 文件名
     * @return 是否删除成功
     */
    fun deleteFile(context: Context, folderName: String, fileName: String): Boolean {
        val folder = createFolder(context, folderName)
        val file = File(folder, fileName)
        return file.delete()
    }
}