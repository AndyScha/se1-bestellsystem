package system.impl;

import system.Repository;

public class RepositoryMOCK implements Repository{

	@Override
	public Object save(Object entity) {
		return entity;
	}
}
