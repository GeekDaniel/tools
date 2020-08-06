package top.dannystone.templateCreator.fileWriter.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * Creed: keep it simple and stupid !
 * Time: 2020/8/6 9:14 AM
 */
public class PathOperationUtils {
    private static final String CD_1_DEPTH = "\\.((?!\\.).)*?\\[cd \\.\\.\\]";
    private static final String CD_2_DEPTH = "(\\.((?!\\.).)*?){2}\\[cd \\.\\.\\/\\.\\.\\]";
    private static final String CD_3_DEPTH = "(\\.((?!\\.).)*?){3}\\[cd \\.\\.\\/\\.\\.\\/\\.\\.\\]";

    public static String doCd(String packagePath, String cdPattern) {
        String afterCd = new String(packagePath);
        Pattern pattern = compile(cdPattern);
        Matcher matcher = pattern.matcher(packagePath);
        while (matcher.find()) {
            //.sadfdsf[cd ..]
            String group = matcher.group(0);
            afterCd = afterCd.replace(group, "");
        }
        return afterCd;
    }

    public static String package1DepthCd(String packagePath) {
        return doCd(packagePath, CD_1_DEPTH);
    }

    public static String package2DepthCd(String packagePath) {
        return doCd(packagePath, CD_2_DEPTH);
    }

    public static String package3DepthCd(String packagePath) {
        return doCd(packagePath, CD_3_DEPTH);
    }


    public static void main(String[] args) {
        String aRelativePath = "com.shizhuang.duapp.daniel[cd ../../..]";
        String s = package3DepthCd(aRelativePath);
        System.out.println(s);

        String aRelativePath2 = "com.shizhuang.duapp.daniel[cd ../..]";
        String s2 = package2DepthCd(aRelativePath2);
        System.out.println(s2);

        String aRelativePath1 = "com.shizhuang.duapp.daniel[cd ..]";
        String s3 = package1DepthCd(aRelativePath1);
        System.out.println(s3);
    }
}
