package com.nixsolutions.webapp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nixsolutions.webapp.model.Role;
import com.nixsolutions.webapp.persistence.RoleDao;
import com.nixsolutions.wsxx.errorHandling.exceptions.DataNotFoundExcpetion;
import com.nixsolutions.wsxx.errorHandling.exceptions.ResourceAlreadyExistsException;

@Service
//performs our logic using persistence layer
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;

	@Override
	@Transactional
	public void create(Role role) {
		if (roleDao.findByName(role.getName()) != null) {
			throw new ResourceAlreadyExistsException(role + " already in db");
		}
		roleDao.create(role);
	}

	@Override
	@Transactional
	public void update(Role role) {
		if (roleDao.findById(role.getRoleId()) == null) {
			throw new DataNotFoundExcpetion("resource " + role + " not found");
		}
		// excess entry in child table
		if (roleDao.findByName(role.getName()) != null) {
			throw new ResourceAlreadyExistsException(role.toString() + " entry with this name already in db");
		}
		roleDao.update(role);
	}

	@Override
	@Transactional
	public void remove(Role role) {
		// upd count = 0
		if (findById(role.getRoleId()) == null) {
			throw new DataNotFoundExcpetion(role.toString() + " doesn't exist in DB");
		}
		roleDao.remove(role);
	}

	@Override
	public Role findById(Long roleId) {
		Role role = roleDao.findById(roleId);
		if (role == null) {
			throw new DataNotFoundExcpetion("no role with id " + roleId);
		}
		return role;
	}

	@Override
	@Transactional
	public Role findByName(String name) {
		Role role = roleDao.findByName(name);
		if (role == null) {
			throw new DataNotFoundExcpetion("no role with name " + name);
		}
		return role;
	}

	@Override
	@Transactional
	public List<Role> findAll() {
		List<Role> roles = roleDao.findAll();
		if (roles.size() == 0) {
			throw new DataNotFoundExcpetion("no roles ");
		}
		return roles;
	}

}
