package br.com.yaw.repository;

import br.com.yaw.entity.UserImage;
import br.com.yaw.exception.RepositoryException;

public interface UserImageRepository {
	
	public void addUserImage(UserImage userImage) throws RepositoryException;

	public UserImage getUserImageByUserId(long uid)throws RepositoryException;
	
}
