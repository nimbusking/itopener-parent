package com.itopener.demo.cache.redis.caffeine.service;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.itopener.demo.cache.redis.caffeine.vo.UserVO;
import com.itopener.utils.TimestampUtil;

@Service
public class CacheRedisCaffeineService {
	
	private final Logger logger = LoggerFactory.getLogger(CacheRedisCaffeineService.class);

	@Cacheable(key = "'cache_user_id_' + #id", value = "userIdCache", cacheManager = "cacheManager", sync = true)
	public UserVO get(long id) {
		logger.info("get by id from db");
		UserVO user = new UserVO();
		user.setId(id);
		user.setName("name" + id);
		user.setCreateTime(TimestampUtil.current());
		return user;
	}
	
	@Cacheable(key = "'cache_user_name_' + #name", value = "userNameCache", cacheManager = "cacheManager")
	public UserVO get(String name) {
		logger.info("get by name from db");
		UserVO user = new UserVO();
		user.setId(new Random().nextLong());
		user.setName(name);
		user.setCreateTime(TimestampUtil.current());
		return user;
	}

	@Cacheable(key = "'cache_user_id_' + #id", value = "userIdCache", cacheManager = "cacheManager", sync = true)
	public UserVO getForNull(long id) {
		logger.info("get by name from db, but return null");
		return null;
	}

	@Cacheable(key = "'cache_user_id_' + #id", value = "userIdCache", cacheManager = "cacheManager")
	public UserVO getForNullWithNoSync(long id) {
		logger.info("get by name from db, but return null");
		return null;
	}
	
	@CachePut(key = "'cache_user_id_' + #userVO.id", value = "userIdCache", cacheManager = "cacheManager")
	public UserVO update(UserVO userVO) {
		logger.info("update to db");
		userVO.setCreateTime(TimestampUtil.current());
		return userVO;
	}
	
	@CacheEvict(key = "'cache_user_id_' + #id", value = "userIdCache", cacheManager = "cacheManager")
	public void delete(long id) {
		logger.info("delete from db");
	}
}
