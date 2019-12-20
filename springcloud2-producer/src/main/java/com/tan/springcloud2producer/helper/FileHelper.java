package com.tan.springcloud2producer.helper;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileHelper {
    public static void main(String[] args) {

        FileHelper helper = new FileHelper();
        List<File> FS =  helper.getAllFile(new File("F:\\Code\\Java\\springcloud\\springcloud2-producer\\src\\main\\java\\com\\tan\\springcloud2producer"));

        System.out.println(helper.readToString("E:\\Elitel\\01_基础平台研发部\\01_svn\\08_GIS\\01_前端\\01_VUE组件\\01_elt-esri-map\\demo\\Vue-map\\src\\geojson1.json"));
    }

    public String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    public List<File> getAllFile(File file){
        File[] fs = file.listFiles();
        List<File> files = new LinkedList<>();
        for(File f:fs){
            if(f.isDirectory()){
                //若是目录，则递归打印该目录下的文件
                files.addAll(getAllFile(f));
            } else {
                files.add(f);
            }
        }
        return files;
    }

    // 验证字符串是否为正确路径名的正则表达式
    private static String matches = "[A-Za-z]:\\\\[^:?\"><*]*";
    // 通过 sPath.matches(matches) 方法的返回值判断是否正确
    // sPath 为路径字符串
    boolean flag = false;
    File file;

    /**
     *  创建目录
     * @param destDirName 需要创建目录的路径
     * @return
     */
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {// 判断目录是否存在
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {// 结尾是否以"/"结束
            destDirName = destDirName + File.separator;
        }
        if (dir.mkdirs()) {// 创建目标目录
            System.out.println("创建目录成功！" + destDirName);
            return true;
        } else {
            System.out.println("创建目录失败！");
            return false;
        }
    }

    /**
     *  根据路径删除指定的目录或文件，无论存在与否
     * @param deletePath 指定的文件的目录
     * @return
     */
    public boolean DeleteFolder(String deletePath) {
        flag = false;
        if (deletePath.matches(matches)) {
            file = new File(deletePath);
            if (!file.exists()) {// 判断目录或文件是否存在
                return flag; // 不存在返回 false
            } else {

                if (file.isFile()) {// 判断是否为文件
                    return deleteFile(deletePath);// 为文件时调用删除文件方法
                } else {
                    return deleteDirectory(deletePath);// 为目录时调用删除目录方法
                }
            }
        } else {
            System.out.println("要传入正确路径！");
            return false;
        }
    }

    /**
     * 删除单个文件
     * @param filePath 文件路径
     * @return
     */
    public boolean deleteFile(String filePath) {
        flag = false;
        file = new File(filePath);
        if (file.isFile() && file.exists()) {// 路径为文件且不为空则进行删除
            file.delete();// 文件删除
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param dirPath
     * @return
     */
    public boolean deleteDirectory(String dirPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!dirPath.endsWith(File.separator)) {
            dirPath = dirPath + File.separator;
        }
        File dirFile = new File(dirPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();// 获得传入路径下的所有文件
        for (int i = 0; i < files.length; i++) {// 循环遍历删除文件夹下的所有文件(包括子目录)
            if (files[i].isFile()) {// 删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                System.out.println(files[i].getAbsolutePath() + " 删除成功");
                if (!flag)
                    break;// 如果删除失败，则跳出
            } else {// 运用递归，删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;// 如果删除失败，则跳出
            }
        }
        if (!flag)
            return false;
        if (dirFile.delete()) {// 删除当前目录
            return true;
        } else {
            return false;
        }
    }

    /**
     *  创建单个文件
     * @param filePath 文件路径
     * @return
     */
    public static boolean createFile(String filePath) {
        File file = new File(filePath);
        // 判断文件是否存在
        if (file.exists()) {
            System.out.println("目标文件已存在" + filePath);
            return false;
        }
        // 判断文件是否为目录
        if (filePath.endsWith(File.separator)) {
            System.out.println("目标文件不能为目录！");
            return false;
        }
        // 判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            // 如果目标文件所在的文件夹不存在，则创建父文件夹
            System.out.println("目标文件所在目录不存在，准备创建它！");
            // 判断创建目录是否成功
            if (!file.getParentFile().mkdirs()) {
                System.out.println("创建目标文件所在的目录失败！");
                return false;
            }
        }
        try {
            // 创建目标文件
            if (file.createNewFile()) {
                System.out.println("创建文件成功:" + filePath);
                return true;
            } else {
                System.out.println("创建文件失败！");
                return false;
            }
        } catch (IOException e) {
            // 捕获异常
            e.printStackTrace();
            System.out.println("创建文件失败！" + e.getMessage());
            return false;
        }
    }


    /**
     *  创建临时文件
     * @param prefix
     * @param suffix
     * @param dirName
     * @return
     */
    public static String createTempFile(String prefix, String suffix,
                                        String dirName) {
        File tempFile = null;
        if (dirName == null) {// 目录如果为空
            try {
                tempFile = File.createTempFile(prefix, suffix);// 在默认文件夹下创建临时文件
                return tempFile.getCanonicalPath();// 返回临时文件的路径
            } catch (IOException e) {// 捕获异常
                e.printStackTrace();
                System.out.println("创建临时文件失败：" + e.getMessage());
                return null;
            }
        } else {
            // 指定目录存在
            File dir = new File(dirName);// 创建目录
            if (!dir.exists()) {
                // 如果目录不存在则创建目录
                if (createDir(dirName)) {
                    System.out.println("创建临时文件失败，不能创建临时文件所在的目录！");
                    return null;
                }
            }
            try {
                tempFile = File.createTempFile(prefix, suffix, dir);// 在指定目录下创建临时文件
                return tempFile.getCanonicalPath();// 返回临时文件的路径
            } catch (IOException e) {// 捕获异常
                e.printStackTrace();
                System.out.println("创建临时文件失败!" + e.getMessage());
                return null;
            }
        }
    }
}
