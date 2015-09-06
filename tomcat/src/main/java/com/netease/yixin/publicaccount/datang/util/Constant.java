/**
 * 
 */
package com.netease.yixin.publicaccount.datang.util;

/**
 * 
 * 公众帐号用到的一些常量
 * 
 * @author Administrator
 * 
 */
public interface Constant {

	/******************
	 * 群发消息的发送情况：成功/失败
	 */
	public static final int PUBLIC_ACCOUNT_GROUP_MSG_SEND_SUCCESS = 0;

	public static final int PUBLIC_ACCOUNT_GROUP_MSG_SEND_FAIL = 2;

	public static final String API_RESULT_SUCCESS = "ok";

	public static final String API_RESULT_ERROR = "error";

	/**
	 * code类型
	 */
	public static final int CODE_SUCCESS = 200; // 请求成功
	public static final int CODE_FAILURE = 400; // 请求失败
	public static final int CODE_PARAM_ERROR = 300;// 参数错误
	public static final int CODE_NET_ERROR = -100;//
	public static final int CODE_CACHE_SUCCESS = 204;//

	/**
	 * 响应的message类型
	 */
	public static final String RESPONSE_MSG_GROUP_NAME_DUPLICATED = "已有重复的分组名称";
	public static final String RESPONSE_MSG_ILLEGAL_GROUP_NAME = "非法的分组名称";
	public static final String RESPONSE_MSG_FAILURE = "请求失败，内部服务器错误";
	public static final String RESPONSE_MSG_NO_GROUP = "分组不存在或者没有权限";
	public static final String RESPONSE_MSG_NO_USER = "用户不存在或者没有权限获取用户信息";

	public static final int VAILD = 0; // 正常
	public static final int VAILDING = 1; // 申请中
	public static final int INVAILD = -1;

	public static final int TYPE_MM = 1;
	public static final int TYPE_AI = 0;

}
