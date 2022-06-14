package com.example.fruits.config;

public class AlipayConfig {
	// 商户appid
	public static String APPID = "2016102700770449";
	// 私钥 pkcs8格式的
	public static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCSsHISpWiibk9ZHBfQsID42y/A6JACA4pbWmyHXUhN2PfYmdrIfbRNRH4fcfrZjqwj+6Vsmist1jklOj+iT19zH7ylJKNGnH9kuoBY02t+C6eeKpJs+IrrMp59oGxO0kdKtI15LF4gdqPg1Ord+dmDsGLpRqv8A6dBUfZ7291Ci6Z7V6UjSDJT4g6+iCuY16owJwc2lQfmjWQpar+Cd6Z7Y0KuIPGzOduR9EJ/cC/dgHbXXdQ18VSphpdTM6JPn4PHaOhjDPUa4pPZI4Csk990ouTPq10z+cGcPBmwI8mDTBLfvkc1O9CdM2mxMvrXf0+4sGbDJTP4svj1oSiK89IJAgMBAAECggEAKgYAeBUI99ewRbAFppSaLjDk6lf1wY3wGiG6i8EOkLbCsjvJcLyKZcRkeW+21JLJ//j8uDMmgZgogODCECa7CokiCxGG8RiualX/pPLUjaSbHXXGww8lBSJYQ1ctEBuvrtgw23D5ekVix2tX6cXFKbPjcj770FPfm5f2jB89sO1Prx5w9YxBygXhhGchl++JyU4bu3kKOIF0aqxwNMPE8VggQipuUtLvRohvQiQ4cvSkaat6b9BUe/1ySv5/fbfpcg2m851IryEeBJ5kQhCEdAkjvlWWNYp7drUVfEUdaR+CuIoxeh7DY6abmK0967M0XSxJ0531SZ9QuNLGwiXQzQKBgQDQSbB6xbhrfI226Ft2WuTIOIpwnJQA78G+LiNllwCT+Z9YsXpVrG/GAKi45xPYMxWg4tyNZkgpXzm4YSCaGexkaLHkikD6JAbQhudZAQpWx6yosqNu5xWw15HrcPSYCHwMjXDiUPl+4gnYp/LsLf58XxAMGQF8H/8DUiR5jyeDNwKBgQC0SoWUv0EqXDxdekrJhDx2NfP5uKG1QTh4uYgwI497s1d+IP4j9yxyZycbcUoarFoupcNyUSS2kadrBvPefzOJQtRmEiSeTstUonjmuDiTLkh/q0uwrm1mETEAnmdxUPemjChOZyq5N11x5zgWERn2C+Irycombu1G6ncA3Rx0vwKBgQCutRClelEdP4Uy5aVKGiWKV/qvSHbQbkqpNdQU81rVw+zHnV5KnG/IumbLaW/4yaiboY5zwFZI9YnZwQZym8nv0u1cECc+aZeZs3475qaAcc2+VtDFRmmoQrkvrjhLidpbQQxtmJQdvIWbyAv31QB/29D/BL59Ge1yIKVdnRj8cwKBgBToT3VgeH4hFB8C/4k+/IP43cIn1xjd516YpWJvvUPrGEpYKSCimVTrAW39RUqykgK21mN7ZXaGjZOryrwSpDdWuKrdj8GMAahe7wZSdr0asO2dP7nNgIJFnBkmRXiumwmFV2dAL4QrF3sOBUMJ2Kjf1+qcDAVI99sxdXt0KMihAoGBAMGYhFj4oQjCNcfoAW5SJLlTSeRSaPIfqNUHtTXcKD4h1RgguEavhs3Vk2bY1sRJIMT1DKD8AjYOcLxbM2BFGYRU0mu8O6xfGr7fre8wmktAodbIcWZh+aoxwRkpmGC4r1M+5/BXO1C/BXAB2hxNXA+//RFk1hFzQHWw0JTtT1iF";
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/notify_url.jsp";
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/return_url.jsp";
	// 请求网关地址
	public static String URL = "https://openapi.alipaydev.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqO9xSaH9A9BmpH4qJI739vXKFUDeSPnA46y06wfeYusMke2zHcxqxPEzPs871KF3mGB7624ndZSdlgllzrPeglQIYixST6WdVUan5RtAU6XinuMnHq3gk0cR8qsXucsWF/w9sUpvZEXg6Au7fKKt48jMZzLuInxtrJ+hSkFK+9T/yCOJUHeKp/8ySXzJGa3udLxY9u5ijcYTqamX7eTL5qyzD2zUlkDETV8bj11Q9crNk1BeM28/k88K6hZ4djUJf7+524II8nWMhyTVAzVNjKPl9R7QozFXy2YKB22trNosnq2xc23HPfI2RtOECgeZTiB+VMGbabw5Rk/hol/SJwIDAQAB";
	// 日志记录目录
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";
}
