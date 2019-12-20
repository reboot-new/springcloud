package com.tan.springcloud2producer.test;


import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <获取系统信息类>
 * <获取系统信息，如CPU、内存、硬盘使用情况>
 * @author  wenkaixuan
 * @version  [版本号, 2012-5-9]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class GetSystemInfo {
    private static final int CPUTIME = 500;

    private static final int PERCENT = 100;

    private static final int FAULTLENGTH = 10;

    private static Map<String,Object> pro = new HashMap<>();

    //获得cpu使用率
    public static String getCpuRatioForWindows() {
        try {
            String procCmd =
                    System.getenv("windir")
                            + "\\system32\\wbem\\wmic.exe process get Caption,ProcessId,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
            // 取进程信息
            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
            Thread.sleep(CPUTIME);
            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
            if (c0 != null && c1 != null) {
                long idletime = c1[0] - c0[0];
                long busytime = c1[1] - c0[1];
                return "CPU使用率:" + Double.valueOf(PERCENT * (busytime) * 1.0 / (busytime + idletime)).intValue() + "%";
            } else {
                return "CPU使用率:" + 0 + "%";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "CPU使用率:" + 0 + "%";
        }
    }

    public static void main(String[] args) {

//        double maxx = 109.475;
//        double minx = 103.7201;
//        double maxy = 29.08667;
//        double miny = 24.7025;

        double maxx = 109.475;
        double minx = 103.766667;
        double maxy = 29.301744;
        double miny = 24.776667;

        double d= Math.sqrt(Math.pow(maxx-minx,2)+Math.pow(maxy-miny,2))/2;
        BigDecimal b   =   new   BigDecimal(d);
        b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(d);


//        System.out.println(getCpuRatioForWindows());
    }

    //读取cpu相关信息
    private static long[] readCpu(final Process proc) {
        long[] retn = new long[2];
        try {
            proc.getOutputStream().close();
            InputStreamReader ir = new InputStreamReader(proc.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line = input.readLine();
            if (line == null || line.length() < FAULTLENGTH) {
                return null;
            }
            int capidx = line.indexOf("Caption");
            int proidx = line.indexOf("ProcessId");
            int cmdidx = line.indexOf("CommandLine");
            int rocidx = line.indexOf("ReadOperationCount");
            int umtidx = line.indexOf("UserModeTime");
            int kmtidx = line.indexOf("KernelModeTime");
            int wocidx = line.indexOf("WriteOperationCount");
            long idletime = 0;
            long kneltime = 0;
            long usertime = 0;
            while ((line = input.readLine()) != null) {
                if (line.length() < wocidx) {
                    continue;
                }
                // 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
                // ThreadCount,UserModeTime,WriteOperation
                String caption = substring(line, capidx, cmdidx - 1).trim();
                String cmd = substring(line, cmdidx, kmtidx - 1).trim();
                if (cmd.indexOf("wmic.exe") >= 0) {
                    continue;
                }

                //KernelModeTime
                String s1 = substring(line, kmtidx, rocidx - 1).trim();

                //UserModeTime
                String s2 = substring(line, umtidx, wocidx - 1).trim();

                if (caption.equals("System Idle Process") || caption.equals("System")) {
                    if (s1.length() > 0)
                        idletime += Long.valueOf(s1).longValue();
                    if (s2.length() > 0)
                        idletime += Long.valueOf(s2).longValue();
                    continue;
                }

                if (s1.length() > 0)
                    kneltime += Long.valueOf(s1).longValue();
                if (s2.length() > 0)
                    usertime += Long.valueOf(s2).longValue();
            }

            retn[0] = idletime;
            retn[1] = kneltime + usertime;
            return retn;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                proc.getInputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 由于String.subString对汉字处理存在问题（把一个汉字视为一个字节)，因此在 包含汉字的字符串时存在隐患，现调整如下：
     *
     * @param src       要截取的字符串
     * @param start_idx 开始坐标（包括该坐标)
     * @param end_idx   截止坐标（包括该坐标）
     * @return
     */
    private static String substring(String src, int start_idx, int end_idx) {
        byte[] b = src.getBytes();
        String tgt = "";
        for (int i = start_idx; i <= end_idx; i++) {
            tgt += (char) b[i];
        }
        return tgt;
    }
}
