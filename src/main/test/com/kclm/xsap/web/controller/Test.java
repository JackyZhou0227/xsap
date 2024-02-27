package com.kclm.xsap.web.controller;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kclm.xsap.entity.CourseEntity;
import com.kclm.xsap.entity.ReservationRecordEntity;
import com.kclm.xsap.utils.R;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author fangkai
 * @description
 * @create 2021-12-09 13:17
 */
@Slf4j
public class Test {

    @org.junit.jupiter.api.Test
    void test26() {
        String s = "我是谁！！";
        char[] chars = s.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char aChar : chars) {
            builder.append((int)aChar);
            System.out.println(aChar);
            System.out.println((int)aChar);
        }
        System.out.println(builder);
        String str;
        System.out.println(builder.substring(builder.length() -3));
        str = builder.substring(0, 2) + builder.substring(builder.length() - 2);
        System.out.println(str);

    }


    public static void t2(){//字符串转换为ASCII码

        String s="新年快乐！";//字符串

        char[]chars=s.toCharArray(); //把字符中转换为字符数组

        System.out.println("\n\n汉字 ASCII\n----------------------");
        for(int i=0;i<chars.length;i++){//输出结果

            System.out.println(" "+chars[i]+" "+(int)chars[i]);
        }
    }

    @org.junit.jupiter.api.Test
    void test25() {

        t2();
        String test = "我是谁！";
        String str = StrUtil.str(test);
        System.out.println(str);
        byte[] bytes = StrUtil.bytes(test);
        for (byte aByte : bytes) {
            System.out.print(aByte);
        }
        byte[] bytes1 = test.getBytes(StandardCharsets.UTF_8);
        String s = null;
        for (byte b : bytes1) {
            System.out.println(b);
            System.out.println();
            s += String.valueOf(b);

        }
        System.out.println(s);

    }

    @org.junit.jupiter.api.Test
    void test24() {

        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader("testT.txt"));
            bw = new BufferedWriter(new FileWriter("testT1.txt"));

            /*int len;
            char[] buffer = new char[1024];
            while ((len = br.read(buffer)) != -1) {
                bw.write(buffer,0,len);
            }*/

            String data;
            /*while ((data = br.readLine()) != null) {    //不包含换行符
                bw.write(data + "\n");
            }*/
            while ((data = br.readLine()) != null) {    //不包含换行符
                bw.write(data );
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }



    //字节缓冲流处理
    public void copyBufferFile(String srcPath, String destPath) {

        FileInputStream fis = null;
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            File srcFile = new File(srcPath);
            File destFile = new File(destPath);

            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);

            int len;
            byte[] buffer = new byte[1024];

            while ((len = bis.read(buffer )) != -1) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //缓冲流的测试
    @org.junit.jupiter.api.Test
    void test23() {
        long start = System.currentTimeMillis();
        String scrPath = new String("E:\\IDEA_space\\project\\xsap\\zhaolinger.jpg");
        String destPath = new String("E:\\IDEA_space\\project\\xsap\\zhaolinger1.jpg");

        copyBufferFile(scrPath,destPath);

        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));
    }


    @org.junit.jupiter.api.Test
    void test22() {
        long start = System.currentTimeMillis();
        copyFile("zhaolinger.jpg", "test.jpg");


        System.out.println("用时" + (System.currentTimeMillis() - start));
    }

    public void copyFile(String srcPath, String destPath) {

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
//            File file = new File("hello.txt");
            File file = new File(srcPath);
            File file1 = new File(destPath);

            fis = new FileInputStream(file);
            fos = new FileOutputStream(file1);

            int len;
            byte[] bytes = new byte[5];
            while ((len = fis.read(bytes)) != -1) {
                String s = new String(bytes,0, len);
                System.out.print(s);
                fos.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @org.junit.jupiter.api.Test
    void test21() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
//            File file = new File("hello.txt");
            File file = new File("zhaolinger.jpg");
            File file1 = new File("xiaolongnv.jpg");

            fis = new FileInputStream(file);
            fos = new FileOutputStream(file1);

            int len;
            byte[] bytes = new byte[5];
            while ((len = fis.read(bytes)) != -1) {
                String s = new String(bytes,0, len);
                System.out.print(s);
                fos.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //复制测试
    @org.junit.jupiter.api.Test
    void test20() {
        FileReader fr = null;
        FileWriter fw = null;
        try {
            File srcFile = new File("hello.txt" );
            File destFile = new File("hello1.txt");

            fr = new FileReader(srcFile);
            fw = new FileWriter(destFile);

            char[] cbuf = new char[5];
            int len;
            while ((len = fr.read(cbuf)) != -1) {
                fw.write(cbuf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    //读入测试
    @org.junit.jupiter.api.Test
    void test19() throws IOException {
        File file = new File("hello.txt");

        FileReader reader = new FileReader(file);

        char[] buffer = new char[5];

        int len ;
        while ((len = reader.read(buffer)) != -1) {
            String s = new String(buffer, 0, len);
            System.out.print(s);
        }
    }


    @org.junit.jupiter.api.Test
    void test18() throws InterruptedException {


        ExpiryMap<String, String> map = new ExpiryMap<>(10);
        map.put("test", "ankang");
        map.put("test1", "ankang");
        map.put("test2", "ankang", 3000);
        System.out.println("test1" + map.get("test"));
        Thread.sleep(1000);
        System.out.println("isInvalid:" + map.isInvalid("test"));
        System.out.println("size:" + map.size());
        System.out.println("size:" + ((HashMap<String, String>) map).size());
        for (Map.Entry<String, String> m : map.entrySet()) {
            System.out.println("isInvalid:" + map.isInvalid(m.getKey()));
            map.containsKey(m.getKey());
            System.out.println("key:" + m.getKey() + "     value:" + m.getValue());
        }
        System.out.println("test1" + map.get("test"));
    }



    @JsonFormat(pattern = "¤00.00")
    private BigDecimal changeMoney;
    @org.junit.jupiter.api.Test
    void test17() {
        changeMoney = new BigDecimal("13");
        System.out.println(changeMoney);

        System.out.println("************");
        BigDecimal decimal = new BigDecimal("11");
        DecimalFormat format = new DecimalFormat("¤00.00");
        System.out.println(format.format(decimal));
    }


    @org.junit.jupiter.api.Test
    void test16() {
        Integer integer = (1);
        integer = null;
        System.out.println(integer);
    }


    @org.junit.jupiter.api.Test
    void test15() {
        Date date = new Date();

        LocalDate now = LocalDate.now();
        System.out.println(date);
        System.out.println();
        System.out.println(now);

        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println(zoneId);
    }

    @org.junit.jupiter.api.Test
    void test14() {
        for (int i = 1; i <= 0; i++) {
            System.out.println("*");

        }
    }

    @org.junit.jupiter.api.Test
    void test13() {
        LocalDateTime parse = LocalDateTime.parse("2020-01-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println(parse);
    }

    @org.junit.jupiter.api.Test
    void test12() {
        ArrayList<BigDecimal> list = new ArrayList<>();
        list.add(new BigDecimal(1.1));
        list.add(new BigDecimal(2.11));
        list.add(new BigDecimal(2.1));
        list.add(new BigDecimal(1));
        BigDecimal reduce = list.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(reduce);
    }

    @org.junit.jupiter.api.Test
    void test11() {
        LocalDate now = LocalDate.now();
        System.out.println(now);
        int dayOfMonth = now.getDayOfMonth();
        System.out.println(dayOfMonth);

        Calendar instance = Calendar.getInstance();
        System.out.println(instance);
    }

    @org.junit.jupiter.api.Test
    void test10() {
        LocalDate now = LocalDate.now().minusYears(15);
        long until = now.until(LocalDate.now(), ChronoUnit.YEARS);
        System.out.println(until);

    }

    @org.junit.jupiter.api.Test
    void test09() {
        ReservationRecordEntity en = new ReservationRecordEntity();
        System.out.println(en.getId());
        if (null == en.getId()) {
            System.out.println("**");
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void test08() {
        R r = getR();
        System.out.println(r);
    }

    public R getR() {
        return R.ok("success");
    }

    @org.junit.jupiter.api.Test
    void test07() {
        String format = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        System.out.println(format);
    }


    @org.junit.jupiter.api.Test
    void test06() {
        CourseEntity test = new CourseEntity().setId(3L).setDuration(45L).setLimitCounts(34);
        R data = new R().put("data", test);
        System.out.println(data);
    }

    @org.junit.jupiter.api.Test
    void test05() {
        String format = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println(format);
    }

    @org.junit.jupiter.api.Test
    void test04() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate now = LocalDate.now();
        String now1 = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println(now);
        System.out.println(now1);
        String s = now.toString();
        String s1 = now1;
        LocalDateTime parse = LocalDateTime.parse((s + " " + s1), dtf);
        System.out.println(parse);

    }

    @org.junit.jupiter.api.Test
    public void test01() {
        String s = "-1";
        String[] split = s.split(",");
        System.out.println(Arrays.toString(split));
    }


    @org.junit.jupiter.api.Test
    void test02() {
        String s = "";
        s = null;
        System.out.println(s.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void test03() {
        Long l = null;
        String.valueOf(l);
    }
}
