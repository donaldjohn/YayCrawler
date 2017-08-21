package yaycrawler.common.utils;

/**
 * Created by Administrator on 2015/11/24.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OCRHelper {
    private final String LANG_OPTION = "-l";
    private final String PSM = "-psm";
    private final String EOL = System.getProperty("line.separator");
    private String language = "eng";
    /**
     * 文件位置我防止在，项目同一路径
     */
    private String tessPath = new File("tesseract").getAbsolutePath();

    public OCRHelper() {

    }

    public OCRHelper(String language) {
        this.language = language;
    }
    /**
     * @param imageFile 传入的图像文件
     *                  //* @param imageFormat
     *                  传入的图像格式
     * @return 识别后的字符串
     */
    public String recognizeText(File imageFile) throws Exception {
        /**
         * 设置输出文件的保存的文件目录
         */
        File outputFile = new File(imageFile.getParentFile(), "output");

        StringBuffer strB = new StringBuffer();
        List<String> cmd = new ArrayList<String>();
//        if (OS.isWindowsXP()) {
//            cmd.add(tessPath + "\\tesseract");
//        } else if (OS.isLinux()) {
//            cmd.add("tesseract");
//        } else {
//            cmd.add(tessPath + "\\tesseract");
//        }
        cmd.add("D:\\Program Files\\Tesseract-OCR" + "\\tesseract");
        cmd.add("");
        cmd.add(outputFile.getName());
        cmd.add(LANG_OPTION);
//		cmd.add("chi_sim");
        cmd.add(language);
        ProcessBuilder pb = new ProcessBuilder();
        /**
         *Sets this process builder's working directory.
         */
        pb.directory(imageFile.getParentFile());
        cmd.set(1, imageFile.getName());
        pb.command(cmd);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        // tesseract.exe 1.jpg 1 -l chi_sim
        // Runtime.getRuntime().exec("tesseract.exe 1.jpg 1 -l chi_sim");
        /**
         * the exit value of the process. By convention, 0 indicates normal
         * termination.
         */
//		System.out.println(cmd.toString());
        int w = process.waitFor();
        if (w == 0)// 0代表正常退出
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(outputFile.getAbsolutePath() + ".txt"),
                    "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                strB.append(str).append(EOL);
            }
            in.close();
        } else {
            String msg;
            switch (w) {
                case 1:
                    msg = "Errors accessing files. There may be spaces in your image's filename.";
                    break;
                case 29:
                    msg = "Cannot recognize the image or its selected region.";
                    break;
                case 31:
                    msg = "Unsupported image format.";
                    break;
                default:
                    msg = "Errors occurred.";
            }
            throw new RuntimeException(msg);
        }
        new File(outputFile.getAbsolutePath() + ".txt").delete();
        return strB.toString().replaceAll("\\s*", "");
    }

    public static void main(String[] args) {
        try {

            File testDataDir = new File("D:\\tmp\\ocr");
            System.out.println(testDataDir.listFiles().length);
            int i = 0;
            int k=0;
//            Pattern pattern = Pattern.compile("^[零,一,二,三,四,五,六,七,八,九,十,靈,壹,贰,叁,肆,伍,陆,柒,捌,玖,拾,0-9]");
//            Matcher matcher = pattern.matcher("123456$wefwfe".replaceAll("[^0-9]",""));
//            if(matcher.find())
//                System.out.println("true");

            long begintime = System.currentTimeMillis();
            String[] imgs = new String[]{"B2II","41EG","EJIS","WTCB","2ROI","6PDC","YKTQ",
                    "4TBE","I2M1","RFKN","AYQC","N1HD","4GNZ","IOIW","JFCE","JJNT","WXJE",
                    "BIDK","KXWF","1K1Y","IM5G","B8JY","VJFZ","KDWC","RV94","IYIP","UYAD",
                    "APOI","GO2J","SWNW","YVQC","XKRC","QDAM","PJAX","GTPK","NWF8","GNOJ",
                    "OVTQ","TSHG","NSG7","DKGW","RKHR","GYUJ","3ED4","RY4D","4RMC","ZSIO","NOXU",
                    "SODH","HAY7","ZCEG","J8V4","TIWN","QKMC","9ZCM","HDV3","J6YZ","VKSY","AHSA",
                    "MYGH","F2OH","IQMW","2AFE","HIB7","OKKH","BORO","WLLO","ZBWD","CQ3H","IVQ5","HTBW","KKEE",
                    "R1RJ","IBBF","SPIC","3ID4","2IAO","5YBY","AXJV","AHLM","MUW4","SHEB","BJVW","W9EP","GOMG",
                    "GCGC","ZNQZ","BWMH","IGUA","AYFH","SLYT","TE7M","2DIW","QRPN","XZTP","7YFN","BDU9","M399","Z9WI","A5MU",
                    "v494","m5m1","zvod","txrp","GTBN","MO7C","V6UH","7DUV","C97D","BYOT","PA2M","BCJ2","JCAN","MDGC","A0IT",
                    "DFEI","ABI7","F0AD","5NZZ","AOMC","7MTD","O4VO","BBGE","2KOA","P4BG","ZSXP","XNES","KJDP","ISYC","GXAN",
                    "H554","QU8Y","EGJW","GJU1","GA9I","YPHD","IFLV","0EM6","A3UY","MD7Y","MDWP","HZQD","ETC2","0CMV","6ZNF",
                    "BMWW","MTMR","8W0V","H2NS","LXU","WV5C","Y4GF","W3JV","6QWX","CY7E","M3IQ","HCYQ","50IQ","H3WX","A1QF",
                    "7BTU","6OJO","NQVM","WAZN","ASIH","O7K3","U2Q0","VVQ2","SB4Z","RMYT","WFBF","UUIC","H6II","KGSI","OFQG",
                    "ROHV","TTHB","9PD3","BLBO","TITN","BVHA","2T9F","NYWM","1VQO","ETD9","ABR2","X4UY","CSFY","FGTS","MGGI",
                    "RYN8","P1NN","DFAY","H5Y9","X44T","JCER","ILH3","BSRX","ZXHD","NXNT"
            };

            for (File file : testDataDir.listFiles()) {

                if (file.isFile()) {
                    String recognizeText = new OCRHelper().recognizeText(file).trim().toLowerCase()
//                            .replaceAll("\\|", "i")
//                            .replaceAll("v\\\\", "w")
//                        .replaceAll("e","9")
//                        .replaceAll("t","1")
//                        .replaceAll("ﬁ","8")
//                        .replaceAll("s","6")
//                        .replaceAll("u","0")
//                        .replaceAll("o","0")
//                        .replaceAll("z","2")
//                        .replaceAll("«","1")
                            ;
                    if (imgs[Integer.parseInt(file.getName().replace(".jpg", ""))].equalsIgnoreCase(recognizeText)) {
                        k++;
                        System.out.print("FiLE:成功***" + file.getName() + " :" + recognizeText + "\n");
                    } else {
                        System.out.print("FiLE:" + file.getName() + " :" + recognizeText + "\n");
                    }

//                matcher = pattern.matcher(recognizeText);
//                if(matcher.find()) {
//                    System.out.println("---------------------------------");
//                    System.out.print("FiLE:" + file.getName() + " :" + recognizeText + "\n");
//                    System.out.println("----------------------------------");
//                    k++;
//                } else {
////                    file.delete();
//                }
                    i++;
                }
                long endtime = System.currentTimeMillis();
                System.out.println(k);
                System.out.println(i);
                System.out.println(k * 1.0 / i);
                System.out.println("left time " + (endtime - begintime));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
