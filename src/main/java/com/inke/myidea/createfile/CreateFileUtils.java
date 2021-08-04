package com.inke.myidea.createfile;

import com.inke.myidea.model.FileDo;

import java.io.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateFileUtils {
    /**
     * 创建文件
     */
    public static FileDo createFile(Map<String, String> map) {
        FileDo fileDo = new FileDo();
        String classPath = map.get("classPath");
        String className = map.get("className");

        String srcPath = classPath.split(File.separator + "src")[0];
        String javaPath = classPath.split(File.separator + "java")[0];
        String extraPath = javaPath.replace(srcPath, "");
        String[] split = extraPath.split(File.separator);
        int extraLength = split.length;

        String manifestName = "AndroidManifest.xml";

        File manifestFile = new File(srcPath, manifestName);
        if(!manifestFile.exists()) {
            String manifestPath = srcPath;
            for (int i = 0; i < extraLength; i++) {
                if("".equals(split[i])) continue;
                manifestPath = manifestPath + File.separator + split[i];
                manifestFile = new File(manifestPath, manifestName);
                if(manifestFile.exists()) {
                    break;
                }
            }
        }

        String manifestFilePath = manifestFile.getParent();

        String packageName = getPackageName(manifestFilePath);

        // 1.创建java文件
        createNewFile(classPath, className, packageName);
        // 2.创建布局文件
        createLayout(javaPath, className);
        // 3.注册Activity
        registManifestActivity(manifestFilePath, classPath, className, packageName);
        fileDo.setFileResult("创建文件信息成功, Reload from Disk刷新一下吧");
        return fileDo;
    }

    /**
     * 注册Activity
     */
    private static void registManifestActivity(String manifestFilePath, String classPath, String className, String packageName) {
        try {
            String classRelStr = classPath.replace(File.separator, ".");
            String classRelPath = "com." + classRelStr.split("com.")[1];
            RandomAccessFile raf = new RandomAccessFile(manifestFilePath + File.separator + "AndroidManifest.xml", "rw");
            classRelPath = classRelPath.replace(packageName, "");
            String line = null;
            long lastPoint = 0;
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("        <activity android:name=" + "\"" + classRelPath + "." + className + "\"");
            sb.append("\n");
            sb.append("            android:screenOrientation=\"portrait\"");
            sb.append("\n");
            sb.append("            />");
            sb.append("\n");
            sb.append("    </application>");
            sb.append("\n");
            sb.append("\n");
            sb.append("</manifest>");
            while ((line = raf.readLine()) != null) {
                final long point = raf.getFilePointer();
                if (line.contains("</application>")) {
                    String str = line.replace("</application>", sb.toString());
                    raf.seek(lastPoint);
                    raf.writeBytes(str);
                }
                lastPoint = point;
            }
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建布局文件
     */
    private static void createLayout(String javaPath, String className) {
        String resFilePath = javaPath + File.separator + "res" + File.separator + "layout";
        String layoutType = "";
        String classAliasName = "";
        if (className.endsWith("Activity")) {
            layoutType = "activity";
            classAliasName = className.replace("Activity", "");
        } else if (className.endsWith("Fragment")) {
            layoutType = "fragment";
            classAliasName = className.replace("Fragment", "");
        }
        String layoutName = layoutType + humpToLine(classAliasName) + ".xml";
        File resFile = new File(resFilePath, layoutName);
        if (!resFile.exists()) {
            try {
                resFile.createNewFile();
                FileWriter writer = new FileWriter(resFile.getPath());
                writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                writer.write("\n");
                writer.write("<androidx.constraintlayout.widget.ConstraintLayout");
                writer.write("\n");
                writer.write("    xmlns:android=\"http://schemas.android.com/apk/res/android\"");
                writer.write("\n");
                writer.write("    android:layout_width=\"match_parent\"");
                writer.write("\n");
                writer.write("    android:layout_height=\"match_parent\">");
                writer.write("\n");
                writer.write("\n");
                writer.write("</androidx.constraintlayout.widget.ConstraintLayout>");
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取包名
     */
    private static String getPackageName(String manifestFilePath) {
        RandomAccessFile raf = null;
        String pName = "";
        try {
            raf = new RandomAccessFile(manifestFilePath + File.separator + "AndroidManifest.xml", "rw");
            String line2 = null;
            while ((line2 = raf.readLine()) != null) {
                if (line2.contains("package=\"")) {
                    break;
                }
            }

            pName = line2.replace(" ", "").replace("package=\"", "").replace("\"", "").replace(">", "");
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pName;
    }

    /**
     * 创建java文件
     */

    private static void createNewFile(String classPath, String className, String packageName) {
        String str = classPath.replace(File.separator, ".");
        String classRelPath = "com." + str.split("com.")[1];
        File classFile = new File(classPath, className + ".java");
        if (!classFile.exists()) {
            try {
                classFile.createNewFile();
                String layoutType = "";
                String classAliasName = "";
                String parentName = "";
                if (className.endsWith("Activity")) {
                    layoutType = "activity";
                    parentName = "BaseActivity";
                    classAliasName = className.replace("Activity", "");
                } else if (className.endsWith("Fragment")) {
                    layoutType = "fragment";
                    parentName = "BaseFragment";
                    classAliasName = className.replace("Fragment", "");
                }

                String layoutName = layoutType + humpToLine(classAliasName);

                FileWriter writer = new FileWriter(classFile.getPath());
                writer.write("package " + classRelPath + ";");
                writer.write("\n");
                writer.write("\n");
                //导包
                writer.write("import com.ziroom.base.");
                writer.write(parentName + ";");
                writer.write("\n");
                writer.write("import " + packageName + ".R;");
                writer.write("\n");
                writer.write("\n");
                //class类
                writer.write("public class " + className + " extends ");
                writer.write(parentName);
                writer.write(" {");
                writer.write("\n");
                writer.write("\n");
                writer.write("    @Override");
                writer.write("\n");
                writer.write("    public int getLayoutId() {");
                writer.write("\n");
                writer.write("        return R.layout." + layoutName + ";");
                writer.write("\n");
                writer.write("    }");
                writer.write("\n");
                writer.write("\n");
                writer.write("}");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 驼峰转下划线
     */
    static String humpToLine(String str) {
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
