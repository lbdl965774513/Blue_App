package com.blue.global;

/**
 * ���ඨ����΢����Ȩʱ����Ҫ�Ĳ�����
 * 
 * @author SINA
 * @since 2013-09-29
 */
public interface Constants {

    /** ��ǰ DEMO Ӧ�õ� APP_KEY��������Ӧ��Ӧ��ʹ���Լ��� APP_KEY �滻�� APP_KEY */
    public static final String APP_KEY  = "3258419148";
    /** ΢�ŵ�appid*/
    public static final String APP_ID = "wxc49f81dea96bc75d";
    /** 
     * ��ǰ DEMO Ӧ�õĻص�ҳ��������Ӧ�ÿ���ʹ���Լ��Ļص�ҳ��
     * 
     * <p>
     * ע��������Ȩ�ص�ҳ���ƶ��ͻ���Ӧ����˵���û��ǲ��ɼ��ģ����Զ���Ϊ������ʽ������Ӱ�죬
     * ����û�ж��彫�޷�ʹ�� SDK ��֤��¼��
     * ����ʹ��Ĭ�ϻص�ҳ��https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String REDIRECT_URL = "http://www.thinklancer.com/";

    /**
     * Scope �� OAuth2.0 ��Ȩ������ authorize �ӿڵ�һ��������ͨ�� Scope��ƽ̨�����Ÿ����΢��
     * ���Ĺ��ܸ������ߣ�ͬʱҲ��ǿ�û���˽�������������û����飬�û����� OAuth2.0 ��Ȩҳ����Ȩ��
     * ѡ����Ӧ�õĹ��ܡ�
     * 
     * ����ͨ������΢������ƽ̨-->��������-->�ҵ�Ӧ��-->�ӿڹ������ܿ�������Ŀǰ������Щ�ӿڵ�
     * ʹ��Ȩ�ޣ��߼�Ȩ����Ҫ�������롣
     * 
     * Ŀǰ Scope ֧�ִ����� Scope Ȩ�ޣ��ö��ŷָ���
     * 
     * �й���Щ OpenAPI ��ҪȨ�����룬��鿴��http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * ���� Scope ���ע�������鿴��http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE = 
            "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
}

