package com.tan.springcloud2producer.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipHelper {
    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * 压缩成ZIP 方法1
     * @param srcDir 压缩文件夹路径
     * @param out    压缩文件输出流
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void compress(String srcDir, OutputStream out, boolean KeepDirStructure)
            throws RuntimeException{
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            fileCompress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipHelper",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void compress(String srcDir, OutputStream out){
        compress(srcDir,out,true);
    }


    /**
     * 压缩成ZIP 方法2
     * @param srcFiles 需要压缩的文件列表
     * @param out           压缩文件输出流
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void compress(List<File> srcFiles , OutputStream out)throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1){
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipHelper",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 递归压缩方法
     * @param sourceFile 源文件
     * @param zos        zip输出流
     * @param name       压缩后的名称
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void fileCompress(File sourceFile, ZipOutputStream zos, String name,
                                     boolean KeepDirStructure) throws Exception{
        byte[] buf = new byte[BUFFER_SIZE];
        if(sourceFile.isFile()){
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1){
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if(KeepDirStructure){
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            }else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        fileCompress(file, zos, name + "/" + file.getName(),KeepDirStructure);
                    } else {
                        fileCompress(file, zos, file.getName(),KeepDirStructure);
                    }

                }
            }
        }
    }

    /**
     * 解压zip文件到指定文件夹
     *
     * @param zipFile zip文件路径
	 * @param dstPath 解压目标文件夹
     * @return 
     * @author tan_alpha
     * @date 2019/5/18 22:27
     **/
    public static void decompress(String zipFile , String dstPath)throws IOException{
        File pathFile = new File(dstPath);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile);
        for(Enumeration entries = zip.entries(); entries.hasMoreElements();){
            ZipEntry entry = (ZipEntry)entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = null;
            OutputStream out = null;
            try{
                in =  zip.getInputStream(entry);
                String outPath = (dstPath+"/"+zipEntryName).replaceAll("\\*", "/");;
                //判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
                if(!file.exists()){
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if(new File(outPath).isDirectory()){
                    continue;
                }
                out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while((len=in.read(buf1))>0){
                    out.write(buf1,0,len);
                }
            }
            finally {
                if(null != in){
                    in.close();
                }

                if(null != out){
                    out.close();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        /** 测试压缩方法1  */
//        FileOutputStream fos1 = new FileOutputStream(new File("c:/mytest01.zip"));
//        ZipHelper.toZip("C:\\Users\\Administrator\\Desktop\\64", fos1);
        /** 测试压缩方法2  */
        List<File> fileList = new ArrayList<>();
        fileList.add(new File("C:\\Users\\Administrator\\Desktop\\综合数据服务平台\\base-frame-web-1.0-SNAPSHOT.jar"));
        fileList.add(new File("C:\\Users\\Administrator\\Desktop\\综合数据服务平台\\springcloud2-0.0.1-SNAPSHOT.jar"));
//        FileOutputStream fos2 = new FileOutputStream(new File("c:/mytest02.zip"));
//        ZipHelper.compress(fileList, fos2);
        ZipHelper.decompress("c:/mytest02.zip","C:\\Users\\Administrator\\Desktop\\test");
    }
}

