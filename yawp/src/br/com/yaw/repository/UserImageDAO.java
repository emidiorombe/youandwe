package br.com.yaw.repository;

import com.google.appengine.api.datastore.Key;

import br.com.yaw.entity.UserImage;
import br.com.yaw.exception.RepositoryException;

public class UserImageDAO extends BaseDAO<UserImage, Key> implements UserImageRepository{

	@Override
	public void addUserImage(UserImage userImage) throws RepositoryException {
		try {
			beginTransaction();
			save(userImage);
			commitTransaction();
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
		
	}

	@Override
	public UserImage getUserImageByUserId(long uid) throws RepositoryException {
		try {
			beginTransaction();
			
			StringBuilder jql = new StringBuilder("select ui from UserImage ui where ui.userId = :uid");
			addParamToQuery("uid", uid);
			
			UserImage userImage =  (UserImage) executeQueryOneResult(jql.toString(), paramsToQuery);
			
			commitTransaction();
			
			return userImage;
			
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
	}
	

}
