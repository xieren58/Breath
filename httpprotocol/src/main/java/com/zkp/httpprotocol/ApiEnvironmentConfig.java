package com.zkp.httpprotocol;

/**
 * API请求-数据基类
 */
public class ApiEnvironmentConfig {

    public static final String TAG = ApiEnvironmentConfig.class.getSimpleName();

    public static String sApiEnvironmentType = ApiEnvironmentType.STABLE;

    public @interface ApiEnvironmentType {
        // 线上版本所需
        String STABLE = "正式环境";
        // 内网测试
        String BETA = "测试环境";
        // 外网测试
        String TRANSITION = "过渡环境";
    }

    public @interface Params {
        String PLATFORM = "Android";
    }

    public @interface ApiPoint {
        /**
         * 新接口正式环境
         */
        String NEW_END_POINT_STABLE = "https://www.181.com/aps/app/apis/";
        /**
         * 新接口过渡环境
         */
        String NEW_END_POINT_TRANSITION = "https://tpc.181.com/aps/app/apis/";
        /**
         * 新接口测试环境
         */
        String NEW_END_POINT_BETA = "http://192.168.5.58:8070/";
        /**
         * 旧接口正式环境
         */
        String OLD_END_POINT_STABLE = "https://www.181.com/aps/app/";
        /**
         * 旧接口过渡环境
         */
        String OLD_END_POINT_BEAT_TRANSITION = "https://tpc.181.com/aps/app/";
        /**
         * 旧接口测试环境
         */
        String OLD_END_POINT_BEAT_BETA = "http://192.168.5.58:8780/app/";
        /**
         * wap正式环境
         */
        String WAP_END_POINT_STABLE = "https://wap.181.com/";
        /**
         * wap过渡环境
         */
        String WAP_END_POINT_TRANSITION = "https://th5.181.com/";
        /**
         * wap测试环境
         */
        String WAP_END_POINT_BETA = "https://th5.181.com/";
        /**
         * SSO正式环境
         */
        String SSO_END_POINT_BEAT_STABLE = "https://sso.181.com/sso/";
        /**
         * SSO过渡环境
         */
        String SSO_END_POINT_BEAT_TRANSITION = "http://tpc.181.com/api/sso/";
        /**
         * SSO测试环境
         */
        String SSO_END_POINT_BEAT_BETA = "http://192.168.5.58:8680/";
        /**
         * 图片正式环境
         */
        String IMG_END_POINT = "https://file.181.com/file/";
    }

    /**
     * 当前环境是否为正式环境
     */
    public static boolean isStable() {
        return ApiEnvironmentType.STABLE.equals(getsApiEnvironmentType());
    }

    /**
     * 当前环境是否为测试环境
     */
    public static boolean isBeta() {
        return ApiEnvironmentType.BETA.equals(getsApiEnvironmentType());
    }

    /**
     * 当前环境是否为过渡环境
     */
    public static boolean isTransition() {
        return ApiEnvironmentType.TRANSITION.equals(getsApiEnvironmentType());
    }

    /**
     * 设置全局api环境标记
     */
    public static String getsApiEnvironmentType() {
        return sApiEnvironmentType;
    }

    /**
     * 设置全局api环境标记
     * @param sApiEnvironmentType {@linkplain ApiEnvironmentType}
     */
    public static void setsApiEnvironmentType(String sApiEnvironmentType) {
        sApiEnvironmentType = sApiEnvironmentType;
    }

    /**
     * 获取图片主机名
     */
    public static String getImgEndPoint() {
        return ApiPoint.IMG_END_POINT;
    }

    /**
     * 获取标记环境wap主机名
     */
    public static String getWapEndPoint() {
        return getWapEndPoint(sApiEnvironmentType);
    }

    /**
     * 获取标记环境旧接口主机名
     */
    public static String getOldEndPoint() {
        return getOldEndPoint(sApiEnvironmentType);
    }

    /**
     * 获取标记环境新接口主机名
     */
    public static String getNewEndPoint() {
        return getNewEndPoint(sApiEnvironmentType);
    }

    /**
     * 获取标记环境sso主机名
     */
    public static String getSsoEndPoint() {
        return getSsoEndPoint(sApiEnvironmentType);
    }

    /**
     * 获取指定环境wap主机名
     *
     * @param apiEnvironmentType {@linkplain ApiEnvironmentType}
     */
    public static String getWapEndPoint(String apiEnvironmentType) {
        switch (apiEnvironmentType) {
            case ApiEnvironmentType.STABLE:
                return ApiPoint.WAP_END_POINT_STABLE;
            case ApiEnvironmentType.BETA:
                return ApiPoint.WAP_END_POINT_BETA;
            case ApiEnvironmentType.TRANSITION:
                return ApiPoint.WAP_END_POINT_TRANSITION;
            default:
                break;
        }
        return "";
    }

    /**
     * 获取指定环境旧接口主机名
     *
     * @param apiEnvironmentType {@linkplain ApiEnvironmentType}
     */
    public static String getOldEndPoint(String apiEnvironmentType) {
        switch (apiEnvironmentType) {
            case ApiEnvironmentType.STABLE:
                return ApiPoint.OLD_END_POINT_STABLE;
            case ApiEnvironmentType.BETA:
                return ApiPoint.OLD_END_POINT_BEAT_BETA;
            case ApiEnvironmentType.TRANSITION:
                return ApiPoint.OLD_END_POINT_BEAT_TRANSITION;
            default:
                break;
        }
        return "";
    }

    /**
     * 获取指定环境新接口主机名
     * @param apiEnvironmentType {@linkplain ApiEnvironmentType}
     */
    public static String getNewEndPoint(String apiEnvironmentType) {
        switch (apiEnvironmentType) {
            case ApiEnvironmentType.STABLE:
                return ApiPoint.NEW_END_POINT_STABLE;
            case ApiEnvironmentType.BETA:
                return ApiPoint.NEW_END_POINT_BETA;
            case ApiEnvironmentType.TRANSITION:
                return ApiPoint.NEW_END_POINT_TRANSITION;
            default:
                break;
        }
        return "";
    }

    /**
     * 获取指定环境sso口主机名
     *
     * @param apiEnvironmentType {@linkplain ApiEnvironmentType}
     */
    public static String getSsoEndPoint(String apiEnvironmentType) {
        switch (apiEnvironmentType) {
            case ApiEnvironmentType.STABLE:
                return ApiPoint.SSO_END_POINT_BEAT_STABLE;
            case ApiEnvironmentType.BETA:
                return ApiPoint.SSO_END_POINT_BEAT_BETA;
            case ApiEnvironmentType.TRANSITION:
                return ApiPoint.SSO_END_POINT_BEAT_TRANSITION;
            default:
                break;
        }
        return "";
    }
}
