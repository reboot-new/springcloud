package com.tan.springcloud2producer.test;

import com.alibaba.fastjson.JSONObject;
import com.tan.springcloud2producer.helper.FileHelper;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class ShareMemoryTest {

    //开辟共享内存大小 50M
    int flen = 5242880;
    //文件的实际大小
    int fsize = 0;
    //共享内存文件名
    String shareFileName;
    //共享内存路径
    String sharePath;
    //定义共享内存缓冲区
    MappedByteBuffer mapBuf = null;
    //定义相应的文件通道
    FileChannel fc = null;
    //定义文件区域锁定的标记。
    FileLock fl = null;
    //定义一个随机存取文件对象
    RandomAccessFile RAFile = null;

    /**
     *
     * @param sp    共享内存文件路径
     * @param sf    共享内存文件名
     */
    public ShareMemoryTest(String sp, String sf) {
        if (sp.length() != 0) {
            FileHelper.createDir(sp);
            this.sharePath = sp + File.separator;
        } else {
            this.sharePath = sp;
        }
        this.shareFileName = sf;

        try {
            // 获得一个只读的随机存取文件对象   "rw" 打开以便读取和写入。如果该文件尚不存在，则尝试创建该文件。
            RAFile = new RandomAccessFile(this.sharePath + this.shareFileName + ".sm", "rw");
            //获取相应的文件通道
            fc = RAFile.getChannel();
            //获取实际文件的大小
            fsize = (int) fc.size();
            if (fsize < flen) {
                byte bb[] = new byte[flen - fsize];
                //创建字节缓冲区
                ByteBuffer bf = ByteBuffer.wrap(bb);
                bf.clear();
                //设置此通道的文件位置。
                fc.position(fsize);
                //将字节序列从给定的缓冲区写入此通道。
                fc.write(bf);
                fc.force(false);

                fsize = flen;
            }
            //将此通道的文件区域直接映射到内存中。
            mapBuf = fc.map(FileChannel.MapMode.READ_WRITE, 0, fsize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param ps        锁定区域开始的位置；必须为非负数
     * @param len       锁定区域的大小；必须为非负数
     * @param buff      写入的数据
     * @return
     */
    public synchronized int write(int ps, int len, byte[] buff) {
        if (ps >= fsize || ps + len >= fsize) {
            return 0;
        }
        try {
            //获取此通道的文件给定区域上的锁定。
            fl = fc.lock(ps, len, false);
            if (fl != null) {
                // 清除文件内容
                // 清除文件内容,对MappedByteBuffer的操作就是对文件的操作
                for (int i = ps ; i < (ps + len); i++) {
                    mapBuf.put(i,(byte)0);
                }
                mapBuf.position(ps);
                ByteBuffer bf1 = ByteBuffer.wrap(buff);
                mapBuf.put(bf1);
                //释放此锁定。
                fl.release();
                return len;
            }
        } catch (Exception e) {
            if (fl != null) {
                try {
                    fl.release();
                } catch (IOException e1) {
                    System.out.println(e1.toString());
                }
            }
            return 0;
        }
        return 0;
    }

    /**
     *
     * @param ps        锁定区域开始的位置；必须为非负数
     * @param len       锁定区域的大小；必须为非负数
     * @param buff      要取的数据
     * @return
     */
    public synchronized int read(int ps, int len, byte[] buff) {
        if (ps >= fsize) {
            return 0;
        }
        //定义文件区域锁定的标记。
        try {
            fl = fc.lock(ps, len, false);
            if (fl != null) {
                //System.out.println( "ps="+ps );
                mapBuf.position(ps);
                if (mapBuf.remaining() < len) {
                    len = mapBuf.remaining();
                }

                if (len > 0) {
                    mapBuf.get(buff, 0, len);
                }

                fl.release();

                return len;
            }
        } catch (Exception e) {
            if (fl != null) {
                try {
                    fl.release();
                } catch (IOException e1) {
                    System.out.println(e1.toString());
                }
            }
            return 0;
        }
        return 0;
    }

    /**
     * 完成，关闭相关操作
     */
    protected void finalize() throws Throwable {
        if (fc != null) {
            try {
                fc.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            fc = null;
        }
        if (RAFile != null) {
            try {
                RAFile.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            RAFile = null;
        }
        mapBuf = null;
    }

    /**
     * 关闭共享内存操作
     */
    public synchronized void closeSMFile() {
        if (fc != null) {
            try {
                fc.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            fc = null;
        }
        if (RAFile != null) {
            try {
                RAFile.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            RAFile = null;
        }
        mapBuf = null;
    }

    public static void main(String arsg[]) throws Exception{
        try {
            ShareMemoryTest sm = new ShareMemoryTest("C:\\Users\\Administrator\\Desktop\\temp","22222");
            JSONObject json = new JSONObject();
            json.put("name","来兮子宁");
            json.put("age",18);
            String str = json.toJSONString();

            sm.write(40, 5000, str.getBytes("UTF-8"));
            byte[] b = new byte[5000];
            Thread.sleep(1000*2);
            sm.read(40, 5000, b);
            String jsonStr = new String(b, "UTF-8").trim();
            System.out.println(jsonStr);
            JSONObject jsonObject = (JSONObject) JSONObject.parse(jsonStr);
            String name = jsonObject.getString("name");
            Integer age = jsonObject.getInteger("age");
            System.out.println("name : " + name + " age : " + age);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
