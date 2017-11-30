package org.boot.tech.web.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.boot.tech.web.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 */
import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 *
 * 微博问卷
 * 
 * @author Lawrence 微博问卷防CSRF攻击说明 思路：手机号上进行防控【每天一次】，IP上进行防控【每秒最大三次】
 *
 *         需求背景：小鸣单车在微博上关于退押金事宜的调查问卷。今日我们的HTTP接口遭受CSRF攻击。这里做一个单个接口的解决案例。
 *         方案的要点：提高对方攻击成本（一个IP封闭7天）提高运营效率比（一个号码一天提交一次）。
 *         方案特点：简要、只适合简单工程和接口使用 
 *         下方以代码贴出
 *
 *
 *         接口说明 1.提交问卷【业务接口】
 *         http://192.168.6.67:8080/complain/weibo/1.0?mobile=15700114734&nikeName=testName&desc=123123123
 *         2.释放IP黑名单【后台操作接口】
 *         http://192.168.6.67:8080/complainRefresh/1.0/?password=]Xming@b&reqeustIp=192.168.6.67
 *         3.释放手机黑名单【后台操作接口】
 *         http://192.168.6.67:8080/complainRefresh/1.0/?password=]Xming@b&mobile=15700114735
 *
 *         接口返回值说明 
 *         200 业务操作成功 
 *         40001，40002 IP命中黑名单（规则：每秒钟不能超过3次）
 *         40003，40004手机命中黑名单（规则：每个手机号一天只能提交1次） 
 *         50001， 刷新名单接口密码错误
 */
@RestController
public class CSRFController {

	private static final Logger logger = LoggerFactory.getLogger(CSRFController.class);

	public static final Integer INIT_COMPLAIN_STATUS = 0;

	public static final String WEIBO_SOURCE = "weibo";

	public final static LoadingCache<String, Integer> ipBlackList = CacheBuilder.newBuilder()
			// 设置cache的初始大小为10，要合理设置该值
			.initialCapacity(1000)
			// 设置并发数为1000，即同一时间最多只能有1000个线程往cache执行写入操作
			.concurrencyLevel(1000).maximumSize(Long.MAX_VALUE)
			// 设置cache中的数据在写入之后的存活时间为24小时
			.expireAfterWrite(7, TimeUnit.DAYS)
			// 构建cache实例
			.build(new CacheLoader<String, Integer>() {
				@Override
				public Integer load(String key) throws Exception {
					return -1;
				}
			});

	private final static LoadingCache<String, Integer> ipFrequency = CacheBuilder.newBuilder()
			// 设置cache的初始大小为10，要合理设置该值
			.initialCapacity(10000).maximumSize(Long.MAX_VALUE)
			// 设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
			.concurrencyLevel(1000)
			// 设置cache中的数据在写入之后的存活时间为10秒
			.expireAfterWrite(10, TimeUnit.SECONDS)
			// 构建cache实例
			.build(new CacheLoader<String, Integer>() {
				@Override
				public Integer load(String key) throws Exception {
					return 0;
				}
			});

	private final static LoadingCache<String, Integer> phoneFrequency = CacheBuilder.newBuilder()
			// 设置cache的初始大小为10，要合理设置该值
			.initialCapacity(10000).maximumSize(Long.MAX_VALUE)
			// 设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
			.concurrencyLevel(1000)
			// 设置cache中的数据在写入之后的存活时间为10秒
			.expireAfterWrite(24, TimeUnit.HOURS)
			// 构建cache实例
			.build(new CacheLoader<String, Integer>() {
				@Override
				public Integer load(String key) throws Exception {
					return 0;
				}
			});

	public final static LoadingCache<String, Integer> phoneBlackList = CacheBuilder.newBuilder()
			// 设置cache的初始大小为10，要合理设置该值
			.initialCapacity(1000)
			// 设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
			.concurrencyLevel(1000).maximumSize(Long.MAX_VALUE)
			// 设置cache中的数据在写入之后的存活时间为24小时
			.expireAfterWrite(24, TimeUnit.HOURS)
			// 构建cache实例
			.build(new CacheLoader<String, Integer>() {
				@Override
				public Integer load(String key) throws Exception {
					return -1;
				}
			});
	private static final int FREQUENCY_MAX_VALUE_MOBILE = 1;

	private static int FREQUENCY_MAX_VALUE = 4;

	@PostMapping(path = "/complain/{source}/1.0") // Map ONLY GET Requests
	public Object complain(@RequestParam String mobile, @RequestParam String nikeName, @RequestParam String desc,
			HttpServletRequest request) throws ExecutionException {

		String ip = getIpAddress(request);
		logger.info("收到访问请求ip {},mobile {},nikeName {}, desc {}", ip, mobile, nikeName, desc);
		checkFrequency(mobile, ip);
		return Response.successResponse("200 , 提交成功");
	}

	@PostMapping(path = "/complainRefresh/1.0/")
	public Object refresh(@RequestParam String password, String reqeustIp, String mobile, HttpServletRequest request)
			throws ExecutionException {
		if (null == password || !"]Xming@b[".equals(password)) {
			String ip = getIpAddress(request);
			logger.warn("50001，警报,有用户试图攻击 ip地址 {}", getIpAddress(request));
			if (null != reqeustIp && !"".equals(reqeustIp)) {
				ipBlackList.put(ip, 10);
			}
			if (null != mobile && !"".equals(mobile)) {
				phoneBlackList.put(mobile, 10);
			}
			return Response.successResponse("50001 , 您已被加入黑名单.");
		}
		if (null != reqeustIp && !"".equals(reqeustIp)) {
			ipBlackList.refresh(reqeustIp);
			ipFrequency.refresh(reqeustIp);
			logger.info("200，IP[" + reqeustIp + "]已经移出黑名单");
			return Response.successResponse("200", "IP[" + reqeustIp + "]已经移出黑名单");
		}
		if (null != mobile && !"".equals(mobile)) {
			phoneBlackList.refresh(mobile);
			phoneFrequency.refresh(mobile);
			logger.info("200，手机[" + mobile + "]已经移出黑名单");
			return Response.successResponse("200", "手机[" + mobile + "]已经移出黑名单");
		}

		return Response.successResponse("404", "page not found.");

	}

	private void checkFrequency(String mobile, String ip) throws ExecutionException {

		int count = ipBlackList.get(ip);
		if (-1 != count) {
			logger.info("40001，ip {} 命中黑名单", ip);
			throw new RuntimeException(JSON.toJSONString(Response.successResponse("40001", "请求频率过快,已被限制访问。请明日再来")));
		}

		int val = ipFrequency.get(ip);
		ipFrequency.put(ip, ++val);
		// 每秒不能超过三次
		if (val >= FREQUENCY_MAX_VALUE) {
			ipBlackList.put(ip, val);
			logger.info("40002，ip {} 首次加入黑名单", ip);
			throw new RuntimeException(JSON.toJSONString(Response.successResponse("40002", "您的频率过快,已被限制访问。")));
		}

		int phoneCount = phoneBlackList.get(mobile);
		if (-1 != phoneCount) {
			logger.info("40003，手机号 {} 命中黑名单", mobile);
			throw new RuntimeException(JSON.toJSONString(Response.successResponse("40003", "您的手机号今天已经提交过。请勿重复提交")));
		}

		int phoneVal = phoneFrequency.get(mobile);
		phoneFrequency.put(mobile, ++phoneVal);
		// 每个号号码一天之内不能提交大于2次
		if (phoneVal > FREQUENCY_MAX_VALUE_MOBILE) {
			phoneBlackList.put(mobile, val);
			logger.info("40004，手机号 {} 首次加入黑名单", mobile);
			throw new RuntimeException(JSON.toJSONString(Response.successResponse("40004", "您的手机号今天已经提交过。请勿重复提交")));

		}

	}

	private String removeEmoji(String source) {
		if (source != null) {
			source = source.replaceAll("[\\x{10000}-\\x{10FFFF}]", "");
			Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
					Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
			Matcher emojiMatcher = emoji.matcher(source);
			if (emojiMatcher.find()) {
				source = emojiMatcher.replaceAll("*");
				return source;
			}
			return source;
		}
		return source;
	}

	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	@ExceptionHandler(Exception.class)
	public Object exception(Exception e, HttpServletRequest request) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(200));
	}

}