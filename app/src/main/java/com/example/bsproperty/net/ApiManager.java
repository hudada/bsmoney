package com.example.bsproperty.net;

/**
 * Created by yezi on 2018/1/27.
 */

public class ApiManager {

    private static final String HTTP = "http://";
    private static final String IP = "192.168.55.102";
    private static final String PROT = ":8080";
    private static final String HOST = HTTP + IP + PROT;
    private static final String API = "/api";
    private static final String USER = "/user";

    private static final String MONEY = "/money";

    private static final String COMMENT = "/comment";

    private static final String RECOMMENT = "/recomment";

    public static final String IMG = HOST + API + COMMENT;


    public static final String REGISTER = HOST + API + USER + "/";
    public static final String LOGIN = HOST + API + USER + "/login";
    public static final String MODIFYPWD = HOST + API + USER + "/editPass";

    public static final String MONEY_LIST = HOST + API + MONEY + "/list/";
    public static final String DEL_MONEY = HOST + API + MONEY + "/del";
    public static final String STATISTICS_TYPE = HOST + API + MONEY + "/statistics/";
    public static final String ADD_SAVE = HOST + API + MONEY + "/add";

    public static final String COMMENT_LIST = HOST + API + COMMENT + "/list";
    public static final String COMMENT_ADD = HOST + API + COMMENT + "/add";
    public static final String COMMENT_ADD_IMG = HOST + API + COMMENT + "/addwithimg";

    public static final String RECOMMENT_LIST = HOST + API + RECOMMENT + "/list/";
    public static final String RECOMMENT_ADD = HOST + API + RECOMMENT + "/add";

}
