package org.boot.tech.core.util;

import java.util.UUID;

public class UUIDUtil {
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
