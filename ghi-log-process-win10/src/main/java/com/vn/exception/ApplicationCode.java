package com.vn.exception;

import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ApplicationCode {

    private static final String LANGUAGE_DEFAULT = "vn";
    private static final ApplicationCode instance = new ApplicationCode();
    private static final Map<Integer, String> msg = new HashMap<>();

    private static final String ISO_8859 = "ISO-8859-1";
    private static final String UTF8 = "UTF-8";
    public static final int SUCCESS = 0;
    public static final int FAILED = 400001;
    public static final int SYSTEM_ERROR = 400002;
    public static final int APP_ERROR = 400003;
    public static final int DATA_INVALID = 400004;
    public static final int DATA_NOT_FOUND = 400005;
    public static final int REG_NOT_FOUND = 400006;
    public static final int FILE_EXCEL_NOT_FOUND = 400007;
    public static final int FILE_EXCEL_NOT_CONTAIN_DATA = 400008;
    public static final int EXPORT_EXCEL_NOT_FOUND_GROUP = 400009;
    public static final int EXPORT_EXCEL_RESULT_EMPTY = 400010;
    public static final int EXPORT_EXCEL_FOLDER_NOT_FOUND = 400011;
    public static final int EXPORT_EXCEL_ERROR_WHEN_EXPORT_EXCEL = 400012;
    public static final int EXPORT_EXCEL_GROUP_LIST_GROUP_ID_EMPTY = 400013;
    public static final int EXPORT_EXCEL_GROUP_RESULT_EMPTY = 400014;
    public static final int MESSANGE = 400015;


    static {
        msg.put(SUCCESS, "SUCCESS");
        msg.put(FAILED, "FAILED");
        msg.put(SYSTEM_ERROR, "SYSTEM_ERROR");
        msg.put(APP_ERROR, "APP_ERROR");
        msg.put(DATA_INVALID, "DATA_INVALID");
        msg.put(DATA_NOT_FOUND, "DATA_NOT_FOUND");
        msg.put(REG_NOT_FOUND, "REG_NOT_FOUND");
        msg.put(FILE_EXCEL_NOT_FOUND, "FILE_EXCEL_NOT_FOUND");
        msg.put(FILE_EXCEL_NOT_CONTAIN_DATA, "FILE_EXCEL_NOT_CONTAIN_DATA");
    }


    public static ResourceBundle getBundle(String language) {
        ResourceBundle bundle = ResourceBundle.getBundle("language_" + language);
        return bundle;
    }

    public static String getProperty(int code, String language) {
        String text = msg.get(code);
        getBundle(language).keySet();
        return getBundle(language).getString(text);

    }

    public static String getMessage(int code) {
        return getMsg(code, LANGUAGE_DEFAULT);
    }

    public static String getMessage(int code, String language) {
        if (StringUtils.isEmpty(language)) {
            language = LANGUAGE_DEFAULT;
        }

        return getMsg(code, language);
    }

    @NotNull
    private static String getMsg(int code, String language) {
        if (msg.containsKey(code)) {
            String message = getProperty(code, language);
            String msg;
            if (code == 0) {
                msg = new String(message.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            } else {
                msg = "[ERR_" + code + "] " + new String(message.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            }
            return msg;
        }

        return "";
    }

    public static String getFolderExcelConfig() {
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        return bundle.getString("excel_folder_path");
    }

    public static String getExportTemplate() {
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        return bundle.getString("excel_export_template");
    }

    public static String getLinkExcelConfig() {
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        return bundle.getString("link_download_excel");
    }

    public static void main(String[] strs) {
        System.out.println("" + getMessage(0, "vn"));
    }
}
